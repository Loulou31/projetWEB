/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author nicolasl
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import modele.Partie ; 


public class PartieDAO extends AbstractDatabaseDAO {
    
    public PartieDAO(DataSource ds) {
        super(ds);
    }
    
    public List<Partie> getListeParties() {
        List<Partie> result = new ArrayList<Partie>();
        try (
	     Connection conn = getConn();
	     Statement st = conn.createStatement();
	     ) {
            ResultSet rs = st.executeQuery("SELECT * FROM PARTIE");
            while (rs.next()) {
                Partie partie =
                    new Partie(rs.getInt("IdPartie"), 
                               rs.getString("login"),
                               rs.getInt("NbJoueurs"),
                               rs.getInt("DureeJour"),
                               rs.getInt("DureeNuit"),
                               rs.getInt("HeureDebut"), 
                               rs.getFloat("ProbaPouvoir"), 
                               rs.getFloat("ProportionLG"));
                result.add(partie);
            }
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
	}
	return result;
    }
    
    
    public void ajouterPartie(int nbJoueurs, 
                              String createur,
                              int dureeJour,
                              int dureeNuit, 
                              int heureDebut, 
                              float probaPouvoir, 
                              float proportionLG) {
        try (
	     Connection conn = getConn();
	     PreparedStatement st = conn.prepareStatement
	       ("INSERT INTO PARTIE (login, NbJoueur, DureeJour, DureeNuit, HeureDebut, ProbaPouvoir, ProportionLG) VALUES (?, ?, ?, ?, ?, ?)");
	     ) {
            st.setString(1, createur) ;
            st.setInt(2, nbJoueurs);
            st.setInt(3, dureeJour);
            st.setInt(4, dureeNuit);
            st.setInt(5, heureDebut);
            st.setFloat(6, probaPouvoir);
            st.setFloat(7, proportionLG);
            
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
    public Partie getPartie(int id) {
        Partie partie; 
        try(Connection conn = getConn()){
        PreparedStatement st = conn.prepareStatement
         ("SELECT * FROM PARTIE WHERE IdPartie = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next() ; 
        partie = new Partie(rs.getInt("IdPartie"),
                               rs.getString("login"),
                               rs.getInt("NbJoueurs"),
                               rs.getInt("DureeJour"),
                               rs.getInt("DureeNuit"),
                               rs.getInt("HeureDebut"), 
                               rs.getFloat("ProbaPouvoir"), 
                               rs.getFloat("ProportionLG"));
        } catch(SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
        return partie ; 
    }
    
    /** Supprime une partie après qu'elle soit terminée **/
    public void supprimerPartie(int id) {
        try(Connection conn = getConn()){
        PreparedStatement st = conn.prepareStatement
         ("DELETE FROM PARTIE WHERE IdPartie = ?");
        st.setInt(1, id);
        st.executeUpdate();
        } catch(SQLException e){
            throw new DAOException("Erreur BD " + e.getMessage(), e);
        }
    }
    
}
