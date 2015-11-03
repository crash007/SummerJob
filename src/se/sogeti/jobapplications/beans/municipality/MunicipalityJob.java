package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.ContactDetails;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name = "summer_job_municipality_job")
@XMLElement
public class MunicipalityJob extends Job{

	@DAOManaged(columnName="manager_id")
	@XMLElement
	@ManyToOne(autoGet=true,autoAdd=true, autoUpdate=true, remoteKeyField="id")
	private MunicipalityManager manager;
	 
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
	
	// Geografiskt område 
	@DAOManaged(columnName="geoAreaId")
	@XMLElement
	@ManyToOne(remoteKeyField = "id", autoAdd = true, autoGet = true, autoUpdate = true)
	private GeoArea geoArea;
	
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

	@Override
	public String toString() {
		return "MunicipalityJob [manager=" + manager + ", mentors=" + mentors + ", period=" + period
				+ ", matchedApplications=" + matchedApplications + ", area=" + area + ", organization=" + organization
				+ ", administration=" + administration + ", location=" + location + ", department=" + department + "]";
	}

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

	public GeoArea getGeoArea() {
		return geoArea;
	}

	public void setGeoArea(GeoArea geoArea) {
		this.geoArea = geoArea;
	}

}
