package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import javax.ejb.EJB;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.ejb.ScrapingEJB;

public class ScrapingTesting {

	@EJB
	private static ScrapingEJB scr_ejb;
	
	@BeforeClass
	public static void init() {
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
}
