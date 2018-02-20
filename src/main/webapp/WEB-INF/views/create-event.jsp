<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script> 
 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

 <script type="text/javascript">
$( function() {
    $( ".datepicker" ).datepicker({dateFormat: 'mm/dd/yy',minDate: 0});
    
 } );
</script>
<title>Create Event</title>

</head>
<body>
	<%@include file="/menu2.jsp"%>
	<div style="margin-top: 5%;">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<h2 class="text-center" >Create Event</h2>		

		<form:form action="${contextPath}/event/createevent" method="post"
			commandName="event" enctype="multipart/form-data"  id="basicExample">

			<table class="table container" style="width:50%">
				<tr>
					<td>Event Title<font color="red">*</font></td>
					<td><form:input path="eventTitle" size="30" type="text"
							required="required"  class="form-control"/> <font color="red"><form:errors
								path="eventTitle" /></font></td>
				</tr>
				<tr>
					<td>Event Category<font color="red">*</font></td>
					<td><form:select path="eventCategoryName"
							items="${sessionScope.eventTypes}" class="form-control"/></td>
				</tr>
				<tr>
					<td>Event Description<font color="red">*</font></td>
					<td><form:textarea path="eventDescription" rows="5" cols="30"
							 class="form-control" /> <font color="red"><form:errors
								path="eventDescription" /></font></td>
				</tr>
				<tr>
					<td>Starts<font color="red">*</font>
					<td><form:input path="startDate" size="30" type="date" required="required" placeholder="MM/dd/yyyy" class="form-control datepicker" /> 
							<form:input path="startTime" size="30" type="text" required="required" placeholder="hh:mm" class="form-control time start"/> 
							<font color="red"><form:errors path="startDate" /> <form:errors path="startTime" /></font>
							</td>
				</tr>
				<tr>
					<td>Ends<font color="red">*</font>
					<td><form:input path="endDate" size="30" type="date" required="required" placeholder="MM/dd/yyyy" class="form-control datepicker"/> 
					<form:input path="endTime" size="30" type="text" required="required" placeholder="hh:mm" class="form-control time end" /> 
					<font color="red"> <form:errors path="endDate" /> <form:errors path="endTime" /></font></td>
				</tr>

				<tr>
					<td>Event Location<font color="red">*</font>
					<td>
					<form:input path="eventAddressLine1" size="30" type="text" required="required" placeholder="Address Line 1" class="form-control" /> 							
					<form:input path="eventAddressLine2" size="30" type="text" placeholder="Address Line 2" class="form-control"/> 
					<form:input path="eventCity" size="30" type="text" required="required" placeholder="City" class="form-control"/> 
					<form:input path="eventState" size="30" type="text" required="required" placeholder="State" class="form-control"/> 
					<form:input path="eventCountry" size="30" type="text" required="required" placeholder="Country" class="form-control"/> 
					<form:input path="eventZipCode" size="30" type="text" required="required" placeholder="Zip" class="form-control" /> 
					
					<font color="red"> <form:errors path="eventAddressLine1" /> <form:errors
								path="eventCity" /> <form:errors path="eventState" /> <form:errors
								path="eventCountry" /> <form:errors path="eventZipCode" />
					</font></td>
				</tr>

				<tr>
					<td>Ticket<font color="red">*</font></td>
					<td><form:input path="numberOfTickets" size="3" type="number"
							required="required" class="form-control" /> <font color="red"><form:errors
								path="numberOfTickets" /></font></td>
				</tr>

				<tr>
					<td>Upload Photo<font color="red">*</font></td>
					<td><input type="file" name="eventPhoto" class="form-control" required="required"  value="${event.eventPhotoFileName}"/>
				</td>
				</tr>

				<tr>
					<td colspan="2"><input type="submit" value="Create Tickets>>" class="form-control btn btn-primary"/></td>
				</tr>
				
			</table>

		</form:form>
	</div>
	
</body>


</html>