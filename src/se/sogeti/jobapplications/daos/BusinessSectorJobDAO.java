package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.dao.QueryParameter;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class BusinessSectorJobDAO extends JobDAO<BusinessSectorJob>{
	
	private static Field JOB_APPLICATIONS_RELATION;
	
	public BusinessSectorJobDAO(DataSource dataSource, Class<BusinessSectorJob> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		
		JOB_APPLICATIONS_RELATION = ReflectionUtils.getField(beanClass, "applications");
	}

	private static final Field JOB_MENTORS_RELATION = ReflectionUtils.getField(BusinessSectorJob.class, "mentor");
	private static final Field JOB_MANAGER_RELATION = ReflectionUtils.getField(BusinessSectorJob.class, "manager");
	private static final Field JOB_DRIVERS_LICENSE_TYPE_RELATION = ReflectionUtils.getField(BusinessSectorJob.class, "driversLicenseType");

	
	public BusinessSectorJob getById(Integer jobId) throws SQLException {
		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		
		query.addRelation(JOB_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(JOB_MANAGER_RELATION);
		query.addRelation(JOB_MENTORS_RELATION);
		query.disableAutoRelations(true);
		
		return this.get(query);
	}
	
	public java.util.List<BusinessSectorJob> getAllApproved() throws SQLException {	
		return this.getByFieldAndBoolAndLastApplicationDayNotPassed("approved", true);
	}
	
	public java.util.List<BusinessSectorJob> getByFieldAndBoolAndLastApplicationDayNotPassed(String field, boolean param) throws SQLException {	
		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
		query.addParameter(this.getParamFactory(field, Boolean.class).getParameter(param));
		query.addParameter(this.getParamFactory("lastApplicationDay", Date.class).getParameter(new Date(Calendar.getInstance().getTimeInMillis()), QueryOperators.BIGGER_THAN_OR_EUALS));
		return this.getAll(query);
	}
	
	public BusinessSectorJob getByIdWithApprovedApplications(Integer jobId, boolean approved, BusinessSectorJobApplicationDAO businessApplicationDAO) throws SQLException {
		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		query.addRelation(JOB_APPLICATIONS_RELATION);
		query.addRelationParameter(BusinessSectorJobApplication.class, businessApplicationDAO.getParamFactory("approved", Boolean.class).getParameter(approved));
		
		return this.get(query);
	}
}
