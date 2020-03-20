<%@page import="mygamewishlist.model.pojo.db.Game"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%!
	JspFunctions jspF = JspFunctions.getJspF();
	MyLogger log = MyLogger.getLOG();
	ClassPaths cp = ClassPaths.getCP();	
%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));

	if (usr == null) {
		response.sendRedirect(cp.REDIRECT_LOGIN);
	} else if (usr.getAdmin() != 1){
		response.sendRedirect(cp.REDIRECT_MYLIST);
	}
%>

<html>
<jsp:include page="../../template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<body>
	<!-- añado el html del header -->
	<jsp:include page="../../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<!-- añado el html del nav -->
	<jsp:include page="../../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>

	<main class="content-fluid p-4">
		<div class="w-75 m-auto">
		
			<%
				Game g = (Game)request.getAttribute("game");
			
				if (g == null) {
					response.sendRedirect(cp.GAME_LIST);
					return;
				}
			%>
		
			<form action="<% out.print(cp.REDIRECT_UPDATE_GAME); %>" method="post">
				<div class="form-row">
					<div class="col">
						<label class="" for="text">Name</label>
					</div>
					<div class="col">
						<input type="text" name="name" class="col form-control" value="<% out.print(g.getName()); %>">
					</div>
				</div>
				<div class="form-row">
					<div class="col">
						<label class="" for="description">Description</label>
					</div>
					<div class="col">
						<input type="text" name="description" class="col form-control" value="<% out.print(g.getDescription()); %>">
					</div>
				</div>
				<button type="submit" class="btn btn-primary">
					Add game
				</button>
			</form>
		</div>
	</main>
	<jsp:include page="../../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>