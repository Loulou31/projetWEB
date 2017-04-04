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
        <link rel="stylesheet" type="text/css" href="css/repaire.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">-->
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet">
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Luckiest Guy", cursive;}
        </style>
        <title>Repaire</title>
    </head>
    <body>
        <h1 align="center">Repaire des loup-garous !</h1>
        <h2> Votez pour une décision en cours ou proposez en une ! </h2>
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
                    <td>
                    <form action="controleur" accept-charset="UTF-8">    
                        <input type="submit" value="Voter"/>
                        <input type="hidden" name="action" value="addVote"/>
                        <input type="hidden" name="joueurConcerne" value="${decision.joueurConcerne}"/>
                    </form>
                    </td>
                </tr>   
            </c:forEach>
            </tr>
        </table>
        <a href="controleur?action=newDecision">Proposer une décision</a>
        <h2> Discutez avec les autres loup-garous... </h2>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td>${message.expediteur}</td><td>${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <form action="controleur" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
    </body>
</html>
