
package controleur;

import dao.DAOException;
import dao.MembreDAO;
import dao.PartieDAO;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.Partie;


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
        PartieDAO partieDAO = new PartieDAO(ds);
        //MembreDAO membreDAO = new MembreDAO(ds);
        try {
            if (action == null){
                actionAfficher(request, response) ; 
            } else if (action.equals("choseGame")){
                actionChoseGame(request, response, partieDAO) ; 
            } else if (action.equals("newGame")){
                actionNewGame(request, response) ; 
            } else if (action.equals("getPartie")){
                actionGetPartie(request, response, partieDAO) ;
            } else if (action.equals("connexion")){
                actionLogin(request,response);
            } else {
                invalidParameters(request, response) ; 
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        

    }

    /**
     * 
     * Affiche la page d’accueil. 
     */
    
    private void actionAfficher(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        /* On interroge la base de données pour obtenir la liste des ouvrages */
        /* On ajoute cette liste à la requête en tant qu’attribut afin de la transférer à la vue
         * Rem. : ne pas confondre attribut (= objet ajouté à la requête par le programme
         * avant un forward, comme ici)
         * et paramètre (= chaîne représentant des données de formulaire envoyées par le client) */
        /* Enfin on transfère la requête avec cet attribut supplémentaire vers la vue qui convient */
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void actionLogin(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
    
    
    private void actionGetPartie(HttpServletRequest request, 
            HttpServletResponse response, 
            PartieDAO partieDAO) throws ServletException, IOException {
        Partie partie = partieDAO.getPartie(Integer.parseInt(request.getParameter("id"))) ; 
        request.setAttribute("partie", partie);
        String action = request.getParameter("view") ; 
        if (action.equals("rejoindre")){
            request.getRequestDispatcher("rejoindre.jsp").forward(request, response);
        }
    }
    
    private void actionChoseGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
                throws IOException, ServletException {
        List<Partie> parties = partieDAO.getListeParties();
        request.setAttribute("parties", parties);
        request.getRequestDispatcher("/WEB-INF/choseGame.jsp").forward(request, response);
    }
    
    
    private void actionNewGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/newPartie.jsp").forward(request, response);
    }
    
    /**
     * Actions possibles en POST : ajouter, supprimer, modifier.
     * Une fois l’action demandée effectuée, on retourne à la page
     * d’accueil avec actionAfficher(...)
     */

    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
         request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
        MembreDAO membreDAO = new MembreDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds) ; 
        try {
            if (action.equals("login")){
                actionConnexionMembre(request, response, membreDAO);
            } else if (action.equals("addGame")){
                actionAddGame(request, response, partieDAO) ; 
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
        

    }


    private void actionConnexionMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        if (membreDAO.idCorrect(request.getParameter("login"), request.getParameter("password"))){
            HttpSession session = request.getSession();
            session.setAttribute("utilisateur", request.getParameter("login"));
        }
        
    }
    
    
    private void actionAddGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
        partieDAO.ajouterPartie(Integer.parseInt(request.getParameter("JMin")), 
                                Integer.parseInt(request.getParameter("JMax")), 
                                "louise", 
                                Integer.parseInt(request.getParameter("day")),
                                Integer.parseInt(request.getParameter("night")),
                                Integer.parseInt(request.getParameter("begin")),
                                Float.parseFloat(request.getParameter("power")),
                                Float.parseFloat(request.getParameter("werewolf")));
    }
    
    
}
