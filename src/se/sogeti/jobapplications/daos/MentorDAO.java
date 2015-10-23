package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.Mentor;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class MentorDAO extends AnnotatedDAO<Mentor> {

	public MentorDAO(DataSource dataSource, Class<Mentor> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}
	
	public void save(Mentor bean) throws SQLException {
		this.addOrUpdate(bean, null);
	}
	
	public Mentor getMentorById(Integer mentorId) throws SQLException {
		HighLevelQuery<Mentor> query = new HighLevelQuery<Mentor>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(mentorId));
		return this.get(query);
	}
	
	public List<Mentor> getAllMentors(Integer municipalityJobId) throws SQLException {
		HighLevelQuery<Mentor> query = new HighLevelQuery<Mentor>();
		query.addParameter(this.getParamFactory("municipalityJobId", Integer.class).getParameter(municipalityJobId));
		return this.getAll(query);
	}
}
