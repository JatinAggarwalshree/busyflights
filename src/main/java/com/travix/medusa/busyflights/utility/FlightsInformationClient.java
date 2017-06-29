package com.travix.medusa.busyflights.utility;


import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

public interface FlightsInformationClient {
	String getAllFlightsFromCrazyAir(CrazyAirRequest request);
	String getAllFlightsFromToughJet(ToughJetRequest request);
}
