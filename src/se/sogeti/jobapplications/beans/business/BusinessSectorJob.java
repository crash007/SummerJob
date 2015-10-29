package se.sogeti.jobapplications.beans.business;

import java.sql.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.Job;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name = "summer_job_business_sector_job")
public class BusinessSectorJob extends Job{
	
	@DAOManaged(columnName="manager_id")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private BusinessSectorManager manager;

	@DAOManaged
	@XMLElement
	private Date startDate;


	@DAOManaged
	@XMLElement
	private Date endDate;
	
	@DAOManaged
	@XMLElement
	private String company;

	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<BusinessSectorJobApplication> appliedApplications;
	
	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<BusinessSectorJobApplication> matchedApplications;
	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<BusinessSectorMentor> mentors;
	
	
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

	public List<BusinessSectorMentor> getMentors() {
		return mentors;
	}

	public void setMentors(List<BusinessSectorMentor> mentors) {
		this.mentors = mentors;
	}
	

	public BusinessSectorManager getManager() {
		return manager;
	}

	public void setManager(BusinessSectorManager manager) {
		this.manager = manager;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}
}
