package mygamewishlist.model.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 * 
 * EJB Class that contains functions for better session management
 */
@Stateless
@LocalBean
public class ClientSessionEJB {

	public ClientSessionEJB() {}
	
	/**
	 * Checks if there is an open session
	 * 
	 * @param session HttpSession
	 * @return true if there is, false if not
	 */
	public boolean isSome1Logged(HttpSession session) {
		return session != null && session.getAttribute("user") != null;
	}
	
	/**
	 * Gets currently logged user.
	 * 
	 * @param session HttpSession
	 * @return User if there is a session, null if not
	 */
	public User getLoggedUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (isSome1Logged(session)) {
			return (User) session.getAttribute("user");
		}
		return null;
	}
	
	/**
	 * Closes users session
	 * 
	 * @param session HttpSession
	 */
	public void logoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
	
	/**
	 * Creates a session for user
	 * 
	 * @param session HttpSession
	 * @param usr User
	 */
	public void loginUser(HttpSession session, User usr) {
		session.setAttribute("user", usr);
	}
}
