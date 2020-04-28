<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%!
	JspFunctions jspF = JspFunctions.getJspF();
%>

<nav class="container-fluid justify-content-center d-flex">
    <div class="navbar navbar-expand d-flex w-75">
        <ul class="navbar-nav w-50">
			<li class="nav-link h4"><u><a href="/MyGameWishlist/ReviewList" class="nav-link">Review List</a></u></li>
        </ul>
		<div class="d-flex justify-content-end w-100">
			<% if (jspF.getLoggedUser(request.getSession(false)) == null) { %>
			<div class="g-signin2 color-black" data-onsuccess="onSignIn" id="myP"></div>
			<% } else { %>
	        <button class="logout color-black">Sign Out</button>
	        <% } %>
        </div>
	</div>
</nav>