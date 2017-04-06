/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author gaunetc
 */
public abstract class SalleDiscussion {
    protected HashSet<Decision> decisions;
    protected HashSet<Message> messages;

    public SalleDiscussion(HashSet<Decision> decisions, HashSet<Message> messages) {
        this.decisions = decisions;
        this.messages = messages;
    }

    public HashSet<Decision> getDecisions() {
        return decisions;
    }

    public HashSet<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message m){
        messages.add(m);
    }
    
    public void addDecision(Decision d){
        decisions.add(d);
    }
    
    public Boolean decisionRatifie() {
        for (Iterator<Decision> it = this.decisions.iterator(); it.hasNext();) {
            if (it.next().isEtat()) {
                return true;
            }
        }
        return false;
    }
    
    public String pseudoDecisionHumainRatifie(){
        for (Iterator<Decision> it = this.decisions.iterator(); it.hasNext();) {
            Decision next = it.next();
            if (next.isEtat()) {
                return next.getJoueurConcerne();
            }
        }
        return null;
    }
}

