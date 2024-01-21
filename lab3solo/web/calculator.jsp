<%--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>
<f:view>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>JSF error page</title>
</head>
<body>
<h:form id="formularz_kalkulator">
    <h:inputText value="#{obliczenia.liczba1}" /><br /><br />
    <h:inputText value="#{obliczenia.znak}" /><br /><br />
    <h:inputText value="#{obliczenia.liczba2}" /><br /><br />
<h:commandButton value="show calculating history" action="goToHistory" />
<h:commandButton value="Oblicz" action="#{obliczenia.oblicz}" />
<h:outputLabel value="Wynik: #{obliczenia.wynik}" />
</h:form>
</body>
</html>
</f:view>
