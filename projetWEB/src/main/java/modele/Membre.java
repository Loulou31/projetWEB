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
public class Membre {
    String pseudo;

    public Membre(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }
    

    @Override
    public String toString() {
        return "Membre{" + "pseudo=" + pseudo + '}';
    }
    
    
}
