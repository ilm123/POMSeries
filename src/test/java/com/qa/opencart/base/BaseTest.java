package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;

import io.qameta.allure.Step;

public class BaseTest {

	WebDriver driver;
	protected Properties prop;
	DriverFactory df; // create one reference variable

	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegistrationPage registrationPage;
	protected SoftAssert softAssert;

	@Step("Launching {0} browser and initializing the properties")
	@Parameters({ "browser" })
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory(); // create the object
		prop = df.initProp();
		
		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop); // call by reference
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@Step("Closing the browser")
	@AfterTest
	public void teardown() {
		driver.quit();
	}
}

//** objects are created inside methods. Not in the class level
// LoginPage loginpage - This reference can be accessed by LoginPageTest class
// But when you type loginpage. you don't see the suggestions bcz loginpage reference is not publicly available
// To access the methods you have use a java keyword 
// If no keyword is used it is default in nature. Default will work within the same package. 
// LoginPageTest is in a different package 
// Don't want to make it public bcz everyone will be able to access it
// So use 'protected' - allows to access within and outside of the class and within and outside of the package

// Fetch the entire prop and give it to initDriver method

// at run time the browser will change in the memory based on the parameter being passed