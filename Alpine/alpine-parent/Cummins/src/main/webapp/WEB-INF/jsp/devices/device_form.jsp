<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:eval var="checkMarkerFieldUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.validate')" />

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="resources/themes/default/js/jquery.min.js"></script>

<link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>
<script src="resources/js/common/jquery.mask.min.js"></script>
<script src="resources/js/common/asset.validation.js"></script>
<style>
.requiredAstrix {
	color: #FF3333
}
</style>

<script type="text/javascript">

	var entityName ;
	var sourceId ;
	var datasourceName ;
	initNotifications();
	var response = '${response}';
	if (response != undefined && response != '') {
		 staticNotification.show({
			 message:response
		 }, "success");
	}

	function submitAction() {
		$("#device_form").submit();
	}
	function submitBackAction() {
		$("#deviceHome").submit();
	}

	function setFormValidation() {

		$("#entityName").attr('mandatory', 'Enter valid Entity Name');
		$("#entityName").attr('unique', 'Entity Name already exist');
		
		$("#sourceId").attr('mandatory', 'Enter valid Source Id');
		$("#sourceId").attr('unique', 'Source Id already exist');

		$("#datasourceName").attr('mandatory', 'Enter valid Datasource Name');
		$("#datasourceName").attr('unique', 'Datasource Name already exist');

		$("#deviceType").attr('required', '');
		$("#deviceType").attr('validationMessage', 'Enter valid Device Type');

		$("#protocolType").attr('required', '');
		$("#protocolType").attr('validationMessage', 'Enter valid Protocol Type');

		$("#networkProtocol").attr('required', '');
		$("#networkProtocol").attr('validationMessage', 'Enter valid Network Protocol');

		$("#deviceIp").attr('required', '');
		$("#deviceIp").attr('validationMessage', 'Enter valid Device IP');
		

		$("#device_form").kendoValidator({
			
			validateOnBlur : true,
			errorTemplate : "<span style='color:red'>#=message#</span>",
			rules : {
				mandatory : function(input) {

					if (input.attr('id') == 'entityName' &&  input.val()== '') {
						return false;
					}
					else if (input.attr('id') == 'sourceId' &&  input.val()== '') {
						return false;
					}
					else if (input.attr('id') == 'datasourceName' && $('#publish').is(':checked') &&  input.val()== '') {
						return false;
					}
					return true;
				},
				unique : function(input) {

					var value = input.val();
					
					if (input.attr('id') == 'entityName' && value != entityName) {
						var status = checkUniqueness("${checkMarkerFieldUrl}",'entityName',value);
						return status ;
					}
					
					else if (input.attr('id') == 'sourceId' && value != sourceId) {
						var status = checkUniqueness("${checkMarkerFieldUrl}",'sourceId',value);
						return status ;
					}

					else if (input.attr('id') == 'datasourceName' && value != datasourceName) {
						var status = checkUniqueness("${checkMarkerFieldUrl}",'datasourceName',value);
						return status ;
					}
					return true;
				},
				mask:function(input) {

					if (input.attr('id') == 'deviceIp' && input.val() == '') {
						return false;
					}
					return true;
				}
			},
			messages : {
				unique : function(input) {
					return input.attr("unique");
				},
				mandatory : function(input) {
					return input.attr("mandatory");
				}
			}
		});
	}

	function publishChange(){

		if(!$('#publish').is(':checked')){
			$("#datasourceName").prop("disabled", true);
			$("#datasourceName").val("");
			$("#datasourceDiv").find("span.k-invalid-msg").hide();
		}
		else{
			$("#datasourceName").prop("disabled", false);
		}
	}
	
	$(document).ready(function() {
		 $('#deviceIp').mask('2ZZ.2ZZ.2ZZ.2ZZ', {
			    translation: {
			        'Z': {
			          pattern: /[0-5]/, optional: false
			        }
			        
			      }
			    });
		 
		 
		
	});
	
</script>
<div style="height: 1px;">
	<form id="deviceHome" action="device_home" method="get"></form>
</div>

<form:form role="form" action="device_create" commandName="device_form"
	id="device_form" autocomplete="off" novalidate="novalidate">
	<form:hidden path="action" />
	<form:hidden path="identifier" />
	<div style="width: 60%">
		<div class="grid" style="border-width: 10px;">
			<div class="row cells2" >
				<div class="cell">
					<label for="entityname">Entity Name<span
						class="requiredAstrix">*</span></label> 
					<div class="input-control text input-control text full-size">
						<form:input path="entityName" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Source Id<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="sourceId" id="sourceId" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Configuration</label>
					<div class="input-control text input-control text full-size">
						<form:input path="configuration" id="configuration" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Tags</label>
					<div class="input-control text input-control text full-size">
						<form:input path="tags" id="tags" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Device Type<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="deviceType" id="deviceType" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Protocol Type<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="protocolType" id="protocolType" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Network Protocol<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="networkProtocol" id="networkProtocol" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Device IP<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="deviceIp" name="deviceIp" id="deviceIp" class="form-control" validationMessage="Enter Valid Device IP"/>
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Device Port</label>
					<div class="input-control text input-control text full-size">
						<form:input path="devicePort" class="form-control" type="number" max="65535"/>
					</div>
				</div>

				<div class="cell">
					<label>WriteBack Port</label>
					<div class="input-control text input-control text full-size">
						<form:input path="writebackPort" class="form-control" type="number" max="65535"/>
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell" id="datasourceDiv">
					<label>Datasource Name</label>
					<div class="input-control text input-control text full-size">
						<form:input path="datasourceName" class="form-control" id="datasourceName"/>
					</div>
				</div>

				<div class="cell">
					<label>Publish</label>
						<form:checkbox class="form-control" path="publish" id="publish" onchange="publishChange()"/>
				</div>
			</div>
		</div>
	</div>
</form:form>
<div>
	<button class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px; "
		id="device-btn-save" onclick="submitAction();">Save</button>
	<button onclick="submitBackAction();"
		class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px; ">Back</button>
</div>

<script type="text/javascript">
	if ('${action}' === 'View') {
		$("#device_form :input").prop("disabled", true);
		$("#btn-back").prop("disabled", false);
		$("#device-btn-save").hide();
	}else {
		setFormValidation();

		entityName = '${device_form.entityName}';
		sourceId = '${device_form.sourceId}';
		datasourceName = '${device_form.datasourceName}';
		publishChange();
	}
	
</script>