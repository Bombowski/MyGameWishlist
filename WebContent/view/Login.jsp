<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%!
	JspFunctions jspF = JspFunctions.getJspF();
	MyLogger log = MyLogger.getLOG();
	ClassPaths cp = ClassPaths.getCP();
%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));

	if (usr != null) {
		response.sendRedirect(cp.REDIRECT_MYLIST);
	}
%>

<html>
<jsp:include page="template/Head.jsp">
	<jsp:param name="" value="" />
</jsp:include>
<body>
	<!-- a�ado el html del header -->
	<jsp:include page="template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	
	<!-- a�ado el html del nav -->
	<jsp:include page="template/Nav.jsp">
		<jsp:param name="" value="" />
	</jsp:include>

	<main class="content-fluid p-4">
		<div class="w-75 m-auto">
			LOGIN
		</div>
	</main>
	<jsp:include page="template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>