package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.nordicpeak.flowengine.beans.QueryInstanceDescriptor;
import com.nordicpeak.flowengine.queries.dropdownquery.DropDownQueryInstance;
import com.nordicpeak.flowengine.queries.textfieldquery.TextFieldQueryInstance;

import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryParameter;
import se.unlogic.standardutils.dao.QueryParameterFactory;

public class TextFieldInstanceQueryDAO extends AnnotatedDAO<TextFieldQueryInstance>{
	
	

	public TextFieldInstanceQueryDAO(DataSource dataSource, Class<TextFieldQueryInstance> beanClass,
			AnnotatedDAOFactory daoFactory) {
		super(dataSource, beanClass, daoFactory);
		// TODO Auto-generated constructor stub
	}
	
//	public Integer getByFlowInstanceIdAndQueryId(Integer flowInstanceId, Integer queryId) throws SQLException{
//		HighLevelQuery<QueryInstanceDescriptor> query = new HighLevelQuery<QueryInstanceDescriptor>();
//		
//		query.addParameter(this.getParamFactory("flowInstanceID", Integer.class).getParameter(flowInstanceId));
//		query.addParameter(this.getParamFactory("queryID", Integer.class).getParameter(queryId));
//		QueryInstanceDescriptor result =this.get(query);
//		
//		if(result!=null){
//			return result.getQueryInstanceID();
//		}
//		
//		return null;
//	}

	public TextFieldQueryInstance getByInstanceId(Integer instanceId) throws SQLException{
		HighLevelQuery<TextFieldQueryInstance> query = new HighLevelQuery<TextFieldQueryInstance>();
		
		query.addParameter(this.getParamFactory("queryInstanceID", Integer.class).getParameter(instanceId));
		query.addRelation(TextFieldQueryInstance.VALUES_RELATION);
		return this.get(query);
		
	}
}
