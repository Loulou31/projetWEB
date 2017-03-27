
<%@page import="modele.Temps"%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <meta content="text/html ; charset=UTF-8">
        <link rel="stylesheet" href="designWEB.css" />
        <title>Start Page</title>
       
       
    </head>
    <body>
        <%  Temps temps = new Temps();
            %>
            <p><%=temps.getTemps()%></p>
        


        <div id="menu">
            <ul id="onglets">
                <li><a href="controleur?action=inscription">Inscription</a></li>
                <li><a href="controleur?action=connexion">Connexion</a></li>
            </ul>
        </div>
    </body>
</html>