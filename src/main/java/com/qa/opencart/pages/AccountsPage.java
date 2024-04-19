package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

/* Day 60 - 3rd April
 * POM_3_AccountsPage_AppConstant_ResultsPage_PageChainingModel.mp4
 * part -- */

public class AccountsPage {

	// Page class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By logoutLink = By.linkText("Logout");
	private By myAccountLink = By.linkText("My Account");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	// 2. Public Page Class Const...
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Method
	public String getAccountPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 5);
		System.out.println("Login page title is : " + title);
		return title;
	}

	public String getAccountPageUrl() {
		String url = eleUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION, 5);
		System.out.println("Login page url is:" + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitforElementVisible(logoutLink, 10).isDisplayed();
	}

	public boolean isMyAccountLinkExist() {
		return eleUtil.waitforElementVisible(myAccountLink, 10).isDisplayed();
	}

	public ArrayList<String> getAccountPageHeaders() {
		
		List<WebElement> headersElementList = eleUtil.getElements(headers);
		ArrayList<String> headersList = new ArrayList<String>();

		for (WebElement e : headersElementList) {
			String header = e.getText();
			headersList.add(header);
		}
		
		return headersList;
	}

	public SearchResultsPage doSearch(String SearchKey) {
		System.out.println("Searching for: " + SearchKey);
		eleUtil.doSendKeys(search, SearchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}

}
