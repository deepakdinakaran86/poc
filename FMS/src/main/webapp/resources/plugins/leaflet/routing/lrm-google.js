var directionsService = new google.maps.DirectionsService();
L.Routing = L.Routing || {};
L.Routing.Google = L.Class.extend({
    options: {
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.METRIC,
        provideRouteAlternatives: true
    },
    initialize: function(options) {
        L.Util.setOptions(this, options);
    },
    _flatten: function(arrs) {
        var arr = [],
            i;
        for (i = 0; i < arrs.length; i++) arr = arr.concat(arrs[i]);
        return arr;
    },
    _decodePolyline: function(geometry) {
			var coords = polyline.decode(geometry, 5),
				latlngs = new Array(coords.length),
				i;
			for (i = 0; i < coords.length; i++) {
				latlngs[i] = new L.LatLng(coords[i][0], coords[i][1]);
			}

			return latlngs;
		},
    route: function(waypoints, callback, context, options) {
        var that = this;
        var directions = this.options;
        directions.origin = waypoints[0].latLng.lat + ',' + waypoints[0].latLng.lng;
        directions.destination = waypoints[waypoints.length - 1].latLng.lat + ',' + waypoints[waypoints.length - 1].latLng.lng;
        if (waypoints.length > 2) {
            directions.waypoints =
                waypoints.slice(1, waypoints.length - 1).map(function(waypoint) {
                    return {
                      location: waypoint.latLng.lat + ',' + waypoint.latLng.lng,
                      stopover: true
                    };
                });
        }
        
        directionsService.route(directions, function(result, status) {
          if (status == google.maps.DirectionsStatus.OK) {
            callback.call(context || callback, null, result.routes.map(function(route) {
                var iroute = {}, i, j, step;
                iroute.name = route.summary;
                iroute.summary = {
                    totalDistance: that._flatten(route.legs.map(function(leg) {
                        return leg.steps.map(function(step) {
                            return step.distance.value;
                        });
                    })).reduce(function(previousValue, currentValue) {
                        return previousValue + currentValue;
                    }),
                    totalTime: that._flatten(route.legs.map(function(leg) {
                        return leg.steps.map(function(step) {
                            return step.duration.value;
                        });
                    })).reduce(function(previousValue, currentValue) {
                        return previousValue + currentValue;
                    })
                };

                iroute.coordinates = [];
                var indices = [], indicesSecondary = [];

                for(i = 0; i < route.legs.length; i++){
                  indicesSecondary = [];
                  for(j = 0; j < route.legs[i].steps.length; j++){
                    indicesSecondary.push(iroute.coordinates.length);
                    step = route.legs[i].steps[j];
                    iroute.coordinates = iroute.coordinates.concat(that._decodePolyline(step.polyline.points));
                  }
                  indices.push(indicesSecondary);
                }
                iroute.inputWaypoints = waypoints || [];
                iroute.waypoints = iroute.actualWaypoints = waypoints;
                iroute.waypointIndices = [0, iroute.coordinates.length - 1];
                //for(i = 0; i < iroute.actualWaypoints; i++) iroute.waypointIndices.push(i);

                iroute.instructions = [];
                for(i = 0; i < route.legs.length; i++)
                  for(j = 0; j < route.legs[i].steps.length; j++){
                    step = route.legs[i].steps[j];
                    type = that._maneuverToInstructionType(step.maneuver, i === route.legs.length - 1);
                    
                    iroute.instructions.push({
            					type: 'Straight',
            					text: step.instructions.replace(/<(?:.|\n)*?>/gm, ''),
            					distance: step.distance.value,
            					time:  step.duration.value,
            					index: indices[i][j],
            					exit: null//instr.exit_number
            				});
                  }
                iroute.legs=route.legs;
                iroute.overview=route.overview_polyline;
                return iroute;
            }));
          }
          else callback.call(context, {
              status: status,
              message: result
          });
        });
        return this;
    }, 
    _maneuverToInstructionType: function(maneuver, lastLeg) {
		switch (maneuver.type) {
		case 'new name':
			return 'Continue';
		case 'arrive':
			return lastLeg ? 'DestinationReached' : 'WaypointReached';
		case 'roundabout':
		case 'rotary':
			return 'Roundabout';
		// These are all reduced to the same instruction in the current model
		//case 'turn':
		//case 'end of road':
		//case 'merge':
		//case 'on ramp': // new in v5.1
		//case 'off ramp': // new in v5.1
		//case 'ramp': // deprecated in v5.1
		//case 'fork':
		default:
			switch (maneuver.modifier) {
			case 'straight':
				return 'Straight';
			case 'slight right':
				return 'SlightRight';
			case 'right':
				return 'Right';
			case 'sharp right':
				return 'SharpRight';
			case 'sharp left':
				return 'SharpLeft';
			case 'left':
				return 'Left';
			case 'slight left':
				return 'SlightLeft';
			case 'uturn':
				return 'TurnAround';
			default:
				return null;
			}
			return null;
		}
	},
		
		
});

L.Routing.google = function(options) {
    return new L.Routing.Google(options);
};
