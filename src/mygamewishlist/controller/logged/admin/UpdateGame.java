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
 * Servlet implementation class UpdateGame
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
	FormatCheckingEJB fc_ejb;
	
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
			}
			
			int id = Integer.parseInt(request.getParameter("id"));
			Game game = cq_ejb.getGame(id);
			
			if (game == null) {
				rd = getServletContext().getRequestDispatcher(cp.GAME_LIST);
			}
			
			idGame = game.getId();
			
			request.setAttribute("game", game);
			request.setAttribute("error", error);
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_GAME_LIST);
			error = "Unexpected error occured";
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (idGame == -1) {
				response.sendRedirect(cp.REDIRECT_GAME_LIST);
			} else {
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String[] genres = request.getParameterValues("genre");
				String rDate = request.getParameter("rDate");
				String idDeveloper = request.getParameter("idDev");
				
				error = fc_ejb.chkGame(name, description, genres, rDate, idDeveloper);
				
				if (error.equals("")) {
					Game g = new Game(name, description, rDate, idGame, 
							cq_ejb.getDeveloperById(Integer.parseInt(idDeveloper)).getName());
					
					cq_ejb.updateGame(g);
					
					response.sendRedirect(cp.REDIRECT_GAME_LIST);
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
