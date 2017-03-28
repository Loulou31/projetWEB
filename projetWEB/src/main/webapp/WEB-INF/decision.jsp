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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Il est temps de prendre une décision ! </h1>
        <ul>
            <li> Choisissez un joueur à éliminer :  
                <SELECT name="decision" size="1">
                    <c:forEach items="${villageois}" var="villageois"> 
                        <OPTION><c:out value="${villageois.pseudo}" />
                    </c:forEach>
                </SELECT>
            </li>
        </ul>
                    
        <input type="submit" value="Valider la décision" />
        <input type="hidden" name="action" value="addDecision"/>
    </body>
</html>
