package se.sogeti.summerjob.match;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.jobapplications.daos.MuncipialityJobApplicationDAO;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class MatchSummerJobsModule extends AnnotatedForegroundModule{
	

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
					
					job.setAppointedApplications(job.getApplications().size());
					job.setOpenApplications(job.getNumberOfWorkersNeeded()-job.getApplications().size());
					XMLUtils.append(doc, doc.createElement("AppointedApplications"), job.getApplications());
				}else{
					job.setOpenApplications(job.getNumberOfWorkersNeeded());
				}
				
				XMLUtils.append(doc, matchMunicipalityJobElement, job);
				
				//First hand pick
				
				List<MunicipalityJobApplication> firstCandidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), job.getIsOverEighteen(), job.getDriversLicenseType());
				XMLUtils.append(doc, matchMunicipalityJobElement, "MunicipalityApplicationFirstPickCandidates", firstCandidates);
				printCandidates(job.getId(), firstCandidates,"first");	
				
				//Second hand pick				
				List<MunicipalityJobApplication> secondCandidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea2(job.getArea(), job.getGeoArea(), job.getIsOverEighteen(), job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "MunicipalityApplicationSecondPickCandidates", secondCandidates);
				printCandidates(job.getId(), secondCandidates,"second");	
				
				//Third hand pick				
				List<MunicipalityJobApplication> thirdCandidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea2AndPreferedGeoArea1(job.getArea(), job.getGeoArea(), job.getIsOverEighteen(), job.getDriversLicenseType());				
				XMLUtils.append(doc, matchMunicipalityJobElement, "MunicipalityApplicationThirdPickCandidates", thirdCandidates);
				printCandidates(job.getId(), thirdCandidates,"third");
				
			}else{
				log.warn("No job with id "+jobId+" found.");
			}
			
		}else{
			log.warn("jobId is missing");
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}

	private void printCandidates(Integer jobId, List<MunicipalityJobApplication> candidates, String prio) {
		if(candidates!=null){	
			log.info(prio+" pcik candidates for job "+jobId);
			for(MunicipalityJobApplication app:candidates){						
				log.info(app);					
			}
		}else{
			log.info("No "+prio+" pick candidates found for job:"+jobId);
			
		}
	}

	
}

