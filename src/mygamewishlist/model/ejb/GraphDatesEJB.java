package mygamewishlist.model.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.pojo.db.TimelineGameDetailed;

/**
 * @author Patryk
 *
 * Class used for generating graph's
 */
@Stateless
@LocalBean
public class GraphDatesEJB {

	private List<LocalDate> dates;
	
	public GraphDatesEJB() {
	}

	/**
	 * Generates a Hashtable of games from timeline. This function will
	 * basically move everything from list to their correct position in
	 * the timeline.
	 * 
	 * @param list Hashtable<String, <ArrayList<TimelineGameDetaied>>
	 * @return Hashtable<String, <ArrayList<TimelineGameDetaied>>
	 */
	public Hashtable<String, ArrayList<TimelineGameDetailed>> generateTimeline(
			Hashtable<String, ArrayList<TimelineGameDetailed>> list) {
		/*
		 * get instance of calendar, and get dates from last 14 days
		 */
		Calendar c = Calendar.getInstance();
		LocalDate finish = getLocalDate(c);
		c.add(Calendar.DAY_OF_MONTH, -14);
		LocalDate start = getLocalDate(c);
		dates = getDatesBetween(start, finish);

		// initialize the Hashtable that will be returned later
		Hashtable<String, ArrayList<TimelineGameDetailed>> toReturn = new Hashtable<String, ArrayList<TimelineGameDetailed>>();
		ArrayList<TimelineGameDetailed> tmp;

		for (Entry<String, ArrayList<TimelineGameDetailed>> entry : list.entrySet()) {
			tmp = new ArrayList<TimelineGameDetailed>(14);
			// initializing the List so it will have a size of 14
			while (tmp.size() < 14) {
				tmp.add(new TimelineGameDetailed());
			}

			/*
			 * each date is getting compared with game date, and if they
			 * match, the game is added to the result table
			 */
			int i = 0;
			for (LocalDate ld : dates) {
				for (TimelineGameDetailed tlgd : entry.getValue()) {
					if (str2Date(tlgd.getTime()).equals(ld)) {
						tmp.set(i, tlgd);
						break;
					}
				}
				i++;
			}
			
			// adding temporal list to the result table, with game title as key
			toReturn.put(entry.getKey(), tmp);
		}

		return toReturn;
	}

	/**
	 * Sqldate in a string to LocalDate object
	 * 
	 * @param str String
	 * @return LocalDate
	 */
	private LocalDate str2Date(String str) {
		String[] split = str.split("-");
		return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	}

	/**
	 * Returns a date between 2 LocalDate's objects
	 * 
	 * @param start LocalDate
	 * @param finish LocalDate
	 * @return List<LocalDate>
	 */
	private List<LocalDate> getDatesBetween(LocalDate start, LocalDate finish) {
		return start.datesUntil(finish).collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of LocalDate's object
	 * 
	 * @return List<LocalDate>
	 */
	public List<LocalDate> getDates() {
		return dates;
	}
	
	private LocalDate getLocalDate(Calendar c) {
		int dayNum = c.get(Calendar.DAY_OF_MONTH);
		int monthNum = c.get(Calendar.MONTH);
		
		if (c.getMaximum(Calendar.DAY_OF_MONTH) == dayNum) {
			dayNum = 1;
			monthNum ++;
		} else {
			dayNum ++;
		}
		
		monthNum ++;
		if (monthNum == 13) {
			monthNum = 1;
		}
		
		return LocalDate.of(c.get(Calendar.YEAR), monthNum, dayNum);
	}
}
