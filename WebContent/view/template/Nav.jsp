<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%!
	JspFunctions jspF = JspFunctions.getJspF();
%>

<nav class="container-fluid bgGray justify-content-center d-flex">
	<div class="navbar navbar-expand d-flex w-75">
		<ul class="navbar-nav">
			<li class="nav-link"><a href="#" class="nav-link">XD</a></li>
			<li class="nav-link"><a href="#" class="nav-link">XD</a></li>
			<li class="nav-link"><a href="#" class="nav-link">XD</a></li>
		</ul>
		<div class="d-flex justify-content-end w-100">
			<% if (jspF.getLoggedUser(request.getSession(false)) == null) { %>
			<div class="g-signin2" data-onsuccess="onSignIn" id="myP"></div>
			<% } else { %>
	        <button class="logout">Sign Out</button>
	        <% } %>
        </div>
	</div>
</nav>