/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import static bll.CsvInterface.commaAlert;
import static gui.AlertBox.displayAlert;
import static java.lang.Integer.parseInt;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 *
 * @author Dylan
 */
public class User implements CsvInterface{

    private String userId;
    private String fName;
    private String lName;
    private String password;
    private String accountType;


    public User(String userId, String fName, String lName, String password, String accountType) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
        this.accountType = accountType;
    }

    public User(Patient pat){
        this.userId = pat.getUserId();
        this.fName = pat.getFName();
        this.lName = pat.getLName();
        this.password = pat.getPassword();
        this.accountType = "Patient";
    }
    
    
    public User(Clinician pat){
        this.userId = pat.getUserId();
        this.fName = pat.getFName();
        this.lName = pat.getLName();
        this.password = pat.getPassword();
        this.accountType = "Clinician";
    }

    public User(PHA pat){
        this.userId = pat.getUserId();
        this.fName = pat.getFName();
        this.lName = pat.getLName();
        this.password = pat.getPassword();
        this.accountType = "PHA";
    }

    /* legacy
    public User(String[] headingsOfCsv, String[] lineOfCsv) {
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equalsIgnoreCase("userId")){
                this.userId = lineOfCsv[i];
            } else if(headingsOfCsv[i].equals("fName")){
                this.fName = lineOfCsv[i];
            } else if(headingsOfCsv[i].equals("lName")){
                this.lName = lineOfCsv[i];
            } else if(headingsOfCsv[i].equals("password")){
                this.password = lineOfCsv[i];
            } else if(headingsOfCsv[i].equalsIgnoreCase("accountType")){
                this.accountType = lineOfCsv[i];
            }
        }
    }
     */

    public User() {

    }


    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        if(commaAlert("First Name", fName).equals("None")){
            this.fName = fName;
        }else {
            displayAlert("Illegal Character", commaAlert("First Name",fName),WARNING);
            //Alert with commaAlert("First Name", fName) as the arg
         }
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        if(commaAlert("Last Name",lName).equals("None")){
            this.lName = lName;
        }else {
            displayAlert("Illegal Character", commaAlert("Last Name",lName),WARNING);
            //alert to tell user that they cant have a comma in their password
        }
        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(commaAlert("Password", password).equals("None")){
            this.password = password;
        }else {
            displayAlert("Illegal Character", commaAlert("Password",password),WARNING);
            //Alert with commaAlert("Password", password) as the arg
         }
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toCsvString() {
        return userId + "," + fName + "," + lName + "," + password + "," + accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
