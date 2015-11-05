package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.reflection.ReflectionUtils;

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

	

}
