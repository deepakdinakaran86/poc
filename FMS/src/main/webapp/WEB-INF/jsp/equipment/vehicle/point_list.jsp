<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
	a.grid-viewuser{
	 padding: 0 0 0 5px;
		cursor: pointer;
		text-transform:capitalize
	}
</style>
<form action="vehicle_view" id="vehicle_view" method="post">
<input class="form-control" type="hidden" id="assetId" name="value" />
</form>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>    Vehicle Management   </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a  href="javascript:void(0);" onclick="FnCancelViewPoints();">View Vehicle</a></li>
        <li class="active"> Points</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box-title fms-title">
      			 <h4 class="pull-left"> Points</h4>
      			 <div class="fms-btngroup text-right">      			  
      				  <button type="button" class="btn btn-default"  onclick="FnCancelViewPoints()"> Cancel</button>
      		    </div>
      		</div>
      <!-- SELECT2 EXAMPLE -->
      <div class="box box-info">
        <div class="box-header with-border">
          <h4 class="box-title"></h4>

          <div class="box-tools pull-left ">
			<div class="caption " style="padding-top: 5px;">
				<span class="caption-subject bold uppercase">Asset Name : </span><span id="pointmap_asset_name">nissan12345</span>
			</div>
          
          </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
          <div class="row">
            <div class="col-md-12">  <div id="gapp-assetpoint-list"> </div></div>
          </div>
          <!-- /.row -->
        </div>
        <!-- /.box-body -->
		</div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  
  

  
<spring:url value="resources/js/equipment/asset/points.js" var="pointsJS"/><script src="${pointsJS}"></script>
  <c:if test="${not empty pointList}">
	<script>
	pointList= ${pointList};
					</script>
</c:if>
<c:if test="${not empty assetId}">
	<script>
	assetId= "${assetId}";
					</script>
</c:if>
<script>
		var pointList;
		var assetId;
		console.log("assetId"+assetId);

</script>