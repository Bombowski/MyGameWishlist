package mygamewishlist.view;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bomboshtml.body.A;
import bomboshtml.body.Img;
import bomboshtml.body.Input;
import bomboshtml.body.table.Tr;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.User;

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
	public User getLoggedUser(HttpSession session) {
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
	
	public String buildScrapedGameTable(ArrayList<ScrapedGame> games) {
		StringBuilder toReturn = new StringBuilder();
		int i = 0;
		
		toReturn.append("<table class='table'>");
		
		Tr th = new Tr();
		th.addTd("");
		th.addTd("Name");
		th.addTd("Default price");
		th.addTd("Current price");
		th.addTd("Currect Discount");
		th.addTd("Check games you want to add");
		toReturn.append(th.print());
		
		for (ScrapedGame sg : games) {
			Tr tr = new Tr();
			tr.addTd(new Img(sg.getImg(), sg.getFullName()));
			tr.addTd(new A(sg.getFullName().replace("'", "&#39;"), sg.getUrl()));
			tr.addTd(getStringPrice(sg.getDefaultPrice()));
			tr.addTd(getStringPrice(sg.getCurrentPrice()));
			tr.addTd(sg.getCurrentDiscount() + "%");
			tr.addTd(new Input("checkbox", "games", i + ""));
			
			toReturn.append(tr.print());
			i++;
		}
		
		return toReturn.append("</table>").toString();
	}
	
	private String getStringPrice(double price) {
		if (price == 0) {
			return "Free to play";
		} else if (price == -1) {
			return "Free demo";
		} else {
			return price + "€";
		}
	}
}
