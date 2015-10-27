package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class JobApplicationDAO<T extends JobApplication> extends AnnotatedDAO<T> {

	public JobApplicationDAO(DataSource dataSource,
			Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(T bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public T getApplicationById(Integer jobApplicationId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobApplicationId));
		return this.get(query);
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
	
}
