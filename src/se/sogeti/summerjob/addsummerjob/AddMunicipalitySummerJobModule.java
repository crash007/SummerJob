package se.sogeti.summerjob.addsummerjob;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityManager;
import se.sogeti.jobapplications.beans.municipality.MunicipalityMentor;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.ContactDetailsDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
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

public class AddMunicipalitySummerJobModule extends AnnotatedRESTModule{
	
	
	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	private GeoAreaDAO geoAreaDAO;
	private ContactDetailsDAO<MunicipalityMentor> municipalityMentorDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, MunicipalityJobArea.class, hierarchyDaoFactory);
		periodDAO = new PeriodDAO(dataSource, Period.class,hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
		geoAreaDAO = new GeoAreaDAO(dataSource, GeoArea.class, hierarchyDaoFactory);
		municipalityMentorDAO = new ContactDetailsDAO<MunicipalityMentor>(dataSource, MunicipalityMentor.class, hierarchyDaoFactory);
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
		Element jobForm = doc.createElement("MunicipalityJobForm");
		doc.getFirstChild().appendChild(jobForm);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		MunicipalityJob job = null;
		if (jobId != null && user.isAdmin()) {
			job = municipalityJobDAO.getById(jobId);
			XMLUtils.append(doc, jobForm, job);
		} 
		
		Element areasElement = doc.createElement("Areas");
		List<MunicipalityJobArea> areas = areaDAO.getAll();
		for (MunicipalityJobArea area : areas) {
			Element jobArea = doc.createElement("MunicipalityJobArea");
			XMLUtils.appendNewElement(doc, jobArea, "id", area.getId());
			XMLUtils.appendNewElement(doc, jobArea, "name", area.getName());
			XMLUtils.appendNewElement(doc, jobArea, "description", area.getDescription());
			XMLUtils.appendNewElement(doc, jobArea, "canBeChosenInApplication", area.isCanBeChosenInApplication());
			
			if (job != null && job.getArea() != null) {
				XMLUtils.appendNewElement(doc, jobArea, "selected", 
						job.getArea().getId().intValue() == area.getId().intValue());
			}
			
			areasElement.appendChild(jobArea);
		}
		
		jobForm.appendChild(areasElement);
		
		Element geoAreaElement = doc.createElement("GeoAreas");
		List<GeoArea> geoAreas = geoAreaDAO.getAll();
		for (GeoArea geoArea : geoAreas) {
			Element geoElement = doc.createElement("GeoArea");
			XMLUtils.appendNewElement(doc, geoElement, "id", geoArea.getId());
			XMLUtils.appendNewElement(doc, geoElement, "name", geoArea.getName());
			XMLUtils.appendNewElement(doc, geoElement, "description", geoArea.getDescription());

			if (job != null && job.getGeoArea() != null) {
				XMLUtils.appendNewElement(doc, geoElement, "selected", 
						job.getGeoArea().getId().intValue() == geoArea.getId().intValue());
			}
			
			geoAreaElement.appendChild(geoElement);
		}
		
		jobForm.appendChild(geoAreaElement);
		
		List<Period> periods = periodDAO.getAll();
		Element periodsElement = doc.createElement("Periods");
		for (Period p : periods) {
			Element periodElement = doc.createElement("Period");
			XMLUtils.appendNewElement(doc, periodElement, "id", p.getId());
			XMLUtils.appendNewElement(doc, periodElement, "name", p.getName());
			XMLUtils.appendNewElement(doc, periodElement, "startDate", p.getStartDate());
			XMLUtils.appendNewElement(doc, periodElement, "endDate", p.getEndDate());
			
			if (job != null && job.getPeriod() != null) {
				XMLUtils.appendNewElement(doc, periodElement, "selected", 
						job.getPeriod().getId().intValue() == p.getId().intValue());
			}
			
			periodsElement.appendChild(periodElement);
		}
		
		jobForm.appendChild(periodsElement);
		
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

	@RESTMethod(alias="add/municipalitysummerjob.json", method="post")
	public void addSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		log.info("POST");
		
//		GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
		
		PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		MunicipalityJob job = jobId != null ? municipalityJobDAO.getById(jobId) : new MunicipalityJob();
		
		String organization = req.getParameter("organisation");
		if (organization == null || organization.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Organisation saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setOrganization(organization);

		String administration = req.getParameter("administration");
		if (administration == null || administration.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Förvaltning saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setAdministration(administration);			//Förvaltning
		
		String location = req.getParameter("location");
		if (location == null || location.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Plats saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setLocation(location);
		
		String city = req.getParameter("postalarea");
		String streetAddress = req.getParameter("street");
		String zipCode = req.getParameter("postalcode");
		if (city == null || streetAddress == null || zipCode == null
				|| city.isEmpty() || streetAddress.isEmpty() || zipCode.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Adressuppgifter till arbetsplatsen saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setStreetAddress(streetAddress);
		job.setZipCode(zipCode);
		job.setCity(city);
		job.setDepartment(req.getParameter("department"));			//Avdelning
		
		MunicipalityJobArea area = null;
		Integer areaId = NumberUtils.toInt(req.getParameter("area"));
		if(areaId == null) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Verksamhetsområde saknas i annonsen.\"}", callback, writer);
			return;
		}
		area = areaDAO.getAreaById(areaId);
		job.setArea(area);
		
		if (jobId != null) {
			job.setUpdated(new java.sql.Date(new Date().getTime()));
		} else {
			job.setCreated(new java.sql.Date(new Date().getTime()));
		}
		
		if(req.getParameter("isOverEighteen")!=null){
			job.setIsOverEighteen(true);
		}else{
			job.setIsOverEighteen(false);
		}
		
		boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;
		job.setHasDriversLicense(hasDriversLicense);
		
		if (hasDriversLicense) {
			Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
        	
        	if (typeId == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste en körkortstyp väljas.\"}", callback, writer);
            	return;
        	}
        	DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
        	job.setDriversLicenseType(licenseType);
		} else {
			job.setDriversLicenseType(null);
		}
		
		String managerFirstname = req.getParameter("manager-firstname");
		String managerLastname = req.getParameter("manager-lastname");
		String managerPhone = req.getParameter("manager-phone");
		
		if (managerFirstname == null || managerLastname == null || managerPhone == null
				|| managerFirstname.isEmpty() || managerLastname.isEmpty() || managerPhone.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kontaktupgifter till den ansvarige på arbetsplatsen saknas i annonsen.\"}", callback, writer);
			return;
		}
		MunicipalityManager manager = job.getManager() != null ? job.getManager() : new MunicipalityManager();
		manager.setFirstname(managerFirstname);
		manager.setLastname(managerLastname);
		manager.setMobilePhone(managerPhone);
		manager.setEmail(req.getParameter("manager-email"));
		job.setManager(manager);
		
		List<MunicipalityMentor> mentors = new ArrayList<MunicipalityMentor>();
        List<String> mentorUuids = FormUtils.getMentorUuids(req.getParameterNames());
		for(String s : mentorUuids){
			 MunicipalityMentor mentor = new MunicipalityMentor();
			 Integer mentorId = NumberUtils.toInt(req.getParameter("mentor-id-" + s));
			 
			 String mentorFirstname = req.getParameter("mentor-firstname_" + s);
			 String mentorLastname = req.getParameter("mentor-lastname_" + s);
			 String mentorPhone = req.getParameter("mentor-phone_" + s);
			 
			 if (mentorId != null) {
				 mentor.setId(mentorId);
				 
				 if ((mentorFirstname == null && mentorLastname == null && mentorPhone == null)
						 || (mentorFirstname.isEmpty() && mentorLastname.isEmpty() && mentorPhone.isEmpty())) {
					 municipalityMentorDAO.removeById(mentorId);
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
		
		if (jobId == null) {
			job.setApproved(false);
			job.setControlled(false);
		} else {
			job.setInitiatedByUser(user.getUsername());
		}
		
		String workDescription = req.getParameter("work-description");
		if (workDescription == null || workDescription.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Arbetsbeskrivning saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setWorkDescription(workDescription);
		
		String workTitle = req.getParameter("work-title");
		if (workTitle == null || workTitle.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Rubrik saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setWorkTitle(workTitle);
		
		GeoArea geoArea = geoAreaDAO.getAreaById(NumberUtils.toInt(req.getParameter("geoArea")));
		if (geoArea == null) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Det angivna geografiska området hittades inte.\"}", callback, writer);
			return;
		}
		job.setGeoArea(geoArea);
		
		List<Period> periods = periodDAO.getAll();
		try {
			if (jobId != null) {
				String parameterName = FormUtils.getParameterNameThatContains("_numberOfWorkersNeeded", req.getParameterNames());
				Integer numberOfWorkers = NumberUtils.toInt(req.getParameter(parameterName));
				if (numberOfWorkers == null) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Antal lediga platser saknas för perioden.\"}", callback, writer);
					return;
				}
				job.setNumberOfWorkersNeeded(numberOfWorkers);
				municipalityJobDAO.save(job);
			} else {
				for(Period p:periods){
					if(req.getParameter("period_"+p.getId())!=null){
						job.setPeriod(p);
						
						Integer numberOfWorkers = NumberUtils.toInt(req.getParameter(p.getName()+"_numberOfWorkersNeeded"));
						if (numberOfWorkers == null) {
							JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Antal lediga platser saknas för period "+p.getName()+".\"}", callback, writer);
							return;
						}
						job.setNumberOfWorkersNeeded(numberOfWorkers);
						
						log.info("saving form for period: "+p.getName());
						
						municipalityJobDAO.save(job);
						
						job.setId(null);
						job.getManager().setId(null);
						
						for(MunicipalityMentor m:job.getMentors()){
							m.setId(null);
						}
					}
				}
			}
			
		} catch (SQLException e) {
			log.error("SQL exception", e);
			if (jobId != null) {
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
			} else {
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när annonsen skulle sparas.\"}", callback, writer);
			}
			return;
		}
//		JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu sparats. En handläggare kommer att granska annonsen innan den blir synlig för sökande.\"}", callback, writer);
		if (jobId != null) {
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"newText\":\"Visa översikt av kommunala sommarjobb\", \"newUrl\":\"list-summerjobs?showMunicipalityJobs=true\", \"backText\":\"Fortsätt redigera\", \"backUrl\":\"manage-municipality-job?jobId=" + jobId + "\", \"header\":\"Ändringarna har nu sparats.\", \"message\":\"Annonsen är nu tillgänglig för att matcha kandidater.\"}", callback, writer);
		} else {
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"backUrl\":\"\", \"newUrl\":\"add-municipality-job\", \"header\":\"Annonsen har nu sparats.\", \"message\":\"En handläggare kommer att granska annonsen innan den blir synlig för sökande.\"}", callback, writer);
		}
	}
}
