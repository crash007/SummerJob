package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.Manager;
import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_municipality_job")
@XMLElement
public class MunicipalityJob extends Job{
	
	@DAOManaged(columnName="manager_id")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private MunicipalityManager manager;

	@DAOManaged(columnName="workplaceId")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private MunicipalityWorkplace workplace;
	
	 
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<MunicipalityMentor> mentors;

	@DAOManaged(columnName="periodId")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private Period period;
	
	@DAOManaged
	@XMLElement
	@OneToMany(autoGet=true,autoAdd=true, autoUpdate=true)
	private List<MunicipalityJobApplication> matchedApplications;
	
	// Område, t.ex Barnomsorg
	@DAOManaged(columnName="areaId")
	@XMLElement
	@ManyToOne(remoteKeyField = "id", autoAdd = true, autoGet = true, autoUpdate = true)
	private MunicipalityJobArea area;
	
	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public List<MunicipalityJobApplication> getMatchedApplications() {
		return matchedApplications;
	}

	public void setMatchedApplications(List<MunicipalityJobApplication> matchedApplications) {
		this.matchedApplications = matchedApplications;
	}

	public MunicipalityManager getManager() {
		return manager;
	}

	public void setManager(MunicipalityManager manager) {
		this.manager = manager;
	}

	public MunicipalityWorkplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(MunicipalityWorkplace workplace) {
		this.workplace = workplace;
	}

	public List<MunicipalityMentor> getMentors() {
		return mentors;
	}

	public void setMentors(List<MunicipalityMentor> mentors) {
		this.mentors = mentors;
	}

	public MunicipalityJobArea getArea() {
		return area;
	}

	public void setArea(MunicipalityJobArea area) {
		this.area = area;
	}


}
