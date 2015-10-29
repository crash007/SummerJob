package se.sogeti.jobapplications.beans.business;

import se.sogeti.jobapplications.beans.Mentor;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_business_sector_mentors")
@XMLElement
public class BusinessSectorMentor extends Mentor{
	
	@DAOManaged(columnName="jobId")
	@ManyToOne
	private BusinessSectorJob job;
	
	public BusinessSectorJob getJob() {
		return job;
	}

	public void setJob(BusinessSectorJob job) {
		this.job = job;
	}
}
