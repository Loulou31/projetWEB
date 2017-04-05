<%-- 
    Document   : nuitInsomnie
    Created on : Apr 4, 2017, 4:32:51 PM
    Author     : nicolasl
--%>

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
        <h2> Restez discrêt... Ils ne doivent pas vous voir... </h2>
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
        <h2> Discutez avec les autres loups... </h2>
        <p> Mais faites attention aux oreilles indiscètes qui pourraient vous écouter....</p>
        <table>
            <c:forEach items="${messagesRepaireInsomnie}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :  </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
    </body>
</html>
