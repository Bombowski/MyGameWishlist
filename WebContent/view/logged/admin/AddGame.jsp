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
	} else if (usr.getAdmin() != 1){
		response.sendRedirect(cp.REDIRECT_MYLIST);
	}
%>

<html>
<jsp:include page="../../template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<jsp:include page="../../template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="../../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="../../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="../../template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<form action="<% out.print(cp.REDIRECT_ADD_GAME); %>" method="post">
			<div class="form-row">
				<div class="col">
					<label class="" for="text">Name</label>
				</div>
				<div class="col">
					<input type="text" name="name" class="col form-control">
				</div>
			</div>
			<div class="form-row">
				<div class="col">
					<label class="" for="description">Description</label>
				</div>
				<div class="col">
					<input type="text" name="description" class="col form-control">
				</div>
			</div>
			<button type="submit" class="btn btn-primary">
				Add game
			</button>
		</form>
	<jsp:include page="../../template/MainBack.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="../../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
<jsp:include page="../../template/BodyContainerBack.jsp">
	<jsp:param name="" value="" />
</jsp:include>
</html>