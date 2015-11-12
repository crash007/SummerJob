package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.MySQLRowLimiter;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class JobDAO<T extends Job> extends SummerJobCommonDAO<T>{

	private static Field JOB_APPLICATIONS_RELATION;
	
	public JobDAO(DataSource dataSource, Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		JOB_APPLICATIONS_RELATION = ReflectionUtils.getField(beanClass, "applications");
	}
	
	public void save(T bean) throws SQLException{
		this.addOrUpdate(bean, null);
	}
	
	public T getById(Integer jobId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		return this.get(query);
	}
	
	public T getByIdWithApplications(Integer jobId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		query.addRelation(JOB_APPLICATIONS_RELATION);
		return this.get(query);
	}

	
}
