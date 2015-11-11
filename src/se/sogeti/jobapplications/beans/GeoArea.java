package se.sogeti.jobapplications.beans;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;

import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

//geografiskt område
@Table(name = "summer_job_geo_area")
@XMLElement
public class GeoArea implements Elementable{

	@DAOManaged
	@Key
	@XMLElement
	private Integer id;
	
	@DAOManaged
	@OneToMany
	private List<MunicipalityJobApplication> applicationsPreferedGeoArea;
	
	@DAOManaged
	@OneToMany
	private List<MunicipalityJob> muncipalityJobs;
	
	
	@DAOManaged(columnName="name")
	@XMLElement
	private String name;
	
	@DAOManaged(columnName="description")
	@XMLElement
	private String description;
	
	@DAOManaged(columnName="nyko")
	@XMLElement
	private String nyko;
	
	
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


	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}


	@Override
	public String toString() {
		return "GeoArea [id=" + id + ", name=" + name + "]";
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNyko() {
		return nyko;
	}


	public void setNyko(String nyko) {
		this.nyko = nyko;
	}

}
