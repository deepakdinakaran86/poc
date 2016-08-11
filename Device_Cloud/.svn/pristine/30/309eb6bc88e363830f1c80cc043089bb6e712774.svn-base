<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="deviceTypeUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.type')" />
<spring:eval var="deviceNwProtocolUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.nwProtocol')" />
<spring:eval var="deviceProtocolUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.protocol')" />
<spring:eval var="deviceTagsUrl"
	expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
<spring:eval var="isDsExistUrl"
	expression="@propertyConfigurer.getProperty('datasource.protocol.datasourcename.isexists')" />
	<spring:eval var="deviceUrlDtls" expression="@propertyConfigurer.getProperty('datasource.device.getDeviceDetails')" />

<spring:eval var="getAllMake" expression="@propertyConfigurer.getProperty('datasource.device.make')" />
<spring:eval var="getAllDeviceTypes" expression="@propertyConfigurer.getProperty('datasource.device.deviceTypes')" />
<spring:eval var="getAllModels" expression="@propertyConfigurer.getProperty('datasource.device.models')" />
<spring:eval var="getAllProtocols" expression="@propertyConfigurer.getProperty('datasource.device.protocols')" />
<spring:eval var="getAllProtocolVersions" expression="@propertyConfigurer.getProperty('datasource.device.protocolVersions')" />
<spring:eval var="getAllDeviceTags" expression="@propertyConfigurer.getProperty('datasource.device.tags')" />
<spring:eval var="createDevice" expression="@propertyConfigurer.getProperty('datasource.device.createDevice')" />
<spring:eval var="getAllStudents"
	expression="@propertyConfigurer.getProperty('datasource.all.students')" />
<!-- Masking -->
<script src="resources/js/jquery.mask.min.js"></script>
<style>
.form-group{
display: inline-block;
}
</style>
<script>

