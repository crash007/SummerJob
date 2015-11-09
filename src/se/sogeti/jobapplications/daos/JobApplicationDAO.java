package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class JobApplicationDAO<T extends JobApplication> extends AnnotatedDAO<T> {

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
	
	public List<T> getAllUncontrolled() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>(); 
		query.addParameter(this.getParamFactory("controlled", Boolean.class).getParameter(false));
		return this.getAll(query);
	}
	
	public List<T> getAllUnapproved() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(false));
		return this.getAll(query);
	}
	
	public List<T> getAllApproved() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		return this.getAll(query);
	}
	
	public List<T> getAllUnapprovedWithJob() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(false));
		query.addRelation(APPLICATION_JOB_RELATION);
		return this.getAll(query);
	}
	
	public List<T> getAllApprovedWithJob() throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(true));
		query.addRelation(APPLICATION_JOB_RELATION);
		return this.getAll(query);
	}

	public T getbySocialSecurityNumber(String social) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(social));		
		return this.get(query);
	}
	
}
