package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Area;
import se.sogeti.jobapplications.beans.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class MunicipalityJobApplicationDAO extends AnnotatedDAO<MunicipalityJobApplication> {

	public MunicipalityJobApplicationDAO(DataSource dataSource,
			Class<MunicipalityJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(MunicipalityJobApplication bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public MunicipalityJobApplication getApplicationById(Integer municipalityJobApplicationId) throws SQLException {
		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(municipalityJobApplicationId));
		return this.get(query);
	}

	/**
	 * To get all applications that has been matched to a certain job.
	 */
	public List<MunicipalityJobApplication> getApplicationsByJobId(Integer municipalityJobId) throws SQLException {
		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
		query.addParameter(this.getParamFactory("municipalityJobId", Integer.class).getParameter(municipalityJobId));
		return this.getAll(query);
	}
	
//	public List<MunicipalityJobApplication> getApplicationsByPreferedArea(Area area) throws SQLException {
//		
//		return null;
//	}
//	
//	public List<MunicipalityJobApplication> getApplicationsByPreferedArea(Integer areaId) throws SQLException {
//		
//		return null;
//	}
}
