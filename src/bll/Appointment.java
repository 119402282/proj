/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import java.util.Date;

import static bll.CsvInterface.*;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 *
 * @author Henry Cullen
 */
public class Appointment implements CsvInterface {

    private int appointmentId;
    private Date appointmentDate;
    private String appointmentStatus;
    private Patient user;
    private Clinician clinician;
    private VaccineBatch vaccineBatch;
    private VaccinationCentre centre;


    public Appointment(int appointmentId, Date appointmentDate, String appointmentStatus, Patient user, Clinician clinician, VaccineBatch vaccineBatch, VaccinationCentre centre) {
        this.setAppointmentId(appointmentId);
        this.setAppointmentDate(appointmentDate);
        this.setAppointmentStatus(appointmentStatus);
        this.setUser(user);
        this.setClinician(clinician);
        this.setVaccineBatch(vaccineBatch);
        this.setCentre(centre);
    }

    /* Legacy
    public Appointment(String[] headingsOfCsv, String[] lineOfCsv, Patient theUser, Clinician theClinician, VaccinationCentre theCentre, VaccineBatch theBatch) {

        for(int i = 0; i < headingsOfCsv.length; i++){
            
            if(headingsOfCsv[i].equalsIgnoreCase("appointmentId")){
                this.appointmentId = parseInt(lineOfCsv[i]);
            } else if(headingsOfCsv[i].equalsIgnoreCase("appointmentDateAndTime")){
                this.setAppointmentDate(lineOfCsv[i]);
            } else if (headingsOfCsv[i].equalsIgnoreCase("appointmentStatus")){
                this.appointmentStatus = lineOfCsv[i];
            }
        }

        this.user = theUser;
        this.clinician = theClinician;
        this.vaccineBatch = theBatch;
        this.centre = theCentre;
    }
    */
    public Appointment() {}

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                ", userId=" + user +
                ", clinicianId=" + clinician +
                ", vaccineBatch=" + vaccineBatch +
                ", centre=" + centre +
                '}';
    }

    @Override
    public String toCsvString() {
        
        String content = String.valueOf(appointmentId);
        content+=","+getAppointmentDateAsString();
        content+=","+ getAppointmentStatus(); 
        content+=","+this.getUser().getUserId();
        content+=","+this.getClinician().getUserId();
        content+=","+this.getVaccineBatch().getBatchNo();
        content+=","+this.getCentre().getCentreEircode();
        return content;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }
    public String getAppointmentDateAsString() {

        return dateToString(this.getAppointmentDate());
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = toDate(appointmentDate);
    }
    public String getAppointmentDateShort(){
        return dateToStringShort(appointmentDate);
    }

    public String getAppointmentDateTime(){
        if(dateToStringTime(appointmentDate).equals("00:00")){
            return "Unallocated";
        } else {
            return dateToStringTime(appointmentDate);
        }
    }

    public String getBatchToTable(){
        if(getVaccineBatch()==null){
            return "Unassigned";
        }else{
            return getVaccineBatch().getBatchNoAsString();
        }
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Patient getUser() {
        return user;
    }

    public void setUser(Patient user) {
        this.user = user;
    }

    public Clinician getClinician() {
        return clinician;
    }

    public String getClinicianToTable(){
        if(clinician == null){
            return "Unassigned";
        }else{
         return clinician.getUserId();
        }
    }

    public void setClinician(Clinician clinician) {
        this.clinician = clinician;
    }

    public VaccineBatch getVaccineBatch() {
        return vaccineBatch;
    }

    public void setVaccineBatch(VaccineBatch vaccBatch) {
        this.vaccineBatch = vaccBatch;
    }

    
    public VaccinationCentre getCentre() {
        return centre;
    }

    public void setCentre(VaccinationCentre centre) {
        this.centre = centre;
    }

}
