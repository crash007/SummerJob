package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.AccountingEntry;
import se.sogeti.periodsadmin.beans.ContactPerson;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class ContactPersonDAO extends AnnotatedDAO<ContactPerson> {

	public ContactPersonDAO(DataSource dataSource,
			Class<ContactPerson> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(ContactPerson bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}

	public ContactPerson getOne() throws SQLException {
		List<ContactPerson> result = this.getAll();
		
		if(result!=null){
			return result.get(0);
		}else
			return null;
	}
}
