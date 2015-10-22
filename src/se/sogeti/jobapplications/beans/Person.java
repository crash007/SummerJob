package se.sogeti.jobapplications.beans;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "summer_job_person")
public class Person {
	
	@DAOManaged
	@Key
	@XMLElement
	private Integer id;
	
	@DAOManaged
	@XMLElement
	private String personalCodeNumber;

	@DAOManaged
	@XMLElement
	private String firstname;
	
	@DAOManaged
	@XMLElement
	private String lastname;
	
	@DAOManaged
	@XMLElement
	private String streetAddress;
	
	@DAOManaged
	@XMLElement
	private String zipCode;
	
	@DAOManaged
	@XMLElement
	private String city;
	
	@DAOManaged
	@XMLElement
	private String email;
	
	@DAOManaged
	@XMLElement
	private String phoneNumber;
	
	@DAOManaged
	@XMLElement
	private boolean matchedForMunicipalityJob;
	
	@DAOManaged
	@XMLElement
	private String matchedJobId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPersonalCodeNumber() {
		return personalCodeNumber;
	}

	public void setPersonalCodeNumber(String personalCodeNumber) {
		this.personalCodeNumber = personalCodeNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getFullName() {
		return this.firstname + " " + this.lastname;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return getFullName() + "(id: " + id + ")";
	}
}
