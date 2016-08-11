<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<spring:eval var="getAllLogs"
	expression="@propertyConfigurer.getProperty('datasource.protocol.getAllLogs')" />
<spring:eval var="deactivateTemplate"
	expression="@propertyConfigurer.getProperty('datasource.system.deactivatetemplate')" />
<spring:eval var="getAllTemplates"
	expression="@propertyConfigurer.getProperty('datasource.system.getalltemplates')" />

<spring:url value="resources/writebackJSONsample.js"
	var="sampleJS" />
<script type="text/javascript" src="${sampleJS}"></script>

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
</style>	

  <div>
        			
                <div style="margin-bottom:10px;margin-top:8px;" align="right">
<!-- 					<input type="submit" class="k-button" id="createConfigTemplate" value="Create New Template Config"/> -->
<!-- 					<input type="submit" class="k-button" id="createParamConfiguration" value="Create New Parameter Config"/> -->
				</div>
                <div id="configTemplateGrid" style="width:100%"></div>
            
	
    </div>
 
    <script type="text/x-kendo-template" id="configSearchTemplate">
    <div class="toolbar" >
			<div style="float: left; display: 'inline-block'">
				<label class="category-label" for="category">Search</label>
            	<input type="text" id="searchTemplate" class"k-textbox" style="width: 250px;height:25px;  border-radius:7px;" class="k-input"/>
			</div>
            
		<div style="float: right; display: 'inline-block'">			
			<a class="k-button k-button-icontext" id="createConfigTemplate"><span class="k-icon k-add"></span>Create Config Template</a>
		</div>
			
        </div>

    </script>
    
    <script type="text/x-kendo-template" id="deleteTemplateConfirm">
	<div style="text-align: center">
    	<p class="delete-message">Are you sure you want to deactivate Configuration template?</p> 
    	<button class="k-button" id="yesButton">Yes</button>
    	<button class="k-button" id="noButton"> No</button>
	</div>
	</script>	
	<script>
	var deactivateTemplateUrl = "${deactivateTemplate}";
	var getAllTemplatesUrl = "${getAllTemplates}";
	
 
	</script>
  
<spring:url value="resources/javascript/configtemplate.js"
	var="configtemplate" />
<script type="text/javascript" src="${configtemplate}"></script>

</body>
</html>