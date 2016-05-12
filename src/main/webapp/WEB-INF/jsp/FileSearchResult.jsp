<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.interview.pojo.UserDetail"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Search Result</title>
</head>
<body>
	<h1>
		Hi,
		<c:out value="${fileLoc.get(0).getFirstName()}"></c:out>
		your files are :
	</h1>
	<table border="1">
		<tr>
			<td>First Name</td>
			<td>Last Name</td>
			<td><center>Email</center></td>
			<td>Location</td>
			<td><center>File Location</center></td>
		</tr>
		<c:forEach items="${fileLoc}" var="userDetail">
			<tr>
				<td><c:out value="${userDetail.getFirstName()}" /></td>
				<td><c:out value="${userDetail.getLastName()}" /></td>
				<td><c:out value="${userDetail.getEmailAddress()}" /></td>
				<td><c:out value="${userDetail.getLocation()}" /></td>
				<td><a href="<c:out value="${userDetail.getFileLocation()}" />">File</a></td>
			</tr>
		</c:forEach>
	</table>
	<!-- <video id="sampleMovie" width="640" height="360" preload controls>
	<source src="file:///D:/interviewService/Wildlife.wmv" /> <source></video> -->

	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
		codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
		width="400" height="400">
		<param name="movie" value="flashplayer.swf">
		<param name="quality" value="high">
		<param name="LOOP" value="false">
		<embed src=file:///D:/interviewService/Wildlife.wmv width="350"
			height="250" loop="false" quality="high"
			pluginspage="http://www.macromedia.com/go/getflashplayer"
			type="application/x-shockwave-flash"></embed>
	</object>
</body>
</html>