"use strict";

$(document).ready(function(){	
	console.log('Dashboard: ready');	
	FnVechileCountSummary();
	FnMileageVsFuelConsumptionChart();	
	FnAssetCountBasedOnType();
	FnAlertCountBasedOnCriticality();
	FnInitiateFullCalendar();
	
	//http://bootstrap-calendar.azurewebsites.net/
});

$(window).load(function() {
	
});

$(window).resize(function () {	
		FnReSizeKendoChart("mileage-chart");
		FnReSizeKendoChart("asset-count-chart");
		FnReSizeKendoChart("alert-count-chart");		
	});


function FnVechileCountSummary(){
	console.log('Dashboard: FnVechileCountSummary');
	
	$('#total-vehicle-connected > h3').html('100');
	$('#total-running-vehicle > h3').html('52');
	$('#total-idle-vehicle > h3').html('44');
	$('#total-stopped-vehicle > h3').html('4');
	/*
	total-vehicle-connected
	total-running-vehicle
	total-idle-vehicle
	total-stopped-vehicle
	*/
	
}
function FnMileageVsFuelConsumptionChart(){
	console.log('Dashboard: FnMileageVsFuelConsumptionChart');
	$(document).ajaxStart(function() { Pace.restart();
	console.log('loading');
	}); 
	/*
	 $(".export-pdf").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportPDF({ paperSize: "auto", margin: { left: "1cm", top: "1cm", right: "1cm", bottom: "1cm" } }).done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.pdf",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });

        $(".export-img").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportImage().done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.png",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });

        $(".export-svg").click(function() {
            var chart = $("#chart").getKendoChart();
            chart.exportSVG().done(function(data) {
                kendo.saveAs({
                    dataURI: data,
                    fileName: "chart.svg",
                    proxyURL: "//demos.telerik.com/kendo-ui/service/export"
                });
            });
        });
*/
      
            $("#mileage-chart").kendoChart({
                title: {
                    text: "",
                    font: "bold 16px 'DejaVu Sans'"
                },
                legend: {
                    position: "bottom",
                    labels: {
                        font: "12px 'DejaVu Sans'"
                    }
                },
                series: [{
                    type: "column",
                    data: [20, 40, 45, 30, 50,60],
                    stack: true,
                    name: "Mileage",
                    color: "#cc6e38"
                }, {
                    type: "column",
                    data: [20, 30, 35, 35, 40,60],
                    stack: true,
                    name: "Fuel",
                    color: "#ef955f"
                }, {
                    type: "line",
                    data: [30, 38, 40, 32, 42,60],
                    name: "Liner(Fuel)",
                    color: "#3c8dbc",
                    axis: "mpg"
                }, {
                    type: "line",
                    data: [7.8, 6.2, 5.9, 7.4, 5.6,5.8],
                    name: "Liner(Mileage)",
                    color: "#4e4141",
                    axis: "l100km"
                }],
                valueAxes: [{
                    title: { text: "miles" },
                    min: 0,
                    max: 100
                }, {
                    name: "km",
                    title: { text: "km" },
                    min: 0,
                    max: 161,
                    majorUnit: 32
                }, {
                    name: "mpg",
                    title: { text: "" },
                    color: "#ec5e0a"
                }, {
                    name: "l100km",
                    title: { text: "liters per 100km" },
                    color: "#4e4141"
                }],
                categoryAxis: {
                    categories: ["Sun","Mon", "Tue", "Wed", "Thu", "Fri"],
                    // Align the first two value axes to the left
                    // and the last two to the right.
                    //
                    // Right alignment is done by specifying a
                    // crossing value greater than or equal to
                    // the number of categories.
                    axisCrossingValues: [0, 0, 10, 10]
                },
				tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
       
	   //FnReSizeChart("#chart",500);

    //    $(document).ready(createChart);
       // $(document).bind("kendo:skinChange", createChart);
	
}



function FnAssetCountBasedOnType(response){
	console.log('Dashboard: FnAssetCountBasedOnType');
	var ArrAssetCount=response;
		 var ArrAssetCount = [
            {
                "source": "Car",
                "percentage": 22,
                "explode": true
            },
            {
                "source": "Truck",
                "percentage": 2
            },
            {
                "source": "Bus",
                "percentage": 49
            },
            {
                "source": "Others",
                "percentage": 27
            }
        ];
	
	  $("#asset-count-chart").kendoChart({
                title: {
                    text: ""
                },
				chartArea: {
				 background: "transparent",
				 width: 400,				
				height: 270
			  },
			  plotArea: {
					background: "transparent",
					 width: 500,				
					height: 270
				   },
							  
                legend: {
                    position: "bottom"
                },
                dataSource: {
                    data: ArrAssetCount
                },
                series: [{
                    type: "pie",
                    field: "percentage",
                    categoryField: "source",
                    explodeField: "explode"
                }],
                seriesColors: ["#03a9f4", "#ff9800", "#fad84a", "#dd4b39 "],
                tooltip: {
                    visible: true,
                    template: "${ category } - ${ value }%"
                }
            });

}


function FnAlertCountBasedOnCriticality(){
		console.log('Dashboard: FnAlertCountBasedOnCriticality');
		kendo.ui.progress($("#alert-count-chart"), true);

		var ArrSeries=[{
                    name: "High",
                    data: [40, 32, 34, 36, 45, 33 ],
                    color: "#f3ac32"
                }, {
                    name: "Medium",
                    data: [19, 25, 21, 26, 28, 31],
                    color: "#b8b8b8"
                }, {
                    name: "Low",
                    data: [17, 17, 16, 28, 34, 30],
                    color: "#bb6e36"
                }];
		var ArrCategories=["Sun","Mon", "Tue", "Wed", "Thu", "Fri"];


		 $("#alert-count-chart").kendoChart({
                title: {
                    text: ""
                },
				chartArea: {
				 background: "transparent",								
				height: 266
				 
			  },
                legend: {
                    visible: true,
					position: "bottom"
                },
                seriesDefaults: {
                    type: "bar",
                    stack: {
                       // type: "100%"
                    }
                },
                series: ArrSeries,
                valueAxis: {
                    line: {
                        visible: false
                    },
                    minorGridLines: {
                        visible: true
                    }
                },
                categoryAxis: {
                    categories: ArrCategories,
                    majorGridLines: {
                        visible: false
                    }
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
        }



function FnInitiateFullCalendar(){
	
	// $('#calendar-small').fullCalendar({});
	  $(function () {
	    /* initialize the external events
	     -----------------------------------------------------------------*/
	    function ini_events(ele) {
	      ele.each(function () {

	        // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
	        // it doesn't need to have a start or end
	        var eventObject = {
	          title: $.trim($(this).text()) // use the element's text as the event title
	        };

	        // store the Event Object in the DOM element so we can get to it later
	        $(this).data('eventObject', eventObject);

	        // make the event draggable using jQuery UI
	        $(this).draggable({
	          zIndex: 1070,
	          revert: true, // will cause the event to go back to its
	          revertDuration: 0  //  original position after the drag
	        });

	      });
	    }

	    ini_events($('#external-events div.external-event'));
	    
	   
	    /* initialize the calendar
	     -----------------------------------------------------------------*/
	    //Date for the calendar events (dummy data)
	    var date = new Date();
	    var d = date.getDate(),
	        m = date.getMonth(),
	        y = date.getFullYear();
	    $('#calendar').fullCalendar({
	      header: {
	        left: 'prev,next today',
	        center: 'title',
	        right: 'month,agendaWeek,agendaDay'
	      },
	      buttonText: {
	        today: 'today',
	        month: 'month',
	        week: 'week',
	        day: 'day'
	      },
	    /*  dayClick: function() {
	          alert('a day has been clicked!');
	      },*/
	      //Random default events
	      events: [
	        {
	          title: 'All Day Event',
	          start: new Date(y, m, 1),
	          backgroundColor: "#f56954", //red
	          borderColor: "#f56954" //red
	        },
	        {
	          title: 'Long Event',
	          start: new Date(y, m, d - 5),
	          end: new Date(y, m, d - 2),
	          backgroundColor: "#f39c12", //yellow
	          borderColor: "#f39c12" //yellow
	        },
	        {
	          title: 'Meeting',
	          start: new Date(y, m, d, 10, 30),
	          allDay: false,
	          backgroundColor: "#0073b7", //Blue
	          borderColor: "#0073b7" //Blue
	        },
	        {
	          title: 'Lunch',
	          start: new Date(y, m, d, 12, 0),
	          end: new Date(y, m, d, 14, 0),
	          allDay: false,
	          backgroundColor: "#00c0ef", //Info (aqua)
	          borderColor: "#00c0ef" //Info (aqua)
	        },
	        {
	          title: 'Birthday Party',
	          start: new Date(y, m, d + 1, 19, 0),
	          end: new Date(y, m, d + 1, 22, 30),
	          allDay: false,
	          backgroundColor: "#00a65a", //Success (green)
	          borderColor: "#00a65a" //Success (green)
	        },
	        {
	          title: 'Click for Google',
	          start: new Date(y, m, 28),
	          end: new Date(y, m, 29),
	          url: 'http://google.com/',
	          backgroundColor: "#3c8dbc", //Primary (light-blue)
	          borderColor: "#3c8dbc" //Primary (light-blue)
	        }
	      ],
	      eventClick: function(calEvent, jsEvent, view) {
	    	 // console.log(calEvent);
	    	  // alert('Event: ' + calEvent.title +' of '+view.name );
	         // alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
	        //  $(this).css('border-color', 'red');
	          
	          $("#eventInfo").html(calEvent.title);
	          $("#eventLink").attr('href', calEvent.url);
	          $("#eventContent").dialog({ 
	        	  	modal: true, 	        	 	        	  
	        	    draggable: false,
	        	    resizable: false,	        	    
	        	    width: 400,
	        	    height: 175,
	        	    show: 'blind',
	        	    hide: 'blind',
	        	    osition: ["bottom",50]
	        	   
	        	  });
	          
	          //$('#myModal').modal('show'); 
	          
	      },
	      editable: true,
	      droppable: true, // this allows things to be dropped onto the calendar !!!
	      drop: function (date, allDay) { // this function is called when something is dropped

	        // retrieve the dropped element's stored Event Object
	        var originalEventObject = $(this).data('eventObject');

	        // we need to copy it, so that multiple events don't have a reference to the same object
	        var copiedEventObject = $.extend({}, originalEventObject);

	        // assign it the date that was reported
	        copiedEventObject.start = date;
	        copiedEventObject.allDay = allDay;
	        copiedEventObject.backgroundColor = $(this).css("background-color");
	        copiedEventObject.borderColor = $(this).css("border-color");

	        // render the event on the calendar
	        // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
	        $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

	        // is the "remove after drop" checkbox checked?
	        if ($('#drop-remove').is(':checked')) {
	          // if so, remove the element from the "Draggable Events" list
	          $(this).remove();
	        }

	      }
	    });
	    $('#calendar').css('width','100%');

	    /* ADDING EVENTS */
	    var currColor = "#3c8dbc"; //Red by default
	    //Color chooser button
	    var colorChooser = $("#color-chooser-btn");
	    $("#color-chooser > li > a").click(function (e) {
	      e.preventDefault();
	      //Save color
	      currColor = $(this).css("color");
	      //Add color effect to button
	      $('#add-new-event').css({"background-color": currColor, "border-color": currColor});
	    });
	    $("#add-new-event").click(function (e) {
	      e.preventDefault();
	      //Get value and make sure it is not null
	      var val = $("#new-event").val();
	      if (val.length == 0) {
	        return;
	      }

	      //Create events
	      var event = $("<div />");
	      event.css({"background-color": currColor, "border-color": currColor, "color": "#fff"}).addClass("external-event");
	      event.html(val);
	      $('#external-events').prepend(event);

	      //Add draggable funtionality
	      ini_events(event);

	      //Remove event from text input
	      $("#new-event").val("");
	    });
	  });

	
	
}



