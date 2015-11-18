package se.sogeti.summerjob.addsummerjobapplication;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class BusinessSectorSummerJobApplicationModule extends AnnotatedRESTModule{
	
	
	private BusinessSectorJobApplicationDAO jobApplicationDAO;
	private BusinessSectorJobDAO jobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till den plats där ansökan hanteras", name="ManageApplicationURL")
	String manageApplicationURL = "manage-business-app";

	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		jobApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, hierarchyDaoFactory);
		jobDAO = new BusinessSectorJobDAO(dataSource, BusinessSectorJob.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		
		if(jobId!=null){
			BusinessSectorJob job = jobDAO.getById(jobId);
			log.info(job);
			Element jobInfo = doc.createElement("JobInfo");
			XMLUtils.append(doc, jobInfo, job);
			doc.getFirstChild().appendChild(jobInfo);

			Element jobApplication = doc.createElement("JobApplicationForm");
			XMLUtils.appendNewElement(doc, jobApplication, "manageAppURL", manageApplicationURL);
			XMLUtils.appendNewElement(doc, jobApplication, "jobId", job.getId());
			BusinessSectorJobApplication app = null;
			if (appId != null) {
				app = jobApplicationDAO.getById(appId);
				XMLUtils.append(doc, jobApplication, app);
			}
			
			Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
			jobApplication.appendChild(driversLicenseElement);
			List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
			for (DriversLicenseType type : driverslicenseTypes) {
				Element typeElement = doc.createElement("DriversLicenseType");
				XMLUtils.appendNewElement(doc, typeElement, "id", type.getId());
				XMLUtils.appendNewElement(doc, typeElement, "name", type.getName());
				XMLUtils.appendNewElement(doc, typeElement, "description", type.getDescription());
				
				if (app != null && app.getDriversLicenseType() != null) {
					XMLUtils.appendNewElement(doc, typeElement, "selected",
							app.getDriversLicenseType().getId().intValue() == type.getId().intValue());
				}
				driversLicenseElement.appendChild(typeElement);
			}
			
			doc.getFirstChild().appendChild(jobApplication);
			
		} else {
			Element jobList = doc.createElement("JobList");
			List<BusinessSectorJob> jobs = jobDAO.getAllApproved();		
			XMLUtils.append(doc, jobList,jobs);
			doc.getFirstChild().appendChild(jobList);
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="save/businessapplication.json", method="post")
	public void saveApplication(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) {
			log.info("POST");
			PrintWriter writer = null;
			try {
				writer = res.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String callback = req.getParameter("callback"); 
			JsonResponse.initJsonResponse(res, writer, callback);
			
			Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
			BusinessSectorJob job = null;
			try {
				job = jobDAO.getByIdWithApplications(jobId);
			} catch (SQLException e1) {
				log.error(e1);
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte hämta jobbet som ansökan ska tillhöra.\"}", callback, writer);
				return;
			}
			
			if(job != null) {
				Integer appId = NumberUtils.toInt(req.getParameter("appId"));
				BusinessSectorJobApplication app = null;
				
				if (appId != null) {
					try {
						app = jobApplicationDAO.getByIdWithJob(appId);
					} catch (SQLException e) {
						log.error(e);
						JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Det går inte att hämta upp den ansökan som ska ändras.\"}", callback, writer);
						return;
					}
				}
				
				// If this is a new application or if getApplicationsFromJob returned null
				if (app == null) {
					app = new BusinessSectorJobApplication();
				}
				
				Citizen person = null;

				String socialSecurityNumber = req.getParameter("socialSecurityNumber");
				if (socialSecurityNumber == null || socialSecurityNumber.isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ange ett personnummer i din ansökan.\"}", callback, writer);
					return;
				}
				

				if (socialSecurityNumber.length() != 12) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret måste bestå av 12 tecken (ÅÅÅÅMMDDxxxx).\"}", callback, writer);
					return;
				}

				try {
					person = smexServiceHandler.getCitizen(socialSecurityNumber);
				} catch (SmexServiceException e){
					log.error(e);
				} catch (Exception e) {
					log.error(e);
				}
				
				FormUtils.createJobApplication(app, req, person);

				boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;
				if (hasDriversLicense) {
					Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
					if (typeId == null) {
						JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste du ange en körkortstyp\"}", callback, writer);
						return;
					}
					DriversLicenseType type = null;
					try {
						type = driversLicenseTypeDAO.getTypeById(typeId);
						app.setDriversLicenseType(type);
					} catch (SQLException e1) {
						log.error(e1);
						JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte hämta körkortstypen.\"}", callback, writer);
						return;
					}
				} else {
					app.setDriversLicenseType(null);
				}
				
				if (app.getFirstname() == null || app.getFirstname().isEmpty() || app.getCity() == null || app.getCity().isEmpty() ||
						app.getLastname() == null || app.getLastname().isEmpty() || app.getPhoneNumber() == null || app.getPhoneNumber().isEmpty() ||
						app.getSocialSecurityNumber() == null || app.getSocialSecurityNumber().isEmpty() ||
						app.getStreetAddress() == null || app.getStreetAddress().isEmpty() || 
						app.getZipCode() == null || app.getZipCode().isEmpty() || app.getEmail() == null || app.getEmail().isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för personuppgifter kan inte lämnas tomma. Det enda som inte krävs är en e-postadress.\"}", callback, writer);
					return;
				}

				if (app.getPersonalLetter() == null || app.getPersonalLetter().isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ge ett kort personligt brev i din ansökan.\"}", callback, writer);
					return;
				}
				
				if (app.getBirthDate() == null) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret innehåller inget korrekt datum.\"}", callback, writer);
					return;
				}
				
				if (app.getId() == null) {
					if(job.getApplications() != null) {
						job.getApplications().add(app);
					} else {
						List<BusinessSectorJobApplication> appliedApplications = new ArrayList<BusinessSectorJobApplication>();
						appliedApplications.add(app);
						job.setApplications(appliedApplications);
					}
				}

				try {
					log.info("Ska påbörja sparning");
					if (app.getId() == null) {
						log.info("Det är en ny ansökan");
						jobDAO.save(job);
						JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Din ansökan har nu sparats.\"}", callback, writer);
					} else {
						log.info("Vi redigerar en befintlig");
						jobApplicationDAO.save(app);
						JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
					}
				} catch (SQLException e) {
					log.error(e);
					JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Ansökan kunde inte sparas.\"}", callback, writer);
				}
			} else {
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ansökan skulle sparas.\"}", callback, writer);
		}
	}
	
//	private BusinessSectorJobApplication getApplicationFromJob(List<BusinessSectorJobApplication> applications, Integer applicationId) {
//		if (applicationId == null || applications == null) {
//			return null;
//		}
//		
//		for (BusinessSectorJobApplication app : applications) {
//			if (app.getId().intValue() == applicationId.intValue()) {
//				return app;
//			}
//		}
//		return null;
//	}
}
