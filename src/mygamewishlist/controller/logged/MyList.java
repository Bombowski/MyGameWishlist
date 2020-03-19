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
import mygamewishlist.model.ejb.CreateQuery;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.User;

/**
 * Servlet implementation class MyList
 */
@WebServlet("/MyList")
public class MyList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQuery cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.LOGIN);
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.JSP_MYLIST);
				rd.forward(request, response);
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(ClassPaths.JSP_LOGIN);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			
			if (usr == null) {
				response.sendRedirect(ClassPaths.REDIRECT_LOGIN);
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.JSP_MYLIST);
				rd.forward(request, response);
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(ClassPaths.REDIRECT_MYLIST);
		}
	}
}
