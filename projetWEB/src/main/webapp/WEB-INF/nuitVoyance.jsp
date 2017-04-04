<%-- 
    Document   : nuitVoyance
    Created on : Apr 4, 2017, 4:38:20 PM
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
        <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">-->
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet">
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Luckiest Guy", cursive;}
        </style>
        <title>Voyance</title>
    </head>
    <body>
        <h1>Choisissez le joueur dont vous voulez connaître le rôle et le pouvoir.</h1>
        <SELECT name="choixVoyance" size="1">
                    <c:forEach items="${vivantsList}" var="vivants"> 
                        <OPTION><c:out value="${vivants.pseudo}" />
                    </c:forEach>
         </SELECT>
        <button type="submit" class="button"><span>Valider la décision</span></button><br>
        <input type="hidden" name="action" value="addChoixVoyant"/>
        <input type="hidden" name="voyant" value="voyant"/>
    </body>
</html>
