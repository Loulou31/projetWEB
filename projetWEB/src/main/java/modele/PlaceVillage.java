/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;

/**
 *
 * @author gaunetc
 */
public class PlaceVillage extends SalleDiscussion{
    
    String nom = "Place du Village";

    public PlaceVillage(ArrayList<Decision> decisions, ArrayList<Message> messages) {
        super(decisions, messages);
    }

    
    public String getNom(){
        return this.nom;
    }
    
}
