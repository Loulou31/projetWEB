<%-- 
    Document   : nuitInsomnie
    Created on : Apr 4, 2017, 4:32:51 PM
    Author     : nicolasl
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
        <title>Repaire des loups</title>
    </head>
    <body>
        <h1 align="center">Vous êtes dans le repaire des Loups-Garou</h1>
        <h2> Restez discret... Ils ne doivent pas vous voir... </h2>
        <p> Ecoutez discrètement ce qu'il se dit au repaire...</p>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :  </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <form action="controleurPartie" method="get" align="center">
            <button type="submit" class="button"><span>Actualiser la page</span></button><br>
            <input type="hidden" name="action" value="reloadMessages"/>
        </form>
        
         <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(0);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>
