package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.SteamGame;

public class SteamTests {

	private static SteamGame sg;
	
	@BeforeClass
	public static void init() {
		sg = new SteamGame();
		sg.setAppid(-1);
		sg.setName("testGame");
	}
	
	@Test
	public void addGame() {
		ArrayList<SteamGame> arrSg = new ArrayList<SteamGame>();
		arrSg.add(sg);
		
		Tests.cq_ejb.addSteamGames(arrSg);
		assertEquals(true, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).size() > 0);
	}
	
	@Test
	public void getSteamGameIdsByName() {
		assertEquals(true, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).size() > 0);
	}
	
	@Test
	public void deleteSteamGameById() {
		Tests.cq_ejb.deleteSteamGameById(sg.getAppid());
		assertEquals(false, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).contains(sg.getAppid()));
	}
}
