package se.sogeti.summerjob.successview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.bool.BooleanUtils;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class SuccessView extends AnnotatedForegroundModule {

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
		Boolean isMunicipalityJob = BooleanUtils.toBoolean(req.getParameter("municipalityJob"));
		XMLUtils.appendNewElement(doc, messageElement, "newText", "Skapa ny annons");
		if (isMunicipalityJob != null && isMunicipalityJob) {
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", "add-municipality-job");
		} else if (isMunicipalityJob != null && !isMunicipalityJob) {
			XMLUtils.appendNewElement(doc, messageElement, "newUrl", "add-business-sector-job");
		} else {
			return new SimpleForegroundModuleResponse(doc);
		}
		
		doc.getFirstChild().appendChild(messageElement);

		return new SimpleForegroundModuleResponse(doc);
	}
}
