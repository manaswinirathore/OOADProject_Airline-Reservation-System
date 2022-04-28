package com.airline.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.airline.dto.FlightDTO;
import com.airline.dto.PassengerDTO;
import com.airline.entities.User;
import com.airline.exceptions.CommonFlightException;
import com.airline.service.FlightService;
import com.airline.service.TicketService;

@Controller
@RequestMapping("/book/{flightNo}")
public class BookingController {
	private static final Logger logger = LogManager.getLogger(BookingController.class);
	private static final String TOTAL_AMOUNT = "totalAmount";  
	private static final String LIST_OF_PASSENGER = "listOfPassenger"; 
	@Autowired
	FlightService flightService;
	@Autowired
	TicketService ticketService;
	
	@ModelAttribute("selectedFlight")
	public FlightDTO selectedFlightModel(@PathVariable String flightNo) {
		return flightService.getByFlightNo(flightNo).convertToDTO();
	}
	@GetMapping("/")
	public ModelAndView redirectToPassengerForm(@PathVariable String flightNo) {
		return new ModelAndView("addpassengers");
	}

	@PostMapping("/addpassenger")
	public String addPassenger(@ModelAttribute PassengerDTO passenger,@PathVariable String flightNo,
			HttpServletRequest request) {
		logger.info("adding passenger.....");
		HttpSession session=request.getSession();
		if(session.getAttribute(LIST_OF_PASSENGER)==null) {
			session.setAttribute(LIST_OF_PASSENGER, new ArrayList<PassengerDTO>());
		}
		 ArrayList<PassengerDTO> listOfPassenger=(ArrayList<PassengerDTO>) session.getAttribute(LIST_OF_PASSENGER);
		 listOfPassenger.add(passenger);
		 session.setAttribute(LIST_OF_PASSENGER, listOfPassenger);
		 int totalAmount=0;
		 if(session.getAttribute(TOTAL_AMOUNT)==null) 
			  session.setAttribute(TOTAL_AMOUNT, totalAmount);
		 totalAmount=ticketService.getTotalAmount(listOfPassenger);
		session.setAttribute(TOTAL_AMOUNT, totalAmount);
		return "redirect:/book/"+flightNo+"/";
	}

	@GetMapping("/removepassenger/{index}")
	public String removePassenger(@PathVariable int index, HttpServletRequest request,@PathVariable String flightNo) {
		logger.info("removing passenger.....");
		HttpSession session=request.getSession();
		int totalAmount=0;
		if(session.getAttribute(LIST_OF_PASSENGER)!=null) {
			ArrayList<PassengerDTO> listOfPassenger=(ArrayList<PassengerDTO>) session.getAttribute(LIST_OF_PASSENGER);
			 listOfPassenger.remove(index);
			 totalAmount=ticketService.getTotalAmount(listOfPassenger);
				session.setAttribute(TOTAL_AMOUNT, totalAmount);
			 session.setAttribute(LIST_OF_PASSENGER, listOfPassenger);
		}
		return "redirect:/book/"+flightNo+"/";

	}
	@GetMapping("/payment/successful")
	public String paymentSuccessfull(HttpServletRequest request,@PathVariable String flightNo,@ModelAttribute("selectedFlight") FlightDTO flight) throws CommonFlightException{
		logger.info("Onsuccessful payment...");
		HttpSession session=request.getSession();
		ArrayList<PassengerDTO> listOfPassenger=(ArrayList<PassengerDTO>) session.getAttribute(LIST_OF_PASSENGER);
		User user=(User) session.getAttribute("currentUser");
		
		//creating Ticket
		ticketService.createTicket(user,flight.convertToEntity(),listOfPassenger,(int) session.getAttribute(TOTAL_AMOUNT));
		ticketService.decreaseTicketCounts(flight.convertToEntity(), listOfPassenger);
		
		logger.info("Ticket Booked Successful for user with id {} for the flight No {}",user.getEmail(),flight.getFlightNo());
		session.removeAttribute(LIST_OF_PASSENGER);
		session.removeAttribute(TOTAL_AMOUNT);
		return "successful";
		
		
		
	}
	@GetMapping("/payment")
	public ModelAndView paymentForm(){
		return new ModelAndView("paymentform");
	}
}
