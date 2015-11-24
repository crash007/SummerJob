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
@Table(name = "summer_job_accounting_entries")
public class AccountingEntry implements Elementable {

	@DAOManaged
	@XMLElement
	@Key
	private Integer id;
	
	@DAOManaged
	@XMLElement
	private Boolean isPrio;
	
	@DAOManaged
	@XMLElement
	private String ansvar;
	
	@DAOManaged
	@XMLElement
	private String verksamhet;
	
	@DAOManaged
	@XMLElement
	private String aktivitet;
	
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

	public String getAnsvar() {
		return ansvar;
	}

	public void setAnsvar(String ansvar) {
		this.ansvar = ansvar;
	}

	public String getVerksamhet() {
		return verksamhet;
	}

	public void setVerksamhet(String verksamhet) {
		this.verksamhet = verksamhet;
	}

	public String getAktivitet() {
		return aktivitet;
	}

	public void setAktivitet(String aktivitet) {
		this.aktivitet = aktivitet;
	}

	public Boolean getIsPrio() {
		return isPrio;
	}

	public void setIsPrio(Boolean isPrio) {
		this.isPrio = isPrio;
	}
}
