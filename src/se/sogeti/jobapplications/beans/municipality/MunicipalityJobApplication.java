package se.sogeti.jobapplications.beans.municipality;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.JobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name="summer_job_municipality_job_application")
@XMLElement
public class MunicipalityJobApplication extends JobApplication{

	public MunicipalityJobApplication() {
		super();
	}

	@DAOManaged(columnName="jobId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	private MunicipalityJob job;
	
	@DAOManaged(columnName="personalMentorId")
	@ManyToOne(remoteKeyField="id", autoGet = true, autoAdd = true, autoUpdate = true)
	private MunicipalityMentor personalMentor;
	
	@DAOManaged
	@XMLElement
	private Integer personalMentorId;
	
	@DAOManaged
	@XMLElement
	private Boolean noPreferedArea=false;
	
	@DAOManaged(columnName="prefered_area_1")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedArea1")
	private MunicipalityJobArea preferedArea1;
	
	@DAOManaged(columnName="prefered_area_2")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedArea2")
	private MunicipalityJobArea preferedArea2;
	
	@DAOManaged(columnName="prefered_area_3")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedArea3")
	private MunicipalityJobArea preferedArea3;
	
	@DAOManaged(columnName="prefered_geo_area_1")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedGeoArea1")
	private GeoArea preferedGeoArea1;
	
	@DAOManaged(columnName="prefered_geo_area_2")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedGeoArea2")
	private GeoArea preferedGeoArea2;
	
	@DAOManaged(columnName="prefered_geo_area_3")
	@ManyToOne(remoteKeyField="id",autoGet=true)
	@XMLElement(name="preferedGeoArea3")
	private GeoArea preferedGeoArea3;

	
	public MunicipalityJobArea getPreferedArea1() {
		return preferedArea1;
	}

	public void setPreferedArea1(MunicipalityJobArea preferedArea1) {
		this.preferedArea1 = preferedArea1;
	}

	public MunicipalityJobArea getPreferedArea2() {
		return preferedArea2;
	}

	public void setPreferedArea2(MunicipalityJobArea preferedArea2) {
		this.preferedArea2 = preferedArea2;
	}

	public MunicipalityJobArea getPreferedArea3() {
		return preferedArea3;
	}

	public void setPreferedArea3(MunicipalityJobArea preferedArea3) {
		this.preferedArea3 = preferedArea3;
	}

	public GeoArea getPreferedGeoArea1() {
		return preferedGeoArea1;
	}

	public void setPreferedGeoArea1(GeoArea preferedGeoArea1) {
		this.preferedGeoArea1 = preferedGeoArea1;
	}

	public GeoArea getPreferedGeoArea2() {
		return preferedGeoArea2;
	}

	public void setPreferedGeoArea2(GeoArea preferedGeoArea2) {
		this.preferedGeoArea2 = preferedGeoArea2;
	}

	public GeoArea getPreferedGeoArea3() {
		return preferedGeoArea3;
	}

	public void setPreferedGeoArea3(GeoArea preferedGeoArea3) {
		this.preferedGeoArea3 = preferedGeoArea3;
	}

	@Override
	public String toString() {
		return "MunicipalityJobApplication [job=" + job + ", preferedArea1=" + preferedArea1 + ", preferedArea2="
				+ preferedArea2 + ", preferedArea3=" + preferedArea3 + ", preferedGeoArea1=" + preferedGeoArea1
				+ ", preferedGeoArea2=" + preferedGeoArea2 + ", preferedGeoArea3=" + preferedGeoArea3 + super.toString()+"]";
		
	}
	
	public String applicationBasicsToString(){
		return super.toString();
	}
	

	@Override
	public Element toXML(Document doc) {
		// TODO Auto-generated method stub
		return XMLGenerator.toXML(this, doc);
	}

	public MunicipalityJob getJob() {
		return job;
	}

	public void setJob(MunicipalityJob job) {
		this.job = job;
	}

	public Boolean getNoPreferedArea() {
		return noPreferedArea;
	}

	public void setNoPreferedArea(Boolean noPreferedArea) {
		this.noPreferedArea = noPreferedArea;
	}

	public MunicipalityMentor getPersonalMentor() {
		return personalMentor;
	}

	public void setPersonalMentor(MunicipalityMentor personalMentor) {
		this.personalMentor = personalMentor;
	}

	public Integer getPersonalMentorId() {
		return personalMentorId;
	}

	public void setPersonalMentorId(Integer personalMentorId) {
		this.personalMentorId = personalMentorId;
	}

}
