package com.pcs.deviceframework.connection.config;

/**
 * A convenient builder utility for creating the configuration for a connector
 * 
 * @author pcseg310
 * @see {@link ConnectorConfiguration}
 *
 */
public class ConnectorConfigurationBuilder {

	private ConnectorConfiguration config;

	public ConnectorConfigurationBuilder() {
		config = new ConnectorConfiguration();
	}

	public ConnectorConfigurationBuilder deviceDetails(String name,
			String vendor, String model) {
		this.name(name).vendor(vendor).model(model);
		return this;
	}

	private ConnectorConfigurationBuilder name(String name) {
		if (name != null && !name.isEmpty()) {
			this.config.setName(name);
		}
		return this;
	}

	private ConnectorConfigurationBuilder vendor(String vendor) {
		if (vendor != null && !vendor.isEmpty()) {
			this.config.setVendor(vendor);
		}
		return this;
	}

	private ConnectorConfigurationBuilder model(String model) {
		if (model != null && !model.isEmpty()) {
			this.config.setModel(model);
		}
		return this;
	}

	public ConnectorConfigurationBuilder port(Integer port) {
		if (port != null && port.intValue() > 0) {
			this.config.setPort(port);
		}
		return this;
	}

	public ConnectorConfigurationBuilder addHandlers(Handler... handlers) {
		for (Handler handler : handlers) {
			this.config.getDeviceHandlers().add(handler);
		}
		return this;
	}

	public ConnectorConfiguration build() {
		return config;
	}
}
