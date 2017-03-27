<%-- 
    Document   : attenteDebutPartie
    Created on : Mar 24, 2017, 3:43:56 PM
    Author     : nicolasl
--%>

<%@page import="modele.Partie"%>
<%@page import="modele.Temps"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="designWEB.css" />
        <title>Attente Début</title>
    </head>
    <body>
        <%  Temps temps = new Temps();
            %>
        <p><%=temps.getTemps()%></p>
        
        <% Partie partie = (Partie) request.getAttribute("partie");
           long heureDeb = partie.getHeureDebut();
        %>
        <p>L'heure du début de partie est : <%=heureDeb%></p>
        
       

        <h1 id="header">Attendez les derniers joueurs ou le début de la partie ! </h1>
        <a href="controleur?action=index">Quitter la partie</a>
    </body>
</html>
