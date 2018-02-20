<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>
 <%@include file="/menu.jsp" %>
 <div style="margin-top: 5%;" class="container" >
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

	<h2>Sign Up</h2>

	<form:form action="${contextPath}/user/register" commandName="user"
		method="post">

		<table class="table" style="width:50%; ">
		<tr>
				<td>First name</td>
				<td><form:input path="firstName" size="30" type="text" class="form-control"/> <font color="red"><form:errors
							path="firstName" /></font></td>
			</tr>
			<tr>
				<td>Last name</td>
				<td><form:input path="lastName" size="30" type="text" class="form-control"/> <font color="red"><form:errors
							path="lastName" /></font></td>
			</tr>
			<tr>
				<td>Email Id:</td>
				<td><form:input path="email" size="30"
						type="email"  class="form-control"/> <font color="red"><form:errors
							path="email" /></font></td>
			</tr>
			
			<tr>
				<td>Password:</td>
				<td><form:password path="password" size="30"
						 class="form-control" /> <font color="red"><form:errors
							path="password" /></font></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Register User" class="form-control btn btn-primary"/></td>
			</tr>
		</table>

	</form:form>
</div>
</body>
</html>