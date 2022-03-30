/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import java.util.Arrays;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.String.valueOf;

/**
 *
 * @author Henry Cullen
 */
public class Vaccine implements CsvInterface {

    private int vaccId;
    private String vaccName;
    private boolean vaccAuthorized;
    private String vaccTypeDesc;
    private int reqDoses;
    private double vaccineTemp;
    private double efficacy;

    public Vaccine(int vaccId, String vaccName, boolean vaccAuthorized, String vaccTypeDesc, int reqDoses, double vaccineTemp, double efficacy) {
        this.vaccId = vaccId;
        this.vaccName = vaccName;
        this.vaccAuthorized = vaccAuthorized;
        this.vaccTypeDesc = vaccTypeDesc;
        this.reqDoses = reqDoses;
        this.vaccineTemp = vaccineTemp;
        this.efficacy = efficacy;
    }

    /* legacy
    public Vaccine(String[] heading, String[] lineOfCsv) {
        for(int i = 0; i < heading.length; i++){
            if(heading[i].equals("vaccineId")){
                this.setVaccId(lineOfCsv[i]);
            } else if(heading[i].equals("vaccName")){
                this.setVaccName(lineOfCsv[i]);
            } else if(heading[i].equals("vacAuthorised")){
                this.vaccAuthorized = parseBoolean(lineOfCsv[i]);
            } else if(heading[i].equals("vaccTypeDesc")){
                this.vaccTypeDesc =lineOfCsv[i];
            } else if(heading[i].equals("reqDoses")){
                this.setReqDoses(lineOfCsv[i]);
            } else if(heading[i].equals("vaccineTemp")){
                this.setVaccineTemp(lineOfCsv[i]);
            } else if(heading[i].equalsIgnoreCase("efficacy")){
                this.setEfficacy(lineOfCsv[i]);
            }
        }
    }
     */

    public Vaccine() {

    }

    public Vaccine(VaccineBatch changedVacBatch) {
        this.vaccId = changedVacBatch.getVaccId();
        this.vaccName = changedVacBatch.getVaccName();
        this.vaccAuthorized = changedVacBatch.isVaccAuthorized();
        this.vaccTypeDesc = changedVacBatch.getVaccTypeDesc();
        this.reqDoses = changedVacBatch.getReqDoses();
        this.vaccineTemp = changedVacBatch.getVaccineTemp();
        this.efficacy = changedVacBatch.getEfficacy();
    }

    public String getVaccineTempAsString() {
         return valueOf(vaccineTemp);
    }
    
    public String getReqDosesAsString() {
         return String.valueOf(reqDoses);
    }
    
    public String getEfficacyAsString() {
         return valueOf(efficacy);
    }
    public int getVaccId() {
        return this.vaccId;
    }

    public String getVaccIdString() {
        return String.valueOf(this.vaccId);
}
    public void setVaccId(String vaccId) {
        this.vaccId = parseInt(vaccId);
    }

    public void setVaccId(int vaccId) {
        this.vaccId = vaccId;
    }

    public String getVaccName() {
        return vaccName;
    }

    public void setVaccName(String vaccName) {
        this.vaccName = vaccName;
    }

    public boolean isVaccAuthorized() {
        return vaccAuthorized;
    }

    public void setVaccAuthorized(boolean vaccAuthorized) {
        this.vaccAuthorized = vaccAuthorized;
    }

    public String getVaccTypeDesc() {
        return vaccTypeDesc;
    }

    public void setVaccTypeDesc(String vaccTypeDesc) {
        this.vaccTypeDesc = vaccTypeDesc;
    }

    public int getReqDoses() {
        return reqDoses;
    }

    public void setReqDoses(int reqDoses) {
        this.reqDoses = reqDoses;
    }

    public void setReqDoses(String reqDoses) {
        this.reqDoses = parseInt(reqDoses);
    }

    public double getVaccineTemp() {
        return vaccineTemp;
    }

    public void setVaccineTemp(double vaccineTemp) {
        this.vaccineTemp = vaccineTemp;
    }

    public void setVaccineTemp(String vaccineTemp) {
        this.vaccineTemp = parseDouble(vaccineTemp);
    }

    public double getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(double efficacy) {
        this.efficacy = efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = parseDouble(efficacy);
    }

    @Override
    public String toCsvString() {
        return getVaccIdString() +","+vaccName+","+vaccAuthorized+","+vaccTypeDesc+","+reqDoses+","+vaccineTemp+","+ efficacy;
    }

   @Override
    public String toString() {
        return vaccName;
    }


}
