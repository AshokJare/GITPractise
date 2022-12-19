package touristAPITest;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiTesting.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class CreateTouristEntity {

	@Test(dataProvider="createTouristEntity")
	public void createTouristEntity(String baseURI, String endpoint, String httpMethod, String touristNm, String touristEmail, String location, Number resStatusCode ) {
		
		JSONObject json = new JSONObject();
		json.put("tourist_name", touristNm+Math.random());
		json.put("tourist_email", touristEmail+Math.random());
		json.put("tourist_location", location);
		
		RestAssured.baseURI=baseURI;
		
		ValidatableResponse resp=RestAssured.given()
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when().post(endpoint).then().log().all();
		
		JSONObject js=new JSONObject(resp.extract().body().asString());
		Assert.assertEquals(js.get("tourist_location"), location);
		Assert.assertTrue(js.get("tourist_name").toString().contains(touristNm));
		Assert.assertTrue(js.get("tourist_email").toString().contains(touristEmail));
	}
	
	@DataProvider(name="createTouristEntity")
	public Object[][] getExcelData() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="createTouristEntity";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
}
