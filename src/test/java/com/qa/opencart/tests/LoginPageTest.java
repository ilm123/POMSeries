package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.listeners.ExtentReportListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/* Day 64 - 10th April
 * POM_7_TestRunners_HeadLess_Incognito_Highlight_AppErrors_ParallelRun.mp4
 * part -- */

/* Day 66 - 15th April
 * POM_9_Log4j_Logger_Listener_extent_report_screenshot.mp4
 * part -- */

/* Day 67 - 16th April
 * POM_10_AllureReport_CustomReport_RetryLogic.mp4
 * part -- */

//@Listeners (ExtentReportListener.class)
@Epic("Epic 100: Design open cart login page")
@Story("US 101: Design login page feature for open cart application")
@Feature("Feature 201: Adding login feature")
public class LoginPageTest extends BaseTest {

	@Description("Checking login page title...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

	@Description("Checking login page URL...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageUrl();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND);
	}

	@Description("Checking forgot pwd link on login page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPasswordLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
	}

	@Description("Checking that the user is able to login")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}

}

// if you create the object of loginPage in this class, when you write another test,
// every time you will have to create it unnecessarily.
// So create it in the parent class only once.
// Then this class can inherit the properties of the parent class and use it