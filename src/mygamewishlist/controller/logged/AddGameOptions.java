package mygamewishlist.controller.logged;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mygamewishlist.model.ejb.ClientSessionEJB;
import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;
import mygamewishlist.model.pojo.db.WishListGameSteam;

/**
 * @author Patryk
 *
 * Servlet used for showing a list of results of games, and for
 * adding games to the wishlist.
 */
@WebServlet("/AddGameOptions")
public class AddGameOptions extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	private Hashtable<String,ArrayList<ScrapedGame>> games = new Hashtable<String,ArrayList<ScrapedGame>>();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	/**
	 * Shows a list of results from the search of AddGameWishlist.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {			
			RequestDispatcher rd;
			
			if (!sc_ejb.isSome1Logged(request.getSession(false))) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
				rd.forward(request, response);
				return;
			}
			
			/**
			 *  if games isn't empty a list of stores and games is
			 *  forwarded to the jsp
			 */			
			if (!games.isEmpty()) {
				rd = getServletContext().getRequestDispatcher(cp.JSP_ADD_GAME_OPTIONS);
				request.setAttribute("stores", cq_ejb.getStoreNames());
				request.setAttribute("games", games);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
				request.setAttribute("error", "No games found with such parameters.");
			}
			
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			rd.forward(request, response);
		}
	}

	/**
	 * Either revieves a list of games, and then forward to post, or 
	 * recieves and adds all of the picked games to the wishlist.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			if (usr == null) {
				response.sendRedirect(cp.REDIRECT_LOGIN);
				return;
			}

			// ids of all the picked games
			String[] id = request.getParameterValues("games");
			
			// in each case the games list is cleared.
			if (id != null) {
				// if i recieved a list of picked games
				cq_ejb.addGame2Wishlist(addGames(id, request), usr.getId());
				games.clear();
				response.sendRedirect(cp.REDIRECT_MYLIST);
			} else {
				// if i recieved a list of games
				games.clear();
				ArrayList<String> stNames = cq_ejb.getStoreNames();
				
				// for each store name im adding a list of games of each store to the global list
				for (String str : stNames) {
					@SuppressWarnings("unchecked")
					Hashtable<String,ArrayList<ScrapedGame>> tmp = (Hashtable<String,ArrayList<ScrapedGame>>)request.getAttribute(str);
					if (tmp != null) {
						if (tmp.isEmpty()) {
							games.put(str, new ArrayList<ScrapedGame>());
						} else {
							games.putAll(tmp);
						}						
					}
					
					request.removeAttribute(str);
				}
				
				doGet(request, response);
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
	
	/**
	 * Reads all of the recieved data and adds games to the wishlist.
	 * 
	 * @param id identificators of each game, they must contain a position of the game in the
	 * ArrayList and the name of their store.
	 * @param request HttpServletRequest
	 * @return ArrayList<WishlistGame>
	 */
	private ArrayList<WishListGame> addGames(String[] id, HttpServletRequest request) {
		ArrayList<WishListGame> toInsert = new ArrayList<WishListGame>();
		
		// for each id a game is added
		for (String s : id) {
			// position in the ArrayList
			int arrPos = Integer.parseInt(s.substring(s.lastIndexOf("&") + 1));
			// store name
			String store = s.substring(0,s.lastIndexOf("&"));			
			double max = -1;
			double min = -1;
			
			// setting the alert settings
			try {
				String minS = request.getParameter(store + "&min" + arrPos);
				String maxS = request.getParameter(store + "&max" + arrPos);
				
				if (!minS.equals("")) {
					min = Double.parseDouble(request.getParameter(store + "&min" + arrPos));
				}
				if (!maxS.equals("")) {
					max = Double.parseDouble(request.getParameter(store + "&max" + arrPos));
				}
			} catch (NumberFormatException e) {
				LOG.logError(e.getMessage());
			}		
			
			// List of games by name of the store
			ArrayList<ScrapedGame> stGames = games.get(store);
			
			if (stGames == null) {
				continue;
			}
			
			// game from ArrayList by position
			ScrapedGame g = stGames.get(arrPos);
			
			WishListGame wlg;
			/*
			 *  if its a steam game a special object for steam games that can also
			 *  contain an appid is created, else a normal WishListGame object is created
			 */			
			if (!store.equals("Steam")) {
				wlg = new WishListGame();
				wlg.setUrlGame(g.getUrlGame());
			} else {
				wlg = new WishListGameSteam();
				((WishListGameSteam) wlg).setAppid(Integer.parseInt(g.getUrlGame().substring(g.getUrlGame().lastIndexOf("/") + 1)));
				wlg.setUrlGame(g.getUrlGame());
			}
			
			// All of the scraped values are being set
			wlg.setIdStore(cq_ejb.getStoreByName(store).getId());
			wlg.setGameName(g.getFullName());
			wlg.setDefaultPrice(g.getDefaultPrice());
			wlg.setCurrentPrice(g.getCurrentPrice());
			wlg.setDiscount(g.getCurrentDiscount());
			wlg.setImg(g.getImg());
			wlg.setMinPrice(min); 
			wlg.setMaxPrice(max);
			
			// game is added to the list of games to insert
			toInsert.add(wlg);
		}
		
		return toInsert;
	}
}
