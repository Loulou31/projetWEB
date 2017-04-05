<%-- 
    Document   : choseGame
    Created on : Mar 23, 2017, 3:18:05 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/chose.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">-->
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Choisir une partie</title>
    </head>
    <body>
        <h1> Choisissez la partie que vous souhaitez ou rejoignez une partie en attente de joueurs ! </h1>
        <form action="controleur" method="get" align="center">
            <button type="submit" class="button"><span>Créer une nouvelle partie</span></button><br>
            <input type="hidden" name="action" value="newGame"/>
        </form>
        <h2> Parties en attente de joueurs </h2>
        
        <tr>
        <table id="customers">
            <tr class="bloc-variable">
                <th>Numéro  </th>
                <th>Nombre Players entre  </th>
                <th>et  </th>
                <th>Heure de début  </th>
                <th>Probabilité pouvoirs  </th>
                <th>Proportion de LG  </th>
                <th><!-- Rejoindre --></th>
            </tr>
        <c:forEach items="${parties}" var="partie">
                <tr>
                    <td>${partie.idPartie}</td>
                    <td>${partie.nbJoueursMin}</td>
                    <td>${partie.nbJoueursMax}</td>
                    <td>${partie.heureDebut}</td>
                    <td>${partie.probaPouvoir}</td>
                    <td>${partie.proportionLG}</td>
                    <td><a href="controleur?action=getPartie&view=rejoindre&id=${partie.idPartie}">Rejoindre</a></td>
                </tr>
        </c:forEach>
        </table>
    </body>
</html>
