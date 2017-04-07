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
public class Repaire extends SalleDiscussion{
    
    String nom = "Repaire des Loups-Garous";

    public Repaire(ArrayList<Decision> decisions, ArrayList<Message> messages) {
        super(decisions, messages);
    }

    public String getNom(){
        return this.nom;
    }
    
}
