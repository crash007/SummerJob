package se.sogeti.jobapplications.daos;

import java.lang.reflect.Field;
import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
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
		
		query.disableAutoRelations(true);
        
        return this.get(query);
	}

}
