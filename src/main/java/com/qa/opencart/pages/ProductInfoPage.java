package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

/* Day 61 - 4th April
 * POM_4_ProductInfoPage_SearchResultsPage_FourLevel_PageChaining_HashMap_LinkedHashMap_TreeMap.mp
 * part -- */

public class ProductInfoPage {

	// Page class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	private Map <String, String> productMap = new HashMap<String, String>();

		// 1. Private By Locators		
	private By productHeader = By.tagName("h1");
	private By images = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath(("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li"));	
	private By productPriceData = By.xpath(("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li"));

	// 2. Public Page Class Const...
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Method
	public String getProductHeader() {
		String header = eleUtil.doGetElementText(productHeader);
		System.out.println("Product Header: " + header);
		return header;
	}

	public int getProductImagesCount() {
		int totalImages = eleUtil.waitForElementsVisible(images, 10).size();
		System.out.println("Image count for " + getProductHeader() + ": " + totalImages);
		return totalImages;
	}

//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);

		for (WebElement e : metaList) {
			String text = e.getText();
			String metaKey = text.split(":")[0].trim();
			String metaValue = text.split(":")[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", price); //custom key
		productMap.put("exTaxPrice", exTaxPrice);
	}
	
	public Map <String, String> getProductData() {
		productMap.put("header", getProductHeader());
		productMap.put("product images", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		System.out.println(productMap);
		return productMap;
	}

}

// getProductHeader - why don't you return? This method will be used by other TCs, 
// It is a good practice to print it in the page before returning it
// zig zag pattern - Page Chaining Model

///****assignment
// Enter qty - one by locator, create method pass int, send keys
// click Add to cart - add to cart locator
// return the msg 
// shopping cart locator, click 
// new page shopping cart
// verification

// Meta data can also be stored in an array. But then you will have to user Assert.contains method.
// Hashmap makes it more readable when asserting it.
