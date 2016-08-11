<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />

<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

<spring:url value="resources/javascript/parameterconfiguration.js"
	var="parameterconfiguration" />
<script type="text/javascript" src="${parameterconfiguration}"></script>

  <div class="container">
		<div class="row">
		
		
		<div class="col-md-12">
			
					<div class="col-md-5">
		
						<div class="form-group">
							<label class="control-label" for="paramConfigName">Parameter Name:</label>
							<div class="">
								<input id="paramConfigName" name="paramConfigName" type="text"
									placeholder="Parameter Name" class="k-textbox selectWidth" validationMessage="Enter valid Parameter Name" style="min-width: 300px;" disabled="disabled">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label" for="paramConfigPhysicalQuantity">Physical Quantity:</label>
							<div class="">
								<input id="paramConfigPhysicalQuantity" name="paramConfigPhysicalQuantity" 
									placeholder="Physical Quantity" class="k-textbox selectWidth" validationMessage="Enter valid Physical Quantity" style="min-width: 300px;" required>
							</div>
						</div>	
						
						
						<div class="form-group">
							<label class="control-label" for="paramConfigDataType">Data Type:</label>
							<div class="">
								<input id="paramConfigDataType" name="paramConfigDataType" 
									placeholder="Make" class="k-textbox selectWidth" validationMessage="Enter valid Data Type" style="min-width: 300px;" required>
							</div>
						</div>								
					</div>
					
					
				
				
			
		
</div>


</div>	
          <div class="row">
         		 <div class="col-md-12">
						<div style="margin-bottom: 10px; margin-top: 8px;" align="right">
							<button id="saveParamMap" class="k-primary k-button">Save</button>
							<button id="cancelParamMap" class="k-primary k-button">Clear</button>
						</div>
					</div>
          </div>      
   
    </div>
    
      <div id="mapParameterTemplateGrid" style="width:100%"></div>
	
</body>
</html>