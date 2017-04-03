<%--<%@page import="modele.Temps"%>--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<title>Le Loup-Garou !</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/test.css"/>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif}
</style>
<body>

<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-theme w3-top w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    <!--<a href="controleur?action=connexion" class="w3-bar-item w3-button w3-hide-small w3-hover-white">Connexion</a>-->
    <button onclick="document.getElementById('id01').style.display='block'" class="w3-bar-item w3-button w3-hide-small w3-hover-white bspec">Connexion</button>
    <a href="controleur?action=inscription" class="w3-bar-item w3-button w3-theme-l1">Inscription</a>
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

<!-- The Modal -->
  <div id="id01" class="modal">
  <span onclick="document.getElementById('id01').style.display='none'"
    class="close" title="Close Modal">&times;</span>

  <!-- Modal Content -->
  <form class="modal-content animate" action="controleur" method="post" accept-charset="UTF-8">
    <div class="imgcontainer">
      <img src="css/loup.png" alt="Avatar" class="avatar">
    </div>
        <div class="container">
          <label><b>Pseudo</b></label>
          <input type="text" placeholder="Entrez votre pseudo..." name="login" required>

          <label><b>Mot de passe</b></label>
          <input type="password" placeholder="Entre votre mot de passe..." name="password" required>

          <button type="submit">Login</button>
        </div>

        <div class="container" style="background-color:#f1f1f1">
          <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
        </div>
      <input type="hidden" name="action" value="login" />
      </form>
    </div> 
  </div>

  <div class="w3-row w3-padding-64" style = "margin-top: 20px">
    <div class="w3-twothird w3-container">
      <h1 class="w3-text-teal">Règles du jeu: </h1>
      <p>Vous déciderez de rejoindre le village de Thiercelieux...</p>
      <p>Mais ce village est différent de tous ceux que vous aurez pu connaître. En effet, certaines personnes sont des humains, tandis que d'autres sont des loup-garous.</p>
      <p>Les loup-garous sont des humains le jour et se transforment la nuit pour manger un villageois. Certaines sources affirment même que vous pourriez en devenir un dû à l'étrange ambiance qui s'y cache...</p>
      <p>Parviendrez-vous à défendre votre village de ces horribles bêtes à l'aide des autres villageois ? Ou bien réussirez-vous à tous les manger...</p>
    </div>
    
  </div>


  <footer id="myFooter">
    <div class="w3-container w3-theme-l2 w3-padding-32">
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
    </div>
  </footer>

<!-- END MAIN -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
    if (mySidebar.style.display === 'block') {
        mySidebar.style.display = 'none';
        overlayBg.style.display = "none";
    } else {
        mySidebar.style.display = 'block';
        overlayBg.style.display = "block";
    }
}

// Close the sidebar with the close button
function w3_close() {
    mySidebar.style.display = "none";
    overlayBg.style.display = "none";
}
</script>

</body>
</html>
