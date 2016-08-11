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

function submitBackAction() {
	$("#resourceHome").submit();
}

function submitAction() {
	var roleNameList = $("#privilegeList").data("kendoMultiSelect");
	$("#permissions").val(roleNameList.value());
	$("#resource_form").submit();
}

</script>
<div style="height: 1px;">
	<form id="resourceHome" action="resource_home" method="get"></form>
</div>

<form:form role="form" action="resource_create" commandName="resource_form"
	id="resource_form" autocomplete="off" novalidate="novalidate">
	<div style="width: 60%">
		<div class="grid" style="border-width: 10px;">
			<div class="row cells2">
				<div class="cell">
					<label for="entityname">Resouce Name<span
						class="requiredAstrix">*</span></label> 
					<div class="input-control text input-control text full-size">
						<form:input path="resourceName" class="form-control" />
					</div>
				</div>
				
				<div class="cell">
					<label for="entityname">Permissions<span
						class="requiredAstrix">*</span></label>
					<div class="input-control text input-control text full-size">
						<form:hidden path="permissions" class="form-control" />
							<select id="privilegeList" multiple="multiple" data-placeholder="Select roles...">
       					</select>
					</div>
				</div>

				
			</div>
		</div>
	</div>
</form:form>
<div>
	<button class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;" id="resource-btn-save"
		onclick="submitAction();">Save</button>
	<button onclick="submitBackAction();"
		class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;">Back</button>
</div>

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
if ('${action}' === 'View') {
	$("#resource_form :input").prop("disabled", true);
	$("#resource-btn-save").hide();
} else {
	//setFormValidation();
}

function configureTagComponent() {

	var newitemtext;

	var url = "ajax" + "${deviceTagsUrl}";

	var multiselect = $("#privilegeList")
			.kendoMultiSelect(
					{
						change : function() {
							$('#tags_taglist li span:first-child').each(
									function() {
										$(this).text(
												$(this).text().replace(
														" (Add New)", ""));
									});
						},
						dataBound : function() {
							if ((newitemtext || this._prev)
									&& newitemtext != this._prev) {
								newitemtext = this._prev;

								var dataitems = this.dataSource.data();

								for (var i = 0; i < dataitems.length; i++) {
									var dataItem = dataitems[i];

									if (dataItem.value != dataItem.name) {
										this.dataSource.remove(dataItem);
									}
								}

								var found = false;
								for (var i = 0; i < dataitems.length; i++) {
									var dataItem = dataitems[i];
									if (dataItem.name == newitemtext) {
										found = true;
									}
								}

								if (!found) {
									this.open();
									this.dataSource.add({
										name : newitemtext + " (Add New)",
										name : newitemtext
									});

								}
							}
						},
						dataSource : {
							schema : {
								total : function(response) {
									return $(response).length;
								},
								model : {
									fields : {
										name : {
											type : "string",
										}
									}
								}
							}
						},
						dataTextField : "name",
						dataValueField : "name",
						animation : false
					});

	var array = [];
	


	if ('${action}' === 'View') {
		var permArray = $("#permissions").val().split(',');
		for(i = 0;i<permArray.length;i++){
			var data1 = {};
			data1.name = permArray[i];
			array.push(data1);
			}
		$("#privilegeList").data("kendoMultiSelect").dataSource.data(array);
		$("#privilegeList").data("kendoMultiSelect").value($("#permissions").val().split(','));
	} 
	else{
		var data1 = {};
		data1.name = 'Create';
		array.push(data1);
		$("#privilegeList").data("kendoMultiSelect").dataSource.data(array);
		}
}

configureTagComponent();

</script>