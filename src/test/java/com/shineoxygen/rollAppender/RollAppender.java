package com.shineoxygen.rollAppender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shineoxygen.fileAppender.FileAppender;

public class RollAppender {
	private static Logger logger = LogManager.getLogger(RollAppender.class);

	public static void doMyBusi() {
		logger.info("该业务逻辑计算工资发放情况：9月份，共4w");

		logger.warn("工资不正常...才4w...");
	}
}