function loadMake(){
	
	// based from config template,
	// do an ajax call to API to get
	// make list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadDevice();

	var getAllMake = "${getAllMake}";

	 $("#createDeviceMake").kendoComboBox({
        dataSource: {
       	 type: "json",
       	 transport: {
   		    read: {
   		      url: "ajax" + getAllMake	    		      
   		    } 
       	 },
       	 schema : {
					data : function(response) {
						if (response.entity != null) {
							return response.entity;
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
        dataTextField: "name",
        dataValueField: "name",
        filter: "contains",
        suggest: true,
        index: 3,
        change: function(e){   
        	clearAndDisableText("createDeviceType");
        	if ($('#createDeviceMake').val() != null && $('#createDeviceMake').val() != '') {
        	enableCombo("createDeviceType");
        	var make = this.text(); //selected make  
        	loadDevice(make);
        	}
        	clearAndDisableText("createDeviceModel");
        	clearAndDisableText("createDeviceProtocol");
        	clearAndDisableText("createProtocolVersion");        	
        }
    });
	
	
}

// method to load device dropdown

function loadDevice(make){
	
	// based from device selection,
	// do an ajax call to API to get
	// device list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadModel();
	var getAllDeviceTypes = "${getAllDeviceTypes}";
	
	var url = "ajax"+ getAllDeviceTypes;
	url = url.replace("{make_name}",make);

	 $("#createDeviceType").kendoComboBox({
	        dataSource: {
	       	 type: "json",
	       	 transport: {
	   		    read: {
	   		      url: url	    		      
	   		    } 
	       	 },
	       	 schema : {
						data : function(response) {
							if (response.entity != null) {
								return response.entity;
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
	        dataTextField: "name",
	        dataValueField: "name",
	        filter: "contains",
	        suggest: true,
 	        index: 3,
	        change: function(e){   
	        	clearAndDisableText("createDeviceModel");
	    		if ($('#createDeviceType').val() != null && $('#createDeviceType').val() != '') {
	         	enableCombo("createDeviceModel");
	         	var device = this.text(); //selected make
 	        	loadModel(device);
	    		}
	        	clearAndDisableText("createDeviceProtocol");
	        	clearAndDisableText("createProtocolVersion");
	        	
	        }
	    });
	
}

//method to load model dropdown

function loadModel(device){
	
	// based from device selection,
	// do an ajax call to API to get
	// model list then populate
	// to drop down.
	
	// on change of drop down,
	// it will call loadProtocol();
	
	// loadProtocol(Device,Make);
	var getAllModels = "${getAllModels}";
	
	var make = $("#createDeviceMake").data("kendoComboBox").text();
		
	var payload = 
			{
			  "make": make,
			  "deviceType": device
			
			}; 
	
	$("#createDeviceModel").kendoComboBox({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllModels,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) {
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
//   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
//   						staticNotification.show({
//   							message: errorMessage
//   						}, "error");
   					}
   				});	                			 
   			 
   		    } 
       	 },
       	 schema : {
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
        dataTextField: "name",
        dataValueField: "name",
        filter: "contains",
        suggest: true,
        index: 3,
        change: function(e){   
        	clearAndDisableText("createDeviceProtocol");
    		if ($('#createDeviceModel').val() != null && $('#createDeviceModel').val() != '') {
        	enableCombo("createDeviceProtocol");
        	var device = this.text(); //selected make        	
        	loadProtocol(device);
        }
        	clearAndDisableText("createProtocolVersion");
        }
    });
	
}

//method to loadProtocol dropdown

function loadProtocol(){
	
	// based from device/model,
	// do an ajax call to API to get
	// protocol version list then populate
	// to drop down.
	
	// parameter mapping grid will be enabled.
	var getAllProtocols = "${getAllProtocols}";
	
	var getAllStudents = "${getAllStudents}";
	
	var make = $("#createDeviceMake").data("kendoComboBox").text();
	var device = $("#createDeviceType").data("kendoComboBox").text();
	var model = $("#createDeviceModel").data("kendoComboBox").text();
	
	var payload = 
	{
	  "make": make,
	  "deviceType": device,
	  "model": model
	
	};
	
	$("#createDeviceProtocol").kendoComboBox({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllProtocols,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) {
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
//   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
//   						staticNotification.show({
//   							message: errorMessage
//   						}, "error");
   					}
   				});	                			 
   			 
   		    } 
       	 },
       	 schema : {
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
        dataTextField: "name",
        dataValueField: "name",
        filter: "contains",
        suggest: true,
        index: 3,
        change: function(e){   
        	clearAndDisableText("createProtocolVersion");
    		if ($('#createDeviceProtocol').val() != null && $('#createDeviceProtocol').val() != '') {
        	enableCombo("createProtocolVersion");
        	var device = this.text(); //selected make        	
        	loadProtocolVersion();
            }	
        }
    });
	
	
}


//method to loadProtocol Version dropdown

