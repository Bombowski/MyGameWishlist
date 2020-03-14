package mygamewishlist.view;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mygamewishlist.model.pojo.User;

/**
 * @author Patryk
 *
 * Clase con funciones utilizadas por los archivos jsp
 */
public class JspFunctions {

	private static JspFunctions jsp = new JspFunctions();
	
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
	 * Comprueba si existe alguna session abierta
	 * 
	 * @param request HttpServletRequest
	 * @return true si exifte, false si no
	 */
	private boolean isSome1Logged(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("user") != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Consigue el objeto Usuario de la session si hay alguna session abierta 
	 * 
	 * @param request HttpServletRequest
	 * @return User, null si no existe una session
	 */
	public User getLoggedUser(HttpServletRequest request) {
		if (isSome1Logged(request)) {
			return (User) request.getSession().getAttribute("user");
		}
		return null;
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
	
	/**
	 * Envuelve cada String del arraylist en los tags option de html
	 * 
	 * @param arr ArrayList<String>
	 * @return String
	 */
	public String printOptions(ArrayList<String> arr) {
		StringBuilder sb = new StringBuilder();
		for (String str : arr) {
			sb.append("<option>")
				.append(str)
				.append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * Envuelve cada String del arraylist en los tags option de html,
	 * y si algun string coincide con selected, le añado al tag
	 * el atributo selected
	 * 
	 * @param arr ArrayList<String>
	 * @return String
	 */
	public String printOptions(ArrayList<String> arr, String selected) {
		StringBuilder sb = new StringBuilder();
		for (String str : arr) {
			if (str.equals(selected)) {
				sb.append("<option selected>")
					.append(str)
					.append("</option>");
			} else {
				sb.append("<option>")
					.append(str)
					.append("</option>");
			}
		}
		
		return sb.toString();
	}
}
