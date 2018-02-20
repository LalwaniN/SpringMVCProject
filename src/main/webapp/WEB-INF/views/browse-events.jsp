<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Browse events</title>
</head>
<body>
<c:choose>
  <c:when test="${sessionScope.user == null}">
    <%@include file="/menu.jsp" %>
  </c:when>
  <c:otherwise>
   <%@include file="/menu2.jsp" %>
  </c:otherwise>
</c:choose>
	<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />

		<h2>All events</h2>
		
		<form id ="searchForm" class="form-inline" method="post"  action="${contextPath}/event/browseevents">
		<input id="keyword" type="text" class="form-control" name="keyword" value="${key}" placeholder="Search event..." pattern="^[A-Za-z0-9.!'& -]*$" autofocus  oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"> 
		<select id="cat" name="cat" class="form-control">
		<option value="all" ${item == eventTypeSelected ? 'selected="selected"' : ''}>ALL</option>
            <c:forEach items="${sessionScope.eventTypes}" var="item">
                <option value="${item}" ${item == eventTypeSelected ? 'selected="selected"' : ''}>${item}</option>
            </c:forEach>
        </select>
        <select id="freeOrPaid" class="form-control" name="freeOrPaid">
        <option value="all" ${"all" == freeOrPaid ? 'selected="selected"' : ''}>ALL</option>
        <option value="paid" ${"paid" == freeOrPaid ? 'selected="selected"' : ''}>PAID</option>
        <option value="free" ${"free" == freeOrPaid ? 'selected="selected"' : ''} >FREE</option>       
        </select>
		<button type="submit" id="search" class="btn btn-primary">Search</button>
		</form>
		<br>
		<p><b>${er} </b></p>
		<br>
		

		<table class="table table-bordered" >
		<thead>
           <th colspan="2">Event Details</th>
           <th >Organizer Details</th>
            <th ></th>
           </thead>
           <tbody>
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
            <td>
            <a href="${contextPath}/ticket/eventdetails?eventId=${event.eventId}" class="form-control btn btn-primary">Book Tickets</a>
            </td>

        </tr>
    	</c:forEach>
    	</tbody>
    	</table>

	</div>
		
		
 <script>
$(function() {

   $("#keyword").autocomplete({
       source: function (request, response) {
           $.getJSON("${pageContext.request.contextPath}/event/searchevents?cat="+$('#cat').val()+"&freeOrPaid="+$('#freeOrPaid').val(), {
               term: request.term
           }, response);
       }
   });
});
</script>		
</body>
</html>