package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import mygamewishlist.model.pojo.db.GameFull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTests {

	private static GameFull gf;
	private static GameFull dataBaseResult;
	
	@BeforeClass
	public static void init() {
		gf = new GameFull();
		gf.setDescription("desc");
		gf.setDeveloper("CD Project Red");
		gf.setGenres("Platform,Shooter");
		gf.setIdDeveloper(1);
		gf.setIdGenres("1,2");
		gf.setName("game1");
		gf.setReleaseDate("2020-08-05");
		
		dataBaseResult = new GameFull(gf);
		dataBaseResult.setIdDeveloper(0);
		dataBaseResult.setIdGenres("1, 2");
	}
	
	@AfterClass
	public static void destroy() {
		Tests.cq_ejb.deleteGame(gf.getId());
	}
	
	@Test
	public void addGame() {
		Tests.cq_ejb.addGame(gf);
		gf.setId(Tests.cq_ejb.getGameIdByTitle(gf.getName()));
		dataBaseResult.setId(gf.getId());
		assertEquals(dataBaseResult, Tests.cq_ejb.getGameById(gf.getId()));
	}	
	
	@Test
	public void getGameById() {
		assertEquals(dataBaseResult, Tests.cq_ejb.getGameById(gf.getId()));
	}

	@Test
	public void getGames() {
		assertEquals(true, Tests.cq_ejb.getGames().size() > 0);
	}

	@Test
	public void updateGame() {
		GameFull tmp = new GameFull(gf);
		tmp.setDescription("new description");
		tmp.setIdDeveloper(2);
		tmp.setReleaseDate("2020-08-20");
		tmp.setName("new name");
		tmp.setDeveloper("Valve");
		
		Tests.cq_ejb.updateGame(tmp);
		tmp.setIdGenres("1, 2");
		tmp.setIdDeveloper(0);
		GameFull tmp2 = Tests.cq_ejb.getGameById(tmp.getId());
		assertEquals(tmp, tmp2);
		Tests.cq_ejb.updateGame(gf);
	}

	@Test
	public void zDeleteGame() {
		Tests.cq_ejb.deleteGame(gf.getId());
		GameFull tmp = Tests.cq_ejb.getGameById(gf.getId());
		assertEquals(null, tmp);
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
