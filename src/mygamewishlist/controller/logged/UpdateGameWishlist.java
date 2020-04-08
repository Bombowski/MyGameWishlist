package mygamewishlist.controller.logged;

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
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * Servlet implementation class UpdateGameWishlist
 */
@WebServlet("/UpdateGameWishlist")
public class UpdateGameWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	private String url;
	
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
				rd.forward(request, response);
				return;
			}
			url = request.getParameter("url");
			
			if (url == null) {
				rd = getServletContext().getRequestDispatcher(cp.MYLIST);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_UPDATE_GAME_WISHLIST);
				WishListGame wlg = cq_ejb.getGameFromListByIdUserUrl(usr.getId(), url);
				request.setAttribute("game", wlg);
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
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				response.sendRedirect(cp.LOGIN);
				return;
			}
			
			if (url == null) {
				response.sendRedirect(cp.MYLIST);
				return;
			}
			
			double min = lowerThan0(Double.parseDouble(request.getParameter("min")));
			double max = lowerThan0(Double.parseDouble(request.getParameter("max")));
			
			cq_ejb.updateMinMax(min, max, url, usr.getId());
			response.sendRedirect(cp.REDIRECT_MYLIST);			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
	
	private double lowerThan0(double v) {
		return v < 0 ? -1 : v;
	}
}
