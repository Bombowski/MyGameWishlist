package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.Genre;

/**
 * @author Patryk
 *
 * Class that is used for checking formats
 */
@LocalBean
@Stateless
public class FormattingEJB {

	@EJB
	CreateQueryEJB cq_ejb;
	
	public FormattingEJB() {}
	
	/**
	 * Checks format, length, if exists in database, etc.
	 * 
	 * @param name String
	 * @param description String
	 * @param genreIds String[]
	 * @param rDate String
	 * @param idDeveloper String
	 * @return String
	 */
	public String chkGame(String name, String description, String[] genreIds, String rDate, String idDeveloper) {
		// checking for null
		if (name == null || description == null || genreIds == null || rDate == null || idDeveloper == null) {
			return "Unvalid value";
		}
		
		// changing id's to int's
		int[] gIds = new int[genreIds.length];
		try {
			for (int i = 0; i < genreIds.length; i++) {
				gIds[i] = Integer.parseInt(genreIds[i]);
			}
		} catch (NumberFormatException nfe) {
			return "Unvalid genre id";
		}
		
		// checking date pattern
		if (!Pattern.matches("^((\\d{4})-(([1-9][0-2]?)|(0[1-9]))-((0[1-9])|(1[0-9]?)|(2[0-9]?)|(3[0-1]?)))$", rDate)) {
			return "Unvalid date";
		}
		
		// checking game name
		String error = chkGameNames(name);
		if (error.equals("")) {
			return error;
		}
		
		// checking game genres
		error = chkGenres(gIds);
		return error;
	}
	
	/**
	 * Checks if this name already exists in the database
	 * 
	 * @param name String
	 * @return String
	 */
	private String chkGameNames(String name) {
		for (Game g : cq_ejb.getGames()) {
			if (g.getName().equals(name)) {
				return "A game with this name already exists";
			}
		}
		
		return "";
	}
	
	/**
	 * Check if all of the provided id's exist in the database
	 * 
	 * @param gIds int[]
	 * @return String
	 */
	private String chkGenres(int[] gIds) {
		ArrayList<Genre> genres = cq_ejb.getGenres();
		
		for (int id : gIds) {
			boolean contains = false;
			for (Genre g : genres) {
				if (g.getId() == id) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				return "Unvalid genre id";
			}
		}
		
		return "";
	}
	
	/**
	 * Changes an array to string, each object is separated
	 * by a comma.
	 * 
	 * @param arr String[]
	 * @return String
	 */
	public String arrToString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (String str : arr) {
			sb.append(str)
				.append(",");
		}
		
		return sb.toString();
	}
}
