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
    /* Variable globale: */
    int nbreDecisionHumain = 1; 
    int nbreDecisionLoup = 1; 

    
    public DecisionDAO(DataSource ds) {
        super(ds);
    }
    
    
    public List<Decision> getListDecisionHumains(int idPartie){
        //Renvoie la liste des decision pour les humainsList<Message> result = new ArrayList<Message>();
        List<Decision> result = new ArrayList<Decision>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Decision_Humain Where id_partie ="+idPartie);
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                votants.add(rs.getString("login_expediteur"));
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
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Decision_Loup Where id_partie ="+idPartie);
            while (rs.next()) {
                HashSet<String> votants = new HashSet<String>();
                votants.add(rs.getString("login_expediteur"));
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
   
    public void ajouteDecisionHumain(String login_joueur, boolean etat, 
            HashSet<String> votants, int idPartie, String login_expeditaire) {
        try (Connection conn = getConn()) {
            int nbreVote = votants.size();
            int etatValide = 0; 
            if (etat) {
                etatValide = 1;
            }
	    PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Decision_Humain (id_decision, id_partie, login_expeditaire, login_joueur_concerne,"
                       + "est_valide, date_envoi, nbreVote) VALUES (?, ?, ?, ?, ?, SYSDATE, ?)");
            st.setInt(1, nbreDecisionHumain);
            st.setInt(2, idPartie);
            st.setString(3, login_expeditaire);
            st.setString(4, login_joueur);
            st.setInt(5, etatValide);
            st.setInt(6, nbreVote);
            st.executeUpdate();
            nbreDecisionHumain++; 
            
            Iterator<String> it = votants.iterator(); 
            for (int i=1; i<=nbreVote; i++) {
                String votant = it.next(); 
                st = conn.prepareStatement("INSERT INTO Decision_Humain (votant"+i+") values (?)");
                st.setString(1, votant);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void ajouteDecisionLoup(String login_joueur, boolean etat, 
            HashSet<String> votants, int idPartie, String login_expeditaire) {
        try (Connection conn = getConn()) {
            int nbreVote = votants.size();
            int etatValide = 0; 
            if (etat) {
                etatValide = 1;
            }
	    PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Decision_Loup (id_partie, login_expeditaire, login_joueur_concerne,"
                       + "est_valide, date_envoi, nbreVote) VALUES (?, ?, ?, ?, SYSDATE, ?)");
            st.setInt(1, nbreDecisionLoup);
            st.setInt(2, idPartie);
            st.setString(3, login_expeditaire);
            st.setString(4, login_joueur);
            st.setInt(5, etatValide);
            st.setInt(6, nbreVote);
            st.executeUpdate();
            nbreDecisionLoup++;
            
            Iterator<String> it = votants.iterator(); 
            for (int i=1; i<=nbreVote; i++) {
                String votant = it.next(); 
                st = conn.prepareStatement("INSERT INTO Decision_Loup (votant"+i+") values (?)");
                st.setString(1, votant);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
    public void ajouteVoteHumain (Decision decision, String votant) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Decision_Humain set NbreVote = NbreVote + 1 Where id_decision = ? ");
            st.setInt(1, decision.getIdDecision());
            st.executeUpdate();
            
            int nbreVotant = decision.getVotants().size(); 
            st = conn.prepareStatement
	       ("UPDATE Decision_Humain set Votant? = ? Where id_decision = ? ");
            st.setInt(1, nbreVotant+1);
            st.setString(2, votant);
            st.setInt(3, decision.getIdDecision());
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void ajouteVoteLoup (Decision decision, String votant) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("UPDATE Decision_Loup set NbreVote = NbreVote + 1 Where id_decision = ? ");
            st.setInt(1, decision.getIdDecision());
            st.executeUpdate();
            
            int nbreVotant = decision.getVotants().size(); 
            st = conn.prepareStatement
	       ("UPDATE Decision_Loup set Votant? = ? Where id_decision = ? ");
            st.setInt(1, nbreVotant+1);
            st.setString(2, votant);
            st.setInt(3, decision.getIdDecision());
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
