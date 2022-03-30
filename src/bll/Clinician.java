/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import dal.DataAccess;

/**
 *
 * @author Henry Cullen
 */
public class Clinician extends User{

    private String position;

    public Clinician(String userId, String fName, String lName, String password, String position) {
        super(userId, fName, lName, password, "Clinician");
        this.setPosition(position);
    }
/* legacy
    public Clinician( User chad, String[] headingsOfCsv, String[] lineOfCsv) {
        super(chad.getUserId(), chad.getFName(),chad.getLName(), chad.getPassword(), "Clinician");

        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equals("position")){
                this.setPosition(lineOfCsv[i]);
            }
        }
    }

 */

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toCsvString() {
        return this.getUserId()+","+this.position;
    }
    public String superCsvString() {
        return this.getUserId() + "," + this.getFName() + "," + this.getLName() + "," + this.getPassword() + "," + this.getAccountType();
    }
    @Override
    public String toString() {
        return getUserId();
    }
}
