<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booking Tickets</title>
</head>
<body>
<%@include file="/menu2.jsp"%>
	<div style="margin-top: 5%; border: 1px solid black; padding:50px" class="container panel" >
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		
		<div class="row">
  <div class="col-sm-8 " > <img height="300" width="600" src="${contextPath}/resources/eventimages/${event.eventPhotoFileName}" /></div>
  <div class="col-sm-4 ">
  	<h1>${event.eventTitle}<small>&nbspby:&nbsp ${event.organizerProfile.user.firstName} &nbsp  ${event.organizerProfile.user.lastName}</small></h1>
  	
  	<br>
  	<a href="${contextPath}/ticket/bookticket?eventId=${event.eventId}" class="form-control btn btn-primary">Ticket</a>
  </div>
  
  <div class="row">
  <div class="col-sm-8 ">
  <h4>DESCRIPTION</h4>
  <br>
 <p> ${event.eventDescription}</p>
  <br>
  
  #${event.eventCategory.eventType}
   </div>
  <div class="col-sm-4 " >
  	<h4>DATE AND TIME</h4>
  	${event.startDate} <br>
  	${event.startTime} &nbsp -&nbsp ${event.endTime} 
  	<br>
  	<br>
  	<h4>LOCATION</h4>
  	${event.eventAddressLine1 },${event.eventAddressLine2 }<br>
  	${event.eventCity },${event.eventState }<br>
  	${event.eventCountry }-${event.eventZipCode } <br>
  </div>
  </div>
</div>
	</div>
</body>
</html>