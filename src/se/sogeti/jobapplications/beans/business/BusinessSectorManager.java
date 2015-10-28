package se.sogeti.jobapplications.beans.business;

import java.util.List;

import se.sogeti.jobapplications.beans.Mentor;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;


@Table(name = "summer_job_business_sector_managers")
public class BusinessSectorManager extends Mentor{

	public List<BusinessSectorJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<BusinessSectorJob> jobs) {
		this.jobs = jobs;
	}

	@DAOManaged
	@OneToMany
	private List<BusinessSectorJob> jobs;


	
	
}
