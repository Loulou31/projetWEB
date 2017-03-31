<%-- 
    Document   : placeDuVillage
    Created on : Mar 24, 2017, 6:14:59 PM
    Author     : gaunetc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Place du village</title>
    </head>
    <body>
        <h1>Place du village !</h1>
        <a href="controleur?action=newDecision">Proposer une décision</a>
        <c:forEach items="${messages}" var="message">
                <tr>
                    <td>${message.expediteur}</td><td>${message.contenu}</td>
                </tr>
        </c:forEach>
        <form action="controleur" method="post" accept-charset="UTF-8">
            Ecrire un message : <input type="text" name="contenu" value = "Rentrez votre message ici"/>
        <input type="submit" name="Message" value ="Poster" />
        <input type="hidden" name="action" value="ajouterUnMessage" />
        </form>
    </body>
</html>
