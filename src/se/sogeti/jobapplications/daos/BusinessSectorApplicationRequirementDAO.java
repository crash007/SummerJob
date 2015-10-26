package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.BusinessSectorApplicationRequirement;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class BusinessSectorApplicationRequirementDAO extends AnnotatedDAO<BusinessSectorApplicationRequirement> {

	public BusinessSectorApplicationRequirementDAO(DataSource dataSource,
			Class<BusinessSectorApplicationRequirement> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	
	public void save(BusinessSectorApplicationRequirement bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public BusinessSectorApplicationRequirement getByRequirementId(Integer requirementId) throws SQLException {
		HighLevelQuery<BusinessSectorApplicationRequirement> query = new HighLevelQuery<BusinessSectorApplicationRequirement>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(requirementId));
		return this.get(query);
	}
	
	public List<BusinessSectorApplicationRequirement> getRequirementsByJobId(Integer businessSectorJobId) throws SQLException {
		HighLevelQuery<BusinessSectorApplicationRequirement> query = new HighLevelQuery<BusinessSectorApplicationRequirement>();
		query.addParameter(this.getParamFactory("businessSectorJobId", Integer.class).getParameter(businessSectorJobId));
		return this.getAll(query);
	}
}
