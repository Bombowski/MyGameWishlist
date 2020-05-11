package mygamewishlist.model.pojo;

import javax.ejb.EJB;

import mygamewishlist.model.ejb.CreateQueryEJB;

/**
 * @author Patryk
 *
 * Singleton class that contains all of the variables from the database
 */
public class SecretClass {

	private static SecretClass sc;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	public String mailPasswd;
	public String steamToken;
	
	private SecretClass() {
		if (cq_ejb == null) {
			cq_ejb = new CreateQueryEJB();
		}
		mailPasswd = cq_ejb.getVariable("emailPasswd");
		steamToken = cq_ejb.getVariable("steamToken");
	}
	
	public static SecretClass getSC() {
		if (sc == null) {
			synchronized (SecretClass.class) {
				if (sc == null) {
					sc = new SecretClass();
				}
			}
		}
		return sc;
	}
}
