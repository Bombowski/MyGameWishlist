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
			<form action="<% out.append(cp.REDIRECT_ADD_GAME_OPTIONS); %>">
				<% 
					String searchParam = (String)request.getAttribute("search");
					
					searchParam = searchParam == null ? "" : searchParam;
					
					out.append("<span class='h3'>Results for: ")
						.append(searchParam)
						.append("</span>");
				%>
				<h5 class="mb-0">
					<%
						try {
							ArrayList<String> stores = (ArrayList<String>)request.getAttribute("stores");
							ArrayList<ScrapedGame> steam = (ArrayList<ScrapedGame>)request.getAttribute("Steam");
							ArrayList<ScrapedGame> g2a = (ArrayList<ScrapedGame>)request.getAttribute("G2A");
							ArrayList<ScrapedGame> instant = (ArrayList<ScrapedGame>)request.getAttribute("Instant Gaming"); 
							
							int i = 0;
							Hashtable<Integer, ArrayList<ScrapedGame>> lists = new Hashtable<Integer, ArrayList<ScrapedGame>>();
							lists.put(1, steam);
							lists.put(2, g2a);
							lists.put(3, instant);
							
							for (String str : stores) {
								out.append("<button type='button' class='btn btn-primary' id='st")
									.append(i + "'>")
			                        .append(str != null ? str : "")
			                        .append("</button>");
							}
					%>
                </h5>
                <div id="tables">
					<%
							String noR = "No results.";
							i = 0;
							
							for (Integer key : lists.keySet()) {
								ArrayList<ScrapedGame> games = lists.get(key);
								
								if (games == null) {
									out.append("<div id='store</div>");
								} else {
									out.append("<div id='")
										.append(key + "'>")
										.append(games.size() == 0 ? noR : jspF.buildScrapedGameTable(games))
										.append("</div>");
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