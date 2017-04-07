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
    
    private int idPartie; // car besoin nb joueurs total
    private int date;
    private String joueurConcerne;
    private boolean etat; 
    private HashSet<String> votants; 

    public Decision(String joueurConcerne, HashSet<String> votants) {
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
    }

    public Decision(String joueurConcerne, HashSet<String> votants, int nbVote, int idPartie) {
        this.idPartie = idPartie;
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
    }

    public int getIdPartie() {
        return idPartie;
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


    public int getNbVote() {
        return this.votants.size();
    }




    @Override
    public String toString() {
        return "Decision{" + "joueurConcerne=" + joueurConcerne + ", etat=" + etat + ", votants=" + votants + '}';
    }
    
    
}
