package com.airline.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.entities.Flight;
import com.airline.exceptions.CommonFlightException;
import com.airline.repositories.FlightRepository;
import com.airline.service.FlightService;
@Service
public class FlightServiceImpl implements FlightService{
	private static final Logger logger = LogManager.getLogger(FlightServiceImpl.class);
	@Autowired
	FlightRepository flightRepository;
	@Override
	public void createFlight(Flight flight) {
		Optional<Flight> checkingFlight=flightRepository.findById(flight.getFlightNo());
		if(checkingFlight.isPresent()) {
			logger.warn("Flight No already exist");
			throw new CommonFlightException("E200","Flight No Already Exist");
		}
		else {
			flightRepository.save(flight);
		}
	}
	@Override
	public List<Flight> getAllFlights() {
		
		return flightRepository.findAll();
	}
	@Override
	public void deleteFlight(String id) {
		flightRepository.deleteById(id);
		
	}
	@Override
	public void updateFlight(Flight flight) throws CommonFlightException{
		Optional<Flight> optionalFlight=flightRepository.findById(flight.getFlightNo());
		if(optionalFlight.isPresent()) {
		Flight oldFlight=optionalFlight.get();
		oldFlight.setFlightName(flight.getFlightName());
		oldFlight.setEconomy(flight.getEconomy());
		oldFlight.setBusiness(flight.getBusiness());
		oldFlight.setFirstClass(flight.getFirstClass());
		oldFlight.setSource(flight.getSource());
		oldFlight.setDestination(flight.getDestination());
		oldFlight.setArrivalTime(flight.getArrivalTime());
		oldFlight.setDepatureTime(flight.getDepatureTime());
		oldFlight.setDate(flight.getDate());
		flightRepository.save(oldFlight);
		}
		else {
			throw new CommonFlightException("201", "Flight Not Found");
		}
		 
	}
	@Override
	public Flight getByFlightNo(String flightNo) throws CommonFlightException {
		Optional<Flight> optionalFlight=flightRepository.findById(flightNo);
		if(optionalFlight.isPresent()) {
		return optionalFlight.get();
		}
		else {
			logger.warn("Updatin flight with {} failed",flightNo);
			throw new CommonFlightException("201", "Flight Not Found");
		}
	}
	@Override
	public List<Flight> getBySourceAndDestination(String source, String destination) {
		return flightRepository.findBySourceAndDestination(source, destination);
	}
	
	@Override
	public Flight getByFlightName(String flightName) {
		
		return flightRepository.findByFlightName(flightName);
	}

}
