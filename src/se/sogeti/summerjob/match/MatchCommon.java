package se.sogeti.summerjob.match;

import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.foregroundmodules.rest.AnnotatedRESTModule;

public class MatchCommon extends AnnotatedRESTModule {
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Path till var dokumentmallar finns.", name = "Template filepath")
	String templateFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\";
	
	@ModuleSetting
	@TextFieldSettingDescriptor(description="Path till där genererade dokument sparas.", name = "New document filepath")
	String newFilePath = "C:\\Users\\pettejoh\\Desktop\\Sommarjobb\\Dokument\\Omgjorda\\Testpopulering\\";
}
