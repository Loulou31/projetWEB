/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author nicolasl
 */
public class Partie {
    private int idPartie ; 
    private String createur ; 
    private int nbJoueurs ; 
    private int dureeJour ; 
    private int dureeNuit ;
    private int heureDebut ; 
    private float probaPouvoir ; 
    private float proportionLG ; 

    public Partie(int idPartie, 
                  String createur,
                  int nbJoueurs, 
                  int dureeJour, 
                  int dureeNuit, 
                  int heureDebut, 
                  float probaPouvoir, 
                  float proportionLG) {
        this.idPartie = idPartie;
        this.createur = createur ; 
        this.nbJoueurs = nbJoueurs;
        this.dureeJour = dureeJour;
        this.dureeNuit = dureeNuit;
        this.heureDebut = heureDebut;
        this.probaPouvoir = probaPouvoir;
        this.proportionLG = proportionLG;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public int getDureeJour() {
        return dureeJour;
    }

    public int getDureeNuit() {
        return dureeNuit;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public float getProbaPouvoir() {
        return probaPouvoir;
    }

    public float getProportionLG() {
        return proportionLG;
    }

    @Override
    public String toString() {
        return "Partie{" + "idPartie=" + idPartie + ", nbJoueurs=" + nbJoueurs + ", dureeJour=" + dureeJour + ", dureeNuit=" + dureeNuit + ", heureDebut=" + heureDebut + ", probaPouvoir=" + probaPouvoir + ", proportionLG=" + proportionLG + '}';
    }
    
    
    
    
}
