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

import se.sogeti.periodsadmin.beans.AccountingEntry;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.beans.PlaceForInformation;
import se.sogeti.periodsadmin.beans.Salary;
import se.sogeti.periodsadmin.daos.AccountingEntryDAO;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.sogeti.periodsadmin.daos.PlaceForInformationDAO;
import se.sogeti.periodsadmin.daos.SalaryDAO;
import se.sogeti.summerjob.JsonResponse;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PeriodViewModule extends AnnotatedRESTModule {

	private PeriodDAO periodDAO;
	private SalaryDAO salaryDAO;
	private PlaceForInformationDAO placeDAO;
	private AccountingEntryDAO accountingEntryDAO;

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
		
		List<Salary> salaries = salaryDAO.getAll();
		Element salariesElement = doc.createElement("Salaries");
		XMLUtils.append(doc, salariesElement, salaries);
		doc.getFirstChild().appendChild(salariesElement);
		
		List<AccountingEntry> accountingEntries = accountingEntryDAO.getAll();
		Element accountingEntriesElement = doc.createElement("AccountingEntries");
		XMLUtils.append(doc, accountingEntriesElement, accountingEntries);
		doc.getFirstChild().appendChild(accountingEntriesElement);
		
		PlaceForInformation place = placeDAO.getById(1);
		XMLUtils.append(doc, element, place);
		
		Element periodsElement = doc.createElement("Periods");
		List<Period> periodList = periodDAO.getPeriodsOrderedByDate();
		
		if(periodList != null){
			for (Period p : periodList) {
				Element period = doc.createElement("Period");
				XMLUtils.appendNewElement(doc, period, "Name", p.getName());
				XMLUtils.appendNewElement(doc, period, "StartDate", p.getStartDate());
				XMLUtils.appendNewElement(doc, period, "EndDate", p.getEndDate());
				XMLUtils.appendNewElement(doc, period, "ID", p.getId());
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
		salaryDAO = new SalaryDAO(dataSource, Salary.class, daoFactory);
		placeDAO = new PlaceForInformationDAO(dataSource, PlaceForInformation.class, daoFactory);
		accountingEntryDAO = new AccountingEntryDAO(dataSource, AccountingEntry.class, daoFactory);
	}
	
	@RESTMethod(alias="add/period.json", method="post")
	public void addPeriod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		
//		GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
        
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
		
        JsonResponse.initJsonResponse(res, writer, callback);
        
        String name = req.getParameter("new-period-name");
        String startdate = req.getParameter("new-period-startdate");
        String enddate = req.getParameter("new-period-enddate");
        
		if(name == null || name.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Namn saknas på perioden.\"}", callback, writer);
			return;
		}
		
		if(startdate == null || startdate.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Startdatum saknas på perioden.\"}", callback, writer);
			return;
		}
		
		if(enddate == null || enddate.isEmpty()) {
			JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Slutdatum saknas på perioden.\"}", callback, writer);
			return;
		}
		
		try {
			Period period = new Period();
			period.setName(name);
			period.setStartDate(Date.valueOf(startdate));
			period.setEndDate(Date.valueOf(enddate));
			periodDAO.save(period);
			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Perioden sparades\"}", callback, writer);
