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

/**
 * @author Patryk
 *
 * Servlet that shows a list of reviews of the current user.
 */
@WebServlet("/UserReviews")
public class UserReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;

	@EJB
	CreateQueryEJB cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.REVIEW_LIST);
			} else {
				String id = request.getParameter("id");
				rd = getServletContext().getRequestDispatcher(cp.JSP_USER_REVIEWS);
				
				if (id != null) {
					cq_ejb.deleteReview(usr.getId(), Integer.parseInt(id));
				}
				
				request.setAttribute("reviews", cq_ejb.getUserReviews(usr.getId()));				
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			rd = getServletContext().getRequestDispatcher(cp.REVIEW_LIST);
		}
		rd.forward(request, response);
	}
}