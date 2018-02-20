package com.eventangled.myapp.pojo;

public class TicketTypeCount {
	TicketType ticketType;
	int count;
	double total;
	public double getTotal() {
		return total;
	}
	public TicketType getTicketType() {
		return ticketType;
	}
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count=count;
	}
	public void setTotal(double total) {
		this.total = total;
	}




}
