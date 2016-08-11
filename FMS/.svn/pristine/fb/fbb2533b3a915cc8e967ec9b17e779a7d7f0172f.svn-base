<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<spring:url value="${contextPath}/resources/css/bootstrap/bootstrap.min.css"	var="bootstrapMINCSS" />
	<link rel="stylesheet" href="${bootstrapMINCSS}">
	
	<spring:url value="${contextPath}/resources/css/AdminLTE.min.css" var="adminLTEMINCSS" />
	<link rel="stylesheet" href="${adminLTEMINCSS}">
	
	<spring:url value="${contextPath}/resources/css/skins/_all-skins.min.css"	var="allSkinsMINCSS" />
	<link rel="stylesheet" href="${allSkinsMINCSS}">
	
	<!-- iCheck -->
	<spring:url value="${contextPath}/resources/plugins/iCheck/flat/blue.css"	var="blueCSS" />
	<link rel="stylesheet" href="${blueCSS}">
	
	<!-- font-awesome.min -->
	<spring:url value="${contextPath}/resources/fonts/font-awesome.min.css" var="fontAwesomeMINCSS" />
	<link rel="stylesheet" href="${fontAwesomeMINCSS}">

	<!-- ionicons.min -->
	<spring:url value="${contextPath}/resources/fonts/ionicons.min.css" var="ioniconsMINCSS" />
	<link rel="stylesheet" href="${ioniconsMINCSS}">
	
	<!-- FMS Theme css -->
	<spring:url value="${contextPath}/resources/plugins/kendoui/css/kendo.common.min.css"	var="kendoCommonMINCSS" />
	<link rel="stylesheet" href="${kendoCommonMINCSS}">
	<spring:url value="${contextPath}/resources/plugins/kendoui/css/kendo.material.css"	var="kendoMaterialMINCSS" />
	<link rel="stylesheet" href="${kendoMaterialMINCSS}">
	<spring:url value="${contextPath}/resources/plugins/kendoui/css/kendo.custom.css"	var="kendoCustomMINCSS" />
	<link rel="stylesheet" href="${kendoCustomMINCSS}">
	<spring:url value="${contextPath}/resources/css/styles.css" var="styleCSS" />
	<link rel="stylesheet" href="${styleCSS}">
	
	<spring:url value="${contextPath}/resources/plugins/jQuery/jQuery-2.2.0.min.js" var="jqueryJS" />
	<script src="${jqueryJS}"></script>
	
	<!-- Bootstrap 3.3.6 -->
	<spring:url value="${contextPath}/resources/js/bootstrap/bootstrap.min.js"	var="bootstrapJS" />
	<script src="${bootstrapJS}"></script>
</head>
<body class="skin-blue sidebar-mini  fixed sidebar-collapse">
	<div id="wrapper">
		<!-- Body Page -->
		<tiles:insertAttribute name="content" />
				
		<!-- kendoCAllJS css -->
		<spring:url value="${contextPath}/resources/plugins/kendoui/js/kendo.all.min.js" var="kendoAllJS" />
		<script src="${kendoAllJS}"></script>

		<!-- kendoCustomJS css -->
		<spring:url value="${contextPath}/resources/plugins/kendoui/js/kendo.custom.js" var="kendoCustomJS" />
		<script src="${kendoCustomJS}"></script>
		
		<!--commonFunctions -->
		<spring:url value="${contextPath}/resources/js/commonFunctions.js"	var="commonFunctionsJS" />
		<script src="${commonFunctionsJS}"></script>
	</div>
</body>
</html>