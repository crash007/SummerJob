package se.sogeti.jobapplications.beans.business;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ContactDetails;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name="summer_job_business_sector_mentors")
@XMLElement
public class BusinessSectorMentor extends ContactDetails{

	@DAOManaged(columnName="jobId")
	@ManyToOne
	private BusinessSectorJob job;
	
	public BusinessSectorJob getJob() {
		return job;
	}

	public void setJob(BusinessSectorJob job) {
		this.job = job;
	}

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

}
