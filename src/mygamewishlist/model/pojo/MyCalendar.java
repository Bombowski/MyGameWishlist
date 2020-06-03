package mygamewishlist.model.pojo;

import java.util.Calendar;

/**
 * @author Patryk
 *
 * Singleton class for managing Calendar object
 */
public class MyCalendar {

	private static MyCalendar mc;
	private static Calendar c;
	
	private MyCalendar() {
		c = Calendar.getInstance();
	}
	
	public static MyCalendar getMC() {
		if (mc == null) {
			synchronized (MyCalendar.class) {
				if (mc == null) {
					mc = new MyCalendar();
				}
			}
		}
		return mc;
	}
	
	/**
	 * Updates calendar with current instance
	 */
	public void updateCalendar() {
		c = Calendar.getInstance();
	}
	
	/**
	 * @return Returns Calendar object
	 */
	public Calendar getCalendar() {
		return c;
	}
	
	/**
	 * Returns a date with a sql date format
	 * 
	 * @return String
	 */
	public String getSqlDate() {
		return new StringBuilder()
				.append(c.get(Calendar.YEAR))
				.append("-")
				.append(c.get(Calendar.MONTH) + 1)
				.append("-")
				.append(c.get(Calendar.DAY_OF_MONTH))
				.toString();
	}
	
	/**
	 * Returns current hour
	 * 
	 * @return int 1-24
	 */
	public int getHour() {
		return c.get(Calendar.HOUR_OF_DAY);
	}
}
