<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
    <style>html { font-size: 14px; font-family: Arial, Helvetica, sans-serif; }</style>
    <title></title>
    <link rel="stylesheet"
	href="resources/kendo/css/kendo.common-material.min.css" />
<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />

<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/themes/default/js/kendo.all.min.js"></script>

</head>
<spring:eval var="listResourcesUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.listResouce')" />
	
<spring:eval var="getResourceUrl"
	expression="@propertyConfigurer.getProperty('cummins.services.findResouce')" />

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
	$("#roleHome").submit();
}

function submitAction() {
	var treeView = $("#treeview").data("kendoTreeView");
	var nodes = treeView.dataSource.view();
	var resources = [];
	$.each(nodes,
			function(index, object) {
		var resource = {};
		resource.resourceName = object.name;
		object.children;
		var pre = [];
		for(i=0;i<object.children._total;i++){
			var child = object.children.at(i);
			if(child.checked){
			pre.push(child.name);
				}
			}
		if(pre.length > 0){
		resource.permissions = pre;
		resources.push(resource);
		}

	});
	$('#privileges').val(JSON.stringify(resources));
	$('#action').val('${action}');
	$("#role_form").submit();
}

function getCheckedNodes(nodes,currentElem){
    var node, childCheckedNodes;
    var checkedNodes = [];

    for (var i = 0; i < nodes.length; i++) {
      node = nodes[i];
      if (node.checked && !node.ischecked) {
        checkedNodes.push(node.entity);
      }else if(!node.checked && node.ischecked){
		var index = currentElem.indexOf(JSON.stringify(node.entity));
		currentElem.splice(index,1);
	  }

      if (node.hasChildren) {
        childCheckedNodes = getCheckedNodes(node.children.view(),currentElem);
        if (childCheckedNodes.length > 0){
          checkedNodes = checkedNodes.concat(childCheckedNodes);
        }
      }

    }

    return checkedNodes;
  }

function populateResources() {
	$("#treeview").kendoTreeView({
		dataSource : hDataSource,
		dataTextField : "name",
		checkboxes : {
			template : chkTemplate,
			checkChildren: true
		},
		 check: onCheck
	});

}

var hDataSource = new kendo.data.HierarchicalDataSource(
		{
			transport : {
				read :function (options) {
					if(options.data.hasOwnProperty('id')){
						var url = "ajax" + "${getResourceUrl}";
						 url = url.replace("{group_name}",options.data.id);
						$.ajax({
							url : url,
							dataType : 'json',
							type : "GET",
							success : function(response) {

								if (response.entity != null) {
									if (response.entity.permissions != null) {
										responseData = [];
										$.each(response.entity.permissions,
												function(index, object) {
											var id = options.data.id+ "/" +object
											var node = {};
											if(prevArray.indexOf(id.toLowerCase()) != -1){
												node.checked = true;
											}
											node.hasChildren = false;
											node.id = id;
											node.name = object;
											responseData.push(node);
										});
										options.success(responseData);
									}else{
										options.success({});
										}
								}
							},
							error : function(xhr, status, error) {
								options.success({});
							}
						});
						
						}else{
							$.ajax({
								url : "ajax/" + "${listResourcesUrl}",
								dataType : 'json',
								type : "GET",
								success : function(response) {

									if (response.entity != null) {

										if (response.entity.resources != null) {
											responseData = [];
											$.each(response.entity.resources,
													function(index, object) {
												var node = {};
												node.hasChildren = true;
												node.id=object;
												node.name = object;
												responseData.push(node);
											});
											options.success(responseData);
										}else{
											options.success({});
											}
									}
								},
								error : function(xhr, status, error) {
									options.success({});
								}
							});
							}
					  
				}
			},
			schema : {
				data : function(response) {
					var responseData = [];
					if (response != null) {
						$.each(response,
										function(index, object) {
							var node = {};
							node.name = object.name;
							node.id = object.id;
							node.expanded=object.hasChildren;
							node.hasChildren=object.hasChildren;
							if(object.checked){
								node.checked = object.checked;
							}
							responseData.push(node);
										});

					return responseData;
				}
			},
			model : {
				id : "id",
				expanded:"expanded",
				hasChildren:"hasChildren",
			}

			}
		});
		


function chkTemplate(e) {
	if('${action}' === 'View'){

	if(e.item.checked){
		return "<input type='checkbox' checked='checked' disabled/>";
		}
	return "<input type='checkbox' disabled/>";
		}else{
			if(e.item.checked){
				return "<input type='checkbox' checked='checked'/>";
				}
			return "<input type='checkbox'/>";
			}
}

</script>
<div style="height: 1px;">
	<form id="roleHome" action="role_home" method="get"></form>
</div>

<form:form role="form" action="role_create" commandName="role_form"
	id="role_form" autocomplete="off" novalidate="novalidate">
	<div style="width: 60%">
		<div class="grid" style="border-width: 10px;">
			<div class="row cells2">
				<div class="cell">
					<label for="entityname">Role Name<span
						class="requiredAstrix">*</span></label> 
					<div class="input-control text input-control text full-size">
						<form:input path="roleName" class="form-control" />
						<form:hidden path="newRoleName" class="form-control" />
					</div>
					<label for="entityname">Permissions</label><div id="treeview"></div>
				</div>
				
				<div class="cell">
					
					<div class="input-control text input-control text full-size">
						<form:hidden path="privileges" class="form-control" />
						<form:hidden path="action" class="form-control" />
					</div>
				</div>

				
			</div>
		</div>
	</div>
</form:form>
<div>
	<button class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;" id="role-btn-save"
		onclick="submitAction();">Save</button>
	<button onclick="submitBackAction();"
		class="button text-shadow large-button fg-white bg-blue "
		style="height: 40px; width: 80px;">Back</button>
</div>

<script type="text/javascript">
var prevArray = [];
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
	$("#role_form :input").prop("disabled", true);
	$("#role-btn-save").hide();
	prevArray = setPrevileges();
} else if('${action}' === 'Update'){
	prevArray = setPrevileges();
	//setFormValidation();
}

var checkedNodeIds =[];
function onCheck(e) {
	var chbx = $(e.node).find('.k-checkbox input').filter(":first");
    var state = chbx.is(':checked');
    $(e.node).find(".k-group input").prop('checked', state);

    //check the dataSource elements
    $(e.node).find(".k-group li.k-item").each(function(i,v){
      e.sender.dataSource.getByUid($(v).attr('data-uid')).checked = state;
    });
}

function setPrevileges(){
	var previlegeString = $('#privileges').val()
	var previlegeJson = jQuery.parseJSON(previlegeString);
		var preArray = [];
	$.each(previlegeJson,function(index, object) {
		var array = object.permissions;
		for(i=0;i<array.length;i++){
			preArray.push((object.resourceName+"/"+array[i]).toLowerCase());
			}
	});
	return preArray;
	}
populateResources();
</script>