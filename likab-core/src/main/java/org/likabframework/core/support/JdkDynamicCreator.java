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
package org.likabframework.core.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.likabframework.core.util.ClassLoaderUtils;

/**
 * ClassName:JdkDynamicCreator
 * @author sxp
 * @date:2017年4月15日 下午7:40:11
 */
public class JdkDynamicCreator implements DynamicCreator,InvocationHandler,ProxyHandler {
	
	private Object target;
	
	private static final Log logger = LogFactory.getLog(JdkDynamicCreator.class);
	
	public JdkDynamicCreator() {}
	
	/**
	 * 
	 */
	public JdkDynamicCreator(Object target) {
		this.target = target;
	}

	/**
	 * getTarget
	 * Object
	 */
	public Object getTarget() {
		return target;
	}

	/**
	 * setTarget
	 * void
	 */
	public void setTarget(Object target) {
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler
	 * @see #invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		logger.debug("method invoke begin......");
		Object result = method.invoke(target, args);
		logger.debug("method invoke successfully");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.likabframework.core.support.DynamicCreator#getProxy()
	 */
	public Object getProxy(ProxyHandler handler) {
		Class<?>[] interfaces = target.getClass().getInterfaces();
		return newProxyInstance(interfaces, handler);
	}

	/* (non-Javadoc)
	 * @see org.likabframework.core.support.DynamicCreator
	 * @see #getProxy(java.lang.Class, org.likabframework.core.support.ProxyHandler)
	 */
	@Override
	public Object getProxy(Class<?> targetClass, ProxyHandler handler) {
		Class<?>[] interfaces = targetClass.getInterfaces();
		if (targetClass.isInterface()) {
			if (interfaces == null) {
				interfaces = new Class<?>[] {targetClass};
			} else {
				interfaces = getInterfaces(targetClass, interfaces);
			}
		}
		return newProxyInstance(interfaces, handler);
	}
	
	/**
	 * getInterfaces
	 * Class<?>[]
	 */
	private Class<?>[] getInterfaces(Class<?> targetClass, Class<?>[] interfaces) {
		Class<?>[] interfaceArray = new Class<?>[interfaces.length + 1];
		for (int i=0; i<interfaces.length; i++) {
			interfaceArray[i] = interfaces[i];
		}
		interfaceArray[interfaces.length] = targetClass;
		return interfaceArray;
	}
	
	/**
	 * newProxyInstance
	 * Object
	 */
	private Object newProxyInstance(Class<?>[] interfaces, ProxyHandler handler) {
		ClassLoader classLoader = ClassLoaderUtils.getDefaultClassLoader();
		if (!InvocationHandler.class.isAssignableFrom(handler.getClass())) {
			throw new ClassCastException("can not cast type ProxyHandler to InvocationHandler");
		}
		InvocationHandler invocationHandler = (InvocationHandler) handler;
		return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
	}
}
