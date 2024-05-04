package com.xyc.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: xuYuYu
 * @createTime: 2024/5/4 11:30
 * @Description: TODO
 */

@SpringBootTest
class UserInterfaceInfoServiceTest {
	@Autowired
	UserInterfaceInfoService userInterfaceInfoService;
	@Test
	void invokeCount() {
		boolean count = userInterfaceInfoService.invokeCount(1, 1);
		assertTrue(count);
	}
}
