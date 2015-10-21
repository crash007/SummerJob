package se.sogeti.periodsadmin.beans;

import java.sql.Date;

import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OrderBy;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

/**
 * 
 * @author Petter Johansson
 *
 */

@Table(name = "period_query_alternatives")
@XMLElement
public class Period {
	
	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer alternativeID;
	
	@DAOManaged
	@XMLElement
	private Integer queryID;

	@DAOManaged
	@WebPopulate(required=true,maxLength=255)
	@XMLElement
	private String name;

	// Använder java.sql.Date, ska vi använda det eller java.util.Date?
	@DAOManaged
	@WebPopulate(required=true)
	@XMLElement
	private Date fromDate;
	
	// Använder java.sql.Date, ska vi använda det eller java.util.Date?
	@DAOManaged
	@WebPopulate(required=true)
	@XMLElement
	private Date toDate;
	
	@DAOManaged
	@OrderBy
	@XMLElement
	private Integer sortIndex;
	
	public void setAlternativeID(Integer id) {
		this.alternativeID = id;
	}
	
	public Integer getAlternativeID() {
		return alternativeID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setFromDate(Date date) {
		this.fromDate = date;
	}
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setToDate(Date date) {
		this.toDate = date;
	}
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
	public Integer getSortIndex() {
		return sortIndex;
	}

	@Override
	public String toString() {
//		return "Period" + " (alternativeID: " + alternativeID + ")";
		return name + " (alternativeID: " + alternativeID + ")";
	}

	public Integer getQueryID() {
		return queryID;
	}

	public void setQueryID(Integer queryID) {
		this.queryID = queryID;
	}
}
