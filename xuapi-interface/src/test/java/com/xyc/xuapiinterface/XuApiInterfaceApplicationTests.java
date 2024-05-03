package com.xyc.xuapiinterface;

import com.xyc.xuapiclientsdk.client.XuApiClient;
import com.xyc.xuapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class XuApiInterfaceApplicationTests {
	@Resource
	private XuApiClient xuApiClient;

	@Test
	void contextLoads() {
		String result = xuApiClient.getNameByGet("yupi");
		User user = new User();
		user.setUsername("xuyc");
		String usernameByPost = xuApiClient.getUsernameByPost(user);
		System.out.println(result);
		System.out.println(usernameByPost);
	}

}
