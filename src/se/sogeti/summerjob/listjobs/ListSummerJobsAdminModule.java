package se.sogeti.summerjob.listjobs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class ListSummerJobsAdminModule extends AnnotatedForegroundModule {
	
	JobDAO<MunicipalityJob> municipalityJobDAO;
	JobDAO<BusinessSectorJob> businessSectorJobDAO;
	GeoAreaDAO geoAreaDAO;
	
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
		
		Element headingElement = doc.createElement("Heading");
		Element controlledJobsElement = doc.createElement("ControlledJobs");
		Element uncontrolledJobsElement = doc.createElement("UncontrolledJobs");
		Element controlledDisapprovedJobsElement = doc.createElement("ControlledDisapprovedJobs");

		Boolean showMunicipalityJobs = BooleanUtils.toBoolean(req.getParameter("showMunicipalityJobs"));
		if (showMunicipalityJobs != null && showMunicipalityJobs) {
			XMLUtils.appendNewElement(doc, headingElement, "title", "Interna sommarjobb (Kommunala)");

			List<MunicipalityJob> controlledJobs = municipalityJobDAO.getAllControlledAndApproved();
			if (controlledJobs != null) {
				for (MunicipalityJob job : controlledJobs) {
					Element businessSectorJob = doc.createElement("ApprovedJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					Period period = job.getPeriod();
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", period.getName() + " (" + period.getStartDate() + " - " + period.getEndDate() + ")");
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "approvedByUser", job.getApprovedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "controlledDate", job.getControlledDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "view_municipalityjob_" + job.getId());
					controlledJobsElement.appendChild(businessSectorJob);
				}
			}
			
			List<MunicipalityJob> uncontrolledJobs = municipalityJobDAO.getAllUncontrolled();
			if (uncontrolledJobs != null) {
				for (MunicipalityJob job : uncontrolledJobs) {
					Element businessSectorJob = doc.createElement("UncontrolledJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					Period period = job.getPeriod();
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", period.getName() + " (" + period.getStartDate() + " - " + period.getEndDate() + ")");
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "initiatedByUser", job.getInitiatedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "view_municipalityjob_" + job.getId());
					uncontrolledJobsElement.appendChild(businessSectorJob);
				}
			}
			
			List<MunicipalityJob> controlledDisapprovedJobs = municipalityJobDAO.getAllControlledAndDisapproved();
			if (controlledDisapprovedJobs != null) {
				for (MunicipalityJob job : controlledDisapprovedJobs) {
					Element businessSectorJob = doc.createElement("DisapprovedJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					Period period = job.getPeriod();
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", period.getName() + " (" + period.getStartDate() + " - " + period.getEndDate() + ")");
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "approvedByUser", job.getApprovedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "controlledDate", job.getControlledDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "view_municipalityjob_" + job.getId());
					controlledDisapprovedJobsElement.appendChild(businessSectorJob);
				}
			}
		} else {
			XMLUtils.appendNewElement(doc, headingElement, "title", "Externa sommarjobb (Näringslivet)");

			List<BusinessSectorJob> controlledJobs = businessSectorJobDAO.getAllControlledAndApproved();
			if (controlledJobs != null) {
				for (BusinessSectorJob job : controlledJobs) {
					Element businessSectorJob = doc.createElement("ControlledJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", job.getStartDate() + " - " + job.getEndDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "approvedByUser", job.getApprovedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "controlledDate", job.getControlledDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "manage-businesssector-job?jobId=" + job.getId());
					controlledJobsElement.appendChild(businessSectorJob);
				}
			}
			
			List<BusinessSectorJob> uncontrolledJobs = businessSectorJobDAO.getAllUncontrolled();
			if (uncontrolledJobs != null) {
				for (BusinessSectorJob job : uncontrolledJobs) {
					Element businessSectorJob = doc.createElement("UncontrolledJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", job.getStartDate() + " - " + job.getEndDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "initiatedByUser", job.getInitiatedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "manage-businesssector-job?jobId=" + job.getId());
					uncontrolledJobsElement.appendChild(businessSectorJob);
				}
			}
			
			List<BusinessSectorJob> controlledDisapprovedJobs = businessSectorJobDAO.getAllControlledAndDisapproved();
			if (controlledDisapprovedJobs != null) {
				for (BusinessSectorJob job : controlledDisapprovedJobs) {
					Element businessSectorJob = doc.createElement("ControlledJob");
					XMLUtils.appendNewElement(doc, businessSectorJob, "workTitle", job.getWorkTitle());
					XMLUtils.appendNewElement(doc, businessSectorJob, "dates", job.getStartDate() + " - " + job.getEndDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "numberOfWorkersNeeded", job.getNumberOfWorkersNeeded());
					XMLUtils.appendNewElement(doc, businessSectorJob, "created", job.getCreated());
					XMLUtils.appendNewElement(doc, businessSectorJob, "approvedByUser", job.getApprovedByUser());
					XMLUtils.appendNewElement(doc, businessSectorJob, "controlledDate", job.getControlledDate());
					XMLUtils.appendNewElement(doc, businessSectorJob, "url", "manage-businesssector-job?jobId=" + job.getId());
					controlledDisapprovedJobsElement.appendChild(businessSectorJob);
				}
			}
		}
		
		doc.getFirstChild().appendChild(headingElement);
		doc.getFirstChild().appendChild(controlledJobsElement);
		doc.getFirstChild().appendChild(uncontrolledJobsElement);
		doc.getFirstChild().appendChild(controlledDisapprovedJobsElement);
		
		return new SimpleForegroundModuleResponse(doc);
	}
}
