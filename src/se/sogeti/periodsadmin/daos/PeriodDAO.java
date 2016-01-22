package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.Period;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.enums.Order;

/**
 * 
 * @author Petter Johansson
 *
 */

public class PeriodDAO extends AnnotatedDAO<Period> {
	

	public PeriodDAO(DataSource dataSource, Class<Period> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(Period bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Period getById(Integer id) throws SQLException {
		HighLevelQuery<Period> query = new HighLevelQuery<Period>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		return this.get(query);
	}
	
	public Period getLatestCreatedPeriod() throws SQLException {
		List<Period> periods = this.getAll();
		return periods.get(periods.size() - 1);
	}
	
	public List<Period> getPeriodsOrderedByDateAndIsUnique(boolean isUnique) throws SQLException {
		HighLevelQuery<Period> query = new HighLevelQuery<Period>();
		query.addParameter(this.getParamFactory("isUnique", Boolean.class).getParameter(isUnique));
		query.addOrderByCriteria(this.getOrderByCriteria("startDate", Order.ASC));
		return this.getAll(query);
	}
	
	public List<Period> getPeriodsByIsUnique(boolean isUnique) throws SQLException {
		HighLevelQuery<Period> query = new HighLevelQuery<Period>();
		query.addParameter(this.getParamFactory("isUnique", Boolean.class).getParameter(isUnique));
		query.addOrderByCriteria(this.getOrderByCriteria("id", Order.ASC));
		return this.getAll(query);
	}
}
