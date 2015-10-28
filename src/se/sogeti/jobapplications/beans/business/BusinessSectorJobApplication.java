package se.sogeti.jobapplications.beans.business;

import java.sql.Date;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_business_sector_job_application")
public class BusinessSectorJobApplication extends JobApplication{
	

	@DAOManaged(columnName="jobId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	private BusinessSectorJob job;
	
	@DAOManaged(columnName="driversLicenseTypeId")
	@ManyToOne(remoteKeyField="id")
	private DriversLicenseType driversLicenseType;

}
