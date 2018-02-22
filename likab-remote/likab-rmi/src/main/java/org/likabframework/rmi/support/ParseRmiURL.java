package org.likabframework.rmi.support;

import org.likabframework.core.remote.URL;

public class ParseRmiURL extends URL {
	
	/**
	 * @param rmiUrl
	 */
	public ParseRmiURL(String rmiUrl) {
		String rmiUrlWithoutRmiProtocol = rmiUrl.substring("rmi://".length());
		String[] splitRmiUrlString = rmiUrlWithoutRmiProtocol.split("/");
		String hostAndPort = splitRmiUrlString[0];
		String[] splitHostAndPort = hostAndPort.split(":");
		String host = splitHostAndPort[0];
		this.setHost(host);
		String port = splitHostAndPort[1];
		this.setPort(port);
	}

	/**
	 * @param host
	 * @param port
	 * @param serviceName
	 */
	public ParseRmiURL(String host, String port, String serviceName) {
		super(host, port, serviceName);
	}

	@Override
	protected void doParse() {
		this.url = "rmi://" + this.host + ":" + this.port + "/"
				+ this.serviceName;
	}

}
