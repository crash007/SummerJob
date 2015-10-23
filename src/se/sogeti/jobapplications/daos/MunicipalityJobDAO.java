package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class MunicipalityJobDAO extends AnnotatedDAO<MunicipalityJob>{

	public MunicipalityJobDAO(DataSource dataSource, Class<MunicipalityJob> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(MunicipalityJob bean) throws SQLException{
		this.addOrUpdate(bean, null);
	}
	
	public MunicipalityJob getById(Integer jobId) throws SQLException {
		HighLevelQuery<MunicipalityJob> query = new HighLevelQuery<MunicipalityJob>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(jobId));
		return this.get(query);
	}
}
