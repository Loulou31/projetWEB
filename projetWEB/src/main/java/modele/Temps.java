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
    
    public GregorianCalendar intToCal(int date){
        GregorianCalendar cal = new GregorianCalendar();
        //on ajoute les 3 derniers chiffres
        cal.setTimeInMillis((long)(date)*1000);
        return cal;
    } 
    
    public String calToString(GregorianCalendar gc){    
    	return String.valueOf(gc.get(gc.DATE))
                + "-"
                + String.valueOf(gc.get(gc.MONTH) + 1)
                + "-"
                + String.valueOf(gc.get(gc.YEAR))
                + "  "
                + new DecimalFormat("00").format(gc.get(gc.HOUR_OF_DAY))
                + ":"
                + new DecimalFormat("00").format(gc.get(gc.MINUTE))
                ;
    }
    
    
    public int calToInt(int heures, int minutes){
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH), heures, minutes);
        return (int)(Math.floor(cal2.getTimeInMillis())/1000);
    }
    
    public Boolean estJour(int idPartie){
        return (true);
    }
    
    public Boolean estNuit(int idPartie){
        return (false);
    }
    
}
