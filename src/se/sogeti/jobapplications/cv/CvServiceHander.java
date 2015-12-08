package se.sogeti.jobapplications.cv;

import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;

public interface CvServiceHander {
	public String getBusinessApplicationCvUrl();
	public String getMunicipalityApplicationCvUrl();
}
