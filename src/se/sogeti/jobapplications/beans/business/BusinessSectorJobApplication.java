package se.sogeti.jobapplications.beans.business;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.JobApplication;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@Table(name="summer_job_business_sector_job_application")
@XMLElement
public class BusinessSectorJobApplication extends JobApplication{
	

	@DAOManaged(columnName="jobId")
	@ManyToOne(remoteKeyField="id",autoGet=false,autoAdd=false,autoUpdate=false)
	private BusinessSectorJob job;
	
	//assigned is true if application is matched with the job.
	@DAOManaged
	private Boolean assigned;


	public Boolean getAssigned() {
		return assigned;
	}


	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}


	@Override
	public Element toXML(Document doc) {

		return XMLGenerator.toXML(this, doc);
	}

}
