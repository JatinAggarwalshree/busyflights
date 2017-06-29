package com.travix.medusa.busyflights.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.httpclient.GetServiceResponse;
import com.travix.medusa.busyflights.utility.CommonUtilities;
import com.travix.medusa.busyflights.utility.FlightsInformationClientImpl;


public class BusyFlightServiceImpl implements BusyFlightService{

	public List<Object> findAllAvailableFlights(BusyFlightsRequest busyFlightsRequest) {
		
		CommonUtilities commonUtilities = new CommonUtilities();
		
		CrazyAirRequest crazyRequest = new CrazyAirRequest();
		crazyRequest.setOrigin(busyFlightsRequest.getOrigin());
		crazyRequest.setDestination(busyFlightsRequest.getDestination());
		crazyRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
		crazyRequest.setReturnDate(busyFlightsRequest.getReturnDate());
		crazyRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());
		
		ToughJetRequest toughJetRequest = new ToughJetRequest();
		toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
		toughJetRequest.setTo(busyFlightsRequest.getDestination());
		toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
		toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
		toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
		
		String crazyJson =   new FlightsInformationClientImpl().getAllFlightsFromCrazyAir(crazyRequest); 
		
		String toughJetJson = new FlightsInformationClientImpl().getAllFlightsFromToughJet(toughJetRequest);
		

		//JSON response being converted into List
		List<CrazyAirResponse> crazyAirList = (List<CrazyAirResponse>) commonUtilities.ObjectCrazyAir(crazyJson);
		
			    
		//JSON response being converted into List
		List<ToughJetResponse> toughJetList = (List<ToughJetResponse>) commonUtilities.ObjectToughJet(toughJetJson);
		
				
				List<Object> allFlightsList = new ArrayList<Object>();
				
				for (CrazyAirResponse object : crazyAirList) {
					System.out.println("object.getAirline(): "+object.getAirline());
					System.out.println("object.getArrivalDate(): "+object.getArrivalDate());
					System.out.println("object.getCabinclass(): "+object.getCabinclass());
					System.out.println("object.getDepartureAirportCode(): "+object.getDepartureAirportCode());			
					System.out.println("object.getDestinationAirportCode(): "+object.getDestinationAirportCode());
					System.out.println("object.getPrice(): "+object.getPrice());
					
					allFlightsList.add(object);
				}
				
				for (ToughJetResponse object : toughJetList) {
					System.out.println("object.getArrivalAirportName(): "+object.getArrivalAirportName());
					System.out.println("object.getBasePrice(): "+object.getBasePrice());
					System.out.println("object.getCarrier(): "+object.getCarrier());
					System.out.println("object.getDepartureAirportName(): "+object.getDepartureAirportName());
					System.out.println("object.getDiscount(): "+object.getDiscount());
					System.out.println("object.getInboundDateTime(): "+object.getInboundDateTime());
					System.out.println("object.getOutboundDateTime(): "+object.getOutboundDateTime());
					System.out.println("object.getTax(): "+object.getTax());
					
					allFlightsList.add(object);
				}
				
				//to be implemented
				System.out.println("Before Sorting >>>>>>");
				for(Object obj:allFlightsList)
					System.out.println(obj);
				allFlightsList = (List<Object>) commonUtilities.sortOnFlightsFare(allFlightsList);
				System.out.println("After Sorting >>>>>>");
				for(Object obj:allFlightsList)
					System.out.println(obj);
			
				allFlightsList = createBusyFlightsResponse(allFlightsList);
				System.out.println(allFlightsList);
				
				return allFlightsList;
				
				
	}

			/*
			 * finally the response object to be shared
			 * */
			private List<Object> createBusyFlightsResponse(List<Object> allFlightsList) {
				BusyFlightsResponse busyFlightsResponse = null;
				List<Object> responseList = new ArrayList<Object>();
				for (Object object : allFlightsList) {
					if (object instanceof CrazyAirResponse){
						busyFlightsResponse = new BusyFlightsResponse();
						busyFlightsResponse.setAirline(((CrazyAirResponse) object).getAirline());
						busyFlightsResponse.setSupplier("CrazyAir");
						busyFlightsResponse.setFare(((CrazyAirResponse) object).getPrice());
						busyFlightsResponse.setDepartureAirportCode(((CrazyAirResponse) object).getDepartureAirportCode());
						busyFlightsResponse.setDestinationAirportCode(((CrazyAirResponse) object).getDestinationAirportCode());
						busyFlightsResponse.setDepartureDate(((CrazyAirResponse) object).getDepartureDate());
						busyFlightsResponse.setArrivalDate(((CrazyAirResponse) object).getArrivalDate());
						
						responseList.add(busyFlightsResponse);
						
					}else if(object instanceof ToughJetResponse){
						busyFlightsResponse = new BusyFlightsResponse();
						busyFlightsResponse.setAirline(((ToughJetResponse) object).getCarrier());
						busyFlightsResponse.setSupplier("ToughJet");
						busyFlightsResponse.setFare((((ToughJetResponse) object).getBasePrice() + ((ToughJetResponse) object).getTax()) - 
								(((ToughJetResponse) object).getDiscount()/100)* (((ToughJetResponse) object).getBasePrice()));
						busyFlightsResponse.setDepartureAirportCode(((ToughJetResponse) object).getDepartureAirportName());
						busyFlightsResponse.setDestinationAirportCode(((ToughJetResponse) object).getArrivalAirportName());
						busyFlightsResponse.setDepartureDate(((ToughJetResponse) object).getOutboundDateTime());
						busyFlightsResponse.setArrivalDate(((ToughJetResponse) object).getInboundDateTime());
						
						responseList.add(busyFlightsResponse);
					}
				}
				return responseList;
			}
		
}
