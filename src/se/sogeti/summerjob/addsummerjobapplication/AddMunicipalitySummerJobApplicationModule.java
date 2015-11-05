package se.sogeti.summerjob.addsummerjobapplication;


import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.sogeti.summerjob.FormUtils;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddMunicipalitySummerJobApplicationModule extends AnnotatedRESTModule{
	
	
	private JobApplicationDAO<MunicipalityJobApplication> jobApplicationDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	private GeoAreaDAO geoAreaDAO;
	
	@InstanceManagerDependency(required = true)
	private SmexServiceHandler smexServiceHandler;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		jobApplicationDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, MunicipalityJobArea.class, hierarchyDaoFactory);
		periodDAO = new PeriodDAO(dataSource, Period.class, hierarchyDaoFactory);
		geoAreaDAO = new GeoAreaDAO(dataSource, GeoArea.class, hierarchyDaoFactory);
	
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		// TODO Auto-generated method stub
		
		if(req.getMethod().equals("POST")){
			log.info("POST");
			MunicipalityJobApplication app = new MunicipalityJobApplication();
			
			Citizen person=null;
			
			try{
				person = smexServiceHandler.getCitizen(req.getParameter("socialSecurityNumber"));	
			}catch( SmexServiceException e){
				log.error(e);
			}
			
			
			FormUtils.createJobApplication(app, req, person);
			
			Integer preferedArea1 = NumberUtils.toInt(req.getParameter("preferedArea1"));
			Integer preferedArea2 = NumberUtils.toInt(req.getParameter("preferedArea2"));
			Integer preferedArea3 = NumberUtils.toInt(req.getParameter("preferedArea3"));
			MunicipalityJobArea area1 = areaDAO.getAreaById(preferedArea1);
			MunicipalityJobArea area2 = areaDAO.getAreaById(preferedArea2);
			MunicipalityJobArea area3 = areaDAO.getAreaById(preferedArea3);
			
			
			app.setPreferedArea1(area1);
			app.setPreferedArea2(area2);
			app.setPreferedArea3(area3);
			
			Integer preferedGeoArea1 = NumberUtils.toInt(req.getParameter("geoArea1"));
			Integer preferedGeoArea2 = NumberUtils.toInt(req.getParameter("geoArea2"));
			Integer preferedGeoArea3 = NumberUtils.toInt(req.getParameter("geoArea3"));
			GeoArea geoArea1 = geoAreaDAO.getAreaById(preferedGeoArea1);
			GeoArea geoArea2 = geoAreaDAO.getAreaById(preferedGeoArea2);
			GeoArea geoArea3 = geoAreaDAO.getAreaById(preferedGeoArea3);
			
			app.setPreferedGeoArea1(geoArea1);
			app.setPreferedGeoArea2(geoArea2);
			app.setPreferedGeoArea3(geoArea3);
			
			
			log.info(app);
			jobApplicationDAO.add(app);
		
		}
		
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		Element form = doc.createElement("MunicipalityJobApplicationForm");
		doc.getFirstChild().appendChild(form);
		
		Element areasElement = doc.createElement("Areas");
		List<MunicipalityJobArea> areas = areaDAO.getAll();		
		XMLUtils.append(doc, areasElement,areas);
		form.appendChild(areasElement);	
		
		Element geoAreasElement = doc.createElement("GeoAreas");
		List<GeoArea> geoAreas = geoAreaDAO.getAll();
		XMLUtils.append(doc, geoAreasElement, geoAreas);
		form.appendChild(geoAreasElement);
		
		
		return new SimpleForegroundModuleResponse(doc);
		
	}
	
}
