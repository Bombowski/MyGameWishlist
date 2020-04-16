package mygamewishlist.controller.logged;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mygamewishlist.model.ejb.ClientSessionEJB;
import mygamewishlist.model.ejb.CreateQueryEJB;
import mygamewishlist.model.ejb.GraphDatesEJB;
import mygamewishlist.model.pojo.ClassPaths;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.db.TimelineGameDetailed;
import mygamewishlist.model.pojo.db.User;

@WebServlet("/PriceTimeline")
public class PriceTimeline extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final ClassPaths cp = ClassPaths.getCP();
	
	@EJB
	ClientSessionEJB sc_ejb;
	
	@EJB
	CreateQueryEJB cq_ejb;
	
	@EJB
	GraphDatesEJB gd_ejb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User usr = sc_ejb.getLoggedUser(request);
			RequestDispatcher rd;
			if (usr == null) {
				rd = getServletContext().getRequestDispatcher(cp.LOGIN);
			} else {
				String[] games = request.getParameterValues("games");
				
				if (games == null) {
					rd = getServletContext().getRequestDispatcher(cp.MYLIST);
				} else {
					Hashtable<String, ArrayList<TimelineGameDetailed>> list = new Hashtable<String, ArrayList<TimelineGameDetailed>>();
					for (String str : games) {
						list.put(str, cq_ejb.getTimelineByUrlDetailed(str));						
					}
					
					rd = getServletContext().getRequestDispatcher(cp.JSP_PRICE_TIMELINE);
					request.setAttribute("list", gd_ejb.generateTimeline(list));
					request.setAttribute("dates", gd_ejb.getDates());
				}
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
			
		} catch(Exception e) {
			LOG.logError(e.getMessage());
		}
	}
}
