<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />

<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

<spring:eval var="deviceTagsUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.tags')" />

<spring:url value="resources/javascript/templateGrid.js"
	var="templateGrid" />

<spring:eval var="getAllMake" expression="@propertyConfigurer.getProperty('datasource.device.make')" />
<spring:eval var="getAllDeviceTypes" expression="@propertyConfigurer.getProperty('datasource.device.deviceTypes')" />
<spring:eval var="getAllModels" expression="@propertyConfigurer.getProperty('datasource.device.models')" />
<spring:eval var="getAllProtocols" expression="@propertyConfigurer.getProperty('datasource.device.protocols')" />
<spring:eval var="getAllProtocolVersions" expression="@propertyConfigurer.getProperty('datasource.device.protocolVersions')" />

<spring:eval var="getAllAccessTypes" expression="@propertyConfigurer.getProperty('datasource.system.getallaccesstypes')" />
<spring:eval var="getAllDataTypes" expression="@propertyConfigurer.getProperty('datasource.system.getalldatatypes')" />
<spring:eval var="getAllParams" expression="@propertyConfigurer.getProperty('datasource.params.getallparams')" />
<spring:eval var="saveTemplateUrl" expression="@propertyConfigurer.getProperty('datasource.device.saveTemplate')" />
<spring:eval var="getAllPointsByProtocolVersion" expression="@propertyConfigurer.getProperty('datasource.device.getAllPointsByProtocolVersion')" />
<spring:eval var="getAllSystemTags" expression="@propertyConfigurer.getProperty('datasource.system.getallsystemTags')" />

<script type="text/javascript">
var getAllAccessTypes = "${getAllAccessTypes}";
var getAllDataTypes = "${getAllDataTypes}";
var getAllParams = "${getAllParams}";
var saveTemplateUrl ="${saveTemplateUrl}";
var getAllSystemTags = "${getAllSystemTags}";

currentGrid = "createConfigTemplateGrid";

</script>	
<script type="text/javascript" src="${templateGrid}"></script>

<style>
#createConfigTemplateGrid{
font-size: smaller;
}
.selectWidth{
min-width: 400px;
}
</style>
<form id="createTemplateForm">
  <div class="container">
		<div class="row">
		
		
		<div class="col-md-12">
			<fieldset id="deviceConfigFieldSet">
			
			
			<legend>Template Configuration: </legend>
					<div class="col-md-5">
						
						<div class="form-group">
							<label class="control-label required" for="configTemplateName">Configuration Template:</label>
							<div class="">
								<input id="configTemplateName" name="configTemplateName" type="text"
									placeholder="Configuration Template" class="k-textbox" validationMessage="Enter valid Configuration Template" required>
							</div>
						</div>	
						
						<div class="form-group">
							<label class="control-label required" for="configTemplateMake">Make:</label>
							<div class="">
								<input id="configTemplateMake" name="configTemplateMake" 
									placeholder="Make" class="k-textbox selectWidth" validationMessage="Enter valid Make" required>
									<span class="k-invalid-msg" data-for="configTemplateMake"></span>
							</div>
						</div>
		
						<div class="form-group">
							<label class="control-label required" for="configTemplateDevice">Device:</label>
							<div class="">
								<input id="configTemplateDevice" name="configTemplateDevice" 
									placeholder="Device" class="k-textbox selectWidth" validationMessage="Enter Device" required>
									<span class="k-invalid-msg" data-for="configTemplateDevice"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label required" for="configTemplateModel">Model:</label>
							<div class="">
								<input id="configTemplateModel" name="configTemplateModel" 
									placeholder="Model" class="k-textbox selectWidth" validationMessage="Enter valid Model" required>
									<span class="k-invalid-msg" data-for="configTemplateModel"></span>
							</div>
						</div>				
				</div>
				<div class="col-md-5">
		
						<div class="form-group">
							<label class="control-label required" for="configTemplateProtocol">Protocol:</label>
							<div class="">
								<input id="configTemplateProtocol" name="configTemplateProtocol" 
									placeholder="Protocol" class="k-textbox selectWidth" validationMessage="Enter valid Protocol" required>
									<span class="k-invalid-msg" data-for="configTemplateProtocol"></span>
							</div>
						</div>	
						
						<div class="form-group">
							<label class="control-label required" for="configTemplateProtocolVersion">Protocol Version:</label>
							<div class="">
								<input id="configTemplateProtocolVersion" name="configTemplateProtocolVersion" 
									placeholder="Protocol Version" class="k-textbox selectWidth" validationMessage="Enter valid Protocol Version" required>
									<span class="k-invalid-msg" data-for="configTemplateProtocolVersion"></span>
							</div>
						</div>																			
						
						<div class="form-group">
							<label class="control-label" for="configDesc">Description:
						    </label>
							<div class="">
								<textarea placeholder="Description" class="k-textbox" rows="2" id="configDesc" style="height: 80px" maxlength="250"></textarea>
							</div>
		
						</div>				
				</div>
		
			</fieldset>
		</div>
		
