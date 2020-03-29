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
			<div>
				<a href="<% out.append(cp.REDIRECT_ADD_GAME_WISHLIST); %>" class="btn btn-primary">
					Add game
				</a>
			</div>
			<table class="table">
				<tr>
					<th>Name</th>
					<th>Default price</th>
					<th>Current price</th>
					<th>Current discount</th>
					<th colspan="2">Price at which you want to get notified</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<%
					try {
						ArrayList<WishListGame> list = (ArrayList<WishListGame>) request.getAttribute("list");
						StringBuilder sb = new StringBuilder();
						
						for (WishListGame g : list) {
							Tr tr = new Tr();
							tr.addTd(new A(g.getName(),g.getUrlStore() + g.getUrlGame()));
							tr.addTd(g.getDefaultPrice() + "");
							tr.addTd(g.getCurrentPrice() + "");
							tr.addTd(g.getDiscount() + "%");
							tr.addTd(g.getMaxPrice() == -1 ? "Not specified" : ">=" + g.getMaxPrice());
							tr.addTd(g.getMinPrice() == -1 ? "Not specified" : "<=" + g.getMinPrice());
							tr.addTd(new A("Edit", 
										new StringBuilder()
											.append(cp.REDIRECT_UPDATE_GAME_WISHLIST)
											.append("?url=")
											.append(g.getUrlGame().replace("?", "%3f"))
											.toString()));
							tr.addTd(new A("Delete",
										new StringBuilder()
											.append(cp.REDIRECT_DELETE_GAME_WISHLIST)
											.append("?url=")
											.append(g.getUrlGame().replace("?", "%3f"))
											.toString()));
							
							sb.append(tr.print());
						}
						
						out.append(sb.toString());
					} catch(Exception e) {
						log.logError(e.getMessage());
						response.sendRedirect(cp.REDIRECT_MYLIST);
					}
				%>
			</table>
		</div>
	</main>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>
