package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Manager;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

/**
 * 
 * @author Petter Johansson
 *
 */

public class ManagerDAO<T extends Manager> extends AnnotatedDAO<T> {

	public ManagerDAO(DataSource dataSource, Class<T> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public void save(T bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Manager getManagerById(Integer managerId) throws SQLException {
		HighLevelQuery<T> query = new HighLevelQuery<T>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(managerId));
		return this.get(query);
	}
}
