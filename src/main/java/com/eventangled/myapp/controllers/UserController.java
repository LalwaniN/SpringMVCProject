package com.eventangled.myapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventangled.myapp.dao.EventDao;
import com.eventangled.myapp.dao.UserDao;
import com.eventangled.myapp.exceptions.UserException;
import com.eventangled.myapp.pojo.Event;
import com.eventangled.myapp.pojo.User;
import com.eventangled.myapp.validators.UserValidator;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	

	@Autowired
	EventDao eventDao;
	
	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;
	
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	
	//redirecting user to Sign up page
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");

		return new ModelAndView("SignUp", "user", new User());

	}
	
	// Saving user details to database and sending confirmation email
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult result) throws Exception {

		validator.validate(user, result);

		if (result.hasErrors()) {
			return new ModelAndView("SignUp", "user", user);
		}

		try {

			System.out.print("registerNewUser");

			User u = userDao.register(user);
			System.out.println("userId:"+u.getUserId());
			
			request.getSession().setAttribute("user", u);
			try
            {
            Email email = new SimpleEmail();
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
            email.setHostName("smtp.gmail.com");//if a server is capable of sending email.
            email.setSSL(true);//setSSLOnConnect(true);
            email.setFrom("admin@eventory.com");
            email.setSubject("Registeration Successfull!!!");
            email.setMsg("You have successfully signed up with the Eventtory! \n Continue exploring events around you!");
            email.addTo(u.getEmail());
            email.setTLS(true);//startTLS.enable.true
            email.send();
            System.out.println("EMAIL SENT ");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return new ModelAndView("error", "errorMessage", "error while sending signup email");
        }
			List<Event> events = eventDao.getApprovedEvents();
			List<String> eventTypes = eventDao.getEventCategories();
			request.getSession().setAttribute("eventTypes", eventTypes);
		return new ModelAndView("browse-events", "events", events);
		

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}
	
	// redirecting user to login page
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	protected String login() throws Exception {
		System.out.print("loginUser");
		return "Login";
		
	}
	
	//checking if user logging in is registered or not and redirecting to error page if not;
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected ModelAndView loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		//if(!username.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$") || !password.matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,15})")){
		//	return new ModelAndView("error", "errorMessage", "Invalid input");
		//}
		
		try {

			System.out.print("inside login post");

			User u = userDao.get(request.getParameter("username"), request.getParameter("password"));
			
			if(u == null){
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return new ModelAndView("error", "errorMessage", "UserName/Password does not exist");
			}
			
			session.setAttribute("user", u);
			
			List<Event> approvedEvents = eventDao.getApprovedEvents();
			List<Event> allEvents = eventDao.getAllEvents();
			List<String> eventTypes = eventDao.getEventCategories();
			request.getSession().setAttribute("eventTypes", eventTypes);
			
			if (u.getUserId()==1){
				return new ModelAndView("admin-home", "events", allEvents);
			}
			else{
				return new ModelAndView("browse-events", "events", approvedEvents);
			}
			
			

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView("error", "errorMessage", "error while login");
		}
		
	}
		
	//error page mapping interceptor
		@RequestMapping(value = "/user/error", method = RequestMethod.GET)
		protected String error(HttpServletRequest request) throws Exception {
		
			
			User user = (User) request.getSession().getAttribute("user");
			if (user==null){
				request.setAttribute("errorMessage", "Please login first!");
			}
			else{
				request.setAttribute("errorMessage", "You are not authorized to access this page!");
			}
			
			return "error";
			
		}

	
}
