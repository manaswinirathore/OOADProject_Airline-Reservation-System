package com.airline.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.airline.entities.Flight;
import com.airline.entities.Ticket;

public class FlightDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flightNo;
	private String flightName;
	private int economy;
	private int business;
	private int firstclass;
	private String source;
	private String destination;
	private String arrivalTime;
	private String depatureTime;
	private String date;
	private List<Ticket> listOfTickets = new ArrayList<>();

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public int getEconomy() {
		return economy;		//2s economy, sl business, ac first class
	}

	public void setEconomy(int economy) {
		this.economy = economy;
	}

	public int getBusiness() {
		return business;
	}

	public void setBusiness(int business) {
		this.business = business;
	}

	public int getFirstClass() {
		return firstclass;
	}

	public void setFirstClass(int firstclass) {
		this.firstclass = firstclass;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepatureTime() {
		return depatureTime;
	}

	public void setDepatureTime(String depatureTime) {
		this.depatureTime = depatureTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Ticket> getListOfTickets() {
		return listOfTickets;
	}

	public void setListOfTickets(List<Ticket> listOfTickets) {
		this.listOfTickets = listOfTickets;
	}

	@Override
	public String toString() {
		return "Flight [flightNo=" + flightNo + ", flightName=" + flightName + ", economy=" + economy + ", business=" + business
				+ ", firstclass=" + firstclass + ", source=" + source + ", destination=" + destination + ", arrivalTime="
				+ arrivalTime + ", depatureTime=" + depatureTime + ", date=" + date + "]";
	}
	public Flight convertToEntity() {
		Flight flight=new Flight();
		flight.setFlightNo(this.flightNo);
		flight.setFlightName(this.flightName);
		flight.setSource(this.source);
		flight.setDestination(this.destination);
		flight.setEconomy(this.economy);
		flight.setFirstClass(this.firstclass);
		flight.setBusiness(this.business);
		flight.setArrivalTime(this.arrivalTime);
		flight.setDepatureTime(this.depatureTime);
		flight.setListOfTickets(this.listOfTickets);
		return flight;
		
	}
}
