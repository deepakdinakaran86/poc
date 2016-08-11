<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<tiles:insertAttribute name="headTag" />
</head>
<body>
	<div style="border: #C1C1C1 solid 1px; border-radius: 10px;"  class="container" align="center">
		<!-- Header -->
		<tiles:insertAttribute name="header" />
		<!-- Menu Page -->
		<div>
			<tiles:insertAttribute name="menu" />
		</div>
		<!-- Body Page -->
		<div style="height:500px;">
			<tiles:insertAttribute name="content" />
		</div>
		<!-- Footer Page -->
		<div>
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>