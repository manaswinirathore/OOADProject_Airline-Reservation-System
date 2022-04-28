package com.airline.service;

import java.util.List;

import com.airline.entities.Flight;

public interface FlightService {
	void createFlight(Flight flight);

	List<Flight> getAllFlights();

	Flight getByFlightNo(String flightNo);

	void deleteFlight(String id);

	void updateFlight(Flight flight);

	List<Flight> getBySourceAndDestination(String source, String destination);

	Flight getByFlightName(String name);
}
