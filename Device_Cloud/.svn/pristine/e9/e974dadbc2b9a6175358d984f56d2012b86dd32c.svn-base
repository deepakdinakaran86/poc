<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="getDeviceUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.getDeviceDetails')" />
<spring:eval var="deviceNwProtocolUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.nwProtocol')" />
<spring:eval var="deviceTagsUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
<spring:eval var="deviceMakeUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.make')" />
<spring:eval var="deviceTypeUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.deviceTypes')" />
<spring:eval var="deviceModelUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.models')" />
<spring:eval var="deviceProtocolsUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.protocols')" />
<spring:eval var="deviceProtocolVersionUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.protocolVersions')" />
<style>
.form-group {
	display: inline-block;
}
</style>
<!-- Masking -->
<script src="resources/js/jquery.mask.min.js"></script>

<script>


configureTagComponent();

	$(document).ready(
			function() {
				 $('#m_deviceIP').mask('099.099.099.099');
				viewDeviceDetails("${sourceId}");
				//method for view device details
				function viewDeviceDetails(sourceId) {
					
					//get device data url
					var url = "${getDeviceUrl}";
				    url = url.replace("{source_id}", sourceId);
				    
					//disable input fields
					$("#deviceContainer").find(":text").attr("readonly", true);
					$("#deviceContainer").find("textarea").attr("readonly", true);
					$("#deviceContainer").find(":checkbox").attr("disabled", true);
					$("#deviceContainer").find("select").attr("disabled", true);
					var multiselect = $("#tags").data("kendoMultiSelect");
					multiselect.enable(false);
					$("#btn-edit").show();
					$("#btn-view").hide();
					$("#btn-update").hide();
					
					$.ajax({
						url : "ajax" + url,
						dataType : 'json',
						type : "GET",
						success : function(response) {
							var device =response.entity;
							
							//set values to corresponding filelds 
							$('#sourceId').val(device.sourceId);
							$('#prevSourceId').val(device.sourceId);
							$('#make').append(
									'<option value=' + JSON.stringify(device.version.make) + '>'
									+ device.version.make  + '</option>');
							$('#deviceType').append(
									'<option value=' + JSON.stringify(device.version.deviceType) + '>'
									+ device.version.deviceType + '</option>');
							$('#model').append(
									'<option value=' + JSON.stringify(device.version.model) + '>'
									+ device.version.model + '</option>');
							$('#protocol').append(
									'<option value=' + JSON.stringify(device.version.protocol) + '>'
											+ device.version.protocol  + '</option>');
							$('#version').append(
									'<option value=' + JSON.stringify(device.version.version) + '>'
											+ device.version.version  + '</option>');
							$('#nwProtocol').append(
									'<option value=' + JSON.stringify(device.networkProtocol.name) + '>'
											+ device.networkProtocol.name + '</option>');
							$('#dsName').val(device.datasourceName);
							$('#m_deviceIP').val(device.ip);
							$('#m_connectedPort').val(device.connectedPort);
							$('#m_writebackPort').val(device.writeBackPort);
							
							if (device.isPublishing) {
								$('#m_createDsGroup').show();
								$('#prevdsName').val(device.datasourceName);
								$("#publish").prop('checked', true);
							} else {
								$("#publish").prop('checked', false);
							}
							$('#config').val(JSON.stringify(device.configurations));
							var tagsObjArray = device.tags ;
							var tagsStringArray = [] ;
							if(tagsObjArray != undefined){
								$.each(tagsObjArray,function(i){
									   tagsStringArray.push(tagsObjArray[i].name);
									});
								
								var multiselect = $("#tags").data("kendoMultiSelect");
								multiselect.value(tagsStringArray);
							}
						},
						error : function(xhr, status, error) {
							var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
							staticNotification.show({
				                message: errorMessage
				            }, "error");
						
						}
					});

				}
				
					$(document).on('keyup', '.numericTextBox', function(event) {
						   var v = this.value;
						   if($.isNumeric(v) === false) {
						        //chop off the last char entered
						        this.value = this.value.slice(0,-1);
						   }						   
					});				
			});
	
	
	
	
	//method to edit device
	function editDevice() {
		
		//enable input fields
		$("#deviceContainer").find(":text").attr("readonly", false);
		$("#deviceContainer").find("textarea").attr("readonly", false);
		$("#deviceContainer").find(":checkbox").attr("disabled", false);
		$("#deviceContainer").find("select").attr("disabled", false);
		var multiselect = $("#tags").data("kendoMultiSelect");
		multiselect.enable(true);
		$("#btn-create").hide();
		$("#btn-edit").hide();
		$("#btn-view").hide();
		$("#btn-update").show();
		if($('#dsName').val() != null || $('#dsName').val() != ''){
		$('#dsName').attr("readonly", true);
		}
		populateDeviceMake($('#make').val());
 		populateType($('#make').val(),$('#deviceType').val());
  		populateModel($('#model').val());
		populateProtocol($('#protocol').val());
		populateProtocolVersion($('#version').val());
     	populateNwProtocol($('#nwProtocol').val());
		
	}

	//method to update device
	function updateDevice() {
		
		var validator = $("#deviceContainer").kendoValidator({
	        onfocusout: true,
	        onkeyup: true,
	        validateOnBlur: false,
			rules : {
				greaterdate : function(input) {
					return writebackValidatorUpdate(input);
				}
			},
			messages : {
				greaterdate : function(input) {
					return input.attr("data-val-greaterdate");
				}
			}
	    }).data("kendoValidator");
		
		if (validator.validate()){
		
		//get device data url
		var url = "${getDeviceUrl}";
	    url = url.replace("{source_id}", $('#prevSourceId').val());
	    
		
		 var device = {};
		 device["sourceId"] = $('#sourceId').val();

		 device["ip"] = $('#m_deviceIP').val();
		 device["connectedPort"] = $('#m_connectedPort').val();
		 device["writeBackPort"] = $('#m_writebackPort').val();
			
		 var subscription = {};
		 subscription["subId"] = 1;
		 device["subscription"] = subscription;
		 
		 if ($('#publish').is(':checked')) {
		 device["datasourceName"] = $('#dsName').val();
		 device["isPublishing"] = true;
		 }
		 else{
			 device["isPublishing"] = false;
		 }
		 		 
		 var networkProtocol = {};
		 networkProtocol["name"] = $('#nwProtocol option:selected').val();
		 device["networkProtocol"] = networkProtocol;
		 	 
		 var tags = [];
		 var multiselect = $("#tags").data("kendoMultiSelect");
		 var tagSplits = multiselect.value();
		 $.each(tagSplits,function(i){
			   var tag = {};
			   tag.name = tagSplits[i];
			   tags.push(tag);
			});
		 device["tags"] = tags;
		 
		 device["configurations"] = $('#config').val();	
		//version
			var version = {};
			
			version["make"] = $('#make option:selected').val();
			version["deviceType"] = $('#deviceType option:selected').val();
			version["model"] = $('#model option:selected').val();
			version["protocol"] = $('#protocol option:selected').val();
			version["version"] = $('#version option:selected').val();
			
			device["version"] = version;
			
		 $.ajax({
			url : "ajax" + url,
			contentType: 'application/json; charset=utf-8',
			dataType : 'json',
			data: JSON.stringify(device),
			type : "PUT",
			success : function(response) {
				
				 staticNotification.show({
                     message:$("#sourceId").val() +" updated successfully"
                 }, "success");
                 $('#prevSourceId').val($('#sourceId').val());
				 //refreshManageGrid();
				 $('#tags').data('kendoMultiSelect').dataSource.read();
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
	                message: errorMessage
	            }, "error");
			
			}
		});
		}
	}
	
	//populate device make drop down
	function populateDeviceMake(make){
		$.ajax({
			url : "ajax" + "${deviceMakeUrl}",
			dataType : 'json',
			type : "GET",
			success : function(response) {
				
				if (response.entity != null) {
					var dataSource = $("#make").kendoComboBox({
						   dataTextField: "name",
						   dataValueField: "name",
						   filter: "contains",
					       suggest: true
						 }).data().kendoComboBox;
					dataSource.dataSource.data(response.entity);
					dataSource.text(make); 
					dataSource.value(make); 
				}
			},
			error : function(xhr, status, error) {
				var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
				staticNotification.show({
	                message: errorMessage
	            }, "error");
			
			}
		});
	}
	
	//change type onchnage of make
	 
	$('#make').change(function() {
		clearAndDisableCombo("deviceType");
		if ($('#make').val() != null && $('#make').val() != '') {
		enableCombo("deviceType");
		populateType($('#make').val(), null);
		}
		clearAndDisableCombo("model");
		clearAndDisableCombo("protocol");
		clearAndDisableCombo("version");	

	});

	
	//populate device type drop down on the basis of make
	function populateType(make, deviceType) {
		var url = "ajax" + "${deviceTypeUrl}";
		url = url.replace("{make_name}", make);

		$
				.ajax({
					url : url,
					dataType : 'json',
					type : "GET",
					success : function(response) {

						if (response.entity != null) {
							var data = $("#deviceType").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name",
								   filter: "contains",
							       suggest: true
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							data.text(deviceType);
							data.value(deviceType);
						}
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//change model onchange of type
	$('#deviceType').change(function() {
		clearAndDisableCombo("model");
		if ($('#deviceType').val() != null && $('#deviceType').val() != '') {
		enableCombo("model");
		populateModel(null);
		}
		clearAndDisableCombo("protocol");
		clearAndDisableCombo("version");
	});

	//populate device model drop down on the basis of type
	function populateModel(model) {
		var make = $("#make").val();
		var type = $("#deviceType").val();
		var payload = {
			"make" : make,
			"deviceType" : type
		};
		$
				.ajax({
					url : "ajax" + "${deviceModelUrl}",
					dataType : 'json',
					type : "POST",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(payload),
					success : function(response) {

						if (response.entity != null) {
							var data = $("#model").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name",
								   filter: "contains",
							       suggest: true
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							data.text(model);
							data.value(model);
						}
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//change protocol onchange of model
	$('#model').change(function() {
		clearAndDisableCombo("protocol");
		if ($('#model').val() != null  && $('#model').val() != '') {
		enableCombo("protocol");
		populateProtocol(null);
		}
		clearAndDisableCombo("version");
	});

	//populate protocol drop down
	function populateProtocol(protocol) {
		var make = $("#make").val();
		var type = $("#deviceType").val();
		var model = $("#model").val();
		var payload = {
			"make" : make,
			"deviceType" : type,
			"model" : model
		};
		$
				.ajax({
					url : "ajax" + "${deviceProtocolsUrl}",
					dataType : 'json',
					type : "POST",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(payload),
					success : function(response) {
						if (response.entity != null) {

							var data = $("#protocol").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name",
								   filter: "contains",
							       suggest: true
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							data.text(protocol);
							data.value(protocol);
						}
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//change version onchange of protocol
	$('#protocol').change(function() {
		clearAndDisableCombo("version");
		if ($('#protocol').val() != null && $('#protocol').val() != '') {
		enableCombo("version");
		populateProtocolVersion(null);
		}
		
	});

	//populate protocol drop down
	function populateProtocolVersion(version) {
		var make = $("#make").val();
		var type = $("#deviceType").val();
		var model = $("#model").val();
		var protocol = $("#protocol").val();
		var payload = {
			"make" : make,
			"deviceType" : type,
			"model" : model,
			"protocol" : protocol
		};
		$
				.ajax({
					url : "ajax" + "${deviceProtocolVersionUrl}",
					dataType : 'json',
					type : "POST",
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(payload),
					success : function(response) {

						if (response.entity != null) {
							var data = $("#version").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name",
								   filter: "contains",
							       suggest: true
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							data.text(version);
							data.value(version);
						}
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//populate network protocol drop down
	function populateNwProtocol(nwProtocol) {
		$
				.ajax({
					url : "ajax" + "${deviceNwProtocolUrl}",
					dataType : 'json',
					type : "GET",
					success : function(response) {
						if (response.entity != null) {
							var data = $("#nwProtocol").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name",
								   filter: "contains",
							       suggest: true
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							data.text(nwProtocol);
							data.value(nwProtocol);
						}
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//clears the kendo combo box
	function clearAndDisableCombo(id) {
		var dataSource = $("#" + id).data("kendoComboBox");
		dataSource.value("");
		dataSource.text("");
		var data = new kendo.data.DataSource({
			data : []
		});
		dataSource.setDataSource(data);
		dataSource.enable(false);
	}
		
	//enable combobox
	function enableCombo(id) {
		var dataSource = $("#" + id).data("kendoComboBox");
		dataSource.enable(true);
	}
	
	//tag configure component
	function configureTagComponent() {

		var newitemtext;

		var url = "ajax" + "${deviceTagsUrl}";

		var multiselect = $("#tags")
				.kendoMultiSelect(
						{
							change : function() {
								$('#tags_taglist li span:first-child').each(
										function() {
											$(this).text(
													$(this).text().replace(
															" (Add New)", ""));
										});
							},
							dataBound : function() {
								if ((newitemtext || this._prev)
										&& newitemtext != this._prev) {
									newitemtext = this._prev;

									var dataitems = this.dataSource.data();

									for (var i = 0; i < dataitems.length; i++) {
										var dataItem = dataitems[i];

										if (dataItem.value != dataItem.name) {
											this.dataSource.remove(dataItem);
										}
									}

									var found = false;
									for (var i = 0; i < dataitems.length; i++) {
										var dataItem = dataitems[i];
										if (dataItem.name == newitemtext) {
											found = true;
										}
									}

									if (!found) {
										this.open();
										this.dataSource.add({
											name : newitemtext + " (Add New)",
											name : newitemtext
										});

									}
								}
							},
							dataSource : {
								type : "json",
								transport : {
									read : url
								},
								schema : {
									data : function(response) {
										if (response.entity != null) {
											return response.entity;
										} else {
											var errorMessage = jQuery
													.parseJSON(xhr.responseText).errorMessage.errorMessage;
											staticNotification.show({
												message : errorMessage
											}, "error");
										}

									},
									total : function(response) {
										return $(response.entity).length;
									},
									model : {
										fields : {
											name : {
												type : "string",
											}
										}
									}
								}
							},
							dataTextField : "name",
							dataValueField : "name",
							animation : false
						});
	}

	function handleMPublish() {
		if ($('#publish').is(':checked')) {
			if ($('#prevdsName').val() == null || $('#prevdsName').val() == '') {
				fetchDataSourceName($('#prevSourceId').val());
			} else {
				$('#dsName').val($('#prevdsName').val());
			}
			$('#m_createDsGroup').show();
		} else {
			$('#m_createDsGroup').hide();
		}
	}
</script>


<body>

	<div class="container" id="deviceContainer">
		<div class="row">


			<div class="col-md-5">
				<fieldset id="deviceConfigFieldSet">
					<legend>Device Configuration:</legend>
					<div class="col-md-5">

						<div class="form-group">
							<label class="control-label" for="name">Source Identifier</label>
							<div class="">
								<input id="sourceId" name="Host address/ IMEI" type="text"
									placeholder="Source ID" class="k-textbox selectWidth"
									validationMessage="Enter valid Source Identifier" required>
								<input id="prevSourceId" type="hidden" />
							</div>
						</div>

						<div class="form-group">
							<label class="" for="tags">Tags</label> <select name="tags"
								class="k-textbox input-small selectWidth" id="tags">
							</select>
						</div>

						<div class="form-group">
							<label class="" for="make">Make</label> <select name="make"
								class="k-textbox input-small selectWidth" id="make"
								validationMessage="Enter valid Device Make" required>>
							</select>
						</div>

						<div class="form-group" id="device">
							<label class="" for="deviceType">Type</label> <select
								name="deviceType" class="k-textbox input-small selectWidth"
								id="deviceType" validationMessage="Enter valid Device Type"
								required>
							</select>
						</div>

						<div class="form-group">
							<label class="" for="model">Model</label> <select name="model"
								class="k-textbox input-small selectWidth" id="model"
								validationMessage="Enter valid Device Model" required>
							</select>
						</div>

						<div class="form-group">
							<label class="" for="protocol">Protocol</label> <select
								name="protocol" class="k-textbox input-small selectWidth"
								id="protocol" validationMessage="Enter valid Protocol"
								required>
							</select>
						</div>

						<div class="form-group">
							<label class="" for="version">Version</label> <select
								name="version" class="k-textbox input-small selectWidth"
								id="version" validationMessage="Enter valid Version" required>
							</select>
						</div>

					</div>

				</fieldset>
			</div>



			<div class="col-md-5">

				<fieldset id="ipConfigFieldSet">
					<legend>Ip Configuration:</legend>

					<div class="form-group">
						<label class="" for="deviceType">Network Protocol </label> <select
							name="nwProtocol" class="k-textbox input-small selectWidth"
							id="nwProtocol" validationMessage="Enter Network Protocol"
							required>
						</select>
					</div>

					<div class="form-group" id="device">
						<label for="m_deviceIP">Device IP</label>
						<div class="">
							<input name="m_deviceIP" class="k-textbox selectWidth"
								id="m_deviceIP" placeholder="Device IP"
								class="k-textbox selectWidth"
								validationMessage="Enter Valid Device IP">
						</div>
					</div>

					<div class="form-group">
						<label for="m_connectedPort">Device Port</label>
						<div class="">
							<input name="m_connectedPort"
								class="k-textbox selectWidth numericTextBox"
								id="m_connectedPort" placeholder="Device Port" maxlength="5"
								validationMessage="Enter Valid Device Port">
						</div>
					</div>

					<div class="form-group">
						<label for="m_writebackPort">Writeback Port</label>
						<div class="">
							<input name="m_writebackPort"
								class="k-textbox selectWidth numericTextBox"
								id="m_writebackPort" placeholder="Writeback Port" maxlength="5"
								validationMessage="Enter Valid Writeback Port">
						</div>
					</div>

					<div id="m_createDsGroup" class="form-group" style="display: none;">
						<label class="control-label" for="ds-name">Datasource Name</label>
						<div class="">
							<input id="dsName" name="dsName" type="text"
								placeholder="DataSource Name" class="k-textbox selectWidth"
								validationMessage="Enter valid Datasource Name"> <input
								id="prevdsName" type="hidden" />
						</div>
					</div>

					<div class="">
						<span class="dcCheckbox"> <input type="checkbox"
							id="publish" class="k-checkbox" onclick="handleMPublish()">
							<label class="k-checkbox-label" for="publish">Publish</label>
						</span>
					</div>
					<div class="form-group dc-right">

						<button type="submit" id="btn-edit" class="k-button k-primary"
							onclick="editDevice()">Edit</button>
						<button type="submit" id="btn-view" class="k-button k-primary">View</button>
						<button type="submit" id="btn-update" class="k-button k-primary"
							onclick="updateDevice()">Save</button>
					</div>

				</fieldset>


			</div>

		</div>
	</div>