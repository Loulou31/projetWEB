/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author gaunetc
 */
public class Humain extends Villageois{

    public Humain(String pseudo) {
        super(pseudo);
    }
    
    @Override
    public Boolean accesRepaireLoup(){
        return false;
    }
}
