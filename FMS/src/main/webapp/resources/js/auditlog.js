$(document).ready(function(){
FnInitializeServiceList()
})

$(window).load(function(){
  FnGetAuditList();
});

function FnInitializeServiceList(){
//var ViewLink = (ObjPageAccess['view']=='1') ? "<a class='grid-viewuser'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
var ViewLink ="<a class='grid-viewuser'>userName</a>";
	var ArrColumns = [{field: "userName",title: "User Name",width: 130 },{field: "activityTimeStamp",title: "Activity TimeStamp",width: 130},{field: "eventDomain",title: "Event Domain",width: 130},{field: "affectedModule",title: "Affected Module",width: 130},{field: "auditSummary",title: "Audit Summary",width: 130},{field: "ipAddress",title: "IP Address",width: 130},{field: "eventLocale",title: "Event Locale",width: 130}];
	var ObjGridConfig = {
		"scrollable" : false,
		"filterable" : { mode : "row" },
		"sortable" : true,
		"pageable" : { pageSize: 10,numeric: true,pageSizes: true,refresh: false },
		"selectable" : true
	};
	
	
	//FnDrawGridView('#audit-list',[],ArrColumns,ObjGridConfig);
} 
 
function FnGetAuditList(){
	$("#GBL_loading").show();
	var VarUrl = 'ajax/' +  getAuditListUrl;
	FnMakeAsyncAjaxRequest(VarUrl, 'GET', '', 'application/json; charset=utf-8', 'json', FnResAuditList);
}

function FnResAuditList(response){
	$("#GBL_loading").hide();
	var ArrResponse = response.entity;
	var VarResLength = ArrResponse.length;
	var ArrResponseData = [];
	
	if(VarResLength > 0){
		$(ArrResponse).each(function(index,val){
			var element = {};
			element['userName'] = val.userName;
			element['activityTimeStamp'] = val.activityTimeStamp;
			element['eventDomain'] = val.eventDomain;
			element['affectedModule'] = val.affectedModule;
			element['auditSummary'] = val.auditSummary;
			element['ipAddress'] = val.ipAddress;
			element['eventLocale'] = val.eventLocale;
			ArrResponseData.push(element);
		});
	}
	var ObjPageAccess = {'view':'1'};
	var ViewLink = (ObjPageAccess['view']=='1') ? "<a href='Javascript:void(0)' class='grid-viewuser' data-toggle='tooltip' title='View #=userName# details'>#=userName#</a>" : "<a href='Javascript:void(0)'>#=userName#</a>";
	
	var ArrColumns = [{field: "userName",title: "User Name",width: 130 },{field: "activityTimeStamp",title: "Activity TimeStamp",width: 130},{field: "eventDomain",title: "Event Domain",width: 130},{field: "affectedModule",title: "Affected Module",width: 130},{field: "auditSummary",title: "Audit Summary",width: 130},{field: "ipAddress",title: "IP Address",width: 130},{field: "eventLocale",title: "Event Locale",width: 130}];

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
	objDatasource['sort']={field: "userName", dir: "asc"}
	
	$(".k-grid-content").mCustomScrollbar('destroy');
	FnDrawGridView('#audit-list',objDatasource,ArrColumns,ObjGridConfig);
	

}
