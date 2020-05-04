package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import mygamewishlist.model.pojo.db.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTests {
	
	private static User usrOld;
	
	@BeforeClass
	public static void init() {
		usrOld = new User();
		usrOld.setAdmin(0);
		usrOld.setEmail("emailtest@gmail.com");
		usrOld.setName("testname");
	}
	
	@Test
	public void addUser() {		
		Tests.cq_ejb.addUser(usrOld);				
		assertEquals(usrOld, Tests.cq_ejb.getUserByEmail(usrOld.getEmail()));
	}
	
	@Test
	public void getUserByEmail() {
		assertEquals(usrOld, Tests.cq_ejb.getUserByEmail(usrOld.getEmail()));
	}
	
	@Test
	public void getUserWithList() {
		assertEquals(true, Tests.cq_ejb.getUsersWithList().size() > 0);
	}
	
	@Test
	public void zDeleteUser() {
		Tests.cq_ejb.deleteUser(usrOld.getEmail());
		assertEquals(null, Tests.cq_ejb.getUserByEmail(usrOld.getEmail()));
	}
}