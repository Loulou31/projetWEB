<%-- 
    Document   : attenteDebutPartiePrete
    Created on : Mar 31, 2017, 12:18:57 PM
    Author     : lenetd
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
        <p id="demo"></p>

        <script>
        // Set the date we're counting down to
        var countDownDate = <%=temps.getTempsLong()%>;

        // Update the count down every 1 second
        var x = setInterval(function() {

          // Get todays date and time
          var now = new Date().getTime();

          // Find the distance between now an the count down date
          var distance = countDownDate - now;

          // Time calculations for days, hours, minutes and seconds
          var days = Math.floor(distance / (1000 * 60 * 60 * 24));
          var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
          var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
          var seconds = Math.floor((distance % (1000 * 60)) / 1000);

          // Display the result in the element with id="demo"
          document.getElementById("demo").innerHTML = days + "d " + hours + "h "
          + minutes + "m " + seconds + "s ";

          // If the count down is finished, write some text
          if (distance < 0) {
            clearInterval(x);
            document.getElementById("demo").innerHTML = "EXPIRED";
          }
        }, 1000);
        </script>
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
        
        <a href="controleur?action=debutPartie&id=${partie.idPartie}">Démarrer Partie</a>
        
        
        <a href="controleur?action=index">Retourner au menu principal</a>
    </body>
</html>
