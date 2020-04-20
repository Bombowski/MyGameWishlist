<%@page import="bomboshtml.body.A"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.time.LocalDate"%>
<%@page import="org.json.JSONArray"%>
<%@page import="mygamewishlist.model.pojo.db.TimelineGameDetailed"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="mygamewishlist.model.pojo.db.User"%>
<%@page import="mygamewishlist.model.pojo.ClassPaths"%>
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
	<jsp:param name="js" value="Chart" />
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
			<%
				
			%>
			<div style="width: 900px; height: 300px;">
	            <canvas id="ctx">
	                <script>
		                <%
		                @SuppressWarnings("unchecked")
		                Hashtable<String, ArrayList<TimelineGameDetailed>> list = (Hashtable<String, ArrayList<TimelineGameDetailed>>)request.getAttribute("list");
		                @SuppressWarnings("unchecked")
		                List<LocalDate> dates = (List<LocalDate>)request.getAttribute("dates");
		                @SuppressWarnings("unchecked")
		                ArrayList<String> names = (ArrayList<String>)request.getAttribute("names");
		                JSONArray labels = new JSONArray();
		                
		                for (LocalDate ld : dates) {
		                	labels.put(ld);
		                }
		                
		                if (list == null || dates == null) {
		                	response.sendRedirect(cp.REDIRECT_MYLIST);
		                }
		                %>
	                    var ctx = document.getElementById("ctx").getContext('2d');
	                    var chart = new Chart(ctx, {
	                        type: 'line',
	                        data: {
	                            labels: <% out.append(labels.toString()); %>,
	                            datasets: [
	                            <%
	                            int i = 0;
	                            for (String key : list.keySet()) {
	                            	ArrayList<TimelineGameDetailed> tmp = list.get(key);
	                            	JSONArray jsArr = new JSONArray();
	                            	
	                            	for (TimelineGameDetailed tlgd : tmp) {
	                            		jsArr.put(tlgd.getPrice() == 0 ? null : tlgd.getPrice());
	                            	}
	                            %>	                            
		                        	{
		                            	label: "<% out.print(names.get(i)); %>",
		                            	fill: false,
		                                lineTension: 0,
		                                backgroundColor: "rgba(75, 192, 192, 0.4)",
		                                borderColor: "rgba(75, 192, 192, 1)",
		                                pointBorderColor: "rgba(75,192,192,1)",
		                                pointBackgroundColor: "#fff",
		                                pointHoverRadius: 5,
		                                pointHitRadius: 10,		                          
		                        		data: <% out.append(jsArr.toString()); %>,
		                            },		                        
	                        	<%
	                        		i++;
	                            }
	                    		%>
	                    		],
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