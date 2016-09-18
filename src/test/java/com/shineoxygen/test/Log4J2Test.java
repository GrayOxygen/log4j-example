package com.shineoxygen.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Test;

import com.shineoxygen.HiLog4J;
import com.shineoxygen.fileAppender.FileAppender;
import com.shineoxygen.review.ReviewLog4J;
import com.shineoxygen.rollAppender.RollAppender;
import com.shineoxygen.study.HelloLog4J;

/**
 * 
 * @author 王辉阳
 * @description 
 * 
 * I, log4j2的配置：
 * 
 *  多种配置方式：
 *  1，Using a configuration file written in XML, JSON, YAML or properties file.(log4j可自动检查并加载)
	2，Programmatically, by creating a configuration factory and configuration implementation.
	3，Programmatically, by calling APIs exposed in the configuration interface.
	4，Programmatically, by calling methods on the internal logger class.
 *  
 * 
 * II,Automatic Configuration
 * 
 * ConfigurationFactory负责加载配置，log4j自动加载配置文件顺序：
 * 
 * Log4j 根据 "log4j.configurationFile" system property 加载配置文件
   If no system property is set the properties ConfigurationFactory will look for log4j2-test.properties in the classpath.
   If no such file is found the YAML ConfigurationFactory will look for log4j2-test.yaml or log4j2-test.yml in the classpath.
   If no such file is found the JSON ConfigurationFactory will look for log4j2-test.json or log4j2-test.jsn in the classpath.
   If no such file is found the XML ConfigurationFactory will look for log4j2-test.xml in the classpath.
   If a test file cannot be located the properties ConfigurationFactory will look for log4j2.properties on the classpath.
   If a properties file cannot be located the YAML ConfigurationFactory will look for log4j2.yaml or log4j2.yml on the classpath.
   If a YAML file cannot be located the JSON ConfigurationFactory will look for log4j2.json or log4j2.jsn on the classpath.
   If a JSON file cannot be located the XML ConfigurationFactory will try to locate log4j2.xml on the classpath.
   If no configuration file could be located the DefaultConfiguration will be used. This will cause logging output to go to the console.
 * 
 * 
 * III，Named Hierarchy
 * 
 * 命名规则的继承结构，com.shineoxygen与com，后者是父，前者是子，通过.号分开的命名形式，log4j中会识别出父/祖先和子/子孙的关系
 * 
 * IV，Levels
 * 
 * 各个级别权重如下(log4J根据logger的level决定哪些logEvent有效即哪些打印方法会输出日志，如果日志事件所表示的级别<=level即输出)：
 * ALL>TRACE>DEBUG>INFO>WARN>ERROR>FATAL>OFF
 * Level Inheritance根据logger的named hierarchy来继承level(向上找到即使用所找到的level)
 * 
 * V，Appenders
 * 
 * appender具有可添加特性(additivity)，true为父/祖先logger的appender都会附加到当前logger上
 * 
 * 常用的有
 * 
 * ConsoleAppender
 * 日志信息输出到控制台
 * 
 * FileAppender
 * 将日志信息输出到指定文件
 * 
 * RollingFileAppender 
 * 将日志信息输出到指定文件，根据配置的TriggeringPolicy和RolloverStrategy重新生成新的文件记录后面的日志(即roll滚动)
 * TriggeringPolicy
 * 日志输出的策略
 * CronTriggeringPolicy基于cron表达式(语法同quartz schelduler)，控制日志输出的时机
 * SizeBasedTriggeringPolicy  文件大小达到指定大小时触发日志滚动
 * TimeBasedTriggeringPolicy  按时间触发日志滚动
 * 
 * RolloverStrategy
 * 滚动策略,控制滚动文件是如何完成的
 * 举例：
 * 若DefaultRolloverStrategy配置fileIndex为min：
 * 如active output target为log.log，滚动时log.log重命名为生成log-1.log，新的日志往新的log.log里写
 * 再次滚动时，log.log重名为log-1.log,log-1.log重命名为log-2.log，同时生成新的log.log依次类推
 * 
 * 若配置fileIndex为max，这是默认值
 * 如active output target为log.log，滚动时log.log重命名为生成log-1.log，新的日志往新的log.log里写
 * 再次滚动时，log.log重名为log-2.同时生成新的log.log依次类推
 * 
 * 
 * VI，Filters
 * 参照文档，我很少用
 * 
 * 
 * 
 * VII，Layouts
 * 
 * 控制日志信息的格式，常用PatternLayout，另有HTMLLayout(指定将日志信息在html中展示的格式)等等
 * PatternLayout:
 * conversion pattern是普通字符串和控制格式的表达式(conversion specifiers,约束说明符，前面必须带%)，定义了格式(类似c语言的打印语法)
 * 
 * 具体conversion配置参照log4j2官方操作手册
 * 
 * @time 2016年9月17日 上午12:04:21
 */
