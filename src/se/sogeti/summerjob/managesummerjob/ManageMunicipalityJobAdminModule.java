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
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.summerjob.JsonResponse;
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

public class ManageMunicipalityJobAdminModule extends AnnotatedRESTModule {
	
	JobDAO<MunicipalityJob> municipalityJobDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
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
		Element jobElement = doc.createElement("MunicipalityJobInfo");
		doc.getFirstChild().appendChild(jobElement);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		if (jobId == null) {
			return new SimpleForegroundModuleResponse(doc);
		}
		
		MunicipalityJob job = municipalityJobDAO.getById(jobId);
		XMLUtils.append(doc, jobElement, job);
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	@RESTMethod(alias="approvesummerjob.json", method="post")
	public void approveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        if (jobId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
        }
        MunicipalityJob job = municipalityJobDAO.getById(jobId);
        
        job.setApproved(true);
        job.setApprovedByUser(user.getUsername());
        job.setApprovedDate(new Date(Calendar.getInstance().getTimeInMillis()));
        job.setControlled(true);
        job.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        job.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			municipalityJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen är nu godkänd.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
	
	@RESTMethod(alias="disapprovesummerjob.json", method="post")
	public void disapproveSummerjob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        if (jobId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
        }
        MunicipalityJob job = municipalityJobDAO.getById(jobId);
        
        job.setApproved(false);
        job.setApprovedByUser(user.getUsername());
        job.setControlled(true);
        job.setControlledDate(new Date(Calendar.getInstance().getTimeInMillis()));
        job.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			municipalityJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen har nu nekats.\"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
	
	@RESTMethod(alias="initiatesummerjob.json", method="post")
	public void initiateControl(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
        if (jobId == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Kunde inte hämta det aktuella jobbet för att spara ändringarna.\"}", callback, writer);
        }
        MunicipalityJob job = municipalityJobDAO.getById(jobId);
        
        job.setInitiatedByUser(user.getUsername());
        job.setAdminNotes(req.getParameter("adminNotes"));
        
		try {
			municipalityJobDAO.save(job);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Annonsen kontrolleras nu av dig. \"}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception", e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ändringarna skulle sparas.\"}", callback, writer);
		}				
	}
}
