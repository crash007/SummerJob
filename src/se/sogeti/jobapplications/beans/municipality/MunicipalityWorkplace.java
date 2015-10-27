package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.Workplace;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OneToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_municipality_workplace")
public class MunicipalityWorkplace extends Workplace{

	@DAOManaged
	@OneToMany(autoAdd=true)
	private List<MunicipalityJob> job;

	public List<MunicipalityJob> getJob() {
		return job;
	}

	public void setJob(List<MunicipalityJob> job) {
		this.job = job;
	}
	
	@DAOManaged
	@XMLElement
	private String organization;
	
	//Förvaltning
	@DAOManaged
	@XMLElement
	private String administration;
	
	//Plats
	@DAOManaged
	@XMLElement
	private String location;
	
	// Fritext för avdelning
	@DAOManaged
	@XMLElement
	private String department;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	

}
