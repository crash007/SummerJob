package se.sogeti.summerjob;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.JobApplication;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.standardutils.bool.BooleanUtils;

public class FormUtils {
	public static List<String> getMentorUuids(Enumeration<String> paramNames) {
		List<String> result = new ArrayList<String>();
		while(paramNames.hasMoreElements()){
			String s = paramNames.nextElement();
			if(s.startsWith("mentor-firstname")){		
				String[] splitted = s.split("_");
//				String uuid = s.split("_")[1];
				
				String uuid = splitted[1];
				if (splitted.length == 3) {
//					uuid += "_" + s.split("_")[2];
					uuid += "_" + splitted[2];
				}
				
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
		
		
		app.setCity(req.getParameter("postalarea"));
		app.setEmail(req.getParameter("email"));
		app.setFirstname(req.getParameter("firstname"));
		app.setLastname(req.getParameter("lastname"));
		app.setPhoneNumber(req.getParameter("phone"));
		app.setSocialSecurityNumber(req.getParameter("socialSecurityNumber"));
		app.setStreetAddress(req.getParameter("street"));
		app.setZipCode(req.getParameter("postalcode"));
		app.setBirthdate(FormUtils.getDateOfBirth(req.getParameter("socialSecurityNumber")));
				
		app.setPersonalLetter(req.getParameter("personal-letter"));

		app.setCreated(new Date(new java.util.Date().getTime()));
		
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
		
		date = Date.valueOf(birthDateString);
		
		return date;
	}
	
	/**
	 * Takes a social security number formatted as "YYYYmmddxxxx" and returns "YYmmdd-xxxx".
	 * Beware, no validation is taken care of.
	 */
	public static String getSSNMunicipalityFormatting(String ssn) {
		return ssn.substring(2, ssn.length() - 4) + "-" + ssn.substring(ssn.length() - 4);
	}
	
	public static Integer countApplications(List<? extends JobApplication> applications, ApplicationStatus status){
		
		if(applications!=null){
			Integer result=0;
			for(JobApplication app:applications){
				if(app.getStatus().equals(status)){
					result++;
				}
			}
			return result;
		} else {
			return null;
		}
		
	}
}
