<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="bomboshtml.body.Input"%>
<%@page import="bomboshtml.body.A"%>
<%@page import="bomboshtml.body.table.Tr"%>
<%@page import="bomboshtml.body.table.Table"%>
<%@page import="mygamewishlist.model.pojo.db.ReviewList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!
	JspFunctions jspF = JspFunctions.getJspF();
	MyLogger log = MyLogger.getLOG();
	ClassPaths cp = ClassPaths.getCP();
%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));
%>

<html>
<jsp:include page="template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<jsp:include page="template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% if (usr == null) { %>
	<jsp:include page="template/Nav.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else if (usr.getAdmin() == 1) { %>
	<jsp:include page="template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>	
	<% } %>
	<jsp:include page="template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<table class="table">
			<tr>
				<th>Average rating</th>
				<th>Game</th>
				<th><% out.append(usr == null ? "Your rating: (not logged)" : "Your rating:"); %></th>
				<th>Rate</th>
				<th></th>
			</tr>
			<%
				try {
					@SuppressWarnings("unchecked")
					ArrayList<ReviewList> reviews = (ArrayList<ReviewList>)request.getAttribute("reviews");
					
					StringBuilder sb = new StringBuilder();
					
					String rateInfo = usr == null ? "Game info" : "Rate/ Game info";
					
					for (ReviewList rl : reviews) {
						Tr tr = new Tr();
						tr.addTd(rl.getAverageRating() + "");
						tr.addTd(rl.getName());
						
						if (rl.getUserRating() == -1) {
							if (usr == null) {
								tr.addTd("");
							} else {
								tr.addTd("Not rated yet.");
							}
						} else {
							tr.addTd(rl.getUserRating() + "");
						}
						
						tr.addTd(new A(rateInfo, new StringBuilder()
								.append(cp.REDIRECT_GAME_INFO)
								.append("?id=")
								.append(rl.getIdGame())
								.toString()));
						
						sb.append(tr.print());
					}
					
					out.print(sb.toString());
				} catch(Exception e) {
					log.logError(e.getMessage());
				}
			%>
		</table>
	<jsp:include page="template/MainBack.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
<jsp:include page="template/BodyContainerBack.jsp">
	<jsp:param name="" value="" />
</jsp:include>
</html>