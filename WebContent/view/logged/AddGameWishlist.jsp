<%@page import="bomboshtml.body.Img"%>
<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Store"%>
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
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="js" value="Checkboxes" />
</jsp:include>
<jsp:include page="../template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% if (usr.getAdmin() == 1) { %>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>	
	<% } %>

	<jsp:include page="../template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<form action="<% out.append(cp.REDIRECT_ADD_GAME_WISHLIST); %>" method="post" class="row flex-column">
			<div class="col-lg-6 col-12 justify-content-center mx-auto">
				<div class="col-md-6 col-12 mx-auto text-center h4">
					Game title
				</div>
				<div class="col-lg-6 col-12 color-black mx-auto mt-3">
					<input class="form-control" type="text" name="name" required>
				</div>
			</div>
			<div class="col-12 row justify-content-center mx-auto mt-5">
				<div class="col-12 text-center h5">
					Stores in which you are interested
				</div>
				<div class="col-12 row flex-row justify-content-center">
					<%
						try {
							@SuppressWarnings("unchecked")
							ArrayList<Store> stores = (ArrayList<Store>) request.getAttribute("stores");
							StringBuilder sb = new StringBuilder();
							
							for (Store st : stores) {
							%>
							<div class="flex-column row col-xl-3 col-lg-4 col-md-5 col-sm-6 col-12 bg-gray my-3 mx-2 px-0 py-2 checkbox-block hide-chkbox">
							
							<img alt="storeLogo" src="view/imgs/storeImgs/<% out.append(st.getImg()); %>" width="100" class="mx-auto">
							<%
							Input in = new Input("checkbox","store",st.getName());
							in.addClass("position-absolute ml-3 d-none chkBox");
							
							out.append("<span class='text-center'>")
								.append(st.getName())
								.append("</span>")
								.append(in.print());
							%>
							</div>
							<%
							}
						} catch(Exception e) {
							log.logError(e.getMessage());
							response.sendRedirect(cp.REDIRECT_GAME_LIST);
						}
					%>
				</div>
				<button class="btn btn-dark h5" type="submit">
				 	Search
				</button>
			</div>
		</form>
		<%
			try {
				
			} catch(Exception e) {
				log.logError(e.getMessage());
			}
		%>
	<jsp:include page="../template/MainBack.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
<jsp:include page="../template/BodyContainerBack.jsp">
	<jsp:param name="" value="" />
</jsp:include>
</html>