<%-- 
    Document   : attenteDebutPartie
    Created on : Mar 24, 2017, 3:43:56 PM
    Author     : nicolasl
--%>

<%@page import="modele.Villageois"%>
<%@page import="java.util.List"%>
<%@page import="modele.Partie"%>
<%@page import="modele.Temps"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/wait.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Revalia", cursive;}
        </style>
        <title>Attente Début</title>
    </head>
    <body>


        <h1 id="header">Attendez les derniers joueurs ou le début de la partie ! </h1>
        
        <p><%Temps temps = new Temps();%></p>
        <p><%=temps.getTemps()%></p>
        
        <% Partie partie = (Partie) request.getAttribute("partie");
           int intDeb = partie.getHeureDebut();
           String heureDebut = temps.calToString(temps.intToCal(intDeb));
        %>
        
        <p>L'heure du début de partie est : <%=heureDebut%></p>
        
        <p>Vous êtes dans la partie : <%=partie.getIdPartie()%></p>
        
        <% 
            int nbJoueurs = (int) request.getAttribute("nombreJoueurs");
            
            String stringVillageois = "La liste des joueurs présents est : ";
            List<Villageois> listeVillageois = (List<Villageois>) request.getAttribute("listeVillageois");
            for (int i = 0; i < listeVillageois.size(); i++){
                stringVillageois += listeVillageois.get(i).getPseudo() + "\n";
            }
        %>
        <p>La partie doit contenir entre : <b><%= partie.getNbJoueursMin() %></b> et <b><%= partie.getNbJoueursMax() %></b> joueurs.<br>
            Elle en contient actuellement : <%= nbJoueurs %>.<br>
            <%= stringVillageois %>
        </p>

        <% if (request.getAttribute("partiePrete") != null) { %>
            <form action="controleurPartie" method="get" align="center">
                <button type="submit" class="button"><span>Démarrer Partie</span></button><br>
                <input type="hidden" name="action" value="debutPartie"/>
                <input type="hidden" name="id" value="${partie.idPartie}"/>
            </form>
        <%}%>
        <p></p>
        <form action="controleur" method="get" align="center">
            <button type="submit" class="button"><span>Actualiser</span></button><br>
            <input type="hidden" name="action" value="actualiseAttente"/>
            <input type="hidden" name="idPartie" value="${partie.idPartie}"/>
        </form>
        <form action="controleur" method="get" align="center">
            <button type="submit" class="button"><span>Quitter la salle d'attente et retourner au menu principal</span></button><br>
            <input type="hidden" name="action" value="quitteAttentePartie"/>
        </form>
        
    </body>
</html>
