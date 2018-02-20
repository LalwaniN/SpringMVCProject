<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create event tickets</title>

</head>
<body>
<%@include file="/menu2.jsp"%>
	<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />

		<h2>Create Tickets</h2>
		   
		<form:form method="post" action="${contextPath}/event/createtickets">
		<table class="table" >
            <thead>
            <th>Ticket Title</th>
            <th>Free or Paid</th>
            <th>Ticket Count</th>
            <th>Price</th>
            </thead>
            <tbody>
           
   		 <c:forEach begin="1" end="${event.numberOfTickets}" step="1" varStatus="status">
   		  <tr>
   		  <td>
   		  	  <input class="form-control" name="ticketTypes[${status.index}].tickettitle" type="text" required= "required" pattern="([a-zA-Z ]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"/><font color="red">*</font>
   		  </td>
                    <td>
                    <select class="form-control" name="ticketTypes[${status.index}].freeOrPaid" >

                    	  <option value="paid">PAID </option>
                    	  <option value="free">FREE </option>
                    </select><font color="red">*</font>
                    </td>
                    <td> <input class="form-control" name="ticketTypes[${status.index}].ticketCount" type="number" required="required" pattern="([0-9]+)"/><font color="red">*</font></td>
                     <td> <input class="form-control price" name="ticketTypes[${status.index}].price" type="number" required="required"/><font color="red">*</font></td>
       
       
        </tr>
    	</c:forEach>
    	</tbody>
    	</table>
    	 <input type="hidden" name="eventId" value="${event.eventId}" >
    	  <input type="hidden" name="numberOfTickets" value="${event.numberOfTickets}" >
    	 <input type="submit" value="Add Tickets" class="form-control btn btn-primary" >
		</form:form>
	</div>

<script>
$(document).ready(function(){
	
$("select").on('change', function(){
	if ($(this).val()=="free"){
	
		$(this).parent().next().next().find('.price').attr("disabled", true).val('0.0'); 
	}
	if ($(this).val()=="paid"){
		$(this).parent().next().next().find('.price').attr("disabled", false).val('0.0'); 
	}
	
	});
});
</script>
</body>
</html>