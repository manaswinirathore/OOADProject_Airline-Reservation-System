package com.airline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AirlineReservationSystemApplication {
	private static final Logger logger = LogManager.getLogger(AirlineReservationSystemApplication.class);
	public static void main(String[] args) {
		logger.info("Entering Application...");
		SpringApplication.run(AirlineReservationSystemApplication.class, args);
	
	}
	
}
