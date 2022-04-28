package com.airline.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.dto.PassengerDTO;
import com.airline.entities.Flight;
import com.airline.entities.Passenger;
import com.airline.entities.Ticket;
import com.airline.entities.User;
import com.airline.exceptions.CommonUserException;
import com.airline.repositories.FlightRepository;
import com.airline.repositories.TicketRepository;
import com.airline.repositories.UserRepository;
import com.airline.service.FlightService;
import com.airline.service.TicketService;
import com.airline.service.UserService;

@Service
public class TicketServiceImpl implements TicketService {
	private static final Logger logger = LogManager.getLogger(TicketServiceImpl.class);
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	FlightService flightService;
	@Autowired
	FlightRepository flightRepository;

	@Override
	public int getTotalAmount(ArrayList<PassengerDTO> listOfPassenger) {
		int totalAmount = 0;
		for (PassengerDTO p : listOfPassenger) {
			if (p.getSeatPreference().equals("Economy"))
				totalAmount += 1500;
			else if (p.getSeatPreference().equals("Business"))
				totalAmount += 3800;
			else
				totalAmount += 6000;
		}
		logger.info("Total Amount to pay {}", totalAmount);
		return totalAmount;
	}

	@Override
	public void createTicket(User user, Flight flight,  ArrayList<PassengerDTO> listOfPassengerDTO, int totalAmount) {
		logger.info("Creating ticket for user with Id {}", user.getEmail());
		ArrayList<Passenger> listOfPassenger=new ArrayList<>();
		User user1 = userService.getUserById(user.getEmail());
		Ticket ticket = new Ticket();
		ticket.setNoOfTickets(listOfPassengerDTO.size());
		ticket.setFlightId(flight);
		ticket.setUserId(user1);
		ticket.setAmountPaid(totalAmount);
		for (PassengerDTO p : listOfPassengerDTO) {
			listOfPassenger.add(p.convertToEntity());
			
		}
		for(Passenger p:listOfPassenger) {
			p.setTicketId(ticket);
		}
		ticket.setListOfPassenger(listOfPassenger);
		ticketRepository.save(ticket);
	}

	@Override
	public void decreaseTicketCounts(Flight flight, ArrayList<PassengerDTO> listOfPassenger) {
		logger.info("Decreasing Ticket Counts");
		int countE = 0;
		int countB = 0;
		int countF = 0;
		for (PassengerDTO passenger : listOfPassenger) {
			if (passenger.getSeatPreference().equals("Economy")) {
				countE++;
			} else if (passenger.getSeatPreference().equals("Business")) {
				countB++;
			} else {
				countF++;
			}
		}

		Flight proxyFlight = flightService.getByFlightNo(flight.getFlightNo());

		if ((proxyFlight.getEconomy() - countE >= 0) && (proxyFlight.getBusiness() - countB >= 0)
				&& (proxyFlight.getFirstClass() - countF >= 0)) {
			proxyFlight.setEconomy(proxyFlight.getEconomy() - countE);
			proxyFlight.setBusiness(proxyFlight.getBusiness() - countB);
			proxyFlight.setFirstClass(proxyFlight.getFirstClass() - countF);
			flightRepository.save(proxyFlight);
		} else {
			logger.warn("Ticket Booking failed,Ticket not available for one of your seat preferences");
			throw new CommonUserException("E102",
					"No tickets Available in one of your seat preferences.Kindly check ticket availability");
		}
	}

	@Override
	public void cancelOrDeleteTicket(int ticketId, User user) {
		Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
		if (optionalTicket.isPresent()) {
			Ticket ticket = optionalTicket.get();
			ticket.getListOfPassenger().clear();
			ticketRepository.save(ticket);
			ticketRepository.deleteById(ticketId);
		}

	}

	@Override
	public void increaseTicketCounts(int ticketId) {
		logger.info("Increasing Ticket counts....");
		Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
		if (optionalTicket.isPresent()) {
			Ticket ticket = optionalTicket.get();
			int countE = 0;
			int countB = 0;
			int countF = 0;
			for (Passenger passenger : ticket.getListOfPassenger()) {
				if (passenger.getSeatPreference().equals("Economy")) {
					countE++;
				} else if (passenger.getSeatPreference().equals("Business")) {
					countB++;
				} else {
					countF++;
				}
			}
			Flight proxyFlight = flightService.getByFlightNo(ticket.getFlightId().getFlightNo());
			proxyFlight.setEconomy(proxyFlight.getEconomy() + countE);
			proxyFlight.setBusiness(proxyFlight.getBusiness() + countB);
			proxyFlight.setFirstClass(proxyFlight.getFirstClass() + countF);
			flightRepository.save(proxyFlight);
		}

	}

	@Override
	public List<Ticket> getUserTickets(String email) {
		logger.info("Getting Tickets of User with id{}", email);
		return userService.getUserById(email).getListOfTickets();
	}

	public Ticket getByTicketId(int ticketId) {
		Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
		if (optionalTicket.isPresent()) {
			return optionalTicket.get();
		} else {
			logger.warn("Ticket not found with Id {}", ticketId);
			return null;
		}
	}
}