package com.ebox3.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;


/**
 * Properties specific to WebGen backend.
 * <p>
 * Properties are configured in the application.properties file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {
	
	private final CorsConfiguration cors = new CorsConfiguration();

	public CorsConfiguration getCors() {
		return cors;
	}
		
}

