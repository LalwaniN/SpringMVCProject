package com.eventangled.myapp.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ticket_table")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ticketId", unique=true, nullable = false)
	int ticketId;
	
	@Column(name = "attendeeFirstName")
	String attendeeFirstName;
	
	@Column(name = "attendeeLastName")
	String attendeeLastName;
	
	@Column(name = "bookingDate")
	Date bookingDate;
	
	@ManyToOne
	@JoinColumn(name="ticketBuyerId")
	User user;
	
	@ManyToOne
	@JoinColumn(name="ticketTypeId")
	TicketType ticketType;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getAttendeeFirstName() {
		return attendeeFirstName;
	}

	public void setAttendeeFirstName(String attendeeFirstName) {
		this.attendeeFirstName = attendeeFirstName;
	}

	public String getAttendeeLastName() {
		return attendeeLastName;
	}

	public void setAttendeeLastName(String attendeeLastName) {
		this.attendeeLastName = attendeeLastName;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	
	
	

}
