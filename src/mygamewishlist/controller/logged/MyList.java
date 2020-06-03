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
import mygamewishlist.model.pojo.MyCalendar;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.Pagination;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * @author Patryk
 *
 * Servlet that shows a list of games from users wishlist
 */
@WebServlet("/MyList")
public class MyList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths CP = ClassPaths.getCP();
	private static final MyCalendar CAL = MyCalendar.getMC();

	private Pagination<WishListGame> list;
	private int previousUser = -1;
	private String lastDate = "";
	private int lastHour = -1;
	
	@EJB
	ClientSessionEJB sc_ejb;

	@EJB
	CreateQueryEJB cq_ejb;
	
	/**
	 * Shows a list of games from users wishlist
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;		
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(CP.LOGIN);
			} else {
				rd = getServletContext().getRequestDispatcher(CP.JSP_MYLIST);
			
				if (previousUser == -1) {
					previousUser = usr.getId();
				} else if(previousUser != usr.getId()) {
					previousUser = usr.getId();
					list = new Pagination<WishListGame>(
							cq_ejb.getListByIdUser(usr.getId()), 10);
					updateLastDateHour();
				}
				
				/*
				 * if the list is null, or the restart attribute was sent, 
				 * or an hour or more time have passed, the list gets refreshed
				 */
				if (list == null || request.getAttribute("r") != null || 
						!lastDate.equals(CAL.getSqlDate()) || lastHour != CAL.getHour()) {
					request.removeAttribute("r");
					
					list = new Pagination<WishListGame>(
						cq_ejb.getListByIdUser(usr.getId()), 10);
					updateLastDateHour();
				}
				
				request.setAttribute("stores", cq_ejb.getStores());
				request.setAttribute("list", list);
			}

			rd.forward(request, response);
		} catch (Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(CP.JSP_LOGIN);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usr = sc_ejb.getLoggedUser(request);
		
		if (usr == null) {
			response.sendRedirect(CP.REDIRECT_LOGIN);
		} else {
			list = new Pagination<WishListGame>(
					cq_ejb.getListByIdUser(usr.getId()), 10);
			
			response.sendRedirect(CP.REDIRECT_MYLIST);
		}
	}
	
	/**
	 * Updates current hour and date and hour and sqlDate from MyCalendar
	 */
	private void updateLastDateHour() {
		lastDate = CAL.getSqlDate();
		lastHour = CAL.getHour();
	}
}
