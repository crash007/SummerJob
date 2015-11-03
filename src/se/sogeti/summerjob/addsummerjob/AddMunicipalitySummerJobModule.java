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
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.JsonResponse;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
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

public class AddMunicipalitySummerJobModule extends AnnotatedRESTModule{
	
	
	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	private GeoAreaDAO geoAreaDAO;
	
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
		
		Element areasElement = doc.createElement("Areas");
		List<MunicipalityJobArea> areas = areaDAO.getAll();
		
		XMLUtils.append(doc, areasElement,areas);
		jobForm.appendChild(areasElement);	
		
		List<Period> periods = periodDAO.getAll();
		Element periodsElement =doc.createElement("Periods");
		XMLUtils.append(doc, periodsElement, periods);
		jobForm.appendChild(periodsElement);
		
		Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
		List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
		jobForm.appendChild(driversLicenseElement);
		XMLUtils.append(doc, driversLicenseElement, driverslicenseTypes);
		
		Element geoAreaElement = doc.createElement("GeoAreas");
		List<GeoArea> geoAreas = geoAreaDAO.getAll();
		jobForm.appendChild(geoAreaElement);
		XMLUtils.append(doc, geoAreaElement, geoAreas);
		
		return new SimpleForegroundModuleResponse(doc);
		
	}

	@RESTMethod(alias="add/municipalitysummerjob.json", method="post")
	public void addSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		log.info("POST");
		PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        JsonResponse.initJsonResponse(res, writer, callback);
        
		MunicipalityJob job = new MunicipalityJob();
		
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
		
		job.setCreated(new java.sql.Date(new Date().getTime()));
		
		if(req.getParameter("isOverEighteen")!=null){
			job.setIsOverEighteen(true);
		}else{
			job.setIsOverEighteen(false);
		}
		
		boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;
		job.setHasDriversLicense(hasDriversLicense);
		
		if (hasDriversLicense) {
			Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
        	
        	if (typeId != null) {
        		DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
        		job.setDriversLicenseType(licenseType);
        	}
		}
		
		String managerFirstname = req.getParameter("manager-firstname");
		String managerLastname = req.getParameter("manager-lastname");
		String managerPhone = req.getParameter("manager-phone");
		
		if (managerFirstname == null || managerLastname == null || managerPhone == null
				|| managerFirstname.isEmpty() || managerLastname.isEmpty() || managerPhone.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kontaktupgifter till den ansvarige på arbetsplatsen saknas i annonsen.\"}", callback, writer);
			return;
		}
		MunicipalityManager manager = new MunicipalityManager();
		manager.setFirstname(managerFirstname);
		manager.setLastname(managerLastname);
		manager.setMobilePhone(managerPhone);
		manager.setEmail(req.getParameter("manager-email"));
		job.setManager(manager);
		
		List<MunicipalityMentor> mentors = new ArrayList<MunicipalityMentor>();
		//Find mentor uuids
		 List<String> mentorUuids = FormUtils.getMentorUuids(req.getParameterNames());
		 for(String s:mentorUuids){
			 MunicipalityMentor mentor = new MunicipalityMentor();
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
		
		Integer numberOfWorkers = NumberUtils.toInt(req.getParameter("numberOfWorkersNeeded"));
		if (numberOfWorkers == null) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Antal lediga platser saknas i annonsen.\"}", callback, writer);
			return;
		}
		job.setNumberOfWorkersNeeded(numberOfWorkers);
		
		job.setApproved(false);
		job.setControlled(false);
		
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
		
//		Integer geoAreaId = NumberUtils.toInt(req.getParameter("geoArea"));
		GeoArea geoArea = geoAreaDAO.getAreaById(NumberUtils.toInt(req.getParameter("geoArea")));
		if (geoArea == null) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Det angivna geografiska området hittades inte.\"}", callback, writer);
			return;
		}
		job.setGeoArea(geoArea);
		
		List<Period> periods = periodDAO.getAll();
		try {
			for(Period p:periods){
				if(req.getParameter("period_"+p.getId())!=null){
					job.setPeriod(p);
					log.info("saving form for period: "+p.getName());
					
					municipalityJobDAO.add(job);
					job.setId(null);
					job.getManager().setId(null);
					
					for(MunicipalityMentor m:job.getMentors()){
						m.setId(null);
					}
				}
			}
			
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när annonsen skulle sparas.\"}", callback, writer);
			return;
		}
		JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu sparats. En handläggare kommer att granska annonsen innan den blir synlig för sökande.\"}", callback, writer);
	}
}
