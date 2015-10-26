package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.BusinessSectorJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class BusinessSectorJobDAO extends AnnotatedDAO<BusinessSectorJob> {

	public BusinessSectorJobDAO(DataSource dataSource,
			Class<BusinessSectorJob> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(BusinessSectorJob bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public BusinessSectorJob getByJobId(Integer jobId) throws SQLException {
		HighLevelQuery<BusinessSectorJob> query = new HighLevelQuery<BusinessSectorJob>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		return this.get(query);
	}
}