function loadProtocolVersion(){
	
	// based from device/model,
	// do an ajax call to API to get
	// protocol version list then populate
	// to drop down.
	
	// parameter mapping grid will be enabled.
	var getAllProtocolVersions = "${getAllProtocolVersions}";
	var make = $("#createDeviceMake").data("kendoComboBox").text();
	var device = $("#createDeviceType").data("kendoComboBox").text();
	var model = $("#createDeviceModel").data("kendoComboBox").text();
	var protocol = $("#createDeviceProtocol").data("kendoComboBox").text();
	
	
	var payload = 
	{
	  "make": make,
	  "deviceType": device,
	  "model": model,
	  "protocol": protocol
	};
	
	$("#createProtocolVersion").kendoComboBox({
        dataSource: {
       	 type: "json",
       	 transport: {
       		read: function(options) {
   			 
   			 $.ajax({
   					url : "ajax" + getAllProtocolVersions,
   					type : 'POST',
   					dataType : 'JSON',
   					contentType : "application/json; charset=utf-8",
   					data : JSON.stringify(payload),
   					success : function(response) { 						
   						return options.success(response.entity);
   					
   					},
   					error : function(xhr, status, error) {
   						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
   						staticNotification.show({
   							message: errorMessage
   						}, "error");
//   						var response = {"name":"1.0.0"};
//   						return options.success(response);
   					}
   				});	                			 
   			 
   		    } 
       	 },
       	 schema : {
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
        dataTextField: "name",
        dataValueField: "name",
        filter: "contains",
        suggest: true,
        index: 3,
        change: function(e){   

        	var protocolVersion = this.text();        	
       	    
        }
    });
	
	
}


var createDeviceValidator = null;
	$(document).ready(function() {
		 $('#c_deviceIP').mask('099.099.099.099');
		//populateDeviceType();

		populateNwProtocol();
		
		
		
	$(document).on('keyup', '.numericTextBox', function(event) {
			var v = this.value;
			if ($.isNumeric(v) === false) {
				//chop off the last char entered
				this.value = this.value.slice(0, -1);
			}
		});

		//kendovalidator
		createDeviceValidator = $("#createDeviceContainer").kendoValidator({
			validateOnBlur : true,
			rules : {
				greaterdate : function(input) {
					return writebackValidatorCreate(input);
				},
				available:function(input){
					return isDSExists(input);
				}
			},
			messages : {
				greaterdate : function(input) {
					return input.attr("data-val-greaterdate");
				}
			},
			available: function(input) {
				return input.data("available-msg");
			}
		}).data("kendoValidator");

		
		 loadMake();


	});

	//method to save device
	function saveDevice() {
	
	
		if (createDeviceValidator.validate()) {
			//get device data url
			var url = "${createDevice}"; 
			
			var device = {};
			device["sourceId"] = $('#createSourceId').val();

			device["ip"] = $('#c_deviceIP').val();
			device["connectedPort"] = $('#c_connectedPort').val();
			device["writeBackPort"] = $('#c_writebackPort').val();

			if ($('#createPublish').is(':checked')) {
				device["isPublishing"] = true;
				device["datasourceName"] = $('#createDsName').val();
			} else {
				device["isPublishing"] = false;
			}

			var networkProtocol = {};
			networkProtocol["name"] = $('#createNwProtocol option:selected')
					.val();
			device["networkProtocol"] = networkProtocol;

			var tags = [];
			var multiselect = $("#createTags").data("kendoMultiSelect");
			var tagSplits = multiselect.value();
			$.each(tagSplits, function(i) {
				var tag = {};
				tag.name = tagSplits[i];
				tags[i] = tag;
			});
			device["tags"] = tags;
			
			//version
			var version = {};
			
			version["make"] = $('#createDeviceMake option:selected').val();
			version["deviceType"] = $('#createDeviceType option:selected').val();
			version["model"] = $('#createDeviceModel option:selected').val();
			version["protocol"] = $('#createDeviceProtocol option:selected').val();
			version["version"] = $('#createProtocolVersion option:selected').val();
			
			device["version"] = version;

						

			device["configurations"] = $('#createConfig option:selected').val();
			
			$
					.ajax({
						url : "ajax" + url,
						contentType : 'application/json; charset=utf-8',
						dataType : 'json',
						data : JSON.stringify(device),
						type : "POST",
						success : function(response) {
							staticNotification.show({
								message : $("#createSourceId").val()
										+ " created successfully"
							}, "success");
							
							$("#createDeviceContainer").find(":text").val("");
							$("#createDeviceContainer").find("textarea")
									.val("");
							$("#createDeviceContainer").find(":checkbox").prop(
									'checked', false);
							var multiSelect = $('#createTags').data(
									'kendoMultiSelect');
							multiSelect.value([]);

 							$('#createDeviceType option:first-child').attr(
 									'selected', 'selected');

 							var deviceJson = {};
 							deviceJson["name"] = $(
 									'#createDeviceType option:first-child')
									.val();

							$('#createNwProtocol option:first-child').attr(
									'selected', 'selected');
							$('#createTags').data('kendoMultiSelect').dataSource
									.read();
							
							//refreshManageGrid();
						},
						error : function(xhr, status, error) {
							
							var errorMessage = jQuery
									.parseJSON(xhr.responseText).errorMessage.errorMessage;
							staticNotification.show({
								message : errorMessage
							}, "error");

						}
					});
			enableCombo("createDeviceMake");
			clearAndDisableText("createDeviceType");
	    	clearAndDisableText("createDeviceModel");
	    	clearAndDisableText("createDeviceProtocol");
	    	clearAndDisableText("createProtocolVersion");
	    	
	    	
	$('#createDeviceMake').change(function() {
		 		$('#createDeviceType').val()=="";
				var make = $('#createDeviceMake').val(); //selected make
				if ($('#createDeviceMake').val() != null && $('#createDeviceMake').val() != '') {
					enableCombo("createDeviceType");
					loadDevice(make);
				}
			});

		}

	}

	//populate device type drop down
	function populateDeviceType() {

		$
				.ajax({
					url : "ajax" + "${deviceTypeUrl}",
					dataType : 'json',
					type : "GET",
					success : function(response) {
						if (response.entity != null) {
							$('#createDeviceType').empty();
							$(response.entity)
									.each(
											function(index) {
												$('#createDeviceType')
														.append(
																'<option value='
																		+ JSON
																				.stringify(this.name)
																		+ '>'
																		+ this.name
																		+ '</option>');

											});
						}

						var deviceJson = {};
						deviceJson["name"] = $(
								'#createDeviceType option:selected').text();
						populateCreateProtocol(JSON.stringify(deviceJson));
					},
					error : function(xhr, status, error) {
						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
						staticNotification.show({
							message : errorMessage
						}, "error");

					}
				});
	}

	//populate protocol onchnage of devicetype
	$('#createDeviceType').change(function() {
		var deviceJson = {};
		deviceJson["name"] = $('#createDeviceType option:selected').text();
		//populateCreateProtocol(JSON.stringify(deviceJson));

	});

	//populate protocol drop down
	function populateCreateProtocol(device) {
		$('#createProtocolType').empty();
		$
				.ajax({
					url : "ajax" + "${deviceProtocolUrl}",
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					data : device,
					type : "POST",
					success : function(response) {
						if (response.entity != null) {
							$('#createProtocolType').empty();
							$(response.entity)
									.each(
											function(index) {
												$('#createProtocolType')
														.append(
																'<option value='
																		+ JSON
																				.stringify(this.name)
																		+ '>'
																		+ this.name
																		+ '</option>');
											});
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
	function populateNwProtocol() {
		$
				.ajax({
					url : "ajax" + "${deviceNwProtocolUrl}",
					dataType : 'json',
					type : "GET",
					success : function(response) {
						if (response.entity != null) {
							var data = $("#createNwProtocol").kendoComboBox({
								dataTextField : "name",
								dataValueField : "name"
							}).data().kendoComboBox;
							data.dataSource.data(response.entity);
							var nwProtocol = $('#createNwProtocol').val()
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

	configureTagComponent();

	//method to configure tag componenet
	function configureTagComponent() {

		var newitemtext;

		var url = "ajax" + "${deviceTagsUrl}";

		var multiselect = $("#createTags")
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
								schema : {
									total : function(response) {
										return $(response).length;
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

		$.ajax({
			url : 'ajax' + "${getAllDeviceTags}",
			dataType : 'json',
			type : "GET",
			success : function(response) {
				$("#createTags").data("kendoMultiSelect").dataSource.data(response.entity);
			},
			error : function(xhr, status, error) {
				console.log('eeee');
			}
		});
	}

	function handlePublish() {
		if ($('#createPublish').is(':checked')) {
			$('#createDsGroup').show();
		} else {
			$('#createDsGroup').hide();
		}
	}

	function clearAndDisableText(elementName) {

		var ddl = $("#" + elementName).kendoComboBox({
			dataTextField : "text",
			dataValueField : "value"
		}).data().kendoComboBox;
		ddl.dataSource.data([]);
		ddl.text(""); 
		ddl.value("");
		ddl.enable(false);
	}

	//enable combobox
	function enableCombo(id) {
		var dataSource = $("#" + id).data("kendoComboBox");
		dataSource.enable(true);
	}

</script>

<body>
	<div class="container" id="createDeviceContainer">
		<div class="row">

			<div class="col-md-5">
			<fieldset id="deviceConfigFieldset">
			 <legend>Device Configuration:</legend>
			<div class="col-md-5">


				<div class="form-group">
					<label class="control-label" for="name">Source Identifier</label>
					<div class="">
						<input id="createSourceId" name="Host address/ IMEI" type="text"
							placeholder="Source ID" class="k-textbox selectWidth"
							validationMessage="Enter valid Source Identifier" required>
					</div>
				</div>

				<div class="form-group">
					<label for="createTags">Tags</label> <select name="tags"
						class="k-textbox input-small selectWidth" id="createTags"></select>
				</div>

				<div class="form-group" id="make">
					<label class="control-label required" for="createDeviceMake">Make:</label>
							
				<select id="createDeviceMake" name="createDeviceMake" 
					class="k-textbox input-small selectWidth" validationMessage="Enter valid Make" required></select>
					<span class="k-invalid-msg" data-for="createDeviceMake"></span>
							

				</div>

				<div class="form-group" id="device">
					<label for="deviceType">Device Type</label> <select
						name="deviceType" class="k-textbox input-small selectWidth"
						id="createDeviceType" validationMessage="Enter valid Device Type"
						required>
					</select>

				</div>

				<div class="form-group">
					<label for="deviceModel">Model</label> <select
						name="deviceModel" class="k-textbox input-small selectWidth"
						id="createDeviceModel" validationMessage="Enter Model"
						required>
					</select>
				</div>

				<div class="form-group">
					<label for="deviceProtocol">Protocol </label> <select
						name="deviceProtocol" class="k-textbox input-small selectWidth"
						id="createDeviceProtocol" validationMessage="Enter Protocol Type"
						required>
					</select>
				</div>

				<div class="form-group">
					<label for="protocolVersion">Protocol Version</label> <select
						name="protocolVersion" class="k-textbox input-small selectWidth"
						id="createProtocolVersion" validationMessage="Enter Protocol Version"
						required>
					</select>
				</div>
				
			</div>
			</fieldset>
			</div>
			
			<div class="col-md-5">
			<fieldset id="ipConfigFieldSet">
				<legend>Ip Configuration:</legend>

				<div class="form-group">
					<label for="deviceType">Network Protocol </label> <select
						name="nwProtocol" class="k-textbox input-small selectWidth"
						id="createNwProtocol" validationMessage="Enter Network Protocol"
						required>
					</select>
				</div>
				

				<div class="form-group" id="device">
					<label for="c_deviceIP">Device IP</label>
					<div class="">
						<input name="c_deviceIP" class="k-textbox selectWidth"
							id="c_deviceIP" placeholder="Device IP"
							class="k-textbox selectWidth" validationMessage="Enter Valid Device IP">
					</div>

				</div>

				<div class="form-group">
					<label for="c_connectedPort">Device Port</label>
					<div class="">
						<input name="c_connectedPort" class="k-textbox selectWidth numericTextBox"
							id="c_connectedPort" placeholder="Device Port" maxlength="5" validationMessage="Enter Valid Device Port">
					</div>
				</div>

				<div class="form-group">
					<label for="c_writebackPort">Writeback Port</label>
					<div class="">
						<input name="c_writebackPort" class="k-textbox selectWidth numericTextBox"  maxlength="5"
							id="c_writebackPort" placeholder="Writeback Port" validationMessage="Enter Valid Writeback Port">
					</div>
				</div>
			
				<div id="createDsGroup" class="form-group" style="display: none;">
					<label class="control-label" for="ds-name">Datasource Name</label>
					<div class="">
						<input id="createDsName" name="createDsName" type="text"
							placeholder="DataSource Name" class="k-textbox selectWidth"
							validationMessage="Enter valid Datasource Name" data-available data-available-url="${isDsExistUrl}" data-available-msg="Datasource Name already exists">
					</div>
				</div>
				
				<div class="">
					<span class="dcCheckbox"> <input type="checkbox"
						id="createPublish" class="k-checkbox" onclick="handlePublish()"> <label
						class="k-checkbox-label" for="createPublish" >Publish</label>
					</span>
				</div>

				<div class="form-group dc-right">
					<button type="submit" id="btn-create" class="k-button k-primary"
						onclick="saveDevice()">Save</button>
				</div>
			
			</fieldset>
			</div>
			
	</div>


</body>