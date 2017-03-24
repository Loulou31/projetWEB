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

    public Humain(boolean vivant, Pouvoir pouvoir) {
        super(pouvoir, vivant);
    }
    
    @Override
    public Boolean accesRepaireLoup(){
        return false;
    }
}
