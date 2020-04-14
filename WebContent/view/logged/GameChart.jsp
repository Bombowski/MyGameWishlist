<%@page import="mygamewishlist.view.JspFunctions"%>
<%@page import="mygamewishlist.model.pojo.MyLogger"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
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

	if (usr == null) {
		response.sendRedirect(cp.REDIRECT_LOGIN);
	}
%>

<html>
<jsp:include page="../template/Head.jsp">
	<jsp:param name="js" value="ResizeImgs" />
</jsp:include>
<body>
	<!-- añado el html del header -->
	<jsp:include page="../template/Header.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<!-- añado el html del nav -->
	<% if (usr.getAdmin() == 1) { %>
	<jsp:include page="../template/NavAdmin.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
	<% } else { %>
	<jsp:include page="../template/NavLogged.jsp">
		<jsp:param name="" value="" />
	</jsp:include>	
	<% } %>

	<main class="content-fluid p-4 mb-5">
		<div class="w-75 m-auto">
			<div style="width: 300px; height: 100px;">
			    <canvas id="ctx">
			        <script>
			            var ctx = document.getElementById("ctx").getContext('2d');
			            var chart = new Chart(ctx, {
			                type: 'line',
			                data: {
			                    labels: ["January", "February", "March", "April", "May", "June", "July"],
			                    datasets: [
			                        {
			                            fill: false,
			                            lineTension: 0.1,
			                            backgroundColor: "rgba(75, 192, 192, 0.4)",
			                            borderColor: "rgba(75, 192, 192, 1)",
			                            borderCapStyle: 'butt',
			                            borderDash: [],
			                            borderDashOffset: 0.0,
			                            borderJoinStyle: 'miter',
			                            pointBorderColor: "rgba(75,192,192,1)",
			                            pointBackgroundColor: "#fff",
			                            pointBorderWidth: 1,
			                            pointHoverRadius: 5,
			                            pointHitRadius: 10,
			                            data: [65, 59, 80, 81, 56, 55, 40]
			                        }
			                    ]
			                },                            
			                options: {
			                    scales: {
			                        yAxes: [{
			                            ticks: {
			                                beginAtZero: true
			                            }
			                        }]
			                    }
			                }
			            });
			        </script>
			    </canvas>
			</div>
		</div>
	</main>
	<jsp:include page="../template/Footer.jsp">
		<jsp:param name="" value="" />
	</jsp:include>
</body>
</html>