package com.pcs.device.gateway.enevo.onecollect.api;

/**
 * 
 * @author PCSEG311
 * 
 */
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.device.gateway.enevo.onecollect.api.authenticate.EnevoAuthenticationService;
import com.pcs.device.gateway.enevo.onecollect.api.authenticate.bean.Credentials;
import com.pcs.device.gateway.enevo.onecollect.api.authenticate.request.AuthRequest;
import com.pcs.device.gateway.enevo.onecollect.api.authenticate.request.AuthResponse;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.EnevoSnapshotService;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans.Site;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans.SiteContentType;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans.Snapshot;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.request.SnapshotRequest;
import com.pcs.device.gateway.enevo.onecollect.config.EnevoOnecollectGatewayConfiguration;

public class BinUpdater {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BinUpdater.class);
	public BinUpdater() {
	}



	public AuthResponse authenticate() throws Exception {
		AuthRequest request = new AuthRequest();
		Credentials credentials = new Credentials(EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.REMOTE_AUTHENTICATION_USERNAME),
									EnevoOnecollectGatewayConfiguration.getProperty(EnevoOnecollectGatewayConfiguration.REMOTE_AUTHENTICATION_PASSWORD));
		request.setCredentials(credentials);
		EnevoAuthenticationService authenticationService = new EnevoAuthenticationService();
		return authenticationService.remoteAuthenticate(request);
	}


	public static void checkForBinUpdate() {
		BinUpdater binUpdater = new BinUpdater();
		try {
			String x_token = binUpdater.authenticate().getSession().getToken();
			SnapshotRequest request = new SnapshotRequest(x_token);
			Snapshot Snapshot = new EnevoSnapshotService().getSnapshot(request);
			if (Snapshot != null) {
				LOGGER.info("got Snapshot");
				for (Site Site : Snapshot.getSites()) {
					if(Site.getSiteContentTypes()!= null){
						for (SiteContentType SiteContentType : Site
								.getSiteContentTypes()) {
							HashMap<String, String> dataMap = new HashMap<String, String>();
							dataMap.put("poi_id", SiteContentType.getId() + "");
							dataMap.put("fill_level",
									SiteContentType.getFillLevel() + "");
							dataMap.put("date_when_full",
									SiteContentType.getDateWhenFull());
							dataMap.put("last_collection",
									SiteContentType.getLastCollection());
							LOGGER.info("Site Details {}",dataMap);
						}
					}
				}
				LOGGER.info("Terminating the session");
				AuthRequest logout = new AuthRequest();
				logout.setAccessToken(x_token);
				EnevoAuthenticationService authenticationService = new EnevoAuthenticationService();
				LOGGER.info(authenticationService.logout(logout));
				
			} else {
				LOGGER.info("Terminating the session");
			}

		} catch (Exception e) {
			LOGGER.error("Exception updating bin data {}",e);
		}
	}
}
