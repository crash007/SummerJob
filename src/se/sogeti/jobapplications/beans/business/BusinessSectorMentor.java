package se.sogeti.jobapplications.beans.business;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ContactDetails;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;


//Näringslivsjobb har bara en handledare/kontaktperson
@Table(name="summer_job_business_sector_mentors")
@XMLElement
public class BusinessSectorMentor extends ContactDetails{
	
	public List<BusinessSectorJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<BusinessSectorJob> jobs) {
		this.jobs = jobs;
	}

	@DAOManaged
	@OneToMany
	private List<BusinessSectorJob> jobs;

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

}
