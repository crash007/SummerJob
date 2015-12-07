package se.sogeti.summerjob.addsummerjobapplication;

import java.io.File;
import java.io.PrintWriter;

import org.apache.commons.fileupload.FileItem;

import se.sogeti.jobapplications.beans.JobApplication;
import se.sogeti.summerjob.JsonResponse;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;

public abstract class AddSummerJobApplication<T extends JobApplication> extends AnnotatedRESTModule{
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Path till var cv'n ska sparas.", name = "FilePath")
	String filePath = "/tmp/";
	
	protected void saveCv(T application, FileItem fileItem, String fileName,PrintWriter writer, String callback) {
		String fullPath = filePath + fileName;
		File file = new File(fullPath);
		try {
			fileItem.write(file);
			log.info("Saved cv file:"+file.toString());
			application.setCvFilename(fullPath);
		} catch (Exception e1) {
			log.error(e1);
			JsonResponse.sendJsonResponse("{\"status\":\"error\", \"message\":\"" + "exception when saving file." + "\"}", callback, writer);
		}
	}
	
}
