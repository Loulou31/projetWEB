/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.ceil;
import java.util.ArrayList;
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
import modele.*;

/**
 *
 * @author louise
 */
@WebServlet(name = "ControleurPartie", urlPatterns = {"/controleurPartie"})
public class ControleurPartie extends HttpServlet {

    @Resource(name = "jdbc/loupGarou")
    private DataSource ds;
    private String joueurChoisiSpiritisme;

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
     * Actions possibles en GET : - Rejoindre la salle de discussion
     * ("choseGame" transféré par controleur.java) - Commencer une partie -
     * Faire une nouvelle décision (et ses dérivés par pouvoir) - Ajouter une
     * décision à la BD (et ses dérivés par pouvoir) - Ajouter un vote - Faire
     * un choix (relatif à certains pouvoirs) - Rejoindre la salle de discussion
     * appropriée à certains pouvoirs - Recharger des pages - Archiver les
     * messages
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String action2 = response.getHeader("action");
        System.out.println("ACTION : " + action);
        try {
            if (action == null && action2 == null) {
                request.getRequestDispatcher("controleur").forward(request, response);
                // Choix et début de partie
            } else if (action.equals("choseGame")) {
                actionRejoindreSalleDiscussion(request, response);
            } else if (action.equals("debutPartie")) {
                actionDebutPartie(request, response);
                // Nouvelles decisions
            } else if (action.equals("newDecision")) {
                actionNewDecision(request, response);
            } else if (action.equals("newDecisionLoup")) {
                actionNewDecisionLoup(request, response);
            } else if (action.equals("newDecisionContamination")) {
                actionChoseVillageoisTransformer(request, response);
            } else if (action.equals("addDecisionContamination")) {
                actionAddDecisionContamination(request, response);
                // Ajout de decisions ou choix pour les pouvoirs
            } else if (action.equals("addDecision")) {
                actionAddDecision(request, response);
            } else if (action.equals("addDecisionSpiritisme")) {
                actionAddDecisionSpiritisme(request, response);
            } else if (action.equals("addVote")) {
                actionAddVote(request, response);
            } else if (action.equals("addChoixVoyant")) {
                actionAddChoixVoyant(request, response);
            } else if (action.equals("addChoixVoyantLoup")) {
                actionAddChoixVoyantLoup(request, response);
                // Actions relatives aux pouvoirs
            } else if (action.equals("rejoindreNuitHumain")) {
                actionRejoindreNuit(request, response);
            } else if (action.equals("rejoindreNuitLoup")) {
                actionRejoindreRepaire(request, response);
            } else if (action.equals("rejoindreNuitVoyance")) {
                actionRejoindreNuitVoyance(request, response);
            } else if (action.equals("rejoindreNuitLoupVoyanceUtilise")) {
                actionRejoindreNuitLoupVoyanceUtilise(request, response);
            } else if (action.equals("retourRepaire")) {
                actionRetourRepaire(request, response);
                // Racraichir la page pour les messages
            } else if (action.equals("reloadMessages")) {
                actionRejoindreSalleDiscussion(request, response);
                // Reactualiser pour certains pouvoirs
            } else if (action.equals("reloadVoyance")) {
                actionRejoindreSalleDiscussionVoyance(request, response);
            } else if (action.equals("reloadVoyanceLoup")) {
                actionRejoindreSalleDiscussionVoyanceLoup(request, response);
            } else if (action.equals("reloadVoyanceLoupRatifie")) {
                actionRejoindreSalleDiscussionVoyanceLoup(request, response);
            } else if (action.equals("reloadMessagesSpiritisme")) {
                actionAddDecisionSpiritisme(request, response);
                // Archivage de messages
            } else if (action.equals("archivage")) {
                actionRejoindreArchivage(request, response);
            } else if (action.equals("archivageJour")) {
                actionRejoindreArchivageJour(request, response);
            } else if (action.equals("archivageNuit")) {
                actionRejoindreArchivageNuit(request, response);
                // Messages pour pouvoir spiritisme
            } else {
                System.out.println("PROBLEME : " + action);
                invalidParameters(request, response);
            }
        } catch (DAOException e) {
            erreurBD(request, response, e);
        }

    }

    /**
     * @brief Ajout d'un message dans repaire (pour loup + voyant)
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionAddMessageVoyanceLoup(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        // Creation des DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);

        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);

        if (!partie.estJour()) {
            if (request.getParameter("contenu").toString().equals("")) {
                request.getRequestDispatcher("/WEB-INF/Partie/messageVide.jsp").forward(request, response);
            } else {
                messageDAO.ajouteMessageRepaire(pseudo, request.getParameter("contenu").toString(), idPartie);
                actionRejoindreSalleDiscussionVoyanceLoup(request, response);
            }
        } else {
            actionRejoindreSalleDiscussion(request, response);
        }
    }

    /**
     * @brief Ajouter message dans repaire (pour loup + spiritisme)
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionAddMessageSpiritismeLoup(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        System.out.println("debut action Add Message Voyance Loup");
        MessageDAO messageDAO = new MessageDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        if (!partie.estJour()) {
            if (request.getParameter("contenu").toString().equals("")) {
                request.getRequestDispatcher("/WEB-INF/Partie/messageVide.jsp").forward(request, response);
            } else {
                messageDAO.ajouteMessageSpiritisme(pseudo, request.getParameter("contenu"), idPartie);
                List<Message> messagesDiscussionSpiritisme = messageDAO.getListMessageSpiritisme(idPartie);
                messagesDiscussionSpiritisme = partie.messageDuJour(messagesDiscussionSpiritisme);
                messagesDiscussionSpiritisme = partie.triListe(messagesDiscussionSpiritisme);
                request.setAttribute("messages", messagesDiscussionSpiritisme);
                request.getRequestDispatcher("/WEB-INF/Partie/discussionSpiritismeLoup.jsp").forward(request, response);
            }
        } else {
            actionRejoindreSalleDiscussion(request, response);
        }
    }

    /**
     * @brief Rejoindre la salle de discussion appropriée
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionRejoindreSalleDiscussionVoyanceLoup(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        // Appel des DAO
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        HttpSession session = request.getSession();

        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);

        if (!partie.estJour()) {
            if (partie.getVoyance() == 1) {
                actionRejoindreNuitLoupVoyanceUtilise(request, response);
            } else {
                goToVoyanceLoup(request, response, idPartie, villageoisDAO);
            }
        } else {
            actionRejoindreSalleDiscussion(request, response);
        }
    }

    /**
     * @brief Rejoindre repaire (pour loup + voyance)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     * Permet de rejoindre le repaire pour un loup, après avoir utilisé son
     * pouvoir de voyance. Le pouvoir ne peut être utilisé qu'une fois dans la
     * nuit. Au retour au repaire, le loup ne plus utiliser son pouvoir.
     */
    private void actionRejoindreNuitLoupVoyanceUtilise(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Creation DAO
        PartieDAO partieDAO = new PartieDAO(ds);
        MessageDAO messageDAO = new MessageDAO(ds);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        // Pseudo joueur
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        // idPartie
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        // Messages du repaire
        messagesRepaire = partie.messageDuJour(messagesRepaire);
        messagesRepaire = partie.triListe(messagesRepaire);
        List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);

        // Transmission des attributs
        request.setAttribute("decisions", decisions);
        request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
        request.setAttribute("messages", messagesRepaire);
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);

        if (partieDAO.decisionLoupRatifie(idPartie)) {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireRatifieVoyanceUtilise.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireVoyanceUtilise.jsp").forward(request, response);
        }
    }

    /**
     * @brief Rejoindre page de nuit simple
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     * Permet de rejoindre la page de nuit simple, pour les humains sans
     * pouvoir, ou ayant utilisé leur pouvoir pour la nuit en cours.
     */
    private void actionRejoindreNuit(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        // Recupere pseudo joueur et villageois associe
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        // id Partie
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        // Transfert des informations
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);
        request.getRequestDispatcher("/WEB-INF/Partie/nuit.jsp").forward(request, response);
    }

    private void actionRejoindreNuitVoyance(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        /* On récupère le pseudo du joueur connecté, et le villageois correspondant */
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        /* On récupère l'id Partie et la Partie */
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        /* On donne les infos à la prochaine page jsp appelée */
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);
        messageDAO.getListMessageRepaire(idPartie);
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        messagesRepaire = partie.messageDuJour(messagesRepaire);
        messagesRepaire = partie.triListe(messagesRepaire);
        request.setAttribute("messages", messagesRepaire);
        request.getRequestDispatcher("/WEB-INF/Partie/nuitVoyanceUtilise.jsp").forward(request, response);
    }

    private void actionRetourRepaire(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        PartieDAO partieDAO = new PartieDAO(ds);
        Partie partie = partieDAO.getPartie(villageois.getPartie());
        actionRejoindreRepaireUtilise(request, response);
    }

    private void actionRejoindreRepaireUtilise(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        /* Création des DAO */
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        /* On récupère le pseudi du joueur connecté, et le villageois correspondant */
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);

        /* On récupère l'id Partie et la Partie */
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);
        request.setAttribute("decisions", decisions);
        request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
        /* On donne les infos à la prochaine page jsp appelée */
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        messagesRepaire = partie.messageDuJour(messagesRepaire);
        messagesRepaire = partie.triListe(messagesRepaire);
        request.setAttribute("messages", messagesRepaire);
        if (partieDAO.decisionLoupRatifie(idPartie)) {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireSpiritismeRatifieUtilise.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireSpiritismeUtilise.jsp").forward(request, response);
        }
    }

    private void actionRejoindreRepaire(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        /* On récupère le pseudi du joueur connecté, et le villageois correspondant */
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);

        /* On récupère l'id Partie et la Partie */
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);
        request.setAttribute("decisions", decisions);
        request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
        /* On donne les infos à la prochaine page jsp appelée */
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        messagesRepaire = partie.messageDuJour(messagesRepaire);
        messagesRepaire = partie.triListe(messagesRepaire);
        request.setAttribute("messages", messagesRepaire);
        if (partie.estJour()) {
            actionRejoindreSalleDiscussion(request, response);
        } else if (partieDAO.decisionLoupRatifie(idPartie)) {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireRatifie.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/repaire.jsp").forward(request, response);

        }
    }

    private void actionDebutPartie(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        /* on récupère l'Id de la partie */
        int idPartie = Integer.parseInt(request.getParameter("id"));
        // Creation DAO
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        /* recupération des listes de villageois */
        List<Villageois> testVillageois = villageoisDAO.getListVillageois(idPartie);
        List<Villageois> villageois = villageoisDAO.getListJoueurs(idPartie);
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        request.setAttribute("joueurs", joueurs);
        /* on récupère la partie */
        Partie partie = partieDAO.getPartie(idPartie);
        /* on récupère le nombre de joueurs */
        int nbJoueurs = partieDAO.getNbJoueurs(idPartie);
        /* on récupère la proportion des loups */
        float proportionLoup = partie.getProportionLG();
        int nbLoup;
        if (proportionLoup == 0) {
            nbLoup = 1;
        } else {
            nbLoup = (int) ceil(nbJoueurs * proportionLoup);
        }
        /* on récupère la probabilité d'avoir un pouvoir */
        float probaPouvoir = partie.getProbaPouvoir();

        /* si les roles ont deja été attribué: ne rien faire*/
        if (testVillageois.get(0).getRole() == -1) {
            /* selection des loups */
            int nbLoupCourant = 0;
            while (nbLoupCourant != nbLoup) {
                villageois = villageoisDAO.getListJoueurs(idPartie);
                int nbHumain = villageois.size();
                int valeur = generateurAleatoire(-1, nbHumain);
                while (valeur == -1 || valeur == nbHumain) {
                    valeur = generateurAleatoire(-1, nbHumain);
                }
                Villageois nouveauLoup = villageois.get(valeur);
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

                /* attribution du pouvoir contamination à un loup */
                if (contamination != 0) {
                    List<Villageois> loups = villageoisDAO.getListLoupsSansPouvoir(idPartie);
                    if (loups.size() > 0) {
                        int valContam = generateurAleatoire(-1, loups.size());
                        while (valContam == -1 || valContam == loups.size()) {
                            valContam = generateurAleatoire(-1, loups.size());
                        }
                        villageoisDAO.updatePlayerStatus(loups.get(valContam).getPseudo(), "contamination");
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
                        villageoisDAO.updatePlayerStatus(humains.get(valInsomn).getPseudo(), "insomnie");
                    } else {
                    }
                }
               
                /* attribution du pouvoir voyance à un villageois */
                if (voyance != 0) {
                    villageois = villageoisDAO.getListVillageoisSansPouvoir(idPartie);
                    if (villageois.size() > 0) {
                        int valVoyance = generateurAleatoire(-1, villageois.size());
                        while (valVoyance == -1 || valVoyance == villageois.size()) {
                            valVoyance = generateurAleatoire(-1, villageois.size());
                        }
                        villageoisDAO.updatePlayerStatus(villageois.get(valVoyance).getPseudo(), "voyance");
                    }
                }

                /* attribution du pouvoir spiritisme à un villageois */
                if (spiritisme != 0) {
                    villageois = villageoisDAO.getListVillageoisSansPouvoir(idPartie);
                    if (villageois.size() > 0) {
                        int valSpirit = generateurAleatoire(-1, villageois.size());
                        while (valSpirit == -1 || valSpirit == villageois.size()) {
                            valSpirit = generateurAleatoire(-1, villageois.size());
                        }
                        villageoisDAO.updatePlayerStatus(villageois.get(valSpirit).getPseudo(), "spiritisme");
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
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        /* On récupère le pseudi du joueur connecté, et le villageois correspondant */
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        /* On récupère l'id Partie et la Partie */
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        request.setAttribute("joueurs", joueurs);
        /* on met à 0 contamination et spiritisme */
        if (partie.estJour()) {
            partieDAO.passerVoyance(idPartie, 0);
            partieDAO.passerSpiritisme(idPartie, 0);
            partieDAO.passerContamination(idPartie, 0);
            request.setAttribute("enDiscussion", 0);
            partie = partieDAO.getPartie(idPartie);
            joueurChoisiSpiritisme = null;
            if (partie.decisionsCorrompues(decisionDAO.getListDecisionHumains(idPartie))) {
                decisionDAO.supprimerToutesDecisionsJour(idPartie);
            }
        } else if (partie.decisionsCorrompuesNuit(decisionDAO.getListDecisionLoup(idPartie))) {
            decisionDAO.supprimerToutesDecisionsNuit(idPartie);
        }
        /* On donne les infos à la prochaine page jsp appelée */
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);

        /* On vérifie si la partie n'est pas finie */
        if (nbLoupsVivants == 0) {
            /* Les loups ont perdu */
            request.getRequestDispatcher("/WEB-INF/Partie/loupsPerdent.jsp").forward(request, response);
        } else if (nbLoupsVivants == nbJoueursVivants) {
            /* Les loups ont gagné */
            request.getRequestDispatcher("/WEB-INF/Partie/loupsGagnent.jsp").forward(request, response);
        }
        // Si je suis mort
        if (villageois.getVivant() == 0) {
            System.out.println("joueur choisi: " + joueurChoisiSpiritisme);
            if (villageois.getPseudo().equals(joueurChoisiSpiritisme)) {
                System.out.println("je vais rejoindre le joueur qui veut me parler");
                List<Message> messagesDiscussionSpiritisme = messageDAO.getListMessageSpiritisme(idPartie);
                messagesDiscussionSpiritisme = partie.messageDuJour(messagesDiscussionSpiritisme);
                request.setAttribute("messages", messagesDiscussionSpiritisme);
                request.getRequestDispatcher("/WEB-INF/Partie/discussionSpiritisme.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/Partie/joueurMort.jsp").forward(request, response);
            }
        } else if (partie.estJour()) {
            List<Message> messagesVillage = messageDAO.getListeMessagesSalleDiscussion(idPartie);
            //Une fois que les messages contiendront les dates.
            messagesVillage = partie.messageDuJour(messagesVillage);
            messagesVillage = partie.triListe(messagesVillage);
            request.setAttribute("messages", messagesVillage);
            List<Decision> decisions = decisionDAO.getListDecisionHumains(idPartie);
            request.setAttribute("decisions", decisions);
            request.setAttribute("nbJoueurs", villageoisDAO.getListVillageoisVivants(idPartie).size());
            if (!partieDAO.decisionHumainRatifie(idPartie)) {
                request.getRequestDispatcher("/WEB-INF/Partie/placeDuVillage.jsp").forward(request, response);
            } else {
                String pseudoJoueurElimine = partieDAO.pseudoDecisionHumainRatifie(idPartie);
                Villageois joueurElimine = villageoisDAO.getVillageois(pseudoJoueurElimine);
                request.setAttribute("pseudoJoueurElimine", joueurElimine.getPseudo());
                request.setAttribute("roleJoueurElimine", joueurElimine.getRoleString());
                request.getRequestDispatcher("/WEB-INF/Partie/placeRatifie.jsp").forward(request, response);
            }
        } else if (villageois.getRole() == 1) {
            List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
            messagesRepaire = partie.messageDuJour(messagesRepaire);
            messagesRepaire = partie.triListe(messagesRepaire);
            request.setAttribute("messages", messagesRepaire);
            List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);
            request.setAttribute("decisions", decisions);
            request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
            System.out.println("pouvoirrrrrr = " + villageois.getPouvoir());
            System.out.println(villageois.getPouvoir().toString().equals("spiritisme"));
            if (villageois.getPouvoir().equals("contamination")) {
                System.out.println("contamination");
                if (!partieDAO.decisionLoupRatifie(idPartie)) {
                    if (partie.getContamination() == 1) {
                        request.getRequestDispatcher("/WEB-INF/Partie/repaire.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/WEB-INF/Partie/repaireContamination.jsp").forward(request, response);
                    }
                } else if (partie.getContamination() == 1) {
                    request.getRequestDispatcher("/WEB-INF/Partie/repaireRatifie.jsp").forward(request, response);
                } else {
                    partieDAO.passerContamination(idPartie, 1);
                    request.getRequestDispatcher("/WEB-INF/Partie/repaireContaminationRatifie.jsp").forward(request, response);
                }
            } else if (villageois.getPouvoir().equals("voyance")) {
                System.out.println("voyance");
                goToVoyanceLoup(request, response, idPartie, villageoisDAO);
            } else if (villageois.getPouvoir().toString().equals("spiritisme")) {
                System.out.println("j'ai le pouvoir spiristime");
                if (partie.isDiscussionSpiritisme() == 1) {
                    actionRejoindreRepaire(request, response);
                } else if (partieDAO.decisionLoupRatifie(idPartie)) {
                    List<Villageois> morts = villageoisDAO.getListVillageoisMorts(idPartie);
                    request.setAttribute("morts", morts);
                    request.getRequestDispatcher("/WEB-INF/Partie/repaireSpiritismeRatifie.jsp").forward(request, response);
                } else {
                    System.out.println("c'est la premiere fois que je l'utilise");
                    List<Villageois> morts = villageoisDAO.getListVillageoisMorts(idPartie);
                    request.setAttribute("morts", morts);
                    request.getRequestDispatcher("/WEB-INF/Partie/repaireSpiritisme.jsp").forward(request, response);
                }
            } else if (!partieDAO.decisionLoupRatifie(idPartie)) {
                System.out.println("aucun pouvoir go repaire");
                request.getRequestDispatcher("/WEB-INF/Partie/repaire.jsp").forward(request, response);
            } else {
                System.out.println("aucun pouvoir go repaire ratif");
                request.getRequestDispatcher("/WEB-INF/Partie/repaireRatifie.jsp").forward(request, response);
            }
            // Humains pendant la nuit
        } else if (villageois.getPouvoir().equals("voyance")) {
            goToVoyance(request, response, idPartie, villageoisDAO);
        } else if (villageois.getPouvoir().equals("insomnie")) {
            List<Message> messagesRepaireInsomnie = messageDAO.getListMessageRepaire(idPartie);
            //Une fois que les messages contiendront les dates.
            messagesRepaireInsomnie = partie.messageDuJour(messagesRepaireInsomnie);
            messagesRepaireInsomnie = partie.triListe(messagesRepaireInsomnie);
            request.setAttribute("messages", messagesRepaireInsomnie);
            request.getRequestDispatcher("/WEB-INF/Partie/nuitInsomnie.jsp").forward(request, response);
        } else if (villageois.getPouvoir().equals("spiritisme")) {
            if (partie.isDiscussionSpiritisme() == 1) {
                List<Message> messagesDiscussionSpiritisme = messageDAO.getListMessageSpiritisme(idPartie);
                messagesDiscussionSpiritisme = partie.messageDuJour(messagesDiscussionSpiritisme);
                messagesDiscussionSpiritisme = partie.triListe(messagesDiscussionSpiritisme);
                request.setAttribute("messages", messagesDiscussionSpiritisme);
                request.getRequestDispatcher("/WEB-INF/Partie/discussionSpiritisme.jsp").forward(request, response);
            } else {
                partieDAO.passerSpiritisme(idPartie, 1);
                List<Villageois> morts = villageoisDAO.getListVillageoisMorts(idPartie);
                if (morts != null) {
                    request.setAttribute("morts", morts);
                    request.getRequestDispatcher("/WEB-INF/Partie/nuitSpiritisme.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/Partie/nuit.jsp").forward(request, response);
                }

            }
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/nuit.jsp").forward(request, response);
        }
    }

    private void actionRejoindreSalleDiscussionVoyance(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        request.setAttribute("joueurs", joueurs);
        /* On récupère l'id Partie et la Partie */
        if (!partie.estJour() && villageois.getVivant() != 0) {
            actionRejoindreNuitVoyance(request, response);
        } else {
            actionRejoindreSalleDiscussion(request, response);
        }
    }

    /**
     * @brief Rejoindre la page de voyance pour un humain
     * @param request
     * @param response
     * @param idPartie
     * @param villageoisDAO
     * @throws IOException
     * @throws ServletException
     */
    private void goToVoyance(HttpServletRequest request, HttpServletResponse response,
            int idPartie, VillageoisDAO villageoisDAO)
            throws IOException, ServletException {
        // Recupère pseudo du joueur et villageois correspondant
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);

        // Recupère liste des villageois vivants
        List<Villageois> vivants = villageoisDAO.getListVillageoisVivants(idPartie);
        // Transfert des données
        request.setAttribute("vivants", vivants);
        // Test si le villageois a été tué entre temps par les loups
        if (villageois.getVivant() == 0) {
            actionRejoindreSalleDiscussion(request, response);
        }
        request.getRequestDispatcher("/WEB-INF/Partie/nuitVoyance.jsp").forward(request, response);
    }

    /**
     * @brief Rejoindre la page de voyance pour un loup + voyant
     * @param request
     * @param response
     * @param idPartie
     * @param villageoisDAO
     * @throws IOException
     * @throws ServletException
     *
     * Redirige un loup sur la bonne page si c'est un voyant.
     */
    private void goToVoyanceLoup(HttpServletRequest request, HttpServletResponse response,
            int idPartie, VillageoisDAO villageoisDAO)
            throws IOException, ServletException {
        List<Villageois> vivants = villageoisDAO.getListVillageoisVivants(idPartie);

        // Creation DAO
        PartieDAO partieDAO = new PartieDAO(ds);
        MessageDAO messageDAO = new MessageDAO(ds);
        DecisionDAO decisionDAO = new DecisionDAO(ds);

        // Récupère pseudo joueur et villageois correspondant
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);

        // Récupère Partie & messages
        Partie partie = partieDAO.getPartie(idPartie);
        int nbJoueursVivants = villageoisDAO.getListVillageoisVivants(idPartie).size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
        List<Decision> decisions = decisionDAO.getListDecisionLoup(idPartie);
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        messagesRepaire = partie.messageDuJour(messagesRepaire);
        messagesRepaire = partie.triListe(messagesRepaire);

        // Transfert des informations
        request.setAttribute("partie", partie);
        request.setAttribute("pseudoJoueurEnCours", pseudo);
        // Liste des villageois vivants
        request.setAttribute("vivants", vivants);
        // Liste des messages du repaire
        request.setAttribute("messages", messagesRepaire);
        request.setAttribute("decisions", decisions);
        request.setAttribute("nbJoueurs", villageoisDAO.getListLoupsVivants(idPartie).size());
        request.setAttribute("roleJoueurEnCours", villageois.getRoleString());
        request.setAttribute("pouvoirJoueurEnCours", villageois.getPouvoir());
        request.setAttribute("nbJoueurs", nbJoueursVivants);
        request.setAttribute("nbLoups", nbLoupsVivants);

        if (villageois.getVivant() == 0) {
            actionRejoindreSalleDiscussion(request, response);
        }
        if (partieDAO.decisionLoupRatifie(idPartie)) {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireVoyanceRatifie.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/repaireVoyance.jsp").forward(request, response);
        }
    }

    /**
     * @brief Nouvelle décision
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionNewDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        List<Villageois> villageoisList = villageoisDAO.getListVillageoisVivants(idPartie);
        request.setAttribute("villageoisList", villageoisList);
        PartieDAO partieDAO = new PartieDAO(ds);
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);
        if (partie.estJour()) {
            request.getRequestDispatcher("/WEB-INF/Partie/decision.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/decisionLoup.jsp").forward(request, response);
        }
    }

    /**
     * @brief Faire une nouvelle décision pour les loups
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     *
     * Nouvelle décision pour que loups choisissent le villageois à dévorer
     * pendant la nuit.
     */
    private void actionNewDecisionLoup(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        List<Villageois> villageoisList = villageoisDAO.getListVillageoisVivants(idPartie);
        request.setAttribute("villageoisList", villageoisList);
        PartieDAO partieDAO = new PartieDAO(ds);
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);
        request.getRequestDispatcher("/WEB-INF/Partie/decisionLoup.jsp").forward(request, response);
    }

    /**
     * @brief Ajouter une décision
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionAddDecision(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        /*  on récupère le pseudo joueur connecté */
        HttpSession session = request.getSession();
        String pseudoJoueur = session.getAttribute("membre").toString();

        // Creation DAO
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);

        /* On récupère le villageois correspondant au pseudo du joueur connecté */
        Villageois villageois = villageoisDAO.getVillageois(pseudoJoueur);
        /* on récupère l'id de la partie, puis la partie de la BD */
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);

        /* ajout de la décision chez les humains ou loups */
        if (partie.estJour()) {
            decisionDAO.ajouteDecisionHumain(request.getParameter("decision"), idPartie, pseudoJoueur);
            /* On vérifie si la décision doit etre ratifié */
            int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
            int limiteRatifie = (nbJoueurs / 2) + 1;
            int nbVoteActuel = decisionDAO.getDecisionHumain(request.getParameter("decision"), idPartie).getNbVote();
            decisionDAO.ratifieDecisionHumainSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        } else {
            decisionDAO.ajouteDecisionLoup(request.getParameter("decision"), idPartie, pseudoJoueur);
            /* On vérifie si la décision doit etre ratifié */
            int nbJoueurs = villageoisDAO.getListLoupsVivants(idPartie).size();
            System.out.println("nbLoupVivants : " + nbJoueurs);
            int limiteRatifie = (nbJoueurs / 2) + 1;
            System.out.println("limite :" + limiteRatifie);
            int nbVoteActuel = decisionDAO.getDecisionLoup(request.getParameter("decision"), idPartie).getNbVote();
            System.out.println("nbVoteActuel : " + nbVoteActuel);
            decisionDAO.ratifieDecisionLoupSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("decision"), idPartie);
        }

        /* On rejoint la salle de discussion */
        if (villageois.getPouvoir().equals("voyance")) {
            actionRejoindreSalleDiscussionVoyanceLoup(request, response);
        }
        actionRejoindreSalleDiscussion(request, response);
    }

    /**
     * @brief Ajout d'un vote à une décision
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionAddVote(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String votant = session.getAttribute("membre").toString();
        DecisionDAO decisionDAO = new DecisionDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);

        Villageois v = villageoisDAO.getVillageois(votant);
        int idPartie = v.getPartie();
        String joueurConcerne = request.getParameter("joueurConcerne");
        boolean vote;

        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);
        if (partie.estJour()) {
            Decision decision = decisionDAO.getDecisionHumain(joueurConcerne, idPartie);
            vote = decisionDAO.ajouteVoteHumain(decision, votant, idPartie);
        } else {
            Decision decision = decisionDAO.getDecisionLoup(joueurConcerne, idPartie);
            vote = decisionDAO.ajouteVoteLoup(decision, votant, idPartie);
        }


        /* On vérifie si la decision doit être ratifiée */
        if (vote) {
            int nbJoueurs = partieDAO.getNbJoueursVivants(idPartie);
            int limiteRatifie = (nbJoueurs / 2) + 1;
            int nbLoup = villageoisDAO.getListLoupsVivants(idPartie).size();
            int limiteRatifieLoup = (nbLoup / 2) + 1;
            request.setAttribute("partie", partie);
            if (partie.estJour()) {
                int nbVoteActuel = decisionDAO.getDecisionHumain(request.getParameter("joueurConcerne"), idPartie).getNbVote();
                decisionDAO.ratifieDecisionHumainSiBesoin(limiteRatifie, nbVoteActuel, request.getParameter("joueurConcerne"), idPartie);
            } else {
                System.out.println("dans voteAddLoup");
                int nbVoteActuel = decisionDAO.getDecisionLoup(joueurConcerne, idPartie).getNbVote();
                System.out.println("limite : " + limiteRatifieLoup);
                System.out.println("nbVoteActuel : " + nbVoteActuel);
                decisionDAO.ratifieDecisionLoupSiBesoin(limiteRatifieLoup, nbVoteActuel, joueurConcerne, idPartie);
            }
        }
        if (v.getPouvoir().equals("voyance")) {
            actionRejoindreSalleDiscussionVoyanceLoup(request, response);
        }
        actionRejoindreSalleDiscussion(request, response);
    }

    /**
     * @brief Choisir la personne à "espionner" pour la voyance
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     *
     * Choix du joueur dont l'utilisateur souhaite connaitre le rôle et le
     * pouvoir pendant la nuit.
     */
    public void actionAddChoixVoyant(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        String choixVoyant = request.getParameter("choixVoyance");
        Villageois villageois = villageoisDAO.getVillageois(choixVoyant);
        String role = villageois.getRoleString();
        String pouvoir = villageois.getPouvoir();
        int idPartie = villageois.getPartie();
        PartieDAO partieDAO = new PartieDAO(ds);
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);
        request.setAttribute("pseudo", choixVoyant);
        request.setAttribute("role", role);
        request.setAttribute("pouvoir", pouvoir);
        request.getRequestDispatcher("/WEB-INF/Partie/nuitVoyanceReponse.jsp").forward(request, response);
    }

    /**
     * @brief Confirmation de la décision du voyant
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionAddChoixVoyantLoup(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        String choixVoyant = request.getParameter("choixVoyantLoup");
        System.out.println("PSEUDO: " + choixVoyant);
        Villageois villageois = villageoisDAO.getVillageois(choixVoyant);
        String role = villageois.getRoleString();
        String pouvoir = villageois.getPouvoir();
        int idPartie = villageois.getPartie();
        PartieDAO partieDAO = new PartieDAO(ds);
        Partie partie = partieDAO.getPartie(idPartie);
        request.setAttribute("partie", partie);
        request.setAttribute("pseudo", choixVoyant);
        request.setAttribute("role", role);
        request.setAttribute("pouvoir", pouvoir);
        partieDAO.passerVoyance(idPartie, 1);
        request.getRequestDispatcher("/WEB-INF/Partie/repaireVoyanceReponse.jsp").forward(request, response);
    }

    /**
     * @brief Choisir un humain à transformer en loup
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionChoseVillageoisTransformer(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);

        // Recupère partie, et liste des villageois vivants
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        List<Villageois> vivants = villageoisDAO.getListHumainsVivants(idPartie);
        Partie partie = partieDAO.getPartie(idPartie);

        // Transfert d'informations
        request.setAttribute("partie", partie);
        request.setAttribute("vivants", vivants);
        request.getRequestDispatcher("/WEB-INF/Partie/decisionContamination.jsp").forward(request, response);
    }

    /**
     * @brief Confirmer la décision de l'humain à transformer en loup
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionAddDecisionContamination(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        // Creation DAO et rérupère villageois
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        HttpSession session = request.getSession();
        String login = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(login);
        int idPartie = villageois.getPartie();
        // Récupère le résultat de la décision
        String pseudo = request.getParameter("decisionContamination");
        // Mise à jour du rôle du joueur contaminé
        villageoisDAO.updatePlayerRole(pseudo, 1);
        partieDAO.passerContamination(idPartie, 1);
        actionRejoindreSalleDiscussion(request, response);
    }

    /**
     * @brief Choisir la personne avec qui discuter pour le spiritisme
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionAddDecisionSpiritisme(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        // Recupère villageois associe au joueur
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);

        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(villageois.getPartie());
        // Mise à jour du joueur appelé 
        joueurChoisiSpiritisme = request.getParameter("decisionSpiritisme");
        partieDAO.passerSpiritisme(villageois.getPartie(), 1);
        // Recupère messages 
        List<Message> messagesDiscussionSpiritisme = messageDAO.getListMessageSpiritisme(idPartie);
        messagesDiscussionSpiritisme = partie.messageDuJour(messagesDiscussionSpiritisme);
        messagesDiscussionSpiritisme = partie.triListe(messagesDiscussionSpiritisme);
        // Transfert des informations
        request.setAttribute("partie", partie);
        request.setAttribute("view", villageois.getRoleString());
        request.setAttribute("messages", messagesDiscussionSpiritisme);
        // Le joueur est un loup
        if (partie.estJour()) {
            actionRejoindreSalleDiscussion(request, response);
        } else if (villageois.getRole() == 1) {
            request.getRequestDispatcher("/WEB-INF/Partie/discussionSpiritismeLoup.jsp").forward(request, response);
            // Le joueur est un humain
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/discussionSpiritisme.jsp").forward(request, response);
        }
    }

    /**
     * @brief Rejoindre la page d'archives des discussions
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     *
     * Après avoir rejoint cette page, l'utilisateur devra choisir quel archives
     * il souhaite voir.
     */
    public void actionRejoindreArchivage(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        // Recupère villageois associe au joueur
        HttpSession session = request.getSession();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);

        // Partie 
        int idPartie = villageois.getPartie();
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        int nbJoueursVivants = joueurs.size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();

        // Defaite des loups
        if (nbLoupsVivants == 0) {
            request.getRequestDispatcher("/WEB-INF/Partie/loupsPerdent.jsp").forward(request, response);
            // Victoire des loups
        } else if (nbLoupsVivants == nbJoueursVivants) {
            request.getRequestDispatcher("/WEB-INF/Partie/loupsGagnent.jsp").forward(request, response);
            // Partie non terminee, accès aux archives
        } else {
            request.getRequestDispatcher("/WEB-INF/Partie/archive.jsp").forward(request, response);
        }
    }

    /**
     * @brief Rejoindre discussions archivées de la place du village
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionRejoindreArchivageJour(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        MessageDAO messageDAO = new MessageDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        int nbJoueursVivants = joueurs.size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
//        if (nbLoupsVivants == 0) {
//            /* Les loups ont perdu */
//            request.getRequestDispatcher("/WEB-INF/Partie/loupsPerdent.jsp").forward(request, response);
//        } else if (nbLoupsVivants == nbJoueursVivants) {
//            /* Les loups ont gagné */
//            request.getRequestDispatcher("/WEB-INF/Partie/loupsGagnent.jsp").forward(request, response);
//        }else{
        List<Message> messagesVillage = messageDAO.getListeMessagesSalleDiscussion(idPartie);
        messagesVillage = partie.triListe(messagesVillage);
        ArrayList<String> archives = partie.messagesArchives(messagesVillage);
        request.setAttribute("archives", archives);
        request.getRequestDispatcher("/WEB-INF/Partie/archivePlace.jsp").forward(request, response);
