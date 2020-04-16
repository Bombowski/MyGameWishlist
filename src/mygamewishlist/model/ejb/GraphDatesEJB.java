package mygamewishlist.model.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.pojo.db.TimelineGameDetailed;

@Stateless
@LocalBean
public class GraphDatesEJB {

	private List<LocalDate> dates;
	
	public GraphDatesEJB() {
	}

	public Hashtable<String, ArrayList<TimelineGameDetailed>> generateTimeline(
			Hashtable<String, ArrayList<TimelineGameDetailed>> list) {
		Calendar c = Calendar.getInstance();
		LocalDate finish = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
		c.add(Calendar.DAY_OF_MONTH, -14);
		LocalDate start = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
		dates = getDatesBetween(start, finish);

		Hashtable<String, ArrayList<TimelineGameDetailed>> toReturn = new Hashtable<String, ArrayList<TimelineGameDetailed>>();
		ArrayList<TimelineGameDetailed> tmp;

		for (String key : list.keySet()) {
			ArrayList<TimelineGameDetailed> arr = list.get(key);
			tmp = new ArrayList<TimelineGameDetailed>(14);
			while (tmp.size() < 14) {
				tmp.add(new TimelineGameDetailed());
			}

			for (LocalDate ld : dates) {
				int i = 0;
				for (TimelineGameDetailed tlgd : arr) {
					if (str2Date(tlgd.getTime()).equals(ld)) {
						tmp.set(i, tlgd);
					}
					i++;
				}
			}
			
			toReturn.put(key, tmp);
		}

		return toReturn;
	}

	private LocalDate str2Date(String str) {
		str = str.substring(0, str.indexOf(" "));
		String[] split = str.split("-");
		LocalDate ld = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

		return ld;
	}

	private List<LocalDate> getDatesBetween(LocalDate start, LocalDate finish) {
		return start.datesUntil(finish).collect(Collectors.toList());
	}
	
	public List<LocalDate> getDates() {
		return dates;
	}
}
