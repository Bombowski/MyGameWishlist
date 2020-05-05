<%@page import="mygamewishlist.model.pojo.db.ReviewOfGame"%>
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
			ArrayList<ReviewOfGame> reviews = null;
			ReviewOfGame rog = null;
			try {
				 g = (GameFull) request.getAttribute("game");
				 reviews = (ArrayList<ReviewOfGame>) request.getAttribute("reviews");
				 rog = (ReviewOfGame) request.getAttribute("myReview"); 
			} catch(Exception e) {
				 log.logError(e.getMessage());
				 response.sendRedirect(cp.REDIRECT_GAME_LIST);
			}
		%>
		<div class="row d-flex flex-row mb-5">
			<div class="row d-flex flex-column col-6 mx-auto">
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-3 d-flex">
	                        <span class="ml-auto my-auto" >Game Title</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <span class="form-control my-auto"><% out.append(g.getName()); %></span>
	                    </div>
                    </div>
                </div>
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-3 d-flex">
	                        <span class="ml-auto my-auto" >Developer</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <span class="form-control my-auto"><% out.append(g.getDeveloper()); %></span>
	                    </div>
                    </div>
                </div>
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-3 d-flex">
	                        <span class="ml-auto my-auto" >Realese Date</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <span class="form-control my-auto"><% out.append(g.getReleaseDate()); %></span>
	                    </div>
                    </div>
                </div>
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-3 d-flex">
	                        <span class="ml-auto my-auto" >Description</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <span class="form-control my-auto"><% out.append(g.getDescription()); %></span>
	                    </div>
                    </div>
                </div>
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-3 d-flex">
	                        <span class="ml-auto my-auto" >Genres</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <span class="form-control my-auto"><% out.append(g.getGenres()); %></span>
	                    </div>
                    </div>
                </div>
            </div>
            <%
            	if (jspF.isSome1Logged(request.getSession(false))) {
            %>
            <form method="post" action="<% out.append(cp.REDIRECT_GAME_INFO); %>" class="row d-flex flex-column col-6">
            	<div class="form-row my-2 mx-auto">
	                <span class="h4">Review</span>
	            </div>
	            <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-6 d-flex">
	                        <span class="ml-auto my-auto">Review</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <textarea name="review" class="form-control my-auto"><% out.append(jspF.ifNullEmpty(rog.getReview())); %></textarea>
	                    </div>
                    </div>
                </div>
                <div class="form-row my-2">
                	<div class="col-12 d-flex">
	                    <div class="col-6 d-flex">
	                        <span class="ml-auto my-auto">Rating</span>
	                    </div>
	                    <div class="col-6 d-flex color-black">
	                        <input class="form-control my-auto" name="rating" type="number" step='0.01' min='0' required 
	                        	value="<% out.append(rog.getRating() + ""); %>">
	                    </div>
                    </div>
                </div>
	            <div class="form-row my-2 color-black">
	                <button type="submit" class="btn button-dark mx-auto">
	                	Send review
	                </button>
	            </div>
			</form>
			<%
				} 
			%>
        </div>
        <div class="mt-5 d-flex">
        	<u class="h3 mx-auto">User reviews</u>
        </div>
        <div class="row justify-content-center">
        	<%
        		for (ReviewOfGame rev : reviews) {
        	%>
            <div class="col-xl-3 col-lg-4 col-md-5 col-sm-6 col-12 card bg-gray m-2">
            	<div class="card-body">
            		<div class="row card-header d-flex">
            			<div class="col-6 text-center my-auto">
            				<% out.append(rev.getUsrName()); %>
            			</div>
            			<div class="col-6 d-flex">
            				<span class="ml-auto bg-success h4 px-3 py-2">
            					<% out.append(rev.getRating() + ""); %>
            				</span>
            			</div>
            		</div>
           			<% out.append(jspF.ifNullEmpty(rev.getReview())); %>
           		</div>
            </div>
            <%
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