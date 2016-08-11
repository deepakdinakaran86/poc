<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:eval var="ListVehicleUrl" expression="@propertyConfigurer.getProperty('fms.services.devicelocation')" />
<spring:eval var="SearchDeviceUrl" expression="@propertyConfigurer.getProperty('fms.services.searchdevice')" />

<style type="text/css">
	.skin-blue .main-header .navbar{
      background: radial-gradient(#197aa5 15%, #005180 60%);
	  }
   .skin-blue .main-header .logo {
    background-color: #158dc1;
	}
	
	.content{
	    margin: 10px 0px 0px 0px;
    border-top: 1px solid #989292;
	 border-bottom: 1px solid #989292;
    padding: 0px;
	}
	
	.monitoring-popup{
		width:300px;
	    display: inline-block;
	}
	
	.monitoring-popup figure {
		width: 33%;
		padding: 5px;
		height: 110px;
		float: left;
		text-align: center;
		background: #c3c4c3 url("resources/images/equipicon.svg") no-repeat;
		background-position: center center;
		background-size: 44px 44px;
		margin-top: 7px;
	}
	.monitoring-popup section {
		width: 63%;
		float: left;
		margin-left: 10px;
		overflow: hidden
	}
	
	.monitoring-popup section strong.popup-header{
		    padding: 4px 0px 2px 0px;
			color: #fff;
			float: left;
			font-size: 16px;
			text-transform: uppercase;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: pre-wrap;
			width: 200px;
	}
	
	.leaflet-popup-content-wrapper,.leaflet-popup-content-wrapper, .leaflet-popup-tip{background-color: rgba(49, 48, 48, 0.8);}
	.brand-success{color:#3DA53D;padding-bottom:10px;font-size:14px;font-weight:bold;}
	.brand-default strong{font-size:13px!important;}
	.brand-default{color:#fff;font-size:13px;}
	.leaflet-popup-content-wrapper {
		border-radius: 3px;
	}
	.k-state-focused{color:#000!important}
	.leaflet-popup-content{color:#fff!important}
	
	.pin {
    width: 45px;
    height: 45px;
    border-radius: 50% 50% 50% 3%;
    background: #14a5ae;
    /* background: linear-gradient(#14a5ae, #189097); */
    position: relative;
    transform: rotate(-43deg);
		margin:0;
		animation-name: bounce;
		animation-fill-mode: both;
		animation-duration: 1s;
float:left;
		
}
.pin:after {
    content: '';
    width: 33px;
    height: 33px;
    margin: 6px 0 0 6px;
    background: #fff;
    position: absolute;
    border-radius: 50%;
    overflow: hidden;
}
.pin img{
position: absolute;
    z-index: 3;
    transform: rotate(43deg);
       left: 11px;
    top: 11px;
}
.pin:before {
       background: rgba(0,0,0,0.4);
    border-radius: 50%;
    height: 24px;
    width: 7px;
    position: absolute;
    margin: 11px 0px 0px -12px;
    transform: rotateZ(136deg);
    /* z-index: -2; */
    top: 22px;
    left: 9px;
    content: "";
    filter: blur(16px);
    -webkit-filter: blur(0.1em);
}

span.k-in.k-state-hover{cursor: pointer!important;}

@-moz-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-43deg);
  }
  80% {
    transform: translateY(-10px) rotate(-43deg);
  }
  100% {
    transform: translateY(0) rotate(-43deg);
  }
}
@-webkit-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-42deg);
  }
  80% {
    transform: translateY(-10px) rotate(-42deg);
  }
  100% {
    transform: translateY(0) rotate(-42deg);
  }
}
@-o-keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-42deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-42deg);
  }
  80% {
    transform: translateY(-10px) rotate(-42deg);
  }
  100% {
    transform: translateY(0) rotate(-42deg);
  }
}
@keyframes bounce {
  0% {
    opacity: 0;
    transform: translateY(-2000px) rotate(-43deg);
  }
  60% {
    opacity: 1;
    transform: translateY(30px) rotate(-43deg);
  }
  80% {
    transform: translateY(-10px) rotate(-43deg);
  }
  100% {
    transform: translateY(0) rotate(-43deg);
  }
}

.leaflet-div-icon {
    background: transparent;
    border: 0px solid #666;
}

