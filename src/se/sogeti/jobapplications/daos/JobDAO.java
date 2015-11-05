package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class JobDAO<T extends Job> extends AnnotatedDAO<T>{

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

	public java.util.List<T> getAllUncontrolled() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(false));
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllControlled() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(true));
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllControlledAndApproved() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(true));
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllControlledAndDisapproved() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(true));
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(false));
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllApproved() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		return this.getAll(query);
	}
	
	public T getByIdWithApplications(Integer jobId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		query.addRelation(JOB_APPLICATIONS_RELATION);
		return this.get(query);
	}
	
}
