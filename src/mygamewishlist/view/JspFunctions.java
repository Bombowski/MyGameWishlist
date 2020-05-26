package mygamewishlist.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bomboshtml.body.A;
import bomboshtml.body.Img;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * @author Patryk
 *
 * Singleton class that contains functions used by jsp's.
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
	 * Checks if there is a logged user.
	 * 
	 * @param session HttpSession
	 * @return boolean
	 */
	public boolean isSome1Logged(HttpSession session) {
		return session != null && session.getAttribute("user") != null;
	}
	
	/**
	 * Returns the logged user and his data, if there is one.
	 * 
	 * @param session HttpSession
	 * @return User
	 */
	public User getLoggedUser(HttpSession session) {
		if (isSome1Logged(session)) {
			return (User) session.getAttribute("user");
		}
		return null;
	}
	
	/**
	 * Closes user's session.
	 * 
	 * @param session HttpSession
	 */
	public void logoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
	
	/**
	 * Opens a session for the user.
	 * 
	 * @param session HttpSession
	 * @param usr User, object that is added to the session
	 */
	public void loginUser(HttpSession session, User usr) {
		session.setAttribute("user", usr);
	}
	
	/**
	 * Returns an error if there is one.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public String getError(HttpServletRequest request) {
		String error = (String)request.getAttribute("error");
		
		if (error == null) {
			error = "";
		}
		
		return new StringBuilder()
				.append("<p class='error align-self-center'>")
				.append(error)
				.append("</p>")
				.toString();
	}
	
	/**
	 * If provided string is a null, an empty string is returned,
	 * else the entry string is returned.
	 * 
	 * @param chk String
	 * @return String
	 */
	public String ifNullEmpty(String chk) {
		return chk == null ? "" : chk;
	}
	
	/**
	 * Generates a list of random rgb colors.
	 * 
	 * @param noColors number of colors
	 * @return ArrayList<Color>
	 */
	public ArrayList<Color> generateColors(int noColors) {
		ArrayList<Color> colors = new ArrayList<Color>();
		Random r = new Random();
		for (int i = 0; i < noColors; i++) {
			Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			colors.add(c);
		}
		return colors;
	}
	
	/**
	 * Based on the integer provided, a different bootstrap-4 css rule
	 * will be returned.
	 * 
	 * @param num double
	 * @return String
	 */
	public String getPriceBgColor(double num) {
		if (num < 0) {
			return "bg-dark";
		} else if (num < 4) {
			return "bg-danger";
		} else if (num < 7) {
			return "bg-warning";
		} else {
			return "bg-success";
		}
	}
	
	/**
	 * Builds an image using data stored in the WishlistGame object.
	 * 
	 * @param g WishlistGame
	 * @return String
	 */
	public String buildImg(WishListGame g) {
		return new Img(g.getImg(), g.getGameName()).print();
	}
	
	/**
	 * Builds title of a game from user's wishlist
	 * 
	 * @param g WishListGame
	 * @param st Store
	 * @return String
	 */
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
	
	/**
	 * Builds discount of a game from user's wishlist
	 * 
	 * @param g WishlistGame
	 * @return String
	 */
	public String buildDiscount(WishListGame g) {
		return (Math.round(g.getDiscount() * 100f) / 100f) + "%";
	}
	
	/**
	 * Builds prices of a game from user's wishlist
	 * 
	 * @param discount String, double value with a % at the end
	 * @param g WishListGame
	 * @return String
	 */
	public String buildPrices(String discount, WishListGame g) {
		return discount.equals("0.0%") ? g.getCurrentPrice() + "€"
				: new StringBuilder()
					.append("<p><s>")
					.append(g.getDefaultPrice())
					.append("€</s></p><span class='h5 bg-success p-1'> ")
					.append(g.getCurrentPrice())
					.append("€</span>")
					.toString();
	}
	
	/**
	 * Builds minimal price of a game from user's wishlist
	 * 
	 * @param g WishListGame
	 * @return String
	 */
	public String buildMinPrice(WishListGame g) {
		return g.getMinPrice() == -1 ? "-"
				: new StringBuilder()
				.append("<=")
				.append(g.getMinPrice() + "€")
				.toString();
	}

	/**
	 * Build maximal price of a game from user's wishlist
	 * 
	 * @param g WishlistGame
	 * @return String
	 */
	public String buildMaxPrice(WishListGame g) {
		return g.getMaxPrice() == -1 ? "-"
				: new StringBuilder()
				.append(">=")
				.append(g.getMaxPrice() + "€")
				.toString();
	}
	
	/**
	 * Builds edit button of a game from user's wishlist
	 * 
	 * @param g WishlistGame
	 * @param edit Img, image that the button will contain
	 * @param targetClass String, target of the button
	 * @return String
	 */
	public String buildEdit(WishListGame g, Img edit, String targetClass) {
		return new A(edit.print(), 
				new StringBuilder()
				.append(targetClass)
				.append("?url=")
				.append(g.getUrlGame()
						.replace("?", "%3f"))
				.toString()).print();
	}
	
	/**
	 * Builds delete button of a game from user's wishlist
	 * 
	 * @param g WishlistGame
	 * @param del Img, image that the button will contain
	 * @param targetClass String, target of the button
	 * @return String
	 */
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
