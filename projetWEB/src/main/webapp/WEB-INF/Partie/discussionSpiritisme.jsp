<%-- 
    Document   : discussionSpiritisme
    Created on : Apr 6, 2017, 5:44:30 PM
    Author     : nicolasl
--%>

<%@page import="modele.Partie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/place.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Place du village</title>
    </head>
    <body>
        <h1 align="center">Bienvenue sur la salle de discussions privée</h1>
        <p></p>
        <p>Un vivant... un mort... Arriverez-vous à obtenir des informations ? </p>
        <p> Ou bien, saurez-vous gardez vos secrets ? </p>
        <p></p>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :   </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <p></p>
        <% request.setAttribute("enDiscussion", 1);
        %>
        <form action="controleurPartie" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        <input type="hidden" name="spiritisme" value="true" />
        </form>
        <form action="controleurPartie" method="get" align="center">
            <button type="submit" class="button"><span>Actualiser la page</span></button><br>
            <input type="hidden" name="action" value="reloadMessages"/>
        </form>
        <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(1);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>
