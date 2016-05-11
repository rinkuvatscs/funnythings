<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.interview.pojo.UserDetail"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Search Result</title>
</head>
<body>
	<h1>Hi ${fname} , your files are :</h1>
	<table border="1">
		<tr>
			<td>First Name</td>
			<td>Last Name</td>
			<td>Email</td>
			<td>Location</td>
			<td>File Location</td>
		</tr>
		<%
			Object obj = request.getAttribute("fileLoc");
			if (obj instanceof List<?>) {
				List<UserDetail> fileLocation = (ArrayList<UserDetail>) obj;
				for (int i = 0; i < fileLocation.size(); i++) {
		%>
		<tr>
			<td>
				<%
					out.print(fileLocation.get(i).getFirstName());
				%>
			</td>
			<td>
				<%
					out.print(fileLocation.get(i).getLastName());
				%>
			</td>
			<td>
				<%
					out.print(fileLocation.get(i).getEmailAddress());
				%>
			</td>
			<td>
				<%
					out.print(fileLocation.get(i).getLocation());
				%>
			</td>
			<td><a href="<%=fileLocation.get(i).getFileLocation()%>" target="_BLANK"><%=fileLocation.get(i).getFileLocation()%></a></td>
		</tr>
		<%
			}
			}
		%>

	</table>

</body>
</html>