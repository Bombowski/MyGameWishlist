package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VariableTests {

	@Test
	public void getVariableByName() {
		assertEquals(true, Tests.cq_ejb.getVariable("emailPasswd") != null);
	}
}
