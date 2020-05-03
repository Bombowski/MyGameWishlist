package mygamewishlist.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import mygamewishlist.model.pojo.db.User;

public class UserTests {
	
	private static User usrOld;
	
	@BeforeClass
	public static void init() {
		usrOld = new User();
		usrOld.setAdmin(1);
		usrOld.setEmail("patryk080998@gmail.com");
		usrOld.setName("Patryk");
	}
	
	@Test
	public void getUserByEmail() {
		assertEquals(usrOld.getEmail(), Tests.cq_ejb.getUserByEmail(usrOld.getEmail()));
	}
	
	@Test
	public void addUser() {
		User usr = new User();
		usr.setEmail("emailtest@gmail.com");
		usr.setName("testname");
		usr.setAdmin(0);
		
		Tests.cq_ejb.addUser(usr);
		User usr2 = Tests.cq_ejb.getUserByEmail(usr.getEmail());
				
		assertEquals(usr, usr2);
	}
	
	@Test
	public void getUserWithList() {
		assertEquals(true, Tests.cq_ejb.getUsersWithList().size() > 0);
	}
}