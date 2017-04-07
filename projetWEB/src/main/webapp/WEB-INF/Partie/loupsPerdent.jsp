<%-- 
    Document   : loupsPerdent
    Created on : Apr 6, 2017, 6:25:50 PM
    Author     : bagouc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/villagewin.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Partie Terminée</title>
    </head>
    <body>
        <h1>Les humains ont gagné !!!!!</h1>
        <form action="controleur" method="get" align="center" class="formi">
                <button type="submit" class="button"><span>Voulez-vous rejouer ?</span></button><br>
                <input type="hidden" name="action" value="quittePartie"/>
        </form>
    </body>
</html>
