<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getSourceParams"
	expression="@propertyConfigurer.getProperty('mc.protocol.getSourceParams')" />
<style>

div#compareBox {
    overflow: hidden;
}


div#rangeWrapper {
    position: relative;
    display: block;    
    width: 99%;
}

div#config_tabs {
    min-height: 200px;
}

.k-tabstrip .k-content.k-state-active { 
    min-height: 170px;
}

div#compareBox {
    min-height: 170px;
}
#compareBox label, input {
    margin: 14px 0;
}
</style>
<div class="container0 theme-showcase dc_main" role="main" id="mainDiv" style="margin: 0px 10px;">
		<section class="dc_maincontainer">
		
		
			<div class="page-header">
				<h1>Event Configuration</h1>
			</div>
			<span id="writebackErrorNotification"></span>
			<span id="writebackSuccessNotification"></span>
			<input type="text" value="${sourceId}" id="wbSrcId" hidden="true" />
			<form action="device_home" id="device_home" method="get" hidden="true"></form>
			<div>
				<div class="gx-toolbar-action">
					<section class="text-left pull-left" style="padding-top:12px;"><label>Selected Device:</label>${sourceId}</section>
						
				</div>
				<div class="row">
					<div class="col-md-12" />
					<div id="parameterGrid" />
				</div>
				
				<div>
				<section class="text-right dc_groupbtn">
				<button type="button" onClick="submitBackAction()"
					class="btn btn-default">Back</button>
			</section>
				</div>
			</div>
		</section>
</div>


<!-- Modal -->
<div id="popupModelRange" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Configure events</h4>
      </div>
      <div class="modal-body">
	  <div id="rangeWrapper">
	  
        	<div id="config_tabs" hidden="hidden">
			    <ul>
			        <li id="tabRange" >Range</li>
			        <li id="tabHigh" >High</li>
			        <li id="tabLow" >Low</li>
			        <li id="tabCompare" >Compare</li>
			    </ul>
			    <div>
			    	<div class="form-group" id="rangeBox"  >
					
						<span class=" col-sm-2 "><label class=" control-label" >Min<span class="requiredAstrix">*</span></label></span>
						<span class=" col-sm-10 "><input id="rangeMin" type="number" style="width:100px"class="form-control  col-sm-10 dc_inputstyle" /></span>
							
						<span class=" col-sm-2 "><label  >Max<span class="requiredAstrix">*</span></label></span>
						<span class=" col-sm-10 "><input id="rangeMax" type="number" class="form-control dc_inputstyle col-sm-10" style="width:100px"/></span>
					 </div>		
						<div class="form-group">
										<div class="col-sm-12">
										<button type="button" id="btn-set-range" class="btn btn-primary btn-lg"	onclick="saveRange()" >Set</button>
						  </div>
                
					</div>
			    </div>
			    
			    <div>
			    	<div class="form-group" id="highBox">
						<label>Max<span class="requiredAstrix">*</span></label>
						<input id="maxValue" type="number" style="width:100px" class="form-control dc_inputstyle" />
						<button type="button" id="btn-set-high" class="btn btn-primary" 	onclick="saveHigh()" >Set</button>
					</div>
			    </div>
			    
			    <div>
			    	<div class="form-group" id="lowBox">
						<label>Min<span class="requiredAstrix">*</span></label>
						<input id="minValue" type="number" style="width:100px" 	class="form-control dc_inputstyle" />
						<button type="button" id="btn-set-low" class="btn btn-primary"	onclick="saveLow()" >Set</button>
					</div>
			    </div>
			    
			    <div>
			    	<div class="form-group" id="compareBox">
						<label class="col-sm-12">Compare With<span class="requiredAstrix">*</span></label>
						<span class=" col-sm-12 "> <input id="compareValue" type="text" style="width:100px" class="form-control dc_inputstyle" /></span>
						<span class=" col-sm-12 "> <button type="button" id="btn-set-compare" class="btn btn-primary"	onclick="saveCompare()" >Set</button></span>
						
					</div>
			    </div>
			</div>
			
			</div> <!-- rangeWrapper -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>









<script type="text/x-kendo-template" id="writeBackSearchTemplate">
        <div class="toolbar" >
            <label class="category-label" for="category">Search</label>
            <input type="text" id="searchIdWriteBack" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
