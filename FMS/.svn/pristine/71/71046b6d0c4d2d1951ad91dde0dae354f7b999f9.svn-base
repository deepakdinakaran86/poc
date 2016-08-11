<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.pcs.g2021.web.client.G2021AccessManager"%>

<style>
.k-datepicker {
	width: 250px !important;
}

.form-control-custom {
	height: 20px;
	padding: 6px 12px;
	font-size: 14px;
	color: #555;
	border: 1px solid #ccc;
	border-radius: 4px;
	width: 85%;
}
</style>
<body>
	<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
		<section class="dc_maincontainer">
			<div class="page-header">
				<h1>Write Back Logs</h1>
			</div>
			<div class="row">
				<div id="writeBackLabel" class="col-md-12"
					style="padding: 20px 20px;">
					<label>From</label> <input id="fromCalendar" /> <label>To</label>
					<input id="toCalendar" />
					<button id="btnView" class="btn btn-primary">View</button>
				</div>


			</div>

			<%
				if (FMSAccessManager.isSuperAdmin()) {
			%>
			<div id="splitter" style="height: 500px;">
				<div>
					<div id="tenantGrid"></div>
				</div>
				<div>
					<div id="writebackloggrid"></div>
				</div>
			</div>

			<%
				} else if (FMSAccessManager.isTenantAdmin()) {
			%>
			<div>
				<div id="writebackloggrid"></div>
			</div>
			<%
				}
			%>

			<script type="text/x-kendo-template" id="writeBackLogSearchtemplate">
            	<div class="toolbar" >
                 	<label class="category-label" for="category">Search</label>
                 	<input type="text" id="searchIdWriteBackLog" class"k-textbox" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>
            	</div>
            </script>

			<script type="text/x-kendo-template" id="tenantSearchtemplate">
            	<div class="toolbar" >
                 	<input type="text" id="searchTenantId" class="form-control-custom" placeholder="Search tenant"/>
            	</div>
            </script>

			<script>
				var tenant = '${tenant}';
				var domain = '${domain}';
				$(document)
						.ready(
								function() {
									if (tenant === '') {
										drawTenantGrid();
									}

									$(document).on('click',
											'#writebackloggrid .k-i-refresh',
											function() {
												getAllLogData();
											});

									//initialized date picker.    
									$("#fromCalendar").kendoDatePicker();
									$("#toCalendar").kendoDatePicker();
									$("button").kendoButton();

									loadDatePicker();

									//Splitter initiation from kendo         
									var splitterElement = $("#splitter")
											.kendoSplitter({
												panes : [ {
													collapsible : true,
													size : '20%'
												} ]
											/*,
											layoutChange : function(e) {
												if($('#tenantGrid').width()>0){
													$('#deviceSection').css("width", "75%");
												} else {
													$('#deviceSection').css("width", "95%");
												}
												
											}*/
											});

									var grid = $("#writebackloggrid")
											.kendoGrid(
													{
														dataSource : {
															pageSize : 20
														},
														//toolbar : kendo
														//		.template($(
														//				"#writeBackLogSearchtemplate")
														//				.html()),
														height : 470,
														sortable : true,
				    									filterable : {
															mode : "row",
															extra: false
														},
														pageable : {
															refresh : true,
															pageSizes : true,
															previousNext : true
														},
														scrollable : true,
														columns : [
																{
																	field : "parameter",
																	title : "Parameter",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Parameter<strong>"
																},
																{
																	field : "assetName",
																	title : "Source Id",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Source Id<strong>"
																},
																{
																	field : "priority",
																	title : "Priority",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Priority<strong>"
																},
																{
																	field : "status",
																	title : "Status",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Status<strong>"
																},
																{
																	field : "requestedTime",
																	title : "Requested Time",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Requested Time<strong>"
																},
																{
																	field : "errorMsg",
																	title : "Error Message",
																	width : 50,
																	editable : false,
																	headerTemplate : "<strong style='color:black ;' >Error Message<strong>"
																} ]
													});

									$("#btnView").click(function() {
										getAllLogData();
									});

									$("#searchIdWriteBackLog")
											.keyup(
													function() {
														var val = $(
																"#searchIdWriteBackLog")
																.val();
														$("#writebackloggrid")
																.data(
																		"kendoGrid").dataSource
																.filter({
																	logic : "or",
																	filters : [
																			{
																				field : "parameter",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "assetName",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "priority",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "errorMsg",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "status",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "requestedTime",
																				operator : "contains",
																				value : val
																			},
																			{
																				field : "errorMsg",
																				operator : "contains",
																				value : val
																			} ]
																});

													});

								});

				// function to load initial date selection (based on current date - 2 days)
				function loadDatePicker() {
					var today = new Date();
					var previousDay = new Date(new Date().setDate(new Date()
							.getDate() - 3));//make it 3 in the mean time      
					var endDate = kendo.toString(kendo.parseDate(today),
							'MM/dd/yyyy');
					$("#toCalendar").data("kendoDatePicker").value(endDate);

					var startDate = kendo.toString(
							kendo.parseDate(previousDay), 'MM/dd/yyyy');
					$("#fromCalendar").data("kendoDatePicker").value(startDate);
				}

				// convert local time to UTC and get in milliseconds         
				function convertTimeToUtc(localDate, addHour, addMins, addSecs) {
					var dateToConvert = new Date(localDate);
					var month = dateToConvert.getMonth(); //months from 1-12
					var day = dateToConvert.getDate();
					var year = dateToConvert.getFullYear();
					return Date.UTC(year, month, day, addHour)
				}

				// get logs data
				function getAllLogData() {

					$("#writebackloggrid").data('kendoGrid').dataSource
							.data([]);
					var getStartDate = $("#fromCalendar").data(
							"kendoDatePicker").value();
					var getEndDate = $("#toCalendar").data("kendoDatePicker")
							.value();

					if (getStartDate == null || getEndDate == null) {
						//staticNotification.show({
						//          message: "Please enter valid date."
						//     }, "error");
						
						staticNotification.show({message : "Please enter valid date."}, "error");

					}

					else {
						var getStartDate = kendo.toString($("#fromCalendar")
								.data("kendoDatePicker").value(), "MM/dd/yyyy");
						var getEndDate = kendo.toString($("#toCalendar").data(
								"kendoDatePicker").value(), "MM/dd/yyyy");

						var startTime = convertTimeToUtc(getStartDate, 0, 0, 0);
						var endTime = convertTimeToUtc(getEndDate, 23, 59, 59);

						if (startTime > endTime) {
							staticNotification.show({message : "Please enter valid date."}, "error");
							return;
						}

						var tenantGrid = $("#tenantGrid").data("kendoGrid");
						var selectedItem = undefined;
						if (tenantGrid != undefined) {
							var rows = tenantGrid.select();
							selectedItem = tenantGrid.dataItem(rows);
						}

						var targetUrl = getWbLogTargetURL(selectedItem,
								startTime, endTime);

						$
								.ajax({
									url : targetUrl,
									dataType : 'json',
									type : "POST",
									success : function(response) {
										if (response.entity == undefined) {
											var errorMessage =response.errorMessage.errorMessage;
											staticNotification.show({message : errorMessage}, "error");
										}
										createDS(response);
									},
									error : function(xhr, status, error) {
										var errorMessage = jQuery
												.parseJSON(xhr.responseText).errorMessage.errorMessage;
										staticNotification.show({message: errorMessage},"error");
										$("#writebackloggrid")
												.data('kendoGrid').dataSource
												.data([]);
									}
								});
					}

				}

				var dataSrc = [];
				// create data for grid
				function createDS(response) {

					var parameter = "";
					var assetName = "";
					var deviceName = "";
					var priority = "";
					var status = "";
					var requestedTime = "";
					var errorMsg = "";

					$
							.each(
									response.entity,
									function() {

										updatedTime = this.command.requestedAt;
										requestedTime = dt = kendo
												.toString(
														new Date(
																this.command.requestedAt),
														"G");
										status = this.command.status;
										priority = JSON
												.parse(this.command.customSpecsJSON).priority;

										parameter = this.command.customTag;
										assetName = this.device.sourceId;

										dataSrc.push({
											"parameter" : parameter,
											"assetName" : assetName,
											"deviceName" : deviceName,
											"priority" : priority,
											"status" : status,
											"requestedTime" : requestedTime,
											"errorMsg" : errorMsg
										});

									});

					loadGrid(dataSrc);
				}

				function loadGrid(ds) {
					dataSrc = [];
					//clear data
					$("#writebackloggrid").data('kendoGrid').dataSource
							.data([]);

					// refresh dataSource
					$("#writebackloggrid").data('kendoGrid').dataSource
							.data(ds);
					$('#writebackloggrid').data('kendoGrid').refresh();

				}

				function drawTenantGrid() {

					var grid = $("#tenantGrid")
							.kendoGrid(
									{
										toolbar : kendo
												.template($(
														"#tenantSearchtemplate")
														.html()),
										dataSource : {
											type : "json",
											transport : {
												read : "tenant_list"
											},
											schema : {
												data : function(response) {
													if (response.entity != null) {
														return response.entity;
													} else {
														var errorMessage = jQuery
																.parseJSON(xhr.responseText).errorMessage.errorMessage;
														staticNotification
																.show(
																		{
																			message : errorMessage
																		},
																		"error");
													}

												},
												total : function(response) {
													return $(response.entity).length;
												}
											}
										},
										height : 470,
										sortable : false,
										scrollable : true,
										columns : [
												{
													field : "identifier.value",
													title : "Tenant",
													width : 50,
													editable : false,
													headerTemplate : "<strong style='color:black ;' >Tenants<strong>"
												},

										],
										selectable : true,
										change : function(e) {
											console.log("Selection Changed");
											getAllLogData();
										}
									});

					$("#searchTenantId").keyup(function() {
						var val = $("#searchTenantId").val();
						$("#tenantGrid").data("kendoGrid").dataSource.filter({
							logic : "or",
							filters : [ {
								field : "identifier.value",
								operator : "contains",
								value : val
							} ]
						});
					});
				}

				function getWbLogTargetURL(selectedItem, startTime, endTime) {
					var currentTenant;
					var currentDomain;
					if (tenant == '') {
						if (selectedItem != undefined && selectedItem != null) {
							currentTenant = selectedItem.identifier.value;
							currentDomain = selectedItem.domain.domainName;
						} else {
							return "wblogs?start_time=" + startTime
									+ "&end_time=" + endTime;
						}

					} else {
						currentTenant = tenant;
						currentDomain = domain;
					}
					return "wblogs?tenant=" + currentTenant + "&domain="
							+ currentDomain + "&start_time=" + startTime
							+ "&end_time=" + endTime;
				}
			</script>

		</section>
	</div>

</body>
</html>