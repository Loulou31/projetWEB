package controleur;

import dao.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import modele.*;

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
     * Actions possibles en GET : 
     * - Inscription
     * - Connexion
     * - Deconnexion
     * - Jouer (choseGame)
     * - Rejoindre une partie 
     * - Créer une partie (newGame)
     * - Quitter l'attente d'une partie
     * - Quitter une partie
     * - Actualiser l'attente d'une partie
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        PartieDAO partieDAO = new PartieDAO(ds);
        try {
            if (action == null || action.equals("accueil")) {
                actionAccueil(request, response);
            } else if (action.equals("inscription")) {
                request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
            } else if (action.equals("connexion")) {
                actionLogin(request, response);
            } else if (action.equals("deconnexion")) {
                actionDeconnexion(request, response);
            } else if (action.equals("index")) {
                actionIndex(request, response);
            } else if (action.equals("choseGame")) {
                actionChoseGame(request, response, partieDAO);
            } else if (action.equals("newGame")) {
                actionNewGame(request, response);
            } else if (action.equals("getPartie")) {
                actionGetPartie(request, response, partieDAO);
            } else if (action.equals("debutPartie")) {
                request.getRequestDispatcher("controleurPartie").forward(request, response);
            } else if (action.equals("quitteAttentePartie")){
                quitteAttentePartie(request, response);
            } else if (action.equals("quittePartie")){
                quittePartie(request, response) ; 
            } else if (action.equals("rejoindreJeu")) {
                request.setAttribute("action", action);
                request.getRequestDispatcher("/controleurPartie").forward(request, response);
            } else if (action.equals("actualiseAttente")){
                actionActualiseAttente(request, response) ;
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

        HttpSession session = request.getSession();
        if(session.getAttribute("membre") == null)
            request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
        else{
            request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
        }
    }
    
    /**
     * @brief Quitte une partie et supprime les tables correspondantes dans 
     * la base de données
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void quittePartie(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        PartieDAO partieDAO = new PartieDAO(ds);
        MessageDAO messageDAO = new MessageDAO(ds) ; 
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        
        //si dernier joueur de partie : on détruit tout
        int idPartie = partieDAO.getIDPartieJoueur(pseudo);
        if (partieDAO.getNbJoueurs(idPartie) == 1) {
            villageoisDAO.supprimerVillageois(pseudo);
            // Supression des décisions
            DecisionDAO decisionDAO = new DecisionDAO(ds); 
            decisionDAO.supprimerToutesDecisionsJour(idPartie);
            decisionDAO.supprimerToutesDecisionsNuit(idPartie);
            partieDAO.supprimerPartie(idPartie);
        // Si je suis le 1er à quitter la partie
        } else if (partieDAO.getNbJoueurs(idPartie) == partieDAO.getNbJoueurs(idPartie)) {
            messageDAO.supprimerTousMessages(idPartie);
            villageoisDAO.supprimerVillageois(pseudo);
        } else {
         villageoisDAO.supprimerVillageois(pseudo);
        }
        request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }
    
    
    /**
     * @brief Quitte l'attente d'une partie s'il n'y a pas assez de joueurs
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void quitteAttentePartie(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);

        //si dernier joueur de partie : on détruit tout
        int idPartie = partieDAO.getIDPartieJoueur(pseudo);
        if (partieDAO.getNbJoueurs(idPartie) == 1) {
            partieDAO.supprimerPartie(idPartie);
        } 
        villageoisDAO.supprimerVillageois(pseudo);
        // Retour à la page intermédiaire
        request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }
    

    /**
     * @brief Permet de rejoindre la page intermédiaire
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void actionIndex(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }

    /**
     * @brief Permet de se connecter
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void actionLogin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("membre") == null)
            request.getRequestDispatcher("/WEB-INF/AvantPartie/login.jsp").forward(request, response);
        else
            request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }

    /**
     * @brief Permet de se déconnecter
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void actionDeconnexion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/WEB-INF/AvantPartie/logout.jsp").forward(request, response);
    }

    /**
     * @brief Rejoint une partie
     * @param request
     * @param response
     * @param partieDAO
     * @throws ServletException
     * @throws IOException 
     */
    private void actionGetPartie(HttpServletRequest request,
            HttpServletResponse response,
            PartieDAO partieDAO) throws ServletException, IOException {
        Partie partie = partieDAO.getPartie(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("partie", partie);
        String view = request.getParameter("view");
        if (view.equals("rejoindre")) {
            actionAddPlayer(request, response, partieDAO);
        }
    }

    /**
     * @brief Afficher la liste des parties
     * @param request
     * @param response
     * @param partieDAO
     * @throws IOException
     * @throws ServletException 
     */
    private void actionChoseGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
        Temps temps = new Temps(); 
        // Récupérer toutes les parties, même celles en cours ou terminées
        List<Partie> parties = partieDAO.getListeParties();
        ArrayList<Partie> partiesAffiche = new ArrayList() ; 
        Iterator<Partie> i = parties.iterator() ; 
        // Afficher seulement les parties accessibles
        while (i.hasNext()){
            Partie p = i.next() ; 
            int id = p.getIdPartie() ; 
            int nbJoueurs = partieDAO.getNbJoueurs(id) ; 
            if (!p.complet(partieDAO) || !(nbJoueurs >= p.getNbJoueursMin() && p.getHeureDebut() >= temps.getTempsInt())){
                partiesAffiche.add(p) ; 
            }
        }
        request.setAttribute("parties", partiesAffiche);
        HttpSession session = request.getSession();
        MembreDAO membreDAO = new MembreDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        // Si joueur a déjà une partie, il est redirigé directement sur son jeu
        if (membreDAO.memberHasPartie(pseudo)) {
            String action = "rejoindreJeu" ; 
            request.setAttribute("action", action);
            request.getRequestDispatcher("/controleurPartie").forward(request, response);
        // Sinon il choisi une partie
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/choseGame.jsp").forward(request, response);
        }
    }

       
    /**
     * @brief Redirige vers la page pour créer une partie
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    private void actionNewGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);
    }

    /**
     * @brief Ajoute un joueur à une partie
     * @param request
     * @param response
     * @param partieDAO
     * @throws IOException
     * @throws ServletException 
     */
    private void actionAddPlayer(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        String login = request.getSession().getAttribute("membre").toString();
        int idPartie = Integer.parseInt(request.getParameter("id"));
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);

        int id = partieDAO.getIDPartieJoueur(login);
        if (id == 0 || id == -1) {
            villageoisDAO.addPlayer(login, idPartie);
        }

        int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
        request.setAttribute("nombreJoueurs", nombreJoueurs);

        List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
        request.setAttribute("listeVillageois", listeVillageois);
        
        // Redirection vers la salle d'attente
        actionWaitGame(request, response);
    }

    private void actionWaitGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        Temps temps = new Temps();
