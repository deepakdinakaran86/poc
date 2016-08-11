"use strict";

$(document).ready(function(){
	FnInitializeGeofenceGrid();
		
});

$(window).load(function(){
	$("#geofence_id").val('');
	FnResGeofenceList(geoListresponse);
});

function FnInitializeGeofenceGrid(){
	//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewgeofence' style='text-transform: capitalize;'>#=geofenceName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=geofenceName#</a>";
	var ViewLink = "<a href='Javascript:void(0)' class='grid-viewgeofence' style='text-transform: capitalize; cursor: pointer; cursor: hand;'>#=geofenceName#</a>";
	
	var ArrColumns = [{field: "geofenceName",title: "Geofence name",template: ViewLink },{field: "type",title: "Type"},{field: "status",title: "Status"}];
	
	var ObjGridConfig = {
		"scrollable" : false,
		//"filterable" : { mode : "row" },
		"sortable" : true,
		"height" : 0,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	FnDrawGridView('#gapp-geofence-list',[],ArrColumns,ObjGridConfig);
}

function FnGetGeofenceList(){
	//$("#GBL_loading").show();
	var VarUrl ='/FMS/ajax/geo-services/1.0.0/geofence/list?domain_name={domain}';
	VarUrl = VarUrl.replace("{domain}",domainName);
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResGeofenceList);
}

function FnResGeofenceList(response){
	//$("#GBL_loading").hide();
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
	
	var ViewLink = (UserInfo.hasPermission('geofence','view')) ? "<a class='grid-viewgeofence' style='text-transform: capitalize;'>#=geofenceName#</a>" : "<a href='Javascript:void(0)' style='text-transform: capitalize;'>#=geofenceName#</a>";
	//var ViewLink ="<a class='grid-viewgeofence' style='text-transform: capitalize;'>#=geofenceName#</a>";
	//var ArrColumns = [{field: "geofenceName",title: "Geofence name",template: ViewLink },{field: "type",title: "Type"},{field: "status",title: "Status"}];
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
	
	console.log(objDatasource);
	
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
	geofencelist.bind("dataBound", FnDataBound);
		
}

var ObjSelGeofence = {};
function FnGridChangeEvent(){
	var geofencelist = $("#gapp-geofence-list").data("kendoGrid");
	if(geofencelist.select().length > 0){
		$('#gapp-geofence-delete').attr('disabled',false);
		var VarSelectedItem = geofencelist.dataItem(geofencelist.select());
		ObjSelGeofence = VarSelectedItem;
	} else {
		$('#gapp-geofence-delete').attr('disabled',true);
		ObjSelGeofence = {};
	}
}

function FnDataBound(e){
	var view = this.dataSource.view();
	var VarChkCnt = 0;
	for(var i = 0; i < view.length; i++){
		if(ObjSelGeofence.uid != undefined && ObjSelGeofence.uid === view[i].uid){
			this.tbody.find("tr[data-uid='" + view[i].uid + "']").closest("tr").addClass('k-state-selected');
			VarChkCnt++;
		}
	}
	
	if(VarChkCnt>0){
		$('#gapp-geofence-delete').attr('disabled',false);
	} else {
		$('#gapp-geofence-delete').attr('disabled',true);
	}
}

function FnCreateGeofence(){
	$('#gapp-geofence-create').submit();
}

function FnDeleteGeofence(){

	if(!$.isEmptyObject(ObjSelGeofence)){
	
		var kendoWindow = $("<div />").kendoWindow({
								title: "Confirm",
								height:120,
								width: 310,
								resizable: false,
								modal: true,
								visible: true,
							    resizable: true
							});
							
		kendoWindow.data("kendoWindow").content($("#delete-confirmation").html()).center().open();
		kendoWindow.find(".delete-confirm,.delete-cancel").click(function() {
								if ($(this).hasClass("delete-confirm")) {
									$("#GBL_loading").show();
									var VarDeleteGeofenceId = ObjSelGeofence.identifier;
									kendoWindow.data("kendoWindow").close();
									$("#geofence-delete #geofence_delete_id").val(VarDeleteGeofenceId);
									$("#geofence-delete").submit();
									//FnMakeAsyncAjaxRequest(VarUrl, 'POST', JSON.stringify(VarParam), 'application/json; charset=utf-8', 'json', FnResDeleteGeofence);
									
								} else {
									kendoWindow.data("kendoWindow").close();
								}
							}).end();
	} else {
		$("#alertModal").modal('show').find(".modalMessage").text("Please select at least one geofence.");
		return false;
	}
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



