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

import java.io.Serializable;
import java.rmi.Remote;

import org.likabframework.core.remote.Invocation;

/**
 * ClassName:RmiInvokeHandler
 * @author sxp
 * @date:2017年5月7日 上午10:01:21
 */
public interface RmiInvokeHandler extends Remote, Serializable {
	
	/**
	 * invoke
	 * Object
	 */
	Object invoke(Invocation invocation) throws Exception;

}
