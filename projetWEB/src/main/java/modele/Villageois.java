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
    protected Pouvoir pouvoir;
    protected Boolean vivant;

    public Villageois(Pouvoir pouvoir, Boolean vivant) {
        this.pouvoir = pouvoir;
        this.vivant = vivant;
        this.pseudo = pseudo;
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

    public void setVivant(Boolean vivant) {
        this.vivant = vivant;
    }
        
    public abstract Boolean accesRepaireLoup();
    
    @Override
    public String toString() {
        return "Villageois{" + "pouvoir=" + pouvoir + ", vivant=" + vivant + '}';
    }
}
