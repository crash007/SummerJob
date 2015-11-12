package se.sogeti.summerjob;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import se.unlogic.standardutils.json.JsonUtils;

/**
 * @author Petter Wallin
 *
 */
public class JsonResponse {
	public static void initJsonResponse(HttpServletResponse res, PrintWriter writer,
			String callback) {
		res.setCharacterEncoding("ISO-8859-1");
		if(callback != null){
			res.setContentType("text/javascript");
			writer.append(callback+'(' );
		}else{
			res.setContentType(JsonUtils.getContentType());
		}
	}
	
	public static void sendJsonResponse(String json, String callback,PrintWriter writer){
		
		writer.append(json);
		
		if(callback!=null){
			writer.append(")");
		}
		
		writer.flush();
		writer.close();
	
	}
}
