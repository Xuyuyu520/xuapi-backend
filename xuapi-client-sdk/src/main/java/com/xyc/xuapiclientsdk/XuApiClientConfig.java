package com.xyc.xuapiclientsdk;

import com.xyc.xuapiclientsdk.client.XuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xuYuYu
 * @createTime: 2024/5/3 9:29
 * @Description: TODO
 */
@Data
@Configuration
@ComponentScan
@ConfigurationProperties("xuapi-client")
public class XuApiClientConfig {
	private String accessKey;
	private String secretKey;
	@Bean
	public XuApiClient xuApiClient() {
		return new XuApiClient(accessKey, secretKey);
	}
}
