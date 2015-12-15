package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.PersonApplications;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class PersonApplicationsDAO extends AnnotatedDAO<PersonApplications> {

	public static final Field PERSON_APPLICATIONS_MUNICIPALITY_APPLICATIONS = ReflectionUtils.getField(PersonApplications.class, "municipalityApplications");
	public static final Field PERSON_APPLICATIONS_BUSINESS_APPLICATIONS = ReflectionUtils.getField(PersonApplications.class, "businessApplications");
	
	public PersonApplicationsDAO(DataSource dataSource, Class<PersonApplications> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
		
	public PersonApplications getBySocialSecurityNumber(String socialSecurityNumber) throws SQLException {
		HighLevelQuery<PersonApplications> query = new HighLevelQuery<PersonApplications>();
		query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(socialSecurityNumber));
		query.disableAutoRelations(true);
		query.addRelation(PERSON_APPLICATIONS_BUSINESS_APPLICATIONS);
		return this.get(query);
	}
	
}
