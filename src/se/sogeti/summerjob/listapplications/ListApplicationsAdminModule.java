package se.sogeti.summerjob.listapplications;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.io.FileUtils;
import se.unlogic.standardutils.mime.MimeUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.streams.StreamUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.HTTPUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ListApplicationsAdminModule extends AnnotatedForegroundModule {
	
	JobApplicationDAO<MunicipalityJobApplication> municipalityJobApplicationDAO;
	JobApplicationDAO<BusinessSectorJobApplication> businessSectorJobApplicationDAO;
	BusinessSectorJobApplicationDAO businessApplicationDAO;
	
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
		
		Element approvedMunicipalityElement = doc.createElement("ApprovedMunicipality");
		Element disapprovedMunicipalityElement = doc.createElement("DisapprovedMunicipality");
		Element approvedBusinessElement = doc.createElement("ApprovedBusiness");
		Element disapprovedBusinessElement = doc.createElement("DisapprovedBusiness");
		
		// Dessa används för att filtrera ansökningar. Annars får vi alla ansökningar
		String socialSecurityNumber = req.getParameter("socialSecurityNumber");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");

		List<MunicipalityJobApplication> approvedMunicipalityApplications = municipalityJobApplicationDAO.getAllByApprovedByDescendingOrder(socialSecurityNumber, firstname, lastname, true, true);
		createMunicipalityApplicationElements(doc, approvedMunicipalityElement, approvedMunicipalityApplications);
		
		List<MunicipalityJobApplication> disapprovedMunicipalityApplications = municipalityJobApplicationDAO.getAllByApprovedByDescendingOrder(socialSecurityNumber, firstname, lastname, false, true);
		createMunicipalityApplicationElements(doc, disapprovedMunicipalityElement, disapprovedMunicipalityApplications);
		
		List<BusinessSectorJobApplication> approvedBusinessApplications = businessApplicationDAO.getAllByApprovedWithJobByDescendingOrder(socialSecurityNumber, firstname, lastname, true, true);
		createBusinessApplicationElements(doc, approvedBusinessElement, approvedBusinessApplications);
		
		List<BusinessSectorJobApplication> disapprovedBusinessApplications = businessApplicationDAO.getAllByApprovedWithJobByDescendingOrder(socialSecurityNumber, firstname, lastname, false, true);

		createBusinessApplicationElements(doc, disapprovedBusinessElement, disapprovedBusinessApplications);
		
		doc.getFirstChild().appendChild(approvedMunicipalityElement);
		doc.getFirstChild().appendChild(disapprovedMunicipalityElement);
		doc.getFirstChild().appendChild(approvedBusinessElement);
		doc.getFirstChild().appendChild(disapprovedBusinessElement);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@WebPublic
	public ForegroundModuleResponse getBusinessApplicationCv(HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
			throws Throwable {
		
		Integer appId = NumberUtils.toInt(req.getParameter("id"));
		if(appId!=null){			
			BusinessSectorJobApplication application = businessApplicationDAO.getById(appId);
			if(application!=null){
				FileInputStream in = null;
				OutputStream out = null;
				
				File cv = new File(application.getCvFilename());
				in = new FileInputStream(cv);

				HTTPUtils.setContentLength(cv.length(), res);

				res.setContentType(MimeUtils.getMimeType(cv));

				res.setHeader("Content-Disposition", "inline; filename=\"" + FileUtils.toValidHttpFilename("cv") + "\"");

				out = res.getOutputStream();

				StreamUtils.transfer(in, out);
				 
			}else{
				log.warn("No application with id="+appId);
			}
			 
		}else{
			log.warn("No app id found");
		}
		
		return null;
		
	}

	protected void createMunicipalityApplicationElements(Document doc, Element applicationElementList,
			List<MunicipalityJobApplication> applications) {
		if (applications != null) {
			
			for (MunicipalityJobApplication app : applications) {
				Element businessElement = doc.createElement("MunicipalityJobApplication");
				XMLUtils.appendNewElement(doc, businessElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, businessElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, businessElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.appendNewElement(doc, businessElement, "id", app.getId());
				XMLUtils.appendNewElement(doc, businessElement, "url", manageMunicipalityUrl + "?appId=" + app.getId());
				applicationElementList.appendChild(businessElement);
			}
		}
	}
	
	protected void createBusinessApplicationElements(Document doc, Element applicationElementList,
			List<BusinessSectorJobApplication> applications) {
		if (applications != null) {
			
			for (BusinessSectorJobApplication app : applications) {
				Element businessElement = doc.createElement("BusinessSectorJobApplication");
				XMLUtils.appendNewElement(doc, businessElement, "ranking", app.getRanking());
				XMLUtils.appendNewElement(doc, businessElement, "socialSecurityNumber", app.getSocialSecurityNumber());
				XMLUtils.appendNewElement(doc, businessElement, "name", app.getFirstname() + " " + app.getLastname());
				XMLUtils.appendNewElement(doc, businessElement, "id", app.getId());
				if(app.getJob()!=null){
					XMLUtils.appendNewElement(doc, businessElement, "WorkTitle", app.getJob().getWorkTitle());
					XMLUtils.appendNewElement(doc, businessElement, "Company", app.getJob().getCompany());
				}
				
				XMLUtils.appendNewElement(doc, businessElement, "url", manageBusinessUrl + "?appId=" + app.getId());
				
				applicationElementList.appendChild(businessElement);
			}
		}
	}
	
}
