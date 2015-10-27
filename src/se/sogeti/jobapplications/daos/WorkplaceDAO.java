package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Job;
import se.sogeti.jobapplications.beans.Workplace;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class WorkplaceDAO<T extends Workplace> extends AnnotatedDAO<T> {

	public WorkplaceDAO(DataSource dataSource, Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public void save(T bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public T getById(Integer workplaceId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(workplaceId));
		return this.get(query);
	}
	
	public T getByJobId(Integer jobId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("job_id", Integer.class).getParameter(jobId));
		return this.get(query);
	}
}
