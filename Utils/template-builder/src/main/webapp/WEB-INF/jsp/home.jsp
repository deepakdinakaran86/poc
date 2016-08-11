<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
</script>

<head>
<style>
table,td,th {
	border: 1px solid black;
}

th {
	background-color: brown;
	color: white;
}

.cellClass {
	vertical-align: top;
}
.selecteddiv {
    background-color: #F7BE81;
}
legend1 {
	display: block;
	-webkit-padding-start: 2px;
	-webkit-padding-end: 2px;
	border: none;
	border-image-source: initial;
	border-image-slice: initial;
	border-image-width: initial;
	border-image-outset: initial;
	border-image-repeat: initial;
}
</style>
</head>
<body style="width: 100%; height: 1500">
<script src="resources/js/fieldComponents/Galaxy_TextBox.js"></script>

	<table style="width: 100%; height: 1500">
		<tr>
			<th style="width: 70%; height: 2%">Form</th>
			<th style="width: 30%; height: 2%">Palette</th>
		</tr>
		<tr>
			<td rowspan="3" style="width: 70%; height: 100%">
				<div style="float: left; width: 50%; height: 650px; border:1px solid" id="form1" ondrop="drop('form1',event)" ondragover="allowDrop(event)"></div>
				<div style="float: right; width: 50%; height: 650px; border:1px solid" id="form2" ondrop="drop('form2',event)" ondragover="allowDrop(event)"></div>
			</td>
			<td>
				<div style="width: 100%; height: 50%">

					<ul style="list-style-type: none;">
						<li style="margin-top: 10px">
							<button href="#" type="submit" id="textField"
								style="width: 200px; margin-left: 10px; text-align: left" draggable="true" ondragstart="drag(event)">
								<img src="resources/images/text_form_field.png"
									style="weight: 30px; height: 24px; margin-right: 10px"  />Text
								Box
							</button>
							<button href="#" type="submit" id="checkBoxField"
								style="width: 200px; margin-left: 10px; text-align: left" disabled>
								<img src="resources/images/checkbox_form_field.png"
									style="weight: 30px; height: 24px; margin-right: 10px" />Check
								Box
							</button>
						</li>
						</b>
						<li style="margin-top: 10px">
							<button href="#" type="submit" id="dropDownField"
								style="width: 200px; margin-left: 10px; text-align: left" disabled>
								<img src="resources/images/dropdown_form_field.jpg"
									style="weight: 30px; height: 24px; margin-right: 10px" />Drop
								down
							</button>
							<button href="#" type="submit" id="textAreaField"
								style="width: 200px; margin-left: 10px; text-align: left" disabled>
								<img src="resources/images/text_area_field.png"
									style="weight: 30px; height: 24px; margin-right: 10px" />Text
								Area
							</button>
						</li>
					</ul>

				</div>
			</td>
		</tr>
		<tr>
			<th style="width: 30%; height: 2%">Field Settings</th>
		</tr>
		<tr>
			<td style="width: 30%; height: 50%">
				<div id="propertiesDiv" >
					<ul style="list-style-type: none;">
						<li><label>Field Label</label> <input id="labelPpty" type="text" disabled="disabled"> 
						
						<label>Title</label>
							<input type="text" id="textPpty" disabled="disabled"></li>
						<li style="margin-top: 10px">
							<fieldset
								style="display: inline; border: 2px #af3333 solid; border-radius: 10px">
								<legend class="legend1">Options</legend>
								<div style="float: left;">
									<label
										style="text-align: right; clear: both; float: left; margin-right: 15px;">Required</label>
									<input style="margin-right: 15px;" id="requiredPpty" type="checkbox" disabled="disabled">
								</div>
								<div style="float: right;">
									<label
										style="text-align: right; clear: both; float: left; margin-right: 15px;">Unique</label>
									<input style="margin-right: 15px;" type="checkbox" disabled="disabled">
								</div>

							</fieldset>
						</li>

						<li style="margin-top: 10px">
							<fieldset style="display: inline">
								<div style="float: left;">
									<label
										style="text-align: right; clear: both; float: left; margin-right: 15px;">Min</label>
									<input style="margin-right: 15px;" type="text" disabled="disabled">
								</div>
								<div style="float: left;">
									<label
										style="text-align: right; clear: both; float: left; margin-right: 15px;">Max</label>
									<input style="margin-right: 15px;" type="text" disabled="disabled">
								</div>

							</fieldset>
						</li>

					</ul>

				</div>
				<input type="button" id="redraw" value="redraw">
			</td>
		</tr>
	</table>
	<script>

	var compCount=1;
	var selectedDivId = undefined;
	
	function allowDrop(ev) {
	    ev.preventDefault();
	}

	function drag(ev) {
	    ev.dataTransfer.setData("id", ev.target.id);
	}

	function selectionHandler(event,object){
			var parent = object.parent();
			parent.find( "div" ).removeClass('selecteddiv');
			 if (!event.ctrlKey) {
				object.addClass('selecteddiv');
			 }
		}
	var onTextClick = function (event,fieldId){
		var field = $( "#"+fieldId );
		selectionHandler(event,field);
		if ( field.length ) {
			var myJson = jQuery.parseJSON($("#"+fieldId).attr('my-data'));

			
 			selectedDivId =  fieldId;
 			var labelPpty = $("#labelPpty");
 			if(myJson.mandatory){
 				labelPpty.val($("#label_"+fieldId).text().slice(0,-1));
			}
			else{
				labelPpty.val($("#label_"+fieldId).text());
				}
			labelPpty.prop('disabled', false);

			var textPpty = $("#textPpty");
			textPpty.val($("#text_"+fieldId)[0].placeholder);
			textPpty.prop('disabled', false);

			var requiredPpty = $("#requiredPpty");
			requiredPpty.prop( "checked", myJson.mandatory );
			requiredPpty.prop('disabled', false);
			
			    	
 		}
		$('#redraw').attr('onclick','').unbind('click');
 		$('#redraw').click(function(){
 			redraw(fieldId);
 		});
	};
	
	var onDeleteComponenetClick = function (divId){
	
		$( "#"+divId ).remove();
	};

	function drop(formId,ev) {
	    ev.preventDefault();
	    var divId = 'divId_'+compCount;
	    var draggedFieldId = ev.dataTransfer.getData("id");
	    var compId = 'divId_'+compCount;
	    
	    
	    $("#" + formId).append("<div style=\"width:100%; height:10%\" id=\""+divId+"\" onclick=\"onTextClick(event,'"+divId+"')\"> </div>" );
	    
		if(draggedFieldId === 'textField'){
			var text_obj = {};
			text_obj.name = 'mytext_name';
			text_obj.id = compId ;
			text_obj.text_style = "margin-left:20px; disabled";
			text_obj.mandatory = false;
			text_obj.label = 'Label';
			text_obj.label_style = 'margin-right:20px;';
			text_obj.container = divId;
			text_obj.placeholder = 'Placeholder';
			text_obj.draggable = false;
			text_obj.server_validation = false;
			text_obj.disabled = true;
			text_obj.onClick = onTextClick;
			var a = new GalaxyTextBox(text_obj);

			$("#"+compId).attr("disabled", true);

			compCount ++;

		}
	    
	    //ev.target.appendChild(document.getElementById(data));
	}

	function redraw(fieldId){
		var myJson = jQuery.parseJSON($("#"+fieldId).attr('my-data'));
			myJson.label =  $("#labelPpty").val();
			myJson.placeholder = $("#textPpty").val();
			myJson.mandatory = $("#requiredPpty").is(":checked");
			new GalaxyTextBox(myJson);
		}
	
	
	
