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
    
//    public Villageois getVillageois(String pseudo){
//        
//    }
}
