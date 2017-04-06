/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;

/**
 *
 * @author gaunetc
 */
public abstract class SalleDiscussion {
    protected ArrayList<Decision> decisions;
    protected ArrayList<Message> messages;

    public SalleDiscussion(ArrayList<Decision> decisions, ArrayList<Message> messages) {
        this.decisions = decisions;
        this.messages = messages;
    }

    public ArrayList<Decision> getDecisions() {
        return decisions;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    
    public ArrayList<Message> getMessagesDuJour() {
        return messages;
    }
    
    public ArrayList<Message> getMessagesDeLaNuit() {
        return messages;
    }

    public void addMessage(Message m){
        messages.add(m);
    }
    
    public void addDecision(Decision d){
        decisions.add(d);
    }
    
}
