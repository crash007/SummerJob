package se.sogeti.periodsadmin.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.periodsadmin.beans.ContactPerson;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;

public class ContactPersonDAO extends AnnotatedDAO<ContactPerson> {

	public ContactPersonDAO(DataSource dataSource,
			Class<ContactPerson> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(ContactPerson bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
}
