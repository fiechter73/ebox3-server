package com.ebox3.server;

import java.time.LocalDateTime;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ebox3.server.config.ApplicationProperties;

import jakarta.annotation.PostConstruct;


@EnableConfigurationProperties({ ApplicationProperties.class })
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	private final Log logger = LogFactory.getLog(getClass());
	
	  @PostConstruct
	  public void init() {
	     // TimeZone.setDefault(TimeZone.getTimeZone(DateUtils.));
	      TimeZone.setDefault(TimeZone.getTimeZone("Europe/Zurich"));
	      logger.info("Current time zone set to " + TimeZone.getTimeZone("Europe/Zurich"));
	      logger.info("Current time: " + LocalDateTime.now());
	  }

}
