package se.sogeti.jobapplications.beans.business;

import se.sogeti.jobapplications.beans.Mentor;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name="summer_job_muncipiality_mentor")
@XMLElement
public class BusinessSectorMentor extends Mentor<BusinessSectorJob>{


}
