<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
 	<div>
 	${requestScope.name}
 	</div>
 	<div>
 		<img src="${requestScope.picture}"></img>
 	</div>
 	<div>has (${requestScope.friends}) friends.</div>
</html>