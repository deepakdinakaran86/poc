<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<script src="resources/javascript/commonfunctions.js"></script>
<script src="resources/javascript/configtemplate.js"></script>

<spring:eval var="getAllMake"
	expression="@propertyConfigurer.getProperty('mc.device.make')" />
<spring:eval var="getAllDeviceTypes"
	expression="@propertyConfigurer.getProperty('mc.device.deviceTypes')" />
<spring:eval var="getAllModels"
	expression="@propertyConfigurer.getProperty('mc.device.models')" />
<spring:eval var="getAllProtocols"
	expression="@propertyConfigurer.getProperty('mc.device.protocols')" />
<spring:eval var="getAllProtocolVersions"
	expression="@propertyConfigurer.getProperty('mc.device.protocolVersions')" />
<spring:eval var="getAllIOPoints"
	expression="@propertyConfigurer.getProperty('mc.protocol.points')" />
<spring:eval var="getAllPhysicalQuantity"
	expression="@propertyConfigurer.getProperty('mc.physicalquantity.getall')" />
<spring:eval var="getAllPhysicalQuantityUnit"
	expression="@propertyConfigurer.getProperty('mc.physicalquantity.unit')" />
<spring:eval var="getAllDataType"
	expression="@propertyConfigurer.getProperty('mc.system.getalldatatypes')" />
<spring:eval var="getConfigTemplateDup"
	expression="@propertyConfigurer.getProperty('mc.config.checkduplicates')" />

<style>

	.requiredAstrix 
	{
		color: #FF3333
	}
	
	input[type="text"],textarea{
	box-sizing:border-box!important;
	height:100%!important;
	}

	.ctemplateDiv{
	padding:15px 0px 10px!important;}

</style>


<script type="text/javascript">
	var deviceTypeUrl = "${getAllDeviceTypes}";
	var modelUrl = "${getAllModels}";
	var protocoleUrl = "${getAllProtocols}";
	var versionUrl = "${getAllProtocolVersions}";
	var ioPointsUrl = "${getAllIOPoints}";
	var physicalQuantityUrl = "${getAllPhysicalQuantity}";
	var unitUrl = "${getAllPhysicalQuantityUnit}";
	var dataTypeUrl = "${getAllDataType}";
	var templateDupUrl = "${getConfigTemplateDup}";
	populateMakes("${getAllMake}");
	populateDataType("${getAllDataType}");
</script>

