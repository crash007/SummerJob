package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.MySQLRowLimiter;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class SummerJobCommonDAO<T> extends AnnotatedDAO<T>{

	public SummerJobCommonDAO(DataSource dataSource, Class<T> beanClass,
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

	public java.util.List<T> getByFieldAndBool(String field,boolean param,Integer rows) throws SQLException{
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory(field, Boolean.class).getParameter(param));
		
		if(rows != null) {
			MySQLRowLimiter limit = new MySQLRowLimiter(rows);
			query.setRowLimiter(limit);
		}
		
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllUncontrolled() throws SQLException {		
		return this.getByFieldAndBool("controlled", false, null);
	}
	
	public java.util.List<T> getLatestUncontrolled(Integer rows) throws SQLException {		
		return this.getByFieldAndBool("controlled", false, rows);
	}
	
	public java.util.List<T> getAllControlled() throws SQLException {
		return this.getByFieldAndBool("controlled", true, null);
	}
	
	public java.util.List<T> getLatestControlled(Integer rows) throws SQLException {
		return this.getByFieldAndBool("controlled", true, rows);
	}
	
	public java.util.List<T> getAllApproved() throws SQLException {		
		return this.getByFieldAndBool("approved", true, null);
	}
	
	public java.util.List<T> getLatestApproved(Integer rows) throws SQLException {		
		return this.getByFieldAndBool("approved", true, rows);
	}
	
	public java.util.List<T> getAllUnapproved() throws SQLException {		
		return this.getByFieldAndBool("approved", false, null);
	}
	
	public java.util.List<T> getLatestUnapproved(Integer rows) throws SQLException {		
		return this.getByFieldAndBool("approved", false, rows);
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
	
}
