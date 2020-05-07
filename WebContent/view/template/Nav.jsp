<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%!JspFunctions jspF = JspFunctions.getJspF();%>

<nav class="col-12">
	<div class="w-75 row mx-auto">
		<div class="col-md-6 col-sm-12 navbar navbar-expand ">
			<ul class="navbar-nav mx-auto mx-lg-0 mr-lg-auto">
				<li class="nav-link h4"><u><a href="/MyGameWishlist/ReviewList" class="nav-link">Review List</a></u></li>
			</ul>
		</div>
		<div class="col-md-3 col-sm-12 d-flex ml-md-auto">
			<%
				if (jspF.getLoggedUser(request.getSession(false)) == null) {
			%>
			<div class="g-signin2 color-black my-auto mx-md-0 mx-auto ml-md-auto" data-onsuccess="onSignIn" id="myP"></div>
			<%
				} else {
			%>
			<button class="logout color-black my-auto mx-md-0 mx-auto ml-md-auto">Sign Out</button>
			<%
				}
			%>
		</div>
	</div>
</nav>