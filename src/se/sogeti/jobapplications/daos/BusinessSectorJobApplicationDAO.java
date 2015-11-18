package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class BusinessSectorJobApplicationDAO extends JobApplicationDAO<BusinessSectorJobApplication>{
	
	private static final Field APPLICATION_JOB_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "job");
	private static final Field APPLICATION_DRIVERS_LICENSE_TYPE_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "driversLicenseType");
	
	public BusinessSectorJobApplicationDAO(DataSource dataSource, Class<BusinessSectorJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

	public BusinessSectorJobApplication getByIdWithJob(Integer id) throws SQLException {
        HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
        query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
			
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		query.disableAutoRelations(true);
        return this.get(query);
	}

	public List<BusinessSectorJobApplication> getCandidatesFulfillingCriteras(BusinessSectorJob job) throws SQLException {
		 HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		 
		 query.addParameter(this.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
		 query.addParameter(this.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
		 
		 if(job.getMustBeOverEighteen()){
			 Calendar cal = Calendar.getInstance();
				cal.setTime(job.getStartDate());					
				cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
				Date bornBefore = new Date(cal.getTime().getTime());
			 query.addParameter(this.getParamFactory("birthDate", Date.class).getParameter(bornBefore,QueryOperators.SMALLER_THAN_OR_EUALS));
		 }
		 if(job.getDriversLicenseType()!=null){
			 query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(job.getDriversLicenseType(),QueryOperators.BIGGER_THAN_OR_EUALS));	 
		 }
		 
		 return this.getAll(query);
		 
	}

	public List<BusinessSectorJobApplication> getCandidatesNotFulfillingCriteras(BusinessSectorJob job) throws SQLException {
		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		 
		 query.addParameter(this.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
		 query.addParameter(this.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
		 
		 if(job.getMustBeOverEighteen()){
			 Calendar cal = Calendar.getInstance();
				cal.setTime(job.getStartDate());					
				cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
				Date bornBefore = new Date(cal.getTime().getTime());
			 query.addParameter(this.getParamFactory("birthDate", Date.class).getParameter(bornBefore,QueryOperators.BIGGER_THAN));
		 }
		 if(job.getDriversLicenseType()!=null){
			 query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(job.getDriversLicenseType(),QueryOperators.SMALLER_THAN));	 
		 }
		 
		 return this.getAll(query);
	}
}