//        }
    }

    /**
     * @brief Rejoindre discussions archivées du repaire
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void actionRejoindreArchivageNuit(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);
        List<Villageois> joueurs = villageoisDAO.getListVillageoisVivants(idPartie);
        int nbJoueursVivants = joueurs.size();
        int nbLoupsVivants = villageoisDAO.getListLoupsVivants(idPartie).size();
//        if (nbLoupsVivants == 0) {
//            /* Les loups ont perdu */
//            request.getRequestDispatcher("/WEB-INF/Partie/loupsPerdent.jsp").forward(request, response);
//        } else if (nbLoupsVivants == nbJoueursVivants) {
//            /* Les loups ont gagné */
//            request.getRequestDispatcher("/WEB-INF/Partie/loupsGagnent.jsp").forward(request, response);
//        }else{
        MessageDAO messageDAO = new MessageDAO(ds);
        List<Message> messagesRepaire = messageDAO.getListMessageRepaire(idPartie);
        messagesRepaire = partie.triListe(messagesRepaire);
        ArrayList<String> archives = partie.messagesArchives(messagesRepaire);
        request.setAttribute("archives", archives);
        request.getRequestDispatcher("/WEB-INF/Partie/archiveRepaire.jsp").forward(request, response);
//        }
    }

    /**
     * Actions possibles en POST : - Ajouter un message - Ajouter un message
     * dans le repaire (pour loup + voyance)
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        System.out.println("ACTION : " + action);
        if (action == null) {
            invalidParameters(request, response);
            return;
        }
        try {
            if (action.equals("ajouterUnMessage")) {
                actionAddMessage(request, response);
            } else if (action.equals("addMessVoyanceLoup")) {
                actionAddMessageVoyanceLoup(request, response);
            } else if (action.equals("ajouterMessageSpiritisme")) {
                actionAddMessageSpiritismeLoup(request, response);
            } else {
                invalidParameters(request, response);
            }

        } catch (DAOException e) {
            erreurBD(request, response, e);
        }
    }

    /**
     * @brief Ajouter un message dans la salle de discussion appropriée
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void actionAddMessage(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        // Creation DAO
        MessageDAO messageDAO = new MessageDAO(ds);
        PartieDAO partieDAO = new PartieDAO(ds);
        VillageoisDAO villageoisDAO = new VillageoisDAO(ds);
        // Recupere pseudo joueur et villageois associé
        HttpSession session = request.getSession();
        String pseudo = session.getAttribute("membre").toString();
        Villageois villageois = villageoisDAO.getVillageois(pseudo);
        int idPartie = villageois.getPartie();
        Partie partie = partieDAO.getPartie(idPartie);

        if (request.getParameter("contenu").toString().equals("")) {
            request.getRequestDispatcher("/WEB-INF/Partie/messageVide.jsp").forward(request, response);
        } else {
            // Ajouter message dans la place du village
            if (partie.estJour()) {
                messageDAO.ajouteMessageSalleDiscussion(pseudo, request.getParameter("contenu").toString(), idPartie);
                // Ajouter message dans la salle de discussion spiritisme 
            } else if (request.getParameter("spiritisme") != null && request.getParameter("spiritisme").toString().equals("true")) {
                System.out.println("je rajoute un mess ds spirit");
                messageDAO.ajouteMessageSpiritisme(pseudo, request.getParameter("contenu").toString(), idPartie);
                // Ajouter un message dans le repaire
            } else {
                messageDAO.ajouteMessageRepaire(pseudo, request.getParameter("contenu").toString(), idPartie);
            }
            actionRejoindreSalleDiscussion(request, response);
        }
    }

    /**
     * @brief Générateur de valeur
     * @param proba
     * @return 0 ou 1 (avec une probabilité @proba)
     *
     * Retourne 0 ou 1 avec une certaine probabilité donnée
     */
    public int computeX(float proba) {
        int i = new Random().nextInt(10);
        if (i < proba * 10) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @brief Génère valeur aléatoire entre valeurMin et valeurMax
     * @param valeurMin
     * @param valeurMax
     * @return valeur aléatoire
     */
    public int generateurAleatoire(int valeurMin, int valeurMax) {
        Random r = new Random();
        int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
        return valeur;
    }

}
