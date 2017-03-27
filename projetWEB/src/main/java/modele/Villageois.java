/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author gaunetc
 */
public class Villageois {
    
    protected String pseudo;
    protected int role ; 
    protected int vivant;
    protected String pouvoir;
    protected int partie ; 
    

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
    
    
    
    @Override
    public String toString() {
        return "Villageois{" + "pouvoir=" + pouvoir + ", vivant=" + vivant + '}';
    }
}
