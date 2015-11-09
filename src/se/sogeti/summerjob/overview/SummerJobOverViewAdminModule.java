package se.sogeti.summerjob.overview;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.validation.PositiveStringIntegerValidator;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SummerJobOverViewAdminModule extends AnnotatedForegroundModule{
	

	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private JobApplicationDAO<MunicipalityJobApplication> municipalityJobApplicationDAO;
	private JobDAO<BusinessSectorJob> businessJobDAO;
	private BusinessSectorJobApplicationDAO businessJobApplicationDAO;
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Rows", description="Number of rows to display in each panel",required=true,formatValidator=PositiveStringIntegerValidator.class)
	private Integer rows=20;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
		municipalityJobApplicationDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, daoFactory);
		businessJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, daoFactory);
		businessJobApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, daoFactory);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		
		boolean showMunicipality=true;
		boolean showBusiness=true;
		
		if(req.getParameter("showMunicipality") !=null && req.getParameter("showMunicipality").equalsIgnoreCase("true")){
			showBusiness=false;
		}
		
		if(req.getParameter("showBusiness") !=null && req.getParameter("showBusiness").equalsIgnoreCase("true")){
			showMunicipality=false;
		}
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		//Element overView = doc.createElement("OverView");
		//doc.getFirstChild().appendChild(overView);
		
		if(showMunicipality){
			Element municipality = doc.createElement("Municipality");
			doc.getFirstChild().appendChild(municipality);
			Element newMunicipalityJobsElement = doc.createElement("NewMunicipalityJobs");
			List<MunicipalityJob> newJobs = municipalityJobDAO.getLatestUncontrolled(rows);
			XMLUtils.append(doc,newMunicipalityJobsElement, newJobs);		
			municipality.appendChild(newMunicipalityJobsElement);
			
			Element approvedMunicipalityJobsElement = doc.createElement("approvedMunicipalityJobs");
			List<MunicipalityJob> approvedJobs = municipalityJobDAO.getLatestApproved(rows);
			XMLUtils.append(doc,approvedMunicipalityJobsElement, approvedJobs);		
			municipality.appendChild(approvedMunicipalityJobsElement);
			
			
			Element approvedMunicipalityApplicationsElem = doc.createElement("approvedMunicipalityApplications");
			List<MunicipalityJobApplication> approvedMunicipalityApplications = municipalityJobApplicationDAO.getLatestApproved(rows);	
			createApplicationElementList(doc, approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
			XMLUtils.append(doc,approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
			municipality.appendChild(approvedMunicipalityApplicationsElem);
			
			Element unapprovedMunicipalityApplicationsElem = doc.createElement("unapprovedMunicipalityApplications");
			List<MunicipalityJobApplication> unapprovedMunicipalityApplications = municipalityJobApplicationDAO.getLatestUnapproved(rows);	
			createApplicationElementList(doc, unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
			XMLUtils.append(doc,unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
			municipality.appendChild(unapprovedMunicipalityApplicationsElem);
		}
		
		if(showBusiness){
			Element business = doc.createElement("Business");
			doc.getFirstChild().appendChild(business);
			
			//New Business jobs
			Element newBusinessyJobsElement = doc.createElement("NewBusinessJobs");
			List<BusinessSectorJob> newBusinessJobs = businessJobDAO.getLatestUncontrolled(rows);
			XMLUtils.append(doc,newBusinessyJobsElement, newBusinessJobs);		
			business.appendChild(newBusinessyJobsElement);
			
			Element approvedBusinessJobsElement = doc.createElement("ApprovedBusinessJobs");
			List<BusinessSectorJob> approvedBusinessJobs = businessJobDAO.getLatestApproved(rows);		
			XMLUtils.append(doc,approvedBusinessJobsElement, approvedBusinessJobs);		
			business.appendChild(approvedBusinessJobsElement);
			
			//Business applications
			Element approvedBusinessApplicationsElem = doc.createElement("ApprovedBusinessApplications");
			List<BusinessSectorJobApplication> approvedBusinessApplications = businessJobApplicationDAO.getApprovedWithJob(rows);
			XMLUtils.append(doc,approvedBusinessApplicationsElem, approvedBusinessApplications);		
			business.appendChild(approvedBusinessApplicationsElem);
			
			Element unapprovedBusinessApplicationsElem = doc.createElement("UnapprovedBusinessApplications");
			List<BusinessSectorJobApplication> unapprovedBusinessApplications = businessJobApplicationDAO.getUnapprovedWithJob(rows);
			XMLUtils.append(doc,unapprovedBusinessApplicationsElem, unapprovedBusinessApplications);		
			business.appendChild(unapprovedBusinessApplicationsElem);
		}
		return new SimpleForegroundModuleResponse(doc);
	}

	private void createApplicationElementList(Document doc, Element MunicipalityApplicationsElem,
			List<MunicipalityJobApplication> municipalityApplications) {
				
			if(municipalityApplications!=null){
				for(MunicipalityJobApplication app: municipalityApplications){
					Element municipalityApplication = doc.createElement("MunicipalityApplication");
					XMLUtils.appendNewElement(doc, municipalityApplication, "firstname", app.getFirstname());
					XMLUtils.appendNewElement(doc, municipalityApplication, "lastname", app.getLastname());
					
					if(app.getPreferedArea1()!=null){
						XMLUtils.appendNewElement(doc, municipalityApplication, "preferedArea1", app.getPreferedArea1().getName());			
					}
					
					if(app.getPreferedGeoArea1()!=null){
						XMLUtils.appendNewElement(doc, municipalityApplication, "preferedGeoArea1", app.getPreferedGeoArea1().getName());
					}
					
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
}

