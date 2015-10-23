package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Person;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class PersonDAO extends AnnotatedDAO<Person> {

	public PersonDAO(DataSource dataSource, Class<Person> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(Person bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
		
	public Person getById(Integer personId) throws SQLException {
		HighLevelQuery<Person> query = new HighLevelQuery<Person>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(personId));
		return this.get(query);
	}
	
	public List<Person> getAllMatchedForMunicipalityJob(boolean matched) throws SQLException {
		HighLevelQuery<Person> query = new HighLevelQuery<Person>();
		query.addParameter(this.getParamFactory("matchedForMunicipalityJob", Boolean.class).getParameter(matched));
		return this.getAll(query);
	}
}
