package com.shineoxygen.review;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shineoxygen.study.HelloLog4J;

public class ReviewLog4J {
	public static void review() {
		Logger logger = LogManager.getLogger(ReviewLog4J.class);
		logger.trace("trace log event message  in ReviewLog4J");
		logger.debug("debug log event message  in ReviewLog4J");
		logger.info("info log event message  in ReviewLog4J");
		logger.warn("warn log event message  in ReviewLog4J");
		logger.error("error log event message  in ReviewLog4J");
		logger.fatal("fatal log event message  in ReviewLog4J");
		System.out.println();
	}
}
