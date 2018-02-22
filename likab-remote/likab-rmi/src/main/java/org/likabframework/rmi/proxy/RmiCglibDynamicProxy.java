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
import org.likabframework.core.support.ProxyHandler;
import org.likabframework.rmi.invocation.RmiInvokeHandler;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ClassName:RmiCglibDynamicProxy
 * @author sxp
 * @date:2017年5月7日 上午11:28:20
 */
public class RmiCglibDynamicProxy extends AbstractRmiDynamicProxy implements MethodInterceptor,ProxyHandler {

	/**
	 * @param zkAddress
	 * @param serviceName
	 * @param loadBalance
	 */
	public RmiCglibDynamicProxy(String zkAddress, String serviceName,
			String loadBalance) {
		super(zkAddress, serviceName, loadBalance);
	}

	/* (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor
	 * @see #intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
	 */
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		String rmiUrl = getUrlFromZookeeperRegistryer();
		if (rmiUrl == null) {
			throw new IllegalAccessException("can not get serviceName["
					+ getServiceName() + "] rmi url from zookeeper registryer["
					+ getZkAddress() + "]");
		}
		Object stub = lookupStub(rmiUrl);
		if (stub instanceof RmiInvokeHandler) {
			return doRmiInvoker((RmiInvokeHandler)stub, method, args);
		}
		return method.invoke(stub, args);
	}
}
