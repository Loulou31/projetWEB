<%-- 
    Document   : archiveRepaire
    Created on : Apr 7, 2017, 1:59:38 PM
    Author     : gaunetc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Archive Repaire</title>
    </head>
    <body>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Retentez d'être contacté !</span></button><br>
                <input type="hidden" name="action" value="reloadMessages"/>
        </form>
    </body>
</html>
