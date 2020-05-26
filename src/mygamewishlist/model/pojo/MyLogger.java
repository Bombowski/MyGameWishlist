package mygamewishlist.model.pojo;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * @author Patryk
 *
 * Singleton logger class
 */
public class MyLogger {

	private static MyLogger ml = new MyLogger();
	private static Logger logger;
	
	private MyLogger() {
		logger = (Logger) LoggerFactory.getLogger("DefaultLogger");
	}
	
	public static MyLogger getLOG() {
		if (ml == null) {
			synchronized (MyLogger.class) {
				if (ml == null) {
					ml = new MyLogger();
				}
			}
		}
		return ml;
	}
	
	/**
	 * Logs a message as an error
	 * 
	 * @param msg String
	 */
	public void logError(String msg) {
		logger.error(msg);
	}
	
	public void logError(StackTraceElement[] ste) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement s : ste) {
			sb.append(s.toString());
		}
		logger.error(sb.toString());
	}	
	
	/**
	 * Logs a message as debug
	 * 
	 * @param msg String
	 */
	public void logDebug(String msg) {
		logger.debug(msg);
	}
}
