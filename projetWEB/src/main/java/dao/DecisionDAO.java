/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.sql.DataSource;
import modele.Decision;

/**
 *
 * @author gaunetc
 */
public class DecisionDAO extends AbstractDatabaseDAO{

    public DecisionDAO(DataSource ds) {
        super(ds);
    }
    
    //A décommenter 
    
//    public List<Decision> getListDecisionHumains(int idPartie){
//        //Renvoie la liste des decision pour les humains
//    }
//    
//    public List<Decision> getListDecisionLoupGarous(int idPartie){
//        //Renvoie la liste des decision pour les loup-garous
//    }
}
