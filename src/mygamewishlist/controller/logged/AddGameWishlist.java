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
import mygamewishlist.model.ejb.ScrapingEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.Game2Scrap;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.Store;
import mygamewishlist.model.pojo.db.User;

/**
 * Servlet implementation class AddGameWishlist
 */
@WebServlet("/AddGameWishlist")
public class AddGameWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	@EJB
	ScrapingEJB scrap_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (!sc_ejb.isSome1Logged(request.getSession(false))) {
				response.sendRedirect(cp.REDIRECT_LOGIN);
				return;
			}
			
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_ADD_GAME_WISHLIST);
				
				ArrayList<Store> stores = cq_ejb.getStores();
				request.setAttribute("stores", stores);
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
			if (!sc_ejb.isSome1Logged(request.getSession(false))) {
				response.sendRedirect(cp.REDIRECT_LOGIN);
				return;
			}
			
			String name = request.getParameter("name");
			String chked[] = request.getParameterValues("store");
			ArrayList<Store> stores = cq_ejb.getStores();
			
			for (String str : chked) {
				for (Store st : stores) {
					if (str.equals(st.getName())) {						
						Hashtable<String,ArrayList<ScrapedGame>> games = 
								scrap_ejb.getGamesByNameUrl(new Game2Scrap(name, st.getName(), st.getUrl(), st.getQueryPart()));
						request.setAttribute(st.getName(), games);
						break;
					}
				}
			}			
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.ADD_GAME_OPTIONS);
			request.setAttribute("search", name);
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
}
