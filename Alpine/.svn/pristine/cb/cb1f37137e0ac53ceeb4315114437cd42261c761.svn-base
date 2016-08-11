<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:eval var="getUnclaimedDeviceUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.getUnclaimedDevices')" />
	
<spring:eval var="checkMarkerFieldUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.validate')" />

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="resources/js/common/asset.validation.js"></script>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<style>
.requiredAstrix {
	color: #FF3333
}
</style>
<style>
.requiredAstrix {
	color: #FF3333
}
</style>

<script type="text/javascript">

	var entityName ;
	var esn ;

	function submitBackAction() {
		$("#gensetHome").submit();
	}

	function populateUnclaimedDevice() {
		$.ajax({
			url : "ajax/" + "${getUnclaimedDeviceUrl}",
			dataType : 'json',
			type : "GET",
			success : function(response) {

				if (response.entity != null) {
					var data = $("#deviceComboBox").kendoComboBox({
						dataTextField : "sourceId",
						dataValueField : "sourceId",
						filter : "contains",
						suggest : true
					}).data().kendoComboBox;
					data.dataSource.data(response.entity);
					var deviceSelected = '${genset_form.device}';
					if (deviceSelected != '') {
						data.text(deviceSelected);
						data.value(deviceSelected);
						var combobox = $("#deviceComboBox").data(
								"kendoComboBox");
						combobox.readonly(true);
					}

				}
			},
			error : function(xhr, status, error) {
				//var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				//alert("Error in Fetching Unclaimed devices");
				var errorMessage = "Error in Fetching Unclaimed devices";
				 staticNotification.show({
					 message:errorMessage
				 }, "error");
				
			}
		});

	}

	
	function setFormValidation() {
		$("#entityName").attr('mandatory', 'Enter valid Entity Name');
		$("#entityName").attr('unique', 'Entity Name already exist');
		
		$("#esn").attr('mandatory', 'Enter valid ESN');
		$("#esn").attr('unique', 'ESN already exist');


		$("#engineModel").attr('required', '');
		$("#engineModel").attr('validationMessage', 'Enter valid Engine Model');

		$("#genset_form").kendoValidator({
			validateOnBlur : true,
			errorTemplate : "<span style='color:red'>#=message#</span>",
			rules : {
				mandatory : function(input) {

					if (input.attr('id') == 'entityName' &&  input.val()== '') {
						return false;
					}
					else if (input.attr('id') == 'esn' &&  input.val()== '') {
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
					
					else if (input.attr('id') == 'esn' && value != esn) {
						var status = checkUniqueness("${checkMarkerFieldUrl}",'esn',value);
						return status ;
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
	function submitAction() {
		$("#genset_form").submit();
	}
</script>
<div style="height: 1px;">
	<form id="gensetHome" action="genset_home" method="get"></form>
</div>

<form:form role="form" action="genset_create" commandName="genset_form"
	id="genset_form" autocomplete="off" novalidate="novalidate">
	<form:hidden path="identifier" />
	<div style="width: 60%">
		<div class="grid" style="border-width: 10px;">
			<div class="row cells2">
				<div class="cell">
					<label for="entityname">Entity Name<span
						class="requiredAstrix">*</span></label> 
					<div class="input-control text input-control text full-size">
						<form:input path="entityName" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label>Asset Type</label>
					<div class="input-control text input-control text full-size">
						<form:input path="assetType" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell">
					<label>Engine Model<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="engineModel" class="form-control" />
					</div>
				</div>

				<div class="cell">
					<label for="esn">Engine Serial Number<span class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:input path="esn" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row cells2">
				<div class="cell" style="width:50% ">
					<label>Device</label>
					<div class="input-control text input-control text " style="width:100%;">
						<form:input id="deviceComboBox" path="device" style="width: 100%;"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<div>
	<button class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;" id="genset-btn-save"
		onclick="submitAction();">Save</button>
	<button onclick="submitBackAction();"
		class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;">Back</button>
</div>

<script type="text/javascript">

    initNotifications();
	var response = '${response}';
	if(response != ''){
		 staticNotification.show({
			 message:response
		 }, "success");
	}

	if ('${action}' === 'View') {
		$("#genset_form :input").prop("disabled", true);
		$("#genset-btn-save").hide();
	} else {
		setFormValidation();
		entityName = '${genset_form.entityName}';
		esn = '${genset_form.esn}';
	}
	populateUnclaimedDevice();
</script>