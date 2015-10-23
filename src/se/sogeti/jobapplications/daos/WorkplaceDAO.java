package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Workplace;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class WorkplaceDAO extends AnnotatedDAO<Workplace> {

	public WorkplaceDAO(DataSource dataSource, Class<Workplace> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public void save(Workplace bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Workplace getById(Integer workplaceId) throws SQLException {
		HighLevelQuery<Workplace> query = new HighLevelQuery<Workplace>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(workplaceId));
		return this.get(query);
	}
	
	public Workplace getByJobId(Integer jobId) throws SQLException {
		HighLevelQuery<Workplace> query = new HighLevelQuery<Workplace>();
		query.addParameter(this.getParamFactory("municipality_job_id", Integer.class).getParameter(jobId));
		return this.get(query);
	}
}
