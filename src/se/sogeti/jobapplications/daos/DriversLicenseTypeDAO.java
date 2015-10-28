package se.sogeti.jobapplications.daos;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;

public class DriversLicenseTypeDAO extends AnnotatedDAO<DriversLicenseType> {

	public DriversLicenseTypeDAO(DataSource dataSource,
			Class<DriversLicenseType> beanClass, AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
	}

	public DriversLicenseType getTypeById(Integer driverslicenseTypeId) throws SQLException {
		HighLevelQuery<DriversLicenseType> query = new HighLevelQuery<DriversLicenseType>();
		query.addParameter(this.getParamFactory("id", Integer.class).getParameter(driverslicenseTypeId));
		return this.get(query);
	}
}
