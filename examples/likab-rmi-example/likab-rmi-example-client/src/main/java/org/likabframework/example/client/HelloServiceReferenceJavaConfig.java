package org.likabframework.example.client;

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
import org.likabframework.example.service.IHelloService;
import org.likabframework.loadbalance.LoadBalance;
import org.likabframework.rmi.support.RmiProxyFactoryBean;
import org.springframework.context.annotation.Bean;

/**
 * ClassName:HelloServiceReferenceJavaConfig
 * @author sxp
 * @date:2017年5月14日 上午9:21:52
 */
public class HelloServiceReferenceJavaConfig {
	
	@Bean
	public RmiProxyFactoryBean factory() {
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setLoadBalance(LoadBalance.CONSUMER_HASH_LOADBALANCE);
		factory.setServiceName("HelloService");
		factory.setServiceInterface(IHelloService.class);
		factory.setZkAddress("192.168.233.130:2181");
		return factory;
	}
	
	@Bean
	public IHelloService helloService() {
		RmiProxyFactoryBean factory = factory();
		return (IHelloService) factory.getObject();
	}

}
