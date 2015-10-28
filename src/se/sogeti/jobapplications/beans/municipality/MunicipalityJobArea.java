package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;

import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

//Example Barnomsorg, Äldreomsorg
@Table(name = "summer_job_areas")
@XMLElement
public class MunicipalityJobArea implements Elementable{

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
	@OneToMany
	private List<MunicipalityJobApplication> applicationsPreferedAreas;
	
	
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

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

	@Override
	public String toString() {
		return "MunicipalityJobArea [id=" + id + ", name=" + name + ", description=" + description
				+ ", canBeChosenInApplication=" + canBeChosenInApplication + ", jobs=" + jobs
				+ ", applicationsPreferedAreas=" + applicationsPreferedAreas + "]";
	}

}
