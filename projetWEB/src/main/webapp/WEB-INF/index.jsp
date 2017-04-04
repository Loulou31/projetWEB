<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Le Loup-Garou !</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/village.css"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
    <!--<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">-->
     <link href="https://fonts.googleapis.com/css?family=Rock+Salt" rel="stylesheet"> 
    <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">-->
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

<!-- Sidebar 
<nav class="w3-sidebar w3-bar-block w3-collapse w3-large w3-theme-l5 w3-animate-left" style="z-index:3;width:250px;margin-top:43px;" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large" title="Close Menu">
    <i class="fa fa-remove"></i>
  </a>
</nav>-->

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- Main content: shift it to the right by 250 pixels when the sidebar is visible -->
<!--<div class="w3-main" style="margin-left:250px">-->

  <!-- Button to open the modal login form -->


  <div class="w3-row w3-padding-64" style = "margin-top: 20px">
    <div class="w3-twothird w3-container">
        <p><h3 class="colspec" align="center">Etes-vous prêt à entrer dans le village ?</h3></p>
         <form action="controleur" method="get" align="center">
             <button type="submit" class="button"><span>Jouer</span></button><br>
             <input type="hidden" name="action" value="choseGame"/>
         </form> 
    </div>
  </div>


<!--  <footer id="myFooter">
    <div class="w3-container w3-theme-l2 w3-padding-32">
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
    </div>
  </footer>-->

<!-- END MAIN -->
</body>
</html>


