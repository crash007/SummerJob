package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.util.Date;
import java.sql.SQLException;
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
	
	private static final Field APPLICATION_MENTOR_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "personalMentor");
	
	public MuncipialityJobApplicationDAO(DataSource dataSource, Class<MunicipalityJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea1(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea2(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea3(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea1(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea2(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea3(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea1(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea2(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea3(MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		return getCandidates(this.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea), this.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicense);
	}

	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea1(GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		// TODO Auto-generated method stub
		return getCandidates(null, this.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicenseType);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea2(GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		// TODO Auto-generated method stub
		return getCandidates(null, this.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicenseType);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea3(GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		// TODO Auto-generated method stub
		return getCandidates(null, this.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea), bornBeforeDate, driversLicenseType);
	}
	
	public List<MunicipalityJobApplication> getCandidates(QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> areaQuery, QueryParameter<MunicipalityJobApplication, GeoArea> geoQueryArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{
		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
		
		if(areaQuery!=null){
			query.addParameter(areaQuery);
		}else{
			query.addParameter(this.getParamFactory("noPreferedArea", Boolean.class).getParameter(true));
		}
		
		query.addParameter(geoQueryArea);
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		query.addParameter(this.getParamFactory("job", MunicipalityJob.class).getIsNullParameter());
		
		if(bornBeforeDate != null){
			java.sql.Date bornBeforeSqlDate = new java.sql.Date(bornBeforeDate.getTime());
			query.addParameter(this.getParamFactory("birthDate", java.sql.Date.class).getParameter(bornBeforeSqlDate, QueryOperators.SMALLER_THAN_OR_EUALS));
		}
		
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
		return this.getAll(query);
	}
	
	public MunicipalityJobApplication getByIdWithJob(Integer id) throws SQLException {
        HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
        query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		
        query.addRelation(APPLICATION_PREFERED_AREA1_RELATION);
		query.addRelation(APPLICATION_PREFERED_AREA2_RELATION);
		query.addRelation(APPLICATION_PREFERED_AREA3_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA1_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA2_RELATION);
		query.addRelation(APPLICATION_PREFERED_GEO_AREA3_RELATION);		
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		
		query.addRelation(APPLICATION_MENTOR_RELATION);

		query.disableAutoRelations(true);
        
        return this.get(query);
	}
	
//	public MunicipalityJobApplication getByIdWithMentor(Integer id) throws SQLException {
//		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
//        query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
//		
//        query.addRelation(APPLICATION_PREFERED_AREA1_RELATION);
//		query.addRelation(APPLICATION_PREFERED_AREA2_RELATION);
//		query.addRelation(APPLICATION_PREFERED_AREA3_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA1_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA2_RELATION);
//		query.addRelation(APPLICATION_PREFERED_GEO_AREA3_RELATION);		
//		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
////		query.addRelation(APPLICATION_JOB_RELATION);
//		query.addRelation(APPLICATION_MENTOR_RELATION);
//        
//		query.disableAutoRelations(true);
//        
//        return this.get(query);
//	}
}
