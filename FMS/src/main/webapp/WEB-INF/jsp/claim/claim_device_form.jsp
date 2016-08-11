<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="resources/javascript/commonfunctions.js"></script>
<script src="resources/js/common/uniqueness.validation.js"></script>
<script src="resources/javascript/claimdevicemanage.js"></script>
<style>
	.requiredAstrix {
		color: #FF3333
	}
</style>

<spring:eval var="getAllMake"
	expression="@propertyConfigurer.getProperty('mc.device.make')" />
<spring:eval var="getAllDeviceTypes"
	expression="@propertyConfigurer.getProperty('mc.device.deviceTypes')" />
<spring:eval var="getAllModels"
	expression="@propertyConfigurer.getProperty('mc.device.models')" />
<spring:eval var="getAllProtocols"
	expression="@propertyConfigurer.getProperty('mc.device.protocols')" />
<spring:eval var="getAllProtocolVersions"
	expression="@propertyConfigurer.getProperty('mc.device.protocolVersions')" />
<spring:eval var="deviceNwProtocolUrl"
	expression="@propertyConfigurer.getProperty('mc.device.nwProtocol')" />
<spring:eval var="deviceTagslUrl"
	expression="@propertyConfigurer.getProperty('mc.device.tags')" />
<spring:eval var="deviceConfiglUrl"
	expression="@propertyConfigurer.getProperty('mc.all.config.templates')" />
<spring:eval var="deviceUniquenessUrl"
	expression="@propertyConfigurer.getProperty('mc.services.validation')" />	
	
	

<script type="text/javascript">
	var deviceTypeUrl = "${getAllDeviceTypes}";
	var modelUrl = "${getAllModels}";
	var protocolUrl = "${getAllProtocols}";
	var versionUrl = "${getAllProtocolVersions}";
	var configurationUrl = '${deviceConfiglUrl}';
	var deviceUniquenessUrl  = '${deviceUniquenessUrl}';
	
</script>

<body>

			<div class="container0 theme-showcase dc_main" role="main"
				id="createDeviceContainer" style="margin: 0px 10px;">
				<section class="dc_maincontainer animate-panel">
					<div class="page-header">
						<h1>Claim Device</h1>
					</div>
					
						<form role="form" action="claim_home" id="claim_home" method="get"></form>
							<form:form role="form" action="claim_device"
								commandName="claim_form" id="claim_form" autocomplete="off"
								novalidate="novalidate" method="POST">
								<form:hidden path="action" />
								<form:hidden path="identifier" />
								<form:hidden path="oldSourceId" />
								<form:hidden path="oldDsName" />
								<div class="row">
									<section class="col-md-8">
										<h4>Device Configuration</h4>
										<section class="col-md-6">
												<div class="form-group">
													<label>Source Id<span class="requiredAstrix">*</span></label>
														<form:input path="sourceId" id="sourceId" class="form-control dc_inputstyle" />
												</div>
												<div class="form-group">
													<label>Tags</label>
														<form:hidden id="tags" path="tags" class="dc_kendocomboflexi form-control dc_inputstyle" />
														<div  id="tags_help" class="form-control" />
												</div>
						
												<div class="form-group">
													<label>Make<span class="requiredAstrix">*</span></label>
														<form:input id="make" path="make" class="dc_kendocomboflexi form-control dc_inputstyle" />
												</div>
												<div class="form-group">
													<label>Device Type<span class="requiredAstrix">*</span></label>
														<form:input id="deviceType" path="deviceType"
															class="dc_kendocomboflexi form-control dc_inputstyle" />
												</div>
										</section>
										<aside class="col-md-6">
										<div class="form-group">
											<label>Model<span class="requiredAstrix">*</span></label>
												<form:input id="model" path="model" class="dc_kendocomboflexi form-control dc_inputstyle" />
										</div>
										<div class="form-group">
											<label>protocol<span class="requiredAstrix">*</span></label>
												<form:input id="protocol" path="protocol" class="dc_kendocomboflexi form-control dc_inputstyle" />
										</div>
				
										<div class="form-group">
											<label>protocol Version<span class="requiredAstrix">*</span></label>
												<form:input id="version" path="version" class="dc_kendocomboflexi form-control dc_inputstyle" />
										</div>
										
										<div class="form-group">
											<label>Configuration Template</label>
												<form:input id="configuration" path="configuration" class="dc_kendocomboflexi form-control dc_inputstyle" />
										</div>
										
										<div class="form-group">
											<label>Status</label>
											<form:checkbox id="status" path="status"
												class="dc_kendocomboflexi form-control dc_inputstyle" data-role="switch" data-click="onChange"/>
										</div>
										
										</aside>
									</section>
									<aside class="col-md-4">
									<h4>IP Configuration</h4>
									<div class="dc_ipouterbg">
										<div class="form-group">
											<label>Network protocol<span class="requiredAstrix">*</span></label>
												<form:input id="nwProtocol" path="nwProtocol"
													class="dc_kendocomboflexi dc_inputstyle form-control" />
										</div>
				
										<div class="form-group">
											<label>Device IP</label>
												<form:input path="deviceIp" id="deviceIp" class="form-control dc_inputstyle" />
										</div>
										<div class="form-group">
											<label>Device Port</label>
												<form:input path="devicePort" id="devicePort"
													class="form-control dc_inputstyle" />
										</div>
				
				
										<div class="form-group">
											<label>Writeback Port</label>
												<form:input path="wbPort" id="wbPort" class="form-control dc_inputstyle" />
										</div>
										
										<div class="form-group">
											<label>Publish</label>
											<form:checkbox path="isPublish"
												id="isPublish" onchange="handlePublish()" />
										</div>
										
										<div class="form-group" id="dsNameDiv">
											<label>DataSource Name<span class="requiredAstrix">*</span></label>
												<form:input path="dsName" id="dsName" class="form-control dc_inputstyle" />
										</div>
										
										<div class="form-group"></div>
										</div>
									</aside>
								</div>
								<div class="row">
												<section class="col-md-12 text-right dc_groupbtn">
													<button type="button" id="btn-edit" class="btn btn-primary" onclick="claimDevice()">Claim</button>
													<button type="button" id="btn-back" class="btn btn-default" onclick="backClaimDevice()">Cancel</button>
												</section>
								</div>
					
						</form:form>
				</section>
			</div>
		
