package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.ejb.ScrapingEJB;
import mygamewishlist.model.pojo.Pagination;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGame2Scrap;

public class ScrapingTesting {

	private static CreateQueryEJB cq_ejb;
	private static ScrapingEJB scr_ejb;
	
	@BeforeClass
	public static void init() {
		cq_ejb = new CreateQueryEJB();
		scr_ejb = new ScrapingEJB();
	}
	
	public void loadSteam() {
		try {
			assertEquals(true, scr_ejb.loadGames().size() > 0);
		} catch (JSONException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}
	
	@Test
	public void testLowerPrice() {
		assertEquals(1, interpretPriceCodeNew());

		/*
		 * code 0 = no changes - don't update price nor add it to notification list
		 * code 1 = different price - update price and send notification to suer
		 * code 2 = different price but not beetween min and max - update price 
		 */
	}	
	
	private int interpretPriceCodeNew() {		
		WishListGame2Scrap wlg = new WishListGame2Scrap();
		wlg.setCurrentPrice(20);
		wlg.setDefaultPrice(30);
		wlg.setMinPrice(5);
		wlg.setMaxPrice(30);
		
		ScrapedGame sg = new ScrapedGame();
		sg.setCurrentPrice(30);
		sg.setDefaultPrice(30);
		sg.setCurrentDiscount(50);
		
		return lowerPrice(wlg, sg);
	}
	
	private int lowerPrice(WishListGame2Scrap wlg, ScrapedGame sg) {
		/*
		 * save object parameters in local variables so they will
		 * be easier to manage
		 */
		double newCurrent = sg.getCurrentPrice();
		double oldCurrent = wlg.getCurrentPrice();
		double min = wlg.getMinPrice();
		double max = wlg.getMaxPrice();
		
		/*
		 * check if current price is lower, then if min price is set,
		 * then if current price is lower or equal to min price.
		 */
		if (newCurrent < oldCurrent) {
			if (min != -1) {
				if (min >= newCurrent) {				
					return 1;
				}
				return 2;
			}
			return 1;
		/*
		 * check if current price is higher, then if max price is set,
		 * then if current price is higher or equal to max price.
		 */
		} else if (newCurrent > oldCurrent) {
			if (max != -1) {
				if (max <= newCurrent) {
					return 1;
				}			
				return 2;
			}
			return 1;
		} 
		
		// check if default prices are different
		if (wlg.getDefaultPrice() != sg.getDefaultPrice()) {
			return 2;
		}
		
		return 0;
	}
}
