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
public abstract class Villageois {
    
    protected String pseudo;
    protected int role ; 
    protected Boolean vivant;
    protected Pouvoir pouvoir;
    protected int partie ; 
    

    public Villageois(String pseudo) {
        this.pseudo = pseudo;
        this.role = 0 ; 
        this.vivant = true ;
        this.pouvoir = null ;
        this.partie = 0 ; 
    }

    public Pouvoir getPouvoir() {
        return pouvoir;
    }

    public Boolean getVivant() {
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

    public void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public void setPartie(int partie) {
        this.partie = partie;
    }
    
        public void setVivant(Boolean vivant) {
        this.vivant = vivant;
    }
        
    public abstract Boolean accesRepaireLoup();
    
    @Override
    public String toString() {
        return "Villageois{" + "pouvoir=" + pouvoir + ", vivant=" + vivant + '}';
    }
}
