package se.sogeti.summerjob.listapplications;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jhlabs.image.DissolveFilter;

import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ListApplicationsAdminModule extends AnnotatedForegroundModule {
	
	JobApplicationDAO<MunicipalityJobApplication> municipalityJobApplicationDAO;
	JobApplicationDAO<BusinessSectorJobApplication> businessSectorJobApplicationDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera kommunala ansökningar",name="ManageMunicipalityApplication")
	private String manageMunicipalityUrl="manage-municipality-app";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera näringslivsansökningar",name="ManageBusinessApplication")
	private String manageBusinessUrl="manage-business-app";
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		municipalityJobApplicationDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, daoFactory);
		businessSectorJobApplicationDAO = new JobApplicationDAO<BusinessSectorJobApplication>(dataSource, BusinessSectorJobApplication.class, daoFactory);
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
		
		Element approvedMunicipalityElement = doc.createElement("ApprovedMunicipality");
		Element disapprovedMunicipalityElement = doc.createElement("DisapprovedMunicipality");
		Element approvedBusinessElement = doc.createElement("ApprovedBusiness");
		Element disapprovedBusinessElement = doc.createElement("DisapprovedBusiness");
		
		String socialSecurityNumber = req.getParameter("socialSecurityNumber"); // If this is null, we get all applications.
		if (socialSecurityNumber != null) {
			socialSecurityNumber = socialSecurityNumber.isEmpty() ? null : socialSecurityNumber;
		}

		List<MunicipalityJobApplication> approvedMunicipalityApplications = municipalityJobApplicationDAO.getAllByApprovedAndDescendingOrder(socialSecurityNumber, true, true);
		if (approvedMunicipalityApplications != null) {
			for (MunicipalityJobApplication app : approvedMunicipalityApplications) {
				Element municipalityElement = doc.createElement("MunicipalityJobApplication");
				XMLUtils.appendNewElement(doc, municipalityElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, municipalityElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, municipalityElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.appendNewElement(doc, municipalityElement, "url", manageMunicipalityUrl + "?appId=" + app.getId());
				approvedMunicipalityElement.appendChild(municipalityElement);
			}
		}
		
		List<MunicipalityJobApplication> disapprovedMunicipalityApplications = municipalityJobApplicationDAO.getAllByApprovedAndDescendingOrder(socialSecurityNumber, false, true);
		if (disapprovedMunicipalityApplications != null) { 
			for (MunicipalityJobApplication app : disapprovedMunicipalityApplications) {
				Element municipalityElement = doc.createElement("MunicipalityJobApplication");
				XMLUtils.appendNewElement(doc, municipalityElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, municipalityElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, municipalityElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.appendNewElement(doc, municipalityElement, "url", manageMunicipalityUrl + "?appId=" + app.getId());
				disapprovedMunicipalityElement.appendChild(municipalityElement);
			}
		}
		
		List<BusinessSectorJobApplication> approvedBusinessApplications = businessSectorJobApplicationDAO.getAllByApprovedAndDescendingOrder(socialSecurityNumber, true, true);
		if (approvedBusinessApplications != null) {
			for (BusinessSectorJobApplication app : approvedBusinessApplications) {
				Element businessElement = doc.createElement("BusinessSectorJobApplication");
				XMLUtils.appendNewElement(doc, businessElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, businessElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, businessElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.append(doc, businessElement, app.getJob());
				XMLUtils.appendNewElement(doc, businessElement, "url", manageBusinessUrl + "?appId=" + app.getId());
				approvedBusinessElement.appendChild(businessElement);
			}
		}
		
		List<BusinessSectorJobApplication> disapprovedBusinessApplications = businessSectorJobApplicationDAO.getAllByApprovedAndDescendingOrder(socialSecurityNumber, false, true);
		if (disapprovedBusinessApplications != null) {
			for (BusinessSectorJobApplication app : disapprovedBusinessApplications) {
				Element businessElement = doc.createElement("BusinessSectorJobApplication");
				XMLUtils.appendNewElement(doc, businessElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, businessElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, businessElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.append(doc, businessElement, app.getJob());
				XMLUtils.appendNewElement(doc, businessElement, "url", manageBusinessUrl + "?appId=" + app.getId());
				disapprovedBusinessElement.appendChild(businessElement);
			}
		}
		
		doc.getFirstChild().appendChild(approvedMunicipalityElement);
		doc.getFirstChild().appendChild(disapprovedMunicipalityElement);
		doc.getFirstChild().appendChild(approvedBusinessElement);
		doc.getFirstChild().appendChild(disapprovedBusinessElement);
		
		return new SimpleForegroundModuleResponse(doc);
	}
}
