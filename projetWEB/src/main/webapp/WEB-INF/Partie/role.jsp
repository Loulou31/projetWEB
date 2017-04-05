<%-- 
    Document   : role
    Created on : Mar 31, 2017, 2:15:19 PM
    Author     : bagouc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attribution des rôles</title>
    </head>
    <body>
        <h1>Votre rôle dans cette partie est :${role}.</h1>
        <h1>Il y a ${nombreLoup} loups.</h1>
        <h1>Les pouvoirs sont présents à la probabilité: ${proba}.</h1>
        <% String pouvoir = request.getAttribute("pouvoir").toString();
            if (!pouvoir.equals("rien")) {
                out.print("<h1>Vous possedez le pouvoir : " + pouvoir + ".</h1>");
            } else {
                out.print("<h1>Vous ne possedez aucun pouvoir.</h1>");
            }
        %>
        <li><a href="controleurPartie?action=rejoindreJeu">Rejoindre Salle Discussion</a></li>
    </body>
</html>
