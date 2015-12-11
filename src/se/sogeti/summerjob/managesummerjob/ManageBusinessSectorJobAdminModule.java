package se.sogeti.summerjob.managesummerjob;

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

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
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
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ManageBusinessSectorJobAdminModule extends AnnotatedRESTModule {
	
	JobDAO<BusinessSectorJob> businessSectorJobDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lägga till och redigera ett jobb", name="AddEditJobURL")
	String editJobURL="add-business-sector-job";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till sidan för att lista näringslivsjobb", name="ListJobsURL")
	String listJobsURL="list-summerjobs";
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		businessSectorJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, daoFactory);
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
		Element jobElement = doc.createElement("BusinessSectorJobInfo");
		doc.getFirstChild().appendChild(jobElement);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		if (jobId == null) {
			return new SimpleForegroundModuleResponse(doc);
		}
		
		BusinessSectorJob job = businessSectorJobDAO.getById(jobId);
		XMLUtils.append(doc, jobElement, job);
		XMLUtils.appendNewElement(doc, jobElement, "editURL", editJobURL);
		XMLUtils.appendNewElement(doc, jobElement, "listJobsURL", listJobsURL);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="savejoboptions.json", method="post")
	public void saveJobOptions(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        if (jobId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
        }
        
        BusinessSectorJob job = businessSectorJobDAO.getById(jobId);
        Boolean approveStatus = BooleanUtils.toBoolean(req.getParameter("statusApprove"));
        
        job.setApproved(approveStatus);
        job.setIsOpen(approveStatus);
        job.setApprovedByUser(user.getUsername());
        job.setControlled(true);
        job.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        job.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			businessSectorJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
	
//	@RESTMethod(alias="approvesummerjob.json", method="post")
//	public void approveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
//        PrintWriter writer = res.getWriter();
//        String callback = req.getParameter("callback"); 
//		
//        JsonResponse.initJsonResponse(res, writer, callback);
//        
//        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
//        if (jobId == null) {
//        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
//        }
//        BusinessSectorJob job = businessSectorJobDAO.getById(jobId);
//        
//        job.setApproved(true);
//        job.setApprovedByUser(user.getUsername());
//        job.setApprovedDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        job.setControlled(true);
//        job.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        job.setAdminNotes(req.getParameter("adminNotes"));
//        
//		try {
//			businessSectorJobDAO.save(job);
//			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen är nu godkänd.\"}", callback, writer);
//			return;
//		} catch (SQLException e) {
//			log.error("SQL exception", e);
//			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
//		}				
//	}
//	
//	@RESTMethod(alias="disapprovesummerjob.json", method="post")
//	public void disapproveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
//        PrintWriter writer = res.getWriter();
//        String callback = req.getParameter("callback"); 
//		
//        JsonResponse.initJsonResponse(res, writer, callback);
//        
//        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
//        if (jobId == null) {
//        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
//        }
//        BusinessSectorJob job = businessSectorJobDAO.getById(jobId);
//        
//        job.setApproved(false);
//        job.setApprovedByUser(user.getUsername());
//        job.setControlled(true);
//        job.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        job.setAdminNotes(req.getParameter("adminNotes"));
//        
//		try {
//			businessSectorJobDAO.save(job);
//			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu nekats.\"}", callback, writer);
//			return;
//		} catch (SQLException e) {
//			log.error("SQL exception", e);
//			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
//		}				
//	}
	
	@RESTMethod(alias="initiatesummerjob.json", method="post")
	public void initiateControl(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        if (jobId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
        }
        BusinessSectorJob job = businessSectorJobDAO.getById(jobId);
        
        job.setInitiatedByUser(user.getUsername());
        job.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			businessSectorJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen kontrolleras nu av dig. \"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
}
