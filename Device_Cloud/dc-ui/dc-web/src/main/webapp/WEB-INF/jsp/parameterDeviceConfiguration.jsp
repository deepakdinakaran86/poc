<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />

<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

<spring:url value="resources/javascript/templateGrid.js"
	var="templateGrid" />
<script type="text/javascript" src="${templateGrid}"></script>

<spring:url value="resources/javascript/parameterdeviceconfiguration.js"
	var="parameterdeviceconfiguration" />
<script type="text/javascript" src="${parameterdeviceconfiguration}"></script>

<style>
#mapParameterTemplateGrid{
font-size: smaller;
}
</style>
  <div class="container" style="width: auto">
		<div class="row">
		
		
		<div class="col-md-12">
			
					<div class="col-md-3">
		
						<div class="form-group">
							<label class="control-label" for="configTemplateName">Device Name:</label> <label class="control-label" id="configTemplateName"> XXXXXXXX </label>
						</div>
						
						<div class="form-group">
							<label class="control-label" for="configTemplateProtocol">Protocol:</label> <label class="control-label" id="configTemplateProtocol"> XXXXXXXX </label>
							
						</div>								
					</div>
					<div class="col-md-3">
		
						<div class="form-group">
							<label class="control-label" for="configTemplateMake">Make:</label>	<label class="control-label" id="configTemplateMake"> XXXXXXXX </label>
						</div>
						
						<div class="form-group">
							<label class="control-label" for="configTemplateProtocolVersion">Device Type:</label> <label class="control-label" id="configTemplateProtocolVersion"> XXXXXXXXX</label>							
						</div>	
															
					</div>
					<div class="col-md-3">
		
						<div class="form-group">
							<label class="control-label" for="configTemplateModel">Model:</label> <label class="control-label" id="configTemplateModel"> XXXXXXXX </label>
						</div>
														
					</div>
				
			<input type="hidden" value="mapParameterTemplateGrid" id="templateContainer" />  
		
</div>


</div>	
     
    </div>
            
     <div id="mapParameterTemplateGrid" style="width:100%"></div>
<div style="margin-bottom: 10px; margin-top: 8px;" align="right">
			<button id="saveParamMap" class="k-primary k-button">Save All</button>
			<button id="cancelParamMap" class="k-primary k-button">Cancel</button>
		</div>
		           
            
	         
            
	<script type="text/x-kendo-template" id="filterTemplate">
        <div class="toolbar" >
            <label class="category-label" for="filterTemplateGrid">Search Template: </label>
            <input type="text" id="filterTemplateGrid" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
    </script>
 
</body>
</html>