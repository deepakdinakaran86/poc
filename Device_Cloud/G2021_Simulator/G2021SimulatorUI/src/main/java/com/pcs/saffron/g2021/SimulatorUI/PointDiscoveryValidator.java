package com.pcs.saffron.g2021.SimulatorUI;

import com.pcs.saffron.g2021.SimulatorEngine.CS.message.Points;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.PointsDiscoveryMesasge;

public class PointDiscoveryValidator {
	
	
	protected static boolean validatePointsObject(PointsDiscoveryMesasge pointDiscovry) {
		
		Points[] points = pointDiscovry.getPoints();
		
		for(int i=0;i<points.length;i++){
			if(points[i].getpID() == null)
				return false;
			if(points[i].getpName() == null || points[i].getpName().trim().isEmpty())
				return false;
			if(points[i].getpUnit() == null || points[i].getpUnit().trim().isEmpty())
				return false;
			if(points[i].getpType() == null || points[i].getpType()>1)
				return false;
			if(points[i].getpDataType() == null || points[i].getpDataType() > 5)
				return false;
			if(points[i].getpDAQType() == null || points[i].getpDAQType() > 3)
				return false;
			if(points[i].getpAlarmType() == null || points[i].getpAlarmType() > 2)
				return false;
			if(points[i].getpAlarmCriticality() == null || points[i].getpAlarmCriticality() > 3)
				return false;			
			
			if(points[i].getpAlarmType() > 0){
				if(points[i].getpAlarm_LT() == null || points[i].getpAlarm_UT() == null || points[i].getNormalTEXT() == null || points[i].getOffnormalTEXT() == null
						|| points[i].getOffnormalTEXT_LT() == null || points[i].getOffnormalTEXT_UT() == null)
					return false;
			}
			
			if(points[i].getpAlarm_LT() > points[i].getpAlarm_UT()){
				return false;
			}
			
		}
		
		return true;		
	}
	
}
