package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.ApplicationStatus;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.reflection.ReflectionUtils;

public class MunicipalityJobApplicationDAO extends JobApplicationDAO<MunicipalityJobApplication>{
	
	public static final Field MUNICIPALITY_APPLICATION_JOB_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "job");
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_AREA1_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea1");
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_AREA2_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea2");
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_AREA3_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedArea3");
	
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA1_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea1");
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA2_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea2");
	public static final Field MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA3_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "preferedGeoArea3");
	public static final Field MUNICIPALITY_APPLICATION_DRIVERS_LICENSE_TYPE_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "driversLicenseType");
	public static final Field MUNICIPALITY_APPLICATION_PERSON_APPLICATIONS_RELATION = ReflectionUtils.getField(MunicipalityJobApplication.class, "personApplications");
	
	public MunicipalityJobApplicationDAO(DataSource dataSource, Class<MunicipalityJobApplication> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}
	
	public MunicipalityJobApplication getByIdWithJob(Integer id) throws SQLException {
        HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
        query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		
        query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_AREA1_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_AREA2_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_AREA3_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA1_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA2_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PREFERED_GEO_AREA3_RELATION);		
		query.addRelation(MUNICIPALITY_APPLICATION_DRIVERS_LICENSE_TYPE_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_JOB_RELATION);
		query.addRelation(MUNICIPALITY_APPLICATION_PERSON_APPLICATIONS_RELATION);
		
		query.disableAutoRelations(true);
        
        return this.get(query);
	}
	
	public List<MunicipalityJobApplication> getAllByStatusWithJob(ApplicationStatus status) throws SQLException{
		HighLevelQuery<MunicipalityJobApplication> query = this.createCommonQuery(null, null, null, null, null, status, Order.DESC);
		query.disableAutoRelations(true);
		query.addRelation(MUNICIPALITY_APPLICATION_JOB_RELATION);
		return this.getAll(query);
	}
	
	public List<MunicipalityJobApplication> getAllWithJob(String socialSecurityNumber, String firstname, String lastname, String personalLetter, Boolean approved,ApplicationStatus status, Order order) throws SQLException {
		HighLevelQuery<MunicipalityJobApplication> query = new HighLevelQuery<MunicipalityJobApplication>();
		
		query = this.createCommonQuery(socialSecurityNumber, firstname, lastname, personalLetter, approved, status, order);
		
		query.addRelation(MUNICIPALITY_APPLICATION_JOB_RELATION);
		query.disableAutoRelations(true);
		return this.getAll(query);
	}
	
	
}
