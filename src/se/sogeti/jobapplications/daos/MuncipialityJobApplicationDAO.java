package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.dao.QueryParameter;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class MuncipialityJobApplicationDAO extends JobApplicationDAO<MunicipalityJobApplication>{
	
	private static final Field APPLICATION_JOB_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "job");
	private static final Field APPLICATION_PREFERED_AREA1_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea1");
	private static final Field APPLICATION_PREFERED_AREA2_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea2");
	private static final Field APPLICATION_PREFERED_AREA3_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea3");
	
	private static final Field APPLICATION_PREFERED_GEO_AREA1_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea1");
	private static final Field APPLICATION_PREFERED_GEO_AREA2_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea2");
	private static final Field APPLICATION_PREFERED_GEO_AREA3_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea3");
	
	private static final Field APPLICATION_DRIVERS_LICENSE_TYPE_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "driversLicenseType");
	
	public MuncipialityJobApplicationDAO(DataSource dataSource, Class<MunicipalityJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea1(MunicipalityJobArea jobArea, GeoArea geoArea,  Boolean overEighteen, DriversLicenseType driversLicense, Date jobStartDate) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), overEighteen, driversLicense, jobStartDate);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea2(MunicipalityJobArea jobArea, GeoArea geoArea,  Boolean overEighteen, DriversLicenseType driversLicense, Date jobStartDate) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea), overEighteen, driversLicense, jobStartDate);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea1(MunicipalityJobArea jobArea, GeoArea geoArea,  Boolean overEighteen, DriversLicenseType driversLicense, Date jobStartDate) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), overEighteen, driversLicense, jobStartDate);
	}
	
//	public List<MunicipalityJobApplication> getCandidates(QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> areaQuery, QueryParameter<MunicipalityJobApplication, GeoArea> geoQueryArea,  Boolean overEighteen, DriversLicenseType driversLicense) throws SQLException{
//		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
//		query.addParameter(areaQuery);
//		query.addParameter(geoQueryArea);
//		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
//		query.addParameter(this.getParamFactory("job", MunicipalityJob.class).getIsNullParameter());
//		
//		if(overEighteen!=null){
//			query.addParameter(this.getParamFactory("isOverEighteen", Boolean.class).getParameter(overEighteen));		
//		}
//		
//		if(driversLicense!=null){
//			query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(driversLicense, QueryOperators.BIGGER_THAN_OR_EUALS));
//		}
//		
//		query.addRelation(APPLICATION_PREFERED_AREA1_RELATION);
//		query.addRelation(APPLICATION_PREFERED_AREA2_RELATION);
//		query.addRelation(APPLICATION_PREFERED_AREA3_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA1_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA2_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA3_RELATION);		
//		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
//		query.addRelation(APPLICATION_JOB_RELATION);
//		
//		
//		query.disableAutoRelations(true);
//		return this.getAll(query);
//	}
	
	public List<MunicipalityJobApplication> getCandidates(QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> areaQuery, QueryParameter<MunicipalityJobApplication, GeoArea> geoQueryArea,  Boolean overEighteen, DriversLicenseType driversLicense, Date jobStartDate) throws SQLException{
		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
		query.addParameter(areaQuery);
		query.addParameter(geoQueryArea);
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		query.addParameter(this.getParamFactory("job", MunicipalityJob.class).getIsNullParameter());
		
//		if(overEighteen!=null){
//			query.addParameter(this.getParamFactory("isOverEighteen", Boolean.class).getParameter(overEighteen));		
//		}
		
		if(driversLicense!=null){
			query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(driversLicense, QueryOperators.BIGGER_THAN_OR_EUALS));
		}
		
		query.addRelation(APPLICATION_PREFERED_AREA1_RELATION);
		query.addRelation(APPLICATION_PREFERED_AREA2_RELATION);
		query.addRelation(APPLICATION_PREFERED_AREA3_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA1_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA2_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA3_RELATION);		
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		
		
		query.disableAutoRelations(true);
		
		List<MunicipalityJobApplication> candidates = this.getAll(query);
		
		if (candidates != null && overEighteen != null && overEighteen.booleanValue() == true) {
			System.out.println("This job needs candidates that is over 18 years old.");
			
			List<MunicipalityJobApplication> candidatesTemp = new ArrayList<MunicipalityJobApplication>(candidates);
			for (MunicipalityJobApplication app : candidates) {
				if (!candidateIsOfAge(jobStartDate, app.getDateOfBirth())) { // The candidate is too young when the job starts.
					candidatesTemp.remove(app);
					System.out.println("The candidate was too young.");
				}
			}
			candidates = candidatesTemp;
		}
		
		return candidates;
	}
	
	public boolean candidateIsOfAge(Date startDate, Date dateOfBirth) {
		
		if (startDate == null || dateOfBirth == null) {
			return false;
		}
		
		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOfBirth);
		Calendar jobStartDate = Calendar.getInstance();
		jobStartDate.setTime(startDate);
		int age = jobStartDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (jobStartDate.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (jobStartDate.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && jobStartDate.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		return age >= 18;
	}
	
	
//	public List<BusinessSectorJobApplication> getAllUnapprovedWithJob() throws SQLException {
//		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
//		query.addParameter(this.getParamFactory("approved", boolean.class).getParameter(false));
//		query.addRelation(APPLICATION_JOB_RELATION);
//		return this.getAll(query);
//	}
//	
//	public List<BusinessSectorJobApplication> getAllApprovedWithJob() throws SQLException {
//		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
//		query.addParameter(this.getParamFactory("approved", boolean.class).getParameter(true));
//		query.addRelation(APPLICATION_JOB_RELATION);
//		return this.getAll(query);
//	}

}
