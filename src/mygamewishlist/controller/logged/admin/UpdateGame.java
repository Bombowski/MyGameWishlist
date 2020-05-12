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
 * Servlet that updates game infromation
 */
@WebServlet("/UpdateGame")
public class UpdateGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
		
	private int idGame = -1;
	private String error = "";
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	@EJB
	FormattingEJB fc_ejb;
	
	/**
	 * Recieve game id, get get information, and send it to jsp.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			idGame = -1;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else if (usr.getAdmin() != 1) {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_UPDATE_GAME);
				
				int id = Integer.parseInt(request.getParameter("id"));
				GameFull game = cq_ejb.getGameById(id);
				
				if (game == null) {
					rd = getServletContext().getRequestDispatcher(cp.GAME_LIST);
				} else {
					idGame = game.getId();
					
					request.setAttribute("game", game);
					request.setAttribute("error", error);
					request.setAttribute("genres", cq_ejb.getGenres());
					request.setAttribute("devs", cq_ejb.getDevelopers());
				}
			}
			
			rd.forward(request, response);
			error = "";
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_GAME_LIST);
			error = "Unexpected error occured";
		}
	}

	/**
	 * Recieve intruduced game data, check if its valid, and then update game data.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (idGame == -1) {
				response.sendRedirect(cp.REDIRECT_GAME_LIST);
			} else {
				// get all of the data
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String[] genres = request.getParameterValues("genre");
				String rDate = request.getParameter("rDate");
				String idDeveloper = request.getParameter("idDev");
				
				// if it isn't correct error won't be an empty string
				error = fc_ejb.chkGame(name, description, genres, rDate, idDeveloper);
				
				if (error.equals("")) {
					GameFull g = new GameFull(fc_ejb.arrToString(genres),Integer.parseInt(idDeveloper));
					g.setDescription(description);
					g.setName(name);
					g.setReleaseDate(rDate);
					g.setId(idGame);
					
					cq_ejb.updateGame(g);
					
					getServletContext().getRequestDispatcher(cp.GAME_LIST).forward(request, response);
				} else {
					response.sendRedirect(cp.REDIRECT_UPDATE_GAME);
				}
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_GAME_LIST);
			error = "Unexpected error occured";
		}
	}
}
