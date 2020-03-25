package mygamewishlist.controller.logged;

import java.io.IOException;
import java.util.ArrayList;

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
	
	private ArrayList<ScrapedGame> steam;
	private ArrayList<ScrapedGame> g2a;
	private ArrayList<ScrapedGame> instant;
	private String searchParam;
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQuery cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher rd;
			
			if (steam != null || g2a != null || instant != null) {
				rd = getServletContext().getRequestDispatcher(cp.JSP_ADD_GAME_OPTIONS);
				request.setAttribute("stores", cq_ejb.getStoreNames());
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
				addGames(id, request);
			} else {
				steam = (ArrayList<ScrapedGame>)request.getAttribute("Steam");
				g2a = (ArrayList<ScrapedGame>)request.getAttribute("G2A");
				instant = (ArrayList<ScrapedGame>)request.getAttribute("Instant Gaming");
				
				doGet(request, response);
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		steam = g2a = instant = null;
	}
	
	private void addGames(String[] id, HttpServletRequest request) {
		ArrayList<WishListGame> toInsert = new ArrayList<WishListGame>();
		ArrayList<Store> stores = cq_ejb.getStores();
		
		for (String s : id) {
			ScrapedGame g = steam.get(Integer.parseInt(s.substring(s.length() - 1)));
			WishListGame wlg = new WishListGame();
			
			wlg.setUrlGame(fixUrl(g.getUrl(), stores));
			wlg.setIdList(cq_ejb.getIdList(sc_ejb.getLoggedUser(request).getId()));
			wlg.setIdStore(Integer.parseInt(s.substring(0,s.indexOf("&"))));
			wlg.setName(g.getFullName());
			wlg.setDefaultPrice(g.getDefaultPrice());
			wlg.setCurrentPrice(g.getCurrentPrice());
			wlg.setDiscount(g.getCurrentDiscount());
			wlg.setImg(g.getImg());
			
			toInsert.add(wlg);
		}
	}
	
	private String fixUrl(String url, ArrayList<Store> stores) {
		int storeId = Integer.parseInt(url.substring(0,url.indexOf("&")));
		
		for (Store s : stores) {
			if (s.getId() == storeId) {
				url = url.replace(s.getUrl() + s.getQueryPart(), "");
				return url;
			}
		}
		
		return url;
	}	
}
