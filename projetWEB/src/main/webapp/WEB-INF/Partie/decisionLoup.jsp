<%-- 
    Document   : decisionLoup
    Created on : Apr 5, 2017, 2:57:45 PM
    Author     : bagouc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Decision</title>
    </head>
    <body>
        <h1>Il est temps de prendre une décision ! </h1>
        <p>Vous êtes dans le repaire des loups...
        <form action="controleurPartie" accept-charset="utf-8">
        <ul>
            <li> Choisissez un joueur à éliminer : 
                <SELECT name="decision" size="1">
                    <c:forEach items="${villageoisList}" var="villageois"> 
                        <OPTION><c:out value="${villageois.pseudo}" />
                    </c:forEach>
                </SELECT>
            </li>
        </ul>
        <input type="submit" value="Valider la décision" />
        <input type="hidden" name="action" value="addDecision"/>
        <input type="hidden" name="decision" value="decision"/>
        </form>
    </body>
</html>