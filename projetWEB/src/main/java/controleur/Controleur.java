package controleur;

import dao.*;
import java.io.*;
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
     * Actions possibles en GET : afficher (correspond à l’absence du param),
     * getOuvrage.
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        PartieDAO partieDAO = new PartieDAO(ds);
        System.out.println("ACTION : " + action);
        try {
            if (action == null || action.equals("accueil")) {
                actionAccueil(request, response);
            } else if (action.equals("inscription")) {
                request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
            } else if (action.equals("index")) {
                actionIndex(request, response);
            } else if (action.equals("connexion")) {
                actionLogin(request, response);
            } else if (action.equals("deconnexion")) {
                actionDeconnexion(request, response);
            } else if (action.equals("choseGame")) {
                actionChoseGame(request, response, partieDAO);
            } else if (action.equals("newGame")) {
                actionNewGame(request, response);
            } else if (action.equals("rejoindrePartie")) {
                actionRejoindrePartie(request, response, partieDAO);
            } else if (action.equals("quitteAttentePartie")) {
                quitteAttentePartie(request, response);
            } else if (action.equals("actualiseAttente")) {
                actionActualiseAttente(request, response);
            } else if (action.equals("debutPartie")) {
                request.setAttribute("idPartie", request.getParameter("id"));
                request.getRequestDispatcher("controleurPartie").forward(request, response);
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
        if (session.getAttribute("membre") == null) {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
        }
    }

    private void actionIndex(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }

    private void actionLogin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("membre") == null) {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
        }
    }

    private void actionDeconnexion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getRequestDispatcher("/WEB-INF/AvantPartie/logout.jsp").forward(request, response);
    }

    private void actionChoseGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {

        List<Partie> parties = partieDAO.getListePartiesNonEnCours();
        request.setAttribute("parties", parties);

        HttpSession session = request.getSession();
        MembreDAO membreDAO = new MembreDAO(ds);
        String pseudo = session.getAttribute("membre").toString();

        //si le joueur a précédemment quitté et qu'il est déjà en partie 
        //ne fonctionne pas encore où l'envoie t on ?
        if (membreDAO.membreEnJeu(pseudo)) {
            VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
            Villageois villageois = villageoisDAO.getVillageois(pseudo);
            Partie partie = partieDAO.getPartie(villageois.getPartie());

            request.setAttribute("action", "rejoindreJeu");
            request.getRequestDispatcher("controleurPartie").forward(request, response);

        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/choseGame.jsp").forward(request, response);
        }
    }

    private void actionNewGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);
    }

    private void actionRejoindrePartie(HttpServletRequest request,
            HttpServletResponse response,
            PartieDAO partieDAO) throws ServletException, IOException {

        int idPartie = Integer.parseInt(request.getParameter("id"));
        actionAddPlayer(request, response, partieDAO, idPartie);

    }

    //cretateur = 1 si arrivée ici par la voie créateur, 0 sinon
    private void actionAddPlayer(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO, int idPartie)
            throws IOException, ServletException {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);

        String login = request.getSession().getAttribute("membre").toString();
        villageoisDAO.addPlayer(login, idPartie);

        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);

        //pas de rasion que le joueur ait de partie si on supprime f5 int id = partieDAO.getIDPartieJoueur(login);
        //if (id == 0 || id == -1) {
        //    villageoisDAO.addPlayer(login, idPartie);
        //}
                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        actionWaitGame(request, response);
    }

    private void actionWaitGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        Temps temps = new Temps();
        Partie partie = (Partie) request.getAttribute("partie");

        int intDeb = partie.getHeureDebut();
        int nombreJoueurs = partie.getNbJoueurs();
        int nombreJoueursMin = partie.getNbJoueursMin();

        if (!temps.estApres(intDeb, temps.getTempsLong())) {
            if (nombreJoueursMin <= nombreJoueurs) {
                request.setAttribute("partiePrete", 1);
                request.getRequestDispatcher("/WEB-INF/AvantPartie/attenteDebutPartie.jsp").forward(request, response);
            } else {
                quitteAttentePartie(request, response);
            }
        } else {
                        System.out.println("dddddddddddddddddddddddddddddddddd");
            request.getRequestDispatcher("/WEB-INF/AvantPartie/attenteDebutPartie.jsp").forward(request, response);
        }
    }

    
    private void actionActualiseAttente(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
        
        int idPartie = Integer.parseInt(request.getParameter("idPartie"));
        PartieDAO partieDAO = new PartieDAO(ds);
        request.setAttribute("partie", partieDAO.getPartie(idPartie));

        actionWaitGame(request, response);
    }
    

    private void quitteAttentePartie(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        PartieDAO partieDAO = new PartieDAO(ds);

        //si dernier joueur de partie : on détruit tout
        int idPartie = partieDAO.getIDPartieJoueur(pseudo);
        Partie partie = partieDAO.getPartie(idPartie);

        if (partie.getNbJoueurs() == 1) {
            partieDAO.supprimerPartie(idPartie);
        }

        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        villageoisDAO.supprimerVillageois(pseudo);

        request.getRequestDispatcher("/WEB-INF/AvantPartie/index.jsp").forward(request, response);
    }

    /**
     * Actions possibles en POST
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

    private void actionAjoutMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        if (membreDAO.idCorrectInscription(request.getParameter("login"))) {
            membreDAO.ajouterMembre(request.getParameter("login"), request.getParameter("password"));
            request.getRequestDispatcher("/WEB-INF/AvantPartie/connexion.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/AvantPartie/failRegister.jsp").forward(request, response);
        }
    }


    //finalement on ne gère pas le f5
    private void actionAddGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
                    
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);

        Temps temps = new Temps();
        int heureDeb = temps.calToInt(Integer.parseInt(request.getParameter("beginHour")), Integer.parseInt(request.getParameter("beginMin")));


        //si f5, il y a déjà une partie donc on ne la recréé pas
        /*if (partieDAO.getIDPartieJoueur(pseudo) != -1) {
            int idPartie = partieDAO.getIDPartieJoueur(pseudo);
            Partie partie = partieDAO.getPartie(idPartie);
            request.setAttribute("partie", partie);

            int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
            request.setAttribute("nombreJoueurs", nombreJoueurs);

            List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
            request.setAttribute("listeVillageois", listeVillageois);

            actionWaitGame(request, response);
        
        //après on vérifie que le nombre de joueurs minimum est plus petit que le nombre de j max
        } else*/ if (Integer.parseInt(request.getParameter("JMin")) > Integer.parseInt(request.getParameter("JMax"))) {
            request.setAttribute("erreurNbJoueurs", 1);
            request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);
        } else if (!temps.estApres(heureDeb, temps.getTempsLong())) {
            request.setAttribute("erreurHeure", 1);
            request.getRequestDispatcher("/WEB-INF/AvantPartie/newPartie.jsp").forward(request, response);

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
            

            actionAddPlayer(request,response, partieDAO, idPartie);
        }
    }
}
