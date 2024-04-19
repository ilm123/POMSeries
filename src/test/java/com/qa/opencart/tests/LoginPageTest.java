package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

/* Day 64 - 10th April
 * POM_7_TestRunners_HeadLess_Incognito_Highlight_AppErrors_ParallelRun.mp4
 * part -- */

public class LoginPageTest extends BaseTest {

	@Test (priority = 1)
	public void loginPageTitleTest() {		
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Test (priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageUrl();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION),AppError.URL_NOT_FOUND);
	}

	@Test (priority = 3)
	public void forgotPasswordLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
	}

	@Test (priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

}

// if you create the object of loginPage in this class, when you write another test,
// every time you will have to create it unnecessarily.
// So create it in the parent class only once.
// Then this class can inherit the properties of the parent class and use it