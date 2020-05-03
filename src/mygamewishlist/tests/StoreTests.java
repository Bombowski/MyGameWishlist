package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StoreTests {

	@Test
	public void getStores() {
		assertEquals(true, Tests.cq_ejb.getStores().size() > 0);
	}

	@Test
	public void getStoreNames() {
		assertEquals(true, Tests.cq_ejb.getStoreNames().size() > 0);		
	}

	@Test
	public void getStoreByName() {
		assertEquals(true, Tests.cq_ejb.getStoreByName("Steam").getId() != 0);
	}	
}
