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
	@TextFieldSettingDescriptor(description="Header för sparat kommunjobb", name="MunicipalityJobHeader")
	String municipalityJobHeader = "Tack för er intresseanmälan!";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Text 1 för sparat kommunjobb", name="MunicipalityJobText1")
	String municipalityJobText1 = "Er intresseanmälan kommer att bekräftas via e-post eller telefon.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Text 2 för sparat kommunjobb", name="MunicipalityJobText2")
	String municipalityJobText2 = "Mer information om placeringar på er arbetsplats skickar vi  närmre sommaren. Ingen sommarjobbare kan garanteras till arbetsplatsen!";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Näringslivsjobbheader", name="BusinessJobHeader")
	String businessjobHeader = "Annonsen har sparats.";
		
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att annons sparats", name="BusinessJobSavedMessage")
	String businessjobMessage1 = "En handläggare kommer behöva granska och godkänna annonsen innan den blir synlig för sökande.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Ansökan header", name="ApplicationHeader")
	String applicationHeader = "Tack för din ansökan, den har nu sparats.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att jobbansökan sparats, del 1", name="ApplicationSavedMessagePt1")
	String applicationSavedMessage1 = "Om du blir erbjuden ett sommarjobb så kommer du att bli kallad till ett samtal."
			+ " Kallelsen skickas med vanlig post. Tyvärr kan vi inte meddela om du inte erbjuds något sommarjobb."
			+ " Har du inte fått någon kallelse innan början på juni så kommer du troligen inte att få " 
			+ "något erbjudande.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att jobbansökan sparats, del 2", name="ApplicationSavedMessagePt2")
	String applicationSavedMessage2 = "Har du några funderingar så kontakta någon av sommarjobbshandläggarna.";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Meddelande efter att jobbansökan sparats, del 3", name="ApplicationSavedMessagePt3")
	String applicationSavedMessage3 = "Mer information och kontaktuppgifter finns på <a target='_blank' href='http://www.sundsvall.se/sommarjobb'>www.sundsvall.se/sommarjobb</a>";

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
			XMLUtils.appendNewElement(doc, messageElement, "header", municipalityJobHeader);
			XMLUtils.appendNewElement(doc, messageElement, "message1", municipalityJobText1);
			XMLUtils.appendNewElement(doc, messageElement, "message2", municipalityJobText2);
			XMLUtils.appendNewElement(doc, messageElement, "newText", "Skapa ny annons");
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", newMunicipalityURL);
		} else if (isMunicipalityJob != null && !isMunicipalityJob) {
			XMLUtils.appendNewElement(doc, messageElement, "header",businessjobHeader );
			XMLUtils.appendNewElement(doc, messageElement, "message1", businessjobMessage1);
			XMLUtils.appendNewElement(doc, messageElement, "newText", "Skapa ny annons");
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", newBusinessURL);
		} else if (isMunicipalityJobApplication != null && isMunicipalityJobApplication) {
			XMLUtils.appendNewElement(doc, messageElement, "header", applicationHeader);
			XMLUtils.appendNewElement(doc, messageElement, "message2", applicationSavedMessage1);
			XMLUtils.appendNewElement(doc, messageElement, "message3", applicationSavedMessage2);
			XMLUtils.appendNewElement(doc, messageElement, "message4", applicationSavedMessage3);
		} else if (isMunicipalityJobApplication != null && !isMunicipalityJobApplication) {
			XMLUtils.appendNewElement(doc, messageElement, "header", applicationHeader );
			XMLUtils.appendNewElement(doc, messageElement, "message2", applicationSavedMessage1);
			XMLUtils.appendNewElement(doc, messageElement, "message3", applicationSavedMessage2);
			XMLUtils.appendNewElement(doc, messageElement, "message4", applicationSavedMessage3);
		}
		
		doc.getFirstChild().appendChild(messageElement);

		return new SimpleForegroundModuleResponse(doc);
	}
}
