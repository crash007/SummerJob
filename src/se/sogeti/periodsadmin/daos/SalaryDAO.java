package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.beans.Salary;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class SalaryDAO extends AnnotatedDAO<Salary> {

	public SalaryDAO(DataSource dataSource, Class<Salary> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(Salary bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Salary getById(Integer id) throws SQLException {
		HighLevelQuery<Salary> query = new HighLevelQuery<Salary>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		return this.get(query);
	}
	
	public Salary getByIsOverEighteen(Boolean overEighteen) throws SQLException {
		HighLevelQuery<Salary> query = new HighLevelQuery<Salary>();
		query.addParameter(this.getParamFactory("overEighteen", Boolean.class).getParameter(overEighteen));
		return this.get(query);
	}
}
