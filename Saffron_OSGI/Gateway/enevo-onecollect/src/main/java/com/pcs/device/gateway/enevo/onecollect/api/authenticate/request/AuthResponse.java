package com.pcs.device.gateway.enevo.onecollect.api.authenticate.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pcs.device.gateway.enevo.onecollect.api.snapshot.beans.Session;

@JsonAutoDetect
@JsonInclude(value = Include.NON_NULL)
public class AuthResponse {
	public AuthResponse() {
	}

	private String href;
	private String schema;
	private Session session;
	private String generated;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getGenerated() {
		return generated;
	}

	public void setGenerated(String generated) {
		this.generated = generated;
	}
}
