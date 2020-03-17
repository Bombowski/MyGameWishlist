package mygamewishlist.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mygamewishlist.model.ejb.CreateQuery;
import mygamewishlist.model.ejb.ClientSessionEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final MyLogger LOG = MyLogger.getLOG();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQuery cq_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (sc_ejb.isSome1Logged(request.getSession(false))) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.MYLIST);
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(ClassPaths.JSP_REDIRECT_LOGIN);
				rd.forward(request, response);
			}
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			
			User usr = cq_ejb.getUserByEmail(email);
			
			if (usr == null) {
				usr = new User(email, name, 0);
				cq_ejb.addUser(usr);
				sc_ejb.loginUser(request.getSession(false), usr);
			} else {
				sc_ejb.loginUser(request.getSession(false), usr);
			}
			
			response.sendRedirect(ClassPaths.JSP_REDIRECT_MYLIST);		
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(ClassPaths.JSP_REDIRECT_LOGIN);
		}
	}
}
