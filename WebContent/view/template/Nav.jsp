<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%!
	JspFunctions jspF = JspFunctions.getJspF();
%>

<nav class="container-fluid bgGray justify-content-center d-flex">
	<div class="navbar navbar-expand d-flex w-75">
		<ul class="navbar-nav w-50">
			<li class="nav-link"><a href="/MyGameWishlist/Login" class="nav-link">Login</a></li>
			<li class="nav-link"><a href="/MyGameWishlist/ReviewList" class="nav-link">Review List</a></li>
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