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
    private int idPartie; 
    private String joueurConcerne;
    private boolean etat;
    private HashSet<String> votants;
    private int nbVote ; 

    public Decision(String joueurConcerne, HashSet<String> votants) {
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
        this.nbVote = 0;
    }

    public Decision(String joueurConcerne, HashSet<String> votants, int nbVote, int idPartie) {
        this.idPartie = idPartie;
        this.joueurConcerne = joueurConcerne;
        this.votants = votants;
        this.etat = false;
        this.nbVote = nbVote;
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

    public void setNbVote(int nbVote) {
        this.nbVote = nbVote;
    }

    public int getNbVote() {
        return this.nbVote;
    }
    
    

    @Override
    public String toString() {
        return "Decision{" + "joueurConcerne=" + joueurConcerne + ", etat=" + etat + ", votants=" + votants + '}';
    }
    
    
}
