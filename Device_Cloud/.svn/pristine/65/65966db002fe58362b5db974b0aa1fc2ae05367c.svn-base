<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />

<spring:eval var="getAllDataTypes" expression="@propertyConfigurer.getProperty('datasource.system.getalldatatypes')" />
	
<spring:eval var="getAllParams" expression="@propertyConfigurer.getProperty('datasource.params.getallparams')" />

<spring:eval var="saveParams" expression="@propertyConfigurer.getProperty('datasource.params.saveparams')" />

<spring:eval var="checkUniqueness" expression="@propertyConfigurer.getProperty('datasource.params.checkduplicates')" />

<style>
.k-datepicker{
	width: 250px !important;
}
.k-grid-edit-param{
	height: 35px;
}
.glyphicon-th-list{
  margin-top: 2px;
}
input.k-textbox{
min-width: 0;
}

#viewParameterGrid > div.k-header,.k-grid-toolbar{
text-align: right;
}

</style>	

	
<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

  <div>
        			
<!--                 <div style="margin-bottom:10px;margin-top:8px;" align="right">					 -->
<!-- 					<input type="submit" class="k-button" id="createParamConfiguration" value="Create New Parameter Config"/> -->
<!-- 				</div> -->
				
<!-- 		            <label class="category-label" for="category">Search</label> -->
<!-- 		            <input type="text" id="searchTemplate" class"k-textbox" style="width: 250px;height:35px;  border-radius:7px;  margin-bottom: 15px;" class="k-input"/><br /> -->
		            
		        
                <div id="viewParameterGrid" style="width:100%"></div>
            
	
    </div>

    <script type="text/x-kendo-template" id="configSearchTemplate">
        <div class="toolbar" >
			<div style="float: left; display: 'inline-block'">
				<label class="category-label" for="category">Search</label>
            	<input type="text" id="searchTemplate" class"k-textbox" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>
			</div>
            
		<div style="float: right; display: 'inline-block'">
			<a class="k-button k-button-icontext createParameter"><span class="k-icon k-add"></span>Create New Parameter</a>
		</div>
			
        </div>
    </script>
    
    <script type="text/x-kendo-template" id="createParameterForm">
	<div style="width: 250px; height: 400px">
    	<p><strong class="required">Parameter Name:</strong> <input placeholder="Parameter Name" class="k-input k-textbox" type="text" name="paramName" id="paramName"></input></p>
		<p id="errorMsgParamName" style="color: red; font-size: small; font-weight: 600"></p>
		
		<p><strong class="required">Physical Quantity:</strong> <input placeholder="Physical Quantity" class="k-input k-textbox" type="text" name="phy_quantity" id="phy_quantity"></input></p>
		<p id="errorMsgPhyQuantity" style="color: red; font-size: small; font-weight: 600"></p>

		<p><strong class="required">Data Type:</strong> <input placeholder="Data Type" type="text" name="dataType" id="dataType"></input></p>
		<p id="errorMsgDataType" style="color: red; font-size: small; font-weight: 600"></p>

		<hr>
		<p id="errorMsg" style="color: red; font-size: small; font-weight: 600"></p>
		<div style="text-align: right">
			<button type="submit" id="createParameter" class="k-button k-primary" onclick="">Submit</button>
			<button type="submit" id="cancelCreateParameter" class="k-button" onclick="">Cancel</button>
		</div>
		
		
	</div>
	</script>	
	
  	<script type="text/x-kendo-template" id="parameterConfirmClose">
	<div style="text-align: center">
    	<p class="delete-message">Are you sure you want to leave this page?</p> 
    	<button class="k-button" id="yesButton">Yes</button>
    	<button class="k-button" id="noButton"> No</button>
	</div>
	</script>
	
	<script>	
	var getAllParamsUrl = "${getAllParams}";
    var getAllDataTypesUrl = "${getAllDataTypes}";
    var saveParams = "${saveParams}";
    var paramUniquenessUrl = "${checkUniqueness}";
    
	</script>
	
<spring:url value="resources/javascript/parameterview.js"
	var="parameterview" />
<script type="text/javascript" src="${parameterview}"></script>



	
</body>
</html>