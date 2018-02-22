package org.likabframework.example.tomcat;


/** 
* @ClassName: core TomcatBootStrap 
* @Description:嵌入式tomcat
*/
public class TomcatBootStrap {
	public static void main(String[] args) {
		new TomcatBootstrapHelper(8080, false, "dev").start();
	}
}
