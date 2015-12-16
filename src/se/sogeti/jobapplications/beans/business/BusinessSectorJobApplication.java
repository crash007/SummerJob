package se.sogeti.jobapplications.beans.business;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.JobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name="summer_job_business_sector_job_application")
@XMLElement
public class BusinessSectorJobApplication extends JobApplication{
	
	public BusinessSectorJobApplication() {
		super();
	}
	
	@DAOManaged(columnName="jobId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	@XMLElement
	private BusinessSectorJob job;

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}


	public BusinessSectorJob getJob() {
		return job;
	}


	public void setJob(BusinessSectorJob job) {
		this.job = job;
	}


	@Override
	public String toString() {
		return "BusinessSectorJobApplication [job=" + job + ", toString()=" + super.toString() + "]";
	}

}
