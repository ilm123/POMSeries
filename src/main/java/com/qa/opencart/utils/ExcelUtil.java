package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {

		System.out.println("Reading data from sheet: " + sheetName);
		Object data [][] = null; //declared
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH); //creates the connection with the excel sheet			
			
			book = WorkbookFactory.create(ip); //create a copy			
			sheet = book.getSheet(sheetName);			
			
			data = new Object [sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()]; //Initializing it. Representation of the excel sheet
			
			for (int i=0; i<sheet.getLastRowNum(); i++) {
				for (int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (InvalidFormatException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return data;
	}
}

//getLastRowNum - The active last row with the data