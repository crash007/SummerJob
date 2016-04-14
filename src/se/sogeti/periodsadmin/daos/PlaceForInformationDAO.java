package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.PlaceForInformation;
import se.sogeti.periodsadmin.beans.Salary;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class PlaceForInformationDAO extends AnnotatedDAO<PlaceForInformation> {

	public PlaceForInformationDAO(DataSource dataSource,
			Class<PlaceForInformation> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(PlaceForInformation bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public PlaceForInformation getById(Integer id) throws SQLException {
		HighLevelQuery<PlaceForInformation> query = new HighLevelQuery<PlaceForInformation>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		return this.get(query);
	}
	
	public PlaceForInformation getOne() throws SQLException {
		List<PlaceForInformation> result = this.getAll();
		if(result!=null){
			return result.get(0);
		}else{
			return null;
		}
	}
	
}
