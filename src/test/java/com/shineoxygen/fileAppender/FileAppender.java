package com.shineoxygen.fileAppender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author 王辉阳
 * @description 该类日志需要写入文件
 * @time 2016年9月17日 下午5:26:14
 */
public class FileAppender {
	private static Logger logger = LogManager.getLogger(FileAppender.class);

	public static void doMyBusi() {
		System.out.println("处理业务逻辑，开发人员记录日志");
		logger.info("该业务逻辑计算盈利额为：9月份，共5w");

		logger.warn("盈利额不正常...才5w...");
	}

}
