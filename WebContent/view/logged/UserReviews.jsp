<%@page import="mygamewishlist.model.pojo.db.ReviewUser"%>
<%@page import="mygamewishlist.model.pojo.Pagination"%>
<%@page import="bomboshtml.body.table.Td"%>
<%@page import="bomboshtml.body.table.Table"%>
<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Store"%>
<%@page import="bomboshtml.body.Img"%>
<%@page import="bomboshtml.body.A"%>
<%@page import="bomboshtml.body.table.Tr"%>
<%@page import="mygamewishlist.model.pojo.db.WishListGame"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
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
	<jsp:param name="js" value="ResizeImgs,MyList,Checkboxes" />
</jsp:include>
<jsp:include page="../template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		if (usr.getAdmin() == 1) {
	%>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		} else {
	%>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		}
	%>

	<jsp:include page="../template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<div class="row d-flex justify-content-center">
		<%
			@SuppressWarnings("unchecked")
			ArrayList<ReviewUser> reviews = (ArrayList<ReviewUser>) request.getAttribute("reviews");
		
			if (reviews.size() == 0) {
				out.append("<h4>You haven't reviewed any games yet.</h4>");
			} else {
				StringBuilder sb = new StringBuilder();
				
				for (ReviewUser rev : reviews) {
					%>
					<div class="col-12 col-md-5 m-2 py-2 bg-gray d-flex flex-column text-center">
						<% String link = "/MyGameWishlist/GameInfo?id=" + rev.getIdGame(); %>
						<a class="h4" href="<% out.append(link); %>"><% out.append(rev.getGameName()); %></a>
						<div class="d-flex flex-row">
							<div class="col-6 d-flex flex-column">
								<div class="h6">
									Your review:
								</div>
								<div class="h6">
									<% out.append(rev.getReview()); %>
								</div>
							</div>
							<div class="col-6 d-flex flex-column">
								<div class="h6">
									Your rating:
								</div>
								<div class="h6">
									<% out.append(rev.getRating() + ""); %>
								</div>
							</div>
						</div>
						<div class="d-flex flex-row justify-content-center mt-2">
							<a class="btn btn-dark mr-1" href="<% out.append(link); %>">Update review</a>
							<a class="btn btn-dark ml-1" href="<% out.append("/MyGameWishlist/UserReviews?id=" + rev.getIdGame()); %>">Delete review</a>
						</div>
					</div>
					<%
				}
			}
		%>
		</div>
	<jsp:include page="../template/MainBack.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
<jsp:include page="../template/BodyContainerBack.jsp">
	<jsp:param name="" value="" />
</jsp:include>
</html>
