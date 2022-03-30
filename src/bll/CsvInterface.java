/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static gui.AlertBox.displayAlert;
import java.time.LocalDate;
import java.time.ZoneId;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static jdk.nashorn.internal.objects.NativeString.substring;


/**
 *
 * @author Henry Cullen
 */
public interface CsvInterface {
    String toCsvString();
    
    static String commaAlert(String name, String value){
       if(value.contains(",")){
           return "The "+ name+" field entered above contains the illegal character ','. Please use a different character";
       } else{
           return "None";
       }
    }

    static Date toDate(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);

            Date date = formatter.parse(strDate);
            return date;
        } catch (ParseException ex) {
            displayAlert("Date Error", "The date could not be changed to dd/MM/yyyy HH:mm", ERROR);
            Logger.getLogger(CsvInterface.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    static Date toDateShort(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            return formatter.parse(strDate);
        } catch (ParseException ex) {
            displayAlert("Date Error", "The date could not be changed to dd/MM/yyyy", ERROR);
            Logger.getLogger(CsvInterface.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    static LocalDate dateToLocal(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    static String dateToString(Date theDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        String date = null;
        date = formatter.format(theDate);
        return date;
    }

    static String dateToStringShort(Date theDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String date = null;
        date = formatter.format(theDate);
        return date;
    }

    static String dateToStringTime(Date theDate){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String date = null;
        date = formatter.format(theDate);
        return date;
    }


    static int getDaysBetween(Date firstDate, Date secondDate){

        int days;

        long daysInMilliseconds = Math.abs(secondDate.getTime() - firstDate.getTime());
        long daysLong = TimeUnit.DAYS.convert(daysInMilliseconds, TimeUnit.MILLISECONDS);
        days = (int) daysLong;
        return days;
    }
    
    static String formatEircode(String spaced){
        spaced = spaced.replaceAll("\\s", "");
        if(spaced.length()==7){
            spaced = spaced.substring(0, 3) +" " +spaced.substring(3,7);
        } else{
            spaced = "false";
        }
        System.out.println(spaced.length());
        return spaced;
    }

}
