package se.sogeti.summerjob.addsummerjob;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityManager;
import se.sogeti.jobapplications.beans.municipality.MunicipalityMentor;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.sogeti.summerjob.FormUtils;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddMunicipalitySummerJobModule extends AnnotatedRESTModule{
	
	
	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, MunicipalityJobArea.class, hierarchyDaoFactory);
		periodDAO = new PeriodDAO(dataSource, Period.class,hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		if(req.getMethod().equals("POST")){
			log.info("POST");
			MunicipalityJob job = new MunicipalityJob();
			
			job.setOrganization(req.getParameter("organisation"));
			job.setAdministration(req.getParameter("administration"));			//Förvaltning
			job.setLocation(req.getParameter("location"));
			
			
			job.setCity(req.getParameter("city"));
			job.setStreetAddress(req.getParameter("street"));
			job.setZipCode(req.getParameter("postalcode"));
			job.setCity(req.getParameter("postalarea"));
			job.setDepartment(req.getParameter("department"));			//Avdelning
			
			
			
			MunicipalityJobArea area = null;
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
			
			MunicipalityManager manager = new MunicipalityManager();
			manager.setFirstname(req.getParameter("manager-firstname"));
			manager.setLastname(req.getParameter("manager-lastname"));
			manager.setEmail(req.getParameter("manager-email"));
			manager.setMobilePhone(req.getParameter("manager-phone"));
			job.setManager(manager);
			
			List<MunicipalityMentor> mentors = new ArrayList<MunicipalityMentor>();
			
			//Find mentor uuids
			
			 List<String> mentorUuids =FormUtils.getMentorUuids(req.getParameterNames());
			 for(String s:mentorUuids){
				 MunicipalityMentor mentor = new MunicipalityMentor();
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
					log.info("saving form for period: "+p.getName());
					
					municipalityJobDAO.add(job);
					job.setId(null);
					job.getManager().setId(null);
					
					for(MunicipalityMentor m:job.getMentors()){
						m.setId(null);
					}
				}
			}
		}
		
		
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
		
		return new SimpleForegroundModuleResponse(doc);
		
	}

	
	
}
