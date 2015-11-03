package se.sogeti.jobapplications.beans;

import java.sql.Date;
import java.util.List;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;


@XMLElement
public abstract class JobApplication implements Elementable{
	
	@DAOManaged(autoGenerated=true)
	@Key
	@XMLElement
	private Integer id;

	@DAOManaged
	@XMLElement
	private Integer ranking;
	
	@DAOManaged
	@XMLElement
	private Date created;
	
	@DAOManaged
	@XMLElement
	private boolean hasDriversLicense;
	
	@DAOManaged
	@XMLElement
	private boolean isOverEighteen;
	
	@DAOManaged(columnName="driversLicenseTypeId")
	@ManyToOne(remoteKeyField="id", autoGet = true, autoAdd = true, autoUpdate = true)
	private DriversLicenseType driversLicenseType;
	
//	@DAOManaged
//	@XMLElement
//	private Integer driversLicenseType;
	
	@DAOManaged
	@XMLElement
	private boolean hasAccessToVehicle;

	@DAOManaged
	@XMLElement
	private String personalLetter;
	
	@DAOManaged
	@XMLElement
	private String cvLocation;
	
	@DAOManaged
	@XMLElement
	private boolean approved;
	
	@DAOManaged
	@XMLElement
	private boolean controlled;
	
	@DAOManaged
	@XMLElement
	private String controlledByUser;
	
	@DAOManaged
	@XMLElement
	private Date controlledDate;

	@DAOManaged
	@XMLElement
	private String socialSecurityNumber;

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
	private String schoolName;
	
	@DAOManaged
	@XMLElement
	private String schoolType;
	
	@DAOManaged
	@XMLElement
	private String skvCity;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public boolean isHasDriversLicense() {
		return hasDriversLicense;
	}

	public void setHasDriversLicense(boolean hasDriversLicense) {
		this.hasDriversLicense = hasDriversLicense;
	}

	public DriversLicenseType getDriversLicenseType() {
		return driversLicenseType;
	}

	public void setDriversLicenseType(DriversLicenseType driversLicenseType) {
		this.driversLicenseType = driversLicenseType;
	}

	public boolean isHasAccessToVehicle() {
		return hasAccessToVehicle;
	}

	public void setHasAccessToVehicle(boolean hasAccessToVehicle) {
		this.hasAccessToVehicle = hasAccessToVehicle;
	}

	public String getPersonalLetter() {
		return personalLetter;
	}

	public void setPersonalLetter(String personalLetter) {
		this.personalLetter = personalLetter;
	}

	public String getCvLocation() {
		return cvLocation;
	}

	public void setCvLocation(String cvLocation) {
		this.cvLocation = cvLocation;
	}


	public Date getApprovedDate() {
		return controlledDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.controlledDate = approvedDate;
	}

	public boolean isOverEighteen() {
		return isOverEighteen;
	}

	public void setOverEighteen(boolean isOverEighteen) {
		this.isOverEighteen = isOverEighteen;
	}

	@Override
	public String toString() {
		return "JobApplication [id=" + id + ", ranking=" + ranking + ", created=" + created + ", hasDriversLicense="
				+ hasDriversLicense + ", isOverEighteen=" + isOverEighteen + ", driversLicenseType="
				+ driversLicenseType + ", hasAccessToVehicle=" + hasAccessToVehicle + ", personalLetter="
				+ personalLetter + ", cvLocation=" + cvLocation + ", approved=" + approved + ", controlled="
				+ controlled + ", controlledByUser=" + controlledByUser + ", controlledDate=" + controlledDate
				+ ", socialSecurityNumber=" + socialSecurityNumber + ", firstname=" + firstname + ", lastname="
				+ lastname + ", streetAddress=" + streetAddress + ", zipCode=" + zipCode + ", city=" + city + ", email="
				+ email + ", phoneNumber=" + phoneNumber + ", schoolName=" + schoolName + ", schoolType=" + schoolType
				+ ", skvCity=" + skvCity + "]";
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isControlled() {
		return controlled;
	}

	public void setControlled(boolean controlled) {
		this.controlled = controlled;
	}

	public String getControlledByUser() {
		return controlledByUser;
	}

	public void setControlledByUser(String controlledByUser) {
		this.controlledByUser = controlledByUser;
	}

	public Date getControlledDate() {
		return controlledDate;
	}

	public void setControlledDate(Date controlledDate) {
		this.controlledDate = controlledDate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSkvCity() {
		return skvCity;
	}

	public void setSkvCity(String skvCity) {
		this.skvCity = skvCity;
	}

	
}
