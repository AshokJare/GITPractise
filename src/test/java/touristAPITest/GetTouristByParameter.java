package touristAPITest;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import apiTesting.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class GetTouristByParameter {
	
	@Test(dataProvider="getTouristByQueryParamater")
	public void getTouristByQueryParamater(String baseURI, String endpoint, String httpMethod, Number resStatusCode, String paramName, Number paramValue) {
		RestAssured.baseURI=baseURI;
		ValidatableResponse resp=RestAssured.given().when().queryParam(paramName.toString(),paramValue.intValue()).get(endpoint).then().log().all();
		JSONObject js=new JSONObject(resp.extract().body().asString());
		Assert.assertEquals(js.get(paramName), paramValue.intValue());
	}
	
	@DataProvider(name="getTouristByQueryParamater")
	public Object[][] getExcelData() throws IOException{
		String excelPath="./resources/data/TouristTestData.xlsx";
		String sheetName="getTouristByParamTest";
		
		Object excelData[][]=ExcelUtils.readExcelData(excelPath,sheetName);
		return excelData;
	}
}
