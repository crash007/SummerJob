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
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.JsonResponse;
import se.sogeti.summerjob.FormUtils;
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
	
	
	private JobDAO<BusinessSectorJob> businessSectorJobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		businessSectorJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
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
		
		Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
		List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
		jobForm.appendChild(driversLicenseElement);
		
		XMLUtils.append(doc, driversLicenseElement, driverslicenseTypes);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="add/businesssectorsummerjob.json", method="post")
	public void addSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        BusinessSectorJob job = new BusinessSectorJob();
        
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
        } else if (numberOfWorkers.intValue() == 0) {
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
        job.setStartDate(Date.valueOf(startDate));
        job.setEndDate(Date.valueOf(endDate));
        
        List<BusinessSectorMentor> mentors = new ArrayList<BusinessSectorMentor>();
        List<String> mentorUuids = FormUtils.getMentorUuids(req.getParameterNames());
		for(String s : mentorUuids){
			 BusinessSectorMentor mentor = new BusinessSectorMentor();
			 
			 String mentorFirstname = req.getParameter("mentor-firstname_" + s);
			 String mentorLastname = req.getParameter("mentor-lastname_" + s);
			 String mentorPhone = req.getParameter("mentor-phone_" + s);
			 
			 if (mentorFirstname == null || mentorLastname == null || mentorPhone == null
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
        
        BusinessSectorManager manager = new BusinessSectorManager();
        manager.setFirstname(managerFirstname);
        manager.setLastname(managerLastname);
        manager.setMobilePhone(managerPhone);
        manager.setEmail(managerEmail);
        job.setManager(manager);
        job.setApproved(false);
        job.setControlled(false);
        
        job.setIsOverEighteen(req.getParameter("isOverEighteen") != null ? true : false);
        log.info("isOverEighteen: " + req.getParameter("isOverEighteen"));
        boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;
        job.setHasDriversLicense(hasDriversLicense);
        log.info("hasDriversLicense: " + hasDriversLicense);
        
        if (hasDriversLicense) {
        	Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
        	
        	if (typeId != null) {
        		DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
        		job.setDriversLicenseType(licenseType);
        	}
        }
        
        job.setFreeTextRequirements(req.getParameter("other-requirements"));
        
        job.setCreated(new Date(Calendar.getInstance().getTimeInMillis()));
		try {
			businessSectorJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu sparats. En handläggare kommer att granska annonsen innan den blir synlig för sökande.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när annonsen skulle sparas.\"}", callback, writer);
		}				
	}
}