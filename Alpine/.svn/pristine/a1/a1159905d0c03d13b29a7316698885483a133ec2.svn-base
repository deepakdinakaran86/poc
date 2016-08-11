

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<spring:url value="resources/themes/default/css/components/ngProgress.css" var="progressCss"/>
<link rel="stylesheet" href="${progressCss}">

<spring:url value="resources/themes/default/css/loginFlow/gxLogin.css" var="loginCss"/>
<link href="${loginCss}">

<spring:url value="webjars/bootstrap/2.3.0/css/bootstrap.min.css" var="bootstrapCss"/>
<link href="${bootstrapCss}" rel="stylesheet"/>

<spring:url value="webjars/jquery/2.0.3/jquery.js" var="jQuery"/>
<script src="${jQuery}"></script>

<!-- jquery-ui.js file is really big so we only load what we need instead of loading everything -->
<spring:url value="webjars/jquery-ui/1.10.3/ui/jquery.ui.core.js" var="jQueryUiCore"/>
<script src="${jQueryUiCore}"></script>

<spring:url value="webjars/jquery-ui/1.10.3/ui/jquery.ui.datepicker.js" var="jQueryUiDatePicker"/>
<script src="${jQueryUiDatePicker}"></script>

<!-- jquery-ui.css file is not that big so we can afford to load it -->
<spring:url value="webjars/jquery-ui/1.10.3/themes/base/jquery-ui.css" var="jQueryUiCss"/>
<link href="${jQueryUiCss}" rel="stylesheet"></link>

<link href="resources/css/metro/metro.min.css" rel="stylesheet" />
<link href="resources/css/metro/metro-icons.min.css" rel="stylesheet" />

<script src="resources/js/jquery/jquery-2.1.4.min.js"></script>

<link rel="stylesheet" href="resources/kendo/css/kendo.material.min.css" />
<link rel="stylesheet" href="resources/kendo/css/kendo.common-material.min.css" />
   
<script src="resources/themes/default/js/jquery.min.js"></script>
<script src="resources/kendo/scripts/kendo.all.min.js"></script>
<style>
.successTemp{
 border-width:1px;
 border-style:solid;
 width: 400px;
 height:100px
}
 .successTemp h3{
 color:#FFFFFF;
 font-size: 1.2em;
 padding: 32px 10px 5px; 
 font-weight:normal
 }
 .successTemp img {
  float: left;
   margin: 30px 15px 30px 30px;
   }
 .errorTemp{
  border-width:1px;
  border-style:solid;
  width: 400px;
  height:100px
}
 .errorTemp h3{
  color:#FFFFFF; 
  font-size: 1.2em;
  padding: 32px 10px 5px;
  font-weight:normal
 }
 .errorTemp img {
    float: left;
    margin: 30px 15px 30px 30px;
   }
.warningTemp{
  border-width:1px;
  border-style:solid;
  width: 400px;
  height:100px
}
.warningTemp h3 {
  color:#FFFFFF;
  font-size: 1.2em;
  padding: 32px 10px 5px;
  font-weight:normal;
   }
.warningTemp img {
   float: left;
   margin: 30px 15px 30px 30px;
   }
   
 </style>
 
 <script>
 var staticNotification;
 function initNotifications(){
  staticNotification = $("#staticNotification").kendoNotification({
     position: {
         pinned: true,
          top: 230,
       left:630
     },
     hideOnClick: true,
     allowHideAfter: 0,
     width: 400,
     height: 100,
     //stacking: "down",                      
     templates: [ {
         type: "error",
         template: $("#errorTemplate").html()
     }, {
         type: "success",
         template: $("#successTemplate").html()
     } ,{
    	 type: "warning",
         template: $("#warningTemplate").html()
    }]

 }).data("kendoNotification");
 }
 
 </script>
 
 <script id="errorTemplate" type="text/x-kendo-template">
                <div class="errorTemp">
                   <img src="resources/themes/default/images/icons/white/error-icon.png" />            
                    <h3>#= message #</h3>
                </div>
          </script>

<script id="successTemplate" type="text/x-kendo-template">
                <div class="successTemp">
                    <img src="resources/themes/default/images/icons/white/success-icon.png" />
                    <h3>#= message #</h3>
                </div>
          </script>
<script id="warningTemplate" type="text/x-kendo-template">
                <div class="warningTemp">
                    <img src="resources/themes/default/images/icons/white/important.png" />
                    <h3>#= message #</h3>
                </div>
          </script>
          
 <span id="staticNotification"></span>