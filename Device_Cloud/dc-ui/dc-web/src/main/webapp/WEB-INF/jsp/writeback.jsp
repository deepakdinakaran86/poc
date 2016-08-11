<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="deviceConfigUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.configurations')" />

<body>
	<div>
	<label style="margin-bottom:25px;margin-top:25px;" id="sourceid">Selected Device :</label><%=request.getParameter("datasourceName")%>
		<div id="write_back_grid"></div>
	</div>
	<script>
		var url = "${deviceConfigUrl}";
		sourceId = '<%=request.getParameter("sourceId")%>';
		url = url.replace("{source_id}", sourceId);
		loadPointsGrid(url);
	</script>
</body>
</html>