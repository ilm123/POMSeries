package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

/* Day 65 - 11th April
 * POM_8_MultiEnvSetup_MavenCommands_CommandLineArguments.mp4
 * part -- */

/* Day 66 - 15th April
 * POM_9_Log4j_Logger_Listener_extent_report_screenshot.mp4
 * part -- */

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
//		System.out.println("Browser Name is: " + browserName);
		Log.info("Browser name is: " + browserName);

		highlight = prop.getProperty("highlight"); // initialized

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote grid execution
				init_remoteDriver("chrome");
			} else {
				// local env execution
//				driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));				
			}
			break;

		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			} else {
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));				
			}
			break;

		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("edge");
			} else {
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));				
			}
			break;

		case "safari":
//			driver = new SafariDriver(); // there are no options for safari driver
			tlDriver.set(new SafariDriver());
			break;

		default:
//			System.out.println("Please pass the right browser " + browserName);
			Log.error("Please pass the right browser... " + browserName);
			throw new BrowserException("NO BROWSER FOUND " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		// Don't use implicit wait bcz there are util methods with explicit wait and
		// shouldn't be mixed
		return getDriver();
	}

	/**
	 * Run tests on Selenium Grid
	 * 
	 * @param browserName
	 */

	private void init_remoteDriver(String browserName) {

		System.out.println("Running the test cases on the Remote GRID: " + browserName);

		try {
			switch (browserName.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;

			case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;

			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;

			default:
				System.out.println("Please pass the right supported browser on GRID...");
				break;
			}
		}

		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() { // A wrapper on top of the get method
		return tlDriver.get();
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

	/**
	 * take screenshot
	 */

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp directory
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
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

// ---
// what is the difference between the println statement and the log? which one is faster?
// log is faster. println will consume the resources. 
// first it will use the system class. Then out variable.out variable will call println
// syso is very raw type of log. which class, date time info
// With the log we get the proper information along with our custom message

// tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); internally do the top casting and launch the chrome browser

// ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE)
// type cast the driver as TakesScreenshot. This is like converting the driver to JS executer and using .executeScript()
