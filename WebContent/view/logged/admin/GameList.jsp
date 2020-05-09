<%@page import="mygamewishlist.model.pojo.Pagination"%>
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
	
	@SuppressWarnings("unchecked")
	ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
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
		<div class="w-75 mx-auto">
			<a class="btn btn-primary mb-4" href="/MyGameWishlist/AddGame">
				Add game
			</a>
		</div>
		<div class="col-12 row justify-content-center d-flex d-md-none">
			<%				
				Table tbl = new Table();
				tbl.addClass("table md-table text-center d-md-block d-none mx-auto bg-gray");
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
					String name = g.getName();
					String desc = g.getDescription() == null ? "No description" : g.getDescription();
					String genres = g.getGenres();
					String dev = g.getDeveloper();
					String relD = g.getReleaseDate();
					String update = new A(edit.print(),"/MyGameWishlist/UpdateGame?id=" + g.getId()).print();
					String delete = new A(del.print(),"/MyGameWishlist/DeleteGame?id=" + g.getId()).print();

					Tr tr = new Tr();
					tr.addTd(name);
					tr.addTd(desc);
					tr.addTd(genres);
					tr.addTd(dev);
					tr.addTd(relD);
					tr.addTd(update);
					tr.addTd(delete);
					
					tbl.addRow(tr);
				%>
					<div class="boxes bg-gray col-sm-5 col-12 text-center ml-sm-4 ml-0 mt-3 px-2 row">
		             	<div class="row flex-row mx-auto">
			             	<div class="col-12 m-1 mt-2">
			                     <u><span class="h4"><% out.append(name); %></span></u>
			                </div>
			                <div class="col-12 d-flex mt-3 flex-row justify-content-center">
			                    <div class="mr-3 my-auto">
			                        <% out.append(desc); %>
			                    </div>
			                </div>
			                <div class="col-12 m-1 p-1 d-flex justify-content-center">
			                	<div class="mr-3 my-auto">
			                        <% out.append(genres); %>
			                    </div>
			                </div>
			                <div class="col-12 d-flex mb-3">
			                	<div class="my-auto">
		                            <p class="m-0 p-0">Developer: </p>
			                    </div>
			                    <div class="my-auto">
		                            <% out.append(dev); %>
			                    </div>
		                	</div>
		                	<div class="col-12 d-flex mb-3">
		                		<div class="my-auto">
		                            <p class="m-0 p-0">Release date: </p>
			                    </div>    
			                    <div class="my-auto">
		                            <% out.append(relD); %>
			                    </div>
		                	</div>
		                	<div class="col-12 d-flex mb-3">    
			                    <div class="mr-auto my-auto">
		                            <% out.append(update); %>
			                    </div>
			                    <div class="ml-auto my-auto">
		                            <% out.append(delete); %>
			                    </div>
		                	</div>
		                </div>
		            </div>
					<%
						}
					%>
					</div>
					<%
				out.print(tbl.print());
				StringBuilder sb = new StringBuilder().append("<div class='mt-3 justify-content-center d-flex col-12'>");
				
				int noPags = (Integer)request.getAttribute("total");
				int pag = (Integer)request.getAttribute("pag");
				
				for (int i = 0; i < noPags; i++) {
					A a = new A((i + 1) + "", cp.REDIRECT_GAME_LIST + "?pag=" + i);					
					String color = i == pag ? "btn-primary" : "btn-dark";					
					a.addClass("btn " + color);
					
					sb.append("<div class='mx-1'>")
						.append(a.print())
						.append("</div>");
				}
				
				out.append(sb.append("<div>").toString());
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