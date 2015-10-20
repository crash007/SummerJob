package se.sogeti.jobapplications;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nordicpeak.flowengine.FlowAdminModule;
import com.nordicpeak.flowengine.beans.FlowInstance;
import com.nordicpeak.flowengine.beans.QueryInstanceDescriptor;

import com.nordicpeak.flowengine.queries.dropdownquery.DropDownQueryInstance;
import com.nordicpeak.flowengine.queries.simpleaddressquery.SimpleAddressQueryInstance;
import com.nordicpeak.flowengine.queries.textfieldquery.TextFieldQueryInstance;

import se.sogeti.jobapplications.beans.MunicipalityJob;
import se.sogeti.jobapplications.daos.DropDownInstanceQueryDAO;
import se.sogeti.jobapplications.daos.MunicipalityJobDAO;
import se.sogeti.jobapplications.daos.QueryInstanceDescriptiorQueryDAO;
import se.sogeti.jobapplications.daos.SimpleAddressDAO;
import se.sogeti.jobapplications.daos.TextFieldInstanceQueryDAO;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.enums.EventSource;
import se.unlogic.hierarchy.core.events.CRUDEvent;
import se.unlogic.hierarchy.core.interfaces.EventHandler;
import se.unlogic.hierarchy.core.interfaces.EventListener;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SummerJobAdminModule extends AnnotatedForegroundModule implements EventListener<CRUDEvent<?>>{
	
	@InstanceManagerDependency(required = true)
	private FlowAdminModule flowAdminModule;
	
	//public static final Field[] FLOW_INSTANCE_RELATIONS = {FlowInstance.EVENTS_RELATION, FlowInstanceEvent.ATTRIBUTES_RELATION, FlowInstance.FLOW_RELATION, FlowInstance.FLOW_STATE_RELATION, Flow.FLOW_TYPE_RELATION, Flow.FLOW_FAMILY_RELATION, FlowType.ALLOWED_GROUPS_RELATION, FlowType.ALLOWED_USERS_RELATION, Flow.STEPS_RELATION, Flow.FLOW_FAMILY_RELATION, FlowFamily.MANAGER_GROUPS_RELATION, FlowFamily.MANAGER_USERS_RELATION, Step.QUERY_DESCRIPTORS_RELATION, QueryDescriptor.EVALUATOR_DESCRIPTORS_RELATION, Flow.DEFAULT_FLOW_STATE_MAPPINGS_RELATION, DefaultStatusMapping.FLOW_STATE_RELATION, QueryDescriptor.QUERY_INSTANCE_DESCRIPTORS_RELATION, FlowInstance.ATTRIBUTES_RELATION};
	//protected QueryParameterFactory<FlowInstance, Integer> flowInstanceIDParamFactory;
	//protected QueryParameterFactory<QueryInstanceDescriptor, Integer> queryInstanceDescriptorFlowInstanceIDParamFactory;
	
	
	

	private static final Class[] EVENT_LISTENER_CLASSES = new Class[] { FlowInstance.class };
	protected EventHandler eventHandler;
	
	//@InstanceManagerDependency(required = true)
	//protected QueryHandler queryHandler;
	
	private SimpleAddressDAO simpleAddressDAO;

	//protected FlowEngineDAOFactory daoFactory;
	
	//@InstanceManagerDependency(required = true)
	//protected EvaluationHandler evaluationHandler;
	
	//protected QueryDescriptorCRUD queryDescriptorCRUD;

	private QueryInstanceDescriptiorQueryDAO queryInstanceDescriptiorQueryDAO;
	private DropDownInstanceQueryDAO dropDownInstanceQueryDAO;
	private TextFieldInstanceQueryDAO textFieldInstanceQueryDAO; 
	private MunicipalityJobDAO municipalityJobDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
	//	this.daoFactory = new FlowEngineDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		eventHandler = systemInterface.getEventHandler();
		eventHandler.addEventListener(CRUDEvent.class, this, EVENT_LISTENER_CLASSES);

		simpleAddressDAO = new SimpleAddressDAO(dataSource, SimpleAddressQueryInstance.class, hierarchyDaoFactory);		
		queryInstanceDescriptiorQueryDAO = new QueryInstanceDescriptiorQueryDAO(dataSource, QueryInstanceDescriptor.class, hierarchyDaoFactory);
		dropDownInstanceQueryDAO = new DropDownInstanceQueryDAO(dataSource, DropDownQueryInstance.class, hierarchyDaoFactory);
		textFieldInstanceQueryDAO = new TextFieldInstanceQueryDAO(dataSource, TextFieldQueryInstance.class, hierarchyDaoFactory);
		
		municipalityJobDAO = new MunicipalityJobDAO(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
		//this.daoFactory = new FlowEngineDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());
		
		//AnnotatedDAOWrapper<QueryDescriptor, Integer> queryDescriptorDAOWrapper = daoFactory.getQueryDescriptorDAO().getWrapper(Integer.class);
		//queryDescriptorDAOWrapper.addRelations(QueryDescriptor.STEP_RELATION, Step.FLOW_RELATION, Flow.FLOW_TYPE_RELATION, Flow.CATEGORY_RELATION, FlowType.ALLOWED_GROUPS_RELATION, FlowType.ALLOWED_USERS_RELATION, QueryDescriptor.EVALUATOR_DESCRIPTORS_RELATION);
		//queryDescriptorDAOWrapper.setUseRelationsOnGet(true);

		//flowInstanceIDParamFactory = daoFactory.getFlowInstanceDAO().getParamFactory("flowInstanceID", Integer.class);
		//queryInstanceDescriptorFlowInstanceIDParamFactory = daoFactory.getQueryInstanceDescriptorDAO().getParamFactory("flowInstanceID", Integer.class);

	}


	@Override
	public void unload() throws Exception {
		// TODO Auto-generated method stub
		eventHandler.removeEventListener(CRUDEvent.class, this, EVENT_LISTENER_CLASSES);
		super.unload();
	}


	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void processEvent(CRUDEvent<?> event, EventSource eventSource) {
		// TODO Auto-generated method stub
		log.info("Received event");
		log.info(event);
		FlowInstance bean = (FlowInstance) event.getBeans().get(0);
		log.info(bean.getFlowInstanceID());
		
		log.info(eventSource);
		
		
	}

	private MunicipalityJob populateMunicipalityJob(FlowInstance flowInstance) throws SQLException{
		
		MunicipalityJob municipalityJob = new MunicipalityJob();
		municipalityJob.setFlowInstanceId(flowInstance.getFlowInstanceID());
		
		List<QueryInstanceDescriptor> qid = queryInstanceDescriptiorQueryDAO.getByFlowInstanceId(flowInstance.getFlowInstanceID());
		
		for(QueryInstanceDescriptor q: qid){
			log.info(q);
			log.info(q.getQueryDescriptor().getQueryTypeID());
			
			Integer queryId = q.getQueryDescriptor().getQueryID();
			
			if("com.nordicpeak.flowengine.queries.dropdownquery.DropDownQueryProviderModule".equals(q.getQueryDescriptor().getQueryTypeID())){
				DropDownQueryInstance dropDownQueryInstance = dropDownInstanceQueryDAO.getByInstanceId(q.getQueryInstanceID());
				
				if(dropDownQueryInstance.getAlternative()!=null){
					log.info("Svar: "+dropDownQueryInstance.getAlternative().getName());
				}
				
				//Organisation
				if(queryId==3855){
					log.info("Adding organisation to munucipality bean.");
					municipalityJob.setOrganisationDropDownQueryInstanceId(dropDownQueryInstance.getQueryInstanceID());
				}
				
				//Field, tex barnomsorg
				if(queryId==3856 || queryId ==3857|| queryId==3858){
					log.info("Adding field to munucipality bean.");
					if(dropDownQueryInstance.getAlternative()!=null){
						municipalityJob.setFieldDropDownInstanceId(dropDownQueryInstance.getQueryInstanceID());
					}
				}
				
				
				
			}
			
			if("com.nordicpeak.flowengine.queries.simpleaddressquery.SimpleAddressQueryProviderModule".equals(q.getQueryDescriptor().getQueryTypeID())){
				SimpleAddressQueryInstance address= simpleAddressDAO.getByInstanceId(q.getQueryInstanceID());
				log.info("Address: "+address);
				municipalityJob.setWorkplaceSimpleAdressId(address.getQueryInstanceID());
			}
			
			if("com.nordicpeak.flowengine.queries.textfieldquery.TextFieldQueryProviderModule".equals(q.getQueryDescriptor().getQueryTypeID())){
				TextFieldQueryInstance qi = textFieldInstanceQueryDAO.getByInstanceId(q.getQueryInstanceID());
				log.info("Textfield answer: "+qi);
			}
			
		}
		
		return municipalityJob;
		
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		FlowInstance flowInstance =flowAdminModule.getFlowInstance(806);
		 
		log.info(flowInstance);
		queryInstanceDescriptiorQueryDAO.getByFlowInstanceId(flowInstance.getFlowInstanceID());
		MunicipalityJob job = populateMunicipalityJob(flowInstance);
		log.info(job);
		
		municipalityJobDAO.addOrUpdate(job, null);
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		
		
		
		return new SimpleForegroundModuleResponse(doc);
		
	}


//	@Override
//	public EvaluationHandler getEvaluationHandler() {
//		return evaluationHandler;
//	}
//
//
//	@Override
//	public QueryHandler getQueryHandler() {
//		return queryHandler;
//	}
//
//
//	@Override
//	public SystemInterface getSystemInterface() {
//		return systemInterface;
//	}
//
//
//	@Override
//	public FlowEngineDAOFactory getDAOFactory() {
//		return daoFactory;
//	}
	
//	
//	public FlowInstance getFlowInstance(int flowInstanceID) throws SQLException {
//
//		HighLevelQuery<FlowInstance> query = new HighLevelQuery<FlowInstance>(FLOW_INSTANCE_RELATIONS);
//
//		query.addParameter(flowInstanceIDParamFactory.getParameter(flowInstanceID));
//
//		query.addRelationParameter(QueryInstanceDescriptor.class, queryInstanceDescriptorFlowInstanceIDParamFactory.getParameter(flowInstanceID));
//
//		return daoFactory.getFlowInstanceDAO().get(query);
//	}

}
