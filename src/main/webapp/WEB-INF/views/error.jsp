<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error page</title>
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
<div style="margin-top: 5%;">
<h1>Error Page</h1>
<p>${errorMessage}</p>

</div>
</body>
</html>