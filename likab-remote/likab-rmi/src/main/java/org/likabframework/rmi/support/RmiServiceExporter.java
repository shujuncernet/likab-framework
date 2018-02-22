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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.likabframework.core.remote.URL;
import org.likabframework.registry.support.RegistryCenter;
import org.likabframework.registry.zookeeper.ZookeeperRegistryFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * ClassName:RmiServiceExporter
 * @author sxp
 * @date:2017年4月15日 下午8:58:21
 * @see 实现InitializingBean,DisposableBean Spring 中提供的这两个接口
 * @see 为了方便与Spring集成，托管于Spring容器中
 */
public class RmiServiceExporter extends RmiExporter implements InitializingBean, DisposableBean {
	
	/**
	 * 服务名称，提供给client查找服务时使用
	 */
	private String serviceName;
	
	/**
	 * 服务提供端ip地址
	 */
	private String rmiHost;
	
	/**
	 * 默认使用1099端口号
	 */
	private int rmiPort = Registry.REGISTRY_PORT;
	
	/**
	 * zookeeper注册中心地址
	 */
	private String zkAddress = "localhost:2181";
	
	private Registry registry;
	
	private Remote exportedObject;
	
	private static boolean createdRegistry = true;
	
	private static final Log logger = LogFactory.getLog(RmiServiceExporter.class);

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
	 * getRmiHost
	 * String
	 */
	public String getRmiHost() {
		return rmiHost;
	}

	/**
	 * setRmiHost
	 * void
	 */
	public void setRmiHost(String rmiHost) {
		this.rmiHost = rmiHost;
	}

	/**
	 * getRmiPort
	 * int
	 */
	public int getRmiPort() {
		return rmiPort;
	}

	/**
	 * setRmiPort
	 * void
	 */
	public void setRmiPort(int rmiPort) {
		this.rmiPort = rmiPort;
	}

	/**
	 * getZkAddress
	 * String
	 */
	public String getZkAddress() {
		return zkAddress;
	}

	/**
	 * setZkAddress
	 * void
	 */
	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		export();
	}
	
	/**
	 * export
	 * void
	 * @throws RemoteException 
	 */
	public void export() throws RemoteException {
		//check service Object
		checkService();
		//check service name
		checkServiceName();
		//get registry
		if (registry == null) {
			registry = getRegistry();
		}
		this.exportedObject = objectToExport();
		UnicastRemoteObject.exportObject(this.exportedObject, this.rmiPort);
		try {
			this.registry.bind(this.serviceName, this.exportedObject);
		} catch (AlreadyBoundException e) {
			cancleBoundExport();
		}
		//将服务rmi地址注册到zookeeper实现高可用
		RegistryCenter registry = ZookeeperRegistryFactory.createRegisty();
		URL rmiURL = parseRmiURL();
		registry.registry(zkAddress, rmiURL);
	}
	
	/**
	 * parseRmiURL
	 * ParseRmiURL
	 */
	private URL parseRmiURL() {
		String host = rmiHost;
		if (host == null) {
			try {
				host = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				throw new RuntimeException("Unknown host excepion", e);
			}
		}
		ParseRmiURL parseRmiURL = new ParseRmiURL(host, String.valueOf(rmiPort), serviceName);
		parseRmiURL.doParse();
		return parseRmiURL;
	}
	
	/**
	 * cancleBoundExport
	 * void
	 */
	private void cancleBoundExport() {
		try {
			UnicastRemoteObject.unexportObject(this.exportedObject, true);
		} catch (NoSuchObjectException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("RMI object for service '" + this.serviceName + "' isn't exported anymore", ex);
			}
		}
	}
	
	/**
	 * getRegistry
	 * Registry
	 * @throws RemoteException 
	 */
	private Registry getRegistry() throws RemoteException {
		Registry registry = null;
		synchronized (LocateRegistry.class) {
			try {
				if (rmiHost != null) {
					registry = LocateRegistry.getRegistry(rmiHost,
							rmiPort);
				} else {
					if (createdRegistry) {
						registry = LocateRegistry.createRegistry(rmiPort);
						createdRegistry = false;
						return registry;
					}
					registry = LocateRegistry.getRegistry(rmiPort);
				}
				return registry;
			} catch (RemoteException ex) {
				registry = handleExportException(ex);
				if (registry == null) {
					logger.debug("RMI registry access threw exception", ex);
					logger.info("Could not find RMI registry - creating new one");
					registry = LocateRegistry.createRegistry(rmiPort);
				}
			}
		}
		return registry;
	}
	
	/**
	 * handleExportException
	 * 
	 * @param ex
	 * @return Registry
	 */
	private Registry handleExportException(RemoteException ex) {
		Registry registry = null;
		if (ex instanceof ExportException) {
			try {
				registry = LocateRegistry.getRegistry(rmiPort);
			} catch (RemoteException e) {
				logger.debug("RMI registry access threw exception", ex);
			}
		}
		return registry;
	}
	
	/**
	 * checkServiceName
	 * void
	 */
	private void checkServiceName() {
		if (serviceName == null) {
			throw new IllegalArgumentException(
					"Property 'serviceName' is required");
		}
	}
	
	@Override
	public void destroy() throws RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("RMI object for service '" + this.serviceName
					+ "' cancle bound export");
		}
		try {
			this.registry.unbind(this.serviceName);
		} catch (NotBoundException e) {
			if (logger.isWarnEnabled()) {
				logger.warn("RMI service '" + this.serviceName
						+ "' is not bound to registry" + " at port '"
						+ this.rmiPort + "'", e);
			}
		} finally {
			cancleBoundExport();
		}
	}
}
