<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.interview.pojo.Country"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Country</title>
</head>
<body>
	<center>
		<h1>Add Country Service</h1>
	</center>
	<form action="/interviewservice/UiController/addCountryAction"
		method="post">
		<table border="1">
			<tr>
				<td><label>List of countries already added : </label></td>
				<td><select name="countryList">
						<c:if test="${!empty countryName}">
							<c:forEach items="${countryName}" var="cntryName">
								<option><c:out value="${cntryName.getCountryName()}" /></option>
							</c:forEach>
						</c:if>
				</select></td>
			</tr>
			<tr>
				<td><label>Enter Country Name</label></td>
				<td><input type="text" name="countryName"></td>
			</tr>
			<tr>
				<td><label>Click to submit</label></td>
				<td><center>
						<input type="submit" name="submit" value="Submit">
					</center></td>
			</tr>
		</table>
	</form>
	${msg}
</body>
</html>