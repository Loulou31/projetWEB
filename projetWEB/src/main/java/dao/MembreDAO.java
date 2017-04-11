package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import modele.Membre;

/**
 *
 * @author gaunetc
 */
public class MembreDAO extends AbstractDatabaseDAO{

    public MembreDAO(DataSource ds) {
        super(ds);
    }
    
    /**
     * @brief Vérifie si les paramètres de connexion sont corrects
     * @param pseudo
     * @param mdp
     * @return true si l'identifiant et le mot de passe sont corrects, false sinon
     */
    public Boolean idCorrectConnexion(String pseudo, String mdp){
        try (Connection conn = this.getConn()) {
            PreparedStatement s = conn.prepareStatement(
                    " Select login,password From Membre Where login = ? and password = ?");
            s.setString(1, pseudo);
            s.setString(2, mdp);
            ResultSet r = s.executeQuery();
            return (r.next());
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Vérifie si le pseudo d'inscription est déjà utilisé ou non
     * @param pseudo
     * @return true si le pseudo n'est pas utilisé, false sinon
     */
    public Boolean idCorrectInscription(String pseudo){
        /*Renvoie true si le login et le password sont corrects, false sinon*/
        try (Connection conn = this.getConn()) {
            PreparedStatement s = conn.prepareStatement(
                    " Select login From Membre Where login = ?");
            s.setString(1, pseudo);
            ResultSet r = s.executeQuery();
            return !(r.next());
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Ajoute un nouveau membre à la base de données
     * @param pseudo
     * @param password 
     */
    public void ajouterMembre(String pseudo, String password){
        /* Ajoute un membre à la BD */
        try (Connection conn = getConn()) {	     
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO Membre (login, password) VALUES (?, ?)");
            st.setString(1, pseudo);
            st.setString(2, password);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /**
     * @brief Vérifie si un joueur a une partie ou non
     * @param pseudo
     * @return true si le joueur a une partie, false sinon
     */
    public boolean membreEnJeu(String pseudo){
        ResultSet rs ; 
        try(Connection conn = getConn()){
            PreparedStatement st = conn.prepareStatement
                    ("SELECT * FROM JOUEUR WHERE login = ?") ; 
            st.setString(1, pseudo) ; 
            rs = st.executeQuery() ; 
            return rs.next() ;

        }  catch (SQLException e) {
                throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    
}
