<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Organizer Profile</title>
</head>
<body>
<%@include file="/menu2.jsp" %>
 <div style="margin-top: 5%;" >
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h2 class="text-center" >Organizer Profile</h2>
	<form:form action="${contextPath}/organizerprofile/updateOrganizerProfile" commandName="organizerprofile" >

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
				<td>Description<font color="red">*</font></td>
				<td><form:textarea path="organizerDescription" rows="5" cols="30"  class="form-control" required="required"/> <font color="red"><form:errors
							path="organizerDescription" /></font></td>
			</tr>
			<tr>
			<td>Website URL</td>
				<td><form:input path="websiteUrl" size="30" type="text"  class="form-control"/> <font color="red"><form:errors
							path="websiteUrl" /></font></td>
			</tr>
			<tr>
			<td>Facebook Page</td>
				<td><form:input path="faceBookPage" size="30" type="text"  class="form-control"/> <font color="red"><form:errors
							path="faceBookPage" /></font></td>
			</tr>
			<tr>
			<td>Twitter</td>
				<td><form:input path="twitter" size="30" type="text" class="form-control"/> <font color="red"><form:errors
							path="twitter" /></font></td>
			</tr>
			<tr>
			<td>Instagram</td>
				<td><form:input path="instagram" size="30" type="text" class="form-control"/> <font color="red"><form:errors
							path="instagram" /></font></td>
			</tr>
			  <tr>
			<!--  <td>Upload Photo</td>
				<td><input type="file" name="organizerPhoto" class="form-control"/></td>-->
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save Profile" class="form-control btn btn-primary"/></td>
			</tr>
		</table>

	</form:form>
	

	
</div>
</body>
</html>