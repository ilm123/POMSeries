package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

//POM_3_AccountsPage_AppConstant_ResultsPage_PageChainingModel.mp4

/* Day 62 - 8th April
 * POM_5_DataProvider_RegistrationPage_RandomStringUtils.mp4
 * part -- */

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//input[@name='newsletter' and @value='1']");
	private By subscribeNo = By.xpath("//input[@name='newsletter' and @value='0']");
	private By policyCheckBox = By.name("agree");
	private By continueBtn = By.cssSelector("#content > form > div > div > input.btn.btn-primary");
	private By regSucessMsg = By.tagName("h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public boolean userRegistration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		eleUtil.waitforElementVisible(this.firstName, 10).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(policyCheckBox);
		eleUtil.doClick(continueBtn);

		String successMsg = eleUtil.waitforElementVisible(regSucessMsg, 5).getText();
		System.out.println(successMsg);

		if (successMsg.equals(AppConstants.USER_REG_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;			
		} else {
			return false;
		}
	}
}

// Another way is to return the sucessMsg and let Testng decide true or false