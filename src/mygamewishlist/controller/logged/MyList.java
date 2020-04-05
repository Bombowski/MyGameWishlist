package mygamewishlist.controller.logged;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.json.JSONArray;
import org.json.JSONObject;

import mygamewishlist.model.ejb.ClientSessionEJB;
import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.SteamGame;
import mygamewishlist.model.pojo.db.User;
import mygamewishlist.model.pojo.db.WishListGame;


/**
 * Servlet implementation class MyList
 */
@WebServlet("/MyList")
public class MyList extends HttpServlet {

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
			RequestDispatcher rd;
			
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else {
				rd = getServletContext().getRequestDispatcher(cp.JSP_MYLIST);
				
				ArrayList<WishListGame> list = cq_ejb.getListByIdUser(usr.getId());
				request.setAttribute("list", list);
			}
			
			rd.forward(request, response);
		} catch(Exception e) {
			LOG.logError(e.getMessage());
			response.sendRedirect(cp.JSP_LOGIN);
		}
	}
}
