<%-- 
    Document   : nuitVoyance
    Created on : Apr 4, 2017, 4:38:20 PM
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
        <title>Voyance</title>
    </head>
    <body>
        <h1>Choisissez le joueur dont vous voulez connaître le rôle et le pouvoir.</h1>
        <h2>Réfléchissez bien... ${nbLoups} loups-garou rôdent autour du village....</h2>
        <form action="controleurPartie" accept-charset="utf-8">
        <SELECT name="choixVoyance" size="1">
                    <c:forEach items="${vivants}" var="vivants"> 
                        <OPTION><c:out value="${vivants.getPseudo()}" />
                    </c:forEach>
         </SELECT>
        <button type="submit" class="button"><span>Valider la décision</span></button><br>
        <input type="hidden" name="action" value="addChoixVoyant"/>
        <input type="hidden" name="choixVoyance" value="choixVoyance"/>
        </form>
         <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(0);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>
