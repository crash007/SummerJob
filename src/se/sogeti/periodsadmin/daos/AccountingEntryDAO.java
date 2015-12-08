package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.AccountingEntry;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class AccountingEntryDAO extends AnnotatedDAO<AccountingEntry> {

	public AccountingEntryDAO(DataSource dataSource,
			Class<AccountingEntry> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(AccountingEntry bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public AccountingEntry getById(Integer id) throws SQLException {
		HighLevelQuery<AccountingEntry> query = new HighLevelQuery<AccountingEntry>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		return this.get(query);
	}
}
