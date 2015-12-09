package se.sogeti.summerjob.addsummerjobapplication;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.ApplicationType;
import se.sogeti.jobapplications.beans.DriversLicenseType;
import se.sogeti.jobapplications.beans.GeoArea;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobArea;
import se.sogeti.jobapplications.daos.AreaDAO;
import se.sogeti.jobapplications.daos.DriversLicenseTypeDAO;
import se.sogeti.jobapplications.daos.GeoAreaDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.sogeti.periodsadmin.beans.Period;
import se.sogeti.periodsadmin.daos.PeriodDAO;
import se.sogeti.summerjob.FormUtils;
import se.sogeti.summerjob.JsonResponse;
import se.sundsvall.openetown.smex.SmexServiceHandler;
import se.sundsvall.openetown.smex.service.SmexServiceException;
import se.sundsvall.openetown.smex.vo.Citizen;
import se.unlogic.fileuploadutils.MultipartRequest;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;
import se.unlogic.hierarchy.foregroundmodules.rest.RESTMethod;
import se.unlogic.standardutils.io.BinarySizes;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class AddMunicipalitySummerJobApplicationModule extends AddSummerJobApplication<MunicipalityJobApplication>{
	
	
	private JobApplicationDAO<MunicipalityJobApplication> jobApplicationDAO;
	private AreaDAO areaDAO;
	private PeriodDAO periodDAO;
	private GeoAreaDAO geoAreaDAO;
	private DriversLicenseTypeDAO driversLicenseTypeDAO;
	
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="En ansökan redan finns", name="ApplicationExists")
	String applicationExistsErrorMessage = "Ett fel inträffade. Kontakta FAVI på 060-******* för mer information.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ URL till den plats där ansökan hanteras", name="ManageApplicationURL")
	String manageApplicationURL = "manage-municipality-app";
	

	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory hierarchyDaoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		
		jobApplicationDAO = new JobApplicationDAO<MunicipalityJobApplication>(dataSource, MunicipalityJobApplication.class, hierarchyDaoFactory);
		areaDAO = new AreaDAO(dataSource, MunicipalityJobArea.class, hierarchyDaoFactory);
		periodDAO = new PeriodDAO(dataSource, Period.class, hierarchyDaoFactory);
		geoAreaDAO = new GeoAreaDAO(dataSource, GeoArea.class, hierarchyDaoFactory);
		driversLicenseTypeDAO = new DriversLicenseTypeDAO(dataSource, DriversLicenseType.class, hierarchyDaoFactory);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		
		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		Element form = doc.createElement("MunicipalityJobApplicationForm");
		doc.getFirstChild().appendChild(form);
		
		if (user == null) {
			XMLUtils.appendNewElement(doc, form, "IsAdmin", false);
		} else {
			XMLUtils.appendNewElement(doc, form, "IsAdmin", user.isAdmin());
		}
		
		XMLUtils.appendNewElement(doc, form, "manageAppURL", manageApplicationURL);
		
		MunicipalityJobApplication app = null;
		Integer appId = NumberUtils.toInt(req.getParameter("appId"));
		if (appId != null) {
			app = jobApplicationDAO.getById(appId);
			XMLUtils.append(doc, form, app);
		}
		
		Element areasElement = doc.createElement("Areas");
		List<MunicipalityJobArea> areas = areaDAO.getAll();		
		for (MunicipalityJobArea area : areas) {
			Element areaElement = doc.createElement("Area");
			XMLUtils.appendNewElement(doc, areaElement, "id", area.getId());
			XMLUtils.appendNewElement(doc, areaElement, "name", area.getName());
			XMLUtils.appendNewElement(doc, areaElement, "canBeChosenInApplication", area.isCanBeChosenInApplication());

			if (app != null && app.getPreferedArea1() != null 
					&& app.getPreferedArea1().getId().intValue() == area.getId().intValue()) {
				XMLUtils.appendNewElement(doc, areaElement, "selectedArea1", true);
			} else if (app != null && app.getPreferedArea2() != null 
					&& app.getPreferedArea2().getId().intValue() == area.getId().intValue()) {
				XMLUtils.appendNewElement(doc, areaElement, "selectedArea2", true);
			} else if (app != null && app.getPreferedArea3() != null 
					&& app.getPreferedArea3().getId().intValue() == area.getId().intValue()) {
				XMLUtils.appendNewElement(doc, areaElement, "selectedArea3", true);
			}
			areasElement.appendChild(areaElement);
		}
		form.appendChild(areasElement);	
		
		Element geoAreasElement = doc.createElement("GeoAreas");
		List<GeoArea> geoAreas = geoAreaDAO.getAll();
		for (GeoArea geoArea : geoAreas) {
			Element geoElement = doc.createElement("GeoArea");
			XMLUtils.appendNewElement(doc, geoElement, "id", geoArea.getId());
			XMLUtils.appendNewElement(doc, geoElement, "name", geoArea.getName());

			if (app != null && app.getPreferedGeoArea1() != null 
					&& app.getPreferedGeoArea1().getId().intValue() == geoArea.getId().intValue()) {
				XMLUtils.appendNewElement(doc, geoElement, "selectedGeoArea1", true);
			} else if (app != null && app.getPreferedGeoArea2() != null 
					&& app.getPreferedGeoArea2().getId().intValue() == geoArea.getId().intValue()) {
				XMLUtils.appendNewElement(doc, geoElement, "selectedGeoArea2", true);
			} else if (app != null && app.getPreferedGeoArea3() != null 
					&& app.getPreferedGeoArea3().getId().intValue() == geoArea.getId().intValue()) {
				XMLUtils.appendNewElement(doc, geoElement, "selectedGeoArea3", true);
			}
			geoAreasElement.appendChild(geoElement);
		}
		form.appendChild(geoAreasElement);
		
		populateDriversLicenseTypesElement(doc, form, app, driversLicenseTypeDAO.getAll());
		
		return new SimpleForegroundModuleResponse(doc);
	}

	
	
	@RESTMethod(alias="save/municipalityapplication.json", method="post")
	public void saveApplication(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException {
		log.info("saving municipality application");
		
		MultipartRequest requestWrapper = null;
		try {
			requestWrapper = new MultipartRequest(1024 * BinarySizes.KiloByte, 100 * BinarySizes.MegaByte, req);

			PrintWriter writer = res.getWriter();
			String callback = requestWrapper.getParameter("callback"); 
			JsonResponse.initJsonResponse(res, writer, callback);
	
			MunicipalityJobApplication exisitingApplication = jobApplicationDAO.getbySocialSecurityNumber(requestWrapper.getParameter("socialSecurityNumber"));
			
			if(exisitingApplication != null) {
				if (user != null && !user.isAdmin()){
					log.warn("Municipality application already exists for this user " + exisitingApplication.applicationBasicsToString());
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"" + applicationExistsErrorMessage + "\"}", callback, writer);
					return;
				}
			}
	
			Integer appId = NumberUtils.toInt(requestWrapper.getParameter("appId"));
			MunicipalityJobApplication app = appId != null ? jobApplicationDAO.getById(appId) : new MunicipalityJobApplication();

			String socialSecurityNumber = requestWrapper.getParameter("socialSecurityNumber");
			
			if(!validateSocialSecurityNumber(writer, callback, socialSecurityNumber)){
				return;
			}
	
			Citizen person = getCitizen(socialSecurityNumber);
				
			createJobApplication(app, requestWrapper, person);
			automaticControllAndApprove(app);
			
			FileItem fileItem = requestWrapper.getFile(0);
			if (!StringUtils.isEmpty(fileItem.getName())) {
				saveCv(app,fileItem,app.getSocialSecurityNumber()+"_"+fileItem.getName(),writer, callback);
			}
			
			boolean workWithAnything = requestWrapper.getParameter("noPreferedArea") != null ? true : false;
			
			if (!workWithAnything) {
				Integer preferedArea1 = NumberUtils.toInt(requestWrapper.getParameter("preferedArea1"));
				Integer preferedArea2 = NumberUtils.toInt(requestWrapper.getParameter("preferedArea2"));
				Integer preferedArea3 = NumberUtils.toInt(requestWrapper.getParameter("preferedArea3"));
				MunicipalityJobArea area1 = areaDAO.getAreaById(preferedArea1);
				MunicipalityJobArea area2 = areaDAO.getAreaById(preferedArea2);
				MunicipalityJobArea area3 = areaDAO.getAreaById(preferedArea3);
	
				app.setPreferedArea1(area1);
				app.setPreferedArea2(area2);
				app.setPreferedArea3(area3);
			} else {
				app.setPreferedArea1(null);
				app.setPreferedArea2(null);
				app.setPreferedArea3(null);
			}
			
			Integer preferedGeoArea1 = NumberUtils.toInt(requestWrapper.getParameter("geoArea1"));
			Integer preferedGeoArea2 = NumberUtils.toInt(requestWrapper.getParameter("geoArea2"));
			Integer preferedGeoArea3 = NumberUtils.toInt(requestWrapper.getParameter("geoArea3"));
			GeoArea geoArea1 = geoAreaDAO.getAreaById(preferedGeoArea1);
			GeoArea geoArea2 = geoAreaDAO.getAreaById(preferedGeoArea2);
			GeoArea geoArea3 = geoAreaDAO.getAreaById(preferedGeoArea3);
	
			if (geoArea1 == null || geoArea2 == null) {
				JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Du måste ange minst två geografiska områden du kan jobba inom.\"}", callback, writer);
				return;
			}
			app.setPreferedGeoArea1(geoArea1);
			app.setPreferedGeoArea2(geoArea2);
			app.setPreferedGeoArea3(geoArea3);
			
			//TODO
			//Duplicate code in business
			boolean hasDriversLicense = requestWrapper.getParameter("hasDriversLicense") !=null ? true:false;
			if (hasDriversLicense) {
				Integer typeId = NumberUtils.toInt(requestWrapper.getParameter("driversLicenseType"));
				if (typeId == null) {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Om du har körkort måste du ange en körkortstyp\"}", callback, writer);
					return;
				}
				DriversLicenseType type = driversLicenseTypeDAO.getTypeById(typeId);
				app.setDriversLicenseType(type);
			} else {
				app.setDriversLicenseType(null); // If the application is being updated
			}
			
			if(!validatePersonalInformation(writer, callback, app)){
				return;
			}
			
			if(!validatePersonalLetter(writer, callback, app)){
				return;
			}
			
			if (app.getBirthDate() == null) {
				JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Personnumret innehåller inget korrekt datum.\"}", callback, writer);
				return;
			}
	
			log.debug(app);
			
			// A new application
			if (appId == null) {
				if (user != null && user.getUsername() != null) {
					app.setAddedByUser(user.getUsername());
				}
			}
			
			if (user != null && user.isAdmin()) {
				String applicationType = requestWrapper.getParameter("applicationType");
				ApplicationType type;
				
				if (applicationType.equals(ApplicationType.REGULAR.name())) {
					type = ApplicationType.REGULAR;
				} else if (applicationType.equals(ApplicationType.REGULAR_ADMIN.name())) {
					type = ApplicationType.REGULAR_ADMIN;
				} else if (applicationType.equals(ApplicationType.PRIO.name())) {
					type = ApplicationType.PRIO;
				} else {
					JsonResponse.sendJsonResponse("{\"status\":\"fail\", \"message\":\"Som administratör måste du välja ett alternativ från 'Typ av ansökan'.\"}", callback, writer);
					return;
				}
				app.setApplicationType(type);
			}
			
			try {
				jobApplicationDAO.save(app);
				if (appId != null) {
					JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Ändringarna har nu sparats.\"}", callback, writer);
				} else {
					JsonResponse.sendJsonResponse("{\"status\":\"success\", \"message\":\"Din ansökan har nu sparats.\"}", callback, writer);
				}
			} catch (SQLException e) {
				log.error(e);
				JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"Något gick fel när ansökan skulle sparas.\"}", callback, writer);
			}
		
		} catch (FileUploadException e1) {
			log.error(e1);
			
		}
	}

	

	
	
}
