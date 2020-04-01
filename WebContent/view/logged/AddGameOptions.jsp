<%@page import="java.util.Hashtable"%>
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
			<form action="<% out.append(cp.REDIRECT_ADD_GAME_OPTIONS); %>" method="post">
				<% 
					String searchParam = (String)request.getAttribute("search");
					
					searchParam = searchParam == null ? "" : searchParam;
					
					out.append("<span class='h3'>Results for: ")
						.append(searchParam)
						.append("</span>");
				%>
				<h5 class="mb-0 buttons">
					<%
						try {
							ArrayList<String> stores = (ArrayList<String>)request.getAttribute("stores");
							Hashtable<String,ArrayList<ScrapedGame>> games = 
									(Hashtable<String,ArrayList<ScrapedGame>>)request.getAttribute("games"); 
							
							int i = 0;
							
							if (stores == null) {
								response.sendRedirect(cp.REDIRECT_MYLIST);
								return;
							}
							
							for (String str : stores) {
								if (str != null) {
									out.append("<button type='button' class='btn btn-primary' id='")
										.append(str)
										.append("'>")
				                        .append(str)
				                        .append("</button>");
								}
							}
					%>
                </h5>
                <div id="tables">
					<%
							String noR = "No results.";
							i = 0;
							
							for (String key : games.keySet()) {
								ArrayList<ScrapedGame> list = games.get(key);
								
								if (list == null) {
									out.append("<div id='store'></div>");
								} else {
									out.append("<div id='")
										.append(key)
										.append("tbl'>");
									
										if (list.isEmpty()) { 
											out.append(noR);
										} else {
					%>											
											<table class='table'>
												<th></th>
												<th>Name</th>
												<th>Default price</th>
												<th>Current price</th>
												<th>Current discount</th>
												<th>Check games you want to add</th>
												<th colspan="2">prices at which you want to get notified</th>
												<%
													out.append(jspF.buildScrapedGameTable(list));									
												%>
											</table>
					<%	
										}
									out.append("</div>");
								}
								i++;
							}
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