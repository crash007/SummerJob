package se.sogeti.jobapplications.beans.municipality;

import se.sogeti.jobapplications.beans.JobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_municipality_job_application")
@XMLElement
public class MunicipalityJobApplication extends JobApplication{

	@DAOManaged(columnName="jobId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	private MunicipalityJob job;
	
	@DAOManaged(columnName="personId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	private MunicipalityPerson person;
	
	@DAOManaged(columnName="prefered_area_1")
	@ManyToOne(remoteKeyField="id")
	private MunicipalityJobArea preferedArea1;
	
	@DAOManaged(columnName="prefered_area_2")
	@ManyToOne(remoteKeyField="id")
	private MunicipalityJobArea preferedArea2;
	
	@DAOManaged(columnName="prefered_area_3")
	@ManyToOne(remoteKeyField="id")
	private MunicipalityJobArea preferedArea3;
	


}
