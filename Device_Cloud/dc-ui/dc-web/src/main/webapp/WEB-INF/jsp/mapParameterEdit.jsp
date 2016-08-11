<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>
<spring:url value="resources/javascript/templateGrid.js"
	var="templateGrid" />

<spring:eval var="getAllAccessTypes" expression="@propertyConfigurer.getProperty('datasource.system.getallaccesstypes')" />
<spring:eval var="getAllDataTypes" expression="@propertyConfigurer.getProperty('datasource.system.getalldatatypes')" />
<spring:eval var="getAllParams" expression="@propertyConfigurer.getProperty('datasource.params.getallparams')" />
<spring:eval var="deviceTagsUrl" expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
<spring:eval var="deviceGetTemplateDetails" expression="@propertyConfigurer.getProperty('datasource.system.mapparameterEdit')" />
<spring:eval var="getAllSystemTags" expression="@propertyConfigurer.getProperty('datasource.system.getallsystemTags')" />
<spring:eval var="saveTemplateUrl" expression="@propertyConfigurer.getProperty('datasource.device.saveTemplate')" />

<script type="text/javascript">
var mapParameterEditUrl = "${getAllTemplates}";    
var getAllAccessTypes = "${getAllAccessTypes}";
var getAllDataTypes = "${getAllDataTypes}";
var getAllParams = "${getAllParams}";
var url = "ajax" + "${deviceTagsUrl}";
var getAllSystemTags = "${getAllSystemTags}";
var saveTemplateUrl ="${saveTemplateUrl}";

currentGrid = "mapParameterEditTemplateGrid";

</script>
	
<script type="text/javascript" src="${templateGrid}"></script>

<style>
#mapParameterEditTemplateGrid{
font-size: smaller;
}
</style>

  <div class="container" style="width: auto">
		<div class="row">
		
		
		<div class="col-md-12">
			
					<div class="col-md-3">
		
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateName">Configuration Template:</label>
							<label class="control-label" id="viewEditTemplateName"> XXXXXXXX </label>
						</div>
						
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateDeviceType">Device Type:</label>
							<label class="control-label" id="viewEditTemplateDeviceType"> XXXXXXXX </label>
						</div>									
					</div>
					<div class="col-md-3">
						
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateProtocol">Protocol:</label>
							<label class="control-label" id="viewEditTemplateProtocol"> XXXXXXXX </label>
						</div>		
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateProtocolVersion">Protocol Version:</label>
							<label class="control-label" id="viewEditTemplateProtocolVersion"> XXXXXXXXXX </label>
						</div>							
					</div>
					<div class="col-md-3">
		
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateModel">Model:</label>
							<label class="control-label" id="viewEditTemplateModel"> XXXXXXX </label>
						</div>						
						
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateMake">Make:</label>
							<label class="control-label" id="viewEditTemplateMake"> XXXXXXXXX </label>
						</div>									
					</div>
					<div class="col-md-3">		
						
						<div class="form-group">
							<label class="control-label" for="viewEditTemplateDescription">Description:</label>
							<label class="control-label" id="viewEditTemplateDescription"> XXXXXXXXX </label>
						</div>									
					</div>
				
			<input type="hidden" value="mapParameterEditTemplateGrid" id="templateContainer" />  
		
</div>


</div>	
                
  
    </div>
    
       <div id="mapParameterEditTemplateGrid" style="width:100%"></div>
<div style="margin-bottom: 10px; margin-top: 8px;" align="right">
<!-- 			<button id="assignParamMap" class="k-primary k-button">Assign To...</button> -->
			<button id="saveParamMap" class="k-primary k-button">Save All</button>
			<button id="cancelParamMap" class="k-primary k-button">Cancel</button>
		</div>
		           
            
	<script type="text/x-kendo-template" id="filterTemplate">
        <div class="toolbar" >
            <label class="category-label" for="filterTemplateGrid">Search Template: </label>
            <input type="text" id="filterTemplateGrid" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
    </script>
    <script>
    var templateInfo = "${deviceGetTemplateDetails}";
    </script>
 <spring:url value="resources/javascript/mapparameteredit.js"
	var="mapparameteredit" />
<script type="text/javascript" src="${mapparameteredit}"></script>


</body>
</html>