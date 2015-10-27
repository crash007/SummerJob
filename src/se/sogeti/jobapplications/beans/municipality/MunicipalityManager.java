package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import se.sogeti.jobapplications.beans.Manager;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;


@Table(name = "summer_job_municipality_managers")
public class MunicipalityManager extends Manager {
	
	public List<MunicipalityJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<MunicipalityJob> jobs) {
		this.jobs = jobs;
	}

	@DAOManaged
	@OneToMany
	private List<MunicipalityJob> jobs;
	
	
}