</script>
	<script type="text/javascript">
                        <!--
function showPreview()
{
var s=document.getElementById('testcode').value;
if(s.length>1000){alert("Code length must be within 10 to 100 characters."); return false;}
var url="preview.html?q="+s
location.href=url;
//To generate preview on new window instead of location.href=url; use
window.open(url);
}
-->
                    </script>
	<script type="text/javascript">
/*--This JavaScript method for Print command--*/
    function PrintDoc() {
        var toPrint = document.getElementById('printarea');
        var popupWin = window.open('', '_blank', 'width=350,height=150,location=no,left=200px');
        popupWin.document.open();
        popupWin.document.write('
                        <html>
                            <title>::Preview::</title>
                            <link rel="stylesheet" type="text/css" href="print.css" />
                        </head>
                        <body onload="window.print()">')
        popupWin.document.write(toPrint.innerHTML);
        popupWin.document.write('
                        </html>');
        popupWin.document.close();
    }
/*--This JavaScript method for Print Preview command--*/
    function PrintPreview() {
        var toPrint = document.getElementById('printarea');
        var popupWin = window.open('', '_blank', 'width=350,height=150,location=no,left=200px');
        popupWin.document.open();0
        popupWin.document.write('
                        <html>
                            <title>::Print Preview::</title>
                            <link rel="stylesheet" type="text/css" href="Print.css" media="screen"/>
                        </head>
                        <body">')
        popupWin.document.write(toPrint.innerHTML);
		
        popupWin.document.write('
                        </html>');
        popupWin.document.close();
    }
	
	function hideDiv() {
      //  var toPrint = document.getElementById('propArea1').hide();
		//document.getElementById('propArea1').style.display = 'none';
		alert(document.getElementById('testcode').type);
		}

                    </script>
</body>