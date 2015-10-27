package se.sogeti.summerjob.addsummerjob;


import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.Area;
import se.sogeti.jobapplications.beans.Manager;
import se.sogeti.jobapplications.beans.Mentor;
import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.sogeti.jobapplications.beans.Workplace;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddMunicipalitySummerJobModule extends AnnotatedRESTModule{
	
	
	private MunicipalityJobDAO municipalityJobDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		municipalityJobDAO = new MunicipalityJobDAO(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, Area.class, hierarchyDaoFactory);
		periodDAO = new PeriodDAO(dataSource, Period.class,hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		if(req.getMethod().equals("POST")){
			log.info("POST");
			MunicipalityJob job = new MunicipalityJob();
			Workplace place = new Workplace();
			place.setOrganization(req.getParameter("organisation"));
			place.setAdministration(req.getParameter("administration"));			//Förvaltning
			place.setLocation(req.getParameter("location"));
			
			
			place.setCity(req.getParameter("city"));
			place.setStreetAddress(req.getParameter("street"));
			place.setZipCode(req.getParameter("postalcode"));
			place.setCity(req.getParameter("postalarea"));
			place.setDepartment(req.getParameter("department"));			//Avdelning
			
			job.setWorkplace(place);
			
			Area area = null;
			Integer areaId = NumberUtils.toInt((String) req.getParameter("area"));
			log.info("Selected area id: "+areaId);
			
			if(areaId!=null){
				area = areaDAO.getAreaById(areaId);
				log.info(area);
			}
			
			job.setArea(area);
			job.setCreated(new java.sql.Date(new Date().getTime()));
			
			if(req.getParameter("isOverEighteen")!=null){
				job.setIsOverEighteen(true);
			}else{
				job.setIsOverEighteen(false);
			}
			
			if(req.getParameter("hasDriversLicense")!=null){
				job.setHasDriversLicense(true);
			}else{
				job.setHasDriversLicense(false);
			}
			
			Manager manager = new Manager();
			manager.setFirstname(req.getParameter("manager-firstname"));
			manager.setLastname(req.getParameter("manager-lastname"));
			manager.setEmail(req.getParameter("manager-email"));
			manager.setMobilePhone(req.getParameter("manager-phone"));
			job.setManager(manager);
			
			List<Mentor> mentors = new ArrayList<Mentor>();
			
			//Find mentor uuids
			
			 List<String> mentorUuids =getMentorUuids(req.getParameterNames());
			 for(String s:mentorUuids){
				 Mentor mentor = new Mentor();
				 mentor.setFirstname(req.getParameter("mentor-firstname_"+s));
				 mentor.setLastname(req.getParameter("mentor-lastname_"+s));
				 mentor.setEmail(req.getParameter("mentor-email_"+s));
				 mentor.setMobilePhone(req.getParameter("mentor-phone_"+s));
				 mentors.add(mentor);
			 }
			 
			job.setMentors(mentors);
			job.setNumberOfWorkersNeeded(NumberUtils.toInt(req.getParameter("numberOfWorkersNeeded")));
			job.setApprovedWorkplace(false);
			job.setWorkDescription(req.getParameter("work-description"));
			job.setWorkTitle(req.getParameter("work-title"));
			
			List<Period> periods = periodDAO.getAll();
			
			for(Period p:periods){
				if(req.getParameter("period_"+p.getId())!=null){
					job.setPeriod(p);
					municipalityJobDAO.save(job);
				}
			}
			//job.setFreeTextRequirements(freeTextRequirements);
			//job.setArea(area);
			//job.setPeriod(period);
			
		}
		
		MunicipalityJob job = new MunicipalityJob();
		job.setCreated(new java.sql.Date(new Date().getTime()));
		job.setApprovedWorkplace(false);
		job.setNumberOfWorkersNeeded(10);
		job.setWorkDescription("Det snöar mycket på sommaren därför behöver vi snöskottare.");
		job.setRequirementsFreeText("Var högerhänt");
		job.setWorkTitle("Snöskottare");
		
		List<Mentor> mentors = new ArrayList<Mentor>();
		Mentor mentor = new Mentor();
		mentor.setFirstname("Pelle");
		mentor.setLastname("Plutt");
		mentor.setMobilePhone("2345345345");
		mentors.add(mentor);
		job.setMentors(mentors);
		
		Period period = new Period();
		period.setName("Period1");
		period.setStartDate(new java.sql.Date(new Date().getTime()));
		period.setEndDate(new java.sql.Date(new Date().getTime()));
		job.setPeriod(period);
		
		
		Area area = areaDAO.getAreaById(1);
		job.setArea(area);
		
		
		
		Workplace workplace = new Workplace();
		workplace.setAdministration("Avdelning x");
		workplace.setCity("Sundsvall");
		workplace.setOrganization("Sundsvalls kommun");
		workplace.setLocation("Kultur och fritid");
		job.setWorkplace(workplace);
		
		Manager manager = new Manager();
		manager.setFirstname("Mr Boss");
		manager.setLastname("Boss lastname");
		manager.setMobilePhone("342345345465");
		
		job.setManager(manager);		
		
		
		//municipalityJobDAO.save(job);
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		Element jobForm = doc.createElement("MunicipalityJobForm");
		doc.getFirstChild().appendChild(jobForm);
		
		Element areasElement = doc.createElement("Areas");
		List<Area> areas = areaDAO.getAll();
		
		XMLUtils.append(doc, areasElement,areas);
		jobForm.appendChild(areasElement);	
		
		List<Period> periods = periodDAO.getAll();
		Element periodsElement =doc.createElement("Periods");
		XMLUtils.append(doc, periodsElement, periods);
		jobForm.appendChild(periodsElement);
		
		return new SimpleForegroundModuleResponse(doc);
		
	}

	private List<String> getMentorUuids(Enumeration<String> paramNames) {
		List<String> result = new ArrayList<String>();
		while(paramNames.hasMoreElements()){
			String s = paramNames.nextElement();
			if(s.startsWith("mentor-firstname")){
				log.info(s);
				String uuid = s.split("_")[1];
				
				result.add(uuid);
			}
		}
		return result;
	}
	
}
