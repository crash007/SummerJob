package se.sogeti.summerjob.welcome;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.sogeti.summerjob.addsummerjobapplication.AddMunicipalityJobApplicationHandler;
import se.sogeti.summerjob.addsummerjobapplication.BusinessSectorJobApplicationHandler;
import se.unlogic.hierarchy.core.annotations.CheckboxSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SummerJobWelcomeModule extends AnnotatedForegroundModule {
	
	@ModuleSetting
	@CheckboxSettingDescriptor(description="Bocka i denna när tiden för att lägga upp jobb passerat", name = "Kommunala jobb har passerat")
	private boolean municipalityJobExceeded = false;
	
	@ModuleSetting
	@CheckboxSettingDescriptor(description="Bocka i denna när tiden för att lägga upp jobb passerat", name = "Privata jobb har passerat")
	private boolean businessJobExceeded = false;
	
	@ModuleSetting
	@CheckboxSettingDescriptor(description="Bocka i denna när tiden för att lägga upp ansökningar passerat", name = "Kommunala ansökningar har passerat")
	private boolean municipalityAppExceeded = false;
	
	@ModuleSetting
	@CheckboxSettingDescriptor(description="Bocka i denna när tiden för att lägga upp ansökningar passerat", name = "Privata ansökningar har passerat")
	private boolean businessAppExceeded = false;
	
	@InstanceManagerDependency(required=true)
	private AddMunicipalityJobApplicationHandler addMunicipalityJobApplicationHandler;
	
	@InstanceManagerDependency(required=true)
	private BusinessSectorJobApplicationHandler businessSectorJobApplicationHandler;
	
	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user,
			URIParser uriParser) throws Throwable {
		

		Document doc = XMLUtils.createDomDocument();
		Element element = doc.createElement("Document");
		element.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		element.appendChild(this.sectionInterface.getSectionDescriptor().toXML(doc));
		element.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(element);
		
		XMLUtils.appendNewElement(doc, element, "municipalityJobExceeded", municipalityJobExceeded);
		XMLUtils.appendNewElement(doc, element, "businessJobExceeded", businessJobExceeded);
		XMLUtils.appendNewElement(doc, element, "municipalityAppExceeded", municipalityAppExceeded);
		XMLUtils.appendNewElement(doc, element, "businessAppExceeded", businessAppExceeded);
		
		XMLUtils.appendNewElement(doc, element,"AddMunicipalityApplicationUrl",req.getContextPath()+addMunicipalityJobApplicationHandler.getUrl());
		XMLUtils.appendNewElement(doc, element,"AddBusinessApplicationUrl",req.getContextPath()+businessSectorJobApplicationHandler.getUrl());
		
		return new SimpleForegroundModuleResponse(doc);
		
	}

}