public class Log4J2Test {
	private static String absClassPath = null;

	@BeforeClass
	public static void init() {
		absClassPath = Log4J2Test.class.getClassLoader().getResource("").getPath();
	}

	/**
	 * 程序手动加载配置文件，配置相关信息
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void configByProgram() throws FileNotFoundException, IOException {
		// 获取configuration factory实例,如下类均可获取：
		// ConfigurationFactory，XMLConfigurationFactory，YamlConfigurationFactory,JsonConfigurationFactory
		ConfigurationFactory factory = ConfigurationFactory.getInstance();

		// 定义空的配置文件，仅有<Configuration>标签
		ConfigurationSource configurationSource = new ConfigurationSource(new FileInputStream(new File(absClassPath + "empty-log4j-config.xml")));

		// 获取configuration对象
		Configuration configuration = factory.getConfiguration(configurationSource);

		// 创建默认的控制台附属器
		ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(PatternLayout.createDefaultLayout());

		// 装配到configuration中
		configuration.addAppender(appender);

		// 创建loggerConfig对象
		LoggerConfig loggerConfig = new LoggerConfig("com", Level.FATAL, false);

		// 添加附属器
		loggerConfig.addAppender(appender, null, null);

		// 新增一个logger对象(关联了loggerConfig)
		configuration.addLogger("com", loggerConfig);

		// 获取logger context实例
		LoggerContext context = new LoggerContext("ConfigurationTest");

		// 开启log4j system
		context.start(configuration);

		// 获取logger
		Logger logger = context.getLogger("com");

		// LogEvent
		logger.log(Level.FATAL, "Logger Name [" + logger.getName() + "] FATAL级别日志信息 ");
		logger.log(Level.ERROR, "Logger Name [" + logger.getName() + "] ERROR级别日志信息，不会打印 ");
		// Root Logger处理
		logger.getParent().log(Level.ERROR, "[Root Logger]  日志信息，默认配置是：level为ERROR，appender为console，并拥有其默认样式");
	}

	/**
	 * 配置文件的自动定位
	 * 
	 */
	@Test
	public void autoConfig() {
		// automatic configuration:存在log4j2.xml时自动定位到配置文件
		Logger logger = (Logger) LogManager.getLogger(Log4J2Test.class);
		logger.trace("trace log event message");
		logger.debug("debug log event message");
		logger.info("info log event message");
		logger.warn("warn log event message");
		logger.error("error log event message");
		logger.fatal("fatal log event message");
	}

	/**
	 * level具有继承性，当前logger没有设置level则根据named hierarchy命名继承结构向上找level找到level为止继承之
	 */
	@Test
	public void levelInheritance() {
		// com.shineoxygen.study没配置level，而com.shineoxygen配置level为DEBUG，HelloLog4J继承DEBUG的level
		HelloLog4J.hello();
		HiLog4J.hi();
	}

	/**
	 * appender的additivity（可附加特性），logger默认additivity为true，表示将父/祖先的appender自动添加应用到当前logger
	 */
	@Test
	public void appenderAdditivity() {
		// com.shineoxygen设置为appender不可附件，com.shineoxygen.review设置为可附加
		// 所以日志信息打印了两次，两个appender输出了日志信息
		ReviewLog4J.review();
	}

	/**
	 * 测试FileAppender记录日志信息在指定文件中
	 */
	@Test
	public void fileAppender() {
		// 测试前先删除日志文件，检查是否生成日志文件
//		File file = new File("logs");
//		if (file.isDirectory()) {
//			File[] files = file.listFiles();
//			for (File dir : files) {
//				dir.delete();
//			}
//		}
//		file.delete();
		int counter = 0;
		while (counter++ < 1000) {
			FileAppender.doMyBusi();
		}

	}

	@Test
	public void rollingFileAppender() {
		int counter = 0;
		while (counter++ < 10000) {
			RollAppender.doMyBusi();
		}

	}
}
