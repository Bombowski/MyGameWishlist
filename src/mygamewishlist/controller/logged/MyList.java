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
	private static final ClassPaths cp = ClassPaths.getCP();

	private Pagination<WishListGame> list;
	private int previousUser = -1;
	
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
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_MYLIST);
			
				if (previousUser == -1) {
					previousUser = usr.getId();
				} else if(previousUser != usr.getId()) {
					previousUser = usr.getId();
					list = new Pagination<WishListGame>(
							cq_ejb.getListByIdUser(usr.getId()), 10);
				}
				
				/*
				 * if the list is null, or the restart attribute was sent, 
				 * the list gets refreshed
				 */
				if (list == null || request.getAttribute("r") != null) {
					request.removeAttribute("r");
					
					list = new Pagination<WishListGame>(
						cq_ejb.getListByIdUser(usr.getId()), 10);					
				}
			}
			
			request.setAttribute("stores", cq_ejb.getStores());
			request.setAttribute("list", list);

			rd.forward(request, response);
		} catch (Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.JSP_LOGIN);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User usr = sc_ejb.getLoggedUser(request);
		
		if (usr == null) {
			response.sendRedirect(cp.REDIRECT_LOGIN);
		} else {
			list = new Pagination<WishListGame>(
					cq_ejb.getListByIdUser(usr.getId()), 10);
			
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
}
