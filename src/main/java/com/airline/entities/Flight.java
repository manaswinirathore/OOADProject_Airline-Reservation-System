package com.airline.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.airline.dto.FlightDTO;

@Entity
public class Flight implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
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
	@OneToMany(mappedBy = "flightId",fetch = FetchType.EAGER,orphanRemoval = true)
	private List<Ticket> listOfTickets=new ArrayList<>();

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
		return economy;
	}
	public void setEconomy(int seat2s) {
		this.economy = seat2s;
	}
	public int getBusiness() {
		return business;
	}
	public void setBusiness(int seatSL) {
		this.business = seatSL;
	}
	public int getFirstClass() {
		return firstclass;
	}
	public void setFirstClass(int seatAC) {
		this.firstclass = seatAC;
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
				+ arrivalTime + ", depatureTime=" + depatureTime + ", date=" + date + 
				"]";
	}
	public FlightDTO convertToDTO() {
		FlightDTO flight=new FlightDTO();
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
