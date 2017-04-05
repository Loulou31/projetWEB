<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Le Loup-Garou !</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
     <link href="https://fonts.googleapis.com/css?family=Rock+Salt" rel="stylesheet"> 
    <style>
    html,body,h1,h2,h3,h4,h5,h6 {font-family: "Rock Salt", cursive}
    </style>
</head>
<body>

<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-theme w3-top w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    <a href="controleur?action=deconnexion" class="w3-bar-item w3-button w3-hide-small w3-hover-white bspec">Déconnexion</a>
  </div>
</div>

<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>


  <div class="w3-row w3-padding-64" style = "margin-top: 500px">
    <div class="w3-twothird w3-container">
        <p><h3 class="colspec" align="center">Etes-vous prêt à entrer dans le village ?</h3></p>
         <form action="controleur" method="get" align="center">
             <button type="submit" class="button"><span>Jouer</span></button><br>
             <input type="hidden" name="action" value="choseGame"/>
         </form> 
    </div>
  </div>


</body>
</html>


