package com.airline.controllers;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.entities.Flight;
import com.airline.entities.Ticket;
import com.airline.entities.User;
import com.airline.service.FlightService;
import com.airline.service.TicketService;
import com.airline.service.UserService;

@Controller
public class TicketController {
	private static final Logger logger = LogManager.getLogger(TicketController.class);
	private static final String TICKET_DEATILS_PAGE = "admin/ticketdetails"; // Compliant

	@Autowired
	FlightService flightService;
	@Autowired
	TicketService ticketService;
	@Autowired
	UserService userService;

	@GetMapping("/viewtickets")
	public ModelAndView viewUserTickets(HttpServletRequest request) {
		logger.info("Getting ticket...");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		HashSet<Ticket> listOfTickets = new HashSet<>(ticketService.getUserTickets(user.getEmail()));
		logger.info("Tickets found for user with Id {}", user.getEmail());
		return new ModelAndView("yourtickets", "listOfTickets", listOfTickets);

	}
	
	@GetMapping("/ticketdetails")
	public String ticketDetailsPage() {
		return TICKET_DEATILS_PAGE;
	}

	@GetMapping("/getfulldetails")
	public ModelAndView getFullDetails(@RequestParam("flightName") String flightName) {
		logger.info("Searching for flight tickets with the flight name {}", flightName);
		Flight flight = flightService.getByFlightName(flightName);
		if (flight != null) {
			logger.info("flight ticket details with the flight name {} has found", flightName);
			List<Ticket> ticketDetails = flight.getListOfTickets();
			return new ModelAndView(TICKET_DEATILS_PAGE, "ticketDetails", ticketDetails);
		} else {
			logger.info("Searching for flight with the name {} Not found", flightName);
			return new ModelAndView(TICKET_DEATILS_PAGE, "noFlightFound",
					"No flight available in this name " + "'" + flightName + "'");
		}

	}

	@GetMapping("/cancelticket/{ticketId}")
	public String cancelOrDeleteTicket(@PathVariable int ticketId, HttpSession session,
			RedirectAttributes redirectAttributes) {
		logger.info("Cancelling/deleting ticket....");
		User user = (User) session.getAttribute("currentUser");
		String flightName = ticketService.getByTicketId(ticketId).getFlightId().getFlightName();
		ticketService.increaseTicketCounts(ticketId);
		ticketService.cancelOrDeleteTicket(ticketId, user);
		redirectAttributes.addFlashAttribute("successfulmsg", "Ticket deleted with its corresponding passengers");
		logger.info("Ticket Cancelled or deleted Successful");
		if (user.getRole().equals("USER")) {
			return "redirect:/viewtickets";
		} else {

			return "redirect:/getfulldetails?flightName=" + flightName;

		}
	}
}
