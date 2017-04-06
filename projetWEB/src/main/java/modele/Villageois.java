/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author gaunetc
 */
public class Villageois {
    
    private String pseudo;
    private int role ; // = 0 humain , = 1 loup
    private int vivant; // 0 = mort , 1 = vivant
    private String pouvoir;
    
    //peut être utile mais à supprimer je pense
    private int partie ; 
    

    public Villageois(String pseudo) {
        this.pseudo = pseudo;
        this.role = 0 ; 
        this.vivant = 1 ;
        this.pouvoir = null ;
        this.partie = 0 ; 
    }

    public Villageois(String pseudo, int role, int vivant, String pouvoir, int partie) {
        this.pseudo = pseudo;
        this.role = role;
        this.vivant = vivant;
        this.pouvoir = pouvoir;
        this.partie = partie;
    }
    
    

    public String getPouvoir() {
        return pouvoir;
    }

    public int getVivant() {
        return vivant;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getRole() {
        return role;
    }
    
    public String getRoleString() {
        if (this.getRole() == 0) {
            return "Humain";
        } else {
            return "Loup Garou";
        }
    }

    public int getPartie() {
        return partie;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setPouvoir(String pouvoir) {
        this.pouvoir = pouvoir;
    }

    public void setPartie(int partie) {
        this.partie = partie;
    }
    
        public void setVivant(int vivant) {
        this.vivant = vivant;
    }
        
    public Boolean accesRepaireLoup() {
        return (this.role == 1) ; 
    }
    
    public Boolean estEnPartie(){
        return (this.partie != 0);
    }

    
    @Override
    public String toString() {
        return "Villageois{"+ "pseudo=" + pseudo + ", pouvoir=" + pouvoir + ", vivant=" + vivant + '}';
    }
}
