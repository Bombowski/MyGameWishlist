<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
	<title>My Game Wishlist</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="google-signin-client_id" content="757707046222-u3mrt9efs9ruo9ctsagl4vencig26s3o.apps.googleusercontent.com">
	<link href="view/imgs/logo.png" rel="shortcut icon" />
	<link href="view/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="view/css/template.css" rel="stylesheet" type="text/css" />	
	<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
	<script src="view/js/jquery-3.4.1.min.js" type="text/javascript"></script>
	<script src="view/js/SessionManagement.js" type="text/javascript" async defer></script>
	<% 
		String paths = request.getParameter("js");
		
		if (paths != null) {
			String customJs[] = paths.split(",");
			
			for (String s : customJs) {
		%>
					<script src="view/js/<% out.append(s); %>.js" type="text/javascript"></script>
		<%
			}
		}
		%>
</head>