#sidebar-wrapper {
		z-index: 1000;
		position: absolute; 
    width: 250px;
    height: 100%;
		overflow-y: auto;
		background: rgba(0,0,0,0.8);
		-webkit-transition: all 0.5s ease;
		-moz-transition: all 0.5s ease;
		-o-transition: all 0.5s ease;
		transition: all 0.5s ease;
	}
	
	#sidebar-wrapper{
	     box-shadow: 0px -25px 15px #080808 inset;
	     padding-left: 0px;

	}
	
	.sidebar-title .caption{
		padding: 15px 10px 10px 0px;
		color: #fff;
		font-size: 14px;
		line-height: 22px;
		width: 250px;
	}

	.caption-title{
	    font-size: 13px;
    padding: 10px 10px 10px 12px;
    line-height: 0px;
    display: inline-block;
	position:absolute;
	}

	.sidebar-title .fa-list:before {
		font-size: 19px;
		padding: 14px 17px 12px 12px;
		background: #484848;
		line-height: 0px;
	}
	.sidebar-title .fa-angle-double-right:before {
		font-size: 19px;
    padding: 6px 20px 9px 17px;
    background: #404040;
    line-height: 0px;
    margin-left: 0px;
    /* border-right: 1px solid #616161; */
	}
	.sidebar-title .fa-angle-double-down:before {
		font-size: 19px;
		padding: 14px 18px 12px 18px;
		background: #484848;
		line-height: 0px;
	}
	
	.k-group, .k-flatcolorpicker.k-group, .k-menu, .k-menu .k-group, .k-popup.k-widget.k-context-menu {
    color: #fdfdfd;
    background-color: #ffffff;
}

.leftbar-clientlist{
    margin: 18px;
}

.leftbar-clientlist .treeview .k-treeview{overflow:hidden}

.search-asset{
	padding: 6px 12px;
	font-size: 14px;
	color: #555;
	border: 1px solid #ccc;
	border-radius: 4px;
	width: 85%;
}

.fixed .content-wrapper, .fixed .right-side {
    padding-top: 0px;
}

</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
        
    <!-- Main content -->
    <section class="content">
    		
		<div class="col-split-12">
			<section id="vehiclesmonitoringmap"  style="width:100%; height:750px"></section>
						
		</div>	
    
    </section>
    <!-- /.content -->

</div>
<!-- /.content-wrapper -->

<script type="text/javascript">
	var VarListVehicleUrl = "${ListVehicleUrl}";
	var VarSearchDeviceUrl = "${SearchDeviceUrl}";
	
</script>

<script type="text/javascript" src="resources/plugins/websocket/mqttws31-min.js"></script>
<script type="text/javascript" src="resources/plugins/websocket/websocket.js"></script>
<script type="text/javascript" src="resources/js/users/home.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	
		var TriggerClick = 0;
		$("#leftbar").click(function(){
			if(TriggerClick==0){
				TriggerClick=1;
				$("#sidebar-wrapper").animate({width:"40px"}, 200);
				$(".caption-title").css({"transform":"translate(-86px, -105px) rotate(90deg) scale(1)","transition": "transform 0.2s","transform-origin": "left center","width": "115px","height": "203px","left":"10px","top":"35px"});
			} else {
				TriggerClick=0;
				$("#sidebar-wrapper").animate({width:"250px"}, 200);
					$(".caption-title").css({"transform":"rotate(0deg) scale(1)","transition": "all 0.2s","height": "0px","left":"48px","top":"8px","transform-origin": "left left"});
			}
		});
		
		$("#menu-toggle-2").click(function(e) {	 
			e.preventDefault();
			map.invalidateSize();
			$("#wrapper").toggleClass("toggled-2");
			$('#menu ul').hide();
			
			if($( ".caption-title" ).hasClass( "rotate" )){
				$( ".sidebar-title .caption-title" ).removeClass( "rotate" );
			 	$(".leftbar-clientlist").show();
			} else {
				$( ".sidebar-title .caption-title" ).addClass( "rotate" );
				$(".leftbar-clientlist").hide();
			}
		
			if($( ".sidebar-title .fa" ).hasClass( "fa-angle-double-right" )){
				$( ".sidebar-title .fa" ).removeClass( "fa-angle-double-right" );
				$( ".sidebar-title .fa" ).addClass( "fa-angle-double-down" );
			} else {
				$( ".sidebar-title .fa" ).removeClass( "fa-angle-double-down" );
				$( ".sidebar-title .fa" ).addClass( "fa-angle-double-right" );
			}
		});
		
	});
</script>
