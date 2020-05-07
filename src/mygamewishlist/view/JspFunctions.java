package mygamewishlist.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bomboshtml.body.A;
import bomboshtml.body.Img;
import bomboshtml.body.Input;
import bomboshtml.body.table.Tr;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;

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
		return session != null && session.getAttribute("user") != null;
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
		
		if (error == null) {
			error = "";
		}
		
		return new StringBuilder()
				.append("<p id='error' class='align-self-center'>")
				.append(error)
				.append("</p>")
				.toString();
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
	
	public String ifNullEmpty(String chk) {
		return chk == null ? "" : chk;
	}
	
	public ArrayList<Color> generateColors(int noColors) {
		ArrayList<Color> colors = new ArrayList<Color>();
		Random r = new Random();
		for (int i = 0; i < noColors; i++) {
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			colors.add(c);
		}
		return colors;
	}
	
	public String getPriceBgColor(double num) {
		if (num == -1) {
			return "bg-dark";
		} else if (num < 4) {
			return "bg-danger";
		} else if (num < 7) {
			return "bg-warning";
		} else {
			return "bg-success";
		}
	}
	
	public String buildImg(WishListGame g) {
		return new Img(g.getImg(), g.getGameName()).print();
	}
	
	public String buildTitle(WishListGame g, Store st) {
		return new StringBuilder()
				.append("<a target='_blank' href='")
				.append(g.getUrlStore())
				.append(g.getUrlGame())
				.append("'>")
				.append(g.getGameName())
				.append(" (")
				.append(st.getName())
				.append(")")
				.append("</a>")
				.toString();
	}
	
	public String buildDiscount(WishListGame g) {
		return (Math.round(g.getDiscount() * 100f) / 100f) + "%";
	}
	
	public String buildPrices(String discount, WishListGame g) {
		return discount.equals("0.0%") ? g.getCurrentPrice() + "€"
				: new StringBuilder()
					.append("<s>")
					.append(g.getDefaultPrice())
					.append("€</s><span class='h5'> ")
					.append(g.getCurrentPrice())
					.append("€</span>").toString();
	}
	
	public String buildMinPrice(WishListGame g) {
		return g.getMinPrice() == -1 ? "-"
				: new StringBuilder()
				.append("<=")
				.append(g.getMinPrice() + "€")
				.toString();
	}

	public String buildMaxPrice(WishListGame g) {
		return g.getMaxPrice() == -1 ? "-"
				: new StringBuilder()
				.append(">=")
				.append(g.getMaxPrice() + "€")
				.toString();
	}
	
	public String buildEdit(WishListGame g, Img edit, String targetClass) {
		return new A(edit.print(), 
				new StringBuilder()
				.append(targetClass)
				.append("?url=")
				.append(g.getUrlGame()
						.replace("?", "%3f"))
				.toString()).print();
	}
	
	public String buildDelete(WishListGame g, Img del, String targetClass) {
		return new A(del.print(), 
				new StringBuilder()
				.append(targetClass)
				.append("?url=")
				.append(g.getUrlGame()
						.replace("?", "%3f"))
				.toString()).print();
	}
}
