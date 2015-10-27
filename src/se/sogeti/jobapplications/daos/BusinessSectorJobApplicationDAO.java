package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class BusinessSectorJobApplicationDAO extends AnnotatedDAO<BusinessSectorJobApplication> {

	public BusinessSectorJobApplicationDAO(DataSource dataSource,
			Class<BusinessSectorJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public void save(BusinessSectorJobApplication bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public BusinessSectorJobApplication getByApplicationId(Integer applicationId) throws SQLException {
		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(applicationId));
		return this.get(query);
	}
	
	public List<BusinessSectorJobApplication> getApplicationsByJobId(Integer jobId) throws SQLException {
		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		query.addParameter(this.getParamFactory("businessSectorJobId", Integer.class).getParameter(jobId));
		return this.getAll(query);
	}
}
