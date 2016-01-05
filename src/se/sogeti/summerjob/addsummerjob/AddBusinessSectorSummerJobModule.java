package se.sogeti.summerjob.addsummerjob;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
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
import se.sogeti.summerjob.JsonResponse;
import se.sogeti.summerjob.managesummerjob.ManageBusinessSectorJobHandler;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddBusinessSectorSummerJobModule extends AnnotatedRESTModule implements AddBusinessJobHandler{
	
	@InstanceManagerDependency(required = true)
	private ManageBusinessSectorJobHandler manageBusinessJobHandler;
	
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
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		BusinessSectorJob job = null;
		if (jobId != null && user.isAdmin()) {
			job = businessSectorJobDAO.getById(jobId);
			XMLUtils.append(doc, jobForm, job);
			XMLUtils.appendNewElement(doc, jobForm, "manageJobURL", req.getContextPath()+manageBusinessJobHandler.getUrl()+"?jobId="+job.getId());
		} 
		
		List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
		
		XMLUtils.append(doc, jobForm, "DriversLicenseTypes",driverslicenseTypes);
		
		
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
        String lastApplicationDay = req.getParameter("lastApplicationDay");
        
        if (startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty() || lastApplicationDay == null || lastApplicationDay.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Datumfälten kan inte lämnas tomma.\"}", callback, writer);
			return;
        }
        
        if (startDate.length() > 10) {
        	startDate = startDate.substring(0, 10);
        }
        
        if (endDate.length() > 10) {
        	endDate = endDate.substring(0, 10);
        }
        
        if (lastApplicationDay.length() > 10) {
        	lastApplicationDay = lastApplicationDay.substring(0, 10);
        }
        
        Date jobStartDate, jobEndDate, jobLastApplicationDay;
        
        try {
        	jobStartDate = Date.valueOf(startDate);
        	jobEndDate = Date.valueOf(endDate);
        	jobLastApplicationDay = Date.valueOf(lastApplicationDay);
        } catch (IllegalArgumentException e) {
        	log.error(e);
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Datumfälten innehåller datum som inte är korrekt angivna\"}", callback, writer);
			return;
        }
        
        job.setStartDate(jobStartDate);
        job.setEndDate(jobEndDate);
        job.setLastApplicationDay(jobLastApplicationDay);
        
        String mentorFirstname = req.getParameter("mentor-firstname");
        String mentorLastname = req.getParameter("mentor-lastname");
        String mentorPhone = req.getParameter("mentor-phone");
        String mentorEmail = req.getParameter("mentor-email");
        
        if (!StringUtils.isEmpty(mentorFirstname) && !StringUtils.isEmpty(mentorLastname) 
        		&& !StringUtils.isEmpty(mentorPhone)) {
        	BusinessSectorMentor mentor = job.getMentor() != null ? job.getMentor() : new BusinessSectorMentor();
        	mentor.setFirstname(mentorFirstname);
        	mentor.setLastname(mentorLastname);
        	mentor.setMobilePhone(mentorPhone);
        	mentor.setEmail(mentorEmail);
        	job.setMentor(mentor);
        } else {
        	job.setMentor(null);
        }
        
		String corporateNumber = req.getParameter("corporate-number");
		String company = req.getParameter("company");
        String streetAddress = req.getParameter("street");
        String zipCode = req.getParameter("postalcode");
        String city = req.getParameter("postalarea");
        
        if (corporateNumber == null || corporateNumber.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fältet för organisationsnummer kan inte lämnas tomt.\"}", callback, writer);
        	return;
        }
        
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
        job.setCorporateNumber(corporateNumber);
        
        String managerFirstname = req.getParameter("manager-firstname");
        String managerLastname = req.getParameter("manager-lastname");
        String managerPhone = req.getParameter("manager-phone");
        String managerEmail = req.getParameter("manager-email");
        
        if (managerFirstname == null || managerLastname == null || managerPhone == null
        		|| managerFirstname.isEmpty() || managerLastname.isEmpty() || managerPhone.isEmpty()
        		|| managerEmail == null || managerEmail.isEmpty()) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Namn-, telefonnummer- och e-postfälten kan inte lämnas tomma för den ansvarige på arbetsplatsen.\"}", callback, writer);
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
        
        job.setMustBeOverEighteen(req.getParameter("mustBeOverEighteen") != null ? true : false);
        log.debug("MustBeOverEighteen: " + req.getParameter("mustBeOverEighteen"));
    	Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
    	
    	if (typeId == null) {
    		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste en körkortstyp väljas.\"}", callback, writer);
        	return;
    	}
    	DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
    	job.setDriversLicenseType(licenseType);
    
        job.setFreeTextRequirements(req.getParameter("other-requirements"));
		job.setFreeText(req.getParameter("freetext"));
        
        if (jobId != null) {
        	job.setUpdated(new Date(Calendar.getInstance().getTimeInMillis()));
        } else {
        	job.setCreated(new Date(Calendar.getInstance().getTimeInMillis()));
        }
        
        boolean inChargeOfInterviews = req.getParameter("inChargeOfInterviews") != null ? true : false;
        job.setInChargeOfInterviews(inChargeOfInterviews);
        
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

	@Override
	public String getUrl() {
		return this.getFullAlias();
	}
	
	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface,
			DataSource dataSource) throws Exception {
		
		super.init(moduleDescriptor, sectionInterface, dataSource);
		
		if(!systemInterface.getInstanceHandler().addInstance(AddBusinessJobHandler.class, this)){
			throw new RuntimeException("Unable to register module in global instance handler using key " +AddBusinessJobHandler.class.getSimpleName() + ", another instance is already registered using this key.");
		}
	}

	@Override
	public void unload() throws Exception {
		
		if(this.equals(systemInterface.getInstanceHandler().getInstance(AddBusinessJobHandler.class))){
			log.info("Unloading AddBusinessJobHandler from instanceHandler.");
			systemInterface.getInstanceHandler().removeInstance(AddBusinessJobHandler.class);
		}	
		
		super.unload();
	}
}
