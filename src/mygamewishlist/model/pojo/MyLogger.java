package mygamewishlist.model.pojo;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * @author Patryk
 *
 * Clase logger
 */
public class MyLogger {

	private static MyLogger ml = new MyLogger();
	private static Logger err;
	
	private MyLogger() {
		err = (Logger) LoggerFactory.getLogger("FILE_ERROR");
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
	 * Logea un mensaje
	 * 
	 * @param msg String
	 */
	public void logError(String msg) {
		err.error(msg);
	}
	
	public void logDebug(String msg) {
		err.debug(msg);
	}
}
