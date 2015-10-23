package se.sogeti.summerjob.addsummerjob;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.MunicipalityJob;

import se.sogeti.jobapplications.daos.MunicipalityJobDAO;


import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddBusinessSectorSummerJobModule extends AnnotatedRESTModule{
	
	
	//private MunicipalityJobDAO municipalityJobDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
	//	this.daoFactory = new FlowEngineDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);

			
		
		//municipalityJobDAO = new MunicipalityJobDAO(dataSource, MunicipalityJob.class, hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		Element jobForm = doc.createElement("BusinessSectorJobForm");
		doc.getFirstChild().appendChild(jobForm);
				
		return new SimpleForegroundModuleResponse(doc);
		
	}

}
