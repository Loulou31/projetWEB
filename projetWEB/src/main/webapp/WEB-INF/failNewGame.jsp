<%-- 
    Document   : failNewGame
    Created on : Mar 30, 2017, 11:17:43 AM
    Author     : lenetd
--%>


<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link media="all" rel="stylesheet" type="text/css" href="design.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nouvelle Partie Echec</title>
    </head>
    <body>
        <h1>Vous devez indiquer un horaire de début de partie postérieur à l'heure actuelle</h1>
        <h1>Creation d'une nouvelle partie</h1>
        <p> Veuillez saisir les parametres de la nouvelle partie </p>
        <form action="controleur" method="post" accept-charset="UTF-8">
        <ul>
          <li> Nombre de participants entre  
            <SELECT name="JMin" size="1">
                <c:forEach var="i" begin="5" end="20" step="1"> 
                    <OPTION><c:out value="${ i }" />
                </c:forEach>
            </SELECT>
            et 
            <SELECT name="JMax" size="1">
                <c:forEach var="i" begin="5" end="20" step="1"> 
                    <OPTION><c:out value="${ i }" />
                </c:forEach>
            </SELECT>
          </li>
          <li> Duree d'une journee : <input type="text" name="day" value="14" size="2"/>h 00</li>
          <li> Duree d'une nuit : <input type="text" name="night" value="10" size="2"/>h 00</li>
          <li> A quelle heure commencera la partie aujourd'hui ? <input type="text" name="beginHour" value="8" size="2"/>h <input type="text" name="beginMin" value="00" size="2"/></li>
          <li> Probabilite d'attribution des pouvoirs speciaux :  <input type="text" name="power" value="0.0" size="2"/> </li>
          <li> Proportion initiale des Loups-Garous :  <input type="text" name="werewolf" value="0.3" size="2"/></li>
        </ul>
        <input type="submit" value="Creer une nouvelle partie" />
        <input type="hidden" name="action" value="addGame"/>
        
      </form>
    </body>
</html>
