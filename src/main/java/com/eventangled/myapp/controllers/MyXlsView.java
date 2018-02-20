package com.eventangled.myapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.pojo.User;

public class MyXlsView extends AbstractExcelView {
	
	//@Autowired
	//UserDao userDao;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		
		UserDao userDao = new UserDao();
		List<User> users = userDao.getAllUsers();
		
		HSSFSheet sheet = workbook.createSheet("UserList");
		sheet.setDefaultColumnWidth(12);
		List<String> headers = new ArrayList<String>();
		headers.add("User ID");
		headers.add("First Name");
		headers.add("Last Name");
		headers.add("Email");
		headers.add("IsOrganizer Flag");
		headers.add("City");
		headers.add("Website url");
		headers.add("Organizer Description");
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
		for (User user : users){
			getCell(sheet, row, 0).setCellValue(user.getUserId());
			getCell(sheet, row, 1).setCellValue(user.getFirstName());
			getCell(sheet, row, 2).setCellValue(user.getLastName());
			getCell(sheet, row, 3).setCellValue(user.getEmail());
			getCell(sheet, row, 4).setCellValue(user.isOrganizerFlag());
			if (user.getUserProfile()!=null){
				getCell(sheet, row, 5).setCellValue(user.getUserProfile().getCity());
			}
			else{
				getCell(sheet, row, 5).setCellValue("NULL");
			}
			if(user.getOrganizerProfile()!=null){
				getCell(sheet, row, 6).setCellValue(user.getOrganizerProfile().getWebsiteUrl());
			}
			else{
				getCell(sheet, row, 6).setCellValue("NULL");
			}
			
			if(user.getOrganizerProfile()!=null){
				getCell(sheet, row, 7).setCellValue(user.getOrganizerProfile().getOrganizerDescription());
			}
			else{
				getCell(sheet, row, 7).setCellValue("NULL");
			}
			
			
			row++;
	
		}
		
		
		
	}
}
