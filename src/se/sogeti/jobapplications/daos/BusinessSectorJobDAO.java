package se.sogeti.jobapplications.daos;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryOperators;

public class BusinessSectorJobDAO extends JobDAO<BusinessSectorJob>{
	public BusinessSectorJobDAO(DataSource dataSource, Class<BusinessSectorJob> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

//	private static final Field JOB_APPLICATIONS_RELATION = ReflectionUtils.getField(BusinessSectorJob.class, "applications");
//	
//		
//	public BusinessSectorJob getByIdWithApplications(Integer jobId) throws SQLException {
//		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
//		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
//		query.addRelation(JOB_APPLICATIONS_RELATION);
//		return this.get(query);
//	}
	
	public java.util.List<BusinessSectorJob> getAllApproved() throws SQLException {
		System.out.println("getAllApproved i BusinessSectorJobDAO");
		return this.getByFieldAndBoolAndLastApplicationDayNotPassed("approved", true);
	}
	
	public java.util.List<BusinessSectorJob> getByFieldAndBoolAndLastApplicationDayNotPassed(String field, boolean param) throws SQLException {
		System.out.println("getByFieldAndBoolAndLastApplicationDayNotPassed i BusinessSectorJobDAO");
		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
		query.addParameter(this.getParamFactory(field, Boolean.class).getParameter(param));
		query.addParameter(this.getParamFactory("lastApplicationDay", Date.class).getParameter(new Date(Calendar.getInstance().getTimeInMillis()), QueryOperators.BIGGER_THAN_OR_EUALS));
		return this.getAll(query);
	}
}
