package se.sogeti.summerjob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import se.sogeti.jobapplications.beans.business.BusinessSectorJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJob;
import se.sogeti.jobapplications.beans.municipality.MunicipalityJobApplication;
import se.sogeti.jobapplications.beans.municipality.MunicipalityManager;
import se.sogeti.periodsadmin.beans.AccountingEntry;
import se.sogeti.periodsadmin.beans.ContactPerson;
import se.unlogic.standardutils.arrays.ArrayUtils;
import se.unlogic.standardutils.string.StringUtils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDFGenerator {
	
//	private static String newFilePath = "C:\\Users\\Sogeti\\Desktop\\Sommarjobb\\Populerade\\";
//	private static String templateFilePath = "C:\\Users\\Sogeti\\Desktop\\Sommarjobb\\";
	
//	private static String newFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\Testpopulering\\";
//	private static String templateFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\";
	
	public static File generateEmployeeDocuments(String templatePath, String newFilePath, MunicipalityJob job, 
			MunicipalityJobApplication app,  int salary, 
			String placeForInfo, AccountingEntry accounting, ContactPerson contact) throws IOException, Exception {
		File call = generateCallDocument(templatePath, newFilePath, job, app, placeForInfo, contact);
		File confirmation = generateConfirmationDocument(templatePath, newFilePath, job, app, salary, accounting);
		File proofOfEmployment = generateProofOfEmployment(templatePath, newFilePath, job, app, salary);
		File time = generateTimeReport(templatePath, newFilePath, job, app, accounting);
		File bank = generateBankDocument(templatePath, newFilePath, app, contact);
		File taxDocument = generateTaxDocument(templatePath, newFilePath, app);
		
		File policeDocument = null;
		// Om jobbet är inom barnomsorgen
		if (job.getArea().getId().intValue() == 1) {
			policeDocument = generatePoliceDocument(templatePath, newFilePath, app);
		}
		
		PDFMergerUtility merger = new PDFMergerUtility();
		merger.addSource(call);
		merger.addSource(confirmation);
		merger.addSource(proofOfEmployment);
		merger.addSource(time);
		merger.addSource(bank);
		merger.addSource(taxDocument);
		
		if (policeDocument != null) {
			merger.addSource(policeDocument);
		}
		
		merger.setDestinationFileName(app.getSocialSecurityNumber() + "_" + "documents.pdf");
		merger.mergeDocuments(null);
		
		File allDocuments = new File(merger.getDestinationFileName());
		return allDocuments;
	}
	
	public static File generateConfirmationDocument(String templateFilePath, String newFilePath, MunicipalityJob job, MunicipalityJobApplication app, int salary, AccountingEntry accounting) throws IOException {
		File file = new File(templateFilePath + "bekraftelse.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "bekraftelse-ansvar", accounting.getAnsvar());
		setFieldValue(pdfDocument, "bekraftelse-verksamhet", accounting.getVerksamhet());
		setFieldValue(pdfDocument, "bekraftelse-aktivitet", accounting.getAktivitet());
		setFieldValue(pdfDocument, "bekraftelse-socialsecuritynumber", FormUtils.getSSNMunicipalityFormatting(socialSecurityNumber));
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
		
		File savedFile = saveDocument(newFilePath, pdfDocument, socialSecurityNumber + "_" + "bekraftelse.pdf");
		pdfDocument.close();
		
		return savedFile;
	}
	
	public static File generateProofOfEmployment(String templateFilePath, String newFilePath, MunicipalityJob job, MunicipalityJobApplication app, int salary) throws IOException {
		File file = new File(templateFilePath + "information_anstallningsbevis.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "anstallning-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "anstallning-socialsecuritynumber", FormUtils.getSSNMunicipalityFormatting(socialSecurityNumber));
		setFieldValue(pdfDocument, "anstallning-phone", app.getPhoneNumber());
		setFieldValue(pdfDocument, "anstallning-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "anstallning-zipandcity", app.getZipCode() + " " + app.getCity());
		setFieldValue(pdfDocument, "anstallning-workplace", job.getLocation());
		setFieldValue(pdfDocument, "anstallning-workstreetaddress", job.getStreetAddress());
		setFieldValue(pdfDocument, "anstallning-workzipandcity", job.getZipCode() + " " + job.getCity());
		MunicipalityManager manager = job.getManager();
		setFieldValue(pdfDocument, "anstallning-workmanager", manager.getFirstname() + " " + manager.getLastname() + ", " + manager.getMobilePhone());
		
		if (app.getPersonalMentor() != null) {
			setFieldValue(pdfDocument, "anstallning-workmentor", app.getPersonalMentor().getFirstname() + " " + app.getPersonalMentor().getLastname() + ", " + app.getPersonalMentor().getMobilePhone());
		}
		
		setFieldValue(pdfDocument, "anstallning-salary", salary + "");
		setFieldValue(pdfDocument, "anstallning-fromdate", job.getPeriod().getStartDate().toString());
		setFieldValue(pdfDocument, "anstallning-todate", job.getPeriod().getEndDate().toString());
		
		if (!StringUtils.isEmpty(job.getDescriptionForEmploymentPapers())) {
			setFieldValue(pdfDocument, "anstallning-specificinfo", job.getDescriptionForEmploymentPapers());
		} else {
			setFieldValue(pdfDocument, "anstallning-specificinfo", "");
		}
		
		
		File savedFile = saveDocument(newFilePath, pdfDocument, socialSecurityNumber + "_" + "information_anstallningsbevis.pdf");
		pdfDocument.close();
		
		return savedFile;
	}
	
	public static File generateBankDocument(String templateFilePath, String newFilePath, MunicipalityJobApplication app, ContactPerson contact) throws IOException {
		File file = new File(templateFilePath + "overforingsuppdrag.pdf");
		PDDocument pdfDocument = PDDocument.load(file);

		setFieldValue(pdfDocument, "overforing-contactname", contact.getName());
		setFieldValue(pdfDocument, "overforing-contactphone", contact.getPhoneNumber());
		setFieldValue(pdfDocument, "overforing-socialsecuritynumber", app.getSocialSecurityNumber());
		setFieldValue(pdfDocument, "overforing-lastname", app.getLastname());
		setFieldValue(pdfDocument, "overforing-firstname", app.getFirstname());
		setFieldValue(pdfDocument, "overforing-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "overforing-zipandcity", app.getZipCode() + " " + app.getCity());
		setFieldValue(pdfDocument, "overforing-phone", app.getPhoneNumber());
		setFieldValue(pdfDocument, "overforing-name", app.getFirstname() + " " + app.getLastname());

		File savedFile = saveDocument(newFilePath, pdfDocument, app.getSocialSecurityNumber() + "_overforingsuppdrag.pdf");
		pdfDocument.close();
		return savedFile;
	}
	
	public static File generateTimeReport(String templateFilePath, String newFilePath, MunicipalityJob job, MunicipalityJobApplication app, AccountingEntry accounting) throws IOException, Exception {
		PdfReader reader = new PdfReader(templateFilePath + "tjanstgoringsrapport.pdf");
		OutputStream os = new FileOutputStream(newFilePath + app.getSocialSecurityNumber() + "_tjanstgoringsrapport.pdf");
		PdfStamper stamper = new PdfStamper(reader, os);

		AcroFields acroFields = stamper.getAcroFields();
		acroFields.setField("tjanstgoring-year", Calendar.getInstance().get(Calendar.YEAR) + "");
		acroFields.setField("tjanstgoring-socialsecuritynumber", FormUtils.getSSNMunicipalityFormatting(app.getSocialSecurityNumber()));
		acroFields.setField("tjanstgoring-lastnamefirstname", app.getLastname() + " " + app.getFirstname());
		acroFields.setField("tjanstgoring-streetaddress", app.getStreetAddress());
		acroFields.setField("tjanstgoring-zipcode", app.getZipCode());
		acroFields.setField("tjanstgoring-city", app.getCity());
		acroFields.setField("tjanstgoring-workplace", job.getLocation());
		acroFields.setField("tjanstgoring-phone", app.getPhoneNumber());
		acroFields.setField("tjanstgoring-ansvar", accounting.getAnsvar());
		acroFields.setField("tjanstgoring-verksamhet", accounting.getVerksamhet());
		acroFields.setField("tjanstgoring-aktivitet", accounting.getAktivitet());
		stamper.close();

		File file = new File(newFilePath + app.getSocialSecurityNumber() + "_tjanstgoringsrapport.pdf");
		return file;
	}
	
	public static File generateCallDocument(String templateFilePath, String newFilePath, MunicipalityJob job, MunicipalityJobApplication app, String placeForInfo, ContactPerson contact) throws IOException {
		File file = new File(templateFilePath + "kallelse.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
		setFieldValue(pdfDocument, "kallelse-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "kallelse-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "kallelse-zipandcity", app.getZipCode() + " " + app.getCity());
		setFieldValue(pdfDocument, "kallelse-specificinfo", job.getDescriptionForCallPapers());
		setFieldValue(pdfDocument, "kallelse-timeforinfo", app.getTimeForInformation());
		setFieldValue(pdfDocument, "kallelse-placeforinfo", placeForInfo);
		setFieldValue(pdfDocument, "kallelse-kontaktperson", contact.getName() + " - " + contact.getPhoneNumber());
		
		File savedFile = saveDocument(newFilePath, pdfDocument, app.getSocialSecurityNumber() + "_" + "kallelse.pdf");
		pdfDocument.close();
		
		return savedFile;
	}
	
	public static File generateTaxDocument(String templateFilePath, String newFilePath, MunicipalityJobApplication app) throws IOException {
		File file = new File(templateFilePath + "skattebefrielse.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
		String socialSecurityNumber = app.getSocialSecurityNumber();
		setFieldValue(pdfDocument, "tax-name", app.getFirstname() + " " + app.getLastname());
		setFieldValue(pdfDocument, "tax-socialsecuritynumber", socialSecurityNumber.substring(0, 8) + "-" + socialSecurityNumber.substring(8));
		setFieldValue(pdfDocument, "tax-streetaddress", app.getStreetAddress());
		setFieldValue(pdfDocument, "tax-zipandcity", app.getZipCode() + ", " + app.getCity());
		
		File taxDocument = saveDocument(newFilePath, pdfDocument, socialSecurityNumber + "_" + "Skattebefrielse-redigerad.pdf");
		pdfDocument.close();
		
		return taxDocument;
	}

	public static File generatePoliceDocument(String templateFilePath, String newFilePath, MunicipalityJobApplication app) throws IOException {
		File file = new File(templateFilePath + "PM-442-5-forskoleverksamhet.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
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
		
		File policeDocument = saveDocument(newFilePath, pdfDocument, socialSecurityNumber + "_" + "PM-442-5-forskoleverksamhet.pdf");
		pdfDocument.close();
		
		return policeDocument;
	}
	
	public static File generateBusinessSectorJobAgreementDocument(String templateFilePath, String newFilePath, 
			BusinessSectorJob job, String faviContactInfo) throws IOException {
		File file = new File(templateFilePath + "overenskommelse-naringslivet.pdf");
		PDDocument pdfDocument = PDDocument.load(file);
		
		setFieldValue(pdfDocument, "naringslivet-corporatenumber", job.getCorporateNumber());
		setFieldValue(pdfDocument, "naringslivet-company", job.getCompany());
		setFieldValue(pdfDocument, "naringslivet-address", job.getStreetAddress());
		setFieldValue(pdfDocument, "naringslivet-zipcity", job.getZipCode() + " " + job.getCity());
		setFieldValue(pdfDocument, "naringslivet-managername", job.getManager().getFirstname() + " " + job.getManager().getLastname());
		setFieldValue(pdfDocument, "naringslivet-managerphone", job.getManager().getMobilePhone());
		setFieldValue(pdfDocument, "naringslivet-manageremail", job.getManager().getEmail());
		
		if (job.getMentor() != null) {
			setFieldValue(pdfDocument, "naringslivet-contactname", job.getMentor().getFirstname() + " " + job.getMentor().getLastname());
			setFieldValue(pdfDocument, "naringslivet-contactphone", job.getMentor().getMobilePhone());
			setFieldValue(pdfDocument, "naringslivet-contactemail", job.getMentor().getEmail());
		}
		
		setFieldValue(pdfDocument, "naringslivet-numberofworkers", job.getNumberOfWorkersNeeded().toString());
		setFieldValue(pdfDocument, "naringslivet-dates", job.getStartDate() + " - " + job.getEndDate());
		
		String inChargeString = job.getInChargeOfInterviews() ? 
				"Jag vill att kommunen gör urval av kandidater" : "Jag vill göra urval av kandidater efter kommunens gallring";
		
		setFieldValue(pdfDocument, "naringslivet-inchargeofinterviews", inChargeString);
		setFieldValue(pdfDocument, "naringslivet-favicontact", faviContactInfo);
		
		File businessDocument = saveDocument(newFilePath, pdfDocument, job.getId() + "_overenskommelse-naringslivet.pdf");
		pdfDocument.close();
		
		return businessDocument;
	}
	
	public static void setFieldValue(PDDocument pdfDocument, String fieldName, String value) throws IOException {
		PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		PDField field;
		field = acroForm.getField(fieldName);
		if (field != null) {
			field.setValue(value);
		} else {
			System.err.println("No field found with name:" + fieldName);
		}
	}
	
	public static File saveDocument(String newFilePath, PDDocument pdfDocument, String filename) throws IOException {
		File newFile = null;
		newFile = new File(newFilePath + filename);
		newFile.createNewFile();
		pdfDocument.save(newFile);
		return newFile;
	}
}
