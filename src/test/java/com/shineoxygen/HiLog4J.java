package com.shineoxygen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shineoxygen.study.HelloLog4J;

public class HiLog4J {
	public static void hi() {
		Logger logger = LogManager.getLogger(HiLog4J.class);
		logger.trace("trace log event message  in HiLog4J");
		logger.debug("debug log event message  in HiLog4J");
		logger.info("info log event message  in HiLog4J");
		logger.warn("warn log event message  in HiLog4J");
		logger.error("error log event message  in HiLog4J");
		logger.fatal("fatal log event message  in HiLog4J");
		System.out.println();
	}
}
