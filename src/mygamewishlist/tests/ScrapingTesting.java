package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.ejb.ScrapingEJB;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;

public class ScrapingTesting {

	private static CreateQueryEJB cq_ejb;
	private static ScrapingEJB scr_ejb;
	
	@BeforeClass
	public static void init() {
		cq_ejb = new CreateQueryEJB();
		scr_ejb = new ScrapingEJB();
	}
	
	@Test
	public void loadSteam() {
		try {
			assertEquals(true, scr_ejb.loadGames().size() > 0);
		} catch (JSONException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}
	
	public void testLowerPrice() {
		assertEquals(false, lowerPrice());
	}
	
	private boolean lowerPrice() {
		WishListGame wlg = new WishListGame();
		wlg.setCurrentPrice(20);
		wlg.setDefaultPrice(20);
		wlg.setMinPrice(5);
		wlg.setMaxPrice(30);
		
		ScrapedGame sg = new ScrapedGame();
		sg.setCurrentPrice(30);
		sg.setDefaultPrice(20);
		sg.setCurrentDiscount(50);
		
		double newCurrent = sg.getCurrentPrice();
		double oldCurrent = wlg.getCurrentPrice();
		double min = wlg.getMinPrice();
		double max = wlg.getMaxPrice();
		
		if (min != -1 && min >= newCurrent) {
			return true;
		} else if (max != -1 && max <= newCurrent) {
			return true;
		} else if (oldCurrent != newCurrent) {
			return true;
		} else if (wlg.getDefaultPrice() != sg.getDefaultPrice()) {
			return false;
		}
		
		return false;
	}
}
