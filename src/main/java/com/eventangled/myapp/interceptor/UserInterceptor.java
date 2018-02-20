package com.eventangled.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eventangled.myapp.pojo.User;

public class UserInterceptor extends HandlerInterceptorAdapter {

	String errorPage;

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		User u = (User) session.getAttribute("user");
		System.out.println(request.getRequestURI());

		System.out.println("----------------------");

		if (session.getAttribute("user") == null) {
			if ((request.getRequestURI().contains("admin")) || (request.getRequestURI().contains("ticket"))
					|| (request.getRequestURI().contains("/event/createevent"))
					|| (request.getRequestURI().contains("/event/createtickets"))
					|| (request.getRequestURI().contains("/organizerprofile"))
					|| (request.getRequestURI().contains("/userprofile"))) {
				System.out.println("in interceptor");
				System.out.println("1 -false");
				response.sendRedirect(errorPage);
				return false;
			} else {
				System.out.println("Authorized");
				return true;
			}

		}

		else if (u != null) {
			System.out.println("in interceptor");
			if (u.getEmail().equals("admin@admin.com")) {
				if ((request.getRequestURI().contains("admin")) || (request.getRequestURI().contains("logout.htm"))
						|| (request.getRequestURI().contains("/event/browseevents"))
						|| (request.getRequestURI().contains("/user/"))
						|| (request.getRequestURI().contains("/event/searchevents"))) {

					System.out.println("Authorized");
					return true;
				} else {
					System.out.println("NOT VALID!!");
					response.sendRedirect(errorPage);
					System.out.println("1 -false");
					return false;
				}
			} else if (!u.getEmail().equals("admin@admin.com")) {
				if (!request.getRequestURI().contains("admin")) {
					if ((request.getRequestURI().contains("ticket")) || (request.getRequestURI().contains("/user/"))
							|| (request.getRequestURI().contains("organizerprofile"))
							|| (request.getRequestURI().contains("userprofile"))
							|| (request.getRequestURI().contains("/event/createevent"))
							|| (request.getRequestURI().contains("/event"))
							|| (request.getRequestURI().contains("logout.htm"))) {

						System.out.println("Authorized");
						return true;

					} else {
						System.out.println("NOT VALID!!");
						response.sendRedirect(errorPage);
						System.out.println("1 -false");
						return false;
					}
				} else {
					System.out.println("NOT VALID!!");
					response.sendRedirect(errorPage);
					System.out.println("1 -false");
					return false;
				}
			}
			else{
				System.out.println("NOT VALID!!");
				response.sendRedirect(errorPage);
				System.out.println("1 -false");
				return false;
			}

		} else {
			System.out.println("NOT VALID!!");
			response.sendRedirect(errorPage);
			System.out.println("1 -false");
			return false;
		}

	}
	
	/*
	 * if (u != null) { System.out.println("in interceptor"); if
	 * (((u.getEmail().equals("admin@admin.com")) &&
	 * ((request.getRequestURI().contains("admin")) ||
	 * (request.getRequestURI().contains("logout.htm")) ||
	 * (request.getRequestURI().contains("/event/browseevents")) ||
	 * (request.getRequestURI().contains("user")) ||
	 * (request.getRequestURI().contains("/event/searchevents")))) ||
	 * ((!u.getEmail().equals("admin@admin.com")) &&
	 * (!request.getRequestURI().contains("admin")) &&
	 * ((request.getRequestURI().contains("ticket")) ||
	 * (request.getRequestURI().contains("user")) ||
	 * (request.getRequestURI().contains("organizerprofile")) ||
	 * (request.getRequestURI().contains("userprofile")) ||
	 * (request.getRequestURI().contains("/event/createevent")) ||
	 * (request.getRequestURI().contains("/event")) ||
	 * (request.getRequestURI().contains("logout.htm")))))
	 * 
	 * { System.out.println("in interceptor"); return true; } }
	 */


}
