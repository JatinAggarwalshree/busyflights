package com.travix.medusa.busyflights.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.controller.SearchAirlinesFlightsRestController;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

@Service("GetServiceResponse")
public class GetServiceResponse {
	
	public static final Logger logger = LoggerFactory.getLogger(GetServiceResponse.class);
	
	/**
	 * Sending Request to fetch CrazyAirResponse from airline
	 * @param crazyReq
	 * @param crazyUrl
	 * @return
	 * @throws Exception
	 */
	public String SendGetCrazy(CrazyAirRequest crazyReq, String crazyUrl) throws Exception {
		String crazyHTTP = "http";  //https
		String crazyPort = "80";
		URL objUrl = new URL(crazyUrl);
		InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
		Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
		HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
		con.setRequestMethod("GET");
		int responsecode = con.getResponseCode();
		logger.info("Fetching CrzyAirResponse for CrazyAirRequest{} ::", crazyReq);
		logger.info("Fetching CrzyAirResponse Response code ::", responsecode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();
		return response.toString();
		
	}
	
	/**
	 * Sending Request to fetch ToughJetResponse from airline
	 * @param toughReq
	 * @param toughUrl
	 * @return
	 * @throws Exception
	 */
	public String SendGetTough(ToughJetRequest toughReq, String toughUrl) throws Exception {
		String crazyHTTP = "http";  // https
		String crazyPort = "82";
		URL objUrl = new URL(toughUrl);
		InetSocketAddress proxyinet = new InetSocketAddress(crazyHTTP, Integer.parseInt(crazyPort));
		Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyinet);
		HttpURLConnection con = (HttpURLConnection) objUrl.openConnection(proxy);
		con.setRequestMethod("GET");
		int responsecode = con.getResponseCode();
		logger.info("Fetching CrzyAirResponse for CrazyAirRequest{} ::", toughReq);
		logger.info("Fetching CrzyAirResponse Response code ::", responsecode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();
		return response.toString();
		
	}

}
