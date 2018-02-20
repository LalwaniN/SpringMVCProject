package com.eventangled.myapp.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LogoutController {

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public void logout(HttpServletRequest req,HttpServletResponse res) {
		HttpSession session= req.getSession();
		session.invalidate();
		try {
			res.sendRedirect("index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
