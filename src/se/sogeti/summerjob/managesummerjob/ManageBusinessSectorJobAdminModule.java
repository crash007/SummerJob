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
import se.sogeti.summerjob.addsummerjob.AddBusinessJobHandler;
import se.sogeti.summerjob.listjobs.ListSummerJobsHandler;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ManageBusinessSectorJobAdminModule extends AnnotatedRESTModule implements ManageBusinessSectorJobHandler{
	
	JobDAO<BusinessSectorJob> businessSectorJobDAO;
	
	@InstanceManagerDependency(required = true)
	private AddBusinessJobHandler addBusinessJobHandler;
	
	@InstanceManagerDependency(required = true)
	private ListSummerJobsHandler listSummerJobsHandler;
	
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
		
		if(job!=null){
			XMLUtils.append(doc, jobElement, job);
			XMLUtils.appendNewElement(doc, jobElement, "editUrl", req.getContextPath()+ addBusinessJobHandler.getUrl()+"?jobId="+job.getId());
		}
		
		XMLUtils.appendNewElement(doc, jobElement, "listJobsURL", req.getContextPath()+listSummerJobsHandler.getBusinessJobsUrl());
		XMLUtils.appendNewElement(doc, jobElement, "BackURL", req.getHeader("referer"));
		
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

	@Override
	public String getUrl() {
		return this.getFullAlias();
	}

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface,
			DataSource dataSource) throws Exception {
		
		super.init(moduleDescriptor, sectionInterface, dataSource);
		
		if(!systemInterface.getInstanceHandler().addInstance(ManageBusinessSectorJobHandler.class, this)){
			throw new RuntimeException("Unable to register module in global instance handler using key " +ManageBusinessSectorJobHandler.class.getSimpleName() + ", another instance is already registered using this key.");
		}
	}

	@Override
	public void unload() throws Exception {

		if(this.equals(systemInterface.getInstanceHandler().getInstance(ManageBusinessSectorJobHandler.class))){
			log.info("Unloading ManageBusinessSectorJobHandler from instanceHandler.");
			systemInterface.getInstanceHandler().removeInstance(ManageBusinessSectorJobHandler.class);
		}	
		
		super.unload();
	}
	
	
}
