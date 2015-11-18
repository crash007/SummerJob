package se.sogeti.summerjob.match;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.jobapplications.daos.MuncipialityJobApplicationDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.json.JsonObject;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class MatchSummerJobsModule extends AnnotatedRESTModule{
	

	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private MuncipialityJobApplicationDAO municipalityJobApplicationDAO;
	private JobDAO<BusinessSectorJob> businessJobDAO;
	private BusinessSectorJobApplicationDAO businessJobApplicationDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
		municipalityJobApplicationDAO = new MuncipialityJobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
		businessJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, daoFactory);
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
		
		Integer jobId=null;
		
		if(req.getParameter("jobId")!=null){
		 jobId = NumberUtils.toInt(req.getParameter("jobId"));
		}
	
		if(jobId!=null){
			MunicipalityJob job = municipalityJobDAO.getById(jobId);
			if(job!=null){
				Element matchMunicipalityJobElement = doc.createElement("MatchMunicipalityJob");
				doc.getFirstChild().appendChild(matchMunicipalityJobElement);
				
				if(job.getApplications()!=null){
					
					job.setMatchedApplications(FormUtils.countApplications(job.getApplications(), ApplicationStatus.MATCHED));
					//job.setAssignedApplications(FormUtils.countApplications(job.getApplications(), ApplicationStatus.ASSIGNED));
					job.setOpenApplications(job.getNumberOfWorkersNeeded()-FormUtils.countApplications(job.getApplications(), ApplicationStatus.MATCHED));
					//XMLUtils.append(doc, doc.createElement("AppointedApplications"), job.getApplications());
					
				}else{
					job.setOpenApplications(job.getNumberOfWorkersNeeded());
					job.setMatchedApplications(0);
					
				}
				
				XMLUtils.append(doc, matchMunicipalityJobElement, job);
				
				Date bornBefore =null;
				
				if(job.getMustBeOverEighteen()){
					Calendar cal = Calendar.getInstance();
					cal.setTime(job.getPeriod().getStartDate());					
					cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
					bornBefore = cal.getTime();
				}
				
				//First hand pick				
				List<MunicipalityJobApplication> area1AndGeoArea1Candidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area1AndGeoArea1Candidates", area1AndGeoArea1Candidates);
				printCandidates(job.getId(), area1AndGeoArea1Candidates,"Area1AndGeoArea1");	
				
				//Second hand pick				
				List<MunicipalityJobApplication> area1AndGeoArea2Candidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea2(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area1AndGeoArea2Candidates", area1AndGeoArea2Candidates);
				printCandidates(job.getId(), area1AndGeoArea2Candidates,"Area1AndGeoArea2");	
				
				List<MunicipalityJobApplication> area1AndGeoArea3Candidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea3(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area1AndGeoArea2Candidates", area1AndGeoArea3Candidates);
				printCandidates(job.getId(), area1AndGeoArea3Candidates,"Area1AndGeoArea3");	

				
				//Third hand pick				
				List<MunicipalityJobApplication> area2AndGeoArea1 = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area2AndGeoArea1Candidates", area2AndGeoArea1);
				printCandidates(job.getId(), area2AndGeoArea1,"Area2AndGeoArea1");
				
				List<MunicipalityJobApplication> area2AndGeoArea2 = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea2(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area2AndGeoArea2Candidates", area2AndGeoArea2);
				printCandidates(job.getId(), area2AndGeoArea1,"Area2AndGeoArea2");
				
				//Third hand pick
				List<MunicipalityJobApplication> anyAreaAndGeoArea1Candidates = municipalityJobApplicationDAO.getCandidatesByNoPreferedAreaAndPreferedGeoArea1(job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "AnyAreaAndGeoArea1Candidates", anyAreaAndGeoArea1Candidates);
				printCandidates(job.getId(), anyAreaAndGeoArea1Candidates,"AnyAreaAndGeoArea1");
			
				List<MunicipalityJobApplication> anyAreaAndGeoArea2Candidates = municipalityJobApplicationDAO.getCandidatesByNoPreferedAreaAndPreferedGeoArea2(job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "AnyAreaAndGeoArea2Candidates", anyAreaAndGeoArea2Candidates);
				printCandidates(job.getId(), anyAreaAndGeoArea1Candidates,"AnyAreaAndGeoArea2");
				
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
					MunicipalityJobApplication jobApplication = municipalityJobApplicationDAO.getById(NumberUtils.toInt(id));
					
					if(jobApplication!=null){
						jobApplication.setJob(null);
						jobApplication.setStatus(ApplicationStatus.NONE);
						municipalityJobApplicationDAO.save(jobApplication);
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
				MunicipalityJobApplication jobApplication = municipalityJobApplicationDAO.getById(applicationId);
				MunicipalityJob job = municipalityJobDAO.getById(jobId);
				
				if(jobApplication!=null){
					if(job!=null){
						jobApplication.setStatus(ApplicationStatus.MATCHED);
						jobApplication.setJob(job);
						municipalityJobApplicationDAO.save(jobApplication);
						
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
			log.info("Paramter id was null for job or application.");
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
					MunicipalityJobApplication jobApplication = municipalityJobApplicationDAO.getByIdWithJob(NumberUtils.toInt(id));
					
					
					if(jobApplication!=null){
						log.info(jobApplication.getJob());
						jobApplication.setStatus(ApplicationStatus.DENIED);
						municipalityJobApplicationDAO.save(jobApplication);
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
	private void printCandidates(Integer jobId, List<MunicipalityJobApplication> candidates, String prio) {
		if(candidates!=null){	
			log.info(prio+" pick candidates for job "+jobId);
			for(MunicipalityJobApplication app:candidates){						
				log.info(app);					
			}
		}else{
			log.info("No "+prio+" pick candidates found for job:"+jobId);
			
		}
	}
}

