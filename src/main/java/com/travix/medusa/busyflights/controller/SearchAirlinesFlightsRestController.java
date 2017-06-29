package com.travix.medusa.busyflights.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.BusyFlightService;
import com.travix.medusa.busyflights.service.BusyFlightServiceImpl;
import com.travix.medusa.busyflights.utility.CustomErrorType;


@Controller
@RequestMapping("/busyflightApi")
public class SearchAirlinesFlightsRestController {
	
	
	public static final Logger logger = LoggerFactory.getLogger(SearchAirlinesFlightsRestController.class);
	
	/*
	 * ----------->>>> Retrieve Airline Flights Search
	 */
	/*@Autowired	
	BusyFlightService busyFlight;*/

	
		@RequestMapping(value = "/search/{origin}/{destination}/{departureDate}/{returnDate}/{numberOfPassengers}", method = RequestMethod.GET)
		public ResponseEntity<?> getFlights(@PathVariable("origin") String origin,
											@PathVariable("destination") String destination,
											@PathVariable("departureDate") String departureDate,
											@PathVariable("returnDate") String returnDate,
											@PathVariable("numberOfPassengers") int numberOfPassengers) throws Exception {
			logger.info("Fetching available Airline Flights  with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {} ::", 
															      origin, destination, departureDate, returnDate, numberOfPassengers);
					
			BusyFlightsRequest busyFlightsrequest = new BusyFlightsRequest();
			busyFlightsrequest.setOrigin(origin);
			busyFlightsrequest.setDestination(destination);
			busyFlightsrequest.setDepartureDate(departureDate);
			busyFlightsrequest.setReturnDate(returnDate);
			busyFlightsrequest.setNumberOfPassengers(numberOfPassengers);
			
			if (numberOfPassengers > 4 || numberOfPassengers < 0 ) {
				logger.error("Flight search with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {}  not found. ::", 
															      origin, destination, departureDate, returnDate, numberOfPassengers);
				return new ResponseEntity(new CustomErrorType("Flight search with origin " + origin
						+ " destination " + destination
						+ " departureDate " + departureDate
						+ " returnDate " + returnDate
						+ " passengers limit should be less than 4"), HttpStatus.EXPECTATION_FAILED);
			}
			
			BusyFlightServiceImpl busyFlight = new BusyFlightServiceImpl();
			List <Object> flightsSearchData = busyFlight.findAllAvailableFlights(busyFlightsrequest);
			if (flightsSearchData == null) {
				logger.error("Flight search with origin {} , destination {} , departureDate {}, returnDate {}, numberOfPassengers {}  not found. ::", 
															      origin, destination, departureDate, returnDate, numberOfPassengers);
				return new ResponseEntity(new CustomErrorType("Flight search with origin " + origin
						+ " destination " + destination
						+ " departureDate " + departureDate
						+ " returnDate " + returnDate
						+ " numberOfPassengers " + numberOfPassengers
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			
			
			return new ResponseEntity<List<Object>>(flightsSearchData, HttpStatus.OK);
		}

}
