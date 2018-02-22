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
package org.likabframework.rmi.support;

import java.rmi.Remote;

import org.likabframework.core.factory.DynamicProxyFactory;
import org.likabframework.core.support.JdkDynamicCreator;
import org.likabframework.rmi.invocation.RmiInvoker;

/**
 * ClassName:RemoteExporter
 * 
 * @author sxp
 * @date:2017年4月16日 上午9:16:55
 */

@SuppressWarnings("all")
public class RmiExporter {

	private Object service;

	private Class serviceInterface;

	/**
	 * getService Object
	 */
	public Object getService() {
		return service;
	}

	/**
	 * setService void
	 */
	public void setService(Object service) {
		this.service = service;
	}

	/**
	 * getServiceInterface Class
	 */
	public Class getServiceInterface() {
		return serviceInterface;
	}

	/**
	 * setServiceInterface void
	 */
	public void setServiceInterface(Class serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	/**
	 * 校验服务实例是否存在
	 */
	public void checkService() {
		if (service == null) {
			throw new IllegalArgumentException("Property 'service' is required");
		}
	}

	/**
	 * objectToExport Remote
	 */
	protected Remote objectToExport() {
		if (getService() instanceof Remote
				&& (getServiceInterface() == null || Remote.class
						.isAssignableFrom(getServiceInterface()))) {
			return (Remote) getService();
		}
		return new RmiInvoker(DynamicProxyFactory.getProxy(service));
	}

}
