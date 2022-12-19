package touristAPITest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiTesting.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class DeleteTouristEntity {
	
	private int touristId;
	
	@Test(dataProvider="getAllTourist")
	public void getTouristId(String baseURI, String endpoint, String httpMethod, Number resStatusCode) {
		
		RestAssured.baseURI=baseURI;
		ValidatableResponse resp=RestAssured.given().when().get(endpoint).then().log().all();
		resp.statusCode(resStatusCode.intValue());
		
		JSONObject jsObj=new JSONObject(resp.extract().body().asString());
		System.out.println(jsObj);
		List<Integer> ls=new ArrayList<Integer>();
		JSONArray jsRespArray=jsObj.getJSONArray("data");
		for(int i=0;i<jsRespArray.length();i++) {
			ls.add((Integer) jsRespArray.getJSONObject(i).get("id"));
		}
		Random rn=new Random();
		System.out.println("Tourist ID Selcted :" +ls.get(rn.nextInt(ls.size()-1)));
		touristId= ls.get(rn.nextInt(ls.size()-1));
	
	}
	
	@Test(dataProvider="deleteTouristEntity")
	public void deleteTouristEntity(String baseURI, String endpoint, String httpMethod, Number resStatusCode) {
		RestAssured.baseURI=baseURI;
		ValidatableResponse resp=RestAssured.given().when().delete(endpoint+"/"+touristId).then().log().all();
		resp.statusCode(resStatusCode.intValue());
		
	
	}
	
	@DataProvider(name="getAllTourist")
	public Object[][] getExcelData() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="getAllTouristTest";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
	
	@DataProvider(name="deleteTouristEntity")
	public Object[][] getExcelData1() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="deleteTouristEntity";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
	
	
}
