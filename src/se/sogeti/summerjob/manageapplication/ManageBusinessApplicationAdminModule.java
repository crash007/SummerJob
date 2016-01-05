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

import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.cv.CvServiceHander;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.summerjob.JsonResponse;
import se.sogeti.summerjob.addsummerjob.AddBusinessJobHandler;
import se.sogeti.summerjob.match.MatchBusinessJobHandler;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ManageBusinessApplicationAdminModule extends AnnotatedRESTModule {
	
	BusinessSectorJobApplicationDAO jobApplicationDAO;
	
	@InstanceManagerDependency
	private AddBusinessJobHandler addBusinessJobHandler;
	
	@InstanceManagerDependency
	private MatchBusinessJobHandler matchBusinessJobHandler;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lista ansökningar", name="ListAppsURL")
	String listJobApplicationsURL="list-applications";
	
	@InstanceManagerDependency(required = true)
	private CvServiceHander cvServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		jobApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, hierarchyDaoFactory);
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
		
		XMLUtils.appendNewElement(doc, element, "CvBusinessApplicationUrl", cvServiceHandler.getBusinessApplicationCvUrl());
		
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		
		if(appId != null){
			BusinessSectorJobApplication app = jobApplicationDAO.getByIdWithJobAndPersonApplications(appId);
			if (app == null || app.getJob() == null) {
				return new SimpleForegroundModuleResponse(doc);
			}
			
			Element appInfoElement = doc.createElement("ApplicationInfo");
			XMLUtils.append(doc, appInfoElement, app);
			doc.getFirstChild().appendChild(appInfoElement);
			XMLUtils.appendNewElement(doc, appInfoElement, "listJobApplicationsURL", listJobApplicationsURL);
			XMLUtils.appendNewElement(doc, appInfoElement, "editAppURL", 
					addBusinessJobHandler.getUrl() + "?jobId=" + app.getJob().getId() + "&appId=" + app.getId());
			
			XMLUtils.appendNewElement(doc, appInfoElement, "matchURL", req.getContextPath()+matchBusinessJobHandler.getUrl()+"?jobId="+app.getJob().getId());
			
			
			XMLUtils.appendNewElement(doc, appInfoElement, "BackURL", req.getHeader("referer"));
			
			Element rankingsElement = doc.createElement("Rankings");
			for (int i = 1; i < 11; i++) {
				Element ranking = doc.createElement("Ranking");
				XMLUtils.appendNewElement(doc, ranking, "value", i);
				XMLUtils.appendNewElement(doc, ranking, "selected", app.getRanking().intValue() == i);
				rankingsElement.appendChild(ranking);
			}
			appInfoElement.appendChild(rankingsElement);
		} 
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="saveapplicationoptions.json", method="post")
	public void saveApplicationOptions(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer appId = NumberUtils.toInt(req.getParameter("appId"));
        if (appId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
        }
        BusinessSectorJobApplication app = jobApplicationDAO.getByIdWithJobAndPersonApplications(appId);
        
        app.setApproved(BooleanUtils.toBoolean(req.getParameter("statusApprove")));
        app.setControlled(true);
        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        app.setControlledByUser(user.getUsername());
        app.setAdminNotes(req.getParameter("adminNotes"));
        app.setRanking(NumberUtils.toInt(req.getParameter("ranking")));
        
		try {
			jobApplicationDAO.save(app);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
	
//	@RESTMethod(alias="approveapplication.json", method="post")
//	public void approveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
//        PrintWriter writer = res.getWriter();
//        String callback = req.getParameter("callback"); 
//		
//        JsonResponse.initJsonResponse(res, writer, callback);
//        
//        Integer appId = NumberUtils.toInt(req.getParameter("appId"));
//        if (appId == null) {
//        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
//        }
//        BusinessSectorJobApplication app = jobApplicationDAO.getByIdWithJob(appId);
//        
//        app.setApproved(true);
//        app.setApprovedDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        app.setControlled(true);
//        app.setControlledByUser(user.getUsername());
//        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        app.setAdminNotes(req.getParameter("adminNotes"));
//        app.setRanking(NumberUtils.toInt(req.getParameter("ranking")));
//        
//		try {
//			jobApplicationDAO.save(app);
//			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ansökan är nu godkänd.\"}", callback, writer);
//			return;
//		} catch (SQLException e) {
//			log.error("SQL exception", e);
//			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
//		}				
//	}
//	
//	@RESTMethod(alias="disapproveapplication.json", method="post")
//	public void disapproveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
//        PrintWriter writer = res.getWriter();
//        String callback = req.getParameter("callback"); 
//		
//        JsonResponse.initJsonResponse(res, writer, callback);
//        
//        Integer appId = NumberUtils.toInt(req.getParameter("appId"));
//        if (appId == null) {
//        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
//        }
//        BusinessSectorJobApplication app = jobApplicationDAO.getByIdWithJob(appId);
//        
//        app.setApproved(false);
//        app.setControlledByUser(user.getUsername());
//        app.setControlled(true);
//        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        app.setAdminNotes(req.getParameter("adminNotes"));
//        app.setRanking(NumberUtils.toInt(req.getParameter("ranking")));
//        
//		try {
//			jobApplicationDAO.save(app);
//			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ansökan har nu nekats.\"}", callback, writer);
//			return;
//		} catch (SQLException e) {
//			log.error("SQL exception", e);
//			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
//		}				
//	}
}
