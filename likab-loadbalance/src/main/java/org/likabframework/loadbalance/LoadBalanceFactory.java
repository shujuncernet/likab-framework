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
package org.likabframework.loadbalance;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * ClassName:LoadBalanceFactory
 * @author sxp
 * @date:2017年5月7日 下午6:28:49
 */
public class LoadBalanceFactory {
	
	/**
	 * Map<String,LoadBalance> the loadBalanceMap 
	 */
	private static final Map<String, LoadBalance> loadBalanceMap = new HashMap<>();
	
	static {
		ServiceLoader<LoadBalance> serviceLoader = ServiceLoader.load(LoadBalance.class);
		for(LoadBalance loadBalance : serviceLoader) {
			loadBalanceMap.put(loadBalance.getScheme(), loadBalance);
		}
	}
	
	/**
	* @MethodName: getLoadBalance
	* @Description: the getLoadBalance
	* @param scheme
	* @return LoadBalance
	*/
	public static LoadBalance getLoadBalance(String scheme) {
		return loadBalanceMap.get(scheme);
	}
}
