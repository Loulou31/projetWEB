/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import modele.Decision;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.annotation.Resource;
import modele.Temps;

/**
 *
 * @author gaunetc
 */
public class DecisionDAO extends AbstractDatabaseDAO{
    
    public DecisionDAO(DataSource ds) {
        super(ds);
    }
    
    
    public List<Decision> getListDecisionHumains(int idPartie){
        //Renvoie la liste des decision pour les humainsList<Message> result = new ArrayList<Message>();
        List<Decision> result = new ArrayList<Decision>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_HUMAIN WHERE id_partie = ? ") ; 
            st.setInt(1, idPartie) ; 
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 1; i <= nbVote; i++) {
                    votants.add(rs.getString("votant" + i));
                }
                Decision decision
                        = new Decision(rs.getString("login_joueur_concerne"), votants, nbVote, idPartie, rs.getInt("date_envoi"));
                result.add(decision);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    public List<Decision> getListDecisionLoup(int idPartie) {
        //Renvoie la liste des decision pour les loups List<Message> result = new ArrayList<Message>();
        List<Decision> result = new ArrayList<Decision>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_LOUP WHERE id_partie = ? ") ; 
            st.setInt(1, idPartie) ; 
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 1; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                Decision decision =
                    new Decision(rs.getString("login_joueur_concerne"), votants, nbVote, idPartie, rs.getInt("date_envoi"));
                result.add(decision);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return result;
    }

    //ok c'est un update
    public void ratifieDecisionHumainSiBesoin(int limiteRatifie, int nbVote, String pseudo, int idPartie) {
        System.out.println("dans ratifie");
        if (nbVote >= limiteRatifie) {
            System.out.println("dans ratIFie");
            try (Connection conn = getConn()) {
                PreparedStatement st = conn.prepareStatement("Update Decision_Humain Set"
                        + " ratifie = 1 Where login_joueur_concerne = ? and id_partie = ?");
                st.setString(1, pseudo);
                st.setInt(2, idPartie);
                st.executeUpdate();
                PreparedStatement st1 = conn.prepareStatement
               ("UPDATE JOUEUR SET statut = 0 WHERE login = ?");
                st1.setString(1, pseudo);
                st1.executeUpdate();
                st = conn.prepareStatement("Update Joueur Set"
                        + " rolePartie = 0 Where login = ?");
                st1.setString(1, pseudo);
                st1.executeUpdate();
                /* On décrémente le nombre de joueurs vivants de la partie */
                st = conn.prepareStatement("Update Partie set nbJoueursVivants = nbJoueursVivants - 1 Where IdPartie = ?");
                st.setInt(1, idPartie);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Erreur BD dans ratifie" + e.getMessage(), e);
            }
        }
    }

    public void ratifieDecisionLoupSiBesoin(int limiteRatifie, int nbVote, String pseudo, int idPartie) {
        if (nbVote >= limiteRatifie) {
            try (Connection conn = getConn()) {
                PreparedStatement st = conn.prepareStatement("Update Decision_Loup set"
                        + " ratifie = 1 Where login_joueur_concerne = ? and id_partie = ?");
                st.setString(1, pseudo);
                st.setInt(2, idPartie);
                st.executeQuery();
                PreparedStatement st1 = conn.prepareStatement
                        ("UPDATE JOUEUR SET statut = 0 WHERE login = ?") ;
                st1.setString(1, pseudo) ; 
                st1.executeQuery() ; 
                /* On décrémente le nombre de joueurs vivants de la partie */
                st = conn.prepareStatement("Update Partie set nbJoueursVivants = nbJoueursVivants - 1 Where IdPartie = ?");
                st.setInt(1, idPartie);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("Erreur BD " + e.getMessage(), e);
            }
        }
    }

