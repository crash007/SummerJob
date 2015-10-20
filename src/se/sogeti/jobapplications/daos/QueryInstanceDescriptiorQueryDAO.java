package se.sogeti.jobapplications.daos;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.nordicpeak.flowengine.beans.QueryInstanceDescriptor;

import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryParameter;
import se.unlogic.standardutils.dao.QueryParameterFactory;

public class QueryInstanceDescriptiorQueryDAO extends AnnotatedDAO<QueryInstanceDescriptor>{
	
	

	public QueryInstanceDescriptiorQueryDAO(DataSource dataSource, Class<QueryInstanceDescriptor> beanClass,
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

	public List<QueryInstanceDescriptor> getByFlowInstanceId(Integer flowInstanceId) throws SQLException{
		HighLevelQuery<QueryInstanceDescriptor> query = new HighLevelQuery<QueryInstanceDescriptor>();
		
		query.addParameter(this.getParamFactory("flowInstanceID", Integer.class).getParameter(flowInstanceId));
		query.addRelation(QueryInstanceDescriptor.QUERY_DESCRIPTOR_RELATION);
		return this.getAll(query);
		
	}
}
