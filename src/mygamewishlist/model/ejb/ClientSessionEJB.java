package mygamewishlist.model.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 * 
 * Clase que contiene funciones para manejar las sessiones de usuarios
 */
@Stateless
@LocalBean
public class ClientSessionEJB {

	public ClientSessionEJB() {}
	
	/**
	 * Comprueba si hay una session abierta
	 * 
	 * @param session HttpSession
	 * @return true si la hay, false si no
	 */
	public boolean isSome1Logged(HttpSession session) {
		if (session != null && session.getAttribute("user") != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Consigue el usuario que esta logeado actualmente.
	 * 
	 * @param session HttpSession
	 * @return User si existe una session, null si no
	 */
	public User getLoggedUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (isSome1Logged(session)) {
			return (User) session.getAttribute("user");
		}
		return null;
	}
	
	/**
	 * Cierra la session del usuario
	 * 
	 * @param session HttpSession
	 */
	public void logoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
	
	/**
	 * Crea una session de usuario
	 * 
	 * @param session HttpSession
	 * @param usr User
	 */
	public void loginUser(HttpSession session, User usr) {
		session.setAttribute("user", usr);
	}
}
