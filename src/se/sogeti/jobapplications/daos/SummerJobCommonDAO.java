package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.MySQLRowLimiter;
import se.unlogic.standardutils.dao.OrderByCriteria;

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

	public java.util.List<T> getByFieldAndBoolAndUsername(String field,boolean param,Integer rows, String addedByUsername) throws SQLException{
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory(field, Boolean.class).getParameter(param));
		
		if(rows != null) {
			MySQLRowLimiter limit = new MySQLRowLimiter(rows);
			query.setRowLimiter(limit);
		}
		
		if(addedByUsername!=null){
			query.addParameter(this.getParamFactory("addedByUser", String.class).getParameter(addedByUsername));
		}
		
		return this.getAll(query);
	}
	
	public java.util.List<T> getAllUncontrolled() throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(false, null, null, null);
	}
	/*
	
	
	public java.util.List<T> getAllUncontrolledAddedByUsername(String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(false, null, null, username);
	}
	
	public java.util.List<T> getAllControlled() throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, null, null, null);		
	}
	
	public java.util.List<T> getLatestControlled(Integer rows) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, null, null, null, null,rows);		
	}
	
	public java.util.List<T> getLatestUncontrolled(Integer rows) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(false, null, null, null,null,rows);
	}
	
	public java.util.List<T> getLatestUncontrolledAddedByUsername(Integer rows,String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(false, null, null, username,null,rows);		
	}	
	
	public java.util.List<T> getAllApproved() throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", true, null,null);
	}
	
	public java.util.List<T> getAllUnapproved() throws SQLException {	
		return getByControlledAndApprovedAddedByUsername(null, false, null, null);
	}
	
	public java.util.List<T> getAllUnapprovedAddedByUsername(String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, false, null, null);		
	}
	
	public java.util.List<T> getLatestApproved(Integer rows) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, true, null, null,null,rows);		
	}	
	
	public java.util.List<T> getLatestApprovedAddedByUsername(Integer rows,String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, true, null, username,null,rows);		
	}	
	
	public java.util.List<T> getLatestUnapproved(Integer rows) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, false, null, null,null,rows);		
	}
	
	public java.util.List<T> getLatestUnapprovedAddedByUsername(Integer rows,String username) throws SQLException {		
		return this.getByFieldAndBoolAndUsername("approved", false, rows,username);
	}
	
	public java.util.List<T> getAllControlledAndOpen() throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, null);
	}
	
	public java.util.List<T> getAllControlledAndOpen(List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, null,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndDisapproved() throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, false, false, null);
	}
	
	public java.util.List<T> getAllControlledAndClosed() throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, true, false, null);
	}
	
	public java.util.List<T> getAllControlledAndOpenAddedByUsername(String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, username);
	}
	
	public java.util.List<T> getAllControlledAndClosedAddedByUsername(String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, false, username);
	}
	*/
	
	public java.util.List<T> getLatestUnapprovedAddedByUsername(Integer rows,String username) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(null, false, null, username,null,rows);			
	}
	
	public java.util.List<T> getLatestApprovedAddedByUsername(Integer rows,String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, true, null, username,null,rows);		
	}
	
	public java.util.List<T> getLatestUncontrolledAddedByUsername(Integer rows,String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(false, null, null, username,null,rows);		
	}
	
	public java.util.List<T> getLatestUnapproved(Integer rows) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, false, null, null,null,rows);		
	}
	
	public java.util.List<T> getLatestApproved(Integer rows) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(null, true, null, null,null,rows);		
	}	
	
	public java.util.List<T> getLatestUncontrolled(Integer rows) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(false, null, null, null,null,rows);
	}
	
	public java.util.List<T> getAllControlledAndDisapprovedAddedByUsername(String username) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, false, false, username);
	}
	
	public java.util.List<T> getAllControlledAndClosed() throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, null, false, null);
	}
	
	public java.util.List<T> getAllControlledAndApprovedAndOpen(List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, null,orderByCriterias);
	}
	
	public java.util.List<T> getAllUncontrolled(List<OrderByCriteria<T>> orderByCriterias) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(false, null, null, null,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndClosed(List<OrderByCriteria<T>> orderByCriterias) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, true, false, null,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndDisapproved(List<OrderByCriteria<T>> orderByCriterias) throws SQLException {		
		return getByControlledAndApprovedAddedByUsername(true, false, false, null,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndOpenAddedByUsername(String username,List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, username,orderByCriterias);
	}
	
	public java.util.List<T> getAllUncontrolledAddedByUsername(String username, List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(false, null, null, username,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndClosedAddedByUsername(String username, List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, null, false, username,orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndDisapprovedAddedByUsername(String username, List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, false, null, username, orderByCriterias);
	}
	
	public java.util.List<T> getAllControlledAndApprovedAndOpen() throws SQLException {
		return getByControlledAndApprovedAddedByUsername(true, true, true, null);
	}
		
	public java.util.List<T> getByControlledAndApprovedAddedByUsername(Boolean controlled, Boolean approved, Boolean isOpen, String username, List<OrderByCriteria<T>> orderByCriterias, Integer rows) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		
		if(controlled!=null){
			query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(controlled));
		}
		
		if(approved!=null){
			query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(approved));
		}
		
		if(isOpen!=null){
			query.addParameter(this.getParamFactory("isOpen", Boolean.class).getParameter(isOpen));
		}
		
		if(orderByCriterias!=null){
			query.setOrderByCriterias(orderByCriterias);
		}
		
		if(username!=null){
			query.addParameter(this.getParamFactory("addedByUser", String.class).getParameter(username));
		}
		
		if(rows != null) {
			MySQLRowLimiter limit = new MySQLRowLimiter(rows);
			query.setRowLimiter(limit);
		}
		
		return this.getAll(query);
	}
	
	public java.util.List<T> getByControlledAndApprovedAddedByUsername(Boolean controlled, Boolean approved, Boolean isOpen, String username) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(controlled, approved, isOpen, username,null, null);
	}
	
	public java.util.List<T> getByControlledAndApprovedAddedByUsername(Boolean controlled, Boolean approved, Boolean isOpen, String username, List<OrderByCriteria<T>> orderByCriterias) throws SQLException {
		return getByControlledAndApprovedAddedByUsername(controlled, approved, isOpen, username,orderByCriterias, null);
	}
}
