package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class GeoAreaDAO extends AnnotatedDAO<GeoArea> {

	public GeoAreaDAO(DataSource dataSource, Class<GeoArea> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(GeoArea bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public GeoArea getAreaById(Integer areaId) throws SQLException {
		HighLevelQuery<GeoArea> query = new HighLevelQuery<GeoArea>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(areaId));
		return this.get(query);
	}
	
	public GeoArea getAreaByName(String name) throws SQLException {
		HighLevelQuery<GeoArea> query = new HighLevelQuery<GeoArea>();
		query.addParameter(this.getParamFactory("geo_area", String.class).getParameter(name));
		return this.get(query);
	}
	
	
}
