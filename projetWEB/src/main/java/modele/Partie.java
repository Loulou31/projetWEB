/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import dao.PartieDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author nicolasl
 */
public class Partie {
    private int idPartie ; 
    private int nbJoueursMin ; 
    private int nbJoueursMax ; 
    private int dureeJour ; 
    private int dureeNuit ;
    //Il faut absolument que l'heure de début soit en réalité une date avec l'heure du début :/
    private int heureDebut ; 
    private float probaPouvoir ; 
    private float proportionLG ; 
    //private Set<Membre> joueursPresents;
    
    static int numeroPartie = 0;

    public Partie(int idPartie, 
                  int nbJoueursMin, 
                  int nbJoueursMax, 
                  int dureeJour, 
                  int dureeNuit, 
                  int heureDebut, 
                  float probaPouvoir, 
                  float proportionLG) {
        this.idPartie = idPartie;
        this.nbJoueursMin = nbJoueursMin;
        this.nbJoueursMax = nbJoueursMax;
        this.dureeJour = dureeJour;
        this.dureeNuit = dureeNuit;
        this.heureDebut = heureDebut;
        this.probaPouvoir = probaPouvoir;
        this.proportionLG = proportionLG;
        //this.joueursPresents = new HashSet<Membre>();
    }

    static public int getNumeroPartie(){
        return numeroPartie++;
    }
    
    public int getIdPartie() {
        return idPartie;
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

    public int getHeureDebut() {
        return heureDebut;
    }

    public float getProbaPouvoir() {
        return probaPouvoir;
    }

    public float getProportionLG() {
        return proportionLG;
    }
    
    
    /*public int getNombreJoueursPresents(){
        return this.joueursPresents.size();
    }
    
    
    public String getListeJoueursPresents(){
        String s = "La liste des membres actuellement en attente sont : \n";
        Iterator<Membre> it = this.joueursPresents.iterator();
        while (it.hasNext()) {
            s += it.next().getPseudo() + "\n";
        }
        return s;
    }
    
    public void ajoutMembre(Membre m){
        this.joueursPresents.add(m);
    }*/
    
    //devenu useless je pense (Dorian)
    public Boolean enAttente(PartieDAO partieDAO){
//        SimpleDateFormat d = new SimpleDateFormat ("dd/MM/yyyy" );
//        SimpleDateFormat h = new SimpleDateFormat ("hh:mm");
//        Date currentTime_1 = new Date();
//        String dateString = d.format(currentTime_1);
//        String heureString = h.format(currentTime_1);
//        //A IMPLEMENTER: gestion du temps.
//        if (partieDAO.getNbJoueurs(idPartie)<nbJoueursMin){
//            return true;
//        }else{
//            return false;
//        }
         return false;
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
        return "Partie{" + "idPartie=" + idPartie + ", nbJoueursMin=" + nbJoueursMin + ", nbJoueursMax=" + nbJoueursMax + ", dureeJour=" + dureeJour + ", dureeNuit=" + dureeNuit + ", heureDebut=" + heureDebut + ", probaPouvoir=" + probaPouvoir + ", proportionLG=" + proportionLG + '}';
    }

    
    
    
    
    
}
