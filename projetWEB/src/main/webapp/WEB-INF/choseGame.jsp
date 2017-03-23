<%-- 
    Document   : choseGame
    Created on : Mar 23, 2017, 3:18:05 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choisir une partie</title>
    </head>
    <body>
        <h1> Choisissez la partie que vous souhaitez ou rejoignez une partie en attente de joueurs ! </h1>
        <p>
            <a href="controleur?action=newGame">Créer une nouvelle partie</a>
        </p>
        <h2> Parties en attente de joueurs </h2>
        <tr>
        <table>
            <tr>
                <th>Numéro  </th>
                <th>Nombre de joueurs attendus  </th>
                <th>Heure de début  </th>
                <th>Probabilité pouvoirs  </th>
                <th>Proportion de LG  </th>
                <th><!-- Rejoindre --></th>
            </tr>
        <c:forEach items="${parties}" var="partie">
                <tr>
                    <td>${partie.idPartie}</td>
                    <td>${partie.nbJoueurs}</td>
                    <td>${partie.heureDebut}</td>
                    <td>${partie.probaPouvoir}</td>
                    <td>${partie.proportionLG}</td>
                    <td><a href="controleur?action=getPartie&view=rejoindre&id=${partie.idPartie}">Rejoindre</a></td>
                </tr>
        </c:forEach>
        </table>
    </body>
</html>
