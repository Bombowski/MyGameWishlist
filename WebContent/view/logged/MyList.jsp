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
	<jsp:param name="js" value="ResizeImgs,MyList,bootstrap.min" />
</jsp:include>
<body>
	<!-- añado el html del header -->
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<!-- añado el html del nav -->
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

	<main class="content-fluid p-4 mb-5">
		<div class="w-75 m-auto">
			<div class="modal fade" id="priceTimeline" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">
								Generate price timelines for...
							</h5>
						</div>
						<form method="get" action="<% out.append(cp.REDIRECT_PRICE_TIMELINE); %>">
							<div class="modal-body">
								<table class="table">
									<tr>
										<th>Title</th>
										<th>Store</th>
										<th><input type="checkbox" id="chkAll"></th>
									</tr>
									<%
										@SuppressWarnings("unchecked")
										ArrayList<WishListGame> list = (ArrayList<WishListGame>) request.getAttribute("list");
										@SuppressWarnings("unchecked")
										ArrayList<Store> stores = (ArrayList<Store>) request.getAttribute("stores"); 
										try {
											StringBuilder sb = new StringBuilder();
											for (WishListGame wlg : list) {
												Tr tr = new Tr();
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
												
												sb.append(tr.print());
											}
											
											out.append(sb.toString());
										} catch(Exception e) {
											log.logError(e.getMessage());
										}
									%>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Cancel</button>
								<button type="submit" class="btn btn-primary">Generate</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div>
				<a href="/MyGameWishlist/AddGameWishlist" class="btn btn-primary">
					Add game
				</a>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#priceTimeline">Price timelines
				</button>
			</div>
			<table class="table">
				<tr>
					<th></th>
					<th>Title</th>
					<th>Current price</th>
					<th>Current discount</th>
					<th colspan="2">Price at which you want to get notified</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<%
					try {
						StringBuilder sb = new StringBuilder();

						for (WishListGame g : list) {
							String discount = (Math.round(g.getDiscount() * 100f) / 100f) + "";
							String prices = discount.equals("0.0") ? g.getCurrentPrice() + "€"
									: new StringBuilder().append("<s>").append(g.getDefaultPrice()).append("€</s> ")
											.append(g.getCurrentPrice()).append("€").toString();

							Tr tr = new Tr();
							tr.addTd(new Img(g.getImg(), g.getGameName()));
							tr.addTd(new A(g.getGameName(), g.getUrlStore() + g.getUrlGame()));
							tr.addTd(prices);
							tr.addTd(discount + "%");
							tr.addTd(g.getMaxPrice() == -1 ? ">=Not specified"
									: new StringBuilder().append(">=").append(g.getMaxPrice() + "€").toString());
							tr.addTd(g.getMinPrice() == -1 ? "<=Not specified"
									: new StringBuilder().append("<=").append(g.getMinPrice() + "€").toString());
							tr.addTd(new A("Edit", new StringBuilder().append(cp.REDIRECT_UPDATE_GAME_WISHLIST).append("?url=")
									.append(g.getUrlGame().replace("?", "%3f")).toString()));
							tr.addTd(new A("Delete", new StringBuilder().append(cp.REDIRECT_DELETE_GAME_WISHLIST)
									.append("?url=").append(g.getUrlGame().replace("?", "%3f")).toString()));

							sb.append(tr.print());
						}

						out.append(sb.toString());
					} catch (Exception e) {
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
