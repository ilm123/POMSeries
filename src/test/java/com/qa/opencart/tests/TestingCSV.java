package com.qa.opencart.tests;

import java.io.FileReader;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

/* Day 63 - 9th April
 * POM_6_Apache_POI_Excel_CSV_Reader_DataProvider.mp4
 * part -- */

public class TestingCSV {
	
	@DataProvider
	public Object[][] csvData() throws Exception {
		
		String csvFile = "./src/test/resources/testdata/register.csv";

		CSVReader reader = new CSVReader(new FileReader(csvFile));

		List<String[]> rows = reader.readAll();

		reader.close(); // can also write a try catch and write the close statement in the finally block

		Object[][] data = new Object[rows.size()][]; // [] blank value for the column

		for (int i = 0; i < rows.size(); i++) {
			data[i] = rows.get(i);
		}

		return data;
	}

	@Test(dataProvider = "csvData")
	public void testData(String param1, String param2, String param3, String param4, String param5) {
		System.out.println(
				"Param1: " + param1 + ", Param2: " + param2 + ", Param3: " + param3 + ", Param4: " + param4 + ", Param5: " + param5);
	}
}

// we don't need any column bcz CSV doesn't understand any columns
