package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class AreaDAO extends AnnotatedDAO<MunicipalityJobArea> {

	public AreaDAO(DataSource dataSource, Class<MunicipalityJobArea> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(MunicipalityJobArea bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public MunicipalityJobArea getAreaById(Integer areaId) throws SQLException {
		HighLevelQuery<MunicipalityJobArea> query = new HighLevelQuery<MunicipalityJobArea>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(areaId));
		return this.get(query);
	}
	
	public MunicipalityJobArea getAreaByName(String name) throws SQLException {
		HighLevelQuery<MunicipalityJobArea> query = new HighLevelQuery<MunicipalityJobArea>();
		query.addParameter(this.getParamFactory("name", String.class).getParameter(name));
		return this.get(query);
	}
	
	public List<MunicipalityJobArea> getAllAreas(boolean pickable) throws SQLException {
		HighLevelQuery<MunicipalityJobArea> query = new HighLevelQuery<MunicipalityJobArea>();
		query.addParameter(this.getParamFactory("canBeChosenInApplication", Boolean.class).getParameter(pickable));
		return this.getAll(query);
	}
}
