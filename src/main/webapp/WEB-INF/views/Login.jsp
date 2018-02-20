<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body >
<%@include file="/menu.jsp" %>
<div style="margin-top: 5%;" class="container">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<h2 class="">Login</h2>
	<form action="${contextPath}/user/login" method="post">
	
		<table class="table" style="width:50%; ">
		<tr>
		    <td>User Name(Email):</td>
		    <td><input name="username" size="30" required="required" class="form-control" type="email" required="required" /></td>
		</tr>
		<tr><td></td>
		<td></td></tr>
		<tr>
		    <td>Password:</td>
		    <td><input type="password" name="password" size="30" required="required" class="form-control" /></td>
		    <!--  pattern="((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,15})"   oninvalid="setCustomValidity('Invalid password format')" onchange="try{setCustomValidity('')}catch(e){}"--> 
		</tr>
		<tr>
		<td></td>
		<td></td>
		</tr>
		<tr>
		    <td colspan="2"><input type="submit" value="Login" class="form-control btn btn-primary" /></td>
		</tr>
				
		</table>

	</form>
</div>
</body>
</html>