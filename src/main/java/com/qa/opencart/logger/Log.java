package com.qa.opencart.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* Day 66 - 15th April
 * POM_9_Log4j_Logger_Listener_extent_report_screenshot.mp4
 * part -- */

public class Log {
	private static final Logger logger = LogManager.getLogger(Log.class);

	public static void info(String message) {
		logger.info(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void error(String message, Exception e) {
		logger.error(message, e);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}	
}
