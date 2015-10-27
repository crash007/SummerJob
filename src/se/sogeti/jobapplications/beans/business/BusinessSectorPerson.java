package se.sogeti.jobapplications.beans.business;


import java.util.List;

import se.sogeti.jobapplications.beans.Person;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityPerson;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;


@Table(name="summer_job_bussines_sector_person")
public class BusinessSectorPerson extends Person{
	
	@DAOManaged
	@OneToMany
	private List<BusinessSectorJobApplication> jobApplications;

	public List<BusinessSectorJobApplication> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(List<BusinessSectorJobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
}
