<%@page import="bomboshtml.body.A"%>
<%@page import="mygamewishlist.model.pojo.db.Game"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bomboshtml.body.table.Tr"%>
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
<body>
	<!-- añado el html del header -->
	<jsp:include page="../../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<!-- añado el html del nav -->
	<jsp:include page="../../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>

	<main class="content-fluid p-4 mb-5">
		<div class="w-75 m-auto">
			<div class="row m-2">
				<a class="btn btn-primary" href="/MyGameWishlist/AddGame">
					Add game
				</a>
			</div>
			<table class="table">
				<%
					try {
						@SuppressWarnings("unchecked")
						ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games"); 
						StringBuilder sb = new StringBuilder();
						
						Tr th = new Tr();
						th.addTd("Name");
						th.addTd("Description");
						sb.append(th.print());
						
						for (Game g : games) {
							Tr tr = new Tr();
							tr.addTd(g.getName());
							tr.addTd(g.getDescription() == null ? "No description" : g.getDescription());
							tr.addTd(new A("Modify","/MyGameWishlist/UpdateGame?id=" + g.getId()));
							tr.addTd(new A("Delete","/MyGameWishlist/DeleteGame?id=" + g.getId()));
							sb.append(tr.print());
						}
						
						out.print(sb.toString());
					} catch(Exception e) {
						log.logError(e.getMessage());
						response.sendRedirect(cp.REDIRECT_GAME_LIST);
					}
				%>
			</table>
		</div>
	</main>
	<jsp:include page="../../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>