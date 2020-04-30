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
import mygamewishlist.model.ejb.FormatCheckingEJB;
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
	
	@EJB
	FormatCheckingEJB fc_ejb;
	
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
				request.setAttribute("genres", cq_ejb.getGenres());
				request.setAttribute("devs", cq_ejb.getDevelopers());
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
			String[] genres = request.getParameterValues("genre");
			String rDate = request.getParameter("rDate");
			String idDeveloper = request.getParameter("idDev");
			
			error = fc_ejb.chkGame(name, description, genres, rDate, idDeveloper);
			
			if (error.equals("")) {
				cq_ejb.addGame(new Game(name, description, rDate, 
						cq_ejb.getDeveloperById(Integer.parseInt(idDeveloper)).getName()), 
						genres);
				response.sendRedirect(cp.REDIRECT_GAME_LIST);
			} else {
				response.sendRedirect(cp.REDIRECT_ADD_GAME);
			}
			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
}
