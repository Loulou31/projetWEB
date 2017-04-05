<%-- 
    Document   : failLogin
    Created on : Mar 24, 2017, 11:27:35 AM
    Author     : gaunetc
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="fr">
    <head>
      <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/connexion.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
        <link href="https://fonts.googleapis.com/css?family=Luckiest+Guy" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css?family=Rock+Salt" rel="stylesheet"> 
        <style>
        html,body,h1,h2,h3,h4,h5,h6 {font-family: "Rock Salt", cursive}
        </style>
      <title>Connexion</title>
    </head>
    <body>
      <h1 id="header">Echec de connexion, veuillez réessayer: </h1>
      <form action="controleur" method="post" accept-charset="UTF-8">
        <ul>
          <li> Login : <input type="text" name="login"/></li>
          <li> Mot de passe : <input type="password" name="password"/></li>
        </ul>
        <input type="submit" name="Login" />
        <input type="hidden" name="action" value="login" />
      </form>
      <a href="controleur?action=inscription">----> Vous n'êtes pas inscrit ? Inscrivez-vous ici <----</a>
    </body>
</html>
