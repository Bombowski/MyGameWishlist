package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import mygamewishlist.model.pojo.SteamGame;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SteamTests {

	private static SteamGame sg;
	
	@BeforeClass
	public static void init() {
		sg = new SteamGame();
		sg.setAppid(-1);
		sg.setName("testGame");
	}
	
	@AfterClass
	public static void destroy() {
		Tests.cq_ejb.deleteSteamGameById(sg.getAppid());
	}
	
	@Test
	public void addGame() {
		ArrayList<SteamGame> arrSg = new ArrayList<SteamGame>();
		arrSg.add(sg);
		
		Tests.cq_ejb.addSteamGames(arrSg);
		assertEquals(true, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).size() > 0);
	}
	
	@Test
	public void bGetSteamGameIdsByName() {
		assertEquals(true, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).size() > 0);
	}
	
	@Test
	public void deleteSteamGameById() {
		Tests.cq_ejb.deleteSteamGameById(sg.getAppid());
		assertEquals(false, Tests.cq_ejb.getSteamGameIdsByName(sg.getName()).contains(sg.getAppid()));
	}
}
