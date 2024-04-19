package com.qa.opencart.tests;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

/* Day 60 - 3rd April
 * POM_3_AccountsPage_AppConstant_ResultsPage_PageChainingModel.mp4
 * part -- */

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountPageTitleTest() {
		String actTitle = accPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void accountPageURLTest() {
		String actUrl = accPage.getAccountPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.ACC_PAGE_URL_FRACTION));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void isMyAccountLinkExistTest() {
		Assert.assertTrue(accPage.isMyAccountLinkExist());
	}
	
	@Test
	public void accPageHeadersTest() {
		ArrayList<String> actHeadersList = accPage.getAccountPageHeaders();
		System.out.println(actHeadersList);
		//Assert later
	}
	
	@Test
	public void searchTest() {
		accPage.doSearch("macbook");
		//Assert Later
	}
		

}

// Line 19 - In Java - when ever a method returns an object it is stored in the same reference variable
// reference variable is maintained in BaseTest so that it can be accessed any test class can access it.