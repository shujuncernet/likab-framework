package org.likabframework.rmi.support;

import org.likabframework.core.factory.DynamicProxyFactory;
import org.likabframework.core.factory.ProxyFactoryBeanSupport;
import org.likabframework.rmi.proxy.RmiCglibDynamicProxy;
import org.likabframework.rmi.proxy.RmiJdkDynamicProxy;

public class RmiProxyFactoryBeanSupport extends ProxyFactoryBeanSupport {
	
	private String zkAddress = "localhost:2181";
	
	private String serviceName;
	
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

	/* (non-Javadoc)
	 * @see org.likabframework.core.factory.ProxyFactoryBeanSupport
	 * @see #getProxy(java.lang.Class)
	 */
	@Override
	public Object getProxy(Class<?> serviceClazz) {
		if (serviceClazz.isInterface()) {
			RmiJdkDynamicProxy jdkDynamicProxy = new RmiJdkDynamicProxy(zkAddress, serviceName, loadBalance);
			return DynamicProxyFactory.getProxy(serviceClazz, jdkDynamicProxy);
		}
		RmiCglibDynamicProxy cglibDynamicProxy = new RmiCglibDynamicProxy(zkAddress, serviceName, loadBalance);
		return DynamicProxyFactory.getProxy(serviceClazz, cglibDynamicProxy);
	}
}
