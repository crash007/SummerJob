package se.sogeti.summerjob.match;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.cv.CvServiceHander;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.json.JsonObject;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class MatchBusinessJobsModule extends AnnotatedRESTModule{
	

	private BusinessSectorJobDAO businessJobDAO;
	private BusinessSectorJobApplicationDAO businessJobApplicationDAO;
	
	@InstanceManagerDependency(required=true)
	private CvServiceHander cvServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		businessJobDAO = new BusinessSectorJobDAO(dataSource, BusinessSectorJob.class, daoFactory);
		businessJobApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, daoFactory);
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
		
		Integer jobId=null;
		
		if(req.getParameter("jobId")!=null){
		 jobId = NumberUtils.toInt(req.getParameter("jobId"));
		}
	
		if(jobId!=null){
			BusinessSectorJob job = businessJobDAO.getByIdWithApplications(jobId);
			if(job!=null){
				Element matchBusinessJobElement = doc.createElement("MatchBusinessJob");
				doc.getFirstChild().appendChild(matchBusinessJobElement);
				
				if(job.getApplications()!=null){
					Integer matchedApplications = FormUtils.countApplications(job.getApplications(), ApplicationStatus.MATCHED);
					job.setMatchedApplications(matchedApplications);
					//job.setAssignedApplications(FormUtils.countApplications(job.getApplications(), ApplicationStatus.ASSIGNED));
					job.setOpenApplications(job.getNumberOfWorkersNeeded() - matchedApplications);
					//XMLUtils.append(doc, doc.createElement("AppointedApplications"), job.getApplications());
					
				}else{
					job.setOpenApplications(job.getNumberOfWorkersNeeded());
					job.setMatchedApplications(0);					
				}
				
				XMLUtils.append(doc, matchBusinessJobElement, job);
			
				
				//sort applications by drivers license and if over 18
				//First hand pick				
				List<BusinessSectorJobApplication> goodCandidates = businessJobApplicationDAO.getCandidatesFulfillingCriteras(job);
				if(goodCandidates!=null){
					for(BusinessSectorJobApplication candidate: goodCandidates){
						log.info(candidate);
					}
					
					XMLUtils.append(doc, matchBusinessJobElement, "GoodCandidates", goodCandidates);
				}else{
					log.info("No candidates fulling job criteras");
				}
				
				
				List<BusinessSectorJobApplication> badCandidates = businessJobApplicationDAO.getCandidatesNotFulfillingCriteras(job);
				if(badCandidates!=null){
					for(BusinessSectorJobApplication candidate: badCandidates){
						log.info(candidate);
					}
				}else{
					log.info("No candidates Not fulling job criteras");
				}
				
				XMLUtils.append(doc, matchBusinessJobElement, "BadCandidates", badCandidates);
				

			}else{
				log.warn("No job with id "+jobId+" found.");
			}
			
		}else{
			log.warn("jobId is missing");
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}

	
	@RESTMethod(alias="remove-worker.json", method="post")
	public void removeWorkerFromWork(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException{
		log.info("Request for remove-worker.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		String[] applicationIdStrings =req.getParameterValues("application-id");
		
		if(applicationIdStrings!=null){
			for(String id:applicationIdStrings){
				
				try {
					BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJob(NumberUtils.toInt(id));
					
					if(jobApplication!=null){						
						jobApplication.setStatus(ApplicationStatus.NONE);
						businessJobApplicationDAO.save(jobApplication);
					}else{
						log.warn("No application with id: "+id);
					}
				} catch (SQLException e) {
					log.error("Exception when getting application",e);
					result.putField("status", "error");
					result.putField("message", "Error when calling db");
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					return;
				}
			}
			
			result.putField("status", "success");
			result.putField("message", "Removed applications appointed to job");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			
		}else{
			log.info("Parameter application-id was null.");
			result.putField("status", "fail");
			result.putField("message", "parameter application-id is missing");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
		}
		
	}
	
	@RESTMethod(alias="match-worker.json", method="post")
	public void addApplicationToJob(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException{
		log.info("Request for add-worker.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		Integer applicationId = NumberUtils.toInt(req.getParameter("application-id"));
		Integer jobId = NumberUtils.toInt(req.getParameter("job-id"));
		
		if(applicationId!=null && jobId!=null){
			try {
				log.debug("Getting application with id: "+applicationId);
				BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJob(applicationId);
				BusinessSectorJob job = businessJobDAO.getByIdWithApplications(jobId);
				
				if(jobApplication!=null){
					if(job!=null){
						jobApplication.setStatus(ApplicationStatus.MATCHED);
						jobApplication.setJob(job);
						businessJobApplicationDAO.save(jobApplication);
						
						result.putField("status", "success");
						result.putField("message", "Added job id:"+jobId+" to job application "+applicationId);
						JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					}else{
						log.info("No job found with id "+jobId);
						result.putField("status", "error");
						result.putField("message", "No job found for id "+applicationId);
						JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					}
				}else{
					log.info("No application found with id "+applicationId);
					result.putField("status", "error");
					result.putField("message", "No application found for id "+applicationId);
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("Exception when getting application",e);
				result.putField("status", "error");
				result.putField("message", "Error when calling db");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			}
		}else{
			log.info("Parameter id was null for job or application.");
			result.putField("status", "fail");
			result.putField("message", "parameter id is missing for job or application");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
		}
		
	}
	
	
	@RESTMethod(alias="deny-workers.json", method="post")
	public void denyWorker(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException{
		log.info("Request for assign-workers.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		String[] applicationIdStrings =req.getParameterValues("application-id");
		
		if(applicationIdStrings!=null){
			for(String id:applicationIdStrings){
				
				try {
					BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJob(NumberUtils.toInt(id));
					
					
					if(jobApplication!=null){
						log.info(jobApplication.getJob());
						jobApplication.setStatus(ApplicationStatus.DENIED);
						businessJobApplicationDAO.save(jobApplication);
					}else{
						log.warn("No application with id: "+id);
					}
				} catch (SQLException e) {
					log.error("Exception when getting application",e);
					result.putField("status", "error");
					result.putField("message", "Error when calling db");
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					return;
				}
			}
			
			result.putField("status", "success");
			result.putField("message", "Denied applications");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			
		}else{
			log.info("Parameter application-id was null.");
			result.putField("status", "fail");
			result.putField("message", "parameter application-id is missing");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
		}
		
	}
	
	
	@RESTMethod(alias="save/applicationranking.json", method="post")
	public void saveApplicationRanking(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException{
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		Integer ranking = NumberUtils.toInt(req.getParameter("ranking"));
		
		if (appId != null) {
			BusinessSectorJobApplication app = businessJobApplicationDAO.getByIdWithJob(appId);
			if (ranking != null) {
				if (ranking.intValue() > 10) { ranking = 10; }
				else if (ranking.intValue() < 1) { ranking = 1; }
				app.setRanking(ranking);
				
				try {
					businessJobApplicationDAO.save(app);
				} catch (SQLException e) {
					log.error(e);
					result.putField("status", "fail");
					result.putField("message", "Could not update the ranking");
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					return;
				}
				result.putField("status", "success");
				result.putField("message", "Updated the ranking");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			}
		}
	}
}

