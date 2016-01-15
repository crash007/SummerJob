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
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match municipality jobs url", description="relativ url för matchning av kommunala jobb",required=true)
	private String matchMunicipalityUrl="matchjobs";
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match business jobs url", description="relativ url för matchning av näringslivsjobb",required=true)
	private String matchBusinessUrl="match-business-jobs";

	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match municipality jobs url", description="relativ url för matchning av kommunala jobb",required=true)
	private String manageMunicipalityUrl="manage-municipality-job";
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match business jobs url", description="relativ url för matchning av näringslivsjobb",required=true)
	private String manageBusinessUrl="manage-businesssector-job";
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match business application url", description="relativ url för att hantera näringslivsansökan",required=true)
	private String manageBusinessApplicationUrl="manage-business-app";
	
	@ModuleSetting(allowsNull=false)
	@TextFieldSettingDescriptor(name="Match municipality application url", description="relativ url för att hantera av kommunal ansökan",required=true)
	private String manageMunicipalityApplicationUrl="manage-municipality-app";
	
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
		
		List<MunicipalityJob> newJobs = null;
		List<MunicipalityJob> approvedJobs =null;
		List<MunicipalityJobApplication> approvedMunicipalityApplications = null;	
		List<MunicipalityJobApplication> unapprovedMunicipalityApplications = null;
		
		if(showMunicipality){
			if(user.isAdmin()){
				newJobs = municipalityJobDAO.getLatestUncontrolled(rows);	
				approvedJobs = municipalityJobDAO.getLatestApproved(rows); 
				approvedMunicipalityApplications = municipalityJobApplicationDAO.getLatestApproved(rows);
				unapprovedMunicipalityApplications = municipalityJobApplicationDAO.getLatestUnapproved(rows);
			}else{
				newJobs = municipalityJobDAO.getLatestUncontrolledAddedByUsername(rows, user.getUsername());	
				approvedJobs = municipalityJobDAO.getLatestApprovedAddedByUsername(rows, user.getUsername());
				approvedMunicipalityApplications = municipalityJobApplicationDAO.getLatestApprovedAddedByUsername(rows, user.getUsername());
				unapprovedMunicipalityApplications = municipalityJobApplicationDAO.getLatestUnapprovedAddedByUsername(rows, user.getUsername());
			}
			
			Element municipality = doc.createElement("Municipality");

			//URls
			XMLUtils.appendNewElement(doc,municipality, "MatchMunicipalityJobsUrl",matchMunicipalityUrl);
			XMLUtils.appendNewElement(doc,municipality, "MatchBusinessJobsUrl",matchBusinessUrl);
			XMLUtils.appendNewElement(doc,municipality, "ManageMunicipalityJobsUrl",manageMunicipalityUrl);
			XMLUtils.appendNewElement(doc,municipality, "ManageBusinessJobsUrl",manageBusinessUrl);
			XMLUtils.appendNewElement(doc,municipality, "ManageBusinessApplicationUrl", manageBusinessApplicationUrl);
			XMLUtils.appendNewElement(doc,municipality, "ManageMunicipalityApplicationUrl", manageMunicipalityApplicationUrl);
			
			doc.getFirstChild().appendChild(municipality);
			Element newMunicipalityJobsElement = doc.createElement("NewMunicipalityJobs");
			
			XMLUtils.append(doc,newMunicipalityJobsElement, newJobs);		
			municipality.appendChild(newMunicipalityJobsElement);
			
			Element approvedMunicipalityJobsElement = doc.createElement("approvedMunicipalityJobs");
			
			XMLUtils.append(doc,approvedMunicipalityJobsElement, approvedJobs);		
			municipality.appendChild(approvedMunicipalityJobsElement);
			
			
			Element approvedMunicipalityApplicationsElem = doc.createElement("approvedMunicipalityApplications");
			
			createApplicationElementList(doc, approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
			XMLUtils.append(doc,approvedMunicipalityApplicationsElem, approvedMunicipalityApplications);		
			municipality.appendChild(approvedMunicipalityApplicationsElem);
			
			Element unapprovedMunicipalityApplicationsElem = doc.createElement("unapprovedMunicipalityApplications");
				
			createApplicationElementList(doc, unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
			XMLUtils.append(doc,unapprovedMunicipalityApplicationsElem, unapprovedMunicipalityApplications);		
			municipality.appendChild(unapprovedMunicipalityApplicationsElem);
		}
		
		if(showBusiness){
			
			List<BusinessSectorJob> newBusinessJobs = null;
			List<BusinessSectorJob> approvedBusinessJobs = null;
			List<BusinessSectorJobApplication> approvedBusinessApplications = null;
			List<BusinessSectorJobApplication> unapprovedBusinessApplications = null;
			
			if(user.isAdmin()){
				newBusinessJobs = businessJobDAO.getLatestUncontrolled(rows);
				approvedBusinessJobs = businessJobDAO.getLatestApproved(rows);
				approvedBusinessApplications = businessJobApplicationDAO.getApprovedWithJob(rows);
				unapprovedBusinessApplications = businessJobApplicationDAO.getUnapprovedWithJob(rows);
			
			}else{
				newBusinessJobs = businessJobDAO.getLatestUncontrolledAddedByUsername(rows,user.getUsername());
				approvedBusinessJobs = businessJobDAO.getLatestApprovedAddedByUsername(rows,user.getUsername());
				approvedBusinessApplications = businessJobApplicationDAO.getApprovedWithJobAddedByUsername(rows,user.getUsername());
				unapprovedBusinessApplications = businessJobApplicationDAO.getUnapprovedWithJobAddedByUsername(rows,user.getUsername());
			}
			
			Element business = doc.createElement("Business");
			doc.getFirstChild().appendChild(business);
			
			//New Business jobs
			Element newBusinessyJobsElement = doc.createElement("NewBusinessJobs");
			
			XMLUtils.append(doc,newBusinessyJobsElement, newBusinessJobs);		
			business.appendChild(newBusinessyJobsElement);
			
			Element approvedBusinessJobsElement = doc.createElement("ApprovedBusinessJobs");
					
			XMLUtils.append(doc,approvedBusinessJobsElement, approvedBusinessJobs);		
			business.appendChild(approvedBusinessJobsElement);
			
			//Business applications
			Element approvedBusinessApplicationsElem = doc.createElement("ApprovedBusinessApplications");
			
			XMLUtils.append(doc,approvedBusinessApplicationsElem, approvedBusinessApplications);		
			business.appendChild(approvedBusinessApplicationsElem);
			
			Element unapprovedBusinessApplicationsElem = doc.createElement("UnapprovedBusinessApplications");
			
			XMLUtils.append(doc,unapprovedBusinessApplicationsElem, unapprovedBusinessApplications);		
			business.appendChild(unapprovedBusinessApplicationsElem);
		}
		
		if (user != null) {
			XMLUtils.appendNewElement(doc, element, "IsAdmin", user.isAdmin());
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}

	private void createApplicationElementList(Document doc, Element MunicipalityApplicationsElem,
			List<MunicipalityJobApplication> municipalityApplications) {
				
			if(municipalityApplications!=null){
				for(MunicipalityJobApplication app: municipalityApplications){
					Element municipalityApplication = doc.createElement("MunicipalityApplication");
					XMLUtils.appendNewElement(doc, municipalityApplication, "id", app.getId());
					XMLUtils.appendNewElement(doc, municipalityApplication, "socialSecurityNumber", app.getSocialSecurityNumber());
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
					
					XMLUtils.appendNewElement(doc, municipalityApplication, "driversLicenseType", app.getDriversLicenseType().getName());
					
					MunicipalityApplicationsElem.appendChild(municipalityApplication);
				}
			}
	}
}