<body>

	<div class="container0 theme-showcase dc_main" role="main"
		id="createDeviceContainer" style="margin: 0px 10px;">
		<section class="dc_maincontainer animate-panel">
			<div class="page-header">
				<h1>Create Template</h1>
			</div>

			<div style="height: 1px;">
				<form action="device_config" id="device_config" method="get"></form>
			</div>


			<div class="row">
				<div class="col-md-12">

					<form:form role="form" action="config_template_create"
						commandName="config_template_create" id="configtemplatecreate"
						method="post">
						<ul id="panelbar" id="item1" style="margin-bottom:10px;">
							<li class="k-state-active"><span
								class="k-link k-state-selected"
								style="background-color: #97E8FE !important; color: #000;">New
									Template</span>
								<div class="row ctemplateDiv">
									<div class="">
										<div class="col-md-4">
											<div class="ctemplateDivRight" style="padding: 6px;">

												<input type="hidden" value="" id="selectedPoints"
													name="selectedPoints" />


												<div class="form-group">
													<label>Template Name<span class="requiredAstrix">*</span></label>
														<form:input path="name" class="form-control dc_inputstyle dc_blockinput"  tabindex="1"  />
												</div>

												<div class="form-group">
													<label>Device <span class="requiredAstrix">*</span></label>
														<form:input id="deviceType" path="deviceType"
															class="k-input dc_kendocomboflexi form-control dc_inputstyle"  tabindex="3"/>
												</div>

												<div class="form-group">
													<label>Protocol<span class="requiredAstrix">*</span></label>
														<form:input id="protocole" path="deviceProtocol" class="dc_kendocomboflexi form-control"  tabindex="5"/>
												</div>
												

											</div>
										</div>
										<!-- ctemplateDiv left -->



										<div class="col-md-4">
											<div class="ctemplateDivRight" style="padding: 6px;">

												<div class="form-group ">
													<label>Make<span class="requiredAstrix">*</span></label>
														<form:input id="make" path="deviceMake" 
															class="k-input dc_kendocomboflexi form-control dc_inputstyle"  tabindex="2"/>
												</div>

												<div class="form-group ">
													<label>Model<span class="requiredAstrix">*</span></label>
														<form:input id="model" path="deviceModel"
															class="k-input dc_kendocomboflexi form-control dc_inputstyle" tabindex="4" />
												</div>

												<div class="form-group">
													<label>Protocol Version<span class="requiredAstrix">*</span>
													</label>
														<form:input id="version" path="deviceProtocolVersion"
															style="min-width: 100%"
															class="k-input dc_kendocomboflexi form-control dc_inputstyle" tabindex="6" />
												</div>

											</div>

										</div>
										<div class="col-md-3">
										<div class="form-group">
													<label>Description</label>
														<textarea id="description" name="description" form="configtemplatecreate" rows="5" class="form-control dc_inputstyle" tabindex="7"></textarea>
												</div>
										</div>
										<!-- ctemplateDiv right -->

									</div>

								</div></li>



							<li id="item2">
							<span class="k-link k-state-selected"
								style="background-color: #97E8FE !important; color: #000;">
									Map Device I/O</span>
								<div class="row ctemplateDiv">
												<div class="col-md-4">

													<div class="form-group">
														<label>Device I/O<span class="requiredAstrix">*</span></label>
															<form:input id="deviceIO" path="selectedDataType"
																class="dc_kendocomboflexi form-control dc_inputstyle"  tabindex="8" />
													</div>

													<div class="form-group">
														<label>Display Name<span
															class="requiredAstrix">*</span></label>
															<input id="displayName" name="displayName" type="text"
																class="form-control dc_inputstyle k-invalid" tabindex="10" />
														<span id="displayNameUniqueness" hidden style="color: red"></span>
													</div>
													<div class="form-group">
														<label>Data Type<span
															class="requiredAstrix">*</span></label>
															<form:input id="dataType" path="selectedDataType"
																class="k-input dc_kendocomboflexi dc_inputstyle form-control"  tabindex="12"/>

													</div>


												</div>

												<div class="col-md-4">

													<div class="form-group">
														<label>Physical
															Quantity<span class="requiredAstrix">*</span>
														</label>
															<form:input id="physicalQuantity"
																path="selectedPhysicalQuantity"
																class="dc_kendocomboflexi form-control dc_inputstyle"  tabindex="9" />
													</div>

													<div class="form-group">
														<label>Unit of Measurement<span
															class="requiredAstrix">*</span></label>
															<form:input id="unit" path="selectedUnit"
																class="dc_kendocomboflexi form-control dc_inputstyle" tabindex="11" />
													</div>

												</div>

											<div class="col-md-3">
											<div class="">
												<div class="form-group">
													<label >Expression</label>
														<textarea id="expression" name="expression"
															form="configtemplatecreate" rows="5"
															class="form-control " tabindex="12"></textarea>
<!-- 													<span id="expressionCheck" hidden style="color: red"></span> -->

												</div>
											</div>
											<div class="col-md-12">
												<div id="createactionsbar"
													class="gx-toolbar-action pull-right"></div>
											</div>



										</div>
										<!-- col-md-4 -->



								</div>
								<!-- ctemplateDiv --></li>
						</ul>
						
						
									
					</form:form>
				</div>
			</div>
			
			
									<div class="row">
										<section class="col-md-12">
											<div id="templateconfgrid"></div>
										</section>
										<section class="col-md-12 text-right dc_groupbtn">
											<button type="button" class="btn btn-primary"
												onClick="saveTemplate()">Save</button>
											<button type="button" class=" btn btn-default"
												onClick="cancelTempCreate()">Cancel</button>
										</section>
									</div>
									
		</section>
	</div>





	<script type="text/javascript">
	
	var grid;
	var precedenceVal = 1;
	
 	var response = '${response}';
	
  	if (response != undefined && response != '') {
  		staticNotification.show({message: response}, "success");
 	}
	
	setTemplateNameValidation();
	
	$("#displayName").keyup(function (){
		$("#displayNameUniqueness").hide();	
	});
	
