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
			<%
				WishListGame wlg = (WishListGame)request.getAttribute("game");
			
				if (wlg == null) {
					response.sendRedirect(cp.REDIRECT_MYLIST);
					return;
				}
			%>
			<form action="<% out.append(cp.REDIRECT_UPDATE_GAME_WISHLIST); %>" method="post">
				<div class="form-row">
					<div class="col">
						Notify me when this game goes below or equal:
					</div>
					<div class="col">
						<input name="min" type="number" value="<% out.append(wlg.getMinPrice() + ""); %>"
							step="0.01" min="-1" class="form-control">
					</div>
				</div>
				<div class="form-row">
					<div class="col">
						Notify me when this game goes above or equal:
					</div>
					<div class="col">
						<input name="max" type="number" value="<% out.append(wlg.getMaxPrice() + ""); %>"
							step="0.01" min="-1" class="form-control">
					</div>
				</div>
				<button type="submit" class="btn btn-primary">
					Change
				</button>
			</form>
		</div>
	</main>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>