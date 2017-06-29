package com.travix.medusa.busyflights.utility;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages= {"com.travix.medusa.busyflights.controller.SearchAirlinesFlightsRestController.class","com.travix.medusa.busyflights.service.BusyFlightServiceImpl.class","com.travix.medusa.busyflights.controller"})
@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(BusyFlightsApplication.class, args);
	}
}