// 	$("#expression").keyup(function (){
// 		$("#expressionCheck").hide();	
// 	});
	
	function addPoint() {
		
// 		var exp = document.getElementById("expression").value;
		var dispName = document.getElementById("displayName").value;
		
		if(dispName==''){
			$("#displayNameUniqueness").html('Display Name not specified.').show();
			document.getElementById("displayName").focus();
			return
 		}
//		else if(exp==''){
// 			$("#expressionCheck").html('Expression not specified').show('');
// 			document.getElementById("expression").focus();
			
// 			return
// 		}
		
		
		var data = grid.dataSource.data();
		for(item in data){
			if(data[item].displayName == dispName){
				$("#displayNameUniqueness").html('Display Name already exist.').show();
				document.getElementById("displayName").focus();
				return;
			}
		}
	 
		grid.dataSource.add({
			pointId :  $("#deviceIO").data("kendoComboBox").value(),
			pointName : $("#deviceIO").data("kendoComboBox").text(),
			displayName :  document.getElementById("displayName").value,
			physicalQuantity : $("#physicalQuantity").data("kendoComboBox").text(),
			unit : $("#unit").data("kendoComboBox").text(),
			dataType : $("#dataType").data("kendoComboBox").text(),
			expression : document.getElementById("expression").value,
			precedence : precedenceVal
		});
		
		precedenceVal++;
		$("#deviceIO").data("kendoComboBox").value('');
		$("#displayName").val(''); 		
		$("#dataType").val('');
		populateDataType("${getAllDataType}");
		clearAndDisableText("physicalQuantity");
		clearAndDisableText("unit");
		$("#expression").val('');
	};
	
	function cancelAction() {
		clearData();
	}

	$(document)
			.ready(
					function() {
										
					
					
					// panel bar config
					 $("#panelbar").kendoPanelBar({
                        expandMode: "single"
                        });
                       var panelBar = $("#panelbar").data("kendoPanelBar");
                      // panelBar.expand($("#item1"));
                       
                     //panelBar.expand($('[id^="item"]'));
                        
                       // attach activate event handler during initialization
					/* 
					$("#panelbar").kendoPanelBar({
					        activate: onActivate
					    });
					    
					 */   
					 // var onActivate = function(e) {alert('HI')};
                       
                       // $("#panel").data("kendoPanelBar").collapse($("li", "#panelbar"));
                        
                        
                        
					
						payload = {};
						grid = $("#templateconfgrid").kendoGrid({
							resizable : true,
							dataSource : {
								type : "json",
								error : function(e) {
								},
								schema : {
									data : function(response) {
										if (response.entity != null) {
											return response.entity;
										} else {

											$('#errorContainer').css({
												"color" : "#FF0000",
												"background-color" : "#FFEBE6",
												"border-color" : "#FF8566"
											});
										}
									},
									errors : function(response) {
										return response.error;
									},
									total : function(response) {
										return $(response.entity).length;
									},
									model : {
										fields : {
											pointId : {
												type : "string"
											},
											displayName : {
												type : "string"
											},
											physicalQuantity : {
												type : "string"
											},
											unit : {
												type : "string"
											},
											dataType : {
												type : "string"
											},
											expression : {
												type : "string"
											}
										}
									}
								},
								pageSize : 10,
								page : 1,
								serverPaging : false,
								serverFiltering : false,
								serverSorting : false
							},
							scrollable: {
        						virtual: true
       						},
							selectable : "multiple",
							height : 220,
							sortable : {
								mode : "single",
								allowUnsort : true
							},
							pageable : {
								refresh : true,
								pageSizes : true,
								previousNext : true
							},
							columnMenu : false,

							columns : [ {
								field : "pointId",
								title : "Device I/O",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Device I/O<strong>"
							}, {
								field : "displayName",
								title : "Display Name",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Display Name<strong>"
							}, {
								field : "dataType",
								title : "Data Type",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Data Type<strong>"
							}, {
								field : "physicalQuantity",
								title : "Physical Quantity",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Physical Quantity<strong>"
							}, {
								field : "unit",
								title : "Unit",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Unit<strong>"
							}, {
								field : "expression",
								title : "Expression",
								width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Expression<strong>"
							} ]
						}).data("kendoGrid");

						$("#createactionsbar")
								.kendoToolBar(
										{
											resizable : false,
											items : [
													{
														id : "gx-btn-create",
														type : "button",
														text : "Add",
														attributes : {
															"class" : "btn btn-primary"
														},
														click : addPoint
													},
													{
														id : "gx-btn-view",
														type : "button",
														text : "Cancel",
														attributes : {
															"class" : "btn btn-default"
														},
														click : cancelAction
													} ]
										});

					});
					
	function setTemplateNameValidation(){
		$("#name").attr('mandatory', 'Template Name not specified.');
		$("#name").attr('unique', 'Template Name already exist');
		
		$("#configtemplatecreate")
				.kendoValidator(
						{
							validateOnBlur : true,
							errorTemplate : "<span style='color:red'>#=message#</span>",
							rules : {
								mandatory : function(input) {
									if (input.attr('id') == 'name'
												&& input.val() == '') {
											return false;
									}
									return true;
								},
								unique : function(input) {
	
									if (input.attr('id') == 'name') {
										return checkUniqueness(
												"${getConfigTemplateDup}", input.val());
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
	
	function checkUniqueness(templateDupUrl,value) {
		var status = false;
		
		$.ajax({
			url : "ajax/" + templateDupUrl.replace("{temp_name}", value),
			dataType : 'json',
			type : "GET",
			async : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(response) {
				var message = response.entity;
				if (message != null && message.status == 'FALSE') {
					status = true;
				}
				return false;
			},
			error : function(xhr, status, error) {
				alert("Error in Checking Uniqueness");
				status = true;
			}
		});
		return status;
	}
	

	function saveTemplate() {
	
		var pointData = $("#templateconfgrid").data().kendoGrid.dataSource
				.data();
		var pointDataAsJSON = JSON.stringify(pointData);
		console.log("displayedDataAsJSON " + pointDataAsJSON);
		$("#selectedPoints").val(JSON.stringify(pointData));
		$("#configtemplatecreate").submit();
		
		$(".k-invalid-msg").hide();
		clearAll();
	}
	
	function clearData(){
		$("#name").val('');
		$("#make").val('');
		clearAndDisableText("deviceType");
		clearAndDisableText("model");
		clearAndDisableText("protocole");
		clearAndDisableText("version");	
		$("#description").val('');			
		clearAndDisableText("deviceIO");
		$("#displayName").val('');
		clearAndDisableText("physicalQuantity");
		clearAndDisableText("unit");
		clearAndDisableText("dataType");
		$("#expression").val('');
		populateMakes("${getAllMake}");
		populateDataType("${getAllDataType}");
	}
	
	function clearAll(){
		$("#templateconfgrid").data('kendoGrid').dataSource.data([]);
		clearData();
	}
	
	function cancelTempCreate(){
		var gridData = $('#templateconfgrid').data('kendoGrid').dataSource.data();
		
		if ('${configtemplatecreate.name}' != $('#name').val()
					|| '${configtemplatecreate.make}' != $('#make').val()
					|| '${configtemplatecreate.deviceType}' != $('#deviceType').val()
					|| '${configtemplatecreate.model}' != $('#model').val()
					|| '${configtemplatecreate.protocole}' != $('#protocole').val()
					|| '${configtemplatecreate.version}' != $('#version').val()
					|| '${configtemplatecreate.description}' != $('#description').val()
					|| '${configtemplatecreate.deviceIO}' != $('#deviceIO').val()
					|| '${configtemplatecreate.unit}' != $('#unit').val()
					|| '${configtemplatecreate.displayName}' != $('#displayName').val()
					|| '${configtemplatecreate.dataType}' != $('#dataType').val()
					|| '${configtemplatecreate.physicalQuantity}' != $('#physicalQuantity').val()
					|| gridData.length > 0)
	 				 {
	 				 
	 				 	$('#configTemplateModel').modal('show');
							$('#configTemplateModelBtn').click(function() {           
							            var getsubmitBack =$(this).attr("value");  
										if (getsubmitBack =='yestoback'){             
							            	$('#device_config').submit();
										}  
							        });	
	 				 	
	 				 	
			/*	var r = confirm("Do you want to continue?");
				if (r == true) {
					$('#device_config').submit();
				}
				*/
			} else {
				$('#device_config').submit();
			}
	}
	
</script>



<!-- Modal -->
<div class="modal fade" id="configTemplateModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Cancel</h4>
      </div>
      <div class="modal-body">  			
			Are you sure you want to leave, you will lose your data if you continue!
      </div>
      <div class="modal-footer">       
        <button type="button" class="btn btn-primary" id="configTemplateModelBtn" value="yestoback">Ok</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
      </div>
    </div>
  </div>
</div>

</body>