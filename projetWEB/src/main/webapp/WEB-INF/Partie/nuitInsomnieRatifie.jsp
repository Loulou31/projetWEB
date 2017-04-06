<%-- 
    Document   : nuitInsomnieRatifie
    Created on : Apr 5, 2017, 8:03:45 PM
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
        <title>Repaire</title>
    </head>
    <body>
        <h1 align="center">Vous êtes dans le repaire des Loups-Garou</h1>
        <h2> Restez discrêt... Ils ne doivent pas vous voir... </h2>
        <h2> Les loups ont fait leur choix... ${pseudoJoueurElimine} sera dévoré cette nuit !   </h2>
        <p></p>
        <p> Ecoutez discrètement ce qu'il se dit...</p>
        <table>
            <c:forEach items="${messagesRepaire}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :  </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
    </body>
</html>
