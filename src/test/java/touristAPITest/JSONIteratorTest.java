package touristAPITest;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONIteratorTest {

	@Test
	public void iteratorJSONTest() {
		String jsonString1="{\r\n"
				+ "	\"page\": 1,\r\n"
				+ "	\"per_page\": 10,\r\n"
				+ "	\"totalrecord\": 34848,\r\n"
				+ "	\"total_pages\": 3485,\r\n"
				+ "	\"data\": [\r\n"
				+ "		{\r\n"
				+ "			\"id\": 192279,\r\n"
				+ "			\"tourist_name\": \"Mike\"\r\n"
				+ "		},\r\n"
				+ "		{\r\n"
				+ "			\"id\": 192277,\r\n"
				+ "			\"tourist_name\": \"james\"\r\n"
				+ "		},\r\n"
				+ "		{\r\n"
				+ "		\"contact\" : [\r\n"
				+ "		    {\r\n"
				+ "		      \"ContactType\": \"Email\",\r\n"
				+ "		      \"ContactValue\": \"123456\"\r\n"
				+ "		    },\r\n"
				+ "		    {\r\n"
				+ "		      \"ContactType\": \"Phone\",\r\n"
				+ "		      \"ContactValue\": \"456789\"\r\n"
				+ "		    }\r\n"
				+ "		  ]\r\n"
				+ "		}\r\n"
				+ "	]\r\n"
				+ "}";
		
		
		JSONObject js1=new JSONObject(jsonString1);
		iterateJSON(js1);
		
		
		
		
	}
	public void iterateJSON(JSONObject js) {
		
		boolean jsonExist=js.length()>0;
		
		Iterator <?> jsonKeys;
		String nextKey;
		
		if (jsonExist) {
			jsonKeys=js.keys();
			while(jsonKeys.hasNext()) {
				nextKey=(String) jsonKeys.next();
				
				if(js.get(nextKey) instanceof JSONObject) {
					iterateJSON(js.getJSONObject(nextKey));
				}
				else if(js.get(nextKey) instanceof JSONArray) {
					JSONArray jsArr =js.getJSONArray(nextKey);
					for (int i=0;i<jsArr.length();i++) {
						String jsArrayStr=jsArr.get(i).toString();
						JSONObject innerJSON=new JSONObject(jsArrayStr);
						
						if(innerJSON.length()>0) {
							iterateJSON(innerJSON);
						}
					}
				}
				else {
					System.out.println(nextKey + " : " +js.get(nextKey));
				}
				
			}
		}
		else {
			Assert.assertFalse(false, "JSON is empty");
		}
	}
}

