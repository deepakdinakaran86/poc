<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />
<spring:eval var="getAllTemplates"
	expression="@propertyConfigurer.getProperty('datasource.system.getalltemplates')" />

<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>
	
<spring:eval var="getAllAccessTypes" expression="@propertyConfigurer.getProperty('datasource.system.getallaccesstypes')" />
<spring:eval var="getAllDataTypes" expression="@propertyConfigurer.getProperty('datasource.system.getalldatatypes')" />
<spring:eval var="getAllParams" expression="@propertyConfigurer.getProperty('datasource.params.getallparams')" />
<spring:eval var="getDeviceDetails" expression="@propertyConfigurer.getProperty('datasource.device.getDeviceDetails')" />
<spring:eval var="getAllSystemTags" expression="@propertyConfigurer.getProperty('datasource.system.getallsystemTags')" />
<spring:eval var="assignPointsToDevice" expression="@propertyConfigurer.getProperty('datasource.device.assignPointsToDevice')" />
<spring:eval var="getAllDevices" expression="@propertyConfigurer.getProperty('datasource.device.retrieve')" />

<spring:eval var="deviceTagsUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
	
<script type="text/javascript">
var mapParameterEditUrl = "${getAllTemplates}";    
var getAllAccessTypes = "${getAllAccessTypes}";
var getAllDataTypes = "${getAllDataTypes}";
var getAllParams = "${getAllParams}";
var url = "ajax" + "${deviceTagsUrl}";
var deviceDetails = "${getDeviceDetails}";
var getAllSystemTags = "${getAllSystemTags}";
var assignPointsToDevice = "${assignPointsToDevice}";
var getAllDevices = "${getAllDevices}";

currentGrid = "mapParameterGrid";
</script>
	

<spring:url value="resources/javascript/templateGrid.js"
	var="templateGrid" />
<script type="text/javascript" src="${templateGrid}"></script>
   
<style>
#mapParameterGrid{
font-size: smaller;
}
#mapParamConfigurationTemplate-list input.k-textbox{
min-width: 0;
}
</style>
  <div class="container" style="width: auto">
		<div class="row">
		
		
		<div class="col-md-12">
			
					<div class="col-md-4">
		
						<div class="form-group">
							<div class="">
							<label class="control-label" for="mapParamConfigDeviceName">Device Name:</label> <label class="control-label" id="mapParamConfigDeviceName"> XXXXXXX </label>							
							</div>
						</div>
						
						<div class="form-group">
							
							<div class="">
							<label class="control-label" for="mapParamConfigDeviceType">Device Type:</label>
								<label class="control-label" id="mapParamConfigDeviceType">  XXXXXXXX  </label>
							</div>
						</div>
													
					</div>
					<div class="col-md-4">
		
						<div class="form-group">							
							<div class="">
							<label class="control-label" for="mapParamConfigMake">Make:</label>
								<label class="control-label" id="mapParamConfigMake"> XXXXXXXXX </label>
							</div>
						</div>
						
						<div class="form-group">							
							<div class="">
							<label class="control-label" for="mapParamConfigProtocol">Protocol:</label>
								<label class="control-label" id="mapParamConfigProtocol"> XXXXXXXXX </label>
							</div>
						</div>									
					</div>
					<div class="col-md-4">	
						<div class="form-group">							
							<div class="">
							<label class="control-label" for="mapParamConfigModel">Model:</label>
								<label class="control-label" id="mapParamConfigModel"> XXXXXXXX </label>
							</div>
						</div>
						
						<div class="form-group">							
							<div class="">
							<label class="control-label" for="mapParamConfigProtocolVersion">Protocol Version:</label>
								<label class="control-label" id="mapParamConfigProtocolVersion"> XXXXXXXXX </label>
							</div>
						</div>									
					</div>
				
			
		
</div>


</div>	
<div class="row">
	<div class="col-md-12">		
		<div class="form-group confTemplateSelection">
			
			<div style="display: inline-block; margin-left: 12px;">
			<label class="control-label" for="mapParamConfigurationTemplate">Configuration Template:</label>
				<input id="mapParamConfigurationTemplate" name="mapParamConfigurationTemplate" 
					 placeholder="Select" class="k-textbox selectWidth" style="min-width: 300px; display: inline-block;" disabled> &nbsp;
					 <a href="#" id="selectAddTemplateLink" onclick="toggleTemplate()">Select Template</a>
			</div>
		</div>
		<div id="window">
			<div id="assignToDeviceGrid"></div>
			<div id="assignToDeviceBtns" style="margin-bottom: 10px; margin-top: 8px; display: none" align="right">								
				<button id="assignParametersToDevice" class="k-primary k-button">Assign</button>
				<button id="cancelAssign" class="k-primary k-button">Cancel</button>
			</div>
		</div>	
	</div>
</div>
    </div>
    	<input type="hidden" value="mapParameterGrid" id="templateContainer" />  
         <div id="mapParameterGrid" style="width:100%"></div>
<div style="margin-bottom: 10px; margin-top: 8px;" align="right">
			<button id="assignParamMap" class="k-primary k-button">Assign To...</button>
			<button id="saveParamMap" class="k-primary k-button">Save All</button>
			<button id="cancelParamMap" class="k-primary k-button">Cancel</button>
		</div>
 
   <script type="text/x-kendo-template" id="filterTemplate">
        <div class="toolbar" >
            <label class="category-label" for="filterTemplateGrid">Search Template: </label>
            <input type="text" id="filterTemplateGrid" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
    </script>
     <script type="text/x-kendo-template" id="filterDevice">
        <div class="toolbar" >
            <label class="category-label" for="filterDeviceGrid">Search Device: </label>
            <input type="text" id="filterDeviceGrid" style="width: 250px;height:25px;  border-radius:7px;" class="k-input" onkeyup="filterDevice()"/>              
        </div>
    </script>
    <script type="text/x-kendo-template" id="removeTemplateSelectedConfirm">
	<div style="text-align: center">
    	<p class="delete-message">Do you want to remove the Template and continue with Custom Parameter Config ?</p> 
    	<button class="k-button" id="yesButton">Yes</button>
    	<button class="k-button" id="noButton"> No</button>
	</div>
	</script>
	
<spring:url value="resources/javascript/mapparameterconfig.js"
	var="mapparameterconfig" />
<script type="text/javascript" src="${mapparameterconfig}"></script>
   
</body>
</html>