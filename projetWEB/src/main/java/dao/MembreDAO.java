/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.sql.DataSource;

/**
 *
 * @author gaunetc
 */
public class MembreDAO extends AbstractDatabaseDAO{

    public MembreDAO(DataSource ds) {
        super(ds);
    }
    
    public Boolean idCorrect(String pseudo, String password){
        //A IMPLEMENTER: Renvoie true si le login et le password sont corrects, false sinon
        return true;
    }
    
    public void ajouterMembre(String pseudo, String password){
        //A IMPLEMENTER : Ajoute un membre à la BD
    }
    
}
