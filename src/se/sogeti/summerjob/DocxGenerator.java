package se.sogeti.summerjob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;

public class DocxGenerator {
	
	private static final String templateFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\";
	private static final String newFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\Testpopulering\\";
	

	public static File generateEnvironmentDocument(MunicipalityJob job,
			List<MunicipalityJobApplication> applications) throws Docx4JException, JAXBException, IOException {
		WordprocessingMLPackage template = getTemplate("arbetsmiljoansvar");
		String placeholders[] = { "KANDIDAT_NAMN", "KANDIDAT_PNR", "KANDIDAT_TELEFON" };
		List<Map<String, String>> textToAdd = new ArrayList<Map<String, String>>();
		
		for (MunicipalityJobApplication app : applications) {
			Map<String, String> repl = new HashMap<String, String>();
			repl.put("KANDIDAT_NAMN", app.getFirstname() + " " + app.getLastname());
			repl.put("KANDIDAT_PNR", FormUtils.getSSNMunicipalityFormatting(app.getSocialSecurityNumber()));
			repl.put("KANDIDAT_TELEFON", app.getPhoneNumber());
			textToAdd.add(repl);
		}
		
		replaceTable(placeholders, textToAdd, template);
		File file = writeDocxToStream(template, "arbetsmiljoansvar-test");
		
		return file;
	}
	
	public static File generateAllocationDocument(MunicipalityJob job,
			List<MunicipalityJobApplication> applications) throws Docx4JException, JAXBException, IOException {
		
		WordprocessingMLPackage template = getTemplate("tilldelning");
		
		String periodString = job.getPeriod().getName() 
				+ " (" + job.getPeriod().getStartDate() + " - " + job.getPeriod().getEndDate() + ")";
		replacePlaceholder(template, periodString, "TILLDELNING_PERIOD");
		
		String placeholders[] = { "KANDIDAT_NAMN", "KANDIDAT_PNR", "KANDIDAT_TELEFON" };
		List<Map<String, String>> textToAdd = new ArrayList<Map<String, String>>();
		
		for (MunicipalityJobApplication app : applications) {
			Map<String, String> repl = new HashMap<String, String>();
			repl.put("KANDIDAT_NAMN", app.getFirstname() + " " + app.getLastname());
			repl.put("KANDIDAT_PNR", FormUtils.getSSNMunicipalityFormatting(app.getSocialSecurityNumber()));
			repl.put("KANDIDAT_TELEFON", app.getPhoneNumber());
			textToAdd.add(repl);
		}
		
		replaceTable(placeholders, textToAdd, template);
		File file = writeDocxToStream(template, "tilldelning-test");
		
		return file;
	}
	
	private static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
		WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(templateFilePath + name + ".docx")));
		return template;
	}
	
	private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement) {
			obj = ((JAXBElement<?>) obj).getValue();
		}
 
		if (obj.getClass().equals(toSearch)) {
			result.add(obj);
		} else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
 
		}
		return result;
	}

	
	private static void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder) {
		List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
 
		for (Object text : texts) {
			Text textElement = (Text) text;
			if (textElement.getValue().equals(placeholder)) {
				textElement.setValue(name);
				System.out.println("Japp, vi hittade ordet!");
			}
		}
	}
	
	private static void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
			WordprocessingMLPackage template) throws Docx4JException, JAXBException {
		List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
 
		// 1. find the table
		Tbl tempTable = getTemplateTable(tables, placeholders[0]);
		List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
 
		// first row is header, second row is content
		if (rows.size() == 2) {
			// this is our template row
			Tr templateRow = (Tr) rows.get(1);
 
			for (Map<String, String> replacements : textToAdd) {
				// 2 and 3 are done in this method
				addRowToTable(tempTable, templateRow, replacements);
			}
 
			// 4. remove the template row
			tempTable.getContent().remove(templateRow);
		}
	}
	
	private static Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = getAllElementFromObject(tbl, Text.class);
			for (Object text : textElements) {
				Text textElement = (Text) text;
				if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
					return (Tbl) tbl;
			}
		}
		return null;
	}
	
	private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
		List<?> textElements = getAllElementFromObject(workingRow, Text.class);
		for (Object object : textElements) {
			Text text = (Text) object;
			String replacementValue = (String) replacements.get(text.getValue());
			if (replacementValue != null)
				text.setValue(replacementValue);
		}
		
		reviewtable.getContent().add(workingRow);
	}
	
	private static File writeDocxToStream(WordprocessingMLPackage template, String name) throws IOException, Docx4JException {
		File file = new File(newFilePath + name + ".docx");
		template.save(file);
		return file;
	}
}
