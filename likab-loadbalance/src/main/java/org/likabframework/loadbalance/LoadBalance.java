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

import java.util.List;

/**
 * ClassName:LoadBalance
 * @author sxp
 * @date:2017年5月7日 下午6:28:45
 */
public interface LoadBalance {
	
	/**
	 * String the ROUND_ROBIN_LOADBALANCE 
	 */
	String ROUND_ROBIN_LOADBALANCE = "roundRobin";

	/**
	 * String the RANDOM_LOADBALANCE 
	 */
	String RANDOM_LOADBALANCE = "random";
	
	/**
	 * String the CONSUMER_HASH_LOADBALANCE 
	 */
	String CONSUMER_HASH_LOADBALANCE = "consumerHash";
	
	/**
	* @MethodName: getScheme
	* @Description: the getScheme
	* @return String
	*/
	String getScheme();
	
    /**
    * @MethodName: loadBalance
    * @Description: the loadBalance
    * @param remoteIP
    * @param serverList
    * @return String
    */
    String loadBalance(String remoteIP, List<String> serverList);
}
