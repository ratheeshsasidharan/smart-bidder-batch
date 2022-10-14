package com.smartbidderbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SmartBidderBatchApplication {

	public static void main(String[] args) {
		log.info("Starting Smart Bidder batch application");
		SpringApplication.run(SmartBidderBatchApplication.class, args);
	}

}
