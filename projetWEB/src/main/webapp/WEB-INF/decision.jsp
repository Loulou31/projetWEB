<%-- 
    Document   : decision
    Created on : Mar 28, 2017, 4:30:25 PM
    Author     : nicolasl
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
        <form action="controleur" accept-charset="utf-8">
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
