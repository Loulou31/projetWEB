<%-- 
    Document   : nuitSpiritisme
    Created on : Apr 6, 2017, 5:54:55 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/connexion.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Nuit au village</title>
    </head>
    <body>
        <h1>Une sieste s'impose ! </h1>
        <p></p>
        <h2>Mais attention... ${nbLoups} loups-garou rôdent autour du village....</h2>
        <h2> Choisissez le joueur éliminé avec qui vous souhaitez discutez... </h2>
        <p></p>
        <form action="controleurPartie" accept-charset="utf-8">
        <ul>
            <li> Choisissez un joueur avec qui discuter : 
                <SELECT name="decisionSpiritisme" size="1">
                    <c:forEach items="${morts}" var="villageois"> 
                        <OPTION><c:out value="${morts.pseudo}" />
                    </c:forEach>
                </SELECT>
            </li>
        </ul>
    </body>
</html>