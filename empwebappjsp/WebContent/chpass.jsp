<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<jsp:useBean id = "msg" class="java.lang.String" scope="request"/>
<body>
	
	<form action="./changepassword" method = "post"
		style="width: 200px; height: 400px; margin-top: 100px; margin-left: 600px">

		<h3>
			<a href="./home" style="color: blue; float: left">home</a>
		</h3>
		<h3>
			<a href="./logout" style="color: red; float: right">logout</a>
		</h3>
		<br> <br> 
		<br>New Password: <input type="text" name="newpassword"><br>
		<br>Confirm Password:<input type="text" name="confirmpassword"><br> 
		<br><input type="submit" value = "change">
	</form>
</body>
</html>