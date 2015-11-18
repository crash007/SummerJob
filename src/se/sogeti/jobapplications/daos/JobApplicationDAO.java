package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.MySQLRowLimiter;
import se.unlogic.standardutils.dao.OrderByCriteria;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class JobApplicationDAO<T extends JobApplication> extends SummerJobCommonDAO<T> {

	private static Field APPLICATION_JOB_RELATION;
	
	public JobApplicationDAO(DataSource dataSource,
			Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		 APPLICATION_JOB_RELATION=ReflectionUtils.getField(beanClass, "job");
	}
	
	public void save(T bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	/**
	 * To get all applications that has been matched to a certain job.
	 */
	public List<T> getApplicationsByJobId(Integer jobApplicationId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		//TODO kolla vilket id 
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobApplicationId));
		return this.getAll(query);
	}
	
	public List<T> getAllUnapprovedWithJob() throws SQLException {
		return getUnapprovedWithJob(null);
	}
	
	public List<T> getUnapprovedWithJob(Integer rows) throws SQLException {
		return getWithJob(false, rows,null);
	}
	
	public List<T> getUnapprovedWithJobAddedByUsername(Integer rows,String username) throws SQLException {
		return getWithJob(false, rows, username);
	}
	
	public List<T> getAllApprovedWithJob() throws SQLException {
		return getWithJob(true, null,null);
	}
	
	public List<T> getApprovedWithJob(Integer rows) throws SQLException {
		return getWithJob(true, rows,null);
	}

	public List<T> getApprovedWithJobAddedByUsername(Integer rows,String username) throws SQLException {
		return getWithJob(true, rows, username);
	}
	
	public List<T> getWithJob(boolean approved,Integer rows, String username) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(approved));
		query.addRelation(APPLICATION_JOB_RELATION);
		
		if(rows!=null){
			MySQLRowLimiter limit = new MySQLRowLimiter(rows);
			query.setRowLimiter(limit);
		}
		
		if(username!=null){
			query.addParameter(this.getParamFactory("addedByUser", String.class).getParameter(username));
		}
		
		return this.getAll(query);
	}
	
	public T getbySocialSecurityNumber(String social) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(social));		
		return this.get(query);
	}
	
	public List<T> getAllByApprovedAndDescendingOrder(String socialSecurityNumber, boolean approved, boolean orderByDescending) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		
		if (socialSecurityNumber != null) {			
			query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(socialSecurityNumber, QueryOperators.LIKE));
		}
		
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(approved));
		Order order = null;
		if (orderByDescending) {
			order = Order.DESC;
		} else {
			order = Order.ASC;
		}
		
		OrderByCriteria<T> orderByRanking = this.getOrderByCriteria("ranking", Order.DESC);
		OrderByCriteria<T> orderByCreated = this.getOrderByCriteria("created", order);
		query.addOrderByCriteria(orderByRanking);
		query.addOrderByCriteria(orderByCreated);
		return this.getAll(query);
	}
	


}
