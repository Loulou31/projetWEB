<%-- 
    Document   : repaireContaminationRatifie
    Created on : Apr 5, 2017, 8:01:13 PM
    Author     : nicolasl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/repaire.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet">
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Luckiest Guy", cursive;}
        </style>
        <title>Repaire</title>
    </head>
    <body>
        <h1 align="center">Vous êtes dans le repaire des Loups-Garou</h1>
        <h2> Vous avez fait votre choix... ${pseudoJoueurElimine} sera dévoré cette nuit !   </h2>
        <p></p>
        <a href="controleurPartie?action=newDecisionContamination">Transformer un villageois en Loup-Garou</a>
        <p></p>
        <h2> Discutez avec les autres loups... </h2>
        <p> Mais faites attention aux oreilles indiscrètes qui pourraient vous écouter....</p>
        <table>
            <c:forEach items="${messagesRepaire}" var="message">
                    <tr>
                        <td><B>@${message.expediteur}  :  </B></td><td>  ${message.contenu}</td>
                    </tr>
            </c:forEach>
        </table>
        <form action="controleurPartie" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" placeholder="Entrez votre message..."/>
        <button type="submit" class="button"><span>Poster</span></button><br>
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
    </body>
</html>
