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
import mygamewishlist.model.pojo.db.Game;
import mygamewishlist.model.pojo.db.User;

/**
 * Servlet implementation class AddGame
 */
@WebServlet("/AddGame")
public class AddGame extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	private String error = "";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else if (usr.getAdmin() != 1) {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_ADD_GAME);
			}
			
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				response.sendRedirect(cp.REDIRECT_LOGIN);
			} else if (usr.getAdmin() != 1) {
				response.sendRedirect(cp.REDIRECT_MYLIST);
			} else {
				response.sendRedirect(cp.REDIRECT_ADD_GAME);
			}
			
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			error = chkStuff(name, description);
			
			if (error.equals("")) {
				cq_ejb.addGame(new Game(name, description));
				response.sendRedirect(cp.REDIRECT_GAME_LIST);
			} else {
				response.sendRedirect(cp.REDIRECT_ADD_GAME);
			}
			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
	
	private String chkStuff(String name, String description) {
		if (name == null || description == null) {
			return "Unvalid value";
		} else if(name.length() > 32 || name.length() == 0 || description.length() > 256 || description.length() == 0) {
			return "Unvalid length";
		}
		
		for (Game g : cq_ejb.getGames()) {
			if (g.getName().equals(name)) {
				return "A game with this name already exists";
			}
		}
		
		return "";
	}
}
