<%-- 
    Document   : decisionCanibal
    Created on : Apr 5, 2017, 3:23:33 PM
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
        <form action="controleurPartie" accept-charset="utf-8">
        <ul>
            <li> Choisissez un joueur à éliminer : 
                <SELECT name="decisionCanibal" size="1">
                    <c:forEach items="${vivants}" var="vivants"> 
                        <OPTION><c:out value="${vivants.pseudo}" />
                    </c:forEach>
                </SELECT>
            </li>
        </ul>
        <input type="submit" value="Valider la décision" />
        <input type="hidden" name="action" value="addDecisionCanibal"/>
        <input type="hidden" name="decision" value="decisionCanibal"/>
        </form>
    </body>
</html>