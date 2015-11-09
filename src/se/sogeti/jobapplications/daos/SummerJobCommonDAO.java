package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.MySQLRowLimiter;

public abstract class SummerJobCommonDAO<T> extends AnnotatedDAO<T>{

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

	public java.util.List<T> getByFieldAndBoolAndUsername(String field,boolean param,Integer rows, String username) throws SQLException{
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory(field, Boolean.class).getParameter(param));
		
		if(rows != null) {
			MySQLRowLimiter limit = new MySQLRowLimiter(rows);
			query.setRowLimiter(limit);
		}
		
		if(username!=null){
			query.addParameter(this.getParamFactory("addedByUser", String.class).getParameter(username));
		}
		
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllUncontrolled() throws SQLException {		
		return this.getByFieldAndBoolAndUsername("controlled", false, null,null);
	}
	
	public java.util.List<T> getAllUncontrolledAddedByUsername(String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("controlled", false, null,username);
	}
	
	public java.util.List<T> getAllControlled() throws SQLException {
		return this.getByFieldAndBoolAndUsername("controlled", true, null,null);
	}
	
	public java.util.List<T> getLatestControlled(Integer rows) throws SQLException {
		return this.getByFieldAndBoolAndUsername("controlled", true, rows,null);
	}
	
	public java.util.List<T> getLatestUncontrolled(Integer rows) throws SQLException {
		return this.getByFieldAndBoolAndUsername("controlled", false, rows,null);
	}
	
	public java.util.List<T> getLatestUncontrolledAddedByUsername(Integer rows,String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("controlled", false, rows,username);
	}	
	
	public java.util.List<T> getAllApproved() throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", true, null,null);
	}
	
	public java.util.List<T> getAllUnapproved() throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", false, null,null);
	}
	
	public java.util.List<T> getAllUnapprovedAddedByUsername(String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", false, null,username);
	}
	
	public java.util.List<T> getLatestApproved(Integer rows) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", true, rows,null);
	}	
	
	public java.util.List<T> getLatestApprovedAddedByUsername(Integer rows,String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", true, rows,username);
	}	
	
	public java.util.List<T> getLatestUnapproved(Integer rows) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", false, rows,null);
	}
	
	public java.util.List<T> getLatestUnapprovedAddedByUsername(Integer rows,String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", false, rows,username);
	}
	
	public java.util.List<T> getAllControlledAndApproved() throws SQLException {
		return getControlledAndApprovedAddedByUsername(true,true,null);
	}
	
	public java.util.List<T> getAllControlledAndDisapproved() throws SQLException {		
		return getControlledAndApprovedAddedByUsername(true,false,null);
	}
	
	public java.util.List<T> getAllControlledAndApprovedAddedByUsername(String username) throws SQLException {
		return getControlledAndApprovedAddedByUsername(true,true,username);
	}
	
	public java.util.List<T> getAllControlledAndDisapprovedAddedByUsername(String username) throws SQLException {		
		return getControlledAndApprovedAddedByUsername(true,false,username);
	}
	
	
	public java.util.List<T> getControlledAndApprovedAddedByUsername(boolean controlled,boolean approved, String username) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(controlled));
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(approved));
		
		if(username!=null){
			query.addParameter(this.getParamFactory("addedByUser", String.class).getParameter(username));
		}
		
		return this.getAll(query);
	}
}
