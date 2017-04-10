<%-- 
    Document   : role
    Created on : Mar 31, 2017, 2:15:19 PM
    Author     : bagouc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/chose.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Attribution des rôles</title>
    </head>
    <body>
        <h1 id="header">Votre rôle dans cette partie est :${role}.</h1>
        <h1>Il y a ${nombreLoup} loups.</h1>
        <h1>Les pouvoirs sont présents à la probabilité: ${proba}.</h1>
        <% String pouvoir = request.getAttribute("pouvoir").toString();
            if (!pouvoir.equals("rien")) {
                out.print("<h1>Vous possedez le pouvoir : " + pouvoir + ".</h1>");
            } else {
                out.print("<h1>Vous ne possedez aucun pouvoir.</h1>");
            }
        %>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Rejoindre Salle Discussion</span></button><br>
                <input type="hidden" name="action" value="rejoindreJeu"/>
        </form>
    </body>
</html>
