package com.qa.opencart.utils;

public class StringUtil {

	private StringUtil() {}// Prevents unnecessary creation of the object - A private-blank constructor
	
	public static String getRandomEmail() {
		String email = "testautomation" + System.currentTimeMillis() + "@opencart.com";		
		return email;
	}
}
