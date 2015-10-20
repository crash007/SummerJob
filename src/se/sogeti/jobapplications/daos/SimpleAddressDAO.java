package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.nordicpeak.flowengine.queries.simpleaddressquery.SimpleAddressQueryInstance;

import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class SimpleAddressDAO extends AnnotatedDAO<SimpleAddressQueryInstance>{

	public SimpleAddressDAO(DataSource dataSource, Class<SimpleAddressQueryInstance> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}
	
	public SimpleAddressQueryInstance getByInstanceId(Integer id) throws SQLException{
		HighLevelQuery<SimpleAddressQueryInstance> query = new HighLevelQuery<SimpleAddressQueryInstance>();
		query.addParameter(this.getParamFactory("queryInstanceID", Integer.class).getParameter(id));
		return this.get(query);
		
	}

}
