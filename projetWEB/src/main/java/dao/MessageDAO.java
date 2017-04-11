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
import modele.Temps;

/**
 *
 * @author bagouc
 */
public class MessageDAO extends AbstractDatabaseDAO {
    
    public MessageDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * @brief Renvoie la liste des messages de la place du village
     * @param idPartie
     * @return liste des messages de la place
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
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), rs.getInt("idPartie"), rs.getInt("date_envoi"));
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
     /**
     * @brief Renvoie la liste des messages du repaire
     * @param idPartie
     * @return liste des messages du repaire
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
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), idPartie, rs.getInt("date_envoi"));
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    /**
     * @brief Renvoie la liste des messages des discussions de spiritisme
     * @param idPartie
     * @return liste des messages du spiritisme
     */
    public List<Message> getListMessageSpiritisme(int idPartie) {
        List<Message> result = new ArrayList<Message>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM Message_Spiritisme WHERE idPartie = "+idPartie);
            while (rs.next()) {
                Message message =
                    new Message(rs.getString("login_expediteur"), rs.getString("contenu"), idPartie, rs.getInt("date_envoi"));
                result.add(message);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    /**
     * @brief Ajoute un message sur la place du village
     * @param expediteur
     * @param contenu
     * @param idPartie 
     */
    public void ajouteMessageSalleDiscussion (String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {	
	     PreparedStatement st = conn.prepareStatement("INSERT INTO Message_Salle_Discussion VALUES (?, ?, ?, ?)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            Temps temps = new Temps();
            int date = temps.getTempsInt();
            st.setInt(4, date);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }

    /**
     * @brief Ajoute un message dans le repaire
     * @param expediteur
     * @param contenu
     * @param idPartie 
     */
    public void ajouteMessageRepaire(String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Message_Repaire VALUES (?, ?, ?, ?)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            Temps temps = new Temps();
            int date = temps.getTempsInt();
            st.setInt(4, date);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Ajoute un message sur la page de discussion spiritisme
     * @param expediteur
     * @param contenu
     * @param idPartie 
     */
    public void ajouteMessageSpiritisme(String expediteur, String contenu, int idPartie) {
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Message_Spiritisme VALUES (?, ?, ?, ?)");
            st.setInt(1, idPartie);
            st.setString(2, expediteur);
            st.setString(3, contenu);
            Temps temps = new Temps();
            int date = temps.getTempsInt();
            st.setInt(4, date);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Supprime tous les messages (place du village, repaire et spiritisme)
     * @param idPartie 
     */
    public void supprimerTousMessages(int idPartie){
        try (Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement("DELETE MESSAGE_SALLE_DISCUSSION WHERE idPartie = ?");
            st.setInt(1, idPartie);
            st.executeUpdate();
            PreparedStatement st1 = conn.prepareStatement("DELETE MESSAGE_REPAIRE WHERE idPartie = ?");
            st1.setInt(1, idPartie);
            st1.executeUpdate();
            PreparedStatement st2 = conn.prepareStatement("DELETE MESSAGE_SPIRITISME WHERE idPartie = ?");
            st2.setInt(1, idPartie);
            st2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD supp messages " + e.getMessage(), e);
        }
    }
}
