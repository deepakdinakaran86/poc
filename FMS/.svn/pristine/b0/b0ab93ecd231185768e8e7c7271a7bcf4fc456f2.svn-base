<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<style type="text/css">
</style>
<script id="rowTemplate" type="text/x-kendo-tmpl">		  
		<tr role="row" id="#: rowid#" class="geofence_grid">
		  <td role="gridcell">#: latitude#</td>
		  <td role="gridcell">#: longitude #</td>
		  <td role="gridcell" class="formelement_addition"><a href="javascript:void(0);" class="remove_marker" title="Remove Marker"><i class="fa fa-minus-circle"></i></a></td>
		</tr>     
	</script>

<div class="tag-wrapper" id="tag-wrapper">

	<div class="box-body">
		<form id="form-gapp-tag-management" name="form-gapp-tag-management"
			role="form" method="post">
			<div class="col-md-6 left-pane" id="portlet-body-left-pane">
				<div class="map-content"
					style="border: 1px solid #dbdbdb; margin-top: 12px;">
					<div class="modal-header bg-green-meadow"
						style="background: #fff !important; color: #f44336 !important; border: none !important; padding:0">
						<h4 class="modal-title col-md-4"
							style="font-size: 14px !important; font-weight: bold !important; top: 21px;">
							<i class="fa fa-map-marker"></i>&nbsp; Geo Tag
						</h4>
						
						
						
						<div class="modal-footer col-md-8" id="geotag-action-btn" style="padding: 12px 15px 15px 0px !important;">
						<span class="col-md-5"> 
							<strong>
								<span id="tag-latitude-longitude-display-value"></span>
							</strong>
						</span>
						<span id="geotag-update-button" class="col-md-7">
							<button type="button" id="add-geo-tag" class="btn btn-primary"
								data-dismiss="modal">
								<i class="fa fa-tags " aria-hidden="true"></i><span
									id="geo-tag-label">Tag</span>
							</button>	
								<button type="button" id="clear-geo-tag" class="btn default"
								data-dismiss="modal" style="color:#000">
								<i class="fa fa-refresh" aria-hidden="true"></i>Clear
							</button>			
						</span>	
						
						
						<input type="hidden" class="form-control input-xs "
							name="tag-latitude" id="tag-latitude" placeholder="latitude" />
						<input type="hidden" class="form-control input-xs"
							name="tag-longitude" id="tag-longitude" placeholder="longitude" />
						<input type="hidden" class="form-control input-xs "
							name="tag-domain-name" id="tag-domain-name" /> <input
							type="hidden" class="form-control input-xs"
							name="tag-identifier-key" id="tag-identifier-key" /> <input
							type="hidden" class="form-control input-xs"
							name="tag-identifier-value" id="tag-identifier-value" /> <input
							type="hidden" class="form-control input-xs"
							name="tag-entity-template-name" id="tag-entity-template-name" />
						<input type="hidden" class="form-control input-xs"
							name="tag-update-flag" id="tag-update-flag" value="false" /> 
					</div>
					</div>

					<div class="map-body" style="background: #e5e5e5;">
						<fieldset class="gllpLatlonPicker">
							<div class="form-group col-md-12">
								<div class="input-group" ></div>

								<div>

									<div id="vehicleGeoMap" style="height: 280px; width: 100%">
										Maps loading...</div>
								</div>

								<div class="col-md-12 geo-tag-inputs"	style="margin: 4px 0 0 0;">
									<div class="col-md-4 col-xs-4">
										<label>Latitude: </label>
										<input type="text" class="gllpLatitude"	id="gllpLatitude" onkeypress="return FnAllowNumbersOnly(event)" value="" />
									</div>
									<div class="col-md-4 col-xs-4">
										<label>Longitude:</label>
										<input type="text"	class="gllpLongitude" onkeypress="return FnAllowNumbersOnly(event)" id="gllpLongitude" value="" /> 
									</div>
									<div class="col-md-4 col-xs-4">
									<label>Zoom:</label><br/>
									<input	type="number" class="gllpZoom" id="gllpZoom"	 style="" onkeypress="return FnAllowNumbersOnly(event)"  min="0"  max="18" value="3" /> 
									<span	class="glyphicon glyphicon-search gllpUpdateButton hidden"	style="cursor: pointer;"></span>
										</div>
										
								</div>
							</div>
						</fieldset>
					</div>
					<!-- map-body-->

					
				</div>
				<!-- map-content -->

			</div>
			<!-- eof-left-pane-->

			<div class="col-md-6 right-pane" id="portlet-body-right-pane">
				<!-- right-pane-content -->
				<div class="map-content"
					style="border: 1px solid #dbdbdb; margin-top: 12px;">
					<div class="modal-header bg-green-meadow"
						style="background: #fff !important; color: #f44336 !important; border: none !important;">
						<h4 class="modal-title"
							style="font-size: 14px !important; font-weight: bold !important;">
							<i class="fa fa-tags"></i>&nbsp;Tags
						</h4>
					</div>

					<div class="map-body"
						style="background: #e5e5e5 !important; ">
						<select id="name-tag-placeholder" id="name-tag-placeholder"
							multiple="multiple" data-placeholder="Select tag values"></select>
					</div>
					<!-- map-body-->

					<div class="modal-footer"
						style="padding: 12px 15px 0px 0px !important; background: #e5e5e5;">
						<div id="content-footer"></div>
					</div>
				</div>
				<!-- map-content -->
			</div>
			<!-- eof-right-pane-content -->
		</form>
		<!-- eof-form-gapp-tag-management -->
	</div>
	<!-- eof-portlet-body -->
</div>




<spring:url value="resources/plugins/jquery.form.js" var="fomrJS" /> <script src="${fomrJS}"></script>
<spring:url value="resources/js/getTagTemplate.js" var="getTagTemplateJS" /> <script src="${getTagTemplateJS}"></script>
<script>
	$('aside.control-sidebar').show().addClass('control-sidebar-open'); 
	$('body').addClass('sidebar-collapse');
</script>