//			period = periodDAO.getLatestCreatedPeriod();
//			JsonResponse.sendJsonResponse("{\"status\":\"success\", \"data\":" + gson.toJson(period) + "}", callback, writer);
			return;
		} catch (SQLException e) {
			log.error("SQL exception",e);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel.\"}", callback, writer);
			return;
		}				
	}
	
	@RESTMethod(alias="save/salary.json", method="post")
	public void saveSalary(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        JsonResponse.initJsonResponse(res, writer, callback);
        
        Integer salary1Amount = NumberUtils.toInt(req.getParameter("salary_1"));
        Integer salary2Amount = NumberUtils.toInt(req.getParameter("salary_2"));
        
        Salary salary1 = salaryDAO.getById(1);
        if (salary1Amount == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Lönen kan inte lämnas tom.\"}", callback, writer);
			return;
        }
        salary1.setAmountInSEK(salary1Amount);
        
        Salary salary2 = salaryDAO.getById(2);
        if (salary2Amount == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Lönen kan inte lämnas tom.\"}", callback, writer);
			return;
        }
        salary2.setAmountInSEK(salary2Amount);
        
        try {
        	salaryDAO.save(salary1);
        	salaryDAO.save(salary2);
        } catch (SQLException e) {
          	log.error("Exception when trying to update the salaries: ", e);
        	JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte uppdatera lönerna.\"}", callback, writer);
			return;
        }
        JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Uppdatering genomförd.\"}", callback, writer);
	}
	
	@RESTMethod(alias="save/accountingentries.json", method="post")
	public void saveAccountingEntries(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        JsonResponse.initJsonResponse(res, writer, callback);
        
        String ansvarPrio = req.getParameter("ansvarPrio");
        String verksamhetPrio = req.getParameter("verksamhetPrio");
        String aktivitetPrio = req.getParameter("aktivitetPrio");
        
        String ansvarRegular = req.getParameter("ansvarRegular");
        String verksamhetRegular = req.getParameter("verksamhetRegular");
        String aktivitetRegular = req.getParameter("aktivitetRegular");
        
        AccountingEntry prio = accountingEntryDAO.getById(1);
        if (StringUtils.isEmpty(ansvarPrio) || StringUtils.isEmpty(verksamhetPrio) || StringUtils.isEmpty(aktivitetPrio)) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för kontering kan inte lämnas tomma\"}", callback, writer);
			return;
        }
        prio.setAnsvar(ansvarPrio);
        prio.setVerksamhet(verksamhetPrio);
        prio.setAktivitet(aktivitetPrio);
        
        AccountingEntry regular = accountingEntryDAO.getById(2);
        if (StringUtils.isEmpty(ansvarRegular) || StringUtils.isEmpty(verksamhetRegular) || StringUtils.isEmpty(aktivitetRegular)) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fälten för kontering kan inte lämnas tomma\"}", callback, writer);
			return;
        }
        regular.setAnsvar(ansvarRegular);
        regular.setVerksamhet(verksamhetRegular);
        regular.setAktivitet(aktivitetRegular);
        
        try {
        	accountingEntryDAO.save(prio);
        	accountingEntryDAO.save(regular);
        } catch (SQLException e) {
          	log.error("Exception when trying to update the accounting entries: ", e);
        	JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte uppdatera konteringarna.\"}", callback, writer);
			return;
        }
        JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Uppdatering genomförd.\"}", callback, writer);
	}
	
	@RESTMethod(alias="save/placeforinformation.json", method="post")
	public void savePlaceForInformation(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
        PrintWriter writer = res.getWriter();
        String callback = req.getParameter("callback"); 
        JsonResponse.initJsonResponse(res, writer, callback);
        
        String placeString = req.getParameter("placeforinformation");
        PlaceForInformation place = placeDAO.getById(1);
        
        if (placeString == null) {
        	JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Fältet kan inte lämnas tomt.\"}", callback, writer);
			return;
        }
        
        place.setName(placeString);
        
        try {
        	placeDAO.save(place);
        } catch (SQLException e) {
          	log.error("Exception when trying to update the place for information: ", e);
        	JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte uppdatera 'Plats för samtal'.\"}", callback, writer);
			return;
        }
        JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Uppdatering genomförd.\"}", callback, writer);
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
        
        for (Integer id : ids) {
        	log.info("Efter LinkedHashSet: " + id);
        	Period p = new Period();
        	p.setId(id);
        	p.setName(req.getParameter("period_name_" + id));
        	p.setStartDate(Date.valueOf(req.getParameter("period_startdate_" + id)));
        	p.setEndDate(Date.valueOf(req.getParameter("period_enddate_" + id)));
        	
        	if ((p.getName() == null || p.getName().isEmpty()) || 
        			p.getStartDate() == null || p.getEndDate() == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Alla fält för perioden måste vara ifyllda.\"}", callback, writer);
        		return;
        	} else if (p.getId() == null) {
        		JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Något gick fel: 'ID IS MISSING'.\"}", callback, writer);
        		return;
        	}
        	
        	try {
				periodDAO.update(p);
			} catch (SQLException e) {
	        	log.error("Exception when trying to update the periods: ", e);
	        	JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Databasfel. Kunde inte uppdatera perioderna.\"}", callback, writer);
				return;
			}        	
        }
        
        JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Uppdatering genomförd.\"}", callback, writer);
	}
}
