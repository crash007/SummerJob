package se.sogeti.summerjob.overview;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
	

	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private JobApplicationDAO<MunicipalityJobApplication> municipalityJobApplicationDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
		municipalityJobApplicationDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, daoFactory);

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
		
		Element newMunicipalityJobsElement = doc.createElement("NewMunicipalityJobs");
		List<MunicipalityJob> newJobs = municipalityJobDAO.getAllUncontrolled();
		XMLUtils.append(doc,newMunicipalityJobsElement, newJobs);
		
		overView.appendChild(newMunicipalityJobsElement);
		
		
		Element approvedMunicipalityApplicationsElem = doc.createElement("approvedMunicipalityApplications");
		List<MunicipalityJobApplication> approvedMunicipalityApplications = municipalityJobApplicationDAO.getAllApproved();	
		createApplicationElementList(doc, approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
		XMLUtils.append(doc,approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
		overView.appendChild(approvedMunicipalityApplicationsElem);
		
		Element unapprovedMunicipalityApplicationsElem = doc.createElement("unapprovedMunicipalityApplications");
		List<MunicipalityJobApplication> unapprovedMunicipalityApplications = municipalityJobApplicationDAO.getAllUnapproved();	
		createApplicationElementList(doc, unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
		XMLUtils.append(doc,unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
		overView.appendChild(unapprovedMunicipalityApplicationsElem);
		
		
		return new SimpleForegroundModuleResponse(doc);
	}

	private void createApplicationElementList(Document doc, Element MunicipalityApplicationsElem,
			List<MunicipalityJobApplication> municipalityApplications) {
		for(MunicipalityJobApplication app: municipalityApplications){
			Element municipalityApplication = doc.createElement("MunicipalityApplication");
			XMLUtils.appendNewElement(doc, municipalityApplication, "firstname", app.getFirstname());
			XMLUtils.appendNewElement(doc, municipalityApplication, "lastname", app.getLastname());
			
			XMLUtils.appendNewElement(doc, municipalityApplication, "preferedArea1", app.getPreferedArea1().getName());			
			XMLUtils.appendNewElement(doc, municipalityApplication, "preferedGeoArea1", app.getPreferedGeoArea1().getName());

			XMLUtils.appendNewElement(doc, municipalityApplication, "schoolName", app.getSchoolName());
			XMLUtils.appendNewElement(doc, municipalityApplication, "schoolType", app.getSchoolType());
			XMLUtils.appendNewElement(doc, municipalityApplication, "skvCity", app.getSkvCity());
			
			XMLUtils.appendNewElement(doc, municipalityApplication, "created", app.getCreated());
			
			if(app.getDriversLicenseType()!=null){
				XMLUtils.appendNewElement(doc, municipalityApplication, "driversLicenseType", app.getDriversLicenseType().getName());
			}
			MunicipalityApplicationsElem.appendChild(municipalityApplication);
		}
	}
	
	
	
}

