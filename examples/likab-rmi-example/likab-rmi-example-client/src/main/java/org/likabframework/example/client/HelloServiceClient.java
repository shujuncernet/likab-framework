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
package org.likabframework.example.client;

import org.likabframework.example.service.IHelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName:HelloServiceClient
 * @author sxp
 * @date:2017年5月13日 下午7:55:32
 */
public class HelloServiceClient {
    public static void main(String[] args ) {
    	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	IHelloService helloService = (IHelloService) applicationContext.getBean("helloService");
    	System.out.println("返回结果：" + helloService.sayHello("likab"));
    	applicationContext.close();
    }
}
