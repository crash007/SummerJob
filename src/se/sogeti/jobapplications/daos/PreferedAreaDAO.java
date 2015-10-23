package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.PreferedArea;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class PreferedAreaDAO extends AnnotatedDAO<PreferedArea> {

	public PreferedAreaDAO(DataSource dataSource,
			Class<PreferedArea> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(PreferedArea bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public List<PreferedArea> getByAreaId(Integer areaId) throws SQLException {
		HighLevelQuery<PreferedArea> query = new HighLevelQuery<PreferedArea>();
		query.addParameter(this.getParamFactory("areaId", Integer.class).getParameter(areaId));
		return this.getAll(query);
	}
	
	public List<PreferedArea> getByApplicationId(Integer applicationId) throws SQLException {
		HighLevelQuery<PreferedArea> query = new HighLevelQuery<PreferedArea>();
		query.addParameter(this.getParamFactory("applicationId", Integer.class).getParameter(applicationId));
		return this.getAll(query);
	}
	
	public PreferedArea getById(Integer preferedAreaId) throws SQLException {
		HighLevelQuery<PreferedArea> query = new HighLevelQuery<PreferedArea>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(preferedAreaId));
		return this.get(query);
	}
}
