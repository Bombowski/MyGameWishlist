package mygamewishlist.tests;

import javax.ejb.EJB;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import mygamewishlist.model.ejb.CreateQueryEJB;

@RunWith(Suite.class)
@SuiteClasses({ReviewTests.class
	})
//GameTests.class, GenreTests.class, ReviewTests.class, SteamTests.class, 
//StoreTests.class, TimelineTests.class, UserTests.class, VariableTests.class, WishlistGameTests.class
public class Tests {
	
	@EJB
	protected static CreateQueryEJB cq_ejb;
	
	@BeforeClass
	public static void init() {
		cq_ejb = new CreateQueryEJB();
	}
}
