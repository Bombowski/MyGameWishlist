package mygamewishlist.controller.logged.admin;

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
import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.User;

/**
 * Servlet implementation class GameList
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else if (usr.getAdmin() != 1) {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_GAME_LIST);
				ArrayList<Game> games = cq_ejb.getGames();
				request.setAttribute("games", games);
			}
			
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
}
