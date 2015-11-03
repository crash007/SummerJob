package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class JobDAO<T extends Job> extends AnnotatedDAO<T>{

	public JobDAO(DataSource dataSource, Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
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
}
