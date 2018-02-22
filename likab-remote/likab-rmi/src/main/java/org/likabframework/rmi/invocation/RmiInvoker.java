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
package org.likabframework.rmi.invocation;

import org.likabframework.core.remote.Invocation;

/**
 * ClassName:RmiInvoker
 * @author sxp
 * @date:2017年5月7日 上午9:58:39
 */
public class RmiInvoker implements RmiInvokeHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4840830185492014001L;
	
	private Object proxy;

	/**
	 * getProxy
	 * Object
	 */
	public Object getProxy() {
		return proxy;
	}

	/**
	 * setProxy
	 * void
	 */
	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	/**
	 * @param proxy
	 */
	public RmiInvoker(Object proxy) {
		super();
		this.proxy = proxy;
	}

	/* (non-Javadoc)
	 * @see org.likabframework.rmi.support.RmiInvokeHandler#invoke(org.likabframework.core.remote.Invocation)
	 */
	@Override
	public Object invoke(Invocation invocation) throws Exception {
		return invocation.invoke(proxy);
	}
}
