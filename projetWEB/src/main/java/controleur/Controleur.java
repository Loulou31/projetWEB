
package controleur;

import dao.DAOException;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

/**
 * Le contrôleur de l'application.
 */
@WebServlet(name = "Controleur", urlPatterns = {"/controleur"})
public class Controleur extends HttpServlet {

    @Resource(name = "jdbc/loupGarou")
    private DataSource ds;

    /* pages d’erreurs */
    private void invalidParameters(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/controleurErreur.jsp").forward(request, response);        
    }

    private void erreurBD(HttpServletRequest request,
                HttpServletResponse response, DAOException e)
            throws ServletException, IOException {
        request.setAttribute("erreurMessage", e.getMessage());
        request.getRequestDispatcher("/WEB-INF/bdErreur.jsp").forward(request, response);
    }
  
    /**
     * Actions possibles en GET : afficher (correspond à l’absence du param), getOuvrage.
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        actionAfficher(request, response);

    }

    /**
     * 
     * Affiche la page d’accueil avec la liste de tous les ouvrages. 
     */
    
    private void actionAfficher(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        /* On interroge la base de données pour obtenir la liste des ouvrages */
        /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
         * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
         * avant un forward, comme ici)
         * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
        /* Enfin on transfère la requête avec cet attribut supplémentaire vers la vue qui convient */
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


    
    /**
     * Actions possibles en POST : ajouter, supprimer, modifier.
     * Une fois l’action demandée effectuée, on retourne à la page
     * d’accueil avec actionAfficher(...)
     */

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        actionAfficher(request, response);

    }
    
    
}
