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
    
    public long getTempsLong(){
        Calendar cal = new GregorianCalendar();
        return cal.getTimeInMillis();
    }
    
    public int getTempsInt(){
        Calendar cal = new GregorianCalendar();
        return (int)(cal.getTimeInMillis()/1000);        
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
    
    public Boolean estApres (int apres, long avant){
        GregorianCalendar calApres = new GregorianCalendar();
        GregorianCalendar calAvant = new GregorianCalendar();
        calApres.setTimeInMillis((long)apres*1000);
        calAvant.setTimeInMillis(avant);
        return calApres.after(calAvant);
    }
    
    
    public int calToInt(int heures, int minutes){
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH), heures, minutes);
        return (int)(Math.floor(cal2.getTimeInMillis())/1000);
    }
    
    //transforme les durees en secondes
    public int dureeToInt(int heures, int minutes){
        return 3600 * heures + 60 * minutes;
    }
    
    public String tempsSecToString (int temps){
        int heures;
        int minutes;
        int secondes;
        heures = temps / 3600;
        temps -= heures * 3600;
        minutes = temps / 60;
        temps -= minutes * 60;
        secondes = temps;
        return Integer.toString(heures) + " h " + Integer.toString(minutes) + " m " + Integer.toString(secondes) + " s ";   
    }
    
}
