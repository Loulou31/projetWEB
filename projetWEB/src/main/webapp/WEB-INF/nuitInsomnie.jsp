<%-- 
    Document   : nuitInsomnie
    Created on : Apr 4, 2017, 4:32:51 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/test.css"/>
        <title>Repaire des loups</title>
    </head>
    <body>
        <h1>Vous entrez dans le repaire des Loups-Garou</h1>
        <h2> Qui allez-vous dévorer ce soir...? </h2>
        <p></p>
        <table>
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
        <h2> Discutez avec les autres loups... </h2>
        <p> Mais faites attention aux oreilles indiscètes qui pourraient vous écouter....</p>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td>${message.expediteur}</td><td>${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
    </body>
</html>
