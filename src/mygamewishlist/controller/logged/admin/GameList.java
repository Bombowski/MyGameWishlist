package mygamewishlist.controller.logged.admin;

import java.io.IOException;

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
import mygamewishlist.model.pojo.Pagination;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 *
 * Servlet used for showing a CRUD for games
 */
@WebServlet("/GameList")
public class GameList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	// Object that contains all of the games
	private Pagination<Game> games;
	
	/**
	 * Forwards a page of games to jsp
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else if (usr.getAdmin() != 1) {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			} else {
				/*
				 * if the list is a null or a refresh parameter was sent,
				 * the list gets refreshed.
				 */
				if(games == null || request.getAttribute("r") != null) {
					request.removeAttribute("r");
					games = new Pagination<Game>(cq_ejb.getGames(), 10);
				}
				
				rd = getServletContext().getRequestDispatcher(cp.JSP_GAME_LIST);
								
				// if the page parameter wasn't sent, page is set to 0
				String pagStr = request.getParameter("pag");
				int pag = pagStr == null ? Integer.parseInt("0") : Integer.parseInt(pagStr); 
				
				request.setAttribute("games", games.getPag(pag));
				request.setAttribute("total", games.getTotalPag());
				request.setAttribute("pag", pag);
			}
			
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usr = sc_ejb.getLoggedUser(request);
		
		if (usr == null) {
			response.sendRedirect(cp.REDIRECT_LOGIN);
		} else if (usr.getAdmin() != 1) {
			response.sendRedirect(cp.REDIRECT_MYLIST);
		} else {
			games = new Pagination<Game>(cq_ejb.getGames(), 10);
			response.sendRedirect(cp.REDIRECT_GAME_LIST);
		}
	}
}
