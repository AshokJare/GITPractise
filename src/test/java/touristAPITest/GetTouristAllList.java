package touristAPITest;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import apiTesting.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class GetTouristAllList {

	@Test(dataProvider="getAllTourist")
	public void getAllTourist(String baseURI, String endpoint, String httpMethod, Number resStatusCode) {
		
		RestAssured.baseURI=baseURI;
		ValidatableResponse resp=RestAssured.given().when().get(endpoint).then().log().all();
		resp.statusCode(resStatusCode.intValue());
		
		JSONObject js=new JSONObject(resp.extract().asString());
		System.out.println("Response JSON Object Keys: " +js.length());
		System.out.println("Response JSON Object : " +js);
		
		//for (int i=0;i<js.length();i++) {
			//System.out.println(js.get(httpMethod));
		//}
			
		iterateJSON(js);
		


	}
	
	public void iterateJSON(JSONObject js) {
		
		boolean jsonExist=js.length()>0;
		
		Iterator <?> jsonKeys;
		String nextKey;
		
		if (jsonExist) {
			jsonKeys=js.keys();
			while(jsonKeys.hasNext()) {
				nextKey=(String) jsonKeys.next();
				System.out.println(nextKey + " : " +js.get(nextKey));
				
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
			}
		}
	}
	
	@DataProvider(name="getAllTourist")
	public Object[][] getExcelData() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="getAllTouristTest";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
}
