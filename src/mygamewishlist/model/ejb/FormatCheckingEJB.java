package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.Genre;

@LocalBean
@Stateless
public class FormatCheckingEJB {

	@EJB
	CreateQueryEJB cq_ejb;
	
	public FormatCheckingEJB() {}
	
	public String chkGame(String name, String description, String[] genreIds, String rDate, String idDeveloper) {
		if (name == null || description == null || genreIds == null || rDate == null || idDeveloper == null) {
			return "Unvalid value";
		}
		
		int[] gIds = new int[genreIds.length];
		try {
			for (int i = 0; i < genreIds.length; i++) {
				gIds[i] = Integer.parseInt(genreIds[i]);
			}
		} catch (NumberFormatException nfe) {
			return "Unvalid genre id";
		}
		
		if (!Pattern.matches("^(\\d{4}-(([1-9][0-2]?))-(([1-9])|(1[0-9]?)|(2[0-9]?)|(3[0-1]?)))$", rDate)) {
			return "Unvalid date";
		}
		
		for (Game g : cq_ejb.getGames()) {
			if (g.getName().equals(name)) {
				return "A game with this name already exists";
			}
		}
		
		ArrayList<Genre> genres = cq_ejb.getGenres();
		
		for (Genre g : genres) {
			boolean contains = false;
			for (int id : gIds) {
				if (g.getId() == id) {
					contains = true;
					break;
				}
			}
			
			if (contains) {
				return "Unvalid genre id";
			}
		}
		
		return "";
	}
}
