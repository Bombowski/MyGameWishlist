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
import mygamewishlist.model.ejb.FormattingEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.GameFull;
import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 *
 * Servlet that adds a game.
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
	FormattingEJB fc_ejb;
	
	private String error = "";
	
	/**
	 * Gets and then forwards a list of genres and developers to the jsp
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
				rd = getServletContext().getRequestDispatcher(cp.JSP_ADD_GAME);
				request.setAttribute("genres", cq_ejb.getGenres());
				request.setAttribute("devs", cq_ejb.getDevelopers());
			}
			
			request.setAttribute("error", error);
			error = "";
			rd.forward(request, response);			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}

	/**
	 * Revieves data of the game and if its correct, adds it.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				response.sendRedirect(cp.REDIRECT_LOGIN);
			} else if (usr.getAdmin() != 1) {
				response.sendRedirect(cp.REDIRECT_MYLIST);
			}
			
			// get all of the game data
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String[] genres = request.getParameterValues("genre");
			String rDate = request.getParameter("rDate");
			String idDeveloper = request.getParameter("idDev");
			
			// check if its correct
			error = fc_ejb.chkGame(name, description, genres, rDate, idDeveloper);
			
			// if its correct, add it
			if (error.equals("")) {
				int idDevInt = Integer.parseInt(idDeveloper); 
				GameFull g = new GameFull(fc_ejb.arrToString(genres), idDevInt);
				g.setDescription(description);
				g.setDeveloper(cq_ejb.getDeveloperById(idDevInt).getName());
				g.setName(name);
				g.setReleaseDate(rDate);
				
				cq_ejb.addGame(g);
				
				getServletContext().getRequestDispatcher(cp.GAME_LIST).forward(request, response);
			} else {
				response.sendRedirect(cp.REDIRECT_ADD_GAME);
			}
			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
}
