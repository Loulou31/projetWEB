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
        <p>Vous êtes ${roleJoueurEnCours}. Votre pouvoir est : ${pouvoirJoueurEnCours}.</p>
        <p> Il y a ${nbJoueurs} villageois vivants. Il y a ${nbLoups} loups-garou qui rodent parmi vous ! Faites attention...</p>
        <h2> La décision est ratifiée !</h2>
        <p>Le joueur ${pseudoJoueurElimine} nous a quitté aujourd'hui.... Et c'était un ${roleJoueurElimine} ! </p>
        <p></p>
        <form action="controleurPartie" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Actualiser la page</span></button><br>
                <input type="hidden" name="action" value="reloadMessages"/>
        </form>
    </body>
</html>
