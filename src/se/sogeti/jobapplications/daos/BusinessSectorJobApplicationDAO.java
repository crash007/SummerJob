package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.OrderByCriteria;
import se.unlogic.standardutils.dao.QueryOperators;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.string.StringUtils;

public class BusinessSectorJobApplicationDAO extends JobApplicationDAO<BusinessSectorJobApplication>{
	
	public static final Field APPLICATION_JOB_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "job");
	public static final Field APPLICATION_DRIVERS_LICENSE_TYPE_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "driversLicenseType");
	public static final Field APPLICATION_PERSON_APPLICATIONS_RELATION = ReflectionUtils.getField(BusinessSectorJobApplication.class, "personApplications");
	
	public BusinessSectorJobApplicationDAO(DataSource dataSource, Class<BusinessSectorJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}

	public BusinessSectorJobApplication getByIdWithJobAndPersonApplications(Integer id) throws SQLException {
        HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
        query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
			
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		query.addRelation(APPLICATION_PERSON_APPLICATIONS_RELATION);
		query.disableAutoRelations(true);
        return this.get(query);
	}

	
	/**
	 * socialSecurityNumber, firstname and lastname is used in a LIKE search.
	 * socialSecurityNumber, firstname and lastname is optional.
	 * @param socialSecurityNumber
	 * @param firstname
	 * @param lastname
	 * @param approved
	 * @param orderByDescending
	 * @return
	 * @throws SQLException
	 */
	public List<BusinessSectorJobApplication> getAllWithJob(String socialSecurityNumber, String firstname, String lastname, String personalLetter, Boolean approved,ApplicationStatus status, Order order) throws SQLException {
		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		
		query = this.createCommonQuery(socialSecurityNumber, firstname, lastname, personalLetter, approved, status, order);
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		query.disableAutoRelations(true);
		return this.getAll(query);
	}
	
	public List<BusinessSectorJobApplication> getAllByStatusWithJob(ApplicationStatus status) throws SQLException{
		HighLevelQuery<BusinessSectorJobApplication> query = this.createCommonQuery(null, null, null, null, null, status, Order.DESC);
		query.disableAutoRelations(true);
		query.addRelation(APPLICATION_JOB_RELATION);
		return this.getAll(query);
	}

}
