package se.sogeti.summerjob.addsummerjobapplication;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.summerjob.JsonResponse;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.xml.XMLUtils;

public abstract class AddSummerJobApplication<T extends JobApplication> extends AnnotatedRESTModule{
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Path till var cv'n ska sparas.", name = "FilePath")
	String filePath = "/tmp/";
	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	protected void saveCv(T application, FileItem fileItem, String fileName,PrintWriter writer, String callback) {
		String fullPath = filePath + fileName;
		File file = new File(fullPath);
		try {
			fileItem.write(file);
			log.info("Saved cv file:"+file.toString());
			application.setCvFilename(fullPath);
		} catch (Exception e1) {
			log.error(e1);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"" + "exception when saving file." + "\"}", callback, writer);
		}
	}

	protected void populateDriversLicenseTypesElement(Document doc, Element form, T app, List<DriversLicenseType> licenseTypes)
			throws SQLException {
		Element driversLicenseElement = doc.createElement("DriversLicenseTypes");
		
		for (DriversLicenseType type : licenseTypes) {
			Element typeElement = doc.createElement("DriversLicenseType");
			XMLUtils.appendNewElement(doc, typeElement, "id", type.getId());
			XMLUtils.appendNewElement(doc, typeElement, "name", type.getName());
			XMLUtils.appendNewElement(doc, typeElement, "description", type.getDescription());
			if (app != null && app.getDriversLicenseType() != null) {
				XMLUtils.appendNewElement(doc, typeElement, "selected", 
						app.getDriversLicenseType().getId().intValue() == type.getId().intValue());
			}
			driversLicenseElement.appendChild(typeElement);
		}
		form.appendChild(driversLicenseElement);
	}
	
	protected Boolean validateSocialSecurityNumber(PrintWriter writer, String callback, String socialSecurityNumber) {
		
		if (socialSecurityNumber == null || socialSecurityNumber.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ange ett personnummer i din ansökan.\"}", callback, writer);
			return false;
		}


		if (socialSecurityNumber.length() != 12) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret måste bestå av 12 tecken (ÅÅÅÅMMDDxxxx).\"}", callback, writer);
			return false;
		}

		try {
			java.sql.Date.valueOf(socialSecurityNumber.substring(0, 4) + "-" + socialSecurityNumber.substring(4, 6) + "-" + socialSecurityNumber.substring(6, 8));
		} catch (IllegalArgumentException e) {
			log.error(e);
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret innehåller ej ett giltigt datum.\"}", callback, writer);
			return false;
		}
		return true;
	}
	
	protected Citizen getCitizen(String socialSecurityNumber) {
		try {
			return smexServiceHandler.getCitizen(socialSecurityNumber);	
		} catch(SmexServiceException e) {
			log.error(e);
		} catch(Exception e) {
			log.error(e);
		}
		return null;
	}
	
	protected Boolean validatePersonalInformation(PrintWriter writer, String callback, T app) {
		if (app.getFirstname() == null || app.getFirstname().isEmpty() || app.getCity() == null || app.getCity().isEmpty() ||
				app.getLastname() == null || app.getLastname().isEmpty() || app.getPhoneNumber() == null || app.getPhoneNumber().isEmpty() ||
				app.getSocialSecurityNumber() == null || app.getSocialSecurityNumber().isEmpty() ||
				app.getStreetAddress() == null || app.getStreetAddress().isEmpty() || 
				app.getZipCode() == null || app.getZipCode().isEmpty() || app.getEmail() == null || app.getEmail().isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för personuppgifter kan inte lämnas tomma. Det enda som inte krävs är en e-postadress.\"}", callback, writer);
			return false;
		}else
			return true;
	}
	
	protected Boolean validatePersonalLetter(PrintWriter writer, String callback, T app) {
		if (app.getPersonalLetter() == null || app.getPersonalLetter().isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ge ett kort personligt brev i din ansökan.\"}", callback, writer);
			return false;
		}else
			return true;
	}

}
