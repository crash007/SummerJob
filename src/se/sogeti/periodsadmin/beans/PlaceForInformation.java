package se.sogeti.periodsadmin.beans;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLGenerator;

@XMLElement
@Table(name = "summer_job_place_for_information")
public class PlaceForInformation implements Elementable {
	
	@DAOManaged(autoGenerated=true)
	@Key
	@XMLElement
	private Integer id;

	@DAOManaged
	@XMLElement
	private String name;

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
