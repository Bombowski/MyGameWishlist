<%@page import="mygamewishlist.model.pojo.Pagination"%>
<%@page import="bomboshtml.body.table.Td"%>
<%@page import="bomboshtml.body.table.Table"%>
<%@page import="bomboshtml.body.Input"%>
<%@page import="mygamewishlist.model.pojo.db.Store"%>
<%@page import="bomboshtml.body.Img"%>
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
	String noGamesText = "<p class='text-center h4'>No games on your wishlist... yet.</p>";

	int pag = 0;
	try {
		pag = Integer.parseInt(request.getParameter("pag"));	
	} catch (NumberFormatException e) {
		log.logError(e.getMessage());
	}
		
	if (usr == null) {
		response.sendRedirect(cp.REDIRECT_LOGIN);
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="js" value="ResizeImgs,MyList,bootstrap.min,Checkboxes" />
</jsp:include>
<jsp:include page="../template/BodyContainerFront.jsp">
	<jsp:param name="" value="" />
</jsp:include>
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		if (usr.getAdmin() == 1) {
	%>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		} else {
	%>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<%
		}
	%>

	<jsp:include page="../template/MainFront.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
		<div class="modal fade" id="priceTimeline" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content color-black">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">
							Generate price timelines for...
						</h5>
					</div>
					<form method="post" action="<% out.append(cp.REDIRECT_PRICE_TIMELINE); %>">
						<div class="modal-body d-flex flex-column">
							<div class="col-12 d-flex flex-row">
								<div class="col-6">
									<p class="error my-0 text-center p-1"></p>
								</div>
								<div class="col-6 d-flex justify-content-end">
									<button type="submit" class="btn btn-primary ml-auto mb-2 color-white">Generate</button>
								</div>							
							</div>	
							<%
								Table tbl = new Table();
								tbl.addClass("table");
								Tr th = new Tr();
								th.addClass("h4");
								th.addTd("Title");
								th.addTd("Store");
								Input in = new Input("checkbox");
								in.setId("chkAll");
								th.addTd(in);
								
								tbl.addRow(th);
								
								@SuppressWarnings("unchecked")
								Pagination<WishListGame> list = (Pagination<WishListGame>) request.getAttribute("list");
								@SuppressWarnings("unchecked")
								ArrayList<Store> stores = (ArrayList<Store>) request.getAttribute("stores"); 
								try {
									if (list.size() == 0) {
										out.append("No games on your wishlist... yet.");
									} else {
										for (WishListGame wlg : list) {
											Tr tr = new Tr();
											tr.addClass("checkbox-block");
											
											tr.addTd(new A(wlg.getGameName(), wlg.getUrlStore() + wlg.getUrlGame()));
											for (Store st : stores) {
												if (st.getId() == wlg.getIdStore()) {
													tr.addTd(st.getName());
													break;	
												}
											}

											tr.addTd(new Input("checkbox", "games", new StringBuilder()
													.append(wlg.getUrlGame())
													.append("&")
													.append(wlg.getIdStore())
													.append("&")
													.append(wlg.getGameName())
													.toString()));
											
											tbl.addRow(tr);
										}
										
										out.append(tbl.print());										
									}
								} catch(Exception e) {
									log.logError(e.getMessage());
								}
							%>
						</div>
						<div class="modal-footer color-white d-flex flex-row">
							<div class="col-6">
								<p class="error my-0 text-center p-1"></p>
							</div>
							<div class="col-6 d-flex justify-content-end">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Cancel</button>
								<button type="submit" class="btn btn-primary ml-2">Generate</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<% if (list.size() == 0) { %>
		<div class="mt-2 mx-auto w-75 d-flex justify-content-center">
		<% } else { %>
		<div class="mt-2 mx-auto w-75">
		<% } %>
               <a href="/MyGameWishlist/AddGameWishlist" class="btn btn-dark mx-2 bg-black">
                   Add game
               </a>
               <button type="button" class="btn btn-dark mx-2" data-toggle="modal"
                       data-target="#priceTimeline">Price timelines
               </button>
           </div>
           <div class="d-flex flex-column justify-content-center mt-4 row">
               <%
                   if (list.size() == 0) {
                	   out.append(noGamesText);
                   } else {
               %>
               
               
           	   <div class="col-12 row justify-content-center d-flex d-md-none">
		<%
			try {
				tbl = new Table();
				tbl.addClass("table md-table text-center d-md-block d-none mx-auto bg-gray");
				
				th = new Tr();
				th.addClass("h5");
				th.addTd("");
				th.addTd("Title");
				th.addTd("Current Price");
				th.addTd("Current Discount");
				Td nmw = new Td("Notify me when");
				nmw.setColspan(2);
				th.addTd(nmw);
				th.addTd("Edit");
				th.addTd("Delete");
				
				tbl.addRow(th);

				Img del = new Img("view/imgs/delete.png","delete");
				del.addClass("table-icon");
				Img edit = new Img("view/imgs/edit.png","edit");
				edit.addClass("table-icon");
				
				for (WishListGame g : list.getPag(pag)) {
					String img = jspF.buildImg(g);
					String title = "";
					String discount = jspF.buildDiscount(g);
					String prices = jspF.buildPrices(discount, g);
					String maxPrice = jspF.buildMaxPrice(g);
					String minPrice = jspF.buildMinPrice(g);
					String editt = jspF.buildEdit(g, edit, cp.REDIRECT_UPDATE_GAME_WISHLIST);
					String delete = jspF.buildDelete(g, del, cp.REDIRECT_DELETE_GAME_WISHLIST);
					
					for (Store st : stores) {
						if (st.getUrl().equals(g.getUrlStore())) {
							title = jspF.buildTitle(g,st);
							break;
						}
					}
					
					Tr tr = new Tr();
					tr.addTd(img);					
					tr.addTd(title);
					tr.addTd(prices);
					tr.addTd(discount);
					tr.addTd(maxPrice);
					tr.addTd(minPrice);
					tr.addTd(editt);
					tr.addTd(delete);
					
					tbl.addRow(tr);	
				%>
					<div class="boxes bg-gray col-sm-5 col-12 text-center ml-sm-4 ml-0 mt-3 px-2 row">
		             	<div class="row flex-row mx-auto">
		             		<div class="col-12 m-1 mt-2">
			                     <% out.append(img); %>
			                </div>
			             	<div class="col-12 m-1 mt-2">
			                     <u><span class="h4"><% out.append(title); %></span></u>
			                </div>
			                <div class="col-12 d-flex mt-3 flex-row justify-content-center">
			                    <div class="mr-3 my-auto">
			                        <% out.append(prices); %>
			                    </div>
			                    <div class="ml-3 my-auto align-self-end">
			                        <div class="ml-3 my-auto align-self-end">
			                            <% out.append(discount); %>
			                        </div>
			                    </div>
			                </div>
			                <div class="col-12 m-1 p-1 text-center">
			                	Alert settings
			                </div>
			                <div class="col-12 m-1 p-1 d-flex justify-content-center">
			                	<div class="mr-3 my-auto">
			                        <% out.append(minPrice); %>
			                    </div>
			                    <div class="ml-3 my-auto">
		                            <% out.append(maxPrice); %>
			                    </div>
			                </div>
			                <div class="col-12 d-flex mb-3">
			                    <div class="mr-auto my-auto">
			                        <% out.append(editt); %>
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
					out.append(tbl.print());
				} catch (Exception e) {
					log.logError(e.getMessage());
					response.sendRedirect(cp.REDIRECT_MYLIST);
				}
				
				StringBuilder sb = new StringBuilder().append("<div class='mt-3 justify-content-center d-flex col-12'>");
				for (int i = 0; i < list.getTotalPag(); i++) {
					A a = new A((i + 1) + "", cp.REDIRECT_MYLIST + "?pag=" + i);					
					String color = i == pag ? "btn-primary" : "btn-dark";					
					a.addClass("btn " + color);
					
					sb.append("<div class='mx-1'>")
						.append(a.print())
						.append("</div>");
				}
				
				out.append(sb.append("<div>").toString());
            }
		%>
		</div>
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
