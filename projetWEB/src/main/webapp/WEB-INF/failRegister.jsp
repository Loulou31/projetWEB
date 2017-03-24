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
      <title>Inscription</title>
    </head>
    <body>
      <h1>Echec d'inscription, veuillez réessayer: </h1>
      <form action="controleur" method="post" accept-charset="UTF-8">
        <ul>
          <li> Login : <input type="text" name="login"/></li>
          <li> Mot de passe : <input type="password" name="password"/></li>
        </ul>
        <input type="submit" name="Register" />
        <input type="hidden" name="action" value="register" />
      </form>
    </body>
</html>