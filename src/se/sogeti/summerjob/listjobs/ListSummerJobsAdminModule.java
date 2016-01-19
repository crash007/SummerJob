package se.sogeti.summerjob.listjobs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.match.MatchBusinessJobHandler;
import se.sogeti.summerjob.match.MatchMunicipalityJobHandler;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.dao.OrderByCriteria;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ListSummerJobsAdminModule extends AnnotatedForegroundModule implements ListSummerJobsHandler{
	
	JobDAO<MunicipalityJob> municipalityJobDAO;
	JobDAO<BusinessSectorJob> businessSectorJobDAO;
	GeoAreaDAO geoAreaDAO;
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera kommunala sommarjobb",name="ManageMunicipalityJob")
	private String manageMunicipalityJobUrl="manage-municipality-job";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till att hantera näringslivssommarjobb",name="ManageBusinessJob")
	private String manageBusinessJobUrl="manage-businesssector-job";
	
	@InstanceManagerDependency(required=false)
	private MatchBusinessJobHandler matchBusinessJobHandler;
	
	@InstanceManagerDependency(required=false)
	private MatchMunicipalityJobHandler matchMunicipalityJobHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		municipalityJobDAO = new JobDAO<MunicipalityJob>(dataSource, MunicipalityJob.class, daoFactory);
		businessSectorJobDAO = new JobDAO<BusinessSectorJob>(dataSource, BusinessSectorJob.class, daoFactory);
		
		geoAreaDAO = new GeoAreaDAO(dataSource, GeoArea.class, daoFactory);
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

		boolean showMunicipality=true;
		boolean showBusiness=true;
		
		String contextPath = req.getContextPath();
		
		if(req.getParameter("municipality") !=null && req.getParameter("municipality").equalsIgnoreCase("true")){
			showBusiness=false;
		}
		
		if(req.getParameter("business") !=null && req.getParameter("business").equalsIgnoreCase("true")){
			showMunicipality=false;
		}
		
		if (showMunicipality) {
			
			Element municipalityJobs = doc.createElement("MunicipalityJobs");
			
			Element municipalityOpen = doc.createElement("MunicipalityOpen");
			Element municipalityUncontrolled = doc.createElement("MunicipalityUncontrolled");
			Element municipalityFinished = doc.createElement("MunicipalityFinished");
			Element municipalityDisapproved = doc.createElement("MunicipalityDisapproved");
			municipalityJobs.appendChild(municipalityOpen);
			municipalityJobs.appendChild(municipalityUncontrolled);
			municipalityJobs.appendChild(municipalityFinished);
			municipalityJobs.appendChild(municipalityDisapproved);
			

			List<MunicipalityJob> openJobs = null;
			List<MunicipalityJob> uncontrolledJobs = null;
			List<MunicipalityJob> closedJobs = null;
			List<MunicipalityJob> disapprovedJobs = null;
			
			//Verksamhetsområde
			List<OrderByCriteria<MunicipalityJob>> orderByCriterias = new ArrayList<>();
			OrderByCriteria<MunicipalityJob> jobAreaOrder = municipalityJobDAO.getOrderByCriteria("area", Order.ASC);
			orderByCriterias.add(jobAreaOrder);
			
			//Arbetsplats
			OrderByCriteria<MunicipalityJob> locationOrder = municipalityJobDAO.getOrderByCriteria("location", Order.ASC);
			orderByCriterias.add(locationOrder);
			
			//Arbetsplats
			OrderByCriteria<MunicipalityJob> departmentOrder = municipalityJobDAO.getOrderByCriteria("department", Order.ASC);
			orderByCriterias.add(departmentOrder);
			
			if(user.isAdmin()){
				openJobs = municipalityJobDAO.getAllControlledAndApprovedAndOpen(orderByCriterias);				
				uncontrolledJobs = municipalityJobDAO.getAllUncontrolled(orderByCriterias);
				closedJobs = municipalityJobDAO.getAllControlledAndClosed(orderByCriterias);
				disapprovedJobs = municipalityJobDAO.getAllControlledAndDisapproved(orderByCriterias);
			}else{
				openJobs = municipalityJobDAO.getAllControlledAndOpenAddedByUsername(user.getUsername(),orderByCriterias);
				uncontrolledJobs = municipalityJobDAO.getAllUncontrolledAddedByUsername(user.getUsername(),orderByCriterias);
				closedJobs = municipalityJobDAO.getAllControlledAndClosedAddedByUsername(user.getUsername(),orderByCriterias);
				disapprovedJobs = municipalityJobDAO.getAllControlledAndDisapprovedAddedByUsername(user.getUsername(),orderByCriterias);
			}
			
			if (openJobs != null) {
				for (MunicipalityJob job : openJobs) {
					municipalityOpen.appendChild(createMunicipalityJobElement(doc, job, contextPath));
				}
			}
			
			if (uncontrolledJobs != null) {
				for (MunicipalityJob job : uncontrolledJobs) {
					municipalityUncontrolled.appendChild(createMunicipalityJobElement(doc, job, contextPath));
				}
			}
			
			if (closedJobs != null) {
				for (MunicipalityJob job : closedJobs) {
					municipalityFinished.appendChild(createMunicipalityJobElement(doc, job, contextPath));
				}
			}
			
			if (disapprovedJobs != null) {
				for (MunicipalityJob job : disapprovedJobs) {
					municipalityDisapproved.appendChild(createMunicipalityJobElement(doc, job, contextPath));
				}
			}
			
			doc.getFirstChild().appendChild(municipalityJobs);
		
		} 

		if(showBusiness){
			
			Element businessJobs = doc.createElement("BusinessJobs");
			
			Element businessOpen = doc.createElement("BusinessOpen");
			Element businessUncontrolled = doc.createElement("BusinessUncontrolled");
			Element businessFinished = doc.createElement("BusinessFinished");
			Element businessDisapproved = doc.createElement("BusinessDisapproved");
			businessJobs.appendChild(businessOpen);
			businessJobs.appendChild(businessUncontrolled);
			businessJobs.appendChild(businessFinished);
			businessJobs.appendChild(businessDisapproved);
			
			List<BusinessSectorJob> openJobs = null;
			List<BusinessSectorJob> uncontrolledJobs = null;
			List<BusinessSectorJob> closedJobs = null;
			List<BusinessSectorJob> disapprovedJobs = null; 
			
			if (user.isAdmin()) {
				openJobs = businessSectorJobDAO.getAllControlledAndApprovedAndOpen();
				uncontrolledJobs = businessSectorJobDAO.getAllUncontrolled();
				closedJobs = businessSectorJobDAO.getAllControlledAndClosedAndApproved();
				disapprovedJobs = businessSectorJobDAO.getAllControlledAndDisapproved(null);
			} else {
				openJobs = businessSectorJobDAO.getAllControlledAndOpenAddedByUsername(user.getUsername(), null);
				uncontrolledJobs = businessSectorJobDAO.getAllUncontrolledAddedByUsername(user.getUsername(), null);
				closedJobs = businessSectorJobDAO.getAllControlledAndClosedAddedByUsername(user.getUsername(), null);
				disapprovedJobs = businessSectorJobDAO.getAllControlledAndDisapprovedAddedByUsername(user.getUsername(), null);
			}
			
			if (openJobs != null) {
				for (BusinessSectorJob job : openJobs) {
					businessOpen.appendChild(createBusinessJobElement(doc, job, contextPath));
				}
			}
			
			if (uncontrolledJobs != null) {
				for (BusinessSectorJob job : uncontrolledJobs) {
					businessUncontrolled.appendChild(createBusinessJobElement(doc, job,contextPath));
				}
			}
			
			if (closedJobs != null) {
				for (BusinessSectorJob job : closedJobs) {
					businessFinished.appendChild(createBusinessJobElement(doc, job,contextPath));
				}
			}
			
			if (disapprovedJobs != null) {
				for (BusinessSectorJob job : disapprovedJobs) {
					businessDisapproved.appendChild(createBusinessJobElement(doc, job,contextPath));
				}
			}
			
			doc.getFirstChild().appendChild(businessJobs);
		}
		
		if (user != null) {
			XMLUtils.appendNewElement(doc, element, "IsAdmin", user.isAdmin());
		}
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	private Element createMunicipalityJobElement(Document doc, MunicipalityJob job,String contextPath) {
		Element municipalityJob = doc.createElement("MunicipalityJob");
//		XMLUtils.appendNewElement(doc, municipalityJob, "workTitle", job.getWorkTitle());
		XMLUtils.appendNewElement(doc, municipalityJob, "workDescription", job.getWorkDescription());
		XMLUtils.appendNewElement(doc, municipalityJob, "area", job.getArea().getName());
		
		XMLUtils.appendNewElement(doc, municipalityJob, "period", job.getPeriod().getName());
		XMLUtils.appendNewElement(doc, municipalityJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
		
		XMLUtils.appendNewElement(doc, municipalityJob, "organization", job.getOrganization());
		XMLUtils.appendNewElement(doc, municipalityJob, "administration", job.getAdministration());
		XMLUtils.appendNewElement(doc, municipalityJob, "department", job.getDepartment());
		XMLUtils.appendNewElement(doc, municipalityJob, "location", job.getLocation());
		
		XMLUtils.appendNewElement(doc, municipalityJob, "created", job.getCreated());
		XMLUtils.appendNewElement(doc, municipalityJob, "approvedByUser", job.getApprovedByUser());
		XMLUtils.appendNewElement(doc, municipalityJob, "initiatedByUser", job.getInitiatedByUser());
		XMLUtils.appendNewElement(doc, municipalityJob, "controlledDate", job.getControlledDate());
		XMLUtils.appendNewElement(doc, municipalityJob, "url", manageMunicipalityJobUrl+"?jobId=" + job.getId());
		
		if(matchMunicipalityJobHandler!=null){
		XMLUtils.appendNewElement(doc, municipalityJob, "matchURL", contextPath +matchMunicipalityJobHandler.getUrl()+ "?jobId=" + job.getId());
		}
		
		if (job.getApplications() != null) {
			Integer matchedApplications = FormUtils.countApplications(job.getApplications(), ApplicationStatus.MATCHED);			
			job.setMatchedApplications(matchedApplications);
			XMLUtils.appendNewElement(doc, municipalityJob, "matchedApplications", job.getMatchedApplications());
		
		} else {
			XMLUtils.appendNewElement(doc, municipalityJob, "matchedApplications", 0);
		}
		
		return municipalityJob;
	}
	
	private Element createBusinessJobElement(Document doc, BusinessSectorJob job, String contextPath) throws SQLException {
		Element businessSectorJob = doc.createElement("BusinessJob");
		
		XMLUtils.appendNewElement(doc, businessSectorJob, "company", job.getCompany());
		XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
		XMLUtils.appendNewElement(doc, businessSectorJob, "workDescription", job.getWorkDescription());
		XMLUtils.appendNewElement(doc, businessSectorJob, "dates", job.getStartDate() + " - " + job.getEndDate());
		XMLUtils.appendNewElement(doc, businessSectorJob, "corporateNumber", job.getCorporateNumber());
		XMLUtils.appendNewElement(doc, businessSectorJob, "lastApplicationDay", job.getLastApplicationDay());
		XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
		XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
		XMLUtils.appendNewElement(doc, businessSectorJob, "approvedByUser", job.getApprovedByUser());
		XMLUtils.appendNewElement(doc, businessSectorJob, "initiatedByUser", job.getInitiatedByUser());
		XMLUtils.appendNewElement(doc, businessSectorJob, "controlledDate", job.getControlledDate());
		XMLUtils.appendNewElement(doc, businessSectorJob, "url", manageBusinessJobUrl+"?jobId=" + job.getId());
		
		if(matchBusinessJobHandler!=null){
			XMLUtils.appendNewElement(doc, businessSectorJob, "matchURL", contextPath +matchBusinessJobHandler.getUrl() + "?jobId=" + job.getId());
		}
		BusinessSectorJob jobWithApplications = businessSectorJobDAO.getByIdWithApplications(job.getId());
		
		if (jobWithApplications != null && jobWithApplications.getApplications() != null) {
			Integer matchedApplications = FormUtils.countApplications(jobWithApplications.getApplications(), ApplicationStatus.MATCHED);
			job.setOpenApplications(job.getNumberOfWorkersNeeded() - matchedApplications);
			job.setMatchedApplications(matchedApplications);
			XMLUtils.appendNewElement(doc, businessSectorJob, "matchedApplications", job.getMatchedApplications());
		} 
		else {
			XMLUtils.appendNewElement(doc, businessSectorJob, "matchedApplications", 0);
		}
		
		return businessSectorJob;
	}

	@Override
	public String getMunicipalityJobsUrl() {
		return this.getFullAlias()+"?municipality=true";
	}

	@Override
	public String getBusinessJobsUrl() {		
		return this.getFullAlias()+"?business=true";
	}

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface,
			DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.init(moduleDescriptor, sectionInterface, dataSource);

		if(!systemInterface.getInstanceHandler().addInstance(ListSummerJobsHandler.class, this)){
			throw new RuntimeException("Unable to register module in global instance handler using key " +ListSummerJobsHandler.class.getSimpleName() + ", another instance is already registered using this key.");
		}
	}

	@Override
	public void unload() throws Exception {
		
		if(this.equals(systemInterface.getInstanceHandler().getInstance(ListSummerJobsHandler.class))){
			log.info("Unloading ListSummerJobsHandler from instanceHandler.");
			systemInterface.getInstanceHandler().removeInstance(ListSummerJobsHandler.class);
		}	
		super.unload();
	}
}
