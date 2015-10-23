package se.sogeti.jobapplications.beans;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_prefered_areas")
public class PreferedArea {

	@DAOManaged
	@XMLElement
	private Integer id;
	
	@DAOManaged(columnName="applicationId")
	@ManyToOne(remoteKeyField="id", autoAdd = false, autoGet = false, autoUpdate = false)
	private MunicipalityJobApplication application;
	
	@DAOManaged(columnName="areaId")
	@ManyToOne(remoteKeyField="id", autoAdd = false, autoGet = false, autoUpdate = false)
	private Area area;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MunicipalityJobApplication getApplication() {
		return application;
	}

	public void setApplication(MunicipalityJobApplication application) {
		this.application = application;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
}
