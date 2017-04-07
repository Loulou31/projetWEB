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
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Message;

/**
 *
 * @author bagouc
 */
public class MessageDAO extends AbstractDatabaseDAO {
    
    public MessageDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * Renvoie la liste des messages de la table Message_Salle_Discussion.
     */
    public List<Message> getListeMessagesSalleDiscussion(int idPartie) {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Salle_Discussion WHERE idPartie = "+idPartie);
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), rs.getInt("idPartie"));
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
     /**
     * Renvoie la liste des messages de la table Message_Repaire.
     */
    public List<Message> getListMessageRepaire(int idPartie) {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Repaire WHERE idPartie = "+idPartie);
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), idPartie);
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    
    public List<Message> getListMessageSpiritisme(int idPartie) {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Spiritisme WHERE idPartie = "+idPartie);
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), idPartie);
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    /**
     * Ajoute un message dans la tale MessageSalleDiscussion
     */
    public void ajouteMessageSalleDiscussion (String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {	
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Message_Salle_Discussion VALUES (?, ?, ?, SYSDATE)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Ajoute un message dans la tale MessageRepaire
     */
    public void ajouteMessageRepaire(String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Message_Repaire VALUES (?, ?, ?, SYSDATE)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void ajouteMessageSpiritisme(String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Message_Spiritisme VALUES (?, ?, ?, SYSDATE)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void supprimerTousMessages(int idPartie){
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE * FROM MESSAGE_SALLE_DISCUSSION WHERE idPartie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
            PreparedStatement st1 = conn.prepareStatement("DELETE * FROM MESSAGE_REPAIRE WHERE idPartie = ?");
            st1.setInt(1, idPartie);
            st1.executeUpdate();
            PreparedStatement st2 = conn.prepareStatement("DELETE * FROM MESSAGE_SPIRITISME WHERE idPartie = ?");
            st2.setInt(1, idPartie);
            st2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD supp messages " + e.getMessage(), e);
        }
    }
}
