package com.eventangled.myapp.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ticket_type_table")
public class TicketType {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name = "ticketTypeId", unique=true, nullable = false)
private int ticketTypeId;

@Column(name="ticketTitle")
private String ticketTitle;

@Column(name="freeOrPaid")
private String freeOrPaid;

@Column(name="price")
private double price;

@Column(name="fee")
private double fee;

@Column(name="totalPrice")
private double totalPrice;

@Column(name="commission")
private double commission;

@Column(name="revenueForTicketType")
private double revenuePerTicketType;

@Column(name="ticketNumForTicketType")
private int ticketNumForTicketType;

@Column(name="availableNumOfTickets")
private int availableNumOfTickets;

@Column(name="soldNumOfTickets")
private int soldNumOfTickets;

@ManyToOne
@JoinColumn(name="eventId")
private Event event;

@OneToMany( mappedBy = "ticketType")
private Set<Ticket> tickets = new HashSet<Ticket>();

public double getFee() {
	return fee;
}

public void setFee(double fee) {
	this.fee = fee;
}

public Set<Ticket> getTickets() {
	return tickets;
}

public void setTickets(Set<Ticket> tickets) {
	this.tickets = tickets;
}

public int getTicketTypeId() {
	return ticketTypeId;
}

public void setTicketTypeId(int ticketTypeId) {
	this.ticketTypeId = ticketTypeId;
}

public String getTicketTitle() {
	return ticketTitle;
}

public void setTicketTitle(String ticketTitle) {
	this.ticketTitle = ticketTitle;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public double getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
}

public double getCommission() {
	return commission;
}

public void setCommission(double commission) {
	this.commission = commission;
}

public Event getEvent() {
	return event;
}

public void setEvent(Event event) {
	this.event = event;
}

public String getFreeOrPaid() {
	return freeOrPaid;
}

public void setFreeOrPaid(String freeOrPaid) {
	this.freeOrPaid = freeOrPaid;
}

public int getTicketNumForTicketType() {
	return ticketNumForTicketType;
}

public void setTicketNumForTicketType(int ticketNumForTicketType) {
	this.ticketNumForTicketType = ticketNumForTicketType;
}

public int getAvailableNumOfTickets() {
	return availableNumOfTickets;
}

public void setAvailableNumOfTickets(int availableNumOfTickets) {
	this.availableNumOfTickets = availableNumOfTickets;
}

public int getSoldNumOfTickets() {
	return soldNumOfTickets;
}

public void setSoldNumOfTickets(int soldNumOfTickets) {
	this.soldNumOfTickets = soldNumOfTickets;
}

public double getRevenuePerTicketType() {
	return revenuePerTicketType;
}

public void setRevenuePerTicketType(double revenuePerTicketType) {
	this.revenuePerTicketType = revenuePerTicketType;
}




}
