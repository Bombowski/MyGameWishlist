<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Game"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
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
			<%
			Game g = null;
				try {
					g = (Game) request.getAttribute("game");
				} catch(Exception e) {
					 log.logError(e.getMessage());
					 response.sendRedirect(cp.REDIRECT_GAME_LIST);
				}
			
				StringBuilder sb = new StringBuilder();
				sb.append("<h2>")
					.append(g.getName())
					.append("</h2>")
					.append("<p>")
					.append(g.getDescription())
					.append("</p>");
				
				out.append(sb.toString());
			%>
			<form method="post" action="<% out.append(cp.REDIRECT_GAME_INFO); %>">
				<input type="number" name="rating" step="0.1" min="0" max="10" value="0">
				<button type="submit">
					Rate
				</button>
			</form>
		</div>
	</main>
	<jsp:include page="template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>