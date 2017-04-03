<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="fr">
    <head>
      <meta charset="UTF-8" />
      <link rel="stylesheet" type="text/css" href="css/test.css"/>	
      <title>Connexion</title>
    </head>
    <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Login</button>

    <div id="id01" class="modal">

      <form class="modal-content animate" action="controleur" method="post" accept-charset="UTF-8"">
        <div class="imgcontainer">
          <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
          <img src="css/img_avatar2.png" alt="Avatar" class="avatar">
        </div>

        <div class="container">
          <label><b>Pseudo</b></label>
          <input type="text" placeholder="Entrez votre pseudo..." name="login" required>

          <label><b>Mot de passe</b></label>
          <input type="password" placeholder="Entrez votre mot de passe..." name="password" required>

          <button type="submit">Connexion</button>
        </div>

        <div class="container" style="background-color:#f1f1f1">
          <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
        </div>
        <input type="hidden" name="action" value="login" />
      </form>
    </div>

<script>
// Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};
</script>
</html>
