package se.sogeti.summerjob.addsummerjobapplication;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.summerjob.FormUtils;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class BusinessSectorSummerJobApplicationModule extends AnnotatedRESTModule{
	
	
	private JobApplicationDAO<BusinessSectorJobApplication> jobApplicationDAO;
	private BusinessSectorJobDAO jobDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		jobApplicationDAO = new JobApplicationDAO<BusinessSectorJobApplication>(dataSource, BusinessSectorJobApplication.class, hierarchyDaoFactory);
		jobDAO = new BusinessSectorJobDAO(dataSource, BusinessSectorJob.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		Integer jobId=null;
		
		if(req.getParameter("jobId")!=null){
		 jobId = NumberUtils.toInt(req.getParameter("jobId"));
		}
		
		if(req.getMethod().equals("POST") && jobId!=null){
			log.info("POST");
			
			BusinessSectorJob job = jobDAO.getByIdWithApplications(jobId);
			if(job!=null){
							
				BusinessSectorJobApplication app = new BusinessSectorJobApplication();
				Citizen person=null;
				try{
					person = smexServiceHandler.getCitizen(req.getParameter("socialSecurityNumber"));
				}catch (SmexServiceException e){
					log.error(e);
				}
				
				FormUtils.createJobApplication(app, req, person);
				
				boolean hasDriversLicense = req.getParameter("hasDriversLicense") != null ? true : false;				
				app.setHasDriversLicense(hasDriversLicense);
				
				//Worker applies for a job but has  not yet got the job.
				app.setAssigned(false);
				
				if (hasDriversLicense) {
					Integer typeId = NumberUtils.toInt(req.getParameter("driversLicenseType"));
		        	
		        	if (typeId != null) {
		        		DriversLicenseType licenseType = driversLicenseTypeDAO.getTypeById(typeId);
		        		app.setDriversLicenseType(licenseType);
		        	}
				}
				if(job.getApplications()!=null){
					job.getApplications().add(app);
				}else{
					List<BusinessSectorJobApplication> appliedApplications = new ArrayList<BusinessSectorJobApplication>();
					appliedApplications.add(app);
					job.setApplications(appliedApplications);
				}
				
				log.info(app);
				jobDAO.update(job);
				
			}
		
		}
		
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		
		
		
		if(req.getParameter("jobId")!=null){
			
			if(jobId!=null){
				BusinessSectorJob job =jobDAO.getById(jobId);
				log.info(job);
				Element jobInfo = doc.createElement("JobInfo");
				XMLUtils.append(doc, jobInfo,job);
				doc.getFirstChild().appendChild(jobInfo);
				
				Element jobApplication = doc.createElement("JobApplicationForm");
				Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
				List<DriversLicenseType> driverslicenseTypes = driversLicenseTypeDAO.getAll();
				jobApplication.appendChild(driversLicenseElement);
				XMLUtils.append(doc, driversLicenseElement, driverslicenseTypes);
				
				doc.getFirstChild().appendChild(jobApplication);	
			}
			
		}else{
			Element jobList = doc.createElement("JobList");
			List<BusinessSectorJob> jobs = jobDAO.getAllApproved();		
			XMLUtils.append(doc, jobList,jobs);
			doc.getFirstChild().appendChild(jobList);
			
		}
		
		
		
		/*
		Element areasElement = doc.createElement("Areas");
		List<MunicipalityJobArea> areas = areaDAO.getAll();		
		XMLUtils.append(doc, areasElement,areas);
		form.appendChild(areasElement);	
		
		Element geoAreasElement = doc.createElement("GeoAreas");
		List<GeoArea> geoAreas = geoAreaDAO.getAll();
		XMLUtils.append(doc, geoAreasElement, geoAreas);
		form.appendChild(geoAreasElement);
		*/
		
		return new SimpleForegroundModuleResponse(doc);
		
	}
	
}
