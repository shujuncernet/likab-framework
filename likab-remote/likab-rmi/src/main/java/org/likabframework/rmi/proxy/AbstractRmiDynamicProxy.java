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
package org.likabframework.rmi.proxy;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

import org.likabframework.loadbalance.LoadBalance;
import org.likabframework.loadbalance.LoadBalanceFactory;
import org.likabframework.registry.support.ZkConstant;
import org.likabframework.registry.support.ZookeeperClient;
import org.likabframework.rmi.invocation.RmiInvocation;
import org.likabframework.rmi.invocation.RmiInvokeHandler;
import org.springframework.util.StringUtils;

/**
 * ClassName:AbstractRmiDynamicProxy
 * @author sxp
 * @date:2017年5月7日 下午7:26:52
 */
public abstract class AbstractRmiDynamicProxy {
	
    /**
     * String the zkAddress 
     */
    private String zkAddress;
	
	/**
	 * String the serviceName 
	 */
	private String serviceName;
	
	/**
	 * String the loadBalance 
	 */
	private String loadBalance;
	
	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getLoadBalance() {
		return loadBalance;
	}

	public void setLoadBalance(String loadBalance) {
		this.loadBalance = loadBalance;
	}

	/**
	 * @param zkAddress
	 * @param serviceName
	 * @param loadBalance
	 */
	public AbstractRmiDynamicProxy(String zkAddress, String serviceName,
			String loadBalance) {
		this.zkAddress = zkAddress;
		this.serviceName = serviceName;
		this.loadBalance = StringUtils.isEmpty(loadBalance) ? 
				LoadBalance.RANDOM_LOADBALANCE : loadBalance;
	}

	/**
	 * lookupStub
	 * Object
	 * @throws RemoteException 
	 */
	public Object lookupStub(String rmiUrl) throws Exception {
		if (!rmiUrl.startsWith("rmi://")) {
			throw new IllegalArgumentException("rmi url:[" + rmiUrl + "] format is not concrete");
		}
		Object stub = Naming.lookup(rmiUrl);
		return stub;
	}
	
	/**
	 * getUrlFromZookeeperRegistryer
	 * String
	 * @throws UnknownHostException 
	 */
	public String getUrlFromZookeeperRegistryer() throws UnknownHostException {
		String serviceNameZnode = ZkConstant.CONFIG_PATH + 
				ZkConstant.SERVICE_PROVIDER + "/" + serviceName;
		ZookeeperClient zkClient = new ZookeeperClient(zkAddress);
		boolean serviceNameExists = zkClient.exsistPath(serviceNameZnode);
		String rmiUrl = null;
		if (serviceNameExists) {
			List<String> serviceProviderUrlList = zkClient.getChildren(serviceNameZnode);
			String remoteIP = InetAddress.getLocalHost().getHostAddress();
			String serviceProviderZnode = serviceNameZnode
					+ "/" + LoadBalanceFactory.getLoadBalance(loadBalance)
					.loadBalance(remoteIP, serviceProviderUrlList);
			rmiUrl = zkClient.getDataFromZnode(serviceProviderZnode);
		} else {
			throw new RuntimeException("serviceName znode is not exists!");
		}
		return rmiUrl;
	}
	
	/**
	 * doRmiInvoker
	 * Object
	 * @throws Exception 
	 */
	public Object doRmiInvoker(RmiInvokeHandler handler, Method method, Object[] args) throws Exception {
		String methodName = method.getName();
		Class<?>[] parameterTypes = method.getParameterTypes();
		RmiInvocation invocation = new RmiInvocation(args, methodName, parameterTypes);
		return handler.invoke(invocation);
	}

}
