package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import se.sogeti.jobapplications.beans.Person;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_municipality_person")
public class MunicipalityPerson extends Person{
	
	@DAOManaged
	@OneToMany
	private List<MunicipalityJobApplication> jobApplications;

	public List<MunicipalityJobApplication> getJobApplications() {
		return jobApplications;
	}

	public void setJobApplications(List<MunicipalityJobApplication> jobApplications) {
		this.jobApplications = jobApplications;
	}
}
