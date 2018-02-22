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
package org.likabframework.core.factory;

import org.likabframework.core.support.CglibDynamicCreator;
import org.likabframework.core.support.JdkDynamicCreator;
import org.likabframework.core.support.ProxyHandler;

/**
 * ClassName:DynamicProxyFactory
 * @author sxp
 * @date:2017年5月7日 上午10:46:48
 */
public class DynamicProxyFactory {
	
	/**
	 * getProxy
	 * Object
	 */
	public static Object getProxy(Object target) {
		Class<?>[] interfaces = target.getClass().getInterfaces();
		if (interfaces != null) {
			JdkDynamicCreator jdkDynamicCreator = new JdkDynamicCreator(target);
			return jdkDynamicCreator.getProxy(jdkDynamicCreator);
		}
		CglibDynamicCreator cglibDynamicCreator = new CglibDynamicCreator(target);
		return cglibDynamicCreator.getProxy(cglibDynamicCreator);
	}
	
	/**
	 * getProxy
	 * Object
	 */
	public static Object getProxy(Class<?> targetClass, ProxyHandler handler) {
		if (targetClass.isInterface()) {
			JdkDynamicCreator jdkDynamicCreator = new JdkDynamicCreator();
			return jdkDynamicCreator.getProxy(targetClass, handler);
		}
		CglibDynamicCreator cglibDynamicCreator = new CglibDynamicCreator();
		return cglibDynamicCreator.getProxy(targetClass, handler);
	}
}
