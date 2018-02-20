<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home</title>
</head>
<body>

<%@include file="/menuadmin.jsp"%>
	<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
		<h2>All events</h2>
		<table class="table table-bordered" >
           <thead>
           <th colspan="2">Event Details</th>
           <th >Organizer Details</th>
            <th >Commision Earned</th>
			 <th >Event Status</th>
           </thead>
   		 <c:forEach items="${events}" var="event">
   		  <tr>
   		  <td>
   		  	  <img height="150" width="150" src="${contextPath}/resources/eventimages/${event.eventPhotoFileName}" /><br>
   		  	  
   		  	  <span>
   		  	  Ticket Price : &nbsp
   		  	   <c:forEach items="${event.ticketTypes}" var="ticket">
   		  	  $ ${ticket.price},
   		  	   </c:forEach>
   		  	  </span>
   		  	  
   		  </td>
           <td>
          <b>${event.eventTitle }</b> <br>
          ${event.startDate } &nbsp &nbsp ${event.startTime} <br>
          ${event.eventAddressLine1 },${event.eventAddressLine2 },${event.eventCity },${event.eventState },${event.eventCountry }-${event.eventZipCode } <br>
          #${event.eventCategory.eventType}      
           </td>
            <td> 
            ${event.organizerProfile.user.firstName} &nbsp &nbsp  ${event.organizerProfile.user.lastName} <br>
            ${event.organizerProfile.organizerDescription}
            </td>
            <td>$ ${event.totalCommission}</td>
            <td > 
            ${event.approvalStatus} 
            </td>

        </tr>
    	</c:forEach>
    	
    	</table>

	</div>


</body>
</html>