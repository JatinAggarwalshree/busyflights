package com.travix.medusa.busyflights.utility;


import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

public class FlightsInformationClientImpl implements FlightsInformationClient{

	private DefaultHttpClient httpclient;
	private HttpHost target;
	private HttpGet getRequest;
	private HttpResponse httpResponse;
	private HttpEntity entity;
	private String serviceLocation;
	
	@Override	
	public String getAllFlightsFromCrazyAir(CrazyAirRequest request) {
		String responseCrazyAir = "";
		serviceLocation = "/flightSearchCrazyAir/"+request.getOrigin()+"/"+request.getDestination()+"/"+request.getDepartureDate()+"/"+request.getReturnDate()+"/"+request.getPassengerCount();

		httpclient = new DefaultHttpClient();
	    try {
	      // specify the host, protocol, and port for
	      target = new HttpHost("localhost", 8080, "http");
	      
	      // specify the get request
	      getRequest = new HttpGet(serviceLocation);

	      System.out.println("executing request to " + target);

	      httpResponse = httpclient.execute(target, getRequest);
	      entity = httpResponse.getEntity();

	      System.out.println("----------------------------------------");
	      System.out.println(httpResponse.getStatusLine());
	      Header[] headers = httpResponse.getAllHeaders();
	      for (int i = 0; i < headers.length; i++) {
	        System.out.println(headers[i]);
	      }
	      System.out.println("----------------------------------------");

	      responseCrazyAir = EntityUtils.toString(entity);
	      System.out.println("JSON Response from CrazyAir WS");
	      System.out.println(responseCrazyAir);

	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      // When HttpClient instance is no longer needed,
	      // shut down the connection manager to ensure
	      // immediate deallocation of all system resources
	      httpclient.getConnectionManager().shutdown();
	    }	
		return responseCrazyAir;
	}

	@Override
	public String getAllFlightsFromToughJet(ToughJetRequest request) {
		String responseToughJet = "";
		  serviceLocation = "/flightSearchToughJet/"+request.getFrom()+"/"+request.getTo()+"/"+request.getOutboundDate()+"/"+request.getInboundDate()+"/"+request.getNumberOfAdults();
			httpclient = new DefaultHttpClient();
		    try {
		      // specify the host, protocol, and port
		      target = new HttpHost("localhost", 8080, "http");
		      
		      // specify the get request
		      getRequest = new HttpGet(serviceLocation);
	
		      System.out.println("executing request to " + target);
	
		      httpResponse = httpclient.execute(target, getRequest);
		      entity = httpResponse.getEntity();
	
		      System.out.println("----------------------------------------");
		      System.out.println(httpResponse.getStatusLine());
		      Header[] headers = httpResponse.getAllHeaders();
		      for (int i = 0; i < headers.length; i++) {
		        System.out.println(headers[i]);
		      }
		      System.out.println("----------------------------------------");
	
		      responseToughJet = EntityUtils.toString(entity);
		      System.out.println("JSON Response from ToughJet WS");
		      System.out.println(responseToughJet);
		      
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // When HttpClient instance is no longer needed,
		      // shut down the connection manager to ensure
		      // immediate deallocation of all system resources
		      httpclient.getConnectionManager().shutdown();
		    }	
			return responseToughJet;
	}
}