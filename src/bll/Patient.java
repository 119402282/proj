/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import dal.DataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static bll.CsvInterface.*;
import static gui.AlertBox.displayAlert;
import static java.lang.Boolean.parseBoolean;
import java.time.LocalDate;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 *
 * @author Henry Cullen
 */
public class Patient extends User {

    private String email;
    private String eircode;
    private String mobile;
    private Date dob;
    private boolean eligibility;
    private boolean maySchedule;

    public Patient(String userId, String fName, String lName, String password, String email, String eircode, String mobile, Date dob, boolean eligibility, boolean maySchedule) {
        super(userId, fName, lName, password, "Patient");
        this.setEmail(email);
        this.setEircode(eircode);
        this.setMobile(mobile);
        //dobby is a free elf
        this.setDob(dob);
        this.setEligibility(eligibility);
        this.setMaySchedule(maySchedule);
    }
    /* legacy
    public Patient(User chad, String[] heading, String[] lineOfCsv) {
        super(chad.getUserId(), chad.getFName(),chad.getLName(), chad.getPassword(), "Patient");
        for(int i = 0; i < heading.length; i++){
            if(heading[i].equals("email")){
                this.email = lineOfCsv[i];
            } else if(heading[i].equals("eircode")){
                this.eircode = lineOfCsv[i];
            } else if(heading[i].equals("mobile")){
                this.mobile = lineOfCsv[i];
            } else if(heading[i].equals("dob")){
                this.setDob(lineOfCsv[i]);
            } else if(heading[i].equals("eligibility")){
                this.eligibility = parseBoolean(lineOfCsv[i]);
            } else if(heading[i].equals("maySchedule")){
                this.maySchedule = parseBoolean(lineOfCsv[i]);
            }
            
        }
    
    }
     */

    public Patient() {
       
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { 
        if(commaAlert("Email", email).equals("None")){
            this.email = email;
        }else {
            displayAlert("Illegal Character", commaAlert("Email",email),WARNING);
            //Alert with commaAlert("Email", email) as the arg
         }
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
         if(commaAlert("Eircode", eircode).equals("None")){
            this.eircode = eircode;
        }else {
            displayAlert("Illegal Character", commaAlert("Eircode",eircode),WARNING);
            //Alert with commaAlert("Eircode", eircode) as the arg
        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
         if(commaAlert("Mobile", mobile).equals("None")){
            this.mobile = mobile;
        }else {
            displayAlert("Illegal Character", commaAlert("Mobile",mobile),WARNING);
            //Alert with commaAlert("Mobile", mobile) as the arg
        }
    }

    public Date getDob() {
        return dob;
    }

    public String getDobString(){
        return dateToString(this.getDob());
    }
    //dylan is a coding god 
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public void setDob(String dob) {
        setDob(toDate(dob));
    }
    
   
    
    public boolean isEligibility() {
        return eligibility;
    }

    public void setEligibility(boolean eligibility) {
        this.eligibility = eligibility;
    }

    public boolean isMaySchedule() {
        return maySchedule;
    }

    public void setMaySchedule(boolean maySchedule) {
        this.maySchedule = maySchedule;
    }

    @Override
    public String toCsvString() {
        return this.getUserId()+","+this.email+","+this.eircode+","+this.mobile+"," +
                this.getDobString()+","+this.eligibility+","+ this.maySchedule;
    }
    public String superCsvString() {
        return this.getUserId() + "," + this.getFName() + "," + this.getLName() + "," + this.getPassword() + "," + this.getAccountType();
    }
    @Override
    public String toString() {
        return getUserId();
    }

    
}



