package mygamewishlist.view;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import mygamewishlist.model.ejb.SessionClientEJB;
import mygamewishlist.model.pojo.User;

/**
 * @author Patryk
 *
 * Clase con funciones utilizadas por los archivos jsp
 */
public class JspFunctions {

	private static JspFunctions jsp = new JspFunctions();
	
	@EJB
	SessionClientEJB SCEJB;
	
	private JspFunctions() {}
	
	public static JspFunctions getJspF() {
		if (jsp == null) {
			synchronized (JspFunctions.class) {
				if (jsp == null) {
					jsp = new JspFunctions();
				}
			}
		}
		return jsp;
	}
	
	/**
	 * Consigue el objeto Usuario de la session si hay alguna session abierta 
	 * 
	 * @param request HttpServletRequest
	 * @return User, null si no existe una session
	 */
	public User getLoggedUser(HttpServletRequest request) {
		return getLoggedUser(request);
	}
	
	/**
	 * Comprueba si hay un error, y si lo hay, lo devuelve
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public String getError(HttpServletRequest request) {
		String error = (String)request.getAttribute("error");
		StringBuilder sb = new StringBuilder();
		sb.append("<p id='error' class='align-self-center'>");
		
		if (error == null) {
			error = "";
		}
		
		sb.append(error)
			.append("</p>");
		
		return sb.toString();
	}
}
