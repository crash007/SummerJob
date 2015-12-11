package se.sogeti.summerjob.addsummerjobapplication;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.fileuploadutils.MultipartRequest;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.io.BinarySizes;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class BusinessSectorSummerJobApplicationModule extends AddSummerJobApplication<BusinessSectorJobApplication>{
	
	
	private BusinessSectorJobApplicationDAO jobApplicationDAO;
	private BusinessSectorJobDAO jobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till den plats där ansökan hanteras", name="ManageApplicationURL")
	String manageApplicationURL = "manage-business-app";

	
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
			log.debug(job);
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
			

			XMLUtils.append(doc, jobApplication, "DriversLicenseTypes",driversLicenseTypeDAO.getAll());
		
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
	public void saveApplication(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException {
			
		MultipartRequest requestWrapper = null;

		try {
			requestWrapper = new MultipartRequest(1024 * BinarySizes.KiloByte, 100 * BinarySizes.MegaByte, req);

			PrintWriter writer = null;
			writer = res.getWriter();

			String callback = requestWrapper.getParameter("callback"); 
			JsonResponse.initJsonResponse(res, writer, callback);

			Integer jobId = NumberUtils.toInt(requestWrapper.getParameter("jobId"));
			log.info("Start saving application for jobId: "+jobId);
			
			BusinessSectorJob job = null;
			try {
				job = jobDAO.getByIdWithApplications(jobId);
			} catch (SQLException e1) {
				log.error(e1);
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte hämta jobbet som ansökan ska tillhöra.\"}", callback, writer);
				return;
			}

			if(job != null) {
				Integer appId = NumberUtils.toInt(requestWrapper.getParameter("appId"));
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

				String socialSecurityNumber = requestWrapper.getParameter("socialSecurityNumber");
				
				if(!validateSocialSecurityNumber(writer, callback, socialSecurityNumber)){
					return;
				}

				Citizen person = getCitizen(socialSecurityNumber);

				createJobApplication(app, requestWrapper, person);
				automaticControllAndApprove(app);

				boolean hasDriversLicense = requestWrapper.getParameter("hasDriversLicense") != null ? true : false;
				
				if (hasDriversLicense) {
					Integer typeId = NumberUtils.toInt(requestWrapper.getParameter("driversLicenseType"));
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

				if(!validatePersonalInformation(writer, callback, app)){
					return;
				}

				if(!validatePersonalLetter(writer, callback, app)){
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
				
				FileItem fileItem = requestWrapper.getFile(0);
				if (!StringUtils.isEmpty(fileItem.getName())) {
					saveCv(app,fileItem,job.getId()+"_"+app.getSocialSecurityNumber()+"_"+fileItem.getName(),writer, callback);
				} 
				
				if (app.getId() == null) {
					if (user != null && user.getUsername() != null) {
						app.setAddedByUser(user.getUsername());
					}
				}

				try {
					log.info("Påbörjar sparning av näringslivsansökan");
					if (app.getId() == null) {
						log.debug("Det är en ny ansökan");
						jobDAO.save(job);

						JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Din ansökan har nu sparats.\"}", callback, writer);
					} else {
						log.debug("Vi redigerar en befintlig");
						jobApplicationDAO.save(app);
						JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
					}
					log.info("Ansökan har sparats");
				} catch (SQLException e) {
					log.error(e);
					JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Ansökan kunde inte sparas.\"}", callback, writer);
				}
			} else {
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ansökan skulle sparas.\"}", callback, writer);
			}
		} catch (FileUploadException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
