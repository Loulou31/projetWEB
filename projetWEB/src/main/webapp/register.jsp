<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <title>Page d'inscription</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="style.css" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Page d'inscription:</h1>
        <form method="POST" action="add_player" accept-charset="utf-8">
           Pseudo: <input type="text" name="login" value="" /><br/>
           Mot de passe: <input type="password" name="password" value="" /><br/>
           <input type="submit" value="CrÃ©er" name="Create" /> 
        </form>
    </body>
</html>
