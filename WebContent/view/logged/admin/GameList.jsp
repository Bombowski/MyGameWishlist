<%@page import="bomboshtml.body.Img"%>
<%@page import="bomboshtml.body.table.Table"%>
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
		<div class="row m-2">
			<a class="btn btn-primary" href="/MyGameWishlist/AddGame">
				Add game
			</a>
		</div>
			<%
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
					
					Table tbl = new Table();
					tbl.addClass("table");
					Tr th = new Tr();
					th.addClass("h4");
					th.addTd("Name");
					th.addTd("Description");
					th.addTd("Genres");
					th.addTd("Developer");
					th.addTd("Release date");
					th.addTd("Edit");
					th.addTd("Delete");
					
					tbl.addRow(th);
					
					Img edit = new Img("view/imgs/edit.png");
					edit.addClass("table-icon");
					Img del = new Img("view/imgs/delete.png");
					del.addClass("table-icon");
					
					for (Game g : games) {
						Tr tr = new Tr();
						tr.addTd(g.getName());
						tr.addTd(g.getDescription() == null ? "No description" : g.getDescription());
						tr.addTd(g.getGenres());
						tr.addTd(g.getDeveloper());
						tr.addTd(g.getReleaseDate());
						tr.addTd(new A(edit.print(),"/MyGameWishlist/UpdateGame?id=" + g.getId()));
						tr.addTd(new A(del.print(),"/MyGameWishlist/DeleteGame?id=" + g.getId()));
						
						tbl.addRow(tr);
					}
					
					out.print(tbl.print());
				} catch(Exception e) {
					log.logError(e.getMessage());
					response.sendRedirect(cp.REDIRECT_GAME_LIST);
				}
			%>
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