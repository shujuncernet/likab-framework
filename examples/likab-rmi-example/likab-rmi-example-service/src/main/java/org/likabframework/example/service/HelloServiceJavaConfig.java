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
package org.likabframework.example.service;

import org.likabframework.rmi.support.RmiServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * ClassName:HelloServiceJavaConfig
 * 
 * @author sxp
 * @date:2017年5月13日 下午8:40:44
 */
@Component
public class HelloServiceJavaConfig {

	@Bean
	public RmiServiceExporter exporter() {
		RmiServiceExporter exporter = new RmiServiceExporter();
		IHelloService helloService = new HelloServiceImpl();
		exporter.setService(helloService);
		exporter.setZkAddress("192.168.233.130:2181");
		exporter.setServiceInterface(IHelloService.class);
		exporter.setServiceName("HelloService");
        return exporter;
	}
}
