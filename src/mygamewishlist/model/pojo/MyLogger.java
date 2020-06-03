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
	
	/**
	 * Logs the whole message of the exception's stacktrace
	 * 
	 * @param ste StackTraceElement[]
	 */
	public void logError(StackTraceElement[] ste) {
		logger.error(buildStackTrace(ste));
	}
	
	/**
	 * Calls and logs every result of a function of a Exception
	 * (Unnecessary and not efficient, used only in cases of needing to 
	 * find the exact cause of a bug).
	 * 
	 * @param e Exception
	 */
	public void logError(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append("Localized message: ")
			.append(e.getLocalizedMessage())
			.append("\n")
			.append("Message: ")
			.append(e.getMessage())
			.append("\n")
			.append("Cause: ")
			.append(e.getCause())
			.append("\n")
			.append(buildStackTrace(e.getStackTrace()))
			.append("Suppressed Warnings: ")
			.append(e.getSuppressed().toString())
			.append("\n");
		
		logger.error(sb.toString());
	}
	
	/**
	 * Returns a String with one line per StackTraceElement
	 * 
	 * @param ste StackTraceElement[]
	 * @return String
	 */
	private String buildStackTrace(StackTraceElement[] ste) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement s : ste) {
			sb.append(s.toString())
				.append("\n");
		}
		return sb.toString();
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
