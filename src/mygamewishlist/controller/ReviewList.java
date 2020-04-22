package mygamewishlist.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class ReviewList
 */
@WebServlet("/ReviewList")
public class ReviewList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			ArrayList<mygamewishlist.model.pojo.db.ReviewList> reviews = 
					(usr == null ? cq_ejb.getReviewListNotLogged() : cq_ejb.getReviewList(usr.getId()));
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.JSP_REVIEW_LIST);
			request.setAttribute("reviews", reviews);
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.REDIRECT_LOGIN);
		}
	}
}
