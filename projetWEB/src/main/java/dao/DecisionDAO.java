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
                votants.add(rs.getString("login_expeditaire"));
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 2; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                Decision decision =
                    new Decision(rs.getString("login_joueur_concerne"), votants);
                result.add(decision);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
     public List<Decision> getListDecisionLoup(int idPartie){
        //Renvoie la liste des decision pour les loups List<Message> result = new ArrayList<Message>();
        List<Decision> result = new ArrayList<Decision>();
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_LOUP WHERE id_partie = ? ") ; 
            st.setInt(1, idPartie) ; 
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                votants.add(rs.getString("login_expeditaire"));
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 2; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                Decision decision =
                    new Decision(rs.getString("login_joueur_concerne"), votants);
                result.add(decision);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
   
     
     
    public void ajouteDecisionHumain(String login_joueur, int idPartie, String login_expeditaire) {
        try (Connection conn = getConn()) {
	    PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Decision_Humain (login_joueur_concerne, id_partie, login_expeditaire, est_valide, date_envoi, nbreVote) VALUES (?, ?, ?, 1, SYSDATE, 1)");
            System.out.println("JOUEUR CONCERNE : " + login_joueur) ;
            System.out.println("ID PARTIE : " + idPartie) ;
            System.out.println("EXPEDITAIRE : " + login_expeditaire) ;
            st.setString(1, login_joueur);
            st.setInt(2, idPartie);
            st.setString(3, login_expeditaire);
            st.executeUpdate();
            HashSet<String> votants = new HashSet() ;
            Decision decision = new Decision(login_joueur, votants) ; 
            ajouteVoteHumain(decision, login_joueur);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD decision " + e.getMessage(), e);
        }
    }
    
    
    public void ajouteDecisionLoup(String login_joueur, int idPartie, String login_expeditaire) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Decision_Loup (login_joueur_concerne, id_partie, login_expeditaire, est_valide, date_envoi, nbreVote) VALUES (?, ?, ?, 1, SYSDATE, 1)");
            st.setString(1, login_joueur);
            st.setInt(2, idPartie);
            st.setString(3, login_expeditaire);
            st.executeUpdate();
            HashSet<String> votants = new HashSet() ;
            votants.add(login_expeditaire) ; 
            Decision decision = new Decision(login_joueur, votants) ; 
            ajouteVoteLoup(decision, login_joueur);
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
    public void ajouteVoteHumain (Decision decision, String votant) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Decision_Humain set NbreVote = NbreVote + 1 Where login_joueur_concerne = ? ");
            st.setString(1, decision.getJoueurConcerne());
            st.executeUpdate();
            decision.getVotants().add(votant) ; 
            int nbreVotant = decision.getVotants().size(); 
            st = conn.prepareStatement
	       ("UPDATE Decision_Humain set Votant"+nbreVotant+" = ? Where login_joueur_concerne = ? ");
            st.setString(1, votant);
            st.setString(2, decision.getJoueurConcerne());
            st.executeUpdate();
            int nbVote = decision.getNbVote() +1 ; 
            decision.setNbVote(nbVote) ; 
            System.out.println("NBVOTE : " + nbVote) ; 
        } catch (SQLException e) {
            throw new DAOException("Erreur BD vote " + e.getMessage(), e);
        }
    }
    
    public void ajouteVoteLoup (Decision decision, String votant) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Decision_Loup set NbreVote = NbreVote + 1 Where login_joueur_concerne = ? ");
            st.setString(1, decision.getJoueurConcerne());
            st.executeUpdate();
            
            int nbreVotant = decision.getVotants().size(); 
            st = conn.prepareStatement
	       ("UPDATE Decision_Loup set Votant"+nbreVotant+" = ? Where login_joueur_concerne = ? ");
            st.setString(1, votant);
            st.setString(2, decision.getJoueurConcerne());
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        
       }
    
   public Decision getDecisionHumain (String joueurConcerne){
       Decision decision = null ; 
        try (Connection conn = getConn()) {	     
	    PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM DECISION_HUMAIN WHERE login_joueur_concerne = ? ") ; 
            st.setString(1, joueurConcerne) ; 
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                votants.add(rs.getString("login_expeditaire"));
                int nbVote = Integer.parseInt(rs.getString("nbreVote"));
                for (int i = 2; i <= nbVote; i++) {
                    votants.add(rs.getString("votant"+i));
                }
                decision = new Decision(joueurConcerne, votants);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return decision ; 
   }
}