</body>

<script type="text/javascript">
$("#sourceId").attr("disabled", true);
	handleStatus(${claim_form.status});
	handlePublish();
	configureTagComponent("${deviceTagslUrl}",'${action}',$("#tags").val());
	
	var make = '${claim_form.make}';
	var deviceType = '${claim_form.deviceType}';
	var model = '${claim_form.model}';
	var protocol = '${claim_form.protocol}';
	var version = '${claim_form.version}';
	var configuration = '${claim_form.configuration}';
	
	populateMakes("${getAllMake}",make);
	if(make!='')
	{
		populateDeviceType(make,deviceType);
		var payload = {
							"make" : make,
							"deviceType" : deviceType
						};
		populateModel(payload,model);
		payload.model = model;
		populateProtocol(payload,protocol);
		payload.protocol = protocol;
		populateVersion(payload,version);
		payload.version = version;
		populateConfiguration(payload,configuration);
	}else
	{
		clearAndDisableText("deviceType");
		clearAndDisableText("model");
		clearAndDisableText("protocol");
		clearAndDisableText("version");
		clearAndDisableText("configuration");
	}
	populateNWprotocol("${deviceNwProtocolUrl}",'${claim_form.nwProtocol}');
	if($("#dsName").val().trim() != ""){
		$("#dsName").attr("readonly", true);
	}
	configureTagComponent("${deviceTagslUrl}",'Update',$("#tags").val());
	setFormValidation();
	function confirmCancelation() {
		var tagData = getTagData();
		if ('${claim_form.make}' != $('#make').val()
				|| '${claim_form.deviceType}' != $('#deviceType').val()
				|| '${claim_form.model}' != $('#model').val()
				|| '${claim_form.protocol}' != $('#protocol').val()
				|| '${claim_form.version}' != $('#version').val()
				|| '${claim_form.configuration}' != $('#configuration').val()
				|| '${claim_form.nwProtocol}' != $('#nwProtocol').val()
				|| '${claim_form.deviceIp}' != $('#deviceIp').val()
				|| '${claim_form.devicePort}' != $('#devicePort').val()
				|| '${claim_form.wbPort}' != $('#wbPort').val()
				|| '${claim_form.isPublish}' != $('#isPublish').is(":checked")
						+ "" || '${claim_form.dsName}' != $('#dsName').val()
				|| '${claim_form.sourceId}' != $('#sourceId').val()
				|| '${claim_form.tags}' != tagData) {
			var r = confirm("Do you want to continue?");
			if (r == true) {
				$('#claim_home').submit();
			}
		} else {
			$('#claim_home').submit();
		}
	}
</script>