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
    private Villageois joueurConcerne;
    private boolean etat;
    private HashSet<Villageois> votants;

    public Decision(Villageois joueurConcerne, HashSet<Villageois> votants) {
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
    }

    public Villageois getJoueurConcerne() {
        return joueurConcerne;
    }

    public boolean isEtat() {
        return etat;
    }

    public HashSet<Villageois> getVotants() {
        return votants;
    }
    
    public void addVotants (Villageois votant){
        votants.add(votant);
    }

    @Override
    public String toString() {
        return "Decision{" + "joueurConcerne=" + joueurConcerne + ", etat=" + etat + ", votants=" + votants + '}';
    }
    
    
}
