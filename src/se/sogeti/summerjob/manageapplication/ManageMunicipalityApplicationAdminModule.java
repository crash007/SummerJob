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

import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.cv.CvServiceHander;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobApplicationDAO;
import se.sogeti.summerjob.JsonResponse;
import se.sogeti.summerjob.addsummerjobapplication.AddMunicipalityJobApplicationHandler;
import se.sogeti.summerjob.match.MatchMunicipalityJobHandler;
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

public class ManageMunicipalityApplicationAdminModule extends AnnotatedRESTModule {
	
	MunicipalityJobApplicationDAO appDAO;
	
	@InstanceManagerDependency
	private AddMunicipalityJobApplicationHandler addMunicipalityJobApplicationHandler;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lista näringslivsjobb", name="ListAppsURL")
	String listJobApplicationsURL="list-applications";
	
	@InstanceManagerDependency(required = true)
	private CvServiceHander cvServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		appDAO = new MunicipalityJobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
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
		
		XMLUtils.appendNewElement(doc, element, "CvMunicipalityApplicationUrl", cvServiceHandler.getMunicipalityApplicationCvUrl());
		
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		if (appId == null) {
			return new SimpleForegroundModuleResponse(doc);
		}
		
		MunicipalityJobApplication app = appDAO.getById(appId);
		if(app!=null){
			
			XMLUtils.append(doc, appElement, app);
			
			if(addMunicipalityJobApplicationHandler!=null && req!=null){
				XMLUtils.appendNewElement(doc, appElement, "editAppURL", req.getContextPath()+addMunicipalityJobApplicationHandler.getUrl() + "?appId=" + app.getId());
			}
			
			XMLUtils.appendNewElement(doc, appElement, "listJobApplicationsURL", listJobApplicationsURL);
			XMLUtils.appendNewElement(doc, appElement, "BackURL", req.getHeader("referer"));			
			
			Element rankingsElement = doc.createElement("Rankings");
			for (int i = 1; i < 11; i++) {
				Element ranking = doc.createElement("Ranking");
				XMLUtils.appendNewElement(doc, ranking, "value", i);
				XMLUtils.appendNewElement(doc, ranking, "selected", app.getRanking().intValue() == i);
				rankingsElement.appendChild(ranking);
			}
			appElement.appendChild(rankingsElement);
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="saveapplicationoptions.json", method="post")
	public void saveApplicationOptions(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));        
        log.info("Request for savemunicipalityoptions.json with appId:"+appId+" by user:"+user);
        
		PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        
		if (appId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta den aktuella ansökan för att spara ändringarna.\"}", callback, writer);
        }
        
        MunicipalityJobApplication app = appDAO.getByIdWithJob(appId);
        
        app.setApproved(BooleanUtils.toBoolean(req.getParameter("statusApprove")));
        app.setControlled(true);
        app.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        app.setControlledByUser(user.getUsername());
        app.setAdminNotes(req.getParameter("adminNotes"));
        app.setRanking(NumberUtils.toInt(req.getParameter("ranking")));
        
		try {
			appDAO.save(app);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
}
