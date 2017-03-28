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
public class Temps {
    
    public String getTemps(){
        Calendar cal = new GregorianCalendar();
        int heure = cal.HOUR_OF_DAY;
        int min = cal.MINUTE;
        int sec = cal.SECOND;
        
        return "L'heure est : "
                + new DecimalFormat("00").format(cal.get(heure))
                + ":"
                + new DecimalFormat("00").format(cal.get(min))
                + ":"
                + new DecimalFormat("00").format(cal.get(sec));
    }
    
    public GregorianCalendar longToCal(long date){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(date);
        return cal;
    } 
    
    public long calToLong(int heures, int minutes){
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH), heures, minutes);
        return cal2.getTimeInMillis();
    }
    
    public Boolean estJour(int idPartie){
        return (true);
    }
    
    public Boolean estNuit(int idPartie){
        return (false);
    }
    
}
