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
    
}
