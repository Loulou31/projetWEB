package controleur;


import dao.* ;
import java.io.*;
import static java.lang.Math.ceil;
import java.util.List;
import java.util.Random;
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
            } else if (action.equals("newDecision")) {
                actionNewDecision(request, response);
            } else if (action.equals("addDecision")) {
                actionAddDecision(request, response);
            } else if (action.equals("addVote")){
                actionAddVote(request, response) ; 
            } else if (action.equals("debutPartie")) {
                actionDebutPartie(request, response);
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
                //request.setAttribute("messages", messageDAO.getListeMessagesSalleDiscussion());
                actionRejoindreSalleDiscussion(request,response,villageois);
            }
        }else{
            request.getRequestDispatcher("/WEB-INF/choseGame.jsp").forward(request, response);
        }
    }
    
    private void actionDebutPartie(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        int idPartie = Integer.parseInt(request.getParameter("id")) ; 
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueurs = partieDAO.getNbJoueurs(idPartie); 
        float proportionLoup = partie.getProportionLG(); 
        int nbLoup; 
        if (proportionLoup == 0) {
            nbLoup = 1; 
        } else {
            nbLoup = (int) ceil(nbJoueurs * proportionLoup);
        }

        /* selection des loups */
        int nbLoupCourant = 0;
        while (nbLoupCourant != nbLoup) {
            List<Villageois> villageois = villageoisDAO.getListHumains(idPartie);
            int nbHumain = villageois.size();          
            int valeur = generateurAleatoire(-1, nbHumain);
            Villageois nouveauLoup = villageois.get(valeur);
            villageoisDAO.updatePlayerRole(nouveauLoup.getPseudo(), 1);
            nbLoupCourant++; 
        }
        
        /* attribution des pouvoirs */
        float probaPouvoir = partie.getProbaPouvoir(); 
        if (probaPouvoir != 0.0) {
            int contamination = computeX(probaPouvoir);
            int insomnie = computeX(probaPouvoir); 
            int voyance = computeX(probaPouvoir); 
            int spiritisme = computeX(probaPouvoir); 
            
            /* attribution du pouvoir contamination à un loup */
            if (contamination != 0) {
                List<Villageois> loups = villageoisDAO.getListLoupsSansPouvoir(idPartie);
                int valContam = generateurAleatoire(-1, loups.size());
                villageoisDAO.updatePlayerStatus(loups.get(valContam).getPseudo(), "contamination");
            }
            /* attribution du pouvoir insomnie à un humain */
            if (insomnie != 0) {
                List<Villageois> humains = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                int valInsomn = generateurAleatoire(-1, humains.size());
                villageoisDAO.updatePlayerStatus(humains.get(valInsomn).getPseudo(), "insomnie");
            }
            /* attribution du pouvoir voyance à un villageois */
            if (voyance != 0) {
                List<Villageois> villageois = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                int valVoyance = generateurAleatoire(-1, villageois.size());
                villageoisDAO.updatePlayerStatus(villageois.get(valVoyance).getPseudo(), "voyance");
            }
            /* attribution du pouvoir spiritisme à un villageois */
            if (spiritisme != 0) {
                List<Villageois> villageois = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                int valSpirit = generateurAleatoire(-1, villageois.size());
                villageoisDAO.updatePlayerStatus(villageois.get(valSpirit).getPseudo(), "spriritisme");
            }
        }
        request.setAttribute("partie", partie);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        request.setAttribute("role", villageoisDAO.getVillageois(pseudo).getRoleString());
        request.setAttribute("nombreLoup", nbLoup);
        request.setAttribute("proba", probaPouvoir);
        request.getRequestDispatcher("/WEB-INF/role.jsp").forward(request, response);
    }

    private void actionRejoindreSalleDiscussion(HttpServletRequest request,
            HttpServletResponse response,Villageois villageois)throws IOException, ServletException {
        Temps temps = new Temps();
        MessageDAO messageDAO = new MessageDAO(ds);   
        int idPartie = villageois.getPartie() ; 
        DecisionDAO decisionDAO = null;
        if (temps.estJour(idPartie)){
            List<Message> messages = messageDAO.getListeMessagesSalleDiscussion(idPartie);
            request.setAttribute("messages", messages);
            decisionDAO = new DecisionDAO(ds) ; 
            List<Decision> decisions = decisionDAO.getListDecisionHumains(idPartie) ; 
            request.setAttribute("decisions", decisions) ;
            PartieDAO partieDAO = new PartieDAO(ds);
            request.setAttribute("nbJoueurs", partieDAO.getNbJoueurs(idPartie));
            request.getRequestDispatcher("/WEB-INF/placeDuVillage.jsp").forward(request, response);
        }else{
            if (villageois.getRole() == 1){
                List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie) ; 
                request.setAttribute("decisions", decisions) ;
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

    //cretateur = 1 si arrivée ici par la voie créateur, 0 sinon
    private void actionAddPlayer(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException{
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ; 
        String login = request.getSession().getAttribute("membre").toString() ;
        int idPartie = Integer.parseInt(request.getParameter("id")) ; 
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie) ;
        
        System.out.println("1111");
        int id = partieDAO.getIDPartieJoueur(login);
        if (id == 0 || id == -1)
            villageoisDAO.addPlayer(login, idPartie) ;
        System.out.println("2222");
        int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
        request.setAttribute("nombreJoueurs", nombreJoueurs);

        List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
        request.setAttribute("listeVillageois", listeVillageois);
            
        actionWaitGame(request, response);
    }
    
    
    
    private void actionWaitGame(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException{
        //response.setIntHeader("Refresh",1);
        
        Temps temps = new Temps();
        Partie partie = (Partie) request.getAttribute("partie");
        System.out.println("11");
        int intDeb = partie.getHeureDebut();
        System.out.println("22");
        if (!temps.estApres(intDeb, temps.getTempsLong())){
            System.out.println("33");
            request.getRequestDispatcher("/WEB-INF/placeDuVillage.jsp").forward(request, response);
        }else{
            System.out.println("44");
            request.getRequestDispatcher("/WEB-INF/attenteDebutPartie.jsp").forward(request, response);
        }
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
        request.getRequestDispatcher("/WEB-INF/decision.jsp").forward(request, response);
    }
    
    
    private void actionAddDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String pseudoJoueur = session.getAttribute("membre").toString() ; 
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ; 
        DecisionDAO decisionDAO = new DecisionDAO(ds) ; 
        Villageois villageois = villageoisDAO.getVillageois(pseudoJoueur) ; 
        int idPartie = villageois.getPartie() ; 
        decisionDAO.ajouteDecisionHumain(pseudoJoueur, idPartie, request.getParameter("decision")) ; 
        actionRejoindreSalleDiscussion(request, response, villageois) ; 
    }
    
    
    private void actionAddVote(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        
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
        PartieDAO partieDAO = new PartieDAO(ds);
        
        try {
            if (action.equals("login")) {
                actionConnexionMembre(request, response, membreDAO);
            } else if (action.equals("register")){
                actionAjoutMembre(request, response, membreDAO);
            } else if (action.equals("addGame")){
                actionAddGame(request, response, partieDAO) ; 
            } else if(action.equals("ajouterUnMessage")){
                actionAddMessage(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }

    private void actionAddMessage(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException{
        MessageDAO messageDAO = new MessageDAO(ds);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ;
        Villageois villageois = villageoisDAO.getVillageois(pseudo) ; 
        int idPartie = villageois.getPartie() ;
        if (request.getParameter("contenu").toString().equals("")){
            request.getRequestDispatcher("/WEB-INF/messageVide.jsp").forward(request, response);
        }else{
            messageDAO.ajouteMessageSalleDiscussion(pseudo, request.getParameter("contenu").toString(), idPartie);
            actionRejoindreSalleDiscussion(request, response, villageois);
        }
    }
    
    private void actionConnexionMembre(HttpServletRequest request,
            HttpServletResponse response, MembreDAO membreDAO)
            throws IOException, ServletException {
        if (membreDAO.idCorrectConnexion(request.getParameter("login"), request.getParameter("password"))) {
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
        if (membreDAO.idCorrectInscription(request.getParameter("login"))){
            membreDAO.ajouterMembre(request.getParameter("login"), request.getParameter("password"));
            request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("/WEB-INF/failRegister.jsp").forward(request, response);
        }
    }
    
    private void actionAddGame(HttpServletRequest request,
            HttpServletResponse response, PartieDAO partieDAO)
            throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString() ;
        
        Temps temps = new Temps();
        int heureDeb = temps.calToInt(Integer.parseInt(request.getParameter("beginHour")),Integer.parseInt(request.getParameter("beginMin")));
        
        //si f5, il y a déjà une partie donc on ne la recréé pas
        if(partieDAO.getIDPartieCreateur(pseudo) != -1){
            System.out.println("1");
            int idPartie = partieDAO.getIDPartieJoueur(pseudo);
            System.out.println("2");
            Partie partie = partieDAO.getPartie(idPartie);
            System.out.println("3");
            request.setAttribute("partie", partie);
            System.out.println("4");
            
            VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
            System.out.println("5");
            int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
            System.out.println("6");
            request.setAttribute("nombreJoueurs", nombreJoueurs);
            System.out.println("7");
            
            List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
            System.out.println("8");
            request.setAttribute("listeVillageois", listeVillageois);
            System.out.println("9");
            
            actionWaitGame(request, response);
        }
            
        //attention code redondant à factoriser !
        else if (temps.estApres(heureDeb, temps.getTempsLong())){
 
            partieDAO.ajouterPartie(Integer.parseInt(request.getParameter("JMin")), 
                                    Integer.parseInt(request.getParameter("JMax")), 
                                    pseudo, 
                                    Integer.parseInt(request.getParameter("day")),
                                    Integer.parseInt(request.getParameter("night")),
                                    heureDeb,
                                    Float.parseFloat(request.getParameter("power")),
                                    Float.parseFloat(request.getParameter("werewolf")));
            System.out.println("90");
            int idPartie = partieDAO.getIDPartieCreateur(pseudo);
            System.out.println("91");
            System.out.println(idPartie);
            System.out.println(pseudo);
            Partie partie = partieDAO.getPartie(idPartie);
            System.out.println("92");
            request.setAttribute("partie", partie);
System.out.println("93");

            VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
System.out.println("94");
            villageoisDAO.addPlayer(pseudo, idPartie);

            System.out.println("95");
            int nombreJoueurs = villageoisDAO.nombreJoueursPartie(idPartie);
            System.out.println("96");
            request.setAttribute("nombreJoueurs", nombreJoueurs);
            System.out.println("97");

            List<Villageois> listeVillageois = villageoisDAO.getListVillageois(idPartie);
            System.out.println("98");
            request.setAttribute("listeVillageois", listeVillageois);
            System.out.println("99");

            actionWaitGame(request, response);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/failNewGame.jsp").forward(request, response);
        }
    }

     public int computeX(float proba) {
        int i = new Random().nextInt(10);
        if (i < proba*10) {
            return 1;
        } else {
            return 0;
        }
    }

    public int generateurAleatoire(int valeurMin, int valeurMax) {
        Random r = new Random();
        int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
        return valeur;
    }
}
