<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose Tickets</title>
</head>
<body>
<%@include file="/menu2.jsp"%>
	<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />

		<h2>Choose Tickets</h2>
		
		<font color="red"><p>${err}</p></font>
		   
		<form:form method="post" action="${contextPath}/ticket/bookticket">
		<table class="table" >
            <thead>
            <th>Ticket Title</th>
            <th>Free or Paid</th>
            <th>Price</th>
            <th>Ticket Count</th>
            </thead>
            <tbody>
           
   		 <c:forEach items="${event.ticketTypes}" var="ticketType" varStatus="status">
   		  <tr>
   		  <td>
   		  	  ${ ticketType.ticketTitle}
   		  </td>
            <td>
            ${ticketType.freeOrPaid} 
             </td>
             <td>
               ${ticketType.price}
               </td>
                    <td>
                    <select name="count${status.count}" class="counter"> 
                     <c:forEach begin="0" end="10" step="1" var="num">
                     <option value="${num}">${num}</option>
                     </c:forEach>
                    </select>
                    <input name="ticketTypeId${status.count}" type="hidden" value="${ticketType.ticketTypeId}"/>
                     <input class="availableTickets" type="hidden" value="${ticketType.availableNumOfTickets}"/>
                    </td>
        </tr>
    	</c:forEach>
    	</tbody>
    	</table>
    	 <input type="hidden" name="eventId" value="${event.eventId}" >
    	  <input type="hidden" name="numberOfTickets" value="${event.numberOfTickets}" >
    	 <input type="submit" id ="proceed" value="Proceed to Book Tickets >>" class="form-control btn btn-primary" >
		</form:form>
	</div>

<script type="text/javascript">
		$(document).ready(function(){
			$("#proceed").click(function(){
				//alert("Inside proceed");
				 var allValid = false;	
				
			$(".counter").each(function(){
					if($(this).val() != 0 )
					{
						allValid=true;
						return true;
					}	
			});

		     //return the result
		     
		     if (allValid===false){
		    	 alert("Please choose ticket count!");
		    	 return false;
		     }
		     
		   	return allValid;
			
			
			});
		});
		
		</script> 

</body>
</html>