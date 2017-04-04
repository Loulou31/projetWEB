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
            <%--<li> Nombre de participants entre  
            <SELECT name="JMin" size="1">
                <c:forEach var="i" begin="1" end="20" step="1"> 
                    <OPTION><c:out value="${ i }" />
                </c:forEach>
            </SELECT>
            et 
            <SELECT name="JMax" size="1">
                <c:forEach var="i" begin="5" end="20" step="1"> 
                    <OPTION><c:out value="${ i }" />
                </c:forEach>
            </SELECT>
          </li>--%>
          <li> Nombre de participants entre : <input type="number" name="JMin" value="5" min="2" max="20"/> et <input type="number" name="JMax" value="20" min="2" max="20"/></li>
          <li> Duree d'une journee : <input type="number" name="dayHour" value="14" min="0" max="23"/> h <input type="number" name="dayMin" value="0" min="0" max="59"/></li>
          <li> Duree d'une nuit : <input type="number" name="nightHour" value="10" min="0" max="23"/> h <input type="number" name="nightMin" value="0" min="0" max="59"/></li>
          <li> A quelle heure commencera la partie aujourd'hui ? <input type="number" name="beginHour" value="8" min="0" max="23"/> h <input type="number" name="beginMin" value="0" min="0" max="59"/></li>
          <li> Probabilite d'attribution des pouvoirs speciaux :  <input type="number" name="power" value="0" min="0" max="100" /> % </li>
          <li> Proportion initiale des Loups-Garous :  <input type="number" name="werewolf" value="35" min="0" max="100"/> % </li>
        </ul>
        <input type="submit" value="Creer une nouvelle partie" />
        <input type="hidden" name="action" value="addGame"/>
        
      </form>
    </body>
</html>
