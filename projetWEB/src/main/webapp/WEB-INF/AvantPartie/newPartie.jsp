<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link media="all" rel="stylesheet" type="text/css" href="design.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nouvelle Partie</title>
    </head>
    <body>
        <h1>Creation d'une nouvelle partie</h1>
        <p> Veuillez saisir les parametres de la nouvelle partie </p>
        <form action="controleur" method="post" accept-charset="UTF-8">
        <ul>
          <li> Nombre de participants entre : <input type="number" name="JMin" value="5" min="1" max="20"/> et <input type="number" name="JMax" value="20" min="2" max="20"/> 
              <% if (request.getAttribute("erreurNbJoueurs") != null){
                  out.print("<font color=\"red\">" + "Le nombre de joueurs minimum doit etre plus grand que le nombre de joueur maximum" + "</font>");
              }  
              %>
          </li>
          <li> Duree d'une journee : <input type="number" name="dayHour" value="14" min="0" max="23"/> h <input type="number" name="dayMin" value="0" min="0" max="59"/></li>
          <li> Duree d'une nuit : <input type="number" name="nightHour" value="10" min="0" max="23"/> h <input type="number" name="nightMin" value="0" min="0" max="59"/></li>
          <li> A quelle heure commencera la partie aujourd'hui ? <input type="number" name="beginHour" value="8" min="0" max="23"/> h <input type="number" name="beginMin" value="0" min="0" max="59"/>
              <% if (request.getAttribute("erreurHeure") != null){
                  out.print("<font color=\"red\">" + "Vous devez choisir une heure postérieure à l'heure actuelle" + "</font>");
              }  
              %>
          </li>
          <li> Probabilite d'attribution des pouvoirs speciaux :  <input type="number" name="power" value="0" min="0" max="100" /> % </li>
          <li> Proportion initiale des Loups-Garous :  <input type="number" name="werewolf" value="35" min="0" max="100"/> % </li>
        </ul>
        <input type="submit" value="Creer une nouvelle partie" />
        <input type="hidden" name="action" value="addGame"/>
        <p style="color:red;"></p>
      </form>
    </body>
</html>
