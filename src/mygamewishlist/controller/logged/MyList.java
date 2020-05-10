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
			String pag = request.getParameter("pag");
			RequestDispatcher rd;

			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else if (list != null && pag != null) {
				rd = getServletContext().getRequestDispatcher(cp.JSP_MYLIST);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_MYLIST);

				list = new Pagination<WishListGame>(
						cq_ejb.getListByIdUser(usr.getId()), 10); 
			}
			
			request.setAttribute("stores", cq_ejb.getStores());
			request.setAttribute("list", list);

			rd.forward(request, response);
		} catch (Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.JSP_LOGIN);
		}
	}

	/**
	 * Changes page of list
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	private void changePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (list == null) {
				response.sendRedirect(cp.MYLIST);
			}

			String pagChk = request.getParameter("pag");
			int pag = Integer.parseInt(pagChk == null ? "0" : pagChk);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.JSP_MYLIST);
			request.setAttribute("list", list);
			request.setAttribute("stores", cq_ejb.getStores());
			rd.forward(request, response);			
		} catch (Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_MYLIST);
		}
	}
}
