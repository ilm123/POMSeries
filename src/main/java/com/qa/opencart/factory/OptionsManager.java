package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qa.opencart.logger.Log;

/* Day 64 - 10th April
 * POM_7_TestRunners_HeadLess_Incognito_Highlight_AppErrors_ParallelRun.mp4
 * part -- */

public class OptionsManager {
	
	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;	
	
	public OptionsManager (Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
		}
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {		
//			System.out.println("Running chrome in headless mode");
			Log.info("Running chrome in headless mode");
			co.addArguments("--headless");				
		}
		
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {				
			co.addArguments("--incognito");
			Log.info("Running chrome in incognito mode");
		}
		
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
		}
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {			
			System.out.println("Running firefox in headless mode");
			fo.addArguments("--headless");				
		}
		
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {				
			fo.addArguments("--private");				
		}
		
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
			eo.setCapability("platform", Platform.LINUX);
		}
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {		
			System.out.println("Running edge in headless mode");
			eo.addArguments("--headless");				
		}
		
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {				
			eo.addArguments("--inprivate");				
		}
		
		return eo;
	}

}

// if these checks are not satisfied, i.e both are false, it will return a blank co
