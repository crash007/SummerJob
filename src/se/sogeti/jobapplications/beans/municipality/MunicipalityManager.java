package se.sogeti.jobapplications.beans.municipality;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ContactDetails;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;


@Table(name = "summer_job_municipality_managers")
@XMLElement
public class MunicipalityManager extends ContactDetails {
	
	public List<MunicipalityJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<MunicipalityJob> jobs) {
		this.jobs = jobs;
	}

	@DAOManaged
	@OneToMany
	private List<MunicipalityJob> jobs;

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}
	
	
}
