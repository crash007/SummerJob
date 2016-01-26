package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
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

//	public List<BusinessSectorJobApplication> getCandidatesFulfillingCriteras(BusinessSectorJob job) throws SQLException {
//		 HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
//		 
//		 query.addParameter(this.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
//		 query.addParameter(this.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
//		 
//		 if(job.getMustBeOverEighteen()){
//			 Calendar cal = Calendar.getInstance();
//				cal.setTime(job.getStartDate());					
//				cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
//				Date bornBefore = new Date(cal.getTime().getTime());
//			 query.addParameter(this.getParamFactory("birthDate", Date.class).getParameter(bornBefore,QueryOperators.SMALLER_THAN_OR_EUALS));
//		 }
//		 
//		 query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(job.getDriversLicenseType(),QueryOperators.BIGGER_THAN_OR_EUALS));	 
//		 		 
//		 query.addOrderByCriteria(this.getOrderByCriteria("ranking", Order.ASC));
//		 return this.getAll(query);
//		 
//	}

//	//TODO Nu är vilkoren och'ade, ska vara eller.
//	public List<BusinessSectorJobApplication> getCandidatesNotFulfillingCriteras(BusinessSectorJob job) throws SQLException {
//		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
//		 
//		 query.addParameter(this.getParamFactory("job", BusinessSectorJob.class).getParameter(job));
//		 query.addParameter(this.getParamFactory("status", ApplicationStatus.class).getParameter(ApplicationStatus.NONE));
//		 
//		 if(job.getMustBeOverEighteen()){
//			 Calendar cal = Calendar.getInstance();
//				cal.setTime(job.getStartDate());					
//				cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-18);
//				Date bornBefore = new Date(cal.getTime().getTime());
//			 query.addParameter(this.getParamFactory("birthDate", Date.class).getParameter(bornBefore,QueryOperators.BIGGER_THAN));
//		 }
//		 
//		 query.addParameter(this.getParamFactory("driversLicenseType", DriversLicenseType.class).getParameter(job.getDriversLicenseType(), QueryOperators.SMALLER_THAN));	 
//		 
//		 
//		 query.addOrderByCriteria(this.getOrderByCriteria("ranking", Order.ASC));
//		 return this.getAll(query);
//	}
	
	public BusinessSectorJobApplication getWithJob() throws SQLException {
        HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
			
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
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
	public List<BusinessSectorJobApplication> getAllByApprovedWithJobByDescendingOrder(String socialSecurityNumber, String firstname, String lastname, String personalLetter, boolean approved, boolean orderByDescending) throws SQLException {
		HighLevelQuery<BusinessSectorJobApplication> query = new HighLevelQuery<BusinessSectorJobApplication>();
		
		if (!StringUtils.isEmpty(socialSecurityNumber)) {
			query.addParameter(this.getParamFactory("socialSecurityNumber", String.class).getParameter(socialSecurityNumber + "%", QueryOperators.LIKE));
		}
		
		if (!StringUtils.isEmpty(firstname)) {
			query.addParameter(this.getParamFactory("firstname", String.class).getParameter(firstname + "%", QueryOperators.LIKE));
		}
		
		if (!StringUtils.isEmpty(lastname)) {
			query.addParameter(this.getParamFactory("lastname", String.class).getParameter(lastname + "%", QueryOperators.LIKE));
		}
		
		if (!StringUtils.isEmpty(lastname)) {
			query.addParameter(this.getParamFactory("personalLetter", String.class).getParameter("%" + personalLetter + "%", QueryOperators.LIKE));
		}
		
		query.addParameter(this.getParamFactory("approved", Boolean.class).getParameter(approved));
		Order order = null;
		if (orderByDescending) {
			order = Order.DESC;
		} else {
			order = Order.ASC;
		}
		
		OrderByCriteria<BusinessSectorJobApplication> orderByRanking = this.getOrderByCriteria("ranking", Order.ASC);
		OrderByCriteria<BusinessSectorJobApplication> orderByCreated = this.getOrderByCriteria("created", order);
		query.addOrderByCriteria(orderByRanking);
		query.addOrderByCriteria(orderByCreated);
		
		query.addRelation(APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(APPLICATION_JOB_RELATION);
		query.disableAutoRelations(true);
		return this.getAll(query);
	}

}
