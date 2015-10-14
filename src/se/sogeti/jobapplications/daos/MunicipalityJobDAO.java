package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;

public class MunicipalityJobDAO extends AnnotatedDAO<MunicipalityJob>{

	public MunicipalityJobDAO(DataSource dataSource, Class<MunicipalityJob> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}
	
	public void save(MunicipalityJob bean) throws SQLException{
		this.addOrUpdate(bean, null);
	}

}
