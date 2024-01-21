<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>Lab3 - Bean w JSP</title>
</head>
<body>
<jsp:useBean id="dBean" class="Lab3.NewBean" scope="session" />
<jsp:setProperty name="dBean" property="sampleProperty" value="100" />
Zapisałem dane do Beana.<br>
Wyprowadzam dane z Beana:
<i><jsp:getProperty name="dBean" property="sampleProperty" /></i><br>
</body>
</html>