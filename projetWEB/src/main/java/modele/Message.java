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
    private Villageois expediteur;
    private String contenu;
    private Date date;

    public Message(Villageois expediteur, String contenu) {
        this.expediteur = expediteur;
        this.contenu = contenu;
        this.date = new Date();
    }

    public Villageois getExpediteur() {
        return expediteur;
    }

    public String getContenu() {
        return contenu;
    }

    public Date getDate() {
        return date;
    }
    
    
    
}