package se.sogeti.jobapplications.beans;

import java.util.List;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToMany;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_areas")
public class Area {

	@DAOManaged
	@Key
	@XMLElement
	private Integer id;
	
	@DAOManaged
	@XMLElement
	private String name;
	
	@DAOManaged
	@XMLElement
	private String description;
	
	@DAOManaged
	@XMLElement
	private boolean canBeChosenInApplication;
	
	@DAOManaged
	@OneToMany
	private List<MunicipalityJob> jobs;
	
	@DAOManaged
	@ManyToOne(remoteKeyField="id")
	private MunicipalityJobApplication application;
	
	@DAOManaged
	@OneToMany
	private List<PreferedArea> preferedAreas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCanBeChosenInApplication() {
		return canBeChosenInApplication;
	}

	public void setCanBeChosenInApplication(boolean canBeChosenInApplication) {
		this.canBeChosenInApplication = canBeChosenInApplication;
	}

	public List<MunicipalityJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<MunicipalityJob> jobs) {
		this.jobs = jobs;
	}

	public MunicipalityJobApplication getApplication() {
		return application;
	}

	public void setApplication(MunicipalityJobApplication application) {
		this.application = application;
	}

	public List<PreferedArea> getPreferedAreas() {
		return preferedAreas;
	}

	public void setPreferedAreas(List<PreferedArea> preferedAreas) {
		this.preferedAreas = preferedAreas;
	}
}
