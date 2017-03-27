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
    public List<Message> getListeMessagesSalleDiscussion() {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Salle_Discussion");
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"));
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
    public List<Message> getListMessageRepaire() {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Repaire");
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"));
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
    public void ajouteMessageSalleDiscussion (String expediteur, String contenu) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Message_Salle_Discussion VALUES (?, ?, SYSDATE)");
            st.setString(1,expediteur);
            st.setString(2, contenu);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * Ajoute un message dans la tale MessageRepaire
     */
    public void ajouteMessageRepaire (String expediteur, String contenu) {
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Message_Repaire VALUES (?, ?, SYSDATE)");
            st.setString(1,expediteur);
            st.setString(2, contenu);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
