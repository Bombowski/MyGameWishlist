<%@page import="bomboshtml.body.Input"%>
<%@page import="java.util.Hashtable"%>
<%@page import="mygamewishlist.model.pojo.ScrapedGame"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
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
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="js" value="AddGameOptions,ResizeImgs,Checkboxes" />
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
		<form action="<% out.append(cp.REDIRECT_ADD_GAME_OPTIONS); %>" method="post" class="row flex-column">
			<div class="d-flex row col-12 mb-3">
				<div class="col-6 d-flex justify-content-start">
				<% 
					String searchParam = (String)request.getAttribute("search");
					
					searchParam = searchParam == null ? "" : searchParam;
					
					out.append("<span class='h3'>Results for: ")
						.append(searchParam)
						.append("</span>");
				%>
				</div>
				<div class="col-6 d-flex justify-content-end">
					<p class="error my-auto text-center p-1 h4"></p>
				</div>
			</div>
			<div class="mb-3 col-12 row">
				<div class="buttons col-6">
				<%
					try {
						@SuppressWarnings("unchecked")
						ArrayList<String> stores = (ArrayList<String>)request.getAttribute("stores");
						@SuppressWarnings("unchecked")
						Hashtable<String,ArrayList<ScrapedGame>> games = 
								(Hashtable<String,ArrayList<ScrapedGame>>)request.getAttribute("games"); 
						
						
						
						if (stores == null) {
							response.sendRedirect(cp.REDIRECT_MYLIST);
							return;
						}
						
						for (String str : stores) {
							if (str != null) {
								out.append("<button type='button' class='h5 btn btn-dark mr-2' id='")
									.append(str)
									.append("'>")
			                        .append(str)
			                        .append("</button>");
							}
						}
				%>
				</div>
				<div class="col-6 d-flex justify-content-end">
					<button class="btn btn-dark" type="submit">
						Add games
					</button>
				</div>
			</div>
            <div id="tables" class="col-12 row flex-row mx-auto justify-content-center">
				<%
						String noR = "No results.";
						
						for (String key : games.keySet()) {
							ArrayList<ScrapedGame> list = games.get(key);
							
							if (list == null) {
								out.append("<div id='store'></div>");
							} else {
								out.append("<div id='")
									.append(key)
									.append("tbl' class='row flex-row justify-content-center w-100'>");
								
									if (list.isEmpty()) { 
										out.append(noR);
									} else {
										int i = 0;
										for (ScrapedGame sg : list) {
											Input in = new Input("checkbox", "games", sg.getStoreName() + "&" +  i);
											in.addClass("d-none position-absolute mt-2 ml-2 align-left chkBox");
				%>											
										<div class="flex-column bg-gray col-xl-2 col-lg-3 col-md-4 col-sm-5 col-12 text-center p-0 ml-2 mt-3 checkbox-block hide-chkbox">
						                    <% out.append(in.print());%>
											<div class="row m-0 justify-content-center h-100">
												<div class="col-12 m-1 mt-2">
								                     <img src="<% out.append(sg.getImg()); %>" width="100">
								                </div>
								                <div class="col-12 m-1 mt-2">
								                     <u><a href="<% out.append(sg.getUrlStore() + sg.getUrlGame()); %>" class="h5" target="_blank">
								                     	<% out.append(sg.getFullName()); %>
								                     </a></u>
								                </div>
								                <div class="col-12 d-flex mt-3 flex-row justify-content-center">
								                    <div class="mr-3 my-auto">
								                        <% out.append(sg.getCurrentPrice() + "€"); %>
								                    </div>
								                    <div class="ml-3 my-auto align-self-end">
								                        <div class="p-2 h5 my-auto bg-success">
								                            <% out.append(sg.getCurrentDiscount() + "%"); %>
								                        </div>
								                    </div>
								                </div>
								                <div class="col-12 mt-auto mb-3">
								                	<div class="my-3">
								                		<span>Alert settings</span>
								                	</div>
								                    <div class="mr-auto">
								                        <% out.append("Lower or equal<input type='number' name='")
								        						.append(sg.getStoreName())
								        						.append("&min")
								        						.append(i + "' step='0.01' min='-1' class='form-control bg-dark border-dark rounded'>"); %>
								                    </div>
								                    <div class="mr-auto">
							                            <% out.append("Higher or equal<input type='number' name='")
															.append(sg.getStoreName())
															.append("&max")
															.append(i + "' step='0.01' min='-1' class='form-control bg-dark border-dark rounded'>"); %>
								                    </div>
								                </div>
							                </div>
							            </div>
				<%	
											i++;
										}
									}
								out.append("</div>");
							}
						}
					} catch(Exception e) {
						log.logError(e.getMessage());
						response.sendRedirect(cp.REDIRECT_MYLIST);
					}
				%>
			</div>
			<div class="col-12 row flex-column justify-content-center">
				<button class="btn btn-dark mx-auto mt-5 mb-3" type="submit">
				Add games
				</button>
				<p class="error my-auto mx-auto text-center p-1 h4"></p>
			</div>
		</form>
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