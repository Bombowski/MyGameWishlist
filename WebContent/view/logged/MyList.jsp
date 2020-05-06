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

<%!JspFunctions jspF = JspFunctions.getJspF();
	MyLogger log = MyLogger.getLOG();
	ClassPaths cp = ClassPaths.getCP();%>

<%
	User usr = jspF.getLoggedUser(request.getSession(false));

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
						<div class="modal-body">
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
								ArrayList<WishListGame> list = (ArrayList<WishListGame>) request.getAttribute("list");
								@SuppressWarnings("unchecked")
								ArrayList<Store> stores = (ArrayList<Store>) request.getAttribute("stores"); 
								try {
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
								} catch(Exception e) {
									log.logError(e.getMessage());
								}
							%>
						</div>
						<div class="modal-footer color-white">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Cancel</button>
							<button type="submit" class="btn btn-primary">Generate</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="mt-2 m-auto w-75">
               <a href="/MyGameWishlist/AddGameWishlist" class="btn btn-dark mx-2 bg-black">
                   Add game
               </a>
               <button type="button" class="btn btn-dark mx-2" data-toggle="modal"
                       data-target="#priceTimeline">Price timelines
               </button>
           </div>
           <div class="d-flex justify-content-center mt-4">
		<%
			try {
				tbl = new Table();
				tbl.addClass("table");
				tbl.addClass("md-table");
				tbl.addClass("text-center");
				
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
				
				for (WishListGame g : list) {
					String discount = (Math.round(g.getDiscount() * 100f) / 100f) + "";
					String prices = discount.equals("0.0") ? g.getCurrentPrice() + "€"
							: new StringBuilder()
								.append("<s>")
								.append(g.getDefaultPrice())
								.append("€</s> ")
								.append(g.getCurrentPrice())
								.append("€").toString();

					Tr tr = new Tr();
					tr.addTd(new Img(g.getImg(), g.getGameName()));
					
					for (Store st : stores) {
						if (st.getUrl().equals(g.getUrlStore())) {
							tr.addTd(new A(new StringBuilder()
									.append(g.getGameName())
									.append(" (")
									.append(st.getName())
									.append(")").toString(), 
									g.getUrlStore() + g.getUrlGame()));
							break;
						}
					}
					
					tr.addTd(prices);
					tr.addTd(discount + "%");
					tr.addTd(g.getMaxPrice() == -1 ? "-"
							: new StringBuilder()
								.append(">=")
								.append(g.getMaxPrice() + "€")
								.toString());
					tr.addTd(g.getMinPrice() == -1 ? "-"
							: new StringBuilder()
								.append("<=")
								.append(g.getMinPrice() + "€")
								.toString());
					tr.addTd(new A(edit.print(), 
							new StringBuilder()
								.append(cp.REDIRECT_UPDATE_GAME_WISHLIST)
								.append("?url=")
								.append(g.getUrlGame()
										.replace("?", "%3f"))
								.toString()));
					tr.addTd(new A(del.print(), 
							new StringBuilder()
								.append(cp.REDIRECT_DELETE_GAME_WISHLIST)
								.append("?url=")
								.append(g.getUrlGame()
										.replace("?", "%3f"))
								.toString()));
					
					tbl.addRow(tr);
				}

				out.append(tbl.print());
			} catch (Exception e) {
				log.logError(e.getMessage());
				response.sendRedirect(cp.REDIRECT_MYLIST);
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
