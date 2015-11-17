package se.sogeti.summerjob.addsummerjob;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorManager;
import se.sogeti.jobapplications.beans.business.BusinessSectorMentor;
import se.sogeti.jobapplications.daos.ContactDetailsDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
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

public class AddBusinessSectorSummerJobModule extends AnnotatedRESTModule{
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att hantera jobbet", name="ManageJobURL")
	String manageJobURL = "manage-businesssector-job";
	
	private JobDAO<BusinessSectorJob> businessSectorJobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	private ContactDetailsDAO<BusinessSectorMentor> businessSectorMentorDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		businessSectorJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
		businessSectorMentorDAO = new ContactDetailsDAO<BusinessSectorMentor>(dataSource, BusinessSectorMentor.class, hierarchyDaoFactory);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		Element jobForm = doc.createElement("BusinessSectorJobForm");
		doc.getFirstChild().appendChild(jobForm);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		BusinessSectorJob job = null;
		if (jobId != null && user.isAdmin()) {
			job = businessSectorJobDAO.getById(jobId);
			XMLUtils.append(doc, jobForm, job);
			XMLUtils.appendNewElement(doc, jobForm, "manageJobURL", manageJobURL);
		} 
		
		Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
		List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
		
		for (DriversLicenseType type : driverslicenseTypes) {
			Element licenseType = doc.createElement("DriversLicenseType");
			XMLUtils.appendNewElement(doc, licenseType, "id", type.getId());
			XMLUtils.appendNewElement(doc, licenseType, "name", type.getName());
			XMLUtils.appendNewElement(doc, licenseType, "description", type.getDescription());
			
			if (job != null && job.getDriversLicenseType() != null) {
				XMLUtils.appendNewElement(doc, licenseType, "selected", 
						job.getDriversLicenseType().getId().intValue() == type.getId().intValue());
			}
			
			driversLicenseElement.appendChild(licenseType);
		}
			
