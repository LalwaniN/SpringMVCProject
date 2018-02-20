package com.eventangled.myapp.exceptions;

public class EventException extends Exception{


	public EventException(String message)
	{
		super("EventException-"+message);
	}
	
	public EventException(String message, Throwable cause)
	{
		super("EventException-"+message,cause);
	}	
	
}
