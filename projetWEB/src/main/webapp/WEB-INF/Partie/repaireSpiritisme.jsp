<%-- 
    Document   : spiritisme
    Created on : Apr 6, 2017, 3:45:26 PM
    Author     : nicolasl
--%>

<%@page import="modele.Partie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/repaire.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet">
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Luckiest Guy", cursive;}
        </style>
        <title>Repaire</title>
    </head>
    <body>
        <h1 align="center">Vous entrez dans le repaire des Loups-Garou</h1>
        <h2> Qui allez-vous dévorer ce soir...?  </h2>
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
                    <form action="controleurPartie" accept-charset="UTF-8">    
                        <input type="submit" value="Voter"/>
                        <input type="hidden" name="action" value="addVote"/>
                        <input type="hidden" name="joueurConcerne" value="${decision.joueurConcerne}"/>
                    </form>
                    </td>
                </tr>   
            </c:forEach>
            </tr>
        </table>
        <p></p>
        <a href="controleurPartie?action=newDecisionLoup">Proposer une décision</a>
        <p></p>
        <p></p>
        <h2> Choisissez le joueur éliminé avec qui vous souhaitez discutez... </h2>
        <p></p>
        <form action="controleurPartie" accept-charset="utf-8">
        <ul>
            <li> Choisissez un joueur avec qui discuter : 
                <SELECT name="decisionSpiritisme" size="1">
                    <c:forEach items="${morts}" var="villageois"> 
                        <OPTION><c:out value="${villageois.pseudo}" />
                    </c:forEach>
                </SELECT>
            </li>
        </ul>
         <button type="submit" class="button"><span>Valider</span></button><br>
        <input type="hidden" name="action" value="addDecisionSpiritisme"/>
        <input type="hidden" name="decisionSpiritisme" value="decisionSpiritisme"/>
        </form>
        <h2> Discutez avec les autres loups... </h2>
        <p> Mais faites attention aux oreilles indiscrètes qui pourraient vous écouter....</p>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :  </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <form action="controleurPartie" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
        <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Actualiser la page</span></button><br>
                <input type="hidden" name="action" value="reloadMessages"/>
        </form>
        <% Partie partie = (Partie) request.getAttribute("partie");
           String tempsChangement = partie.tempsAvantChangement(0);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>