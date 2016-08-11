<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getAllDevices" expression="@propertyConfigurer.getProperty('datasource.device.retrieve')" />
<spring:eval var="getAllPoints" expression="@propertyConfigurer.getProperty('datasource.protocol.getallpoints')" />
<spring:eval var="getAllDeviceTags" expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
<spring:eval var="getPointConfigurations" 
	expression="@propertyConfigurer.getProperty('datasource.device.configurations')" />
<spring:eval var="getSystemCommands"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getSystemCommands')" />
	
<body>

	<div>		
		<div style="margin-bottom:10px;margin-top:8px;" align="right">
			<button class="removeItem k-button" onclick='add("ADD")'>Create</button>
			<button class="removeItem k-button" onclick='add("MANAGE")'>Manage</button>
			<button class="removeItem k-button" onclick='add("HISTORY")'>History</button>
			<button class="removeItem k-button" onclick='add("LIVE")'>Live</button>
<!-- 			<button class="removeItem k-button" onclick='add("WRITE")'>Write</button> -->
			<button class="removeItem k-button" onclick='add("MAP PARAMETER")'>Map Parameter</button>
<!-- 				<div class="dropdown" style="display: inline-block;"> -->
<!-- 				  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"> -->
<!-- 				    Configure -->
<!-- 				    <span class="caret"></span> -->
<!-- 				  </button> -->
<!-- 				  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" style="right: 0; left: auto;">				    -->
<!-- 				    <li><a href="#" onclick='add("MAP PARAMETER")'>Map Parameter</a></li> -->
<!-- 				    <li><a href="#" onclick='add("PARAMETER CONFIGURATION")'>Parameter Configuration</a></li> -->
<!-- 				  </ul> -->
<!-- 				</div> -->
		</div>

		<div style="margin-bottom:10px;margin-top:8px;" align="left" >
			<label>Tag Filter </label>
			<select id="select_tag" ></select>
		</div>
		<div id="mainpage_grid">

            <ul id="contextMenu"></ul>
            </div>
	</div>
	<script>
	
	var menu = $("#menu"),
                original = menu.clone(true);
    var commandProtocol = "${getAllPoints}";
    var getSysCmdsUrl = "${getSystemCommands}";
    var getAllDeviceTags = "${getAllDeviceTags}";
    var getPointConfigurations = "${getPointConfigurations}";
    		
		$(document).ready(function() {
			
			$("#mainpage_grid").on("mousedown", "tr[role='row']", function (e) {
			//alert(e.which);				
			   if (e.which === 3) {
			       $("#mainpage_grid tbody tr").removeClass("k-state-selected");
			       $(this).addClass("k-state-selected");
			    
			    var entityGrid = $("#mainpage_grid").data("kendoGrid");
				var rows = entityGrid.select();
				var selectedItem = entityGrid.dataItem(rows);
			    addCommandDs(selectedItem);
			   
			   }
			});
   
			var url = "${getAllDevices}";
			loadDeviceTree(url);	
					
		});

		function add(label) {
			$(".breadcrumb").empty();
			var entityGrid = $("#mainpage_grid").data("kendoGrid");
			var rows = entityGrid.select();
			var selectedItem = entityGrid.dataItem(rows);

			if (label != "ADD" && selectedItem == null) {
				staticNotification.show("Please select a row in grid", "warning");
				$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
				}else{
				
					
				var button = {};
				button.text = label;
				button.id = label;
				switch (label) {
				case "DEVICE":
					$("#contentModule").load("devicetab");
					$("#panelHeaderTitle").text("Device");
					break;
	
				case "MANAGE":
					$("#contentModule").load( "managedevice?sourceId="+ selectedItem.sourceId);
					$("#panelHeaderTitle").text("Manage");
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">Manage</li>');
					break;
	
				case "LIVE":
					$("#contentModule").load( "livetracking?sourceId="
							+ selectedItem.sourceId + "&datasourceName="
							+ selectedItem.datasourceName);
					$("#panelHeaderTitle").text("Live Tracking");
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">Live Tracking</li>');				
					break;
					
				case "HISTORY":
	
					$("#contentModule").load( "historytracking?sourceId="
									+ selectedItem.sourceId + "&datasourceName="
									+ selectedItem.datasourceName);
					$("#panelHeaderTitle").text("History Tracking");	
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">History Tracking</li>');					
					break;
	
				case "ADD":				
					loadPage("createdevice");
					break;
					
				case "WRITE BACK":
					$("#contentModule").load("writebackbatch?device="+ selectedItem.deviceId
					+ "&sourceId=" + selectedItem.sourceId);
					$("#panelHeaderTitle").text("Device Write Back");
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">Write Back</li>');
					break;
				
				case "MAP PARAMETER":
					$("#contentModule").load("mapParameterConfig");
					$("#panelHeaderTitle").text("Map Parameter");
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">Map Parameter</li>');
					break;
				
				case "PARAMETER CONFIGURATION":
					$("#contentModule").load("parameterDeviceConfiguration");
					$("#panelHeaderTitle").text("Parameter Configuration");
					$(".breadcrumb").append('<li><a href="#" onclick="loadPage(\'devicetab\')" >Device Home</a></li>');
					$(".breadcrumb").append('<li class="active">Parameter Configuration</li>');
					break;
				}
				
				}


		}
		
		    
             // load images beside context menu from bootstrap
             function loadGlyphicons(){
             
             $( ".glyphicon-off, .glyphicon-floppy-disk, .glyphicon-pencil, .glyphicon-refresh" ).remove();
             
             
             $('span:contains("Reboot")').prepend('<span class="glyphicon glyphicon-off"></span> ');
             $('span:contains("App Save")').prepend('<span class="glyphicon glyphicon-floppy-disk"></span> ');
             $('span:contains("Point Write Request")').prepend('<span class="glyphicon glyphicon-pencil"></span> ');
             $('span:contains("Sync")').prepend('<span class="glyphicon glyphicon-refresh"></span> ');
             $("#contextMenu .k-link").css("width","150px");
             }
             
             
	</script>
</body>
</html>