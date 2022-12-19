package apiTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {
	
	public static Object[][] readExcelData(String excelPath, String sheetName) throws IOException {

		Object[][] testData=null;
		
		File file=new File(excelPath);
		FileInputStream fis=new FileInputStream(file);
		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheet(sheetName);
		
		int totalRows=sheet.getPhysicalNumberOfRows();
		int columns = sheet.getRow(1).getLastCellNum();
		
		System.out.println("Total Rows: " +totalRows+ " Total Cells: " +columns);
		
		testData=new Object[totalRows-1][columns];
		int ci,cj;
		ci=0;
		for(int i=1;i<=totalRows-1;i++,ci++) {
			cj=0;
			Row row=sheet.getRow(i);
			int totalCells=columns;
			
			for(int j=0;j<totalCells;j++,cj++) {
				
				if (row.getCell(cj).getCellType().toString()=="NUMERIC") {
					testData[ci][cj]=row.getCell(cj).getNumericCellValue();
				}
				else {
					testData[ci][cj]=row.getCell(cj).toString();
				}
			}
		}
		
		return testData;
		
	}
}
