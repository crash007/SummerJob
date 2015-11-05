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
				
				Element candidatesElem = doc.createElement("MunicipalityApplicationCandidates");
				List<MunicipalityJobApplication> candidates = municipalityJobApplicationDAO.getCandidatesByPreferedArea1AndPreferedGeoArea1(job.getArea(), job.getGeoArea());
				if(candidates!=null){
					log.info("Candidates found");
					for(MunicipalityJobApplication app:candidates){
						
						log.info(app);
						log.info(app.getDriversLicenseType());
					}
					XMLUtils.append(doc, candidatesElem, candidates);
					matchMunicipalityJobElement.appendChild(candidatesElem);
					
				}else{
					log.info("No candidates found for job "+job);
					
				}
				
			}else{
				log.warn("No job with id "+jobId+" found.");
			}
			
		}else{
			log.warn("jobId is missing");
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}

	
}

