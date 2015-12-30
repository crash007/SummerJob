package se.sogeti.summerjob.match;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.PersonApplications;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.cv.CvServiceHander;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobApplicationDAO;
import se.sogeti.jobapplications.daos.PersonApplicationsDAO;
import se.sogeti.periodsadmin.beans.ContactPerson;
import se.sogeti.periodsadmin.daos.ContactPersonDAO;
import se.sogeti.summerjob.DocxGenerator;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sogeti.summerjob.PDFGenerator;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.json.JsonObject;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class MatchBusinessJobsModule extends MatchCommon {
	

	private BusinessSectorJobDAO businessJobDAO;
	private BusinessSectorJobApplicationDAO businessJobApplicationDAO;
	private PersonApplicationsDAO personApplicationsDAO;
	private MunicipalityJobApplicationDAO municipalityJobApplicationDAO;
	private ContactPersonDAO contactDAO;
	
	@InstanceManagerDependency(required=true)
	private CvServiceHander cvServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		businessJobDAO = new BusinessSectorJobDAO(dataSource, BusinessSectorJob.class, daoFactory);
		businessJobApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, daoFactory);
		personApplicationsDAO = new PersonApplicationsDAO(dataSource, PersonApplications.class, daoFactory);
		municipalityJobApplicationDAO = new MunicipalityJobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
		contactDAO = new ContactPersonDAO(dataSource, ContactPerson.class, daoFactory);
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
		Boolean generateWorkplaceDocuments = BooleanUtils.toBoolean(req.getParameter("generateWorkDocument"));
		
		if(req.getParameter("jobId")!=null){
			jobId = NumberUtils.toInt(req.getParameter("jobId"));
		}
		
		if (jobId != null && generateWorkplaceDocuments) {
			generateWorkplaceDocuments(res, jobId);
		}
	
		if(jobId!=null){
			BusinessSectorJob job = businessJobDAO.getByIdWithApplications(jobId);
			if(job!=null){
				Element matchBusinessJobElement = doc.createElement("MatchBusinessJob");
				doc.getFirstChild().appendChild(matchBusinessJobElement);
				
				XMLUtils.appendNewElement(doc, element, "hasPastLastApplicationDay", 
						job.getLastApplicationDay().before(new Date(Calendar.getInstance().getTimeInMillis())));

				if(job.getApplications()!=null){
					Integer matchedApplications = FormUtils.countApplications(job.getApplications(), ApplicationStatus.MATCHED);
					job.setMatchedApplications(matchedApplications);
					job.setOpenApplications(job.getNumberOfWorkersNeeded() - matchedApplications);
					
				}else{
					job.setOpenApplications(job.getNumberOfWorkersNeeded());
					job.setMatchedApplications(0);					
				}
				
				XMLUtils.append(doc, matchBusinessJobElement, job);
			
				
				//sort applications by drivers license and if over 18
				//First hand pick				
				List<BusinessSectorJobApplication> goodCandidates = personApplicationsDAO.getBusinessCandidatesFulfillingCriteras(businessJobApplicationDAO, municipalityJobApplicationDAO,job);
				if(goodCandidates!=null && !goodCandidates.isEmpty()){
					for(BusinessSectorJobApplication candidate: goodCandidates){
						log.info(candidate);
					}
					
					XMLUtils.append(doc, matchBusinessJobElement, "GoodCandidates", goodCandidates);
				}else{
					log.info("No candidates fulling job criteras");
				}
				
				
				List<BusinessSectorJobApplication> badCandidates = personApplicationsDAO.getBusinessCandidatesNotFulfillingCriteras(businessJobApplicationDAO, municipalityJobApplicationDAO, job);
				
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
					BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJobAndPersonApplications(NumberUtils.toInt(id));
					
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

		String[] applicationIdStrings =req.getParameterValues("application-id");

		if(applicationIdStrings!=null){
			for(String appIdStr:applicationIdStrings){
				Integer applicationId = NumberUtils.toInt(appIdStr);


				if(applicationId!=null){
					try {
						log.debug("Getting application with id: "+applicationId);
						BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJobAndPersonApplications(applicationId);


						if(jobApplication!=null){

							jobApplication.setStatus(ApplicationStatus.MATCHED);

							businessJobApplicationDAO.save(jobApplication);

							result.putField("status", "success");
							result.putField("message", "Appliction with id="+applicationId+" is changed to matched");
							JsonResponse.sendJsonResponse(result.toJson(), null, writer);

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
					log.info("Parameter application-id is not a number for value="+appIdStr+".");
					result.putField("status", "fail");
					result.putField("message", "parameter application-id is not a number for "+appIdStr+".");
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				}
			}

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
					BusinessSectorJobApplication jobApplication = businessJobApplicationDAO.getByIdWithJobAndPersonApplications(NumberUtils.toInt(id));
					
					
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
			BusinessSectorJobApplication app = businessJobApplicationDAO.getByIdWithJobAndPersonApplications(appId);
			if (ranking != null) {
				if (ranking.intValue() > 10) { ranking = 10; }
				else if (ranking.intValue() < 1) { ranking = 1; }
				app.setRanking(ranking);
				
				try {
					businessJobApplicationDAO.save(app);
				} catch (SQLException e) {
					log.error(e);
					result.putField("status", "fail");
					result.putField("message", "Could not update the ranking for application with id="+appId);
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					return;
				}
				result.putField("status", "success");
				result.putField("message", "Updated the ranking");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			}
		}
	}
	
	@RESTMethod(alias="adjustjobstatus.json", method="post")
	public void adjustJobStatus(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException{
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		String newStatusString = req.getParameter("newStatus");
		
		if (jobId != null && !StringUtils.isEmpty(newStatusString)) {
			BusinessSectorJob job = businessJobDAO.getByIdWithApplications(jobId);
			Boolean newStatus = BooleanUtils.toBoolean(newStatusString);
			job.setIsOpen(newStatus);
			
			try {
				businessJobDAO.save(job);
			} catch (SQLException e){
				log.error("Database error while trying to change status on this job.", e);
				result.putField("status", "fail");
				result.putField("message", "Database error while changing job status.");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				return;
			}
			
			result.putField("status", "success");
			result.putField("message", "Changed the status on this job");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
		}
	}
	
	public void generateWorkplaceDocuments(HttpServletResponse res, int jobId) throws SQLException, IOException {
		BusinessSectorJob job = businessJobDAO.getById(jobId);
		ContactPerson contact = contactDAO.getAll().get(1);
		String faviContactInfo = contact.getName() + ", " + contact.getPhoneNumber();
		PDFGenerator pdfGenerator = new PDFGenerator();
		
		File file = null;
		
		file = pdfGenerator.generateBusinessSectorJobAgreementDocument(templateFilePath, newFilePath, job, faviContactInfo);
		
		FileInputStream inStream = new FileInputStream(file);
		
		String mimeType = "application/pdf";
		res.setContentType(mimeType);
		res.setContentLength((int) file.length());
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
		res.setHeader(headerKey, headerValue);
		
		OutputStream outStream = res.getOutputStream();
		
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		
		inStream.close();
		outStream.close();
	}
}

