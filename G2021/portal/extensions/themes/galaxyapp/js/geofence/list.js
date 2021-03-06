"use strict";

$(document).ready(function(){
	FnGetGeofenceList();
		
});

function FnGetGeofenceList(){
	var VarUrl = GblAppContextPath+'/ajax' + VarListGeofenceUrl;
	VarUrl = VarUrl.replace("{domain}",VarCurrentTenantInfo.tenantDomain);
	//alert(VarUrl);
	FnMakeAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResGeofenceList);
}

function FnResGeofenceList(response){
	var ArrResponse = response;
	var VarResLength = ArrResponse.length;
	var ArrResponseData = [];
	if(VarResLength > 0){
		$(ArrResponse).each(function(){
			var element = {};
			$(this.dataprovider).each(function() {
				var key = this.key;
				element[key] = this.value;
			});
			element["identifier"] = this.identifier.value;
			element["status"] = this.entityStatus.statusName;
			ArrResponseData.push(element);
		});
	}
	
	var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewgeofence' style='text-transform: capitalize;'>#=geofenceName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=geofenceName#</a>";
	
	var ArrColumns = [{field: "geofenceName",title: "Geofence name",template: ViewLink },{field: "type",title: "Type"},{field: "status",title: "Status"}];
	
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	var objDatasource = {};
	objDatasource['data']=ArrResponseData;
	objDatasource['sort']={field: "geofenceName", dir: "asc"};
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#gapp-geofence-list',objDatasource,ArrColumns,ObjGridConfig);
	
	
	$("#gapp-geofence-list").data("kendoGrid").tbody.on("click", ".grid-viewgeofence", function(e) {
		var tr = $(this).closest("tr");
		var data = $("#gapp-geofence-list").data('kendoGrid').dataItem(tr);
		var VarIdentifier = data.identifier;
		$("#gapp-geofence-view #geofence_id").val(VarIdentifier);
		$("#gapp-geofence-view").submit();
	});
	
	var geofencelist = $("#gapp-geofence-list").data("kendoGrid");
	geofencelist.bind("change", FnGridChangeEvent);
		
}

function FnGridChangeEvent(){
	var geofencelist = $("#gapp-geofence-list").data("kendoGrid");
	
	if(geofencelist.select().length > 0){
		$('#gapp-geofence-delete').attr('disabled',false);
	} else {
		$('#gapp-geofence-delete').attr('disabled',true);
	}
}

function FnCreateGeofence(){
	$('#gapp-geofence-create').submit();
}

function FnDeleteGeofence(){
	
	var kendoWindow = $("<div />").kendoWindow({
				            title: "Confirm",
				            height:100,
				            width: 190,
				            resizable: false,
				            modal: true
				        });
						
	kendoWindow.data("kendoWindow").content($("#delete-confirmation").html()).center().open();
	kendoWindow.find(".delete-confirm,.delete-cancel").click(function() {
			                if ($(this).hasClass("delete-confirm")) {
								$("#GBL_loading").show();
			                	var geofencelist = $("#gapp-geofence-list").data("kendoGrid");
								var VarSelectedItem = geofencelist.dataItem(geofencelist.select());
								console.log(VarSelectedItem);
								var VarDeleteGeofenceId = VarSelectedItem.identifier;
								kendoWindow.data("kendoWindow").close();
								var VarUrl = GblAppContextPath + '/ajax' + VarDeleteGeofenceUrl;
								var VarParam = {};
								VarParam["domain"] = {"domainName" : VarCurrentTenantInfo.tenantDomain};
								VarParam["identifier"] = {"key": "identifier","value": VarDeleteGeofenceId};
								FnMakeAjaxRequest(VarUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResDeleteGeofence);
								
			                } else {
								kendoWindow.data("kendoWindow").close();
							}
			            }).end();
						
}

function FnResDeleteGeofence(response){
	var ObjResponse = response;
	$("#GBL_loading").hide();
	if(!$.isEmptyObject(ObjResponse)){
		if(ObjResponse.errorCode == undefined){
			
			notificationMsg.show({
				message : 'Geofence deleted successfully'
			}, 'success');
			
			setTimeout(function(){ location.reload(); }, GBLDelayTime);
			
		} else {
		
			if(ObjResponse.errorCode != undefined){
				FnShowNotificationMessage(ObjResponse);
			}
			
		}
	} else {
		
		notificationMsg.show({
			message : 'Error'
		}, 'error');
		
	}
}



