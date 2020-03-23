<%@page import="mygamewishlist.model.pojo.ScrapedGame"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
	JspFunctions jspF = JspFunctions.getJspF();
	MyLogger log = MyLogger.getLOG();
	ClassPaths cp = ClassPaths.getCP();
%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));

	if (usr == null) {
		response.sendRedirect(cp.REDIRECT_LOGIN);
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<body>
	<!-- añado el html del header -->
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	
	<!-- añado el html del nav -->
	<% if (usr.getAdmin() == 1) { %>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>	
	<% } %>

	<main class="content-fluid p-4 mb-5">
		<div class="w-75 m-auto">
			<%
				try {
					ArrayList<ScrapedGame> steam = (ArrayList<ScrapedGame>)request.getAttribute("steam");
					ArrayList<ScrapedGame> g2a = (ArrayList<ScrapedGame>)request.getAttribute("g2a");
					ArrayList<ScrapedGame> instant = (ArrayList<ScrapedGame>)request.getAttribute("instant");
					String searchParam = (String)request.getAttribute("search");
					
					out.append("<span class='h3'>Results for: ")
						.append(searchParam)
						.append("</span>")
						.append(steam == null ? "" : jspF.buildScrapedGameTable(steam))
						.append(g2a == null ? "" : jspF.buildScrapedGameTable(g2a))
						.append(instant == null ? "" : jspF.buildScrapedGameTable(instant));
				} catch(Exception e) {
					log.logError(e.getMessage());
				}
			%>
		</div>
	</main>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>