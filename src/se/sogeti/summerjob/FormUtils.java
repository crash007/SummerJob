package se.sogeti.summerjob;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FormUtils {
	public static List<String> getMentorUuids(Enumeration<String> paramNames) {
		List<String> result = new ArrayList<String>();
		while(paramNames.hasMoreElements()){
			String s = paramNames.nextElement();
			if(s.startsWith("mentor-firstname")){				
				String uuid = s.split("_")[1];
				
				result.add(uuid);
			}
		}
		return result;
	}
}
