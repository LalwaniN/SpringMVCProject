package com.eventangled.myapp.exceptions;

public class TicketException extends Exception {
	
	public TicketException(String message)
	{
		super("TicketException-"+message);
	}
	
	public TicketException(String message, Throwable cause)
	{
		super("TicketException-"+message,cause);
	}	
	

}
