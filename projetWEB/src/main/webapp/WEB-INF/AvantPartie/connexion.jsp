<%--<%@page import="modele.Temps"%>--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Le Loup-Garou !</title>
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
</head>
<body>

<div class="w3-top">
  <div class="w3-bar w3-theme w3-top w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    <button onclick="document.getElementById('id01').style.display='block'" class="w3-bar-item w3-button w3-hide-small w3-hover-white bspec">Connexion</button>
    <button onclick="document.getElementById('id02').style.display='block'" class="w3-bar-item w3-button w3-theme-l1 bspec">Inscription</button>
  </div>
</div>

<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>


  <div id="id01" class="modal">
  <span onclick="document.getElementById('id01').style.display='none'"
    class="close" title="Close Modal">&times;</span>

  <form class="modal-content animate" action="controleur" method="post" accept-charset="UTF-8">
    <div class="imgcontainer">
      <img src="css/loup.png" alt="Avatar" class="avatar">
    </div>
        <div class="container">
          <label><b>Pseudo</b></label>
          <input type="text" placeholder="Entrez votre pseudo..." name="login" required>

          <label><b>Mot de passe</b></label>
          <input type="password" placeholder="Entre votre mot de passe..." name="password" required>
          <div class="clearfix">
            <button type="submit" class="signupbtn">Login</button>
            <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
            <input type="hidden" name="action" value="login" />
          </div>

        </div>
      </form>
    </div> 
  </div>
  
  
  <div id="id02" class="modal">
  <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">&times;</span>
  <form class="modal-content animate" action="controleur" method="post" accept-charset="UTF-8">
    <div class="imgcontainer">
      <img src="css/loup.png" alt="Avatar" class="avatar">
    </div>
    <div class="container">
      <label><b>Pseudo</b></label>
      <input type="text" placeholder="Entrez votre pseudo..." name="login" required>

      <label><b>Mot de passe</b></label>
      <input type="password" placeholder="Entrez votre mot de passe..." name="password" required>

      <div class="clearfix">
        <button type="submit" class="signupbtn">S'inscrire</button>
        <button type="button" onclick="document.getElementById('id02').style.display='none'" class="cancelbtn">Cancel</button>
        <input type="hidden" name="action" value="register" />
      </div>
    </div>
  </form>
</div>

  <div class="w3-row w3-padding-64" style = "margin-top: 20px">
    <div class="w3-twothird w3-container">
      <h1 class="colspec">Règles du jeu: </h1>
      <p>Vous déciderez de rejoindre le village de Thiercelieux...</p>
      <p>Mais ce village est différent de tous ceux que vous aurez pu connaître. En effet, certaines personnes sont des humains, tandis que d'autres sont des loup-garous.</p>
      <p>Les loup-garous sont des humains le jour et se transforment la nuit pour manger un villageois. Certaines sources affirment même que vous pourriez en devenir un dû à l'étrange ambiance qui s'y cache...</p>
      <p>Parviendrez-vous à défendre votre village de ces horribles bêtes à l'aide des autres villageois ? Ou bien réussirez-vous à tous les manger...</p>
    </div>
    
  </div>

</div>

 <script>
// Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>
 <script>
// Get the modal
var modal = document.getElementById('id02');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>

</body>
</html>
