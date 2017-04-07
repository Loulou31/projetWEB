/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import dao.PartieDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nicolasl
 */
public class Partie{

    private int idPartie;
    private int nbJoueursMin;
    private int nbJoueursMax;
    private int dureeJour;
    private int dureeNuit;
    //Il faut absolument que l'heure de début soit en réalité une date avec l'heure du début
    private int heureDebut;
    private float probaPouvoir;
    private float proportionLG;

    private Boolean enCours;

    //pour ne pas recharger toute la partie à chaque fois
    //il faut faire des accès qui modifient que ces 3 champs
    private List<Villageois> villageoisPresents;
    private PlaceVillage placeVillage;
    private Repaire repaire;

    private int discussionSpiritisme;
    private int contamination;

    public Partie(int idPartie,
            int nbJoueursMin,
            int nbJoueursMax,
            int dureeJour,
            int dureeNuit,
            int heureDebut,
            float probaPouvoir,
            float proportionLG,
            int discussionSpirit,
            int contamination) {
        this.idPartie = idPartie;
        this.nbJoueursMin = nbJoueursMin;
        this.nbJoueursMax = nbJoueursMax;
        this.dureeJour = dureeJour;
        this.dureeNuit = dureeNuit;
        this.heureDebut = heureDebut;
        this.probaPouvoir = probaPouvoir;
        this.proportionLG = proportionLG;
        this.discussionSpiritisme = discussionSpirit;
        this.contamination = contamination;
        //this.joueursPresents = new HashSet<Membre>();

        this.villageoisPresents = new ArrayList<Villageois>();
        this.placeVillage = new PlaceVillage(new ArrayList<Decision>(), new ArrayList<Message>());
        this.repaire = new Repaire(new ArrayList<Decision>(), new ArrayList<Message>());
    }

    public int getContamination() {
        return contamination;
    }

    public void setContamination(int contamination) {
        this.contamination = contamination;
    }

    public void setVillageoisPresents(List<Villageois> villageois) {
        this.villageoisPresents = villageois;
    }

    public void setDiscussionSpiritisme(int discussionSpiritisme) {
        this.discussionSpiritisme = discussionSpiritisme;
    }

    public int isDiscussionSpiritisme() {
        return discussionSpiritisme;
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

    public Repaire getRepaire() {
        return repaire;
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
    //A SUPPRIMER DES QUE POSSIBLE
    //devenu useless je pense (Dorian)
    public Boolean enAttente(PartieDAO partieDAO) {
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

    public Boolean complet(PartieDAO partieDAO) {
        if (partieDAO.getNbJoueurs(idPartie) == nbJoueursMax) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Partie{" + "idPartie=" + idPartie + ", nbJoueursMin=" + nbJoueursMin + ", nbJoueursMax=" + nbJoueursMax + ", dureeJour=" + dureeJour + ", dureeNuit=" + dureeNuit + ", heureDebut=" + heureDebut + ", probaPouvoir=" + probaPouvoir + ", proportionLG=" + proportionLG + '}';
    }

    public Boolean estJour() {
        Temps temps = new Temps();
        int heureActuelle = temps.getTempsInt();
        int tempsPasse = heureActuelle - heureDebut;
        int dureeJournee = dureeJour + dureeNuit;
        int joursPasses = tempsPasse / dureeJournee;
        int heureJournee = tempsPasse - joursPasses * dureeJournee;

        if (dureeJour >= heureJournee) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean estNuit() {
        return !estJour();
    }

    //entier à 1 si jour, à 0 si nuit
    //tous les temps sont en secondes
    public String tempsAvantChangement(int jon) {
        System.out.print("11111111111111111");
        Temps temps = new Temps();
        int heureActuelle = temps.getTempsInt();
        int tempsPasse = heureActuelle - heureDebut;
        int dureeJournee = dureeJour + dureeNuit;
        int joursPasses = tempsPasse / dureeJournee;
        int heureJournee = tempsPasse - joursPasses * dureeJournee;

        int tempsAvant;
        System.out.print("22222222222");
        if (jon == 1) {
            tempsAvant = dureeJour - heureJournee;
            System.out.print("33333333333333");
            return "Il reste : " + temps.tempsSecToString(tempsAvant) + "avant la nuit.";
        } else {
            tempsAvant = dureeJournee - heureJournee;
            return "Il reste : " + temps.tempsSecToString(tempsAvant) + "avant le jour.";
        }

    }

    //retourne le nombre de joueurs vivants dans la partie
    public int getNbJoueursVivants(int id) {
        int nb = 0;
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            if (it.next().getVivant() == 1) {
                nb++;
            }
        }
        return nb;
    }

    //retourne le nombre de joueurs dans la partie
    public int getNbJoueurs(int id) {
        return this.villageoisPresents.size();
    }

    public List<Villageois> getListVillageoisVivants() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getVivant() == 1) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListVillageoisMorts() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getVivant() == 0) {
                result.add(next);
            }
        }
        return result;
    }

