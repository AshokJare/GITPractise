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

public class UpdateTouristEntity {
	@Test(dataProvider="updateTouristEntity")
	public void updateTouristEntity(String baseURI, String endpoint, String httpMethod, Number touristId, String touristNm, String touristEmail, String location, Number resStatusCode) {
		RestAssured.baseURI=baseURI;
		
		JSONObject jsReqBody=new JSONObject();
		jsReqBody.put("tourist_name", touristNm+Math.random());
		jsReqBody.put("tourist_email", touristEmail+Math.random());
		jsReqBody.put("tourist_location", location);
		jsReqBody.put("id", 14842);
		
		ValidatableResponse resp=RestAssured.given()
				.contentType(ContentType.JSON)
				.body(jsReqBody.toString())
				.when()
				.put(endpoint).then().log().all();
		
		JSONObject jsResp=new JSONObject(resp.extract().body().asString());
		Assert.assertEquals(location,jsResp.get("tourist_location"));
		Assert.assertEquals(touristId.intValue(), jsResp.get("id"));
	}
	
	@DataProvider(name="updateTouristEntity")
	public Object[][] getExcelData() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="updateTouristEntity";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
}
