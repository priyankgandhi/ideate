<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="/static/img/favicon.ico" TYPE="image/x-icon">
		<title><tiles:insertAttribute name="title" /></title>		
		<tiles:insertAttribute name="stylesheets" />
		<tiles:insertAttribute name="extrastylesheets" />
	</head>
	<body class="fullpage <tiles:insertAttribute name="pageId" />">
		<!-- THE PAGE HEADER -->
		<div id="pageHeader">
			<div class="navbar navbar-default">
				<div class="container">
					<tiles:insertAttribute name="header" />
				</div>		
			</div>
		</div>
		
		
		<!-- THE PAGE BODY -->
		<div id="pageBody">
			<c:if test="${!empty dangerMessage}">
			<div class="container">
				<div class="alert alert-danger" role="alert">${dangerMessage}</div>
			</div>
			</c:if>
			<div class="container">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
				
		<!-- THE PAGE FOOTER -->
		<div id="pageFooter">
 			<div class="container">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>	
		
		<!-- THE JAVASCRIPT INCLUDES -->
		<tiles:insertAttribute name="javascripts" />
		<tiles:insertAttribute name="extrajavascripts" />
	</body>
</html>