/**
 * for grid template 
 * editors / templates / logic
 * PCSEG339 Kenny Chavez 
 */
  
    // util functions
    var paramDs = [{text: "i1", value: "1"}, {text: "i2", value: "2"}, {text: "i3", value: "3"}];
    var templateGridName;
    // elements with API integrations
    
    // device IO dropdown list
     function deviceIoDropDownEditor(container, options) {
    	 
    	 
		 var targetUrl = "ajax" + getAllParams;
		 targetUrl = targetUrl.replace("{sub_id}",1); // change this to token in the future
		 
		  
    	 
         $('<input id="devIODropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
            	 dataSource: {
                	 type: "json",
                	 transport: {
    	    		    read: {
    	    		      url: targetUrl	    		      
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
                 index: 3             
         });
		 
     	     			
       }
	
   // Parameter dropdown list
     function parameterNameDropDownEditor(container, options) {
    	 
		 var targetUrl = "ajax" + getAllParams;
		 targetUrl = targetUrl.replace("{sub_id}",1); // change this to token in the future
		
		 $('<input id="paramNamesDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
            	 dataSource: {
                	 type: "json",
                	 transport: {
                		 read: function(options) {
                			// setHeader();
                			 $.ajax({
                					url : targetUrl,
                					type : 'GET',
                					dataType : 'JSON',	                					                  					
                					success : function(response) {   
                						
//                						var defaultSelection = {"id": 0,
//                					            "name": "Undefined",
//                					            "dataType": "N-A",
//                					            "physicalQuantity": "N-A"};
//                						
//                						response.entity.push(defaultSelection);
                						
                						return options.success(response.entity);
                					
                					},
                					error : function(xhr, status, error) {
                						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
                						staticNotification.show({
                							message: errorMessage
                						}, "error");
                					}
                				});	                			 
                			 
                		    } 
                	 },
                	 schema : {
//    						data : function(response) {
//    							if (response.entity != null) {
//    								return response.entity;
//    							} 
//
//    						},
    						total : function(response) {
    							return $(response.entity).length;
    						},
    						model : {
    							fields : {
    								name : {
    									type : "string"
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
                 change : function(e){
                	 
                	 	//console.log("//currentGrid: " + currentGrid);
						var thisGrid = $("#"+currentGrid).getKendoGrid();  
	                    var item = thisGrid.dataItem(this.element.closest("tr"));
	                    
	                    //console.log("//selected item: " + JSON.stringify(item));
						
	                    item.set("physicalQuantity",item.parameterName.physicalQuantity);
	                    item.set("dataType",item.parameterName.dataType);
	                  
                 }
         });
		 
       }
     
       
        // System Tag dropdown list
     function systemTagDropDownEditor(container, options) {
          $('<input id="systemTagDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
            	 dataSource: {
                	 type: "json",
                	 transport: {
                		 read: function(options) {
                			 
                			 // change me
                			 targetUrl = "ajax" + getAllSystemTags;
                			
                			 
                			 $.ajax({
                					url : targetUrl,
                					type : 'GET',
                					dataType : 'JSON',	                					                  					
                					success : function(response) {   
                						
                						return options.success(response.entity);
                					
                					},
                					error : function(xhr, status, error) {
                						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
                						staticNotification.show({
                							message: errorMessage
                						}, "error");
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
    									type : "string"
    								}
    							}
    						}
    					}
                 },
                 dataTextField: "name",
                 dataValueField: "name",               
                 filter: "contains",
                 suggest: true,
                 index: 3
         });
       }
     
	   // Access dropdown list
     function accessDropDownEditor(container, options) {
    	 
    	 
    	 var targetUrl = "ajax" + getAllAccessTypes;
    	 
          $('<input id="accessDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
             dataSource: {
            	 type: "json",
            	 transport: {
	    		    read: {
	    		      url: targetUrl	    		      
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
             index: 3
         });
       }
       
          // Data Type dropdown list
     function dataTypeDropDownEditor(container, options) {
    	 
    	 
    	 var targetUrl = "ajax" + getAllDataTypes;
    	 
          $('<input id="dataTypeDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
            	 dataSource: {
                	 type: "json",
                	 transport: {
    	    		    read: {
    	    		      url: targetUrl	    		      
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
             filter: "contains",
             suggest: true,
             index: 3
         });
       }
       
             // Custom tag dropdown list
     function customTagDropDownEditor(container, options) {
    	 var newitemtext;

          $('<input id="customTagDropDown" data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoMultiSelect({
            	 change : function() {
						$('#tags_taglist li span:first-child').each(
								function() {
									$(this).text(
											$(this).text().replace(
													" (Add New)", ""));
								});
					},
					dataBound : function() {
						if((newitemtext || this._prev) && newitemtext != this._prev)
							{
								newitemtext = this._prev;
			
								var dataitems = this.dataSource.data();
			
								for (var i = 0; i < dataitems.length; i++) {
								    var dataItem = dataitems[i];
			
									if(dataItem.value != dataItem.name) {
										this.dataSource.remove(dataItem);
									}
								}
			
								
								var found = false;
								for (var i = 0; i < dataitems.length; i++) {
								    var dataItem = dataitems[i];
								    if(dataItem.name == newitemtext) {
								    	found = true;
								    }
								}
			
								if(!found)
								{
									this.open();
									this.dataSource.add({name: newitemtext + " (Add New)", name: newitemtext});
									
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
									//console.log(JSON.stringify(response.entity));
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
					animation : false

         });

       }
       	
       	     // Unit dropdown list
     function unitDropDownEditor(container, options) {
          $('<input id="unitDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
             .appendTo(container)
             .kendoComboBox({
             dataSource: [
						{"name": "Unitless"},
						{"name": "Δ/cm"},
						{"name": "Θ/hr"},
						{"name": "Ξ/sec"},
						{"name": "Φ/kelvin"}
					],
             filter: "contains",
             suggest: true,
             index: 3
         });
       }

     

   //Parameter dropdown list
   function parameterNameDropDownEditorEditTemplate(container, options) {
   	 
   	 var targetUrl = "ajax" + getAllParams;
   	 targetUrl = targetUrl.replace("{sub_id}",1); // change this to token in the future
   	
   	 $('<input id="paramNamesDropDown" required data-text-field="name" data-value-field="name" data-bind="value:' + options.field + '" />')
           .appendTo(container)
           .kendoComboBox({
          	 dataSource: {
              	 type: "json",
              	 transport: {
              		 read: function(options) {
              			// setHeader();
              			 $.ajax({
              					url : targetUrl,
              					type : 'GET',
              					dataType : 'JSON',	                					                  					
              					success : function(response) {   
              						
              						//console.log("//---> res: " + JSON.stringify(response));
//              						var defaultSelection = {"id": 0,
//              					            "name": "Undefined",
//              					            "dataType": "N-A",
//              					            "physicalQuantity": "N-A"};
//              						
//              						response.entity.push(defaultSelection);
              						
              						return options.success(response.entity);
              					
              					},
              					error : function(xhr, status, error) {
              						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
              						staticNotification.show({
              							message: errorMessage
              						}, "error");
              					}
              				});	                			 
              			 
              		    } 
              	 },
              	 schema : {
//   						data : function(response) {
//   							if (response.entity != null) {
//   								return response.entity;
//   							} 
   //
//   						},
   						total : function(response) {
   							return $(response.entity).length;
   						},
   						model : {
   							fields : {
   								name : {
   									type : "string"
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
               change : function(e){
              	 
              	 	//console.log("//currentGrid: " + currentGrid);
              	 	//console.log("//e: " + e);
              	 	
              	 	
   				var thisGrid = $("#"+currentGrid).getKendoGrid();  
                   var item = thisGrid.dataItem(this.element.closest("tr"));
                   var data = thisGrid.dataSource.data();
                                   
                   //console.log("//selected item: " + JSON.stringify(item));
                   //console.log("//grid data: " + JSON.stringify(data));                 			
                   
                   
                   $.ajax({
      					url : targetUrl,
      					type : 'GET',
      					dataType : 'JSON',	                					                  					
      					success : function(response) {   
      						
      						//console.log("//---> res: " + JSON.stringify(response));

      						$.each(response.entity, function (){
      							var paramName = this.name;
      							if (paramName == item.parameterName){
      				                item.set("physicalQuantity",this.physicalQuantity);
      				                item.set("dataType",this.dataType);
      							}
      							
      						});
      						
      					
      					},
      					error : function(xhr, status, error) {
      						var errorMessage = jQuery.parseJSON(xhr.responseText).errorMessage.errorMessage;
      						staticNotification.show({
      							message: errorMessage
      						}, "error");
      					}
      				});
                   
                   
                   
//          			var grid =$("#writebackgrid"+sourceId).data("kendoGrid");
//          			var data = grid.dataSource.data();
//          			// Find those records that have requestId
//          			// and return them in `res` array
//          			var res = $.grep(data, function (d) {
//          			    return d.deviceIO == reqId;
//          			});
//                      
//      				var dataItem =grid.dataSource.get(res[0].id);
//   				
//   				dataItem.set("lastUpdated",requestedTime);
//   				dataItem.set("status",status);
                    
               }
       });
   	 
     }