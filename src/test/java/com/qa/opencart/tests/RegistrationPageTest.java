package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

/* Day 63 - 9th April
 * POM_6_Apache_POI_Excel_CSV_Reader_DataProvider.mp4
 * part -- */

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registrationPage = loginPage.navigateToRegistrationPage();
	}

	@DataProvider
	public Object[][] userRegistrationTestData() {
		return new Object[][] { { "Mathilda", "Phyliss", "987654332", "math@123", "yes" },
				{ "Jack", "Winona", "4443332221", "jack@123", "no" },
				{ "Della", "Stanton", "123456789", "dell@123", "yes" } };
	}
	
	@DataProvider
	public Object[][] userRegistrationTestDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_CSV_NAME);		
	}
	
	@DataProvider (name = "csvregdata") 
	public Object[][] userRegistrationTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_CSV_NAME);	
	}

//	@Test(dataProvider = "userRegistrationTestDataFromCSV")
	@Test(dataProvider = "csvregdata") // another way to refer the method
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(registrationPage.userRegistration(firstName, lastName, StringUtil.getRandomEmail(), telephone,
				password, subscribe));
	}

}

// using StringUtil.getRandomEmail() in the DP doesn't work. it generates the email only once and uses the same
// email for the rest of the rows bcz the method is static and the treated as common variable

// make sure that the sequence in the test method is maintained correctly according to the column names

// If you have small amount of data use DataProvider
// Excel and CSV are good when you have alot of data. Excel requires a license
// JSON is more complex

// -------------------
// Should we called this framework as data driven framework or hybrid?
// This approach in the framework is called Data Driven Flow or Data Driven Approach
// There is no such thing as Data Driven Framework
// When ever required, maintain the data in the excel or CSV
// Fetching the data from the util class, then given to the Data Provider
// DP gives it to the Test annotation
// Test annotation are performing the action on the page actions
// This is the Data Driven Flow in this framework
// What type of framework do we use?
// A. we use a hybrid framework with the Data Driven Approach

// What is the Design Pattern? Page Object Model design pattern, with utilities and testng
