package se.sogeti.summerjob.addsummerjob;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationRequirement;
import se.sogeti.jobapplications.beans.Area;
import se.sogeti.jobapplications.beans.Manager;
import se.sogeti.jobapplications.beans.Mentor;
import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.sogeti.jobapplications.beans.Workplace;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddMunicipalitySummerJobModule extends AnnotatedRESTModule{
	
	
	private MunicipalityJobDAO municipalityJobDAO;
	private AreaDAO areaDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		municipalityJobDAO = new MunicipalityJobDAO(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, Area.class, hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
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
		
		List<ApplicationRequirement> requirements = new ArrayList<ApplicationRequirement>();
		ApplicationRequirement requirement = new ApplicationRequirement();
		requirement.setName("Hittepåkrav");
		requirement.setStatus(true);
		job.setRequirements(requirements);
		
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
		
	
		
		
				
		return new SimpleForegroundModuleResponse(doc);
		
	}
	
	

}
