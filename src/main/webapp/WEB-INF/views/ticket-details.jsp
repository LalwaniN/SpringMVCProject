<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ticket Details</title>
</head>
<body>

<%@include file="/menu2.jsp"%>
	<div style="margin-top: 5%;" class="container">
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<form action="${contextPath}/ticket/ticketlastStep" method="post">
		<h1> ${sessionScope.event.eventTitle}</h1>
		
		<h2>Order Summary</h2>
		<table class="table" >
            <thead>
            <th>Ticket Title</th>
            <th>Price</th>
            <th>Fee</th>
            <th>Quantity</th>
            <th>Total</th>
            </thead>
            <tbody>
           
   		 <c:forEach items="${typeCount}" var="item">
   		  <tr>
   		  <td>
   		  	  <span>${item.ticketType.ticketTitle}</span>
   		  </td>
            <td>
   		  	  <span>${item.ticketType.price}</span>
   		  </td>
   		  <td>
   		  	  <span>${item.ticketType.fee }</span>
   		  </td>
   		  <td>
   		  	  <span>${item.count}</span>
   		  </td>
   		  <td>
   		  	  <span>${item.total}</span>
   		  </td>

        </tr>
        </c:forEach>
        <tr><td></td><td></td><td></td><td></td><td>${sessionScope.orderTotal}</td></tr>
    	
    	</tbody>
    	</table>
    	
    	<h2>Registration Information</h2>
    	<table class="table" style="width: 50%;">
    	<tr><td colspan="2"><h3>Ticket Buyer</h3></td><td></td></tr>
    	 
		<tr>
				<td>First Name</td>
				<td>${sessionScope.user.firstName}</td>
				<td></td>
			</tr>
			
			<tr>
				<td>Last Name</td>
				<td>${sessionScope.user.lastName}</td>
				<td></td>
			</tr>
			
			<tr>
				<td>Email</td>
				<td>${sessionScope.user.email}</td>
				<td></td>
			</tr>
			
			
			<tr><td colspan="2"><h3>Payment</h3></td><td></td></tr>
			<tr>
				<td>Card<font color="red">*</font></td>
				<td><select name="cardType" class="form-control" >
				<option value="select">Select card option</option>
				<option value="MasterCard" ${"MasterCard" == user.userProfile.cardType ? 'selected="selected"' : ''}>MasterCard</option>
				<option value="Visa" ${"Visa" == user.userProfile.cardType ? 'selected="selected"' : ''}>Visa</option>
				<option value="American Express" ${"American Express" == user.userProfile.cardType ? 'selected="selected"' : ''}>American Express</option>
				<option value="Discover" ${"Discover" == user.userProfile.cardType ? 'selected="selected"' : ''}>Discover</option>
				</select>
				</td><td></td>
				
			</tr>
			
			<tr>
				<td>Card Number<font color="red">*</font></td>
				<td><input name="cardNumber" class="form-control" type="number" value="${user.userProfile.cardNumber}" required="required" pattern="[0-9]+" maxlength="16"></td>
			<td></td>
			</tr>
			
			<tr>
				<td>Expiration Date<font color="red">*</font></td>
				<td>
				<select name="month" class="form-control">
				<option value="">Month</option>
				<option value="Jan" ${"Jan" == user.userProfile.month ? 'selected="selected"' : ''}>Jan</option>
				<option value="Feb" ${"Feb" == user.userProfile.month ? 'selected="selected"' : ''}>Feb</option>
				<option value="March" ${"March" == user.userProfile.month ? 'selected="selected"' : ''}>March</option>
				<option value="April" ${"April" == user.userProfile.month ? 'selected="selected"' : ''}>April</option>
				<option value="May" ${"May" == user.userProfile.month ? 'selected="selected"' : ''}>May</option>
				<option value="June" ${"June" == user.userProfile.month ? 'selected="selected"' : ''}>June</option>
				<option value="July" ${"July" == user.userProfile.month ? 'selected="selected"' : ''}>July</option>
				<option value="Aug" ${"Aug" == user.userProfile.month ? 'selected="selected"' : ''}>Aug</option>
				<option value="Sept" ${"Sept" == user.userProfile.month ? 'selected="selected"' : ''}>Sept</option>
				<option value="Oct" ${"Oct" == user.userProfile.month ? 'selected="selected"' : ''}>Oct</option>
				<option value="Nov" ${"Nov" == user.userProfile.month ? 'selected="selected"' : ''}>Nov</option>
				<option value="Dec" ${"Dec" == user.userProfile.month ? 'selected="selected"' : ''}>Dec</option>
				</select>
				<select name="year" class="form-control" >
				<option value="0">Year</option>
				<c:forEach begin="2017" end="2037" step="1" var="status">
				<option value="${status}" ${status == user.userProfile.year ? 'selected="selected"' : ''}>${status}</option>
				</c:forEach>
				</select>
				<input name="cvv" type="text" class="form-control"  value="${user.userProfile.cvv}" maxlength="3" required="required" pattern='[0-9]{3}'/>
				</td><td><font color="red"><span id="msgmonth"></span></font></td>
			</tr>
			<tr>
				<td colspan="2">Billing Information</td><td></td>
				
			</tr>
			<tr>
				<td>Address Line 1<font color="red">*</font></td>
				<td><input name="addressLine1" class="form-control" type="text" value="${user.userProfile.addressLine1}" required="required" pattern="([0-9a-zA-Z ]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			</tr>
			<tr>
				<td>Address Line 2</td>
				<td><input name="addressLine2" class="form-control" type="text" value="${user.userProfile.addressLine2}"  pattern="([0-9a-zA-Z\s]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			<tr>
				<td>City<font color="red">*</font></td>
				<td><input name="city" class="form-control" type="text" value="${user.userProfile.city}" required="required" pattern="([a-zA-Z\s]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			<tr>
				<td>State<font color="red">*</font></td>
				<td><input name="state" class="form-control" type="text" value="${user.userProfile.state}" required="required" pattern="([a-zA-Z\s]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			<tr>
				<td>Country<font color="red">*</font></td>
				<td><input name="country" class="form-control" type="text" value="${user.userProfile.country}" required="required" pattern="([a-zA-Z\s]+)" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			<tr>
				<td>ZipCode<font color="red">*</font></td>
				<td><input name="zip" class="form-control" type="text" value="${user.userProfile.zip}" required="required" pattern="^[0-9]{5}(?:-[0-9]{4})?$" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			
			<tr>
				<td colspan="2">Attendees Ticket Details</td><td></td>
			</tr>
			<c:forEach items="${typeCount}" var="item" >
			<c:forEach begin="1" end="${item.count}" step="1" varStatus="status" >
			<tr>
				<td colspan="2">Ticket &nbsp ${status.count} &nbsp ${item.ticketType.ticketTitle}</td><td></td>
			</tr>
			<tr>
				<td>First Name <font color="red">*</font></td>
				<td><input name="attendeeFirstName${status.count}ttId${item.ticketType.ticketTypeId}" class="form-control name" type="text" required="required" pattern="[a-zA-z]+([ '-][a-zA-Z]+)*" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td>
			</tr>
			<tr>
				<td>Last Name <font color="red">*</font></td>
				<td><input name="attendeeLastName${status.count}ttId${item.ticketType.ticketTypeId}" class="form-control name" type="text" required="required" pattern="[a-zA-z]+([ '-][a-zA-Z]+)*" oninvalid="setCustomValidity('Invalid Charactes not accepted')" onchange="try{setCustomValidity('')}catch(e){}"></td>
			<td></td></tr>
			</c:forEach>
			</c:forEach>
			
			</table>
			<tr><td></td><td><input type="submit" class="form-control btn btn-primary" value="Book" id="submit"></td><td></td></tr>
			
			
	</form>
    </div>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#submit").click(function(){
			var month = $('select[name=month]').val();
			var year= $('select[name=year]').val();
			var cvv = $('input[name=cvv]').val();
			
			//alert(cvv);
			if(month != "" && year!=0)
			{
			return true;
			}
			else{
				$("#msgmonth").html("Please give valid details");
				return false
			}
			});
			
			
			
			});
		
		</script>   


</body>
</html>