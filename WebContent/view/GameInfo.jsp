<%@page import="mygamewishlist.model.pojo.db.GameFull"%>
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
		<%
			GameFull g = null;
			try {
				 g = (GameFull) request.getAttribute("game");
			} catch(Exception e) {
				 log.logError(e.getMessage());
				 response.sendRedirect(cp.REDIRECT_GAME_LIST);
			}
		%>
		<form method="post" action="<% out.append(cp.REDIRECT_GAME_INFO); %>">
			<div class="row d-flex flex-row">
				<div class="row d-flex flex-column col-6 mx-auto bg-danger">
					<div class="form-row my-2">
		                <div class="col-md-6 col-12 d-flex">
		                    <span class="ml-md-auto mr-md-0 mx-auto my-auto" for="name">Name</span>
		                </div>
		                <div class="col-md-6 col-12 color-black">
		                    <span class="form-control"><% out.append(g.getName()); %></span>
		                </div>
		            </div>
	            </div>
	            <div class="row d-flex flex-column col-6 bg-success">
	            	<div class="form-row my-2 mx-auto">
		                <span class="h4">Review</span>
		            </div>
		            <div class="form-row my-2 color-black">
		                <button type="submit" class="btn button-dark mx-auto">
		                	Send review
		                </button>
		            </div>
	            </div>
            </div>		
		</form>
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