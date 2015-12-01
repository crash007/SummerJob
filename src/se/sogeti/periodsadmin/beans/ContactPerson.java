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
@Table(name = "summer_job_contact_person")
public class ContactPerson implements Elementable {

	@DAOManaged
	@XMLElement
	@Key
	private Integer id;
	
	@DAOManaged
	@XMLElement
	private String name;
	
	@DAOManaged
	@XMLElement
	private String phoneNumber;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public Element toXML(Document doc) {
		return XMLGenerator.toXML(this, doc);
	}
}
