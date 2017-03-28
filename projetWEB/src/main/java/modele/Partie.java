/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import dao.PartieDAO;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nicolasl
 */
public class Partie {
    private int idPartie ; 
    private String createur ; 
    private int nbJoueursMin ; 
    private int nbJoueursMax ; 
    private int dureeJour ; 
    private int dureeNuit ;
    //Il faut absolument que l'heure de début soit en réalité une date avec l'heure du début :/
    private long heureDebut ; 
    private float probaPouvoir ; 
    private float proportionLG ; 

    public Partie(int idPartie, 
                  String createur,
                  int nbJoueursMin, 
                  int nbJoueursMax, 
                  int dureeJour, 
                  int dureeNuit, 
                  long heureDebut, 
                  float probaPouvoir, 
                  float proportionLG) {
        this.idPartie = idPartie;
        this.createur = createur ; 
        this.nbJoueursMin = nbJoueursMin;
        this.nbJoueursMax = nbJoueursMax;
        this.dureeJour = dureeJour;
        this.dureeNuit = dureeNuit;
        this.heureDebut = heureDebut;
        this.probaPouvoir = probaPouvoir;
        this.proportionLG = proportionLG;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public String getCreateur() {
        return createur;
    }

    public int getNbJoueursMin() {
        return nbJoueursMin;
    }

    public int getNbJoueursMax() {
        return nbJoueursMax;
    }

    public int getDureeJour() {
        return dureeJour;
    }

    public int getDureeNuit() {
        return dureeNuit;
    }

    public long getHeureDebut() {
        return heureDebut;
    }

    public float getProbaPouvoir() {
        return probaPouvoir;
    }

    public float getProportionLG() {
        return proportionLG;
    }
    
    public Boolean enAttente(PartieDAO partieDAO){
        SimpleDateFormat d = new SimpleDateFormat ("dd/MM/yyyy" );
        SimpleDateFormat h = new SimpleDateFormat ("hh:mm");
        Date currentTime_1 = new Date();
        String dateString = d.format(currentTime_1);
        String heureString = h.format(currentTime_1);
        //A IMPLEMENTER: gestion du temps.
        if (partieDAO.getNbJoueurs(idPartie)<nbJoueursMin){
            return true;
        }else{
            return false;
        }
    }
    public Boolean complet(PartieDAO partieDAO){
        if (partieDAO.getNbJoueurs(idPartie)==nbJoueursMax){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public String toString() {
        return "Partie{" + "idPartie=" + idPartie + ", createur=" + createur + ", nbJoueursMin=" + nbJoueursMin + ", nbJoueursMax=" + nbJoueursMax + ", dureeJour=" + dureeJour + ", dureeNuit=" + dureeNuit + ", heureDebut=" + heureDebut + ", probaPouvoir=" + probaPouvoir + ", proportionLG=" + proportionLG + '}';
    }

    
    
    
    
    
}
