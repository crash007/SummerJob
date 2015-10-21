package se.sogeti.periodsadmin.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.periodsadmin.JsonResponse;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PeriodViewModule extends AnnotatedRESTModule {

	private PeriodDAO periodDAO;

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
			throws Throwable {
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);	
		
		Element periodsElement = doc.createElement("Periods");
		
		List<Period> periodList = periodDAO.getPeriodsOrderedByDate();
		
		if(periodList != null){
			for (Period p : periodList) {
				Element period = doc.createElement("Period");
				XMLUtils.appendNewElement(doc, period, "Name", p.getName());
				XMLUtils.appendNewElement(doc, period, "FromDate", p.getFromDate());
				XMLUtils.appendNewElement(doc, period, "ToDate", p.getToDate());
				XMLUtils.appendNewElement(doc, period, "ID", p.getAlternativeID());
				periodsElement.appendChild(period);
			}
		}
		doc.getFirstChild().appendChild(periodsElement);
		
		return new SimpleForegroundModuleResponse(doc, this.getDefaultBreadcrumb());
	}

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		periodDAO = new PeriodDAO(dataSource, Period.class, daoFactory);
	}
	
	@RESTMethod(alias="add/period.json", method="post")
	public void addPeriod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		
		GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
        
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        String name = req.getParameter("new-period-name");
        String fromdate = req.getParameter("new-period-fromdate");
        String todate = req.getParameter("new-period-todate");
        
		if(name == null || name.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Namn saknas på perioden.\"}", callback, writer);
			return;
		}
		
		if(fromdate == null || fromdate.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Startdatum saknas på perioden.\"}", callback, writer);
			return;
		}
		
		if(todate == null || todate.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Slutdatum saknas på perioden.\"}", callback, writer);
			return;
		}
		
		Period p1 = periodDAO.getLatestCreatedPeriod();
		
		try {
			Period period = new Period();
			period.setName(name);
			period.setFromDate(Date.valueOf(fromdate));
			period.setToDate(Date.valueOf(todate));
			period.setQueryID(p1.getQueryID());
			periodDAO.save(period);
			period = periodDAO.getLatestCreatedPeriod();
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"data\":" + gson.toJson(period) + "}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception",e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel.\"}", callback, writer);
			return;
		}				
	}
	
	@RESTMethod(alias="update/periods.json", method="post")
	public void updatePeriods(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        
        List<Integer> ids = new ArrayList<Integer>();
		
        JsonResponse.initJsonResponse(res, writer, callback);
        Map arraydata = req.getParameterMap();
        for(Object key : arraydata.keySet()){
        	String keyStr = (String) key;
        	String id = keyStr.split("_")[2];
        	ids.add(Integer.parseInt(id));
        }
        
        ids = new ArrayList<Integer>(new LinkedHashSet<Integer>(ids));
        
        Period p1;
        
        p1 = periodDAO.getLatestCreatedPeriod();

        for (Integer id : ids) {
        	log.info("Efter LinkedHashSet: " + id);
        	Period p = new Period();
        	p.setQueryID(p1.getQueryID());
        	p.setAlternativeID(id);
        	p.setName(req.getParameter("period_name_" + id));
        	p.setFromDate(Date.valueOf(req.getParameter("period_fromdate_" + id)));
        	p.setToDate(Date.valueOf(req.getParameter("period_todate_" + id)));
        	
        	if ((p.getName() == null || p.getName().isEmpty()) || 
        			p.getFromDate() == null || p.getToDate() == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Alla fält för perioden måste vara ifyllda.\"}", callback, writer);
        		return;
        	} else if (p.getAlternativeID() == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Något gick fel: 'ID IS MISSING'.\"}", callback, writer);
        		return;
        	}
        	
        	try {
				periodDAO.update(p);
			} catch (SQLException e) {
	        	log.error("Exception when trying to update the periods: ", e);
	        	JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel.\"}", callback, writer);
				return;
			}        	
        }
        
        JsonResponse.sendJsonResponse("{\"status\":\"success\", \"data\":\"Uppdatering genomförd.\"}", callback, writer);
	}
}
