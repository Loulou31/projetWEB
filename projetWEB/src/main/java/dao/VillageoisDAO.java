/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.* ; 
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Villageois;

/**
 *
 * @author gaunetc
 */
public class VillageoisDAO extends AbstractDatabaseDAO{

    public VillageoisDAO(DataSource ds) {
        super(ds);
    }
    
    public List<Villageois> getListVillageois(int idPartie){
        List<Villageois> result = new ArrayList<Villageois>() ;
        try(
                Connection conn = getConn();
                Statement st = conn.createStatement() ; 
                 ) {
                ResultSet rs = st.executeQuery("SELECT Pseudo FROM JOUEUR") ; 
                // Pas possible d'instancier un villageois car abstract
            }  catch (SQLException e) {
                    throw new DAOException("Erreur BD " + e.getMessage(), e);
            }
        return result ; 
    }
    
    public Villageois getVillageois(String pseudo){
        Villageois villageois ;
        try(
                Connection conn = getConn();
                 ) {
                PreparedStatement st = conn.prepareStatement    
                ("SELECT * FROM JOUEUR WHERE login = ?") ; 
                st.setString(1, pseudo) ; 
                ResultSet rs = st.executeQuery();
                rs.next() ; 
                villageois = new Villageois (rs.getString("login"),
                                             rs.getInt("rolePartie"),
                                             rs.getInt("Statut"),
                                             rs.getString("Pouvoir"),
                                             rs.getInt("IdPartie")) ; 
            }  catch (SQLException e) {
                    throw new DAOException("Erreur BD getVillageois()" + e.getMessage(), e);
            }
        return villageois ; 
    }
    
    public void addPlayer(String pseudo, int idPartie){
        try(Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement    
                ("INSERT INTO JOUEUR VALUES  (?, 0, 1, null, ?) ") ;
            st.setString(1, pseudo) ; 
            st.setInt(2, idPartie) ; 
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public void deletePlayer(String pseudo){
        try(Connection conn = getConn()){
        PreparedStatement st = conn.prepareStatement
         ("DELETE FROM Joueur WHERE id = ?");
        st.setString(1, pseudo);
        st.executeUpdate();
        } catch(SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /* Defini le role (humain ou LG) du joueur en début de partie */
    public void updatePlayerRole(String pseudo, int role){
        try(Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement    
                ("UPDATE JOUEUR SET rolePartie = ? WHERE login = ?") ;
            st.setInt(1, role) ; 
            st.setString(2, pseudo) ; 
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /* Mise à jour du statut vivant ou mort du joueur */
    public void updatePlayerStatus(String pseudo, int statut){
        try(Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement    
                ("UPDATE JOUEUR SET Statut = ? WHERE login = ?") ;
            st.setInt(1, statut) ; 
            st.setString(2, pseudo) ; 
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    /* Mise à jour du pouvoir du joueur */
    public void updatePlayerStatus(String pseudo, String pouvoir){
        try(Connection conn = getConn()) {
            PreparedStatement st = conn.prepareStatement    
                ("UPDATE JOUEUR SET Pouvoir = ? WHERE login = ?") ;
            st.setString(1, pouvoir) ; 
            st.setString(2, pseudo) ; 
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
}
