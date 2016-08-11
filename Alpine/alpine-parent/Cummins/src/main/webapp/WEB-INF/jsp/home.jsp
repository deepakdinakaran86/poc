<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="deviceUrl"
	expression="@propertyConfigurer.getProperty('datasource.um.authenticate')" />
<spring:eval var="datasourceHost"
	expression="@propertyConfigurer.getProperty('datasource.services.host')" />
<spring:eval var="datasourcePort"
	expression="@propertyConfigurer.getProperty('datasource.services.port')" />
<spring:eval var="datasourceScheme"
	expression="@propertyConfigurer.getProperty('datasource.services.scheme')" />
<spring:eval var="datasourcePath"
	expression="@propertyConfigurer.getProperty('datasource.services.context.path')" />

<%-- <spring:url value="resources/kendo/scripts/kendo.all.min.js" var="kendoJS"/>
<script type="text/javascript" src="${kendoJS}"></script>

<spring:url value="resources/kendo/css/kendo.common.min.css" var="kendoCommonCSS"/>
<link rel="stylesheet" href="${kendoCommonCSS}">

<spring:url value="resources/kendo/css/kendo.common-bootstrap.min.css" var="kendoCommonBootstrapCSS"/>
<link rel="stylesheet" href="${kendoCommonBootstrapCSS}">

<spring:url value="resources/kendo/css/kendo.bootstrap.min.css" var="kendoBootstrapCSS"/>
<link rel="stylesheet" href="${kendoBootstrapCSS}"> --%>

<script type="text/javascript">
initNotifications();
var response = '${response}';
if(response != ''){
var type = "success";
	if(response != "SUCCESS"){
		type = "error";
	}
	 staticNotification.show({
		 message:response
	 }, type);
}
var context = '<%=request.getContextPath()%>';
	var deviceHome = function(){
		document.forms[0].action=context+"/device_home";
		document.forms[0].method="GET";
		document.forms[0].submit();
	};
	
	var userHome = function(){
		document.forms[0].action = context + "/user_home";
		document.forms[0].method = "GET";
		document.forms[0].submit();
	};

	var gensetHome = function(){
		document.forms[0].action=context+"/genset_home";
		document.forms[0].method="GET";
		document.forms[0].submit();
	};
	
	var generatorHome = function(){
		document.forms[0].action=context+"/generator_home";
		document.forms[0].method="GET";
		document.forms[0].submit();
	};

	var commuterHome = function() {
		document.forms[0].action = context + "/commuter_home";
		document.forms[0].method = "GET";
		document.forms[0].submit();
	};
</script>
<form style="background: transparent;" method="POST"></form>
<div class="tile-container" style="height: 100%;background: url('resources/images/home/bg.jpg');padding: 65px;" align="center">
	<div class="tile-large bg-orange fg-white"
		style="height: 330px; width: 310px;" onclick="userHome()">
		<div class="tile-content slide-left">
			<div class="slide">
				<div class="image-container image-format-hd"
					style="width: 100%; height: 100%">
					<div class="frame" style="background: transparent;">
						<div
							style="position:inherit; width: 80%; height: 85%; border-radius: 0px; background-image: url(resources/images/users/users.png); background-size: cover; background-repeat: no-repeat;">
						</div>
					</div>
				</div>
			</div>
			<div class="slide-over op-green text-small padding10">
				Description Goes Here
			</div>
			<div class="tile-label large"><h1>Users</h1></div>
		</div>
	</div>

<div class="tile-large fg-white"
		style="height: 330px; width: 415px;background-color: #0F5796" onclick="gensetHome()">
		<div class="tile-content slide-down">
			<div class="slide">
				<div class="image-container image-format-hd"
					style="width: 100%; height: 100%">
					<div class="frame" style="background: transparent;">
						<div
							style="position:inherit; width: 415px; height: 95%; border-radius: 0px; background-image: url(resources/images/gensets/gensets.png); background-size: contain; background-repeat: no-repeat;">
						</div>
					</div>
				</div>
			</div>
			<div class="slide-over op-gray text-small padding10">
				Description Goes Here
			</div>
			<div class="tile-label large"><h1>Gensets</h1></div>
		</div>
	</div>


	<div class="tile-large bg-green fg-white"
		style="height: 330px; width: 310px;" onclick="commuterHome()">
		<div class="tile-content slide-up">
			<div class="slide">
				<div class="image-container image-format-hd"
					style="width: 100%; height: 100%">
					<div class="frame" style="background: transparent;">
						<div
							style="position:inherit; width: 100%; height: 95%; border-radius: 0px; background-image: url(resources/images/commuter/commuters.png); background-size: cover; background-repeat: no-repeat;">
						</div>
					</div>
				</div>
			</div>
			<div class="slide-over op-red text-small padding10">
				Description Goes Here
			</div>
			<div class="tile-label large"><h1>Commuters</h1></div>
		</div>
	</div>

	<div class="tile-large bg-yellow fg-white"
		style="height: 330px; width: 390px" onclick="generatorHome()">
		<div class="tile-content slide-right">
			<div class="slide">
				<div class="image-container image-format-hd"
					style="width: 100%; height: 100%">
					<div class="frame" style="background: transparent;">
						<div
							style="position:inherit; width: 395px; height: 295px; border-radius: 0px; background-image: url(resources/images/generators/generators.png); background-size: cover; background-repeat: no-repeat;">
						</div>
					</div>
				</div>
			</div>
			<div class="slide-over op-orange text-small padding10">
				Description Goes Here	
			</div>
			<div class="tile-label large"><h1>Generators</h1></div>
		</div>
	</div>

	<div class="tile-wide bg-white fg-Black" style="width: 99%;" onclick="deviceHome()">
		<div class="tile-content slide-up">
			<div class="slide">
				<div class="image-container image-format-hd"
					style="width: 100%; height: 100%">
					<div class="frame" style="background: transparent;">
						<div
							style="position:inherit; width: 98%; height: 90%; border-radius: 0px; background-image: url(resources/images/commuter/commuters.png); background-size: cover; background-repeat: no-repeat;">
						</div>
					</div>
				</div>
			</div>
			<div class="slide-over op-blue text-small padding10">
				Description Goes Here
			</div>
			<div class="tile-label large"><h1>Devices</h1></div>
		</div>
	</div>
</div>
