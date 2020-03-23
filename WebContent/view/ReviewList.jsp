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
%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));
%>

<html>
<jsp:include page="template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<body>
	<!-- añado el html del header -->
	<jsp:include page="template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	
	<!-- añado el html del nav -->
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

	<main class="content-fluid p-4 mb-5">
		<div class="w-75 m-auto">
			<table class="table">
				<%
					try {
						ArrayList<ReviewList> reviews = (ArrayList<ReviewList>)request.getAttribute("reviews");
						
						StringBuilder sb = new StringBuilder();
						
						Tr th = new Tr();
						th.addTd("Average rating");
						th.addTd("Game");
						th.addTd(usr == null ? "Your rating: (not logged)" : "Your rating:");
						sb.append(th.print());
						
						for (ReviewList rl : reviews) {
							Tr tr = new Tr();
							tr.addTd(new A(rl.getAverageRating() + "", "/MyGameWishlist/GameInfo?id=" + rl.getIdGame()));
							tr.addTd(rl.getName());
							tr.addTd(rl.getUserRating() == -1 ? "" : rl.getUserRating() + "");
							
							sb.append(tr.print());
						}
						
						out.print(sb.toString());
					} catch(Exception e) {
						log.logError(e.getMessage());
					}
				%>
			</table>
		</div>
	</main>
	<jsp:include page="template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>