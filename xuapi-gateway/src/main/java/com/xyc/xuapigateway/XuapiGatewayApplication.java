package com.xyc.xuapigateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class XuapiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(XuapiGatewayApplication.class, args);
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