</script>
<script>
	var sourceId = '${sourceId}';
	var selectedPointName ;
	var response = '${response}';
	var definitions = '${definitions}';
	if(definitions != ''){
		definitions = eval(definitions);
	}
	
	$(document).ready(function () {
        loadParams();
		//addValidators();
	});
	function loadParams(){
		
		var targetUrl = "${getSourceParams}";
		targetUrl = "ajax/" + targetUrl.replace("{source_id}",sourceId);
		
		$("#parameterGrid").kendoGrid({
				height: 500,
				resizable: true,
				dataSource: {
					type: "json",
					transport: {
						read: {
							url: targetUrl,
							complete: function(){
								setConfiguredValuesToGrid();
							}
						} 					           
					},
					error : function(xhr, status, error) {
						staticNotification.show({message : "No Configurations found"}, "error");
					},
			        
					schema: {
						data: function(response) {
							if(response.entity == undefined){
								staticNotification.show({message : response.errorMessage.errorMessage}, "error");
							}
							else if (response.entity.configPoints != undefined) {
								var configPoints = response.entity.configPoints;
								$.each(configPoints,function(){
									this.pointName_low = this.pointName.toLowerCase();
								});
								return configPoints;
							} else {
								staticNotification.show({message : response.errorMessage.errorMessage}, "error");
							}
						},
						total: function(response) {
							return $(response.entity.configPoints).length;
						},
						model: {
							id : 'pointName_low'
						}
					},
					pageSize: 20,
					page: 1,
					serverPaging: false,
					serverFiltering: false,
					serverSorting: false
				},
				height: 420,
				sortable: {
					mode: "single",
					allowUnsort: true
				},
				selectable: "row",
				pageable: {
					refresh: true,
					pageSizes: true,
					previousNext: true
				},
				columnMenu: false,
				columns: [
					{ 
						field: "pointName", title: "Parameter", width: 50, editable: false, 
						headerTemplate :"<strong style='color:black ;' >Parameter<strong>" 
					},		                            
					{ 	
						field: "type", title: "Type", width: 40, editable: false, 
						headerTemplate :"<strong style='color:black ;' >Type<strong>" 
					},
					{ 	
						title: "Configured Events", width: 50, editable: false, 
						headerTemplate :"<strong style='color:black ;' >Configured Events<strong>",
						columns: [		                            	                          
							{ 
								title: "Range", width: 50, editable: false, 
								headerTemplate :"<strong style='color:black ;' >Range<strong>",
								columns: [		                            	                          
											{ 
												field: "rangeMax",
												title: "Max Value", 
												width: 60,
												headerTemplate :"<strong style='color:black ;' >Max Value<strong>"     
											},
											{ 
												field: "rangeMin",
												title: "Min Value", 
												width: 60,
												headerTemplate :"<strong style='color:black ;' >Min Value<strong>"     
											}
								]
							},
							{ 
								field: "minValue",
								title: "Low", 
								width: 60,
								headerTemplate :"<strong style='color:black ;' >Low<strong>"     
							},
							{ 
								field: "maxValue",
								title: "High", 
								width: 60,
								headerTemplate :"<strong style='color:black ;' >High<strong>"     
							},
							{ 
								field: "compareValue",
								title: "Compare", 
								width: 60,
								headerTemplate :"<strong style='color:black ;' >Compare<strong>"     
							}
						]  
					}, 
					{
				    	field: "action",
				    	title: "Action",
				    	template: "#= FnGenerateAction() #",
				    	filterable: false,
				    	sortable: false,
				    	width:130
				    }
				]
				
		}).data("kendoGrid");
		
		$("#parameterGrid").data("kendoGrid").tbody.on("click", ".grid-event-conf", function(e) {
			var tr = $(this).closest("tr");
			var data = $("#parameterGrid").data('kendoGrid').dataItem(tr);			
			$('#popupModelRange').modal({
				show: 'false'
			});
			clickOnSet(data);
		});
	}
	
	function clickOnSet(data){
		selectedPointName = data.pointName.toLowerCase();
		var type = data.type;
		var clearRange = true;
		var clearHigh = true;
		var clearLow = true;
		var clearCompare = true;
		if(definitions != '' && definitions.length >0 ){
			$.each(definitions,function(){
				if(this.parameter == selectedPointName && this.eventType == 'range'){
					$("#rangeMax").val(this.maxValue);
					$("#rangeMin").val(this.minValue);
					clearRange = false;
				}
				else if(this.parameter == selectedPointName && this.eventType == 'high'){
					$("#maxValue").val(this.maxValue);
					clearHigh = false;
				}
				else if(this.parameter == selectedPointName && this.eventType == 'low'){
					$("#minValue").val(this.minValue);
					clearLow = false;
				}
				else if(this.parameter == selectedPointName && this.eventType == 'compare'){
					$("#compareValue").val(this.compareValue);
					clearCompare = false;
				}
			});
		}
		if (clearRange){
			$("#rangeMax").val('');
			$("#rangeMin").val('');
		}
		if (clearHigh){
			$("#maxValue").val('');
		}
		if (clearLow){
			$("#minValue").val('');
		}
		if (clearCompare){
			$("#compareValue").val('');
		}
		
		if(type =='SHORT' || type =='INTEGER' || type =='DOUBLE' || type =='FLOAT' ){
			showTabs(true);
		}
		else{
			showTabs(false);
		}
		//clearValidationMessages();
	}
	
	
	function FnGenerateAction(){
		var VarActions = "<div class='diviceactions'>";
		VarActions += "<a  class='grid-event-conf'   data-target='popupModelRange' data-toggle='tooltip' data-placement='bottom' title='Configure Events'><span class='glyphicon glyphicon-cog'></span></a>";
		VarActions += "</div>";
		console.log(VarActions);			     
		return VarActions;
	}

	function addValidators(){

		$("#rangeMax").attr('mandatory', 'Max Value not specified');
		$("#rangeMin").attr('mandatory', 'Min Value not specified');

		$("#rangeMax").attr('valueCheck', 'Max Value should be greater than Min Value');
		$("#rangeMin").attr('valueCheck', 'Min Value should be less than Max Value');

		$("#maxValue").attr('mandatory', 'Max Value not specified');
		$("#minValue").attr('mandatory', 'Min Value not specified');

		$("#compareValue").attr('mandatory', 'Compare Value not specified');
		
		$("#rangeBox")
			.kendoValidator({
				validateOnBlur : true,
				errorTemplate : "<span style='color:red'>#=message#</span>",
				rules : {
					mandatory : function(input) {
						if (input.attr('id') == 'rangeMax'
							&& input.val() == '') {
							//$("#btn-set-range").attr("disabled", true);
							return false;
						}
						else if (input.attr('id') == 'rangeMin'
							&& input.val() == '') {
							//$("#btn-set-range").attr("disabled", true);
							return false;
						}
						return true;
					},

					valueCheck : function(input) {
						var rangeMin = $("#rangeMin").val();
						var rangeMax = $("#rangeMax").val();

						if(rangeMin !='' && rangeMax !='' ){
							if(rangeMax <= rangeMin){
								$("#btn-set-range").attr("disabled", true);
								return false;
							}
							$("#btn-set-range").attr("disabled", false);
							return true;
						}
						return true;
					}
				},
				messages : {
					mandatory : function(input) {
						return input.attr("mandatory");
					},
					valueCheck : function(input) {
						return input.attr("valueCheck");
					}
				}
			}
		);
		
		
		$("#highBox")
			.kendoValidator({
				validateOnBlur : true,
				errorTemplate : "<span style='color:red'>#=message#</span>",
				rules : {
					mandatory : function(input) {
						if (input.attr('id') == 'maxValue'
							&& input.val() == '') {
							$("#btn-set-high").attr("disabled", true);
							return false;
						}
						$("#btn-set-high").attr("disabled", false);
						return true;
					}
				},
				messages : {
					mandatory : function(input) {
						return input.attr("mandatory");
					}
				}
			}
		);
		
		$("#lowBox")
			.kendoValidator({
				validateOnBlur : true,
				errorTemplate : "<span style='color:red'>#=message#</span>",
				rules : {
					mandatory : function(input) {
						if (input.attr('id') == 'minValue'
							&& input.val() == '') {
							$("#btn-set-low").attr("disabled", true);
							return false;
						}
						$("#btn-set-low").attr("disabled", false);
						return true;
					}
				},
				messages : {
					mandatory : function(input) {
						return input.attr("mandatory");
					}
				}
			}
		);
		
		$("#compareBox")
			.kendoValidator({
				validateOnBlur : true,
				errorTemplate : "<span id='message' style='color:red'>#=message#</span>",
				rules : {
					mandatory : function(input) {
						if (input.attr('id') == 'compareValue'
							&& input.val() == '') {
							$("#btn-set-compare").attr("disabled", true);
							return false;
						}
						$("#btn-set-compare").attr("disabled", false);
						return true;
					}
				},
				messages : {
					mandatory : function(input) {
						return input.attr("mandatory");
					}
				}
			}
		);
	}

	function showTabs(isNumber){
		$("#config_tabs").attr("hidden", true);
		//clearValues();
		var ts = $("#config_tabs").data("kendoTabStrip");
		if(ts != undefined){
			ts.destroy();
		}
		$("#config_tabs").kendoTabStrip({
			animation:  {
					open: {
						effects: "fadeIn"
					}
				}
		});		
		ts = $("#config_tabs").data("kendoTabStrip");

		if(isNumber){
			$(ts.items()[0]).attr("style", "display:visible");
			$(ts.items()[1]).attr("style", "display:visible");
			$(ts.items()[2]).attr("style", "display:visible");
			$(ts.items()[3]).attr("style", "display:none");
			var tabToActivate = $("#tabRange");
			ts.activateTab(tabToActivate);
		}
		else{
			$(ts.items()[0]).attr("style", "display:none");
			$(ts.items()[1]).attr("style", "display:none");
			$(ts.items()[2]).attr("style", "display:none");
			$(ts.items()[3]).attr("style", "display:visible");
			var tabToActivate = $("#tabCompare");
			ts.activateTab(tabToActivate);
		}

		$("#config_tabs").attr("hidden", false);
		 
		//var tabIndex = 1;
		//ts.enable(ts.tabGroup.children("li:eq(tabIndex)"), true); // enable tab 1
		 // disable tab 1
	}

	function saveRange () {
		var saveRangeJson = {};
		saveRangeJson.sourceId = sourceId;
		saveRangeJson.maxValue = $("#rangeMax").val();
		saveRangeJson.minValue = $("#rangeMin").val();
		saveRangeJson.parameter = selectedPointName;
		saveRangeJson.eventType = "range";
		saveEventConf(saveRangeJson);
	}
	
	function saveHigh () {
		var saveHighJson = {};
		saveHighJson.sourceId = sourceId;
		saveHighJson.maxValue = $("#maxValue").val();
		saveHighJson.parameter = selectedPointName;
		saveHighJson.eventType = "high";
		saveEventConf(saveHighJson);
	}
	
	function saveLow () {
		var saveLowJson = {};
		saveLowJson.sourceId = sourceId;
		saveLowJson.minValue = $("#minValue").val();
		saveLowJson.parameter = selectedPointName;
		saveLowJson.eventType = "low";
		saveEventConf(saveLowJson);
	}
	
	function saveCompare () {
		var saveCompareJson = {};
		saveCompareJson.sourceId = sourceId;
		saveCompareJson.compareValue = $("#compareValue").val();
		saveCompareJson.parameter = selectedPointName;
		saveCompareJson.eventType = "compare";
		saveEventConf(saveCompareJson);
	}
	
	function saveEventConf(payload){
		var targetUrl = "event_conf_create";
		kendo.ui.progress($("#mainDiv"), true);			
		var data;
		$.ajax({
			url : targetUrl,
			dataType : 'json',
			contentType: "application/json; charset=utf-8",
			type : "POST",
			data: JSON.stringify(payload),
			success : function(response) {
				definitions = response;
				staticNotification.show({message : "Event Configuration Successfully Saved"}, "success");
				setConfiguredValuesToGrid();
				kendo.ui.progress($("#mainDiv"), false);						
			},
			error : function(xhr, status, error) {
				staticNotification.show({message : "Error in Saving Event Configuration"}, "error");
				kendo.ui.progress($("#mainDiv"), false);
			}
		});
		//clearValues();
	}
	
	function clearValues(){
		$("#rangeMax").val('');
		$("#rangeMin").val('');
		$("#maxValue").val('');
		$("#minValue").val('');
		$("#compareValue").val('');
	}
	
	function clearValidationMessages(){
		var validator = $("#rangeBox").data("kendoValidator");
		if(validator != undefined){
			validator.hideMessages();
		}
		validator = $("#highBox").data("kendoValidator");
		if(validator != undefined){
			validator.hideMessages();
		}
		validator = $("#lowBox").data("kendoValidator");
		if(validator != undefined){
			validator.hideMessages();
		}
		validator = $("#compareBox").data("kendoValidator");
		if(validator != undefined){
			validator.hideMessages();
		}
	}

	function setConfiguredValuesToGrid(){
		if(definitions !=undefined && definitions.length > 0){
			$.each(definitions,function(){
				var pointName = this.parameter;
				var dataItem = $("#parameterGrid").data("kendoGrid").dataSource.get(pointName);
				if(dataItem !=undefined){
					var eventType = this.eventType;
					if(eventType == 'range'){
						dataItem.set("rangeMax",this.maxValue);
						dataItem.set("rangeMin",this.minValue);
					}
					else if(eventType == 'high'){
						dataItem.set("maxValue",this.maxValue);
					}
					else if(eventType == 'low'){
						dataItem.set("minValue",this.minValue);
					}
					else if(eventType == 'compare'){
						dataItem.set("compareValue",this.compareValue);
					}
				}
			});
		}
	}
	
	function submitBackAction(){
		$("#device_home").submit();
	}
</script>