//        PartieDAO partieDAO = new PartieDAO(ds);
//        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
//        HttpSession session = request.getSession();
//        Villageois villageois = villageoisDAO.getVillageois(session.getAttribute("membre").toString());
        Partie partie = (Partie) request.getAttribute("partie");
//        Partie partie = partieDAO.getPartie(villageois.getPartie());
        int intDeb = partie.getHeureDebut();
//        int nombreJoueurs = partie.getNbJoueurs(partie.getIdPartie());
        int nombreJoueurs = (int)request.getAttribute("nombreJoueurs");
        int nombreJoueursMin = partie.getNbJoueursMin();
        
        if (!temps.estApres(intDeb, temps.getTempsLong())) {
            if (nombreJoueursMin <= nombreJoueurs) {
                request.setAttribute("partiePrete", 1);
                request.getRequestDispatcher("/WEB-INF/AvantPartie/attenteDebutPartie.jsp").forward(request, response);
            } else {
                quitteAttentePartie(request, response);
            }
        } else {
                request.getRequestDispatcher("/WEB-INF/AvantPartie/attenteDebutPartie.jsp").forward(request, response);
        }
    }


    /**
     * @brief Actualise la salle d'attente
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    private void actionActualiseAttente(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        
        int idPartie = Integer.parseInt(request.getParameter("idPartie"));
        PartieDAO partieDAO = new PartieDAO(ds);
        request.setAttribute("partie", partieDAO.getPartie(idPartie));

        actionWaitGame(request, response);
    }
   

    

    /**
     * Actions possibles en POST :
     * - Inscription
     * - Connexion
     * - Ajouter une partie après création
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
        PartieDAO partieDAO = new PartieDAO(ds);

        try {
            if (action.equals("login")) {
                actionConnexionMembre(request, response, membreDAO);
            } else if (action.equals("register")) {
                actionAjoutMembre(request, response, membreDAO);
            } else if (action.equals("addGame")) {
                actionAddGame(request, response, partieDAO);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * @brief Connexion
     * @param request
     * @param response
     * @param membreDAO
     * @throws IOException
     * @throws ServletException 
     */
    private void actionConnexionMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        if (membreDAO.idCorrectConnexion(request.getParameter("login"), request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute("membre", request.getParameter("login"));
            request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/failLogin.jsp").forward(request, response);
        }
    }

    /**
     * @brief Ajout d'un membre après inscription
     * @param request
     * @param response
     * @param membreDAO
     * @throws IOException
     * @throws ServletException 
     */
    private void actionAjoutMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        // Vérification de l'unicité du login
        if (membreDAO.idCorrectInscription(request.getParameter("login"))) {
            membreDAO.ajouterMembre(request.getParameter("login"), request.getParameter("password"));
            request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/failRegister.jsp").forward(request, response);
        }
    }

    /**
     * @brief Ajout d'une partie après création
     * @param request
     * @param response
     * @param partieDAO
     * @throws IOException
     * @throws ServletException 
     */
    private void actionAddGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);

        Temps temps = new Temps();
        int heureDeb = temps.calToInt(Integer.parseInt(request.getParameter("beginHour")), Integer.parseInt(request.getParameter("beginMin")));

        //si f5, il y a déjà une partie donc on ne la recréé pas
        if (partieDAO.getIDPartieJoueur(pseudo) != -1) {
            int idPartie = partieDAO.getIDPartieJoueur(pseudo);
            Partie partie = partieDAO.getPartie(idPartie);
            request.setAttribute("partie", partie);

            int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
            request.setAttribute("nombreJoueurs", nombreJoueurs);

            List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
            request.setAttribute("listeVillageois", listeVillageois);

            actionWaitGame(request, response);
        
        // Vérifie que nbJoueursMin <= nbJoueursMax
        } else if (Integer.parseInt(request.getParameter("JMin")) > Integer.parseInt(request.getParameter("JMax"))) {
            request.setAttribute("erreurNbJoueurs", 1);
            request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);
        } else if (!temps.estApres(heureDeb, temps.getTempsLong())) {
            request.setAttribute("erreurHeure", 1);
            request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);
        // Création de la partie avec les paramètres rentrés par l'utilisateur
        } else {

            int idPartie = partieDAO.getIdDispo();
            int dureeDay = temps.dureeToInt(Integer.parseInt(request.getParameter("dayHour")), Integer.parseInt(request.getParameter("dayMin")));
            int dureeNight = temps.dureeToInt(Integer.parseInt(request.getParameter("nightHour")), Integer.parseInt(request.getParameter("nightMin")));
            partieDAO.ajouterPartie(idPartie,
                    Integer.parseInt(request.getParameter("JMin")),
                    Integer.parseInt(request.getParameter("JMax")),
                    dureeDay,
                    dureeNight,
                    heureDeb,
                    (float) Integer.parseInt(request.getParameter("power")) / (float) 100,
                    (float) Integer.parseInt(request.getParameter("werewolf")) / (float) 100);

            Partie partie = partieDAO.getPartie(idPartie);
            request.setAttribute("partie", partie);

            villageoisDAO.addPlayer(pseudo, idPartie);

            int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
            request.setAttribute("nombreJoueurs", nombreJoueurs);

            List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
            request.setAttribute("listeVillageois", listeVillageois);
            
            // Rejoindre la salle d'attente
            actionWaitGame(request, response);
        }
    }
}
