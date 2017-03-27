/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author lenetd
 */
public final class Temps {
    
    public static String getTemps(){
        Calendar cal = new GregorianCalendar();
        int heure = cal.HOUR_OF_DAY;
        int min = cal.MINUTE;
        int sec = cal.SECOND;
        
        return "L'heure d'arrivée sur la page est : "
                + new DecimalFormat("00").format(cal.get(heure))
                + ":"
                + new DecimalFormat("00").format(cal.get(min))
                + ":"
                + new DecimalFormat("00").format(cal.get(sec));
    }
    
}
