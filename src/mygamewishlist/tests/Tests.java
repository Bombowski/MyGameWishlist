package mygamewishlist.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ejb.EJB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.pojo.db.Game;

class Tests {

	@EJB
	private static CreateQueryEJB cq_ejb;
	
	@BeforeAll
	public static void init() {
		cq_ejb = new CreateQueryEJB();
	}
	
	@Test
	void getUser() {
		Game g = cq_ejb.getGameById(1);
		boolean correct = false;
		
		if (g.getId() == 1 && g.getName().equals("The Witcher 3")) {
			correct = true;
		}		
		
		assertEquals(correct, true);		
	}
}
