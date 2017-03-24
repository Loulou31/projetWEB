/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    
//    public List<Villageois> getListVillageois(int idPartie){
//        
//    }
    
//    public Villageois getVillageois(String pseudo){
//        
//    }
}
