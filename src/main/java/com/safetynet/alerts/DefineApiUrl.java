package com.safetynet.alerts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="com.safetynet.alerts.apiUrl")
public class DefineApiUrl {
	
	private String apiUrl;

}
