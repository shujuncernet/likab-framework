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
package org.likabframework.registry.zookeeper;

import org.likabframework.core.remote.URL;
import org.likabframework.registry.support.RegistryCenter;
import org.likabframework.registry.support.ZkConstant;
import org.likabframework.registry.support.ZookeeperClient;

/**
 * ClassName:ZookeeperRegistry
 * 
 * @author sxp
 * @date:2017年4月16日 下午1:31:28
 */
public class ZookeeperRegistry extends ZookeeperBaseRegistry implements
		RegistryCenter {

	private ZookeeperClient client;

	protected static final RegistryCenter registry = new ZookeeperRegistry();

	private ZookeeperRegistry() {

	}

	@Override
	public void registry(String serverAddress, URL url) {
		client = connect(serverAddress);
		String configCenterZnode = ZkConstant.CONFIG_PATH;
		if (!client.exsistPath(configCenterZnode)) {
			client.createPersistent(configCenterZnode);
		}
		String serviceProviderZnode = configCenterZnode + ZkConstant.SERVICE_PROVIDER;
		if (!client.exsistPath(serviceProviderZnode)) {
			client.createPersistent(serviceProviderZnode);
		}
		String serviceNameZnode = serviceProviderZnode + "/" + url.getServiceName();
		if (!client.exsistPath(serviceNameZnode)) {
			client.createPersistent(serviceNameZnode);
		}
		String serviceProviderAddressZnode = serviceNameZnode + "/" + url.getHost();
		client.createEphemeral(serviceProviderAddressZnode, url.getUrl(), 1);
	}
}
