package se.sogeti.jobapplications.beans.business;

import java.util.List;

import se.sogeti.jobapplications.beans.Workplace;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OneToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_business_sector_workplace")
public class BusinessSectorWorkplace extends Workplace{

	@DAOManaged
	@OneToMany(autoAdd=true)
	private List<BusinessSectorJob> job;
	
	@DAOManaged
	@XMLElement
	private String company;

	public List<BusinessSectorJob> getJob() {
		return job;
	}

	public void setJob(List<BusinessSectorJob> job) {
		this.job = job;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
