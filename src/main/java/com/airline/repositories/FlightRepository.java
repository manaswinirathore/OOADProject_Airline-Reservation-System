package com.airline.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.entities.Flight;


@Repository
public interface FlightRepository extends JpaRepository<Flight,String> {
List<Flight> findBySourceAndDestination(String source,String destination);
Flight findByFlightName(String flightName);

}
