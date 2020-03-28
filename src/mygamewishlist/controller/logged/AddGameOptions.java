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
import mygamewishlist.model.ejb.CreateQuery;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * Servlet implementation class AddGameOptions
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
	CreateQuery cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher rd;
			
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String[] id = request.getParameterValues("games");
			
			if (id != null) {
				cq_ejb.addGame2Wishlist(addGames(id, request));
				games.clear();
				response.sendRedirect(cp.REDIRECT_MYLIST);
			} else {
				ArrayList<String> stNames = cq_ejb.getStoreNames();
				
				for (String str : stNames) {
					Hashtable<String,ArrayList<ScrapedGame>> tmp = (Hashtable<String,ArrayList<ScrapedGame>>)request.getAttribute(str);
					if (tmp != null) {
						games.putAll(tmp);
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
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//games.clear();
	}
	
	private ArrayList<WishListGame> addGames(String[] id, HttpServletRequest request) {
		ArrayList<WishListGame> toInsert = new ArrayList<WishListGame>();
		ArrayList<Store> stores = cq_ejb.getStores();
		
		for (String s : id) {
			int arrPos = Integer.parseInt(s.substring(s.indexOf("&") + 1));
			String store = s.substring(0,s.indexOf("&"));
			
			ArrayList<ScrapedGame> stGames = games.get(store);
			
			if (stGames == null) {
				continue;
			}
			
			ScrapedGame g = stGames.get(arrPos);
			WishListGame wlg = new WishListGame();
			
			wlg.setUrlGame(fixUrl(g.getUrl(), stores, g.getStoreName()));
			wlg.setIdList(cq_ejb.getIdListByIdUser(sc_ejb.getLoggedUser(request).getId()));
			wlg.setIdStore(cq_ejb.getStoreByName(store).getId());
			wlg.setName(g.getFullName());
			wlg.setDefaultPrice(g.getDefaultPrice());
			wlg.setCurrentPrice(g.getCurrentPrice());
			wlg.setDiscount(g.getCurrentDiscount());
			wlg.setImg(g.getImg());
			
			toInsert.add(wlg);
		}
		
		return toInsert;
	}
	
	private String fixUrl(String url, ArrayList<Store> stores, String storeName) {
		for (Store s : stores) {
			if (s.getName().equals(storeName)) {
				url = url.replace(s.getUrl(), "");
				return url;
			}
		}
		
		return url;
	}	
}
