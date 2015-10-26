package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.BusinessSectorMentor;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class BusinessSectorMentorDAO extends AnnotatedDAO<BusinessSectorMentor> {

	public BusinessSectorMentorDAO(DataSource dataSource,
			Class<BusinessSectorMentor> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public void save(BusinessSectorMentor bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public BusinessSectorMentor getByMentorId(Integer mentorId) throws SQLException {
		HighLevelQuery<BusinessSectorMentor> query = new HighLevelQuery<BusinessSectorMentor>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(mentorId));
		return this.get(query);
	}
	
	public List<BusinessSectorMentor> getMentorsByJobId(Integer jobId) throws SQLException {
		HighLevelQuery<BusinessSectorMentor> query = new HighLevelQuery<BusinessSectorMentor>();
		query.addParameter(this.getParamFactory("businessSectorJobId", Integer.class).getParameter(jobId));
		return this.getAll(query);
	}
 }
