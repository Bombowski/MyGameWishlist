<%@page import="mygamewishlist.model.pojo.db.GameFull"%>
<%@page import="mygamewishlist.model.pojo.db.Developer"%>
<%@page import="mygamewishlist.model.pojo.db.Genre"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.db.Game"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.view.JspFunctions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<jsp:param name="js" value="Checkboxes" />
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
			GameFull g = (GameFull)request.getAttribute("game");
		
			if (g == null) {
				response.sendRedirect(cp.GAME_LIST);
				return;
			}
		
			@SuppressWarnings("unchecked")
			ArrayList<Genre> genres = (ArrayList<Genre>)request.getAttribute("genres");
			@SuppressWarnings("unchecked")
			ArrayList<Developer> developers = (ArrayList<Developer>)request.getAttribute("devs");
			
			out.append(jspF.getError(request));
		%>
	
		<form action="<% out.print(cp.REDIRECT_UPDATE_GAME); %>" method="post">
			<div class="d-flex justify-content-center">
				<p class="error my-0 text-center p-1 h5"></p>
			</div>
			<div class="row d-flex flex-column w-50 mx-auto">
				<div class="form-row my-2">
	                <div class="col-md-3 col-12 d-flex">
	                    <label class="ml-md-auto mr-md-0 mx-auto my-auto" for="name">Name</label>
	                </div>
	                <div class="col-md-9 col-12 color-black">
	                    <input type="text" name="name" class="col form-control" value="<% out.append(g.getName()); %>" required>
	                </div>
	            </div>
	            <div class="form-row my-2">
	                <div class="col-md-3 col-12 d-flex">
	                    <label class="ml-md-auto mr-md-0 mx-auto my-auto" for="description">Description</label>
	                </div>
	                <div class="col-md-9 col-12 color-black">
	                    <input type="text" name="description" class="col form-control" value="<% out.append(g.getDescription()); %>">
	                </div>
	            </div>
	            <div class="form-row my-2">
	                <div class="col-md-3 col-12 d-flex">
	                    <label class="ml-md-auto mr-md-0 mx-auto my-auto" for="rDate">Release Year</label>
	                </div>
	                <div class="col-md-9 col-12 color-black">
	                    <input type="date" name="rDate" class="col form-control" value="<% out.append(g.getReleaseDate()); %>" required>
	                </div>
	            </div>
				<div class="form-row mb-2">
	                <div class="col-md-3 col-12 d-flex">
	                    <label class="ml-md-auto mr-md-0 mx-auto my-auto" for="idDev">Developer</label>
	                </div>
	                <div class="col-md-9 col-12 color-black">
						<select name="idDev" class="form-control">
							<% 
								StringBuilder sb = new StringBuilder();
							
								for (Developer dev : developers) {
									if (g.getDeveloper().equals(dev.getName())) {
										sb.append("<option selected value='");
									} else {
										sb.append("<option value='");
									}
									sb.append(dev.getId())
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
                    	String[] arrStr = g.getIdGenres().split(", ");
                    	ArrayList<Integer> arr = new ArrayList<Integer>();
                    	
                    	for (String s : arrStr) {
                    		arr.add(Integer.parseInt(s));
                    	}
                    	
                    	int i = 0;
                    	String checked = "";
						for (Genre ge : genres) {
							if (arr.contains(ge.getId())) {
								checked = "checked";
							}
							%>
							<div class="d-flex flex-row col-xl-2 col-lg-3 col-md-4 col-sm-5 col-12 my-1 border-right border-white checkbox-block">
		                        <label class="my-auto ml-auto text-right"><% out.append(ge.getName()); %></label>
		                        <input type="checkbox" name="genre" value=
		                        	<% out.append("'")
		                        		.append(ge.getId() + "'")
		                        		.append(checked); %> class="ml-2 my-auto">
		                    </div>
							<%
							i++;
							checked = "";
						}						
					%>
                </div>					
			</div>
			<div class="d-flex mt-3">
				<button type="submit" class="mx-auto btn btn-dark">
					<b>Update game</b>
				</button>
			</div>
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