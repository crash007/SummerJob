package se.sogeti.summerjob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityManager;
import se.sogeti.jobapplications.beans.municipality.MunicipalityMentor;

public class PDFGenerator {
	
	private static String newFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\Testpopulering\\";
	private static String templateFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\";
	
	public static File generateEmployeeDocuments(MunicipalityJobApplication app, MunicipalityMentor mentor, 
			int salary, String timeForInfo, String placeForInfo) throws FileNotFoundException {
		File confirmation = generateConfirmationDocument(app, salary);
		File proofOfEmployment = generateProofOfEmployment(app, mentor);
		File call = generateCallDocument(app, timeForInfo, placeForInfo);
//		File bank = generateBankDocument();
//		File time = generateTimeReport();
		File taxDocument = generateTaxDocument(app);
		
		File policeDocument = null;
		// Om jobbet är inom barnomsorgen
		if (app.getJob().getArea().getId().intValue() == 1) {
			policeDocument = generatePoliceDocument(app);
		}
		
		PDFMergerUtility merger = new PDFMergerUtility();
		merger.addSource(confirmation);
		merger.addSource(proofOfEmployment);
		merger.addSource(call);
		
		merger.addSource(taxDocument);
		
		if (policeDocument != null) {
			merger.addSource(policeDocument);
		}
		
		merger.setDestinationFileName(app.getSocialSecurityNumber() + "_" + "documents.pdf");
		try {
			merger.mergeDocuments(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File allDocuments = new File(merger.getDestinationFileName());
		return allDocuments;
	}
	
	private static File generateConfirmationDocument(MunicipalityJobApplication app, int salary) {
		PDDocument pdfDocument = null;
		try {
			File file = new File(templateFilePath + "bekraftelse.pdf");
			pdfDocument = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		MunicipalityJob job = app.getJob();
		setFieldValue(pdfDocument, "bekraftelse-socialsecuritynumber", socialSecurityNumber);
		setFieldValue(pdfDocument, "bekraftelse-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "bekraftelse-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "bekraftelse-zipandcity", app.getZipCode() + " " + app.getCity());
		setFieldValue(pdfDocument, "bekraftelse-phone", app.getPhoneNumber());
		setFieldValue(pdfDocument, "bekraftelse-school", app.getSchoolName());
		setFieldValue(pdfDocument, "bekraftelse-workdate", job.getPeriod().getStartDate() + " - " + job.getPeriod().getEndDate());
		setFieldValue(pdfDocument, "bekraftelse-workplace", job.getLocation());
		setFieldValue(pdfDocument, "bekraftelse-workaddress", job.getStreetAddress() + ", " + job.getZipCode() + " " + job.getCity());
		MunicipalityManager manager = job.getManager();
		setFieldValue(pdfDocument, "bekraftelse-workmanager", manager.getFirstname() + " " + manager.getLastname() + ", " + manager.getMobilePhone());
		setFieldValue(pdfDocument, "bekraftelse-salary", "" + salary);
		
		File file = saveDocument(pdfDocument, socialSecurityNumber + "_" + "bekraftelse.pdf");
		try {
			pdfDocument.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	private static File generateProofOfEmployment(MunicipalityJobApplication app, MunicipalityMentor mentor) {
		PDDocument pdfDocument = null;
		try {
			File file = new File(templateFilePath + "information_anstallningsbevis.pdf");
			pdfDocument = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "anstallning-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "anstallning-socialsecuritynumber", socialSecurityNumber);
		setFieldValue(pdfDocument, "anstallning-phone", app.getPhoneNumber());
		setFieldValue(pdfDocument, "anstallning-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "anstallning-zipandcity", app.getZipCode() + " " + app.getCity());
		MunicipalityJob job = app.getJob();
		setFieldValue(pdfDocument, "anstallning-workplace", job.getLocation());
		setFieldValue(pdfDocument, "anstallning-workstreetaddress", job.getStreetAddress());
		setFieldValue(pdfDocument, "anstallning-workzipandcity", job.getZipCode() + " " + job.getCity());
		MunicipalityManager manager = job.getManager();
		setFieldValue(pdfDocument, "anstallning-workmanager", manager.getFirstname() + " " + manager.getLastname() + ", " + manager.getMobilePhone());
		setFieldValue(pdfDocument, "anstallning-workmentor", mentor.getFirstname() + " " + mentor.getLastname() + ", " + mentor.getMobilePhone());
		setFieldValue(pdfDocument, "anstallning-fromdate", job.getPeriod().getStartDate().toString());
		setFieldValue(pdfDocument, "anstallning-todate", job.getPeriod().getEndDate().toString());
		setFieldValue(pdfDocument, "anstallning-specificinfo", job.getDescriptionForEmploymentPapers());
		
		File file = saveDocument(pdfDocument, socialSecurityNumber + "_" + "information_anstallningsbevis.pdf");
		try {
			pdfDocument.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	private static File generateCallDocument(MunicipalityJobApplication app, String timeForInfo, String placeForInfo) {
		PDDocument pdfDocument = null;
		try {
			File file = new File(templateFilePath + "kallelse.pdf");
			pdfDocument = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setFieldValue(pdfDocument, "kallelse-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "kallelse-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "kallelse-zipandcity", app.getZipCode() + " " + app.getCity());
		setFieldValue(pdfDocument, "kallelse-specificinfo", app.getJob().getDescriptionForCallPapers());
		setFieldValue(pdfDocument, "kallelse-timeforinfo", timeForInfo);
		setFieldValue(pdfDocument, "kallelse-placeforinfo", placeForInfo);
		
		File file = saveDocument(pdfDocument, "198907058559" + "_" + "kallelse.pdf");
		try {
			pdfDocument.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static File generateTaxDocument(MunicipalityJobApplication app) {
		PDDocument pdfDocument = null;
		File file = null;
		try {
			file = new File(templateFilePath + "Skattebefrielse-redigerad.pdf");
			pdfDocument = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "tax-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "tax-socialsecuritynumber", socialSecurityNumber.substring(0, 8) + "-" + socialSecurityNumber.substring(8));
		setFieldValue(pdfDocument, "tax-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "tax-zipandcity", app.getZipCode() + ", " + app.getCity());
		
		File taxDocument = saveDocument(pdfDocument, socialSecurityNumber + "_" + "Skattebefrielse-redigerad.pdf");
		
		try {
			pdfDocument.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return taxDocument;
	}

	public static File generatePoliceDocument(MunicipalityJobApplication app) {
		PDDocument pdfDocument = null;
		File file = null;
		try {
			file = new File(templateFilePath + "PM-442-5-forskoleverksamhet.pdf");
			pdfDocument = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Personnr-fält
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr01[0]", socialSecurityNumber.substring(0, 1));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr02[0]", socialSecurityNumber.substring(1, 2));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr03[0]", socialSecurityNumber.substring(2, 3));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr04[0]", socialSecurityNumber.substring(3, 4));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr05[0]", socialSecurityNumber.substring(4, 5));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr06[0]", socialSecurityNumber.substring(5, 6));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr07[0]", socialSecurityNumber.substring(6, 7));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr08[0]", socialSecurityNumber.substring(7, 8));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr09[0]", socialSecurityNumber.substring(8, 9));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr10[0]", socialSecurityNumber.substring(9, 10));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr11[0]", socialSecurityNumber.substring(10, 11));
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].frm_personnummer[0].flt_txtPnr12[0]", socialSecurityNumber.substring(11, 12));
		
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_efternamn[0]", app.getLastname());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_fornamn[0]", app.getFirstname());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_telefonDagtid[0]", app.getPhoneNumber());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_mobilNr[0]", app.getPhoneNumber());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_adress[0]", app.getStreetAddress());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_postNr[0]", app.getZipCode());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_personuppgifter[0].txt_ort[0]", app.getCity());
		setFieldValue(pdfDocument, "polisen[0].frm_innehall[0].frm_underskrift[0].frm_underskriftDatumOrt[0].txt_ort[0]", app.getCity());
		
		File policeDocument = saveDocument(pdfDocument, socialSecurityNumber + "_" + "PM-442-5-forskoleverksamhet.pdf");
		
		try {
			pdfDocument.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return policeDocument;
	}
	
	private static void setFieldValue(PDDocument pdfDocument, String fieldName, String value) {
		PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		PDField field;
		try {
			field = acroForm.getField(fieldName);
			if (field != null) {
				field.setValue(value);
			} else {
				System.err.println("No field found with name:" + fieldName);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static File saveDocument(PDDocument pdfDocument, String filename) {
		File newFile = null;
		try {
			newFile = new File(newFilePath + filename);
			newFile.createNewFile();
			pdfDocument.save(newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}
	
}
