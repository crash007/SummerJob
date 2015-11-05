package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class BusinessSectorJobApplicationDAO extends JobApplicationDAO<BusinessSectorJobApplication>{
	public BusinessSectorJobApplicationDAO(DataSource dataSource, Class<BusinessSectorJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

//	private static final Field APPLICATION_JOB_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "job");
//	
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
