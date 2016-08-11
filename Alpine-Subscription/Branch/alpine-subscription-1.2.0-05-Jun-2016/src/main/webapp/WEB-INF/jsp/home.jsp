<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:eval var="deviceUrl" expression="@propertyConfigurer.getProperty('datasource.um.authenticate')" />
<spring:eval var="datasourceHost" expression="@propertyConfigurer.getProperty('datasource.services.host')" />
<spring:eval var="datasourcePort" expression="@propertyConfigurer.getProperty('datasource.services.port')" />
<spring:eval var="datasourceScheme" expression="@propertyConfigurer.getProperty('datasource.services.scheme')" />
<spring:eval var="datasourcePath" expression="@propertyConfigurer.getProperty('datasource.services.context.path')" />

<%-- <spring:url value="resources/kendo/scripts/kendo.all.min.js" var="kendoJS"/>
<script type="text/javascript" src="${kendoJS}"></script>

<spring:url value="resources/kendo/css/kendo.common.min.css" var="kendoCommonCSS"/>
<link rel="stylesheet" href="${kendoCommonCSS}">

<spring:url value="resources/kendo/css/kendo.common-bootstrap.min.css" var="kendoCommonBootstrapCSS"/>
<link rel="stylesheet" href="${kendoCommonBootstrapCSS}">

<spring:url value="resources/kendo/css/kendo.bootstrap.min.css" var="kendoBootstrapCSS"/>
<link rel="stylesheet" href="${kendoBootstrapCSS}"> --%>

<script type="text/javascript">

</script>
<h1>Welcome to home page</h1>
<!--script: to show notifications messages -->

<script id="errorTemplate" type="text/x-kendo-template">
                <div class="errorTemp">
                   <img src="resources/themes/gx/images/icons/w/error-icon.png" />            
                    <h3>#= message #</h3>
                </div>
        </script>

<script id="successTemplate" type="text/x-kendo-template">
                <div class="successTemp">
                    <img src="resources/themes/gx/images/icons/w/success-icon.png" />
                    <h3>#= message #</h3>
                </div>
            </script>

<span id="staticNotification"></span>
<div class="container" id="deviceContainer" >
	
</div>
