/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.HashSet;

/**
 *
 * @author gaunetc
 */
public class Decision {
    private String joueurConcerne;
    private boolean etat;
    private HashSet<String> votants;

    public Decision(String joueurConcerne, HashSet<String> votants) {
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
    }

    public String getJoueurConcerne() {
        return joueurConcerne;
    }

    public boolean isEtat() {
        return etat;
    }

    public HashSet<String> getVotants() {
        return votants;
    }
    
    public void addVotants (String votant){
        votants.add(votant);
    }

    @Override
    public String toString() {
        return "Decision{" + "joueurConcerne=" + joueurConcerne + ", etat=" + etat + ", votants=" + votants + '}';
    }
    
    
}
