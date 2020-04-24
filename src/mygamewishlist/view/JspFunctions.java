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
 * 
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
	
	public boolean isSome1Logged(HttpSession session) {
		if (session != null && session.getAttribute("user") != null) {
			return true;
		}
		return false;
	}
	
	public User getLoggedUser(HttpSession session) {
		if (isSome1Logged(session)) {
			return (User) session.getAttribute("user");
		}
		return null;
	}
	
	public void logoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
	
	public void loginUser(HttpSession session, User usr) {
		session.setAttribute("user", usr);
	}
	
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
				
		for (ScrapedGame sg : games) {
			Tr tr = new Tr();
			tr.addTd(new Img(sg.getImg(), sg.getFullName()));
			tr.addTd(new A(sg.getFullName().replace("'", "&#39;"), sg.getUrlStore() + sg.getUrlGame()));
			tr.addTd(Math.round(sg.getDefaultPrice() * 100f) / 100f + "€");
			tr.addTd(Math.round(sg.getCurrentPrice() * 100f) / 100f + "€");
			tr.addTd(sg.getCurrentDiscount() + "%");
			tr.addTd(new Input("checkbox", "games", sg.getStoreName() + "&" +  i));
			tr.addTd(new StringBuilder()
						.append(">=<input type='number' name='")
						.append(sg.getStoreName())
						.append("&min")
						.append(i + "' step='0.01' min='-1'>")
						.toString());
			tr.addTd(new StringBuilder()
						.append(">=<input type='number' name='")
						.append(sg.getStoreName())
						.append("&max")
						.append(i + "' step='0.01' min='-1'>")
						.toString());
			
			toReturn.append(tr.print());
			i++;
		}
		
		return toReturn.toString();
	}
}
