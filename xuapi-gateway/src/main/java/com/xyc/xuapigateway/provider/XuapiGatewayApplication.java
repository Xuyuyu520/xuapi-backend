package com.xyc.xuapigateway.provider;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class})

@Service
@EnableDubbo
public class XuapiGatewayApplication {

	@DubboReference
	private DemoService demoService;

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(XuapiGatewayApplication.class, args);
		XuapiGatewayApplication application = context.getBean(XuapiGatewayApplication.class);
		String result = application.doSayHello("world");
		String result2 = application.doSayHello2("world");
		System.out.println("result: " + result);
		System.out.println("result: " + result2);
	}

	public String doSayHello(String name) {
		return demoService.sayHello(name);
	}

	public String doSayHello2(String name) {
		return demoService.sayHello2(name);
	}

	// @Bean
	// public RouteLocator myRoutes(RouteLocatorBuilder builder) {
	// 	return builder.routes()
	// 			// Add a simple re-route from: /get to: http://httpbin.org:80
	// 			// Add a simple "Hello:World" HTTP Header
	// 			.route(p -> p
	// 					.path("/github") // intercept calls to the /get path
	// 					.filters(f -> f.addRequestHeader("Hello", "World")) // add header
	// 					.uri("https://github.com/Xuyuyu520")) // forward to httpbin
	// 			.build();
	// }


}
