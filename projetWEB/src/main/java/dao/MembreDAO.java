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
    
    public Boolean idCorrectConnexion(String pseudo, String mdp){
        /*Renvoie true si le login et le password sont corrects, false sinon*/
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
    
    //on va dire que c'est bon
    public boolean memberHasPartie(String pseudo){
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