    public void ajouteDecisionHumain(String login_joueur, int idPartie, String login_expeditaire) {

        /* on vérifie que une decision n'est pas en cours sur le joueur concerné */
        if (!decisionCorrecteHumain(login_joueur, idPartie)) {
            try (Connection conn = getConn()) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO Decision_Humain (login_joueur_concerne, id_partie, login_expeditaire, ratifie, date_envoi, nbreVote) VALUES (?, ?, ?, 0, ?, 0)");
                st.setString(1, login_joueur);
                st.setInt(2, idPartie);
                st.setString(3, login_expeditaire);
                Temps temps = new Temps();
                int date = temps.getTempsInt();
                st.setInt(4, date);
                st.executeUpdate();
                HashSet<String> votants = new HashSet();
                Decision decision = new Decision(login_joueur, votants, 0, idPartie);
                ajouteVoteHumain(decision, login_expeditaire, idPartie);
            } catch (SQLException e) {
                throw new DAOException("Erreur BD decision " + e.getMessage(), e);
            }
        }
    }

    public Boolean decisionCorrecteHumain(String pseudo, int idPartie){
        /*Renvoie true si la decision existe, false sinon*/
        try (Connection conn = this.getConn()) {
            PreparedStatement s = conn.prepareStatement(
                    " Select login_joueur_concerne From Decision_Humain Where login_joueur_concerne = ? and id_partie = ?");
            s.setString(1, pseudo);
            s.setInt(2, idPartie);
            ResultSet r = s.executeQuery();
            return (r.next());
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public Boolean decisionCorrecteLoup(String pseudo, int idPartie){
        /*Renvoie true si la decision existe, false sinon*/
        try (Connection conn = this.getConn()) {
            PreparedStatement s = conn.prepareStatement(
                    " Select login_joueur_concerne From Decision_Loup Where login_joueur_concerne = ? and id_partie = ?");
            s.setString(1, pseudo);
            s.setInt(2, idPartie);
            ResultSet r = s.executeQuery();
            return (r.next());
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    public void ajouteDecisionLoup(String login_joueur, int idPartie, String login_expeditaire) {
        /* on vérifie que une decision n'est pas en cours sur le joueur concerné */
        if (!decisionCorrecteLoup(login_joueur, idPartie)) {
            System.out.println("je vais ajouter la dec");
            try (Connection conn = getConn()) {
                PreparedStatement st = conn.prepareStatement("INSERT INTO Decision_Loup (login_joueur_concerne, id_partie, login_expeditaire, ratifie, date_envoi, nbreVote) VALUES (?, ?, ?, 0, ?, 0)");
                st.setString(1, login_joueur);
                st.setInt(2, idPartie);
                st.setString(3, login_expeditaire);
                Temps temps = new Temps();
                int date = temps.getTempsInt();
                st.setInt(4, date);
                st.executeUpdate();
                HashSet<String> votants = new HashSet();
                Decision decision = new Decision(login_joueur, votants, 0, idPartie);
                ajouteVoteLoup(decision, login_expeditaire, idPartie);
            } catch (SQLException e) {
                throw new DAOException("Erreur BD " + e.getMessage(), e);
            }
        }
    }
    
    public boolean ajouteVoteHumain(Decision decision, String votant, int idPartie) {
        try (Connection conn = getConn()) {
            /* On vérifie que la personne n'a pas déjà voté pour cette décision */
            if (!decision.getVotants().contains(votant)) {
                PreparedStatement st = conn.prepareStatement("UPDATE Decision_Humain set NbreVote = NbreVote + 1 Where login_joueur_concerne = ? ");
                st.setString(1, decision.getJoueurConcerne());
                st.executeUpdate();
                decision.getVotants().add(votant);
                int nbreVotant = decision.getVotants().size();
                st = conn.prepareStatement("UPDATE Decision_Humain set Votant"+nbreVotant+" = ? Where login_joueur_concerne = ? ");
                st.setString(1, votant);
                st.setString(2, decision.getJoueurConcerne());
                st.executeUpdate();
                int nbVote = decision.getNbVote() + 1;
                decision.setNbVote(nbVote);
                return true; 
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException("Erreur BD vote " + e.getMessage(), e);
        }
    }

    public boolean ajouteVoteLoup(Decision decision, String votant, int idPartie) {
        try (Connection conn = getConn()) {
            /* On vérifie que la personne n'a pas déjà voté pour cette décision */
            if (!decision.getVotants().contains(votant)) {
                System.out.println("je vais ajouter le vote de celui qui a propose");
                PreparedStatement st = conn.prepareStatement("UPDATE Decision_Loup set NbreVote = NbreVote + 1 Where login_joueur_concerne = ? ");
                st.setString(1, decision.getJoueurConcerne());
                st.executeUpdate();
                decision.getVotants().add(votant);
                int nbreVotant = decision.getVotants().size();
                st = conn.prepareStatement("UPDATE Decision_Loup set Votant" + nbreVotant + " = ? Where login_joueur_concerne = ? ");
                st.setString(1, votant);
                st.setString(2, decision.getJoueurConcerne());
                st.executeUpdate();
                int nbVote = decision.getNbVote() + 1;
                decision.setNbVote(nbVote);
                return true;
                }
            return false; 
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    public Decision getDecisionHumain(String joueurConcerne, int idPartie) {
        Decision decision = null;
        try (Connection conn = getConn()) {	     
	    PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_HUMAIN WHERE login_joueur_concerne = ? and id_partie = ?") ; 
            st.setString(1, joueurConcerne) ; 
            st.setInt(2, idPartie); 
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 1; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                decision = new Decision(joueurConcerne, votants, nbVote, idPartie, rs.getInt("date_envoi"));
                return decision; 
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        if (decision == null) {
            System.out.println("decision est null");
        }
        return decision;
    }

    public Decision getDecisionLoup(String joueurConcerne, int idPartie) {
        Decision decision = null;
        try (Connection conn = getConn()) {	     
	    PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_LOUP WHERE login_joueur_concerne = ? and id_partie = ?") ; 
            st.setString(1, joueurConcerne) ; 
            st.setInt(2, idPartie);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                votants.add(rs.getString("login_expeditaire"));
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 2; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                decision = new Decision(joueurConcerne, votants, nbVote, idPartie, rs.getInt("date_envoi"));
                return decision;
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return decision ; 
   }
    
     public void supprimerToutesDecisionsJour(int idPartie){
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM DECISION_HUMAIN WHERE id_Partie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD supp decisions jour " + e.getMessage(), e);
        }
    }
     
      public void supprimerToutesDecisionsNuit(int idPartie){
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE FROM DECISION_LOUP WHERE id_Partie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD supp decisions nuit" + e.getMessage(), e);
        }
    }
}
