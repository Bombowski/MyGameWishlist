package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.db.Genre;

public class GenreTests {

	private static Genre gen;
	
	@BeforeClass
	public static void init() {
		gen = new Genre();
		gen.setId(1);
		gen.setName("Platform");
	}
	
	@Test
	public void getGenres() {
		assertEquals(true, Tests.cq_ejb.getGenres().size() > 0);
	}

	@Test
	public void getGenreById() {
		assertEquals(gen, Tests.cq_ejb.getGenreById(gen.getId()));
	}
}