</div>


</div>	
                
     <div id="createConfigTemplateGrid" style="width:100%; margin-top: 20px;"></div>
<div style="margin-bottom: 10px; margin-top: 8px;" align="right">
			<button id="saveConfigTemplate" class="k-primary k-button" type="submit">Save Config Template</button>
			<button id="cancelSaveTemplate" class="k-primary k-button">Cancel</button>
		</div>
		<input type="hidden" value="createConfigTemplateGrid" id="templateContainer" />           

</form>
  	
 <script type="text/x-kendo-template" id="filterTemplate">
        <div class="toolbar" >
            <label class="category-label" for="filterTemplateGrid">Search Template: </label>
            <input type="text" id="filterTemplateGrid" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>              
        </div>
    </script>
 
<script>	
	var getAllMake = "${getAllMake}";
	var getAllDeviceTypes = "${getAllDeviceTypes}";
	var getAllModels = "${getAllModels}";
	var getAllProtocols = "${getAllProtocols}";
	var getAllProtocolVersions = "${getAllProtocolVersions}";
	var getAllPointsByProtocolVersion = "${getAllPointsByProtocolVersion}";
</script>

	
<spring:url value="resources/javascript/createconfigtemplate.js"
	var="createconfigtemplate" />
<script type="text/javascript" src="${createconfigtemplate}"></script>
		
	
    <script>    
   var url = "ajax" + "${deviceTagsUrl}";
	 $(document).ready(function () {
			var createTemplateValidator = $("#createTemplateForm").kendoValidator().data("kendoValidator"); 
// 	$(".k-grid-delete").css("display","none");
// 	$(".k-grid-insert").css("display","none");
		 $("#filterTemplateGrid").keyup(function () {                   
          var val = $("#filterTemplateGrid").val();
          
          
          $("#createConfigTemplateGrid").data("kendoGrid").dataSource.filter({
              logic: "or",
              filters: [
              	{
                      field: "deviceIO",
                      operator: "contains",
                      value: val
                  },
                  {
                      field: "pointName",
                      operator: "contains",
                      value: val
                  },
                  {
                      field: "parameterName",
                      operator: "contains",
                      value: val
                  },
                  {
                      field: "customTag",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "systemTag",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "physicalQuantity",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "unitOfMeasurement",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "dataType",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "access",
                      operator: "contains",
                      value:val
                  },
                  {
                      field: "acquisition",
                      operator: "contains",
                      value:val
                  }
              ]
          });

    	});
             
// 			 $("#createConfigTemplate").on('click',function () {
// 			 var validator = $("#createTemplateForm").kendoValidator().data("kendoValidator");				
// 					 event.preventDefault();
//                         if (validator.validate()) {
//                             alert();
//                         } else {
//                             alert("opyamanipopcorn");
//                         }
// 				});
				
				 $("form").submit(function(event) {
                        event.preventDefault();
                       if (createTemplateValidator.validate()) {
                         
							//$("#saveConfigTemplate").on('click', function() {
								// save config	
								saveTemplate();
							//});
                        } else {
                            alert("opyamanipopcorn");
                        }
                    });
			
            });
            
    </script>
	
</body>
</html>