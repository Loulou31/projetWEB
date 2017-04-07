<%-- 
    Document   : placeDuVillage
    Created on : Mar 24, 2017, 6:14:59 PM
    Author     : gaunetc
--%>

<%@page import="modele.Partie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/place.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Place du village</title>
    </head>
    <body>
        <div class="w3-top">
            <div class="w3-bar w3-theme w3-top w3-left-align w3-large">
              <a class="w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
              <a href="controleur?action=deconnexion" class="w3-bar-item w3-button w3-hide-small w3-hover-white bspec">Déconnexion</a>
            </div>
        </div>
        <h1 align="center" style="margin-top: 50px">Bienvenue sur la place du village !</h1>
        <h2> ${morts} </h2>
        <p></p>
        <h2> Désignez le villageois à éliminer aujoud'hui... </h2>
        <p>Vous êtes ${roleJoueurEnCours}. Votre pouvoir est : ${pouvoirJoueurEnCours}.</p>
        <p> Il y a ${nbJoueurs} villageois vivants. Il y a ${nbLoups} loups-garou qui rodent parmi vous ! Faites attention...</p>
        <p></p>
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
        <a href="controleurPartie?action=newDecision">Proposer une décision</a>
        <p></p>
        <p>Liste des villageois vivants : </p>
        <li>
            <c:forEach items="${joueurs}" var="joueurs"> 
                <tr>
                    <td><B>@${joueurs.pseudo} </B></td>
                </tr>
            </c:forEach>
        </li>
        <h2> Discutez avec les autres villageois... </h2>
        <table>
            <c:forEach items="${messages}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :   </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <p></p>
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
           String tempsChangement = partie.tempsAvantChangement(1);
        %>
        <p><%=tempsChangement%></p>
    </body>
</html>