		jobForm.appendChild(driversLicenseElement);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="add/businesssectorsummerjob.json", method="post")
	public void addSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        BusinessSectorJob job = jobId != null ? businessSectorJobDAO.getById(jobId) : new BusinessSectorJob();
        
        String profession = req.getParameter("profession");
        if (profession == null || profession.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Yrkestitel saknas i annonsen.\"}", callback, writer);
			return;
        }
        job.setWorkTitle(profession);
        job.setWorkDescription(req.getParameter("work-description"));

        Integer numberOfWorkers = NumberUtils.toInt(req.getParameter("numberOfWorkersNeeded"));
        if (numberOfWorkers == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Antalet lediga platser saknas i annonsen.\"}", callback, writer);
			return;
        } else if (numberOfWorkers.intValue() <= 0) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Antalet lediga platser måste vara mer än 0.\"}", callback, writer);
			return;
        }
        job.setNumberOfWorkersNeeded(numberOfWorkers);
        
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Datumfälten kan inte lämnas tomma.\"}", callback, writer);
			return;
        }
        
        if (startDate.length() > 10) {
        	startDate = startDate.substring(0, 10);
        }
        
        if (endDate.length() > 10) {
        	endDate = endDate.substring(0, 10);
        }
        
        Date jobStartDate, jobEndDate;
        
        try {
        	jobStartDate = Date.valueOf(startDate);
        	jobEndDate = Date.valueOf(endDate);
        } catch (IllegalArgumentException e) {
        	log.error(e);
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Datumfälten innehåller datum som inte är korrekt angivna\"}", callback, writer);
			return;
        }
        
        job.setStartDate(jobStartDate);
        job.setEndDate(jobEndDate);
        
        List<BusinessSectorMentor> mentors = new ArrayList<BusinessSectorMentor>();
        List<String> mentorUuids = FormUtils.getMentorUuids(req.getParameterNames());
		for(String s : mentorUuids){
			 BusinessSectorMentor mentor = new BusinessSectorMentor();
			 Integer mentorId = NumberUtils.toInt(req.getParameter("mentor-id-" + s));
			 
			 String mentorFirstname = req.getParameter("mentor-firstname_" + s);
			 String mentorLastname = req.getParameter("mentor-lastname_" + s);
			 String mentorPhone = req.getParameter("mentor-phone_" + s);
			 
			 if (mentorId != null) {
				 mentor.setId(mentorId);
				 
				 if ((mentorFirstname == null && mentorLastname == null && mentorPhone == null)
						 || (mentorFirstname.isEmpty() && mentorLastname.isEmpty() && mentorPhone.isEmpty())) {
					 businessSectorMentorDAO.removeById(mentorId);
					 continue;
				 }
			 } else if (mentorFirstname == null || mentorLastname == null || mentorPhone == null
					 || mentorFirstname.isEmpty() || mentorLastname.isEmpty() || mentorPhone.isEmpty()) {
				 continue;
			 }
			 
			 mentor.setFirstname(mentorFirstname);
			 mentor.setLastname(mentorLastname);
			 mentor.setEmail(req.getParameter("mentor-email_" + s));
			 mentor.setMobilePhone(mentorPhone);
			 mentors.add(mentor);
		}
		
		job.setMentors(mentors);
        
		String company = req.getParameter("company");
        String streetAddress = req.getParameter("street");
        String zipCode = req.getParameter("postalcode");
        String city = req.getParameter("postalarea");
        
        if (company == null || company.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fältet för företagsnamn kan inte lämnas tomt.\"}", callback, writer);
        	return;
        }
        
        if (streetAddress == null || zipCode == null || city == null 
        		|| streetAddress.isEmpty() || zipCode.isEmpty() || city.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för arbetsplatsens adress kan inte lämnas tomma.\"}", callback, writer);
        	return;
        }
        
        job.setStreetAddress(streetAddress);
        job.setZipCode(zipCode);
        job.setCity(city);
        job.setCompany(company);
        
        String managerFirstname = req.getParameter("manager-firstname");
        String managerLastname = req.getParameter("manager-lastname");
        String managerPhone = req.getParameter("manager-phone");
        String managerEmail = req.getParameter("manager-email"); // VALFRI!
        
        if (managerFirstname == null || managerLastname == null || managerPhone == null
        		|| managerFirstname.isEmpty() || managerLastname.isEmpty() || managerPhone.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Namn- och telefonnummerfälten kan inte lämnas tomma för den ansvarige på arbetsplatsen.\"}", callback, writer);
        	return;
        }
        
        BusinessSectorManager manager = job.getManager() != null ? job.getManager() : new BusinessSectorManager(); 
        manager.setFirstname(managerFirstname);
        manager.setLastname(managerLastname);
        manager.setMobilePhone(managerPhone);
        manager.setEmail(managerEmail);
        job.setManager(manager);
        
        if (jobId == null) { // Is a new job
            job.setApproved(false);
            job.setControlled(false);
        } else {
        	job.setInitiatedByUser(user.getUsername());
        }
        
        job.setIsOverEighteen(req.getParameter("isOverEighteen") != null ? true : false);
        log.info("isOverEighteen: " + req.getParameter("isOverEighteen"));
        boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;
        job.setHasDriversLicense(hasDriversLicense);
        log.info("hasDriversLicense: " + hasDriversLicense);
        
        if (hasDriversLicense) {
        	Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
        	
        	if (typeId == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste en körkortstyp väljas.\"}", callback, writer);
            	return;
        	}
        	DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
        	job.setDriversLicenseType(licenseType);
        }
        
        job.setFreeTextRequirements(req.getParameter("other-requirements"));
        
        if (jobId != null) {
        	job.setUpdated(new Date(Calendar.getInstance().getTimeInMillis()));
        } else {
        	job.setCreated(new Date(Calendar.getInstance().getTimeInMillis()));
        }
        
		try {
			businessSectorJobDAO.save(job);
			if (jobId != null) {
				JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna i annonsen har nu sparats.\"}", callback, writer);
			} else {
				JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu sparats. En handläggare kommer att granska annonsen innan den blir synlig för sökande.\"}", callback, writer);
			}
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när annonsen skulle sparas.\"}", callback, writer);
		}				
	}
}
