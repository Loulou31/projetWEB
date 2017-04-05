/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import dao.* ; 
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.ceil;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import modele.* ; 

/**
 *
 * @author louise
 */


@WebServlet(name = "ControleurPartie", urlPatterns = {"/controleurPartie"})
public class ControleurPartie extends HttpServlet {

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
     * Actions possibles en GET 
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String action2 = response.getHeader("action") ; 
        System.out.println("ACTION : " + action) ; 
        try {
            if (action == null && action2 == null) {
                request.getRequestDispatcher("controleur").forward(request, response);
            } else if (action.equals("newDecision")) {
                actionNewDecision(request, response);
            } else if (action.equals("addDecision")) {
                actionAddDecision(request, response);
            } else if (action.equals("addVote")) {
                actionAddVote(request, response);
            } else if (action.equals("debutPartie")) {
                actionDebutPartie(request, response);
            } else if (action.equals("rejoindreJeu")) {
                actionRejoindreSalleDiscussion(request, response);
            } else {
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }
    
    
    
    private void actionDebutPartie(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        int idPartie = Integer.parseInt(request.getParameter("id"));
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        List<Villageois> testVillageois = villageoisDAO.getListVillageois(idPartie);
        List<Villageois> villageois = villageoisDAO.getListJoueurs(idPartie);
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueurs = partieDAO.getNbJoueurs(idPartie);
        float proportionLoup = partie.getProportionLG();
        int nbLoup;
        if (proportionLoup == 0) {
            nbLoup = 1;
        } else {
            nbLoup = (int) ceil(nbJoueurs * proportionLoup);
        }
        float probaPouvoir = partie.getProbaPouvoir();
        
        /* si les roles ont deja été attribué: ne rien faire*/
        if (testVillageois.get(0).getRole() == -1) {
            /* selection des loups */
            int nbLoupCourant = 0;
            while (nbLoupCourant != nbLoup) {
                villageois = villageoisDAO.getListJoueurs(idPartie);
                System.out.println("sortie getListVillageois ds actionDebPartie");
                int nbHumain = villageois.size();
                int valeur = generateurAleatoire(-1, nbHumain);
                while (valeur == -1 || valeur == nbHumain) {
                    valeur = generateurAleatoire(-1, nbHumain);
                }
                System.out.println("valeur pr avoir les loups");
                System.out.println(valeur);
                Villageois nouveauLoup = villageois.get(valeur);
                System.out.println("apres villageois.get ds actionDebPartie");
                villageoisDAO.updatePlayerRole(nouveauLoup.getPseudo(), 1);
                nbLoupCourant++;
            }

            /* Les autres joueurs deviennent des villageois */
            villageois = villageoisDAO.getListJoueurs(idPartie);
            for (Villageois v : villageois) {
                villageoisDAO.updatePlayerRole(v.getPseudo(), 0);
            }
            /* attribution des pouvoirs */

            if (probaPouvoir != 0.0) {
                int contamination = computeX(probaPouvoir);
                int insomnie = computeX(probaPouvoir);
                int voyance = computeX(probaPouvoir);
                int spiritisme = computeX(probaPouvoir);
                System.out.println(contamination);
                System.out.println(insomnie);
                System.out.println(voyance);
                System.out.println(spiritisme);
                /* attribution du pouvoir contamination à un loup */
                if (contamination != 0) {
                    List<Villageois> loups = villageoisDAO.getListLoupsSansPouvoir(idPartie);
                    if (loups.size() > 0) {
                        System.out.println("apres getListLoupsSansPouvoirs ds actionDebPartie");
                        int valContam = generateurAleatoire(-1, loups.size());
                        while (valContam == -1 || valContam == loups.size()) {
                            valContam = generateurAleatoire(-1, loups.size());
                        }
                        villageoisDAO.updatePlayerStatus(loups.get(valContam).getPseudo(), "contamination");
                    } else {
                        System.out.println("pas assez de loups pr pouvoir contam");
                    }
                }
                /* attribution du pouvoir insomnie à un humain */
                if (insomnie != 0) {
                    List<Villageois> humains = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                    if (humains.size() > 0) {
                        int valInsomn = generateurAleatoire(-1, humains.size());
                        while (valInsomn == -1 || valInsomn == humains.size()) {
                            valInsomn = generateurAleatoire(-1, humains.size());
                        }
                        System.out.println("valeur pr donner pouvoir insomnie humain");
                        System.out.println(valInsomn);
                        villageoisDAO.updatePlayerStatus(humains.get(valInsomn).getPseudo(), "insomnie");
                    } else {
                        System.out.println("pas assez d'humains pr pouvoir insom");
                    }
                }
                /* attribution du pouvoir voyance à un villageois */
                if (voyance != 0) {
                    villageois = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                    if (villageois.size() > 0) {
                        int valVoyance = generateurAleatoire(-1, villageois.size());
                        while (valVoyance == -1 || valVoyance == villageois.size()) {
                            valVoyance = generateurAleatoire(-1, villageois.size());
                        }
                        System.out.println("valeur pr donner pouvoir voyance humain");
                        System.out.println(valVoyance);
                        villageoisDAO.updatePlayerStatus(villageois.get(valVoyance).getPseudo(), "voyance");
                    } else {
                        System.out.println("pas assez de villageois pr pouvoir voyance");
                    }
                }
                /* attribution du pouvoir spiritisme à un villageois */
                if (spiritisme != 0) {
                    villageois = villageoisDAO.getListHumainsSansPouvoir(idPartie);
                    if (villageois.size() > 0) {
                        int valSpirit = generateurAleatoire(-1, villageois.size());
                        while (valSpirit == -1 || valSpirit == villageois.size()) {
                            valSpirit = generateurAleatoire(-1, villageois.size());
                        }
                        villageoisDAO.updatePlayerStatus(villageois.get(valSpirit).getPseudo(), "spriritisme");
                    } else {
                        System.out.println("pas assez de villageois pr pouvoir spirit");
                    }
                }
            }
        }
        request.setAttribute("partie", partie);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        request.setAttribute("role", villageoisDAO.getVillageois(pseudo).getRoleString());
        request.setAttribute("nombreLoup", nbLoup);
        request.setAttribute("proba", probaPouvoir);
        request.setAttribute("pouvoir", villageoisDAO.getVillageois(pseudo).getPouvoir());
        request.getRequestDispatcher("/WEB-INF/Partie/role.jsp").forward(request, response);
    }

    
    
    private void actionRejoindreSalleDiscussion(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        Temps temps = new Temps();
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds) ; 
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString() ;
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        int idPartie = villageois.getPartie();
        if (temps.estJour(idPartie)) {
            List<Message> messagesVillage = messageDAO.getListeMessagesSalleDiscussion(idPartie);
            request.setAttribute("messages", messagesVillage);
            List<Decision> decisions = decisionDAO.getListDecisionHumains(idPartie);
            request.setAttribute("decisions", decisions);
            request.setAttribute("nbJoueurs", villageoisDAO.getListVillageoisVivants(idPartie).size());
            if (!partieDAO.decisionHumainRatifie(idPartie)) {
                request.getRequestDispatcher("/WEB-INF/Partie/placeDuVillage.jsp").forward(request, response);
                //request.getRequestDispatcher("/WEB-INF/Partie/repaire.jsp").forward(request, response);
                //goToVoyance(request, response, idPartie, villageoisDAO) ; 
            } else {
                System.out.println("ll");
                request.getRequestDispatcher("/WEB-INF/Partie/placeRatifie.jsp").forward(request, response);
            }
        } else if (villageois.getRole() == 1) {
            List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
            request.setAttribute("messages", messagesRepaire);
            List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);
            request.setAttribute("decisions", decisions);
            request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
            
            if (villageois.getPouvoir().equals("contamination")){
                request.getRequestDispatcher("/WEB-INF/Partie/repaireContamination.jsp").forward(request, response);
            } else if (villageois.getPouvoir().equals("voyance")){
                goToVoyance(request, response, idPartie, villageoisDAO) ; 
            } else {
                request.getRequestDispatcher("/WEB-INF/Partie/repaire.jsp").forward(request, response);
            }
            
        } else {
            if (villageois.getPouvoir().equals("voyance")){
                goToVoyance(request, response, idPartie, villageoisDAO) ; 
            } else if (villageois.getPouvoir().equals("insomnie")){
                List<Message> messagesRepaireInsomnie = messageDAO.getListMessageRepaire(idPartie);
                request.setAttribute("messages", messagesRepaireInsomnie);
                request.getRequestDispatcher("/WEB-INF/Partie/nuitInsomnie.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/Partie/nuit.jsp").forward(request, response);
            }
        }
    }
    
    private void goToVoyance(HttpServletRequest request, HttpServletResponse response, 
            int idPartie, VillageoisDAO villageoisDAO)
            throws IOException, ServletException {
        List<Villageois> vivants = villageoisDAO.getListVillageois(idPartie) ; 
        System.out.println("BLBL : " +vivants.toString()) ; 
        request.setAttribute("vivants", vivants) ;
        request.getRequestDispatcher("/WEB-INF/Partie/nuitVoyance.jsp").forward(request, response);
    }
    
    
    
    private void actionNewDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        List<Villageois> villageoisList = villageoisDAO.getListVillageois(idPartie);
        request.setAttribute("villageoisList", villageoisList);
        request.getRequestDispatcher("/WEB-INF/Partie/decision.jsp").forward(request, response);
    }

    private void actionAddDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        Temps temps = new Temps();
        HttpSession session = request.getSession();
        String pseudoJoueur = session.getAttribute("membre").toString() ; 
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds) ; 
        DecisionDAO decisionDAO = new DecisionDAO(ds) ; 
        PartieDAO partieDAO = new PartieDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudoJoueur) ; 
        int idPartie = villageois.getPartie() ; 
        if (temps.estJour(idPartie)){
            request.setAttribute("lieu", "sur la Place du village") ; 
            decisionDAO.ajouteDecisionHumain(request.getParameter("decision"), idPartie, pseudoJoueur) ; 
            int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
            int limiteRatifie = (nbJoueurs / 2) + 1;
            int nbVoteActuel = decisionDAO.getDecisionHumain(request.getParameter("decision"), idPartie).getNbVote();
            decisionDAO.ratifieDecisionHumainSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        } else {
            request.setAttribute("lieu", "dans Repaire des Loups") ; 
            decisionDAO.ajouteDecisionLoup(request.getParameter("decision"), idPartie, pseudoJoueur) ;
            int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
            int limiteRatifie = (nbJoueurs / 2) + 1;
            int nbVoteActuel = decisionDAO.getDecisionLoup(request.getParameter("decision"), idPartie).getNbVote(); 
            decisionDAO.ratifieDecisionLoupSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        }
        
        /* On rejoint la salle de discussion */
        actionRejoindreSalleDiscussion(request, response);
    }
    
    
    private void actionAddVote(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("debut addVote");
        Temps temps = new Temps();
        HttpSession session = request.getSession();
        String votant = session.getAttribute("membre").toString() ; 
        DecisionDAO decisionDAO = new DecisionDAO(ds) ;
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        
        Villageois v = villageoisDAO.getVillageois(votant); 
        int idPartie = v.getPartie(); 
        String joueurConcerne = request.getParameter("joueurConcerne").toString() ; 
        Decision decision = decisionDAO.getDecisionHumain(joueurConcerne, idPartie) ;        
        boolean vote = decisionDAO.ajouteVoteHumain(decision, votant, idPartie); 
        Villageois villageois = villageoisDAO.getVillageois(votant) ; 
       
        /* On vérifie si la decision doit être ratifiée */
        if (vote) {
        int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
        int limiteRatifie = (nbJoueurs / 2) + 1;
        System.out.println("limite="+limiteRatifie);
        System.out.println("nbJoueurs="+nbJoueurs);
        int nbVoteActuel = decisionDAO.getDecisionHumain(request.getParameter("joueurConcerne"), idPartie).getNbVote(); 
        decisionDAO.ratifieDecisionHumainSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("joueurConcerne"), idPartie);
        } 
        
        /*
        int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
        System.out.println("apres get NbJoueursVivants"); 
        int limiteRatifie = (nbJoueurs / 2) + 1;
         /*On vérifie si la decision doit être ratifiée */
        /*if (temps.estJour(idPartie)){
            System.out.println("if temps est jour"); 
            int nbVoteActuel = decisionDAO.getDecisionHumain(request.getParameter("decision"), idPartie).getNbVote(); 
            System.out.println("apres get DecisionHumain ds if"); 
            decisionDAO.ratifieDecisionHumainSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        } else {
            System.out.println("if temps nest pas jour"); 
            int nbVoteActuel = decisionDAO.getDecisionLoup(request.getParameter("decision"), idPartie).getNbVote(); 
            decisionDAO.ratifieDecisionLoupSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        }*/
        actionRejoindreSalleDiscussion(request, response) ; 

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
            if (action.equals("ajouterUnMessage")) {
                actionAddMessage(request, response);
            }
            
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }
    
    
    private void actionAddMessage(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        MessageDAO messageDAO = new MessageDAO(ds);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        if (request.getParameter("contenu").toString().equals("")) {
            request.getRequestDispatcher("/WEB-INF/Partie/messageVide.jsp").forward(request, response);
        } else {
            messageDAO.ajouteMessageSalleDiscussion(pseudo, request.getParameter("contenu").toString(), idPartie);
            actionRejoindreSalleDiscussion(request, response);
        }
    }
    
    
    
    public int computeX(float proba) {
        int i = new Random().nextInt(10);
        if (i < proba * 10) {
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
