<%-- 
    Document   : repaireVoyanceReponse
    Created on : Apr 7, 2017, 1:47:01 PM
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
        <title>Resultat Voyance</title>
    </head>
    <body>
        <h1>Vous avez fait votre choix...</h1>
        <h2> Le villageois  @${pseudo}  est un ${role}.</h2>
        <h2> Son pouvoir est : ${pouvoir}</h2>
        <p></p>
        <p>Peut-être aurez-vous la chance de découvrir d'autres secrets demain.... si vous survivez au vote sur la place du village... ! </p>
        <a href="controleurPartie?action=rejoindreNuitLoup">Retour au repaire...</a>
    </body>
     <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(0);
        %>
        <p><%=tempsChangement%></p>
</html>