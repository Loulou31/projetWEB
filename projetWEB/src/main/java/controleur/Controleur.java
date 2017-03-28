package controleur;

import dao.DAOException;
import dao.MembreDAO;
import dao.PartieDAO;
import dao.VillageoisDAO;
import java.io.*;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.Membre;
import modele.Partie;
import modele.Temps;
import modele.Villageois;

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
     * Actions possibles en GET : afficher (correspond à l’absence du param),
     * getOuvrage.
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String view = request.getParameter("view");
        PartieDAO partieDAO = new PartieDAO(ds);
        //MembreDAO membreDAO = new MembreDAO(ds);
        try {
            if (action == null){
                actionAccueil(request, response);
            } else if (action.equals("index")){
                actionIndex(request, response);
            } else if (action.equals("choseGame")){
                actionChoseGame(request, response, partieDAO) ; 
            } else if (action.equals("newGame")){
                actionNewGame(request, response) ; 
            } else if (action.equals("getPartie")){
                actionGetPartie(request, response, partieDAO) ;
            } else if (action.equals("connexion")){
                actionLogin(request,response);
            } else if (action.equals("inscription")){
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            } else if (action.equals("deconnexion")){
                actionDeconnexion(request, response);
            } else if (action.equals("newDecision")){
                actionNewDecision(request, response) ; 
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }

    /**
     *
     * Affiche la page d’accueil.
     */
    
    private void actionAccueil(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        response.setIntHeader("Refresh",1);
        request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
    }
    
    private void actionIndex(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void actionLogin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
    
    private void actionDeconnexion(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException{
            HttpSession session = request.getSession();
            session.invalidate();
            request.getRequestDispatcher("/WEB-INF/logout.jsp").forward(request, response);
    }
    
    private void actionGetPartie(HttpServletRequest request,
            HttpServletResponse response,
            PartieDAO partieDAO) throws ServletException, IOException {
        Partie partie = partieDAO.getPartie(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("partie", partie);
        String view = request.getParameter("view");
        if (view.equals("rejoindre")) {
            actionAddPlayer(request, response, partieDAO) ; 
        }
    }

    private void actionChoseGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
        List<Partie> parties = partieDAO.getListeParties();
        request.setAttribute("parties", parties);
        HttpSession session = request.getSession();
        MembreDAO membreDAO = new MembreDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        if (membreDAO.memberHasPartie(pseudo)){
            VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
            Villageois villageois = villageoisDAO.getVillageois(pseudo);
            Partie partie = partieDAO.getPartie(villageois.getPartie());
            if (partie.enAttente(partieDAO)){
                actionWaitGame(request,response);
            }else{
                actionRejoindreSalleDiscussion(request,response,villageois);
            }
        }else{
            request.getRequestDispatcher("/WEB-INF/choseGame.jsp").forward(request, response);
        }
    }
    
    private void actionRejoindreSalleDiscussion(HttpServletRequest request,
            HttpServletResponse response,Villageois villageois)throws IOException, ServletException {
        Temps temps = new Temps();
        if (temps.estJour(villageois.getPartie())){
            request.getRequestDispatcher("/WEB-INF/placeDuVillage.jsp").forward(request, response);
        }else{
            if (villageois.getRole() == 1){
                request.getRequestDispatcher("/WEB-INF/repaire.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("/WEB-INF/nuit.jsp").forward(request, response);
            }
        }
    }
    
    private void actionNewGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/newPartie.jsp").forward(request, response);
    }

    private void actionAddPlayer(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException{
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ; 
        String login = request.getSession().getAttribute("membre").toString() ;
        int idPartie = Integer.parseInt(request.getParameter("id")) ; 
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie) ;
        villageoisDAO.addPlayer(login, idPartie) ;
        actionWaitGame(request, response);
    }
    
    private void actionWaitGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException{
        response.setIntHeader("Refresh",1);
        request.getRequestDispatcher("/WEB-INF/attenteDebutPartie.jsp").forward(request, response);
    }
    
    
    private void actionNewDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException{
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString() ; 
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ; 
        Villageois villageois = villageoisDAO.getVillageois(pseudo) ; 
        int idPartie = villageois.getPartie() ; 
        List<Villageois> villageoisList = villageoisDAO.getListVillageois(idPartie) ;
        request.setAttribute("villageoisList", villageoisList) ; 
        request.getRequestDispatcher("/WEB-INF/newDecision.jsp").forward(request, response);
    }
    
    
    
    
    
    
    
    /**
     * Actions possibles en POST : ajouter, supprimer, modifier. Une fois
     * l’action demandée effectuée, on retourne à la page d’accueil avec
     * actionAfficher(...)
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
            if (action.equals("login")) {
                actionConnexionMembre(request, response, membreDAO);
            }else if (action.equals("register")){
                actionAjoutMembre(request, response, membreDAO);
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
        if (membreDAO.idCorrect(request.getParameter("login"), request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute("membre", request.getParameter("login"));
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/WEB-INF/failLogin.jsp").forward(request, response);

        }

    }
    
    private void actionAjoutMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        if (!membreDAO.idCorrect(request.getParameter("pseudo"), request.getParameter("password"))){
            membreDAO.ajouterMembre(request.getParameter("login"), request.getParameter("password"));
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/WEB-INF/failRegister.jsp").forward(request, response);
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
        request.getRequestDispatcher("/WEB-INF/attenteDebutPartie.jsp").forward(request, response);
    }

}
