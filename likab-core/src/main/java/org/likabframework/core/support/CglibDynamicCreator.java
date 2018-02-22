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

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ClassName:CglibDynamicCreator
 * @author sxp
 * @date:2017年4月15日 下午7:45:39
 */
public class CglibDynamicCreator implements DynamicCreator,MethodInterceptor,ProxyHandler {

	private Object target;
	
	private static final Log logger = LogFactory.getLog(JdkDynamicCreator.class);
	
	public CglibDynamicCreator() {}
	
	public CglibDynamicCreator(Object target) {
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
	 * @see net.sf.cglib.proxy.MethodInterceptor
	 * @see #intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		logger.debug("method invoke begin......");
		Object result = method.invoke(target, args);
		logger.debug("method invoke successfully");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.likabframework.core.support.DynamicCreator#getProxy()
	 */
	@Override
	public Object getProxy(ProxyHandler handler) {
		Class<?> superClass = target.getClass().getSuperclass();
		return create(superClass, handler);
	}

	/* (non-Javadoc)
	 * @see org.likabframework.core.support.DynamicCreator
	 * @see #getProxy(java.lang.Class, org.likabframework.core.support.ProxyHandler)
	 */
	@Override
	public Object getProxy(Class<?> targetClass, ProxyHandler handler) {
		Class<?> superClass = targetClass.getSuperclass();
		return create(superClass, handler);
	}
	
	/**
	 * create
	 * Object
	 */
	private Object create(Class<?> superClass, ProxyHandler handler) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(superClass);
		if (!Callback.class.isAssignableFrom(handler.getClass())) {
			throw new ClassCastException("can not cast type ProxyHandler to Callback");
		}
		Callback callbackHandler = (Callback) handler;
	    enhancer.setCallback(callbackHandler);
		return enhancer.create();
	}
}
