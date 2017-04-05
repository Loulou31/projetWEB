<%-- 
    Document   : placeDuVillage
    Created on : Mar 24, 2017, 6:14:59 PM
    Author     : gaunetc
--%>

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
        <h1 align="center">Bienvenue sur la place du village !</h1>
        <h2> La décision est ratifiée !</h2>
        <p></p>
        <table id="customers">
            <tr>
                <th> Joueur désigné  </th>
                <th> Nombre de votes </th>
                <th> Nombre de joueurs : ${nbJoueurs} </th>
            </tr>
            <c:forEach items="${decisions}" var="decision">
                <tr>
                    <td>${decision.joueurConcerne}</td>
                    <td>${decision.getNbVote()}</td>
                </tr>   
            </c:forEach>
            </tr>
        </table>
        <h2> Discutez avec les autres villageois... </h2>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :   </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <p></p>
        <form action="controleurPartie" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
    </body>
</html>
