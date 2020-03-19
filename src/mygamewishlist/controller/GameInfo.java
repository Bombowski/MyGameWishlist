package mygamewishlist.controller;

import java.io.IOException;

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

/**
 * Servlet implementation class GameInfo
 */
@WebServlet("/GameInfo")
public class GameInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final MyLogger LOG = MyLogger.getLOG();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQuery cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.GAME_INFO);
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(ClassPaths.REDIRECT_LOGIN);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
				
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(ClassPaths.REDIRECT_LOGIN);
		}
	}
}
