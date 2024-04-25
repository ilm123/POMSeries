package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.listeners.ExtentReportListener;

/* Day 60 - 3rd April
 * POM_3_AccountsPage_AppConstant_ResultsPage_PageChainingModel.mp4
 * part -- */

/* Day 62 - 8th April
 * POM_5_DataProvider_RegistrationPage_RandomStringUtils.mp4
 * part -- */

public class SearchResultsPageTest extends BaseTest {

	@BeforeClass
	public void searchResultsSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object [][] getProductCount() {
		return new Object [][] { // 2d object array
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
		};
	}
	
	@Test (dataProvider = "getProductCount")
	public void searchResultsCountTest(String searchKey, int productCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getSearchedProductCount(), productCount);		
	}
	
//	@Test
//	public void searchResultsCountTest() {
//		searchResultsPage = accPage.doSearch("macbook");
//		Assert.assertEquals(searchResultsPage.getSearchedProductCount(), 3);		
//	}
	
	@Test
	public void searchResultsTest() { // not sure why this is verifying the same thing as above method
		Assert.assertEquals(searchResultsPage.getSearchedProductCount(), 3);
	}
}

// The reason that doSearch line is not written inside BeforeClass is bcz it cannot be parameterized
// parameterization is done at the test level. You cannot parameterize pre conditions