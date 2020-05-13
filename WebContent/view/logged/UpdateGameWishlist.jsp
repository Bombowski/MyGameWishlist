<%@page import="mygamewishlist.model.pojo.db.WishListGame"%>
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
		return;
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<jsp:include page="../template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% if (usr.getAdmin() == 1) { %>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>	
	<% } %>

	<jsp:include page="../template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<%
			WishListGame wlg = (WishListGame)request.getAttribute("game");
		
			if (wlg == null) {
				response.sendRedirect(cp.REDIRECT_MYLIST);
				return;
			}
		%>
		<form action="<% out.append(cp.REDIRECT_UPDATE_GAME_WISHLIST); %>" method="post" class="d-flex flex-column">
			<div class="row justify-content-center mb-3">
				<div class="col-4 h6">
					Notify me when this game goes below or equal:
				</div>
				<div class="col-2 color-black">
					<input name="min" type="number" value="<% out.append(wlg.getMinPrice() + ""); %>"
						step="0.01" min="-1" class="form-control">
				</div>
			</div>
			<div class="row justify-content-center mb-3">
				<div class="col-4 h6">
					Notify me when this game goes above or equal:
				</div>
				<div class="col-2 color-black">
					<input name="max" type="number" value="<% out.append(wlg.getMaxPrice() + ""); %>"
						step="0.01" min="-1" class="form-control">
				</div>
			</div>
			<button type="submit" class="btn btn-dark mx-auto">
				Change
			</button>
		</form>
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