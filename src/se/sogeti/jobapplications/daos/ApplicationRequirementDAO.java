package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.ApplicationRequirement;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class ApplicationRequirementDAO extends AnnotatedDAO<ApplicationRequirement>{

	public ApplicationRequirementDAO(DataSource dataSource,
			Class<ApplicationRequirement> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(ApplicationRequirement bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public ApplicationRequirement getRequirementById(Integer id) throws SQLException {
		HighLevelQuery<ApplicationRequirement> query = new HighLevelQuery<ApplicationRequirement>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(id));
		return this.get(query);
	}
	
	public List<ApplicationRequirement> getAllRequirements(Integer municipalityJobId) throws SQLException {
		HighLevelQuery<ApplicationRequirement> query = new HighLevelQuery<ApplicationRequirement>();
		query.addParameter(this.getParamFactory("municipalityJobId", Integer.class).getParameter(municipalityJobId));
		return this.getAll(query);
	}
}
