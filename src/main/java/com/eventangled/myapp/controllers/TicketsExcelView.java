package com.eventangled.myapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.eventangled.myapp.dao.TicketDao;
import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.Ticket;
import com.eventangled.myapp.pojo.TicketType;
import com.eventangled.myapp.pojo.User;
import java.text.DateFormat;

public class TicketsExcelView extends AbstractExcelView{

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		 DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		int eventId =Integer.parseInt(req.getParameter("eventId"));
		TicketDao ticketDao = new TicketDao();
		Event event = ticketDao.getEvent(eventId);
		
		Set<TicketType> ticketTypes =event.getTicketTypes();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		//get all tickets with tickettypes as above
		
		for (TicketType ticketType : ticketTypes){
			for (Ticket ticket : ticketType.getTickets()){
				tickets.add(ticket);
			}
		}
		
		HashMap<Integer,String> map = new HashMap<Integer,String>();
		
		for (TicketType ticketType: ticketTypes){
			map.put(ticketType.getTicketTypeId(), ticketType.getTicketTitle());
		}
		
		
		HSSFSheet sheet = workbook.createSheet("TicketList");
		sheet.setDefaultColumnWidth(12);
		List<String> headers = new ArrayList<String>();
		headers.add("Ticket ID");
		headers.add("First Name");
		headers.add("Last Name");
		headers.add("Booking Date");
		headers.add("Ticket Type");
		headers.add("Ticket Buyer name");
		headers.add("Ticket Buyer email");
		
		int currentColumn=0;
		
		  HSSFCellStyle headerStyle = workbook.createCellStyle();
	        HSSFFont headerFont = workbook.createFont();
	        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(headerFont); 
	        //POPULATE HEADER COLUMNS
	        HSSFRow headerRow = sheet.createRow(0);
	        for(String header:headers){
	            HSSFRichTextString text = new HSSFRichTextString(header);
	            HSSFCell cell = headerRow.createCell(currentColumn); 
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(text);            
	            currentColumn++;
	        }
	
		
		int row =1;
		for (Ticket ticket : tickets){
			getCell(sheet, row, 0).setCellValue(ticket.getTicketId());
			getCell(sheet, row, 1).setCellValue(ticket.getAttendeeFirstName());
			getCell(sheet, row, 2).setCellValue(ticket.getAttendeeLastName());
			getCell(sheet, row, 3).setCellValue(DATE_FORMAT.format(ticket.getBookingDate()));
			getCell(sheet, row, 4).setCellValue(map.get(ticket.getTicketType().getTicketTypeId()));
			getCell(sheet, row, 5).setCellValue(ticket.getUser().getFirstName()+" " +ticket.getUser().getLastName());
			getCell(sheet, row, 6).setCellValue(ticket.getUser().getEmail());
			row++;
	
		}
	}
	}
		

