<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Developer"%>
<%@page import="mygamewishlist.model.pojo.db.Genre"%>
<%@page import="java.util.ArrayList"%>
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
	<%
		ArrayList<Genre> genres = (ArrayList<Genre>)request.getAttribute("genres");
		ArrayList<Developer> developers = (ArrayList<Developer>)request.getAttribute("devs");
	%>
		<form action="<% out.print(cp.REDIRECT_ADD_GAME); %>" method="post">
			<div class="row d-flex flex-column w-50 mx-auto">
				<div class="form-row my-2">
	                <div class="col-3 d-flex">
	                    <label class="ml-auto my-auto" for="name">Name</label>
	                </div>
	                <div class="col-9">
	                    <input type="text" name="name" class="col form-control">
	                </div>
	            </div>
	            <div class="form-row my-2">
	                <div class="col-3 d-flex">
	                    <label class="ml-auto my-auto" for="description">Description</label>
	                </div>
	                <div class="col-9">
	                    <input type="text" name="description" class="col form-control">
	                </div>
	            </div>
	            <div class="form-row my-2">
	                <div class="col-3 d-flex">
	                    <label class="ml-auto my-auto" for="rDate">Release Year</label>
	                </div>
	                <div class="col-9">
	                    <input type="text" name="rDate" class="col form-control">
	                </div>
	            </div>
				<div class="form-row my-2">
	                <div class="col-3 d-flex">
	                    <label class="ml-auto my-auto" for="idDev">Developer</label>
	                </div>
	                <div class="col-9 color-black">
						<select name="idDev">
							<% 
								StringBuilder sb = new StringBuilder();
							
								for (Developer dev : developers) {
									sb.append("<option value='")
										.append(dev.getId())
										.append("'>")
										.append(dev.getName())
										.append("</option>");
								}
								
								out.print(sb.toString());
							%>
						</select>
					</div>
				</div>
			</div>
			<div class="my-2 mx-auto bg-gray w-75">
                <div class="row mx-auto justify-content-center">
                    <%
						for (Genre g : genres) {
							%>
							<div class="d-flex flex-row col-2">
		                        <label class="my-auto"><% out.append(g.getName()); %></label>
		                        <input type="checkbox" name="genre" value="<% out.append(g.getId() + ""); %>" class="ml-2 my-auto">
		                    </div>
							<%
						}
						
					%>
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