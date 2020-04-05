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

@WebServlet("/DeleteGameWishlist")
public class DeleteGameWishlist extends HttpServlet {

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
			
			if (usr == null) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.LOGIN);
				rd.forward(request, response);
				return;
			}
			
			int idList = cq_ejb.getIdListByIdUser(usr.getId());
			String url = request.getParameter("url");
			
			cq_ejb.deleteGameWishlist(url, idList);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(cp.MYLIST);
		rd.forward(request, response);
	}
}
