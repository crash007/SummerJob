package se.sogeti.jobapplications.beans.business;

import java.sql.Date;
import java.util.List;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.Manager;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_business_sector_job")
public class BusinessSectorJob extends Job{

	@DAOManaged(columnName="workplaceId")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private BusinessSectorWorkplace workplace;
	
	@DAOManaged(columnName="manager_id")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private BusinessSectorManager manager;

	
	public BusinessSectorWorkplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(BusinessSectorWorkplace workplace) {
		this.workplace = workplace;
	}

	public BusinessSectorManager getManager() {
		return manager;
	}

	public void setManager(BusinessSectorManager manager) {
		this.manager = manager;
	}

	@DAOManaged
	@XMLElement
	private Date startDate;


	@DAOManaged
	@XMLElement
	private Date endDate;

	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<BusinessSectorJobApplication> appliedApplications;
	
	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<BusinessSectorJobApplication> matchedApplications;
	
	
	public List<BusinessSectorJobApplication> getAppliedApplications() {
		return appliedApplications;
	}

	public void setAppliedApplications(List<BusinessSectorJobApplication> appliedApplications) {
		this.appliedApplications = appliedApplications;
	}

	public List<BusinessSectorJobApplication> getMatchedApplications() {
		return matchedApplications;
	}

	public void setMatchedApplications(List<BusinessSectorJobApplication> matchedApplications) {
		this.matchedApplications = matchedApplications;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
