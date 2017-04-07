/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;
import java.util.Date;

/**
 *
 * @author gaunetc
 */
public class Message {
    
    private String expediteur; //villageois
    private String contenu; 
    private Date date; // long
    private int idPartie;//non merci

    public int getIdPartie() {
        return idPartie;
    }

    public Message(String expediteur, String contenu, int IdPartie) {
        this.expediteur = expediteur;
        this.contenu = contenu;
        this.date = new Date();
        this.idPartie = idPartie;
    }
        

    public String getExpediteur() {
        return expediteur;
    }

    public String getContenu() {
        return contenu;
    }

    public Date getDate() {
        return date;
    }
    
    
    
}
