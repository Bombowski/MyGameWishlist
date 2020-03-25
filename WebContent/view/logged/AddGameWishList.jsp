<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Store"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>

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
			<form action="<% out.append(cp.REDIRECT_ADD_GAME_WISHLIST); %>" method="post">
				<div class="form-row">
					<div class="col">
						Name
					</div>
					<div class="col">
						<input class="form-control" type="text" name="name" required>
					</div>
				</div>
				<div class="form-row">
					<div class="col">
						Online stores in which you are interested
					</div>
					<div class="col">
						<%
							try {
								ArrayList<Store> stores = (ArrayList<Store>) request.getAttribute("stores");
								StringBuilder sb = new StringBuilder();
								
								for (Store st : stores) {
									sb.append(new Input("checkbox","store",st.getName()).print())
										.append(st.getName());
								}
								
								out.append(sb.toString());
							} catch(Exception e) {
								log.logError(e.getMessage());
								response.sendRedirect(cp.REDIRECT_GAME_LIST);
							}
						%>
					</div>
					<button class="btn btn-primary" type="submit">
					 	Add game
					</button>
				</div>
			</form>
			<%
				try {
					
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