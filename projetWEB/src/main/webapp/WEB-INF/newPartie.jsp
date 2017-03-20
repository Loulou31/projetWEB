<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nouvelle Partie</title>
    </head>
    <body>
        <h1>Creation d'une nouvelle partie</h1>
        <p> Veuillez saisir les parametres de la nouvelle partie </p>
        <form method="post" action="setGame" accept-charset="UTF-8">
        <ul>
          <li> Nombre de participants : 
            <SELECT name="joueurs" size="1">
                <OPTION>5 - 10
                <OPTION>10 - 15 
                <OPTION>15 - 20 
            </SELECT>
          </li>
          <li> Duree d'une journee : <input type="text" name="dayHour" value="14" size="2"/> h<input type="text" name="dayMin" value="00" size="2"/></li>
          <li> Duree d'une nuit : <input type="text" name="nightHour" value="10" size="2"/> h <input type="text" name="nightMin" value="00" size="2"/></li>
          <li> A quelle heure commencera la partie demain ? <input type="text" name="beginHour" value="8" size="2"/> h <input type="text" name="beginMinu" value="00" size="2"/></li>
          <li> Probabilite d'attribution des pouvoirs speciaux :  <input type="text" name="powerNum" value="0" size="2"/>/<input type="text" name="powerDenum" value="0" size="2"/> </li>
          <li> Proportion initiale des Loups-Garous :  <input type="text" name="werewolfNum" value="1" size="2"/>/<input type="text" name="werewolfDenum" value="3" size="2"/></li>
        </ul>
        <input type="submit" name="Creer une nouvelle partie" />
        <input type="hidden" name="action" value="newGame" />
      </form>
    </body>
</html>
