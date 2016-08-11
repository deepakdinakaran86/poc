<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="resources/javascript/claimdevicehome.js"></script>
<style type="text/css">

</style>

<spring:eval var="unClaimedDevices"
	expression="@propertyConfigurer.getProperty('mc.services.unclaimed.alldevices')" />

<div class="container2 theme-showcase dc_main" role="main" style="margin: 0px 10px;">

	<section class="dc_maincontainer animate-panel" style="padding-bottom: 40px;">
	<form action="claim_form" id="claim_device_get" method="post" hidden="true">
		<input type="hidden" id="action" name="action" />
		<input type="hidden" id="identifier" name="identifier" />
		<input type="hidden" id="sourceId" name="sourceId" />
		<input type="hidden" id="tags" name="tags" />
		<input type="hidden" id="make" name="make" />
		<input type="hidden" id="deviceType" name="deviceType" />
		<input type="hidden" id="model" name="model" />
		<input type="hidden" id="protocol" name="protocol" />
		<input type="hidden" id="version" name="version" />
		<input type="hidden" id="nwProtocol" name="nwProtocol" />
		<input type="hidden" id="deviceIp" name="deviceIp" />
		<input type="hidden" id="devicePort" name="devicePort" />
		<input type="hidden" id="wbPort" name="wbPort" />
		<input type="hidden" id="isPublish" name="isPublish" />
		<input type="hidden" id="dsName" name="dsName" />
	</form>
		<div class="page-header">
			<h1>Claim Device</h1>
		</div>
		
		<div class="gx-toolbar-action text-right">
			<button class="k-button btn dc_btn_secondary glyphicon glyphicon-check" id="claim_device" onclick='claimDevice()'>Claim Device</button>
		</div>
		
		<div id="splitter" style="height:500px;">
			<div class="" id="claimdeviceSection">
				<div id="claim_device_grid"></div>				
			</div>
		</div>		
		
	</section>
</div>



<script type="text/javascript">
var response = '${response}';
var type = '${type}';
if (response != undefined && response != '') {
	staticNotification.show({message: response}, type);
}
$('#claim_device').prop("disabled", true);
claimDeviceGrid("${unClaimedDevices}");
</script>