package dev.alexis.hourlypaytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Hourly Pay Tracker application.
 * Initializes the Spring Boot application context.
 */
@SpringBootApplication
public class HourlyPayTrackerApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(HourlyPayTrackerApplication.class, args);
	}

}
