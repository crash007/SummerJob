package se.sogeti.periodsadmin.beans;

import java.sql.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OrderBy;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

/**
 * 
 * @author Petter Johansson
 *
 */

@Table(name = "summer_job_periods")
@XMLElement
public class Period implements Elementable{
	
	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer id;
	
	@DAOManaged
	@OneToMany
	List<MunicipalityJob> jobs;

	@DAOManaged
	@WebPopulate(required=true, maxLength=255)
	@XMLElement
	private String name;

	@DAOManaged
	@WebPopulate(required=true)
	@XMLElement
	private Date startDate;
	
	@DAOManaged
	@WebPopulate(required=true)
	@XMLElement
	private Date endDate;
	
	@DAOManaged
	@OrderBy
	@XMLElement
	private Integer sortIndex;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	public Integer getSortIndex() {
		return sortIndex;
	}

	@Override
	public String toString() {
		return name + " (id: " + getId() + ")";
	}

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}
}
