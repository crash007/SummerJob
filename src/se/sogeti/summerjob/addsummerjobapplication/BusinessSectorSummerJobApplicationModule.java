package se.sogeti.summerjob.addsummerjobapplication;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.periodsadmin.JsonResponse;
import se.sogeti.summerjob.FormUtils;
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
	
	
	private JobApplicationDAO<BusinessSectorJobApplication> jobApplicationDAO;
	private BusinessSectorJobDAO jobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
//	@ModuleSetting
//	@TextFieldSettingDescriptor(description="Felmeddelande för ", name="Errormessage")
//	String errorMEssage="dsfgsddfgsd";
	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		jobApplicationDAO = new JobApplicationDAO<BusinessSectorJobApplication>(dataSource, BusinessSectorJobApplication.class, hierarchyDaoFactory);
		jobDAO = new BusinessSectorJobDAO(dataSource, BusinessSectorJob.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		
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
			XMLUtils.appendNewElement(doc, jobApplication, "jobId", job.getId());
			Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
			List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
			jobApplication.appendChild(driversLicenseElement);
			XMLUtils.append(doc, driversLicenseElement, driverslicenseTypes);

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
	public void saveApplication(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
			log.info("POST");
			
			PrintWriter writer = res.getWriter();
			String callback = req.getParameter("callback"); 
			JsonResponse.initJsonResponse(res, writer, callback);
			
			Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
			BusinessSectorJob job = jobDAO.getByIdWithApplications(jobId);
			
			if(job != null) {
				BusinessSectorJobApplication app = new BusinessSectorJobApplication();
				Citizen person = null;
				
				String socialSecurityNumber = req.getParameter("socialSecurityNumber");
				if (socialSecurityNumber == null || socialSecurityNumber.isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ange ett personnummer i din ansökan.\"}", callback, writer);
					return;
				}
				
				if (socialSecurityNumber.length() != 12 && socialSecurityNumber.length() != 13) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret måste bestå av 12 eller 13 tecken (med eller utan bindestreck).\"}", callback, writer);
					return;
				}
				
//				try {
//					person = smexServiceHandler.getCitizen(socialSecurityNumber);
//				} catch (SmexServiceException e){
//					log.error(e);
//				}
				
				FormUtils.createJobApplication(app, req, person);
				
				boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;				
				app.setHasDriversLicense(hasDriversLicense);
				
				if (hasDriversLicense) {
					Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
					if (typeId == null) {
						JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste du ange en körkortstyp\"}", callback, writer);
						return;
				}
				DriversLicenseType type = driversLicenseTypeDAO.getTypeById(typeId);
				app.setDriversLicenseType(type);
				
				if (app.getFirstname() == null || app.getFirstname().isEmpty() || app.getCity() == null || app.getCity().isEmpty() ||
						app.getLastname() == null || app.getLastname().isEmpty() || app.getPhoneNumber() == null || app.getPhoneNumber().isEmpty() ||
						app.getSocialSecurityNumber() == null || app.getSocialSecurityNumber().isEmpty() ||
						app.getStreetAddress() == null || app.getStreetAddress().isEmpty() || 
						app.getZipCode() == null || app.getZipCode().isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för personuppgifter kan inte lämnas tomma. Det enda som inte krävs är en e-postadress.\"}", callback, writer);
					return;
				}
				
				if (app.getPersonalLetter() == null || app.getPersonalLetter().isEmpty()) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ge ett kort personligt brev i din ansökan.\"}", callback, writer);
					return;
				}
				
				Calendar dob = Calendar.getInstance();
				if (app.getDateOfBirth() == null) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret innehåller inget korrekt datum.\"}", callback, writer);
					return;
				}
				dob.setTime(app.getDateOfBirth());
				Calendar jobStartDate = Calendar.getInstance();
				jobStartDate.setTime(job.getStartDate());
				int age = jobStartDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
				if (jobStartDate.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;  
				} else if (jobStartDate.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && jobStartDate.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;  
				}
				
				app.setOverEighteen(age >= 18 ? true : false);
				
				//Worker applies for a job but has not yet got the job.
				app.setAssigned(false);
				
				if(job.getApplications() != null) {
					job.getApplications().add(app);
				} else {
					List<BusinessSectorJobApplication> appliedApplications = new ArrayList<BusinessSectorJobApplication>();
					appliedApplications.add(app);
					job.setApplications(appliedApplications);
				}
				
				log.info(app);
				
				try {
					jobDAO.update(job);
					JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Din ansökan har nu sparats.\"}", callback, writer);
				} catch (SQLException e) {
					log.error(e);
					JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ansökan skulle sparas.\"}", callback, writer);
				}
			}
		} else {
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ansökan skulle sparas.\"}", callback, writer);
		}
	}
}
