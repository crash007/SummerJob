package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Area;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class AreaDAO extends AnnotatedDAO<Area> {

	public AreaDAO(DataSource dataSource, Class<Area> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(Area bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Area getAreaById(Integer areaId) throws SQLException {
		HighLevelQuery<Area> query = new HighLevelQuery<Area>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(areaId));
		return this.get(query);
	}
	
	public Area getAreaByName(String name) throws SQLException {
		HighLevelQuery<Area> query = new HighLevelQuery<Area>();
		query.addParameter(this.getParamFactory("name", String.class).getParameter(name));
		return this.get(query);
	}
	
	public List<Area> getAllAreas(boolean pickable) throws SQLException {
		HighLevelQuery<Area> query = new HighLevelQuery<Area>();
		query.addParameter(this.getParamFactory("canBeChosenInApplication", Boolean.class).getParameter(pickable));
		return this.getAll(query);
	}
}
