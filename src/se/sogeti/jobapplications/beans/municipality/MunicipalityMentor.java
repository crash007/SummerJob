package se.sogeti.jobapplications.beans.municipality;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ContactDetails;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name="summer_job_municipality_mentors")
@XMLElement
public class MunicipalityMentor extends ContactDetails{
	
	@DAOManaged(columnName="jobId")
	@ManyToOne
	private MunicipalityJob job;
	
	public MunicipalityJob getJob() {
		return job;
	}

	public void setJob(MunicipalityJob job) {
		this.job = job;
	}

	@Override
	public Element toXML(Document doc) {
		
		return XMLGenerator.toXML(this, doc);
	}

}
