<%-- 
    Document   : joueurMort
    Created on : Apr 6, 2017, 3:04:05 PM
    Author     : nicolasl
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/mort.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Mort</title>
    </head>
    <body>
        <h1 align="center">Vous avez perdu la partie</h1>
        <h2> Vous pouvez avoir acc�s aux discussions qui ont eu lieu durant la partie  </h2>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Actualiser la page au cas o� vous seriez contact� par un esprit sup�rieur !</span></button><br>
                <input type="hidden" name="action" value="reloadMessages"/>
        </form>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Acc�der aux archives</span></button><br>
                <input type="hidden" name="action" value="archivage"/>
        </form>
    </body>
</html>

