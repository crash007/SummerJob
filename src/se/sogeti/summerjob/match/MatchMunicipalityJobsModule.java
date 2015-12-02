package se.sogeti.summerjob.match;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.ApplicationType;
import se.sogeti.jobapplications.beans.CallStatus;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityMentor;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.jobapplications.daos.MuncipialityJobApplicationDAO;
import se.sogeti.periodsadmin.beans.AccountingEntry;
import se.sogeti.periodsadmin.beans.ContactPerson;
import se.sogeti.periodsadmin.beans.PlaceForInformation;
import se.sogeti.periodsadmin.beans.Salary;
import se.sogeti.periodsadmin.daos.AccountingEntryDAO;
import se.sogeti.periodsadmin.daos.ContactPersonDAO;
import se.sogeti.periodsadmin.daos.PlaceForInformationDAO;
import se.sogeti.periodsadmin.daos.SalaryDAO;
import se.sogeti.summerjob.DocxGenerator;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sogeti.summerjob.PDFGenerator;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.json.JsonObject;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class MatchMunicipalityJobsModule extends AnnotatedRESTModule{
	
	private JobDAO<MunicipalityJob> municipalityJobDAO;
	private MuncipialityJobApplicationDAO municipalityJobApplicationDAO;
	private SalaryDAO salaryDAO;
	private PlaceForInformationDAO placeForInformationDAO;
	private AccountingEntryDAO accountingDAO;
	private ContactPersonDAO contactDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);	
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);		

		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
		municipalityJobApplicationDAO = new MuncipialityJobApplicationDAO(dataSource, MunicipalityJobApplication.class, daoFactory);
		salaryDAO = new SalaryDAO(dataSource, Salary.class, daoFactory);
		placeForInformationDAO = new PlaceForInformationDAO(dataSource, PlaceForInformation.class, daoFactory);
		accountingDAO = new AccountingEntryDAO(dataSource, AccountingEntry.class, daoFactory);
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
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		
//		if(req.getParameter("jobId")!=null){
//			jobId = NumberUtils.toInt(req.getParameter("jobId"));
//		}
	
		if(jobId != null){
			MunicipalityJob job = municipalityJobDAO.getById(jobId);
			if(job != null){
				Element matchMunicipalityJobElement = doc.createElement("MatchMunicipalityJob");
				doc.getFirstChild().appendChild(matchMunicipalityJobElement);
				
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
				
				XMLUtils.append(doc, matchMunicipalityJobElement, job);
				
				Date bornBefore = null;
				
				if(job.getMustBeOverEighteen()){
					Calendar cal = Calendar.getInstance();
					cal.setTime(job.getPeriod().getStartDate());					
					cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 18);
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
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area1AndGeoArea3Candidates", area1AndGeoArea3Candidates);
				printCandidates(job.getId(), area1AndGeoArea3Candidates,"Area1AndGeoArea3");	

				
				//Third hand pick				
				List<MunicipalityJobApplication> area2AndGeoArea1 = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area2AndGeoArea1Candidates", area2AndGeoArea1);
				printCandidates(job.getId(), area2AndGeoArea1,"Area2AndGeoArea1");
				
				List<MunicipalityJobApplication> area2AndGeoArea2 = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea2(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area2AndGeoArea2Candidates", area2AndGeoArea2);
				printCandidates(job.getId(), area2AndGeoArea2,"Area2AndGeoArea2");
				
				// TODO Lägg in dessa nya kandidater på sidan
				List<MunicipalityJobApplication> area2AndGeoArea3 = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea3(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area2AndGeoArea3Candidates", area2AndGeoArea3);
				printCandidates(job.getId(), area2AndGeoArea3,"Area2AndGeoArea2");
				
				
				
				//Third hand pick				
				List<MunicipalityJobApplication> area3AndGeoArea1 = municipalityJobApplicationDAO.getCandidatesByPreferedArea3AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area3AndGeoArea1Candidates", area3AndGeoArea1);
				printCandidates(job.getId(), area3AndGeoArea1,"Area2AndGeoArea1");
				
				List<MunicipalityJobApplication> area3AndGeoArea2 = municipalityJobApplicationDAO.getCandidatesByPreferedArea3AndPreferedGeoArea2(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area3AndGeoArea2Candidates", area3AndGeoArea2);
				printCandidates(job.getId(), area3AndGeoArea2,"Area2AndGeoArea2");
				
				List<MunicipalityJobApplication> area3AndGeoArea3 = municipalityJobApplicationDAO.getCandidatesByPreferedArea3AndPreferedGeoArea3(job.getArea(), job.getGeoArea(), bornBefore, job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "Area3AndGeoArea3Candidates", area3AndGeoArea3);
				printCandidates(job.getId(), area3AndGeoArea3, "Area2AndGeoArea2");
				
				
				
				
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
		
		String[] applicationIdStrings = req.getParameterValues("application-id");
		
		if(applicationIdStrings!=null){
			for(String id:applicationIdStrings){
				
				try {
					MunicipalityJobApplication jobApplication = municipalityJobApplicationDAO.getById(NumberUtils.toInt(id));
					
					if(jobApplication!=null){
						jobApplication.setJob(null);
						jobApplication.setStatus(ApplicationStatus.NONE);
						jobApplication.setPersonalMentor(null);
						jobApplication.setPersonalMentorId(null);
						jobApplication.setCallStatus(CallStatus.NONE);
						jobApplication.setTimeForInformation(null);
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
						jobApplication.setPersonalMentor(null);
						jobApplication.setPersonalMentorId(null);
						jobApplication.setCallStatus(CallStatus.NONE);
						jobApplication.setTimeForInformation(null);
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
	
	@RESTMethod(alias="save/applicationoptions.json", method="post")
	public void saveApplicationOptions(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException {
		log.info("Request for save/applicationoptions.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		Integer applicationId = NumberUtils.toInt(req.getParameter("appId"));
		Integer mentorId = NumberUtils.toInt(req.getParameter("mentorId"));
		
		String timeForInfoString = StringUtils.isEmpty(req.getParameter("timeForInfo")) ? null : req.getParameter("timeForInfo");
		
		String callStatusString = req.getParameter("callStatus");
		CallStatus callStatus = null; 
		if (callStatusString.equals("READY_TO_BE_CALLED")) {
			callStatus = CallStatus.READY_TO_BE_CALLED;
		} else if (callStatusString.equals("HAS_BEEN_CALLED")) {
			callStatus = CallStatus.HAS_BEEN_CALLED;
		} else {
			callStatus = CallStatus.NONE;
		}
		
		
		if(jobId != null && applicationId != null) {
			try {
				log.debug("Getting application with id: " + applicationId);
				MunicipalityJobApplication jobApplication = municipalityJobApplicationDAO.getById(applicationId);
				MunicipalityJob job = municipalityJobDAO.getById(jobId);
				
				if(jobApplication != null) {
					if(job != null) {
						if (mentorId != null) {
							List<MunicipalityMentor> mentors = job.getMentors();
							if (mentors != null) {
								for (MunicipalityMentor mentor : mentors) {
									if (mentor.getId().intValue() == mentorId.intValue()) {
										mentor.setJob(job);
										jobApplication.setPersonalMentor(mentor);
										jobApplication.setPersonalMentorId(mentor.getId());
										break;
									}
								}
							}
						} else {
							jobApplication.setPersonalMentor(null);
							jobApplication.setPersonalMentorId(null);
						}
						
						jobApplication.setTimeForInformation(timeForInfoString);
						jobApplication.setCallStatus(callStatus);
						jobApplication.setJob(job);
						municipalityJobApplicationDAO.save(jobApplication);
						result.putField("status", "success");
						result.putField("message", "Added a personal mentor to application: " + applicationId);
						JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					} else {
						log.info("No job found with id " + jobId);
						result.putField("status", "error");
						result.putField("message", "No job found for id " + applicationId);
						JsonResponse.sendJsonResponse(result.toJson(), null, writer);
					}
				} else {
					log.info("No application found with id " + applicationId);
					result.putField("status", "error");
					result.putField("message", "No application found for id "  +applicationId);
					JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("Exception when getting application", e);
				result.putField("status", "error");
				result.putField("message", "Error when calling db");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			}
		} else {
			log.info("Parameter id was null for job or application.");
			result.putField("status", "fail");
			result.putField("message", "parameter id is missing for job or application");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
		}
	}
	
	@RESTMethod(alias="generateworkplacedocuments.json", method="post")
	public void generateWorkplaceDocuments(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		log.info("Request for generateworkplacedocument.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		
		if (jobId != null) {
			MunicipalityJob job = municipalityJobDAO.getByIdWithApplications(jobId);
			ContactPerson contact = contactDAO.getAll().get(0);
			
			File file = null;
			try {
				file = DocxGenerator.generateWorkplaceDocuments(job, contact);
			} catch (Exception e) {
				log.error("Could not generate the desired document.", e);
				e.printStackTrace();
				result.putField("status", "fail");
				result.putField("message", "Kunde inte generera det önskade dokumentet.");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				return;
			}
			
			Desktop.getDesktop().open(file);
			
		} else {
			result.putField("status", "fail");
			result.putField("message", "JobId is null");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			return;
		}
		result.putField("status", "success");
		result.putField("message", "Genererade det önskade dokumentet för annonsen med ID: " + jobId.intValue());
		JsonResponse.sendJsonResponse(result.toJson(), null, writer);
	}
	
	@RESTMethod(alias="generateemployeedocument.json", method="post")
	public void generateEmployeeDocument(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		log.info("Request for generateemployeedocument.json");
		
		PrintWriter writer = res.getWriter();
		JsonObject result = new JsonObject();
		JsonResponse.initJsonResponse(res, writer, null);
		
		Integer jobId = NumberUtils.toInt(req.getParameter("jobId"));
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		String selectValue = req.getParameter("selectedDocument");
		
		if (jobId != null && appId != null && !StringUtils.isEmpty(selectValue)) {
			MunicipalityJob job = municipalityJobDAO.getById(jobId);
			MunicipalityJobApplication app = municipalityJobApplicationDAO.getById(appId);
			Salary salary = !isOverEighteenDuringJob(app.getBirthDate(), job.getPeriod().getEndDate()) 
					? salaryDAO.getById(1) : salaryDAO.getById(2);
			
			PlaceForInformation place = placeForInformationDAO.getAll().get(0);
			AccountingEntry accounting = app.getApplicationType() == ApplicationType.PRIO ? accountingDAO.getById(1) : accountingDAO.getById(2);
			ContactPerson contact = contactDAO.getAll().get(0);
			
			File file = null;
			try {
				file = generateDocumentFromSelectValue(selectValue, job, app, salary.getAmountInSEK(),
						place.getName(), accounting, contact);
			} catch (Exception e) {
				log.error("Could not generate the desired document.", e);
				e.printStackTrace();
				result.putField("status", "fail");
				result.putField("message", "Kunde inte generera det önskade dokumentet.");
				JsonResponse.sendJsonResponse(result.toJson(), null, writer);
				return;
			}
			/**
			 * TODO Open fungerar endast om ingen annan också har samma/en tidigare generering
			 * öppen. Använda Open så de har möjlighet att redigera eller istället
			 * print som öppnar, skriver ut filen och sedan släpper den? 
			 */
			Desktop.getDesktop().open(file);
			
		} else {
			result.putField("status", "fail");
			result.putField("message", "JobId or AppId is null, or a document wasn't selected.");
			JsonResponse.sendJsonResponse(result.toJson(), null, writer);
			return;
		}
		result.putField("status", "success");
		result.putField("message", "Genererade det önskade dokumentet för ansökan med ID: " + appId.intValue());
		JsonResponse.sendJsonResponse(result.toJson(), null, writer);
	}
	
	private File generateDocumentFromSelectValue(String value, MunicipalityJob job, MunicipalityJobApplication app,
			int salary, String placeForInfo, AccountingEntry accounting, ContactPerson contact) throws IOException, Exception {
		
		File file = null;
		
		switch (value) {
		case "ALL":
			file = PDFGenerator.generateEmployeeDocuments(job, app, salary, placeForInfo, accounting, contact);
			break;

		case "kallelse":
			file = PDFGenerator.generateCallDocument(job, app, placeForInfo, contact);
			break;
			
		case "bekraftelse":
			file = PDFGenerator.generateConfirmationDocument(job, app, salary, accounting);
			break;
			
		case "anstallningsbevis":
			file = PDFGenerator.generateProofOfEmployment(job, app, app.getPersonalMentor(), salary);
			break;
			
		case "tjanstgoringsrapport":
			file = PDFGenerator.generateTimeReport(job, app, accounting);
			break;
			
		case "bank":
			file = PDFGenerator.generateBankDocument(app, contact);
			break;
			
		case "skattebefrielse":
			file = PDFGenerator.generateTaxDocument(app);
			break;
			
		case "belastningsregister":
			file = PDFGenerator.generatePoliceDocument(app);
			break;
			
		default:
			break;
		}
		
		return file;
	}
	
	private boolean isOverEighteenDuringJob(Date birthDate, Date jobEndDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);					
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 18);
		Date eighteenthBirthday = cal.getTime();
		
		int value = eighteenthBirthday.compareTo(jobEndDate);
		return value <= 0;
	}
}

