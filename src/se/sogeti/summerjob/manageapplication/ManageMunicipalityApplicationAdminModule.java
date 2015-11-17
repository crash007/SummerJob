package se.sogeti.summerjob.manageapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.summerjob.JsonResponse;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
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

public class ManageMunicipalityApplicationAdminModule extends AnnotatedRESTModule {
	
	JobApplicationDAO<MunicipalityJobApplication> appDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lägga till och redigera ett jobb", name="AddEditAppURL")
	String editApplicationURL="add-municipality-job-application";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lista näringslivsjobb", name="ListAppsURL")
	String listJobApplicationsURL="list-applications";
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		appDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, daoFactory);
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
		Element appElement = doc.createElement("ApplicationInfo");
		doc.getFirstChild().appendChild(appElement);
		
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		if (appId == null) {
			return new SimpleForegroundModuleResponse(doc);
		}
		
		MunicipalityJobApplication app = appDAO.getById(appId);
		XMLUtils.append(doc, appElement, app);
		XMLUtils.appendNewElement(doc, appElement, "editAppURL", editApplicationURL);
		XMLUtils.appendNewElement(doc, appElement, "listJobApplicationsURL", listJobApplicationsURL);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="approveapplication.json", method="post")
	public void approveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer appId = NumberUtils.toInt(req.getParameter("appId"));
        if (appId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
        }
        MunicipalityJobApplication app = appDAO.getById(appId);
        
        app.setApproved(true);
        app.setApprovedDate(new Date(Calendar.getInstance().getTimeInMillis()));
        app.setControlled(true);
        app.setControlledByUser(user.getUsername());
        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        app.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			appDAO.save(app);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ansökan är nu godkänd.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
	
	@RESTMethod(alias="disapproveapplication.json", method="post")
	public void disapproveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer appId = NumberUtils.toInt(req.getParameter("appId"));
        if (appId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
        }
        MunicipalityJobApplication app = appDAO.getById(appId);
        
        app.setApproved(false);
        app.setControlledByUser(user.getUsername());
        app.setControlled(true);
        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        app.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			appDAO.save(app);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ansökan har nu nekats.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
}
