<%-- 
    Document   : failLogin
    Created on : Mar 24, 2017, 11:27:35 AM
    Author     : gaunetc
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="fr">
    <head>
      <meta charset="UTF-8" />
      <link rel="stylesheet" type="text/css" href="style.css" />	
      <title>Connexion</title>
    </head>
    <body>
      <h1>Echec de connexion, veuillez réessayer: </h1>
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
