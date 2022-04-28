package com.airline.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.airline.dto.FlightDTO;
import com.airline.entities.Flight;
import com.airline.service.FlightService;


@Controller
public class FlightController {
	private static final Logger logger = LogManager.getLogger(FlightController.class);
	private static final String SUCCESS_TOAST_MSG = "successfulmsg"; //
	@Autowired
	FlightService flightService;

	@PostMapping("/addflight")
	public String addFlight(@ModelAttribute FlightDTO flight, RedirectAttributes redirectAttributes) {
		logger.info("Adding New Flight....");
		flightService.createFlight(flight.convertToEntity());
		logger.info("New flight with {} added successful", flight.getFlightNo());
		redirectAttributes.addFlashAttribute(SUCCESS_TOAST_MSG, "Flight added Successfully");
		return "redirect:/flightform";
	}

	@GetMapping("/viewflights")
	public ModelAndView viewFlights() {
		logger.info("Getting List of Flights...");
		List<Flight> listOfFlights = flightService.getAllFlights();
		return new ModelAndView("admin/viewflights", "listOfFlights", listOfFlights);
	}

	@GetMapping("/deleteflight/{flightNo}")
	public String deleteFlight(@PathVariable String flightNo, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("Trying to delete flight with No {}", flightNo);
		flightService.deleteFlight(flightNo);
		redirectAttributes.addFlashAttribute(SUCCESS_TOAST_MSG, "Flight deleted Successful");
		logger.info("Flight with {} deleted Successful", flightNo);
		return "redirect:/viewflights";

	}

	@PostMapping("/updateflight")
	public String updateFlight(@ModelAttribute FlightDTO flight, RedirectAttributes redirectAttributes) {
		logger.info("Updating flight with {}...", flight.getFlightNo());
		flightService.updateFlight(flight.convertToEntity());
		logger.info("Flight with {} updated successful", flight.getFlightNo());
		redirectAttributes.addFlashAttribute(SUCCESS_TOAST_MSG, "Flight updated Successful");
		return "redirect:/viewflights";
	}

	@GetMapping("/updateFlightModal/{flightNo}")
	public ModelAndView redirectUpdateModal(@PathVariable String flightNo) {
		Flight updateFlightObj = flightService.getByFlightNo(flightNo);
		logger.info("opening form for updating flight...");
		return new ModelAndView("/admin/updateFlight", "updateFlightObj", updateFlightObj);

	}

	@PostMapping("/available/flights")
	public ModelAndView availableFlights(@RequestParam("source") String source,
			@RequestParam("destination") String destination, @RequestParam("date") String date, Model model) {

		logger.info("Getting available flights from {} to {}", source, destination);
		List<Flight> listOfAvailFlights = flightService.getBySourceAndDestination(source, destination);
		if (listOfAvailFlights.isEmpty()) {
			logger.info("No flights available for given location");
			return new ModelAndView("home", "noFlights", "No flights available for the given location");
		}
		return new ModelAndView("availableflights", "listOfAvailFlights", listOfAvailFlights);

	}

}
