package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.ejb.ScrapingEJB;

public class TestSteamLoad {

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
			scr_ejb.loadGames();
		} catch (JSONException e) {
			e.printStackTrace();
			assertEquals(true, false);
			return;
		}
		assertEquals(true, true);
	}
}
