package se.sogeti.summerjob.successview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SuccessView extends AnnotatedForegroundModule {
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till sida för att skapa nytt jobb", name="NewMunicipalityJob")
	String newMunicipalityURL = "add-municipality-job";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Relativ url till sida för att skapa nytt jobb", name="NewBusinessJob")
	String newBusinessURL = "add-business-sector-job";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att annons sparats", name="JobSavedMessage")
	String jobSavedMessage = "En handläggare kommer behöva granska och godkänna annonsen innan den blir synlig för sökande.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att ansökan sparats", name="ApplicationSavedMessage")
	String applicationSavedMessage = "Tack för visat intresse. Vi kontaktar aktuella kandidater via telefon eller e-post.";

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
		
		Element messageElement = doc.createElement("SuccessMessage");
		Boolean isMunicipalityJob = null, isMunicipalityJobApplication = null;
		
		String isMunicipalityJobString = req.getParameter("municipalityJob");
		if (isMunicipalityJobString != null) {
			isMunicipalityJob = BooleanUtils.toBoolean(isMunicipalityJobString);
		}
		
		String isMunicipalityJobApplicationString = req.getParameter("municipalityJobApplication");
		if (isMunicipalityJobApplicationString != null) {
			isMunicipalityJobApplication = BooleanUtils.toBoolean(isMunicipalityJobApplicationString);
		}
		
		if (isMunicipalityJob != null && isMunicipalityJob) {
			XMLUtils.appendNewElement(doc, messageElement, "header", "Annonsen har sparats.");
			XMLUtils.appendNewElement(doc, messageElement, "message", jobSavedMessage);
			XMLUtils.appendNewElement(doc, messageElement, "newText", "Skapa ny annons");
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", newMunicipalityURL);
		} else if (isMunicipalityJob != null && !isMunicipalityJob) {
			XMLUtils.appendNewElement(doc, messageElement, "header", "Annonsen har sparats.");
			XMLUtils.appendNewElement(doc, messageElement, "message", jobSavedMessage);
			XMLUtils.appendNewElement(doc, messageElement, "newText", "Skapa ny annons");
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", newBusinessURL);
		} else if (isMunicipalityJobApplication != null && isMunicipalityJobApplication) {
			XMLUtils.appendNewElement(doc, messageElement, "header", "Ansökan har sparats");
			XMLUtils.appendNewElement(doc, messageElement, "message", applicationSavedMessage);
		} else if (isMunicipalityJobApplication != null && !isMunicipalityJobApplication) {
			XMLUtils.appendNewElement(doc, messageElement, "header", "Ansökan har sparats");
			XMLUtils.appendNewElement(doc, messageElement, "message", applicationSavedMessage);
		}
		
		doc.getFirstChild().appendChild(messageElement);

		return new SimpleForegroundModuleResponse(doc);
	}
}
