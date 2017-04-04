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
        <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">-->
        <link href="https://fonts.googleapis.com/css?family=Revalia" rel="stylesheet"> 
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
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
        
        <p>Voues êtes dans la partie : <%=partie.getIdPartie()%></p>
        
        <% 
            int nbJoueurs = (int) request.getAttribute("nombreJoueurs");
            
            String stringVillageois = "La liste des joueurs présents est : ";
            List<Villageois> listeVillageois = (List<Villageois>) request.getAttribute("listeVillageois");
            for (int i = 0; i < listeVillageois.size(); i++){
                stringVillageois += listeVillageois.get(i).getPseudo() + "\n";
            }
        %>
        <p>La partie doit contenir entre : <%= partie.getNbJoueursMin() %> et <%= partie.getNbJoueursMax() %> joueurs.<br>
            Elle en contient actuellement : <%= nbJoueurs %>.<br>
            <%= stringVillageois %>
        </p>
        
        
        <a href="controleur?action=quitteAttentePartie">Quitter la salle d'attente et retourner au menu principal</a>
    </body>
</html>
