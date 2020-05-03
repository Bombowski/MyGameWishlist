package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.db.GameFull;

public class GameTests {

	private static GameFull gf;
	
	@BeforeClass
	public static void init() {
		gf = new GameFull();
		gf.setDescription("desc");
		gf.setDeveloper("dev");
		gf.setGenres("genres");
		gf.setIdDeveloper(1);
		gf.setIdGenres("1,2");
		gf.setName("game1");
		gf.setReleaseDate("");
	}

	@AfterClass
	public static void destroy() {
		Tests.cq_ejb.deleteGame(gf.getId());
	}
	
	@Test
	public void addGame() {
		Tests.cq_ejb.addGame(gf);
		gf.setId(Tests.cq_ejb.getGameIdByTitle(gf.getName()));
		assertEquals(gf, Tests.cq_ejb.getGameById(gf.getId()));
	}	
	
	@Test
	public void getGameById() {
		assertEquals(gf, Tests.cq_ejb.getGameById(gf.getId()));
	}

	@Test
	public void getGames() {
		assertEquals(true, Tests.cq_ejb.getGames().size() > 0);
	}

	@Test
	public void updateGame() {
		GameFull tmp = gf;
		tmp.setDescription("new description");
		tmp.setIdDeveloper(2);
		tmp.setReleaseDate("new realease");
		tmp.setName("new name");
		
		Tests.cq_ejb.updateGame(tmp);
		assertEquals(tmp, Tests.cq_ejb.getGameById(tmp.getId()));
		Tests.cq_ejb.updateGame(gf);
	}

	@Test
	public void deleteGame() {
		Tests.cq_ejb.deleteGame(gf.getId());
		assertEquals(true, Tests.cq_ejb.getGameById(gf.getId()) == null);
		Tests.cq_ejb.addGame(gf);
	}

	@Test
	public void getDeveloperById() {
		assertEquals("CD Project Red", Tests.cq_ejb.getDeveloperById(1).getName());
	}

	@Test
	public void getDevelopers() {
		assertEquals(true, Tests.cq_ejb.getDevelopers().size() > 0);		
	}
}
