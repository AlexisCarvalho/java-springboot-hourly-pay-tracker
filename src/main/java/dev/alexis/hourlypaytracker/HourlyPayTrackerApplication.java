package dev.alexis.hourlypaytracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main entry point for the Hourly Pay Tracker application.
 * Initializes the Spring Boot application context.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
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
