package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.PersonApplications;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.dao.QueryParameter;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class PersonApplicationsDAO extends AnnotatedDAO<PersonApplications> {

	private Logger log = Logger.getLogger(this.getClass());
	public static final Field PERSON_APPLICATIONS_MUNICIPALITY_APPLICATIONS = ReflectionUtils.getField(PersonApplications.class, "municipalityApplications");
	public static final Field PERSON_APPLICATIONS_BUSINESS_APPLICATIONS = ReflectionUtils.getField(PersonApplications.class, "businessApplications");
	
	public PersonApplicationsDAO(DataSource dataSource, Class<PersonApplications> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
		
	public PersonApplications getBySocialSecurityNumber(String socialSecurityNumber) throws SQLException {
		HighLevelQuery<PersonApplications> query = new HighLevelQuery<PersonApplications>();
		query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(socialSecurityNumber));
		query.disableAutoRelations(true);
		query.addRelation(PERSON_APPLICATIONS_BUSINESS_APPLICATIONS);
		return this.get(query);
	}
		
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea1(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{				
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea);
		return getCandidates(businessJobApplicationDAO, municipalityApplicationDAO,jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea2(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea);
		return getCandidates(businessJobApplicationDAO, municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea1AndPreferedGeoArea3(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea1", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea);
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea1(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea2(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
		
		
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea2AndPreferedGeoArea3(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea2", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea1(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea2(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByPreferedArea3AndPreferedGeoArea3(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, MunicipalityJobArea jobArea, GeoArea geoArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{		
		QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> jobAreaQuery = municipalityApplicationDAO.getParamFactory("preferedArea3", MunicipalityJobArea.class).getParameter(jobArea);
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO,municipalityApplicationDAO, jobAreaQuery, geoAreaQuery, bornBeforeDate, driversLicense);
	}

	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea1(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea1", GeoArea.class).getParameter(geoArea);		
		return getCandidates(businessJobApplicationDAO, municipalityApplicationDAO,null, geoAreaQuery, bornBeforeDate, driversLicenseType);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea2(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea2", GeoArea.class).getParameter(geoArea);
		return getCandidates(businessJobApplicationDAO, municipalityApplicationDAO,null,geoAreaQuery, bornBeforeDate, driversLicenseType);
	}
	
	public List<MunicipalityJobApplication> getCandidatesByNoPreferedAreaAndPreferedGeoArea3(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, GeoArea geoArea,
			Date bornBeforeDate, DriversLicenseType driversLicenseType) throws SQLException {
		// TODO Auto-generated method stub
		QueryParameter<MunicipalityJobApplication, GeoArea> geoAreaQuery = municipalityApplicationDAO.getParamFactory("preferedGeoArea3", GeoArea.class).getParameter(geoArea);
		return getCandidates(businessJobApplicationDAO, municipalityApplicationDAO,null, geoAreaQuery, bornBeforeDate, driversLicenseType);
	}
	
	
	public List<MunicipalityJobApplication> getCandidates(BusinessSectorJobApplicationDAO businessJobApplicationDAO, MunicipalityJobApplicationDAO municipalityApplicationDAO, QueryParameter<MunicipalityJobApplication, MunicipalityJobArea> areaQuery, QueryParameter<MunicipalityJobApplication, GeoArea> geoQueryArea,  Date bornBeforeDate, DriversLicenseType driversLicense) throws SQLException{

		HighLevelQuery<PersonApplications> query = new HighLevelQuery<PersonApplications>();
		
		if(areaQuery!=null){		
			query.addRelationParameter(MunicipalityJobApplication.class, areaQuery);
		}else{
			query.addRelationParameter(MunicipalityJobApplication.class, municipalityApplicationDAO.getParamFactory("noPreferedArea", Boolean.class).getParameter(true));
		}
		
		query.addRelationParameter(MunicipalityJobApplication.class,geoQueryArea);
		query.addRelationParameter(MunicipalityJobApplication.class,municipalityApplicationDAO.getParamFactory("approved", Boolean.class).getParameter(true));
		
		//hämta alla alla businessApplications som inte har statusen none för denna person. Om business applications inte är null så har personen ett business jobb.
		//Får inte vara matchad eller denied någonstans.
		query.addRelationParameter(BusinessSectorJobApplication.class, businessJobApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE,QueryOperators.NOT_EQUALS));
		query.addRelationParameter(MunicipalityJobApplication.class, municipalityApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE, QueryOperators.EQUALS));
		
		if(bornBeforeDate != null){
			java.sql.Date bornBeforeSqlDate = new java.sql.Date(bornBeforeDate.getTime());
			query.addRelationParameter(MunicipalityJobApplication.class, municipalityApplicationDAO.getParamFactory("birthDate", java.sql.Date.class).getParameter(bornBeforeSqlDate, QueryOperators.SMALLER_THAN_OR_EUALS));
		}
				
		query.addRelationParameter(MunicipalityJobApplication.class, municipalityApplicationDAO.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(driversLicense, QueryOperators.BIGGER_THAN_OR_EUALS));		
		
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_AREA1_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_AREA2_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_AREA3_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA1_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA2_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA3_RELATION);		
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(MunicipalityJobApplicationDAO.MUNICIPALITY_APPLICATION_JOB_RELATION);
		
		query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_BUSINESS_APPLICATIONS);
		query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_MUNICIPALITY_APPLICATIONS);
		
		
		//Behövs inte pga koppling mot job
		//query.addRelationParameter(MunicipalityJobApplication.class, municipalityApplicationDAO.getParamFactory("status", ApplicationStatus.class).getWhereInParameter(status));
		
		query.disableAutoRelations(true);
		
		query.addRelationOrderByCriteria(MunicipalityJobApplication.class,municipalityApplicationDAO.getOrderByCriteria("ranking", Order.ASC));
		
		List<PersonApplications> result = this.getAll(query);
		
		if(result!=null){
			List<MunicipalityJobApplication> applications = new ArrayList<MunicipalityJobApplication>();
			for(PersonApplications personApplications: result){
				log.debug(personApplications);
				if(personApplications.getMunicipalityApplications()!=null && personApplications.getBusinessApplications()==null){
					applications.addAll(personApplications.getMunicipalityApplications());
				}
			}
			return applications;
		}else{
			return null;
		}
		
	}
	
	
	public List<BusinessSectorJobApplication> getBusinessCandidatesFulfillingCriteras(BusinessSectorJobApplicationDAO businessJobApplicationDAO,MunicipalityJobApplicationDAO municipalityJobApplicationDAO, BusinessSectorJob job) throws SQLException {
		 HighLevelQuery<PersonApplications> query = new HighLevelQuery<PersonApplications>();
		 
		 query.addRelation(BusinessSectorJobApplicationDAO.APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		 query.addRelation(BusinessSectorJobApplicationDAO.APPLICATION_JOB_RELATION);
		 query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_BUSINESS_APPLICATIONS);
		 query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_MUNICIPALITY_APPLICATIONS);
		 
		 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
		 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
		 query.addRelationParameter(BusinessSectorJobApplication.class, businessJobApplicationDAO.getParamFactory("approved", Boolean.class).getParameter(true));
		 
		 if(job.getMustBeOverEighteen()){
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(job.getStartDate());					
			 cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
			 java.sql.Date bornBefore = new java.sql.Date(cal.getTime().getTime());
			 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("birthDate", java.sql.Date.class).getParameter(bornBefore,QueryOperators.SMALLER_THAN_OR_EUALS));
		 }
		 
		 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(job.getDriversLicenseType(),QueryOperators.BIGGER_THAN_OR_EUALS));	 		 		 
		 query.addRelationOrderByCriteria(BusinessSectorJobApplication.class,businessJobApplicationDAO.getOrderByCriteria("ranking", Order.ASC));
		
		 //hämta alla municipalityapplications som inte har statusen none för denna person. Om inte null så har personen ett municipality jobb eller tackat nej till ett.
			query.addRelationParameter(MunicipalityJobApplication.class, municipalityJobApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE,QueryOperators.NOT_EQUALS));
		 
		 List<PersonApplications> result = this.getAll(query);
		 if(result!=null){
			 List<BusinessSectorJobApplication> applications = new ArrayList<BusinessSectorJobApplication>();
			 for(PersonApplications personApplications: result){				 
				 log.debug(personApplications);
				 if(personApplications.getMunicipalityApplications()==null && personApplications.getBusinessApplications()!=null){
					 applications.addAll(personApplications.getBusinessApplications());
				 }
			}
			 return applications;
		 			
		 }else{
			 return null;
		 }
	}
	
	
	public List<BusinessSectorJobApplication> getBusinessCandidatesNotFulfillingCriteras(BusinessSectorJobApplicationDAO businessJobApplicationDAO,MunicipalityJobApplicationDAO municipalityJobApplicationDAO, BusinessSectorJob job) throws SQLException {
		HighLevelQuery<PersonApplications> query = new HighLevelQuery<PersonApplications>();
		 
		 query.addRelation(BusinessSectorJobApplicationDAO.APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		 query.addRelation(BusinessSectorJobApplicationDAO.APPLICATION_JOB_RELATION);
		 query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_BUSINESS_APPLICATIONS);
		 query.addRelation(PersonApplicationsDAO.PERSON_APPLICATIONS_MUNICIPALITY_APPLICATIONS);
		 
		 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
		 query.addRelationParameter(BusinessSectorJobApplication.class,businessJobApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
		 query.addRelationParameter(BusinessSectorJobApplication.class, businessJobApplicationDAO.getParamFactory("approved", Boolean.class).getParameter(true));
		 
		 //hämta alla municipalityapplications som inte har statusen none för denna person. Om inte null så har personen ett municipality jobb eller tackat nej till ett.
		query.addRelationParameter(MunicipalityJobApplication.class, municipalityJobApplicationDAO.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE,QueryOperators.NOT_EQUALS));
		query.addRelationOrderByCriteria(BusinessSectorJobApplication.class, businessJobApplicationDAO.getOrderByCriteria("ranking", Order.ASC)); 
		 List<PersonApplications> result = this.getAll(query);
		 if(result!=null){
			 			 
			 List<BusinessSectorJobApplication> applications = new ArrayList<BusinessSectorJobApplication>();
			 
			 Calendar cal = Calendar.getInstance();
			cal.setTime(job.getStartDate());					
			cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
			Date mustBeBornBeforeDate = cal.getTime();
				
			 for(PersonApplications personApplications: result){				 
				 
				 log.debug(personApplications);
				 
				 if(personApplications.getMunicipalityApplications()==null && personApplications.getBusinessApplications()!=null){
					 //Borde bara vara 1 ansökan per person
					 for(BusinessSectorJobApplication app : personApplications.getBusinessApplications()){
						 
						if(app.getDriversLicenseType().getId()< job.getDriversLicenseType().getId() || app.getBirthDate().after(mustBeBornBeforeDate)){
							applications.add(app);							
						}
					 }					 
				 }
			}
			 return applications;
		 			
		 }else{
			 return null;
		 }
		
	}
}
