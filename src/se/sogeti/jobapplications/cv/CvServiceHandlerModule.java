package se.sogeti.jobapplications.cv;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.jobapplications.beans.business.BusinessSectorJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.daos.BusinessSectorJobApplicationDAO;
import se.sogeti.jobapplications.daos.JobApplicationDAO;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.io.FileUtils;
import se.unlogic.standardutils.mime.MimeUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.streams.StreamUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.HTTPUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class CvServiceHandlerModule extends AnnotatedForegroundModule implements CvServiceHander{
	

	BusinessSectorJobApplicationDAO businessApplicationDAO;
	JobApplicationDAO<MunicipalityJobApplication> municipalityApplicationDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {
		super.createDAOs(dataSource);
		
		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface);
		businessApplicationDAO = new BusinessSectorJobApplicationDAO(dataSource, BusinessSectorJobApplication.class, daoFactory);
		municipalityApplicationDAO = new JobApplicationDAO<>(dataSource, MunicipalityJobApplication.class, daoFactory);
	}

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
		
		return new SimpleForegroundModuleResponse(doc);
	}
	
	
	
	public <T extends JobApplicationDAO<?>> ForegroundModuleResponse getCv(T dao, HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
					throws Throwable {

		Integer appId = NumberUtils.toInt(req.getParameter("id"));
		if(appId!=null){			
		JobApplication application = (JobApplication) dao.getById(appId);
			if(application!=null){
				if(application.getCvFilename()!=null){
					FileInputStream in = null;
					OutputStream out = null;

					File cv = new File(application.getCvFilename());
					in = new FileInputStream(cv);

					HTTPUtils.setContentLength(cv.length(), res);

					res.setContentType(MimeUtils.getMimeType(cv));

					res.setHeader("Content-Disposition", "inline; filename=\"" + FileUtils.toValidHttpFilename("cv") + "\"");

					out = res.getOutputStream();

					StreamUtils.transfer(in, out);
				}else{
					log.warn("No cv in business application, id="+appId);
				}
			}else{
				log.warn("No application with id="+appId);
			}

		}else{
			log.warn("No app id found");
		}
		
		return null;
		
	}
	
	@WebPublic
	public ForegroundModuleResponse getBusinessApplicationCv(HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
					throws Throwable {
		
		return getCv(businessApplicationDAO, req, res, user, uriParser);
		
	}
	
	@WebPublic
	public ForegroundModuleResponse getMunicipalityApplicationCv(HttpServletRequest req,
			HttpServletResponse res, User user, URIParser uriParser)
					throws Throwable {
		
		return getCv(municipalityApplicationDAO, req, res, user, uriParser);
		
	}

	@Override
	public String getBusinessApplicationCvUrl() {
		
		return this.getFullAlias()+"/getBusinessApplicationCv";
		
	}

	@Override
	public String getMunicipalityApplicationCvUrl() {
		return this.getFullAlias()+"/getMunicipalityApplicationCv";
	}

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface,
			DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		super.init(moduleDescriptor, sectionInterface, dataSource);

		if(!systemInterface.getInstanceHandler().addInstance(CvServiceHander.class, this)){
			throw new RuntimeException("Unable to register module in global instance handler using key " +CvServiceHander.class.getSimpleName() + ", another instance is already registered using this key.");
		}
	}

	@Override
	public void unload() throws Exception {
		// TODO Auto-generated method stub
		if(this.equals(systemInterface.getInstanceHandler().getInstance(CvServiceHander.class))){
			log.info("Unloading cvServiceHandler from instanceHandler.");
			systemInterface.getInstanceHandler().removeInstance(CvServiceHander.class);
		}		
		this.businessApplicationDAO=null;
		super.unload();
	}

	
}
