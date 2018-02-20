<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile</title>
</head>
<body>
<%@include file="/menu2.jsp" %>
 <div style="margin-top: 5%;" >
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<h2 class="text-center" >My Account Information</h2>

	<form:form action="${contextPath}/userprofile/updateUserProfile" commandName="userprofile" method="post">

		<table class="table container" style="width: 50%;">
		<tr>
				<td>First Name</td>
				<td>${sessionScope.user.firstName}</td>
			</tr>
			
			<tr>
				<td>Last Name</td>
				<td>${sessionScope.user.lastName}</td>
			</tr>
			
			<tr>
				<td>Email</td>
				<td>${sessionScope.user.email}</td>
			</tr>
			
			<tr>
				<td>Contact</td>
				<td><form:input path="contact" size="10" type="text" required="required" class="form-control"/> <font color="red"><form:errors
							path="contact" /></font></td>
			</tr>
			
			<tr>
				<td colspan= "2">Billing Address</td>
			</tr>
			<tr>
			<td>Address Line 1</td>
				<td><form:input path="addressLine1" size="10" type="text" required="required" class="form-control" /> <font color="red"><form:errors
							path="addressLine1" /></font></td>
			</tr>
			<tr>
			<td>Address Line 2</td>
				<td><form:input path="addressLine2" size="10" type="text" class="form-control" /> <font color="red"><form:errors
							path="addressLine2" /></font></td>
			</tr>
			<tr>
			<td>City</td>
				<td><form:input path="city" size="30" type="text" required="required"  class="form-control"/> <font color="red"><form:errors
							path="city" /></font></td>
			</tr>
			<tr>
			<td>State</td>
				<td><form:input path="state" size="30" type="text" required="required" class="form-control" /> <font color="red"><form:errors
							path="state" /></font></td>
			</tr>
			<tr>
			<td>Country</td>
				<td><form:input path="country" size="30" type="text" required="required" class="form-control"/> <font color="red"><form:errors
							path="country" /></font></td>
			</tr>
			<tr>
			<td>Zip</td>
				<td><form:input path="zip" size="30" type="text" required="required" class="form-control"/> <font color="red"><form:errors
							path="zip" /></font></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save Profile" class="form-control btn btn-primary" /></td>
			</tr>
		</table>

	</form:form>
	
</div>

</body>
</html>