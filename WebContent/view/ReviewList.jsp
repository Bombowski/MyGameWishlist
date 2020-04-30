<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="bomboshtml.body.Input"%>
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
		<div class="d-flex row justify-content-center">
		<%
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ReviewList> reviews = (ArrayList<ReviewList>)request.getAttribute("reviews");
			
			String rateInfo = usr == null ? "Game info" : "Rate/ Game info";
			
			for (ReviewList rl : reviews) {
				double avgRating = rl.getAverageRating();
				String rating = avgRating + "";
				StringBuilder sb = new StringBuilder()
						.append(cp.REDIRECT_GAME_INFO)
						.append("?id=")
						.append(rl.getIdGame());
				String color;
				
				if (avgRating == -1) {
					color = "bg-dark";
					rating = "-";
				} else if (avgRating < 4) {
					color = "bg-danger";
				} else if (avgRating < 7) {
					color = "bg-warning";
				} else {
					color = "bg-success";
				}
		%>
             <div class="flex-column bg-gray col-xl-2 col-lg-3 col-md-4 col-sm-5 col-12 justify-content-center text-center p-0 ml-2 mt-3">
                 <div class="m-1 mt-2">
                     <u><span class="h4"><% out.append(rl.getName()); %></span></u>
                </div>
                <div class="d-flex mt-3 flex-row justify-content-center">
                    <div class="mr-3 my-auto">
                        Average score
                    </div>
                    <div class="ml-3 align-self-end">
                        <div class="<% out.append(color); %> p-2 h5 my-auto">
                            <% out.append(rating); %>
                        </div>
                    </div>
                </div>
                <div class="m-1 p-1">
                    <% out.append(rl.getGenres()); %>
                </div>
                <div class="mt-2 mb-3">
                    <a class="btn btn-dark" href="<% out.append(sb.toString()); %>">
                        See more info
                    </a>
                </div>
            </div>
		<% 
			}				
			} catch(Exception e) {
				log.logError(e.getMessage());
			} 
		%>
        </div>
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