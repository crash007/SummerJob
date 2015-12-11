package se.sogeti.summerjob.addsummerjobapplication;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextAreaSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.xml.XMLUtils;

public abstract class AddSummerJobApplication<T extends JobApplication> extends AnnotatedRESTModule{
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Path till var cv'n ska sparas.", name = "FilePath")
	String filePath = "/tmp/";
	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	@ModuleSetting
	@TextAreaSettingDescriptor(name = "Allowed cities", description = "postorter som ingår i Sundsvalls kommun")
	private List<String> cities;
	
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
	
	protected void automaticControllAndApprove(T app){
		//If gymnasium och sundsvall set approved och controlled to true
		if(app.getSchoolType()!=null && app.getSchoolType().equals("GY") && app.getSkvCity()!=null && cities!=null && cities.contains(app.getSkvCity())){
			app.setApproved(true);
			app.setControlled(true);
			app.setControlledByUser("System");
		}		
	}
	
	protected void createJobApplication(T app, HttpServletRequest req, Citizen person){
		
		
		try {
			
			if(req.getParameter("postalarea")!=null){
				String postal = new String(req.getParameter("postalarea").getBytes("iso-8859-1"),"UTF-8");
				app.setCity(postal);
			}
			if(req.getParameter("firstname")!=null){
				String firstname = new String(req.getParameter("firstname").getBytes("iso-8859-1"),"UTF-8");
				app.setFirstname(firstname);
			}
			
			if(req.getParameter("lastname")!=null){
				String lastname = new String(req.getParameter("lastname").getBytes("iso-8859-1"),"UTF-8");
				app.setLastname(lastname);	
			}
			
			if(req.getParameter("street")!=null){
				String street = new String(req.getParameter("street").getBytes("iso-8859-1"),"UTF-8");
				app.setStreetAddress(street);	
			}
			
			if(req.getParameter("personal-letter")!=null){
				String letter = new String(req.getParameter("personal-letter").getBytes("iso-8859-1"),"UTF-8");
				app.setPersonalLetter(letter);;	
			}
			
		} catch (UnsupportedEncodingException e) {
			log.error("Exception",e);
		}
		
		app.setEmail(req.getParameter("email"));
		
		app.setPhoneNumber(req.getParameter("phone"));
		app.setSocialSecurityNumber(req.getParameter("socialSecurityNumber"));
		
		app.setZipCode(req.getParameter("postalcode"));
		app.setBirthdate(FormUtils.getDateOfBirth(req.getParameter("socialSecurityNumber")));

		app.setCreated(new Date(new java.util.Date().getTime()));
		
		//Citizen person = smexServiceHandler.getCitizen(app.getSocialSecurityNumber());
		if(person!=null){
			app.setSchoolName(person.getSchoolName());
			app.setSchoolType(person.getTypeOfSchool());
			app.setSkvCity(person.getCity());
		}
	}

}
