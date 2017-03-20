<%-- 
    Document   : bdErreur
    Created on : Mar 22, 2012, 12:32:53 AM
    Author     : reignier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <title>Erreur BD</title>
    </head>
    <body>
        <h1 style="text-align: center">Erreur</h1>
        <img src="images/scared.png" alt="" />
        <p>Une erreur d’accès à la base de données vient de se produire.</p>
        <p>Message : </p>
        <pre>${erreurMessage}</pre>
</body>
</html>