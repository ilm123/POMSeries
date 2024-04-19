package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

/* Day 62 - 8th April
 * POM_5_DataProvider_RegistrationPage_RandomStringUtils.mp4
 * part -- */

/* Day 63 - 9th April
 * POM_6_Apache_POI_Excel_CSV_Reader_DataProvider.mp4
 * part -- */

public class ProductInfoPageTest extends BaseTest {

	// AAA -->UTs
	// TC -- only one hard assertion or can have multiple soft assertions

	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductSearchData() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "imac", "iMac" },
				{ "samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" } };
	}

	@Test(dataProvider = "getProductSearchData")
	public void getProductHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}

//	@Test
//	public void getProductHeaderTest() {
//		searchResultsPage = accPage.doSearch("macbook");
//		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
//		Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
//	}

	@DataProvider
	public Object[][] getProductImageData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "imac", "iMac", 3 },
				{ "samsung", "Samsung SyncMaster 941BW", 1 }, { "samsung", "Samsung Galaxy Tab 10.1", 7 } };
	}

	@DataProvider
	public Object[][] getProductImageDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
	}

	@Test (dataProvider = "getProductImageDataFromExcel")
	public void getProductImagesCountTest(String searchKey, String productName, String imageCount) {
		searchResultsPage = accPage.doSearch(searchKey); 
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imageCount));


//	@Test
//	public void getProductImagesCountTest() {
//		searchResultsPage = accPage.doSearch("imac"); // when searching, it doesn't need to be capital M
//		productInfoPage = searchResultsPage.selectProduct("iMac");
//		Assert.assertEquals(productInfoPage.getProductImagesCount(), 3);
	}

	@Test
	public void getProductInfoTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productActDetailsMap = productInfoPage.getProductData();
		softAssert.assertEquals(productActDetailsMap.get("Brand"), "Apple"); // Apply the key and get the value
		softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productActDetailsMap.get("Reward Points"), "800");
		softAssert.assertEquals(productActDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productActDetailsMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productActDetailsMap.get("exTaxPrice"), "$2,000.00");
		softAssert.assertAll(); // this line is mandatory as it will should which assertion failed
	}
}

// Read the code from RHS to LHS

// Follow the AAA - Arrange Act Assert (followed  by Developers)
// Performing some action, arranging and asserting it
// Should not write multiple Assertions
// The property of the Assert is that, the moment one assertion fails, the entire the TC will fail
// Use Soft Assertion to avert this issue

// AssertionError
// When an assertion fails - it is an error not an exception - AssertionError
// This is an Error log. Not an exception log
// Exception and Error - Both come from the Throwable class

// No need to write for WaitForPageLoad. It can be written in the individual methods.

// Need to have one scenario starting from login and another scenario starting from registration
// Have one test for each scenario
// Once your pages are ready, it is upto you on how you want arrange the sequence
// But then you cannot have a setup method that starts from the login. It will need to be written inside the test

///---
// Java, selenium, Testng does not provide any method to read data. 
// we need to use an external source such as Apache POI API
// problems with using excel - company might not allow, no license, someone can delete the sheet
// advantage- helps when you have huge amount of data
// worst is to use a DB - what if you have no access, unnecessary connections to get a small piece of data. 
// These are deprecated approaches