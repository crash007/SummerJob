package se.sogeti.jobapplications.beans.municipality;

import se.sogeti.jobapplications.beans.Mentor;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_municipality_mentors")
@XMLElement
public class MunicipalityMentor extends Mentor{
	
	@DAOManaged(columnName="jobId")
	@ManyToOne
	private MunicipalityJob job;
	
	public MunicipalityJob getJob() {
		return job;
	}

	public void setJob(MunicipalityJob job) {
		this.job = job;
	}

}
