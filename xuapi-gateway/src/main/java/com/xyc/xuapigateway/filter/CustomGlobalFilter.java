package com.xyc.xuapigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author: xuYuYu
 * @createTime: 2024/5/4 19:31
 * @Description: TODO
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

	private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

	private static final String INTERFACE_HOST = "http://localhost:8123";

	/**
	 * 全局过滤
	 *
	 * @param exchange
	 * @param chain
	 * @return
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 请求日志
		ServerHttpRequest request = exchange.getRequest();
		String path = INTERFACE_HOST + request.getPath().value();
		String method = request.getMethod().toString();
		log.info("请求唯一标识：" + request.getId());
		log.info("请求路径：" + path);
		log.info("请求方法：" + method);
		log.info("请求参数：" + request.getQueryParams());
		String sourceAddress = request.getLocalAddress().getHostString();
		log.info("请求来源地址：" + sourceAddress);
		log.info("请求来源地址：" + request.getRemoteAddress());
		ServerHttpResponse response = exchange.getResponse();
		// 2. 访问控制 - 黑白名单
		if (!IP_WHITE_LIST.contains(sourceAddress)) {
			response.setStatusCode(HttpStatus.FORBIDDEN);
			return response.setComplete();
		}
		// 3. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
		// todo 实际情况应该是去数据库中查是否已分配给用户
        // User invokeUser = null;
        // try {
        //     invokeUser = innerUserService.getInvokeUser(accessKey);
        // } catch (Exception e) {
        //     log.error("getInvokeUser error", e);
        // }
        // if (invokeUser == null) {
        //     return handleNoAuth(response);
        // }
//        if (!"yupi".equals(accessKey)) {
//            return handleNoAuth(response);
//        }
//         if (Long.parseLong(nonce) > 10000L) {
//             return handleNoAuth(response);
//         }
//         // 时间和当前时间不能超过 5 分钟
//         Long currentTime = System.currentTimeMillis() / 1000;
//         final Long FIVE_MINUTES = 60 * 5L;
//         if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
//             return handleNoAuth(response);
//         }
//         // 实际情况中是从数据库中查出 secretKey
//         String secretKey = invokeUser.getSecretKey();
//         String serverSign = SignUtils.genSign(body, secretKey);
//         if (sign == null || !sign.equals(serverSign)) {
//             return handleNoAuth(response);
//         }
//         // 4. 请求的模拟接口是否存在，以及请求方法是否匹配
//         InterfaceInfo interfaceInfo = null;
//         try {
//             interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
//         } catch (Exception e) {
//             log.error("getInterfaceInfo error", e);
//         }
//         if (interfaceInfo == null) {
//             return handleNoAuth(response);
//         }
//         // todo 是否还有调用次数
//         // 5. 请求转发，调用模拟接口 + 响应日志
//         //        Mono<Void> filter = chain.filter(exchange);
//         //        return filter;
//         return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
