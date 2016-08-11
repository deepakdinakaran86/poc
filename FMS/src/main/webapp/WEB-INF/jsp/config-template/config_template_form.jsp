<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style type='text/css'>
	html,
    body
    {
        height:100%;
        overflow:hidden;
    }

    #verticalsplitter
    {
        height:600px;
        border:0px;
    }
    
	#templategrid .k-virtual-scrollable-wrap td 
	{
  		overflow: auto;  
  		text-overflow:initial;
    }
      
</style>

<script type="text/javascript">
	var grid ;
	var tempJson = ${template};
	var tempConfigPoints = tempJson.configPoints;
	$(document).ready(function() {
			$("#verticalsplitter").kendoSplitter({
				orientation: "vertical",
				panes: [
					{ collapsible: true },
					{ collapsible: false, resizable: false, size: "65%" }
				],
				expand: onExpand,
				collapse:onCollapse
		});

		function onExpand(e) {
			$(".config-title-style").hide(600);
			$("#templategrid").height(250);
		}
		
		function onCollapse(e) {
			$(".config-title-style").show(200);
			$('#temp-name-value').html($('#name').val());
			$('#device-name-value').html($('#deviceMake').val());
			$('#device-type-value').html($('#deviceType').val());
			$("#templategrid").height(420);	
					  
		}

		payload = {};
		grid = $("#templategrid").kendoGrid({
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
							pointName : {
								type : "string"
							},
							displayName : {
								type : "string"
							},
							type : {
								type : "string"
							},
							unit : {
								type : "string"
							},
							physicalQuantity : {
								type : "string"
							},
							expression : {
								type : "string"
							}
// 							,
// 							precedence : {
// 								type : "string"
// 							}
						}
					}
				},
				pageSize : 10,
				page : 1,
				serverPaging : false,
				serverFiltering : false,
				serverSorting : false
			},
			selectable : "multiple",
 			height:250,
			sortable : {
				mode : "single",
				allowUnsort : true
			},
			pageable : {
				refresh : true,
				pageSizes : true,
				previousNext : true
			},
			scrollable: {
        		virtual: false
       		},
			columnMenu : false,

			columns : [ {
				field : "pointName",
				title : "Point Name",
				width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Point Name<strong>"
			}, {
				field : "displayName",
				title : "Display Name",
				width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Display Name<strong>"
			}, {
				field : "type",
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
// 			}, {
// 				field : "precedence",
// 				title : "Precedence",
// 				width: 50, editable: false, headerTemplate :"<strong style='color:black ;' >Precedence<strong>"
			} ]
		}).data("kendoGrid");
		
		addConfigPoint(tempConfigPoints);
	});
	
	var addConfigPoint = function(configPoints){
	if (typeof configPoints != 'undefined') {
					$.each(configPoints,
							function(i) {
								grid.dataSource.add({
						pointName : configPoints[i].point_name,
						displayName : configPoints[i].display_name,
						type : configPoints[i].type,
						physicalQuantity : configPoints[i].physical_quantity,
						unit : configPoints[i].unit,
						expression : configPoints[i].expression,
						precedence : configPoints[i].precedence
					});
							});
				}
	}
	
	function submitBackAction(){
		$("#device_config").submit();
	}
	
</script>
<body>

	<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">
		<section class="dc_maincontainer animate-panel">
			<div class="page-header">
				<h1>All Templates</h1>
			</div>
			<div class="config-title-style" style="display:none;">
				<label>Template Name:</label><span id="temp-name-value"></span> 
				<label>Device Make:</label> <span id="device-name-value"></span>
				<label>Device Type:</label> <span id="device-type-value"></span>
			</div>
			<div style="height: 1px;" >
				<form action="device_config" id="device_config" method="get"></form>
			</div>

			<form:form role="form" action="config_template_view"
				commandName="config_template_form" id="config_template_form"
				method="post">
				<%-- <form:hidden path="identifier" /> --%>
				<div id="verticalsplitter">
				<div class="row">
					<div class="col-md-4">
							<div class="form-group">
								<label>Template Name</label>
									<form:input path="name" class="form-control dc_inputstyle" readonly="true"/>
							</div>

							<div class="form-group">
								<label>Device Make</label>
									<form:input path="deviceMake" class="form-control dc_inputstyle" readonly="true"/>
							</div>
							<div class="form-group">
								<label>Device Type</label> 
									<form:input path="deviceType" class="form-control dc_inputstyle" readonly="true"/>
							</div>
					</div>
					<div class="col-md-4">
							<div class="form-group">
								<label>Device Model</label>
									<form:input path="deviceModel" class="form-control dc_inputstyle" readonly="true"/>
							</div>
							<div class="form-group">
								<label>Device Protocol</label>
									<form:input class="form-control dc_inputstyle" path="deviceProtocol" readonly="true"/>
							</div>
							<div class="form-group">
								<label>Device Protocol Version</label>
									<form:input class="form-control dc_inputstyle" path="deviceProtocolVersion" readonly="true"/>
							</div>
					</div>
				</div>
				
				<div>
					<div style="padding-top:20px;">
						<div id="templategrid"></div>
					</div>
						<section class="text-right dc_groupbtn">
							<button type="button" onClick="submitBackAction()" class="btn btn-default">Back</button>
						</section>
				</div>
			</div>
			</form:form>
		</section>
	</div>
</body>
