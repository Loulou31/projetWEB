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
public class LoupGarou extends Villageois{

    public LoupGarou(Pouvoir pouvoir, Boolean vivant) {
        super(pouvoir, vivant);
    }
    
    @Override
    public Boolean accesRepaireLoup(){
        return true;
    }
}
