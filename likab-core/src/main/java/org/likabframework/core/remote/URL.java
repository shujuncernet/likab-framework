/**
 * Copyright 2017-2020.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.likabframework.core.remote;

/**
 * ClassName:URL
 * @author sxp
 * @date:2017年4月23日 上午9:46:55
 */
public abstract class URL {
	
	protected String host;
	
	protected String port;
	
	protected String serviceName;
	
	protected String url;

	/**
	 * getHost
	 * String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * setHost
	 * void
	 */
	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	/**
	 * setPort
	 * void
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * getServiceName
	 * String
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * setServiceName
	 * void
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * getUrl
	 * String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl
	 * void
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	public URL() {}

	/**
	 * @param host
	 * @param port
	 * @param serviceName
	 */
	public URL(String host, String port, String serviceName) {
		this.host = host;
		this.port = port;
		this.serviceName = serviceName;
	}
	
	/**
	 * parse
	 */
	public void parse() {
		doParse();
	}
	
	/**
	 * doParse
	 * void
	 */
	protected abstract void doParse();
}
