package se.sogeti.jobapplications.beans;

import java.sql.Date;

import se.sogeti.jobapplications.interfaces.Requirements;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.xml.Elementable;
import se.unlogic.standardutils.xml.XMLElement;

@XMLElement
public abstract class Job implements Requirements,Elementable{

	
	@DAOManaged(autoGenerated=true)
	@Key
	@XMLElement
	private Integer id;
	
	@DAOManaged
	@XMLElement
	private Date created;
	
	@DAOManaged
	@XMLElement
	private Date updated;

	@DAOManaged
	@XMLElement
	private String workTitle;
	
	@DAOManaged
	@XMLElement
	private String workDescription;
	
	@DAOManaged
	@XMLElement
	private Integer numberOfWorkersNeeded;
	
	@DAOManaged
	@XMLElement
	private Boolean approved;
	
	@DAOManaged
	@XMLElement
	private Date controlledDate;
	
	@DAOManaged
	@XMLElement
	private Boolean controlled;
	
	@DAOManaged
	@XMLElement
	private String approvedByUser;
	
	@DAOManaged
	@XMLElement
	private String addedByUser;
	
	@DAOManaged
	@XMLElement
	private Boolean mustBeOverEighteen;
	
//	@DAOManaged
//	@XMLElement
//	private Boolean hasDriversLicense;
	
	@DAOManaged(columnName="driversLicenseTypeId")
	@XMLElement
	@ManyToOne(remoteKeyField="id", autoGet = true, autoAdd = true, autoUpdate = true)
	private DriversLicenseType driversLicenseType;
	
	@DAOManaged
	@XMLElement
	private String freeTextRequirements;
	
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
	private String initiatedByUser;
	
	@DAOManaged
	@XMLElement
	private String adminNotes;
	
	@DAOManaged
	@XMLElement
	private String descriptionForEmploymentPapers;
	
	@XMLElement
	private Integer openApplications;
	
	@XMLElement
	private Integer matchedApplications;
	
//	@XMLElement
//	private Integer assignedApplications;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	public Integer getNumberOfWorkersNeeded() {
		return numberOfWorkersNeeded;
	}

	public void setNumberOfWorkersNeeded(Integer numberOfWorkersNeeded) {
		this.numberOfWorkersNeeded = numberOfWorkersNeeded;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getApprovedDate() {
		return controlledDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.controlledDate = approvedDate;
	}

	public String getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(String approvedByUser) {
		this.approvedByUser = approvedByUser;
	}
	
	@Override
	public String toString() {
		return "Job [id=" + id + ", created=" + created + ", updated=" + updated + ", workTitle=" + workTitle
				+ ", workDescription=" + workDescription + ", numberOfWorkersNeeded=" + numberOfWorkersNeeded
				+ ", approved=" + approved + ", controlledDate=" + controlledDate + ", controlled=" + controlled
				+ ", approvedByUser=" + approvedByUser + ", addedByUser=" + addedByUser + ", mustBeOverEighteen="
				+ mustBeOverEighteen + ", driversLicenseType=" + driversLicenseType + ", freeTextRequirements="
				+ freeTextRequirements + ", streetAddress=" + streetAddress + ", zipCode=" + zipCode + ", city=" + city
				+ ", initiatedByUser=" + initiatedByUser + ", adminNotes=" + adminNotes + ", openApplications="
				+ openApplications + ", matchedApplications=" + matchedApplications + "]";
	}

	public Boolean getMustBeOverEighteen() {
		return mustBeOverEighteen;
	}

	public String getFreeTextRequirements() {
		return freeTextRequirements;
	}

	public void setFreeTextRequirements(String freeTextRequirements) {
		this.freeTextRequirements = freeTextRequirements;
	}

	public void setIsOverEighteen(Boolean isOverEighteen) {
		this.mustBeOverEighteen = isOverEighteen;
	}

//	public Boolean getHasDriversLicense() {
//		return hasDriversLicense;
//	}
//
//	public void setHasDriversLicense(Boolean hasDriversLicense) {
//		this.hasDriversLicense = hasDriversLicense;
//	}

	public DriversLicenseType getDriversLicenseType() {
		return driversLicenseType;
	}

	public void setDriversLicenseType(DriversLicenseType driversLicenseType) {
		this.driversLicenseType = driversLicenseType;
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

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public Date getControlledDate() {
		return controlledDate;
	}

	public void setControlledDate(Date controlledDate) {
		this.controlledDate = controlledDate;
	}

	public Boolean getControlled() {
		return controlled;
	}

	public void setControlled(Boolean controlled) {
		this.controlled = controlled;
	}

	public String getAdminNotes() {
		return adminNotes;
	}

	public void setAdminNotes(String adminNotes) {
		this.adminNotes = adminNotes;
	}

	public String getInitiatedByUser() {
		return initiatedByUser;
	}

	public void setInitiatedByUser(String initiatedByUser) {
		this.initiatedByUser = initiatedByUser;
	}

	public String getAddedByUser() {
		return addedByUser;
	}

	public void setAddedByUser(String addedByUser) {
		this.addedByUser = addedByUser;
	}

	public Integer getOpenApplications() {
		return openApplications;
	}

	public void setOpenApplications(Integer openApplications) {
		this.openApplications = openApplications;
	}

	public Integer getMatchedApplications() {
		return matchedApplications;
	}

	public void setMatchedApplications(Integer matchedApplications) {
		this.matchedApplications = matchedApplications;
	}

//	public Integer getAssignedApplications() {
//		return assignedApplications;
//	}
//
//	public void setAssignedApplications(Integer assignedApplications) {
//		this.assignedApplications = assignedApplications;
//	}

	public void setMustBeOverEighteen(Boolean mustBeOverEighteen) {
		this.mustBeOverEighteen = mustBeOverEighteen;
	}

	public String getDescriptionForEmploymentPapers() {
		return descriptionForEmploymentPapers;
	}

	public void setDescriptionForEmploymentPapers(
			String descriptionForEmploymentPapers) {
		this.descriptionForEmploymentPapers = descriptionForEmploymentPapers;
	}

}