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
public class PlaceVillage extends SalleDiscussion{

    public PlaceVillage(HashSet<Decision> decisions, HashSet<Message> messages) {
        super(decisions, messages);
    }
    
}
