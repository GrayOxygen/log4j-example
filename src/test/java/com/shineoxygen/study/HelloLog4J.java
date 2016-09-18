package com.shineoxygen.study;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloLog4J {
	public static void hello() {
		Logger logger = LogManager.getLogger(HelloLog4J.class);
		logger.trace("trace log event message  in HelloLog4J");
		logger.debug("debug log event message  in HelloLog4J");
		logger.info("info log event message  in HelloLog4J");
		logger.warn("warn log event message  in HelloLog4J");
		logger.error("error log event message  in HelloLog4J");
		logger.fatal("fatal log event message  in HelloLog4J");
		System.out.println();
	}
}
