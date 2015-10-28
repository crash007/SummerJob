package se.sogeti.jobapplications.beans;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@XMLElement
@Table(name = "summer_job_driverslicensetype")
public class DriversLicenseType implements Elementable {
	
	@DAOManaged
	@OneToMany
	private List<MunicipalityJob> municipalityJobs;

	@DAOManaged
	@OneToMany
	private List<BusinessSectorJob> businessSectorJobs;

	@DAOManaged
	@OneToMany
	private List<MunicipalityJobApplication> municipalityJobApplications;

	@DAOManaged
	@OneToMany
	private List<BusinessSectorJobApplication> businessSectorJobApplications;
	
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

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

	public List<BusinessSectorJob> getBusinessSectorJobs() {
		return businessSectorJobs;
	}

	public void setBusinessSectorJobs(List<BusinessSectorJob> businessSectorJobs) {
		this.businessSectorJobs = businessSectorJobs;
	}

	public List<MunicipalityJob> getMunicipalityJobs() {
		return municipalityJobs;
	}

	public void setMunicipalityJobs(List<MunicipalityJob> municipalityJobs) {
		this.municipalityJobs = municipalityJobs;
	}
}