    public void mortVillageois(String login) {
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getPseudo().equals(login)) {
                next.setVivant(0);
            }
        }
    }

    public List<Villageois> getListVillageoisSansPouvoir() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getPouvoir() == null) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListHumainsSansPouvoir() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getPouvoir() == null && next.getRole() == 0) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListLoupsSansPouvoir() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getPouvoir() == null && next.getRole() == 1) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListHumainsMorts() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 0 && next.getVivant() == 0) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListHumainsVivants() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 0 && next.getVivant() == 1) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListHumains() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 0) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListLoups() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 1) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListLoupsVivants() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 1 && next.getVivant() == 1) {
                result.add(next);
            }
        }
        return result;
    }

    public List<Villageois> getListLoupsMorts() {
        List<Villageois> result = new ArrayList<Villageois>();
        for (Iterator<Villageois> it = this.villageoisPresents.iterator(); it.hasNext();) {
            Villageois next = it.next();
            if (next.getRole() == 1 && next.getVivant() == 0) {
                result.add(next);
            }
        }
        return result;
    }

    //Sera utilisé pour les message de le place du village pour savoir quels messages doivent être affichés
    public Boolean dansJournee(Date date) {
        return true;
    }

    //Sera utilisé pour les message du repaire pour savoir quels messages doivent être affichés
    public Boolean dansNuitee(Date date) {
        return true;
    }

    public int jourEntite(int date) {
        int tempsPasse = date - heureDebut;
        int dureeJournee = dureeJour + dureeNuit;
        int journee = tempsPasse / dureeJournee;
        if (tempsPasse - journee * dureeJournee < dureeJour) {
            return (journee + 1);
        } else {
            return -(journee + 1);
        }
    }

    public int journeeActuelle() {
        Temps temps = new Temps();
        int heureActuelle = temps.getTempsInt();
        int tempsPasse = heureActuelle - heureDebut;
        return (tempsPasse / (dureeJour + dureeNuit) + 1);
    }

    public ArrayList<Message> messageDuJour(List<Message> m) {
        ArrayList<Message> messages = new ArrayList<Message>();
        for (Message message : m) {
            if (jourEntite(message.getDate()) == Math.abs(journeeActuelle())) {
                messages.add(message);
            }
        }
        return (messages);
    }

    public Boolean decisionsCorrompues(List<Decision> d) {
        Boolean b = false;
        for (Decision decision : d) {
            if (jourEntite(decision.getDate()) != Math.abs(journeeActuelle())) {
                b = true;
            }
        }
        return (b);
    }
    
    public Boolean decisionsCorrompuesNuit(List<Decision> d) {
        Boolean b = false;
        for (Decision decision : d) {
            if (jourEntite(decision.getDate()) != Math.abs(journeeActuelle())-1) {
                b = true;
            }
        }
        return (b);
    }

    public ArrayList<String> messagesArchives(List<Message> m) {
        ArrayList<String> messages = new ArrayList<>();
        int jour = 0;
        Message message;
        Iterator<Message> it = m.iterator();
        while(it.hasNext()){
            message = it.next();
            if(Math.abs(jourEntite(message.getDate()))!=jour){
                jour = Math.abs(jourEntite(message.getDate()));
                messages.add("///////////////// JOUR " + jour + " ///////////////////\n");
            }
            messages.add("@" + message.getExpediteur()+ " :  " + message.getContenu()+"\n");
        }
        return (messages);
    }
    
    public ArrayList<Message> triListe(List<Message> m) {
        Collections.sort(m, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return (m2.getDate()-m1.getDate());
            }
    });
        return new ArrayList<>(m);
    }
    
    public ArrayList<Message> triListeArchive(List<Message> m) {
        Collections.sort(m, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return (m1.getDate()-m2.getDate());
            }
    });
        return new ArrayList<>(m);
    }
    
    
    

}
