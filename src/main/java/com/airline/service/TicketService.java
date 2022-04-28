package com.airline.service;


import java.util.ArrayList;
import java.util.List;

import com.airline.dto.PassengerDTO;
import com.airline.entities.Flight;
import com.airline.entities.Ticket;
import com.airline.entities.User;

public interface TicketService {
	void createTicket(User user, Flight flight, ArrayList<PassengerDTO> listOfPassenger, int totalAmount);

	int getTotalAmount(ArrayList<PassengerDTO> listOfPassenger);

	void decreaseTicketCounts(Flight flight, ArrayList<PassengerDTO> listOfPassenger);

	void cancelOrDeleteTicket(int ticketId, User user);

	void increaseTicketCounts(int ticketId);

	public Ticket getByTicketId(int ticketId);

	List<Ticket> getUserTickets(String email);
}
