package se.sogeti.summerjob.listapplications;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.cv.CvServiceHander;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobApplicationDAO;
import se.sogeti.summerjob.match.MatchBusinessJobHandler;
import se.sogeti.summerjob.match.MatchMunicipalityJobHandler;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ListApplicationsAdminModule extends AnnotatedForegroundModule {
	
	MunicipalityJobApplicationDAO municipalityJobApplicationDAO;
	BusinessSectorJobApplicationDAO businessApplicationDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera kommunala ansökningar",name="ManageMunicipalityApplication")
	private String manageMunicipalityUrl="manage-municipality-app";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera näringslivsansökningar",name="ManageBusinessApplication")
	private String manageBusinessUrl="manage-business-app";
	
	@InstanceManagerDependency(required=false)
	MatchMunicipalityJobHandler matchMunicipalityJobHandler;
	
	@InstanceManagerDependency(required=false)
	MatchBusinessJobHandler matchBusinessJobHandler;
	
	
	@InstanceManagerDependency(required = true)
	private CvServiceHander cvServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		municipalityJobApplicationDAO = new MunicipalityJobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
		businessApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, daoFactory);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
			throws Throwable {
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		
		XMLUtils.appendNewElement(doc, element, "CvBusinessApplicationUrl", cvServiceHandler.getBusinessApplicationCvUrl());
		XMLUtils.appendNewElement(doc, element, "CvMunicipalityApplicationUrl", cvServiceHandler.getMunicipalityApplicationCvUrl());
		
		Element approvedMunicipalityElement = doc.createElement("ApprovedMunicipality");
		Element disapprovedMunicipalityElement = doc.createElement("DisapprovedMunicipality");
		Element approvedBusinessElement = doc.createElement("ApprovedBusiness");
		Element disapprovedBusinessElement = doc.createElement("DisapprovedBusiness");
		
		Element declinedMunicipalityElement = doc.createElement("DeclinedMunicipality");
		Element declinedBusinessElement = doc.createElement("DeclinedBusiness");
		
		// Dessa används för att filtrera ansökningar. Annars får vi alla ansökningar
		String socialSecurityNumber = req.getParameter("socialSecurityNumber");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String personalLetter = req.getParameter("personalLetter");
		
		if(socialSecurityNumber!=null){
			XMLUtils.appendNewElement(doc, element, "SocialSecurityNumber", socialSecurityNumber);
		}
		
		if(firstname!=null){
			XMLUtils.appendNewElement(doc, element, "Firstname", firstname);			
		}

		if(lastname!=null){
			XMLUtils.appendNewElement(doc, element, "Lastname", lastname);			
		}
		
		if (personalLetter != null) {
			XMLUtils.appendNewElement(doc, element, "PersonalLetter", personalLetter);
		}
		
		List<MunicipalityJobApplication> approvedMunicipalityApplications = municipalityJobApplicationDAO.getApprovedAndNotDeclinedWithJob(socialSecurityNumber, firstname, lastname, personalLetter,  Order.DESC);
		createMunicipalityApplicationElements(doc, approvedMunicipalityElement, approvedMunicipalityApplications, req.getContextPath());
		
		List<MunicipalityJobApplication> disapprovedMunicipalityApplications = municipalityJobApplicationDAO.getAll(socialSecurityNumber, firstname, lastname, personalLetter, false, null,Order.DESC);
		createMunicipalityApplicationElements(doc, disapprovedMunicipalityElement, disapprovedMunicipalityApplications, req.getContextPath());
		
		List<BusinessSectorJobApplication> approvedBusinessApplications = businessApplicationDAO.getApprovedAndNotDeclinedWithJob(socialSecurityNumber, firstname, lastname, personalLetter,  Order.DESC);
		createBusinessApplicationElements(doc, approvedBusinessElement, approvedBusinessApplications, req.getContextPath());
		
		List<BusinessSectorJobApplication> disapprovedBusinessApplications = businessApplicationDAO.getAllWithJob(socialSecurityNumber, firstname, lastname, personalLetter, false, null, Order.DESC);

		//DECLINED applications
		List<MunicipalityJobApplication> declinedMunicipalityApplications = municipalityJobApplicationDAO.getAllWithJob(socialSecurityNumber, firstname, lastname, personalLetter, null,ApplicationStatus.DECLINED, Order.DESC);
		List<BusinessSectorJobApplication> declinedBusinessApplications = businessApplicationDAO.getAllWithJob(socialSecurityNumber, firstname, lastname, personalLetter, null, ApplicationStatus.DECLINED, Order.DESC);
		
		createMunicipalityApplicationElements(doc, declinedMunicipalityElement, declinedMunicipalityApplications, req.getContextPath());
		createBusinessApplicationElements(doc, declinedBusinessElement, declinedBusinessApplications, req.getContextPath());
		
		createBusinessApplicationElements(doc, disapprovedBusinessElement, disapprovedBusinessApplications, req.getContextPath());
		
		doc.getFirstChild().appendChild(approvedMunicipalityElement);
		doc.getFirstChild().appendChild(disapprovedMunicipalityElement);
		doc.getFirstChild().appendChild(approvedBusinessElement);
		doc.getFirstChild().appendChild(disapprovedBusinessElement);
		doc.getFirstChild().appendChild(declinedBusinessElement);
		doc.getFirstChild().appendChild(declinedMunicipalityElement);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	

	protected void createMunicipalityApplicationElements(Document doc, Element applicationElementList,
			List<MunicipalityJobApplication> applications, String contextPath) {
		if (applications != null) {
			
			for (MunicipalityJobApplication app : applications) {
				Element appElement = doc.createElement("MunicipalityJobApplication");
				appendCommonElements(doc, app, appElement);
				XMLUtils.appendNewElement(doc, appElement, "applicationType", app.getApplicationType());
				XMLUtils.appendNewElement(doc, appElement, "url", manageMunicipalityUrl + "?appId=" + app.getId());
				if(app.getJob()!=null){
					log.debug("Application has a job.");
					XMLUtils.appendNewElement(doc, appElement, "matchUrl", contextPath+ matchMunicipalityJobHandler.getUrl() + "?jobId=" + app.getJob().getId());
				}
				applicationElementList.appendChild(appElement);
			}
		}
	}
	
	protected void createBusinessApplicationElements(Document doc, Element applicationElementList,
			List<BusinessSectorJobApplication> applications, String contextPath) {
		if (applications != null) {
			
			for (BusinessSectorJobApplication app : applications) {
				Element appElement = doc.createElement("BusinessSectorJobApplication");
				XMLUtils.appendNewElement(doc, appElement, "url", manageBusinessUrl + "?appId=" + app.getId());
				
				appendCommonElements(doc, app, appElement);
				
				if(app.getJob()!=null){
					XMLUtils.appendNewElement(doc, appElement, "WorkTitle", app.getJob().getWorkTitle());
					XMLUtils.appendNewElement(doc, appElement, "Company", app.getJob().getCompany());
					XMLUtils.appendNewElement(doc, appElement, "matchUrl", contextPath+ matchBusinessJobHandler.getUrl() + "?jobId=" + app.getJob().getId());
				}
				
				applicationElementList.appendChild(appElement);
			}
		}
	}
	
	protected <T extends JobApplication> void appendCommonElements(Document doc, T app, Element appElement) {
		XMLUtils.appendNewElement(doc, appElement, "ranking", app.getRanking());
		XMLUtils.appendNewElement(doc, appElement, "socialSecurityNumber", app.getSocialSecurityNumber());
		XMLUtils.appendNewElement(doc, appElement, "name", app.getFirstname() + " " + app.getLastname());
		XMLUtils.appendNewElement(doc, appElement, "id", app.getId());
		XMLUtils.appendNewElement(doc, appElement, "controlledByUser", app.getControlledByUser());
		XMLUtils.appendNewElement(doc, appElement, "controlledDate", app.getControlledDate());
		XMLUtils.appendNewElement(doc, appElement, "approved", app.getApproved());
		
		if(app.getCvFilename()!=null){
			XMLUtils.appendNewElement(doc, appElement, "hasCv", "true");
		}else{
			XMLUtils.appendNewElement(doc, appElement, "hasCv", "false");
		}
	}
}
