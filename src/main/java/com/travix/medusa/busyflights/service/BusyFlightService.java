package com.travix.medusa.busyflights.service;

import java.util.List;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

public interface BusyFlightService {
	
	List<Object> findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest);
	
	
}
