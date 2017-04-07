<%-- 
    Document   : decisionLoup
    Created on : Apr 5, 2017, 2:57:45 PM
    Author     : bagouc
--%>

<%@page import="modele.Partie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/repaire.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet">
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Luckiest Guy", cursive;}
        </style>
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
        <button type="submit" class="button"><span>Valider</span></button><br>
        <input type="hidden" name="action" value="addDecision"/>
        <input type="hidden" name="decision" value="decision"/>
        </form>
         <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(0);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>