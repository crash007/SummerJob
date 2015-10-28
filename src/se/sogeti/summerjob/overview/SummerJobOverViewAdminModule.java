package se.sogeti.summerjob.overview;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SummerJobOverViewAdminModule extends AnnotatedForegroundModule{
	
	private AreaDAO areaDAO;
	//private ManagerDAO managerDAO;
	//private MentorDAO mentorDAO;
	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private JobApplicationDAO municipalityJobApplicationDAO;
	//private PersonDAO personDAO;
//	private PreferedAreaDAO preferedAreaDAO;
	
	private PeriodDAO periodDAO;
	
	
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
	//	this.daoFactory = new FlowEngineDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);	
		areaDAO = new AreaDAO(dataSource, MunicipalityJobArea.class, daoFactory);
		//managerDAO = new ManagerDAO(dataSource, Manager.class, daoFactory);
	//	mentorDAO = new MentorDAO(dataSource, Mentor.class, daoFactory);
		municipalityJobDAO = new JobDAO(dataSource, MunicipalityJob.class, daoFactory);
		municipalityJobApplicationDAO = new JobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
		//personDAO = new PersonDAO(dataSource, Person.class, daoFactory);
//		preferedAreaDAO = new PreferedAreaDAO(dataSource, PreferedArea.class, daoFactory);
		
		periodDAO = new PeriodDAO(dataSource, Period.class, daoFactory);
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
		Element overView = doc.createElement("OverView");
		doc.getFirstChild().appendChild(overView);
		
		Element newJobs = doc.createElement("NewJobs");
		Element newApplications = doc.createElement("NewApplications");
		
		overView.appendChild(newJobs);
		overView.appendChild(newApplications);
		
		
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="add/summerjob.json", method="post")
	public void addSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
	
	}
}
