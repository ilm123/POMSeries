package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

/* Day 65 - 11th April
 * POM_8_MultiEnvSetup_MavenCommands_CommandLineArguments.mp4
 * part -- */

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		System.out.println("Browser Name is: " + browserName);

		highlight = prop.getProperty("highlight"); // initialized

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;

		case "firefox":
			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;

		case "edge":
			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;

		case "safari":
			driver = new SafariDriver();
			break;

		default:
			System.out.println("Please pass the right browser " + browserName);
			throw new BrowserException("NO BROWSER FOUND " + browserName);
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
		// Don't use implicit wait bcz there are util methods with explicit wait and
		// shouldn't be mixed
		return driver;
	}

	// envName=qa,stage,prod,uat,dev
	// mvn clean install -Denv="qa"

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env"); // passing the env name from maven to java

		System.out.println("Running tests on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("No env was given... Hence running the test cases on the QA env...");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					System.out.println("Please pass the right env name..." + envName);
					throw new FrameworkException(AppError.ENV_NAME_NOT_FOUND + ":" + envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}

//public Properties initProp2() {
//	prop = new Properties();
//	try {
//		FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
//		prop.load(ip);
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	return prop;
//
//}

// prop.getProperty("browser"); - reading it from the config.properties file
// current project directory - ./
// FileInputStream - a connection will be established with config.properties
// After that all the properties are loaded into the Properties object
// Multiple catch clauses are added with one single try
// If you want to wait for page load, do it in this class.
// driver property it self behaves as once the page is fully loaded then it proceeds 99% of the time. 
// so page load can be ignored.

// ----
// common try catch block instead of writing it for every line
