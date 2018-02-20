<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <link href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" rel="Stylesheet"></link>
<script src='https://cdn.rawgit.com/pguso/jquery-plugin-circliful/master/js/jquery.circliful.min.js'></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" ></script>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js">
    
</script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
  
<style type="text/css">
.navbar {
      font-family: Montserrat, sans-serif;
      margin-bottom: 0;
      background-color: #2d2d30;
      border: 0;
      font-size: 11px !important;
      letter-spacing: 4px;
      opacity: 0.9;
  }
  .navbar li a, .navbar .navbar-brand { 
      color: #d5d5d5 !important;
  }
  .navbar-nav li a:hover {
      color: #fff !important;
  }
  .navbar-nav li.active a {
      color: #fff !important;
      background-color: #29292c !important;
  }
  .navbar-default .navbar-toggle {
      border-color: transparent;
  }
  
  .open .dropdown-toggle {
      color: #fff;
      background-color: #555 !important;
  }
  .dropdown-menu li a {
      color: #000 !important;
  }
  .dropdown-menu li a:hover {
      background-color: red !important;
  }
  </style>
  
<title></title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="${contextPath}">Eventory</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="${contextPath}">HOME</a></li>
        <li><a href="${contextPath}/event/browseevents">BROWSE EVENTS</a></li>
        <li><a href="${contextPath}/event/createevent" >CREATE EVENT</a></li>
       <!-- <li><a href="${contextPath}/organizerprofile/updateOrganizerProfile" >ORGANIZER PROFILE</a></li>
        <li><a href="${contextPath}/userprofile/updateUserProfile">MY ACCOUNT</a></li>  -->
        <li class="dropdown">
        
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Hello ${sessionScope.user.firstName}
          <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${contextPath}/organizerprofile/updateOrganizerProfile" >ORGANIZER PROFILE</a></li>
            <li><a href="${contextPath}/userprofile/updateUserProfile">MY ACCOUNT</a></li>
            <li><a href="${contextPath}/organizerprofile/myevents">MY EVENTS</a></li> 
          </ul>
       
  			</li>
  			<li><a href="${contextPath}/logout.htm">LOGOUT</a></li>
      </ul>
        
        
        
       
        
     	
    
    
         
    </div>
  </div>
</nav>
</body>
</html>