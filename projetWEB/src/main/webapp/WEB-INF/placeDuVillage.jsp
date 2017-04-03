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
        <link rel="stylesheet" type="text/css" href="css/test.css"/>
        <title>Place du village</title>
    </head>
    <body>
        <h1>Place du village !</h1>
        <h2> Votez pour une décision en cours ou proposez en une ! </h2>
        <table>
            <tr>
                <th> Joueur désigné  </th>
                <th> Nombre de votes </th>
                <th> Nombre de joueurs : ${nbJoueurs} </th>
            </tr>
            <c:forEach items="${decisions}" var="decisions">
                <tr>
                    <td>${decisions.joueurConcerne}</td>
                    <td>${decisions.getNbVote()}</td>
                    <td><input type="submit" value="Voter"/>
                        <input type="hidden" name="action" value="addVote"/>
                        <input type="hidden" name="joueurConcerne" value="${decision.joueurConcerne}"/>
                    </td>
                </tr>   
            </c:forEach>
            </tr>
        </table>
        <a href="controleur?action=newDecision">Proposer une décision</a>
        <h2> Discutez avec les autres villageois... </h2>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td>${message.expediteur}</td><td>${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <form action="controleur" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" value = "Rentrez votre message ici"/>
        <input type="submit" name="Message" value ="Poster" />
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
    </body>
</html>
