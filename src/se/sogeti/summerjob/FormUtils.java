package se.sogeti.summerjob;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.periodsadmin.JsonResponse;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.standardutils.numbers.NumberUtils;

public class FormUtils {
	public static List<String> getMentorUuids(Enumeration<String> paramNames) {
		List<String> result = new ArrayList<String>();
		while(paramNames.hasMoreElements()){
			String s = paramNames.nextElement();
			if(s.startsWith("mentor-firstname")){				
				String uuid = s.split("_")[1];
				
				result.add(uuid);
			}
		}
		return result;
	}
	
	public static String getParameterNameThatContains(String value, Enumeration<String> paramNames) {
		String result = null;
		
		if (value == null || paramNames == null) {
			return null;
		}
		
		while(paramNames.hasMoreElements()){
			String s = paramNames.nextElement();
			if(s.contains(value)){				
				result = s;
				break;
			}
		}
		return result;
	}
	
	public static  <T extends JobApplication> void createJobApplication(T app, HttpServletRequest req, Citizen person){
		app.setApproved(false);
		app.setControlled(false);
		app.setCvLocation(req.getParameter("cvFile"));
		
		app.setHasDriversLicense(req.getParameter("hasDriversLicense") !=null ? true:false);
		app.setOverEighteen(req.getParameter("isOverEighteen") !=null ? true:false);
		
		
		app.setCity(req.getParameter("postalarea"));
		app.setEmail(req.getParameter("email"));
		app.setFirstname(req.getParameter("firstname"));
		app.setLastname(req.getParameter("lastname"));
		app.setPhoneNumber(req.getParameter("phone"));
		app.setSocialSecurityNumber(req.getParameter("socialSecurityNumber"));
		app.setStreetAddress(req.getParameter("street"));
		app.setZipCode(req.getParameter("postalcode"));
		app.setDateOfBirth(FormUtils.getDateOfBirth(req.getParameter("socialSecurityNumber")));
		
		
		app.setPersonalLetter(req.getParameter("personal-letter"));
		
		app.setRanking(3);
		app.setCreated(new Date(new java.util.Date().getTime()));
		
		app.setControlled(false);
		app.setApproved(false);
		
		
		//Citizen person = smexServiceHandler.getCitizen(app.getSocialSecurityNumber());
		if(person!=null){
			app.setSchoolName(person.getSchoolName());
			app.setSchoolType(person.getTypeOfSchool());
			app.setSkvCity(person.getCity());
		}
		
		//If gymnasium och sundsvall set approved och controlled till true
		if(app.getSchoolType()!=null && app.getSchoolType().equals("GY")){
			app.setApproved(true);
			app.setControlled(true);
			app.setControlledByUser("System");
		}
	}
	
	public static Date getDateOfBirth(String socialSecurityNumber) {
		String ssn = socialSecurityNumber.substring(0, 8);
		String birthDateString = ssn.substring(0, 4) + "-" + ssn.substring(4, 6) + "-" + ssn.substring(6, ssn.length());
		Date date = null;
		try {
			date = Date.valueOf(birthDateString);
		} catch (IllegalArgumentException e) {}
		return date;
	}
}
