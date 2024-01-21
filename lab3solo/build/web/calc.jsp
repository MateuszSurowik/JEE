<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<html>
<head>
    <title>Lab3 - Bean w JSP</title>
</head>
<body>
    <c:import url="calcData.xml" var="baza" />
    <x:parse doc="${baza}" var="wynik"/>
    <ol>
        <x:forEach select="$wynik/rownania/rownanie" var="zasob">
            <li>
                <b><x:out select="liczba1" /></b>
                <x:out select="znak" />
                <x:out select="liczba2" />
                <x:out select="wynik" />
            </li>
        </x:forEach>
    </ol>
    <button onclick="window.location.href='calculator.jsp'">back to calculating</button>
</body>
</html>
