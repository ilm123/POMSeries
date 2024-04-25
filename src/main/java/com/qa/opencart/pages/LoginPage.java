package com.qa.opencart.pages;

import java.sql.Time;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

/* Day 62 - 8th April
 * POM_5_DataProvider_RegistrationPage_RandomStringUtils.mp4
 * part -- */

/* Day 64 - 10th April
 * POM_7_TestRunners_HeadLess_Incognito_Highlight_AppErrors_ParallelRun.mp4
 * part -- */

/* Day 66 - 15th April
 * POM_9_Log4j_Logger_Listener_extent_report_screenshot.mp4
 * part -- */

public class LoginPage {
	
	//Page class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By emailID = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwd = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");
    
    // 2. Public Page Class Const...
    public LoginPage(WebDriver driver) {
    	this.driver = driver;
    	eleUtil = new ElementUtil(driver);
    }
    
    // 3. Public Page Actions/Method
    public String getLoginPageTitle() {
    	String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);    	
//    	System.out.println("Login page title is : " + title);
    	Log.info("Login page title is : " + title);
    	return title;
    }
    
    public String getLoginPageUrl() {
    	String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
    	System.out.println("Login page url is:" + url);
    	return url;
    }
    
    public boolean isForgotPasswordLinkExist() {
    	return eleUtil.isElementDisplayed(forgotPwd);
    }
    
    public AccountsPage doLogin(String username, String pwd) {
    	System.out.println("user creds:" + username + ":" + pwd);
    	eleUtil.waitforElementVisible(emailID, TimeUtil.DEFAULT_LONG_TIME).sendKeys(username);
    	eleUtil.doSendKeys(password, pwd);
    	eleUtil.doClick(loginBtn);
    	return new AccountsPage(driver);
    }
    
    public RegistrationPage navigateToRegistrationPage() {
    	eleUtil.waitforElementVisible(registerLink, TimeUtil.DEFAULT_LONG_TIME).click();
    	return new RegistrationPage(driver);
    }
}

// According to the documentation, the locators should be made private
// If it is public, anyone can access it and manipulate it
// If it is static, that is even worse bcz it wont work in parallel mode

// When ever a button click or link click takes you to another page, it is the responsibility of that method
// to return page class object of the new landing page
// Page chaining model - A method that gives the object of the next landing page


// --- 
// Give the method name that matches the behavior of the application

// Are we fetching both Actual title and expected title from AppConstatns.java? No. 
// eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, 5); Reverse engineering
// waitForTitle If it is equal to what is there in AppConstant.java. If it is not there then don't return anything
// According to the logic in element util, wait for upto 5 secs, if the expected title is equal to this then give the exact
// title otherwise don't give it
