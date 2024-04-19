package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

/* Day 60 - 3rd April
 * POM_3_AccountsPage_AppConstant_ResultsPage_PageChainingModel.mp4
 * part -- */

/* Day 61 - 4th April
 * POM_4_ProductInfoPage_SearchResultsPage_FourLevel_PageChaining_HashMap_LinkedHashMap_TreeMap.mp
 * part -- */

public class SearchResultsPage {

	// Page class/Page Library/Page Object
		private WebDriver driver;
		private ElementUtil eleUtil;

		// 1. Private By Locators	
		private By searchedProducts = By.cssSelector("div.product-thumb");

		// 2. Public Page Class Const...
		public SearchResultsPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
		}

		// 3. Public Page Actions/Method
		public int getSearchedProductCount() {
			return eleUtil.waitForElementsVisible(searchedProducts, 10).size();
		}
		
		public ProductInfoPage selectProduct(String SearchKey) {
			System.out.println("Selecting the product: " + SearchKey);
			eleUtil.waitforElementVisible(By.linkText(SearchKey), 10).click(); //creating a locator at run time
			return new ProductInfoPage(driver);
		}
}

// It is not mandatory to have a test class page per page
// ProductInfoPage - TDD approach used here; creating the methods on the fly
