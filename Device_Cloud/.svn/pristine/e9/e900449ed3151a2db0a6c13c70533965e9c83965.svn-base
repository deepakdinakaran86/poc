package com.pcs.device.gateway.meitrack;

import com.pcs.device.gateway.meitrack.devicemanager.MeitrackDeviceManager;
import com.pcs.deviceframework.devicemanager.authentication.AuthenticationResponse;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	MeitrackDeviceManager dm = MeitrackDeviceManager.instance();
		AuthenticationResponse resp = dm.authenticate("351756051523999");
		System.out.println(resp.getAuthenticationMessage());
		System.out.println(resp.getSessionInfo().toString());
    }
}
