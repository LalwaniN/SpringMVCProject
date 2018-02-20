<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Events</title>
</head>
<body>
<%@include file="/menu2.jsp"%>
<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />

		<h2>All events</h2>
		<table class="table table-bordered" >
           <thead>
           <th colspan="2">Event Details</th>
           <th>Tickets Sold</th>
           <th>Revenue Earned</th>
           <th>Event Status</th>
            <th>Download Ticket List</th>
           <th>Cancel event</th>

           </thead>
   		 <c:forEach items="${events}" var="event">
   		  <tr>
   		  <td>
   		  	  <img height="150" width="150" src="${contextPath}/resources/eventimages/${event.eventPhotoFileName}"  /><br>
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
            <c:forEach items="${event.ticketTypes}" var="item">
            	${item.ticketTitle} = ${item.soldNumOfTickets} <br>
            </c:forEach>
            </td>
            <td> 
           $ ${event.totalRevenue} 
            </td>
            <td class="eventstatus"> 
            ${event.approvalStatus} 
            </td>
            <td>
             <a href="${contextPath}/organizerprofile/ticketsexcel?eventId=${event.eventId}" class="btn btn-primary">Get Tickets</a>
            </td>
             <td> 
           <a href="#" class="btn btn-primary cancel"  ${event.approvalStatus == "Cancelled" || event.approvalStatus == "Rejected"? 'disabled="disabled"' : ''}>Cancel Event</a>
           
            </td>
            <td><input type="hidden" class="eventID" value="${event.eventId}"></td>
            
        </tr>
    	</c:forEach>
    	
    	</table>

	</div>
<script>
    $(document).ready(function() {
        $(".cancel").click(function(event) {
           
            event.preventDefault();
            $spanelem = $(this);
            if ( $spanelem.hasClass('disabled')) return;
            //alert("inside ajax method");
            var eventID = $(this).parent().parent().find('.eventID').val();
           // alert(eventID);
            $.post("${pageContext.request.contextPath}/organizerprofile/myevents", {
            	eventID : eventID
            }).done(function(serverdata) {
            	//alert(serverdata);
               // var obj = jQuery.parseJSON(serverdata);
                //$spanelem.html("Cancelled");
              $spanelem.addClass('disabled');
              $spanelem.parent().parent().find(".eventstatus").html("Cancelled")
                alert('Event Cancelled and Refund initiated!')
               
            });
        });

    });
    </script>
</body>
</html>