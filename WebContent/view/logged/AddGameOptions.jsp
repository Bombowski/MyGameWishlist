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
	<jsp:param name="js" value="ClearGameOptions" />
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
			<form action="<% out.append(cp.REDIRECT_ADD_GAME_OPTIONS); %>">
				<% 
					String searchParam = (String)request.getAttribute("search");
					
					searchParam = searchParam == null ? "" : searchParam;
					
					out.append("<span class='h3'>Results for: ")
						.append(searchParam)
						.append("</span>");
				%>
				<h5 class="mb-0">
                    <button type="button" class="btn btn-primary" id="st1">
                        1
                    </button>
                    <button type="button" class="btn btn-primary" id="st2">
                        2
                    </button>
                    <button type="button" class="btn btn-primary" id="st3">
                        3
                    </button>
                </h5>
                <div id="tables">
					<%
						try {
							ArrayList<ScrapedGame> steam = (ArrayList<ScrapedGame>)request.getAttribute("steam");
							ArrayList<ScrapedGame> g2a = (ArrayList<ScrapedGame>)request.getAttribute("g2a");
							ArrayList<ScrapedGame> instant = (ArrayList<ScrapedGame>)request.getAttribute("instant");
							String noR = "No results.";
							
							out.append("<div id='store1'>")
								.append(steam == null ? noR : jspF.buildScrapedGameTable(steam))
								.append("</div>")
								.append("<div id='store2'>")
								.append(g2a == null ? noR : jspF.buildScrapedGameTable(g2a))
								.append("</div>")
								.append("<div id='store3'>")
								.append(instant == null ? noR : jspF.buildScrapedGameTable(instant))
								.append("</div>");
						} catch(Exception e) {
							log.logError(e.getMessage());
							response.sendRedirect(cp.REDIRECT_MYLIST);
						}
					%>
				</div>
				<button class="btn btn-primary" type="submit">
					Add games
				</button>
			</form>
		</div>
	</main>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>