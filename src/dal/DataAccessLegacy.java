/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import bll.*;

import static gui.AlertBox.displayAlert;
import gui.PCOVIDController;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 *
 * @author Henry Culllen
 */



public class DataAccessLegacy {

    //Instantiating all the properties of our data access object
    
    //connections to each entities' data files
    String connUser = "User";
    String connPatient = "Patient";
    String connClinician = "Clinician";
    String connPHA = "PHA";
    String connAppointment = "Appointment";
    String connVaccine = "Vaccine";
    String connVaccineBatch = "VaccineBatch";
    String connVaccinationCentre = "VaccinationCentre";
    
    //The headings of each column in the datafiles
    String[] headingOfPatient;
    String[] headingOfUser;
    String[] headingOfClinician;
    String[] headingOfPHA;
    String[] headingOfAppointment;
    String[] headingOfVaccine;
    String[] headingOfVaccineBatch;
    String[] headingOfVaccinationCentre;

    ArrayList<User> userArrayList;
    ArrayList<Clinician> clinicianArrayList;
    ArrayList<PHA> phaArrayList;
    ArrayList<Patient> patientArrayList;
    ArrayList<VaccinationCentre> vacCentreArrayList;
    ArrayList<Vaccine> vaccineArrayList;
    ArrayList<VaccineBatch> vacBatchArrayList;
    ArrayList<Appointment> appointmentArrayList;
    ArrayList<InfectiousDiseases> diseasesArrayList;
    ArrayList<COVIDInformation> COVIDInfoArrayList;
    
    //Constructor
    public DataAccessLegacy(){

        this.userArrayList = new ArrayList<User>();
        this.loadDataFromFile(connUser);
        
        this.patientArrayList = new ArrayList<Patient>();
        this.loadDataFromFile(connPatient);
        
        this.clinicianArrayList = new ArrayList<Clinician>();
        this.loadDataFromFile(connClinician);
        
        this.phaArrayList = new ArrayList<PHA>();
        this.loadDataFromFile(connPHA);
        
        this.vaccineArrayList = new ArrayList<Vaccine>();
        this.loadDataFromFile(connVaccine);
        
        this.vacBatchArrayList = new ArrayList<VaccineBatch>();
        this.loadDataFromFile(connVaccineBatch);
        
        this.vacCentreArrayList = new ArrayList<VaccinationCentre>();
        this.loadDataFromFile(connVaccinationCentre);

        this.appointmentArrayList = new ArrayList<Appointment>();
        this.loadDataFromFile(connAppointment);
        
        this.diseasesArrayList = new ArrayList<InfectiousDiseases>();
           
        saveDataToFile(connAppointment);
        
    }

    //READING
    private void loadDataFromFile(String connToPersistence) {
        
        //keeping tracker for entity type
        String conn = connToPersistence;
        
        //generate pathname for the file
        connToPersistence = getPathForName(connToPersistence);
        
        //String for holding the headings
        String[] headingOfCsv;
        
        //creating an abstract representation of the file at pathname
        File fileForScan = new File(connToPersistence);

        //try catch block for exception handling for file not found
        try {
            //create new scanner
            Scanner scannIn = new Scanner(fileForScan);
            
            if(scannIn.hasNext()) {
                
                //Scans headings
                headingOfCsv = scannIn.nextLine().split(",");
                
                //scans in lines of data
                while (scannIn.hasNext()) {
                    String[] lineOfCsv = scannIn.nextLine().split(",");
                    
                    //data sent to constrMaker
                    constrMaker(conn, headingOfCsv, lineOfCsv);
                }
            }
            
            scannIn.close();

        }catch (FileNotFoundException ex) {
            
            //Exception handling for user
            displayAlert("File Not Found", "Error reading from the file: "+ connToPersistence + " \nError details: "+ex.toString(),ERROR);

            // exception handling for programmer
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Logic for creating each class based on the file connection
    //Called in the loadDataFromFile
    //We pass in the headings also so that we can save them to our object for when we need to 
    //save back to the file
    //it also allows use to have constructors that will work even when we change 
    //the order of columns in our datafile
    private void constrMaker(String conn, String[] headingsOfCsv, String[] lineOfCsv) {
        
        //checks to see which file we connected to when we read the data
        
        if (conn.equals(connUser)) {

            this.headingOfUser = headingsOfCsv;
          //  User eachUser = new User(headingsOfCsv, lineOfCsv);
           // this.createUser(eachUser);

        } else if (conn.equals(connClinician)) {

            User newChad; 
            newChad= this.getUserById(headingsOfCsv, lineOfCsv);
            this.headingOfClinician = headingsOfCsv;
        //    Clinician eachClinician = new Clinician(newChad, headingsOfCsv, lineOfCsv);
          //  this.createClinician(eachClinician);
            
        } else if (conn.equals(connPatient)) {

            User newChad; 
            newChad= this.getUserById(headingsOfCsv, lineOfCsv);
            this.headingOfPatient = headingsOfCsv;
        //    Patient eachPatient = new Patient(newChad, headingsOfCsv, lineOfCsv);
        //    this.createPatient(eachPatient);
             
        } else if (conn.equals(connPHA)) {

            User newChad; 
            newChad= this.getUserById(lineOfCsv[0]);

            this.headingOfPHA = headingsOfCsv;
            PHA eachPHA = new PHA(newChad);
            this.createPHA(eachPHA);

        } else if (conn.equals(connVaccinationCentre)) {
            
            this.headingOfVaccinationCentre = headingsOfCsv;
           // VaccinationCentre eachVacCentre = new VaccinationCentre(headingsOfCsv, lineOfCsv);
            //this.createVacCentre(eachVacCentre);
          
        }   else if (conn.equals(connVaccine)) {

            this.headingOfVaccine = headingsOfCsv;
           // Vaccine eachVaccine= new Vaccine(headingsOfCsv, lineOfCsv);
            //this.createVaccine(eachVaccine);

        } else if (conn.equals(connVaccineBatch)) {

            Vaccine theVaccine = this.getVaccineById(headingsOfCsv, lineOfCsv);

            this.headingOfVaccineBatch = headingsOfCsv;
       //     VaccineBatch eachVacBatch = new VaccineBatch( theVaccine, headingsOfCsv, lineOfCsv);
       //     this.createVacBatch(eachVacBatch);

        } else if (conn.equals(connAppointment)) {


            Patient theUser = this.getPatientById(headingsOfCsv, lineOfCsv);
            Clinician theClinician = this.getClinicianByClinicianId(headingsOfCsv, lineOfCsv);
            VaccineBatch theBatch = this.getVaccineBatchById(headingsOfCsv, lineOfCsv);
            VaccinationCentre theCentre = this.getCentreByEircode(headingsOfCsv, lineOfCsv);

            this.headingOfAppointment = headingsOfCsv;
        //    Appointment eachAppointment = new Appointment(headingsOfCsv, lineOfCsv, theUser, theClinician, theCentre, theBatch);
        //    this.createAppointment(eachAppointment);
        }
    }
 
    //WRITING
    public void saveDataToFile(String connToPersistence){
        
        //keeps record of entity type
        String conn = connToPersistence;
        
        //gets the filepath
        connToPersistence = getPathForName(connToPersistence);
        
        //create an abstract representation of a file to output text to using PrintWriter
        File fileForWrite = new File(connToPersistence);

        try{
            PrintWriter writeOut = new PrintWriter(fileForWrite);
           if (conn.equals(connAppointment)) {
               
               //adds the column headings
                writeOut.println(readArrayToString(headingOfAppointment));
               for (Appointment appointment : this.appointmentArrayList) {
                   // toCsvString is a method on each object
                   writeOut.println(appointment.toCsvString());
               }
                
            } else if (conn.equals(connClinician)) {
                
                writeOut.println(readArrayToString(headingOfClinician));

               for (Clinician clinician : this.clinicianArrayList) {
                   writeOut.println(clinician.toCsvString());
               }
                
            } else if (conn.equals(connPatient)) {
                
                writeOut.println(readArrayToString(headingOfPatient));

               for (Patient patient : this.patientArrayList) {
                   writeOut.println(patient.toCsvString());
               }
                
            } else if (conn.equals(connPHA)) {
                
                writeOut.println(readArrayToString(headingOfPHA)+",");

               for (PHA pha : this.phaArrayList) {
                   writeOut.println(pha.toCsvString());
               }
               
            } else if (conn.equals(connUser)) {
                
                writeOut.println(readArrayToString(headingOfUser));

               for (User user : this.userArrayList) {
                   writeOut.println(user.toCsvString());
               }
                
            } else if (conn.equals(connVaccinationCentre)) {
                
                writeOut.println(readArrayToString(headingOfVaccinationCentre));

               for (VaccinationCentre vaccinationCentre : this.vacCentreArrayList) {
                   writeOut.println(vaccinationCentre.toCsvString());
               }
               
            } else if (conn.equals(connVaccineBatch)) {
                
                writeOut.println(readArrayToString(headingOfVaccineBatch));

               for (VaccineBatch vaccineBatch : this.vacBatchArrayList) {
                   writeOut.println(vaccineBatch.toCsvString());
               }
               
            } else if (conn.equals(connVaccine)) {
                
                writeOut.println(readArrayToString(this.headingOfVaccine));

               for (Vaccine vaccine : this.vaccineArrayList) {
                   writeOut.println(vaccine.toCsvString());
               }
                
            } else {
                throw new IllegalStateException("Unexpected value: " + connToPersistence);
            }
           
            writeOut.close();

        }catch (FileNotFoundException ex) {

            //Exception handling for user
            displayAlert("File Not Found", "Error writing to the file: "+ connToPersistence + " \nError details: "+ex.toString(),ERROR);

            // exception handling for programmer
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //WRITING TO ALL
    public void saveAll(){

        this.saveDataToFile(connUser);
        this.saveDataToFile(connPatient);
        this.saveDataToFile(connClinician);
        this.saveDataToFile(connPHA);
        this.saveDataToFile(connVaccine);
        this.saveDataToFile(connVaccineBatch);
        this.loadDataFromFile(connVaccinationCentre);
        this.saveDataToFile(connAppointment);

    }
    
    
    //CREATE methods

    public void createClinician(Clinician newClinician){
      
      //creates a new User to pass the details to the user file
      User newUser = new User(newClinician);
      this.createUser(newUser);
      
      //adds to the arraylist
      this.clinicianArrayList.add(newClinician);
      this.saveDataToFile(connClinician);
    }

    public void createPatient(Patient newPatient){
        
      //creates a new User to pass the details to the user file
      User newUser = new User(newPatient);
      this.createUser(newUser);
      
      //adds to the arraylist
      this.patientArrayList.add(newPatient);
      this.saveDataToFile(connPatient);
    }

    public void createPHA(PHA newPHA){

        //creates a new User to pass the details to the user file
        User newUser = new User(newPHA);
        this.createUser(newUser);

        //adds to the arraylist
        this.phaArrayList.add(newPHA);
        this.saveDataToFile(connPatient);
    }
    
    public void createUser(User newUser){
      
      //checks to see if the user is already in the array..
      //We don't want duplication
      if(!isUserAlreadyHere(newUser)){
          this.userArrayList.add(newUser);
      }
          this.saveDataToFile(connUser);
    }

    public void createVacCentre(VaccinationCentre newVacCentre){
        
        if(getCentreByEircode(newVacCentre.getCentreEircode())==null){
            this.vacCentreArrayList.add(newVacCentre);
            this.saveDataToFile(connVaccinationCentre);
        }
        
    }

    public void createVacBatch(VaccineBatch newVacBatch){
        this.vacBatchArrayList.add(newVacBatch);
        this.saveDataToFile(connVaccineBatch);
    }

    public void createVaccine(Vaccine newVaccine){
        this.vaccineArrayList.add(newVaccine);
        this.saveDataToFile(connVaccine);
    }

    public void createAppointment(Appointment newAppointment){
        this.appointmentArrayList.add(newAppointment);
        //this.saveDataToFile(connAppointment);
    }
    
    public void createDisease(InfectiousDiseases ill){
        this.diseasesArrayList.add(ill);
    }

    //RETRIEVE
    public ArrayList<Appointment> getAppointmentArrayList() {
        return appointmentArrayList;
    }
    public ArrayList<Clinician> getClinicianArrayList() {
        return clinicianArrayList;
    }
    public ArrayList<Patient> getPatientArrayList() {
        return patientArrayList;
    }
    public ArrayList<PHA> getPhaArrayList() {
        return phaArrayList;
   }
    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }
    public ArrayList<VaccinationCentre> getVacCentreArrayList() {
        return vacCentreArrayList;
    }
    public ArrayList<Vaccine> getVaccineArrayList() {
        return vaccineArrayList;
    }
    public ArrayList<VaccineBatch> getVacBatchArrayList() {
        return vacBatchArrayList;
    }

    public ArrayList<InfectiousDiseases> getDiseasesArrayList() {
        return diseasesArrayList;
    }
    public ArrayList<COVIDInformation> getCOVIDInfoArrayList() {
        return COVIDInfoArrayList;
    }
    
    //UPDATE
    public void updateAppointment(Appointment changedAppointment){
        for (int i=0; i < this.appointmentArrayList.size(); i++){
            if (this.appointmentArrayList.get(i).getAppointmentId() == changedAppointment.getAppointmentId()){
                this.appointmentArrayList.set(i, changedAppointment);
                break;
            }
        }
       // this.saveDataToFile(this.connAppointment);
    }

    public void updateClinician(Clinician changedClinician){
        for (int i=0; i < this.clinicianArrayList.size(); i++){
            if (this.clinicianArrayList.get(i).getUserId().equals(changedClinician.getUserId())){
                this.clinicianArrayList.set(i, changedClinician);
                break;
            }
        }
        //this will update their entry in the user arraylist as well
        User changedUser = new User(changedClinician);
        updateUser(changedUser);

        this.saveDataToFile(connClinician);
    }

    public void updatePatient(Patient changedPatient){
        for (int i=0; i < this.patientArrayList.size(); i++){
            if (this.patientArrayList.get(i).getUserId().equals(changedPatient.getUserId())){
                this.patientArrayList.set(i, changedPatient);
                break;
            }
        }
        User changedUser = new User(changedPatient);
        updateUser(changedUser);
        this.saveDataToFile(connPatient);
    }

    public void updatePHA(PHA changedPHA){
        for (int i=0; i < this.phaArrayList.size(); i++){
            if (this.phaArrayList.get(i).getUserId().equals(changedPHA.getUserId())){
                this.phaArrayList.set(i, changedPHA);
                break;
            }
        }
        User changedUser = new User(changedPHA);
        updateUser(changedUser);

        this.saveDataToFile(this.connPHA);
    }

    public void updateUser(User changedUser){
        for (int i=0; i < this.userArrayList.size(); i++){
            if (this.userArrayList.get(i).getUserId().equals(changedUser.getUserId())){
                this.userArrayList.set(i, changedUser);
                break;
            }
        }
        this.saveDataToFile(connUser);
    }
    public void updateCentre(VaccinationCentre changedVacCentre){
        for (int i=0; i < this.vacCentreArrayList.size(); i++){
            if (this.vacCentreArrayList.get(i).getCentreEircode().equals(changedVacCentre.getCentreEircode())){
                this.vacCentreArrayList.set(i, changedVacCentre);
                break;
            }
        }
        this.saveDataToFile(this.connVaccinationCentre);
    }
    public void updateBatch(VaccineBatch changedVacBatch){
        for (int i=0; i < this.vacBatchArrayList.size(); i++){
            if (this.vacBatchArrayList.get(i).getBatchNo() == changedVacBatch.getBatchNo()){
                this.vacBatchArrayList.set(i, changedVacBatch);
                break;
            }
           // Vaccine vac = new Vaccine(changedVacBatch);
            //updateVaccine(vac);
        }
        this.saveDataToFile(this.connVaccineBatch);
    }
    public void updateVaccine(Vaccine changedVaccine){
        for (int i=0; i < this.vaccineArrayList.size(); i++){
            if (this.vaccineArrayList.get(i).getVaccId() == changedVaccine.getVaccId()){
                this.vaccineArrayList.set(i, changedVaccine);
                break;
            }
        }
        this.saveDataToFile(this.connVaccine);
    }

    //DELETE
    public void delAppointment(int appointmentId){
        for (int i=0; i < this.appointmentArrayList.size(); i++){
            if (this.appointmentArrayList.get(i).getAppointmentId()== appointmentId){
                this.appointmentArrayList.remove(i);
            }
        }
        this.saveDataToFile(this.connAppointment);
    }
    public void delClinician(String userId){
        for (int i=0; i < this.clinicianArrayList.size(); i++){
            if (this.clinicianArrayList.get(i).getUserId().equals(userId)){
                this.clinicianArrayList.remove(i);
            }
        }
        this.saveDataToFile(connClinician);
    }
    public void delPatient(String userId){
        for (int i=0; i < this.patientArrayList.size(); i++){
            if (this.patientArrayList.get(i).getUserId().equals(userId)){
                this.patientArrayList.remove(i);
            }
        }
        this.saveDataToFile(connPatient);
    }
    /*public void delPha(int phaID){
        for (int i=0; i < this.phaArrayList.size(); i++){
            if (this.phaArrayList.get(i).getPhaID() == phaID){
                this.phaArrayList.remove(i);
            }
        }
        this.saveDataToFile(this.connToPersistence);
    }*/
    public void delUser(String userId){
        for (int i=0; i < this.userArrayList.size(); i++){
            if (this.userArrayList.get(i).getUserId().equals(userId)){
                this.userArrayList.remove(i);
            }
        }
        this.saveDataToFile(connUser);
    }
    public void delVaccinationCentre(String centreId){
        for (int i=0; i < this.vacCentreArrayList.size(); i++){
            if (this.vacCentreArrayList.get(i).getCentreEircode().equals(centreId)){
                this.vacCentreArrayList.remove(i);
            }
        }
        this.saveDataToFile(this.connVaccinationCentre);
    }
//    public void delVaccineBatch(int batchNo){
//        for (int i=0; i < this.vacBatchArrayList.size(); i++){
//            if (this.vacBatchArrayList.get(i).getBatchNo() == batchNo){
//                this.vacBatchArrayList.remove(i);
//            }
//        }
//        this.saveDataToFile(this.connToPersistence);
////    }
//    public void delVaccine(int vaccineID){
//        for (int i=0; i < this.vaccineArrayList.size(); i++){
//            if (this.vaccineArrayList.get(i).getVaccineID() == vaccineID){
//                this.vaccineArrayList.remove(i);
//            }
//        }
//        this.saveDataToFile(this.connToPersistence);
//    }

    //Searches the user array list for a user by their id
    //in  the arrays input form
    public User getUserById(String[] headingsOfCsv, String[] lineOfCsv){
         String chadId = new String();
            for(int i = 0; i < headingsOfCsv.length; i++){
                if(headingsOfCsv[i].equalsIgnoreCase("userId")){
                     chadId = lineOfCsv[i];
                     break;
                }
            }
            for(User chad : this.getUserArrayList()){
                if(chadId.equals(chad.getUserId())){
                    return chad;
                }
            }
         return null;
    }

    //finds a user in the array list so we can return them
    //given their id value entered as an arg
    public User getUserById(String id){

        for(User chad : this.getUserArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    public PHA getPhaById(String id){

        for(PHA chad : this.getPhaArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }
    //Searches the patient array list for a user by their id
    //in  the arrays input form
    public Patient getPatientById(String[] headingsOfCsv, String[] lineOfCsv){
        String chadId = new String();
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equalsIgnoreCase("userId")){
                chadId = lineOfCsv[i];
                break;
            }
        }
        for(Patient chad : this.getPatientArrayList()){
            if(chadId.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    //finds a patient in the array list so we can return them
    //given their id value entered as an arg
    public Patient getPatientById(String id){

        for(Patient chad : this.getPatientArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }
        
    //Returns patients with maySchedule status
    public ArrayList<Patient> getPatientsMaySchedule() {
        ArrayList<Patient> pat = new ArrayList<Patient>();
        for (Patient juan : this.getPatientArrayList() ) {
            if(juan.isEligibility() && this.getAppsByUserIdActive(juan.getUserId()).size() < 2) {
               pat.add(juan);
            }
            
        }
        return pat;
    }   
    
    
    //Searches the clinician array list for a clinician by their id
    //in  the arrays input form
    public Clinician getClinicianById(String[] headingsOfCsv, String[] lineOfCsv){
        String chadId = new String();
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equals("userId")){
                chadId = lineOfCsv[i];
                break;
            }
        }
        for(Clinician chad : this.getClinicianArrayList()){
            if(chadId.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    //Searches the clinician array list for a clinician by their id
    //in  the arrays input form
    public Clinician getClinicianByClinicianId(String[] headingsOfCsv, String[] lineOfCsv){
        String chadId = new String();
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equals("clinicianId")){
                chadId = lineOfCsv[i];
                break;
            }
        }
        for(Clinician chad : this.getClinicianArrayList()){
            if(chadId.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    //finds a user in the array list so we can return them
    //given their id value entered as an arg
    public Clinician getClinicianById(String id){

        for(Clinician chad : this.getClinicianArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    //finds a user in the array list so we can return them
    //given their id value entered as an arg
    public PHA getPHAById(String id){

        for(PHA chad : this.getPhaArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }
    
        public VaccineBatch getVaccBatchById(int id){

        for(VaccineBatch chad : this.getVacBatchArrayList()){
            if(id == chad.getBatchNo()){
                return chad;
            }
        }
        return null;
    }

    //Searches the Batch array list for a Batch with their id
    //in  the 2 array input form
    public VaccineBatch getVaccineBatchById(String[] headingsOfCsv, String[] lineOfCsv){
        int chadId = 0;
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equals("vaccineBatch")){
                chadId = parseInt(lineOfCsv[i]);
                break;
            }
        }
        for(VaccineBatch chad : this.getVacBatchArrayList()){
            if(chadId == chad.getBatchNo()){
                return chad;
            }
        }
        return null;
    }

    public ArrayList<VaccineBatch> getBatchAvailable(int minAmt){
        ArrayList<VaccineBatch> batches = new  ArrayList<VaccineBatch>();
        for(VaccineBatch vim : this.getVacBatchArrayList()){
            if(vim.getQtyRemaining()>=minAmt)
                batches.add(vim);
        }
        return batches;
    }

    //Searches the Vaccine array list for a Vaccine with their id
    //in  the 2 array input form
    public Vaccine getVaccineById(String[] headingsOfCsv, String[] lineOfCsv){
        int chadId = 0;
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equalsIgnoreCase("vaccineId")){
                chadId = parseInt(lineOfCsv[i]);
                break;
            }
        }
        for(Vaccine chad : this.getVaccineArrayList()){
            if(chadId == chad.getVaccId()){
                return chad;
            }
        }
        return null;
    }

    public Vaccine getVaccineByName (String name){
        
        for(Vaccine chad : this.getVaccineArrayList()){
            if(name.equals(chad.getVaccName())){
                return chad;
            }
        }
        return null;
    }

    //Searches the PHA array list for a PHA with their id
    public VaccinationCentre getCentreByEircode(String[] headingsOfCsv, String[] lineOfCsv){
        String chadId = new String();
        for(int i = 0; i < headingsOfCsv.length; i++){
            if(headingsOfCsv[i].equals("centre")){
                chadId = lineOfCsv[i];
                break;
            }
        }
        for(VaccinationCentre chad : this.getVacCentreArrayList()){
            if(chadId.equals(chad.getCentreEircode())){
                return chad;
            }
        }
        return null;
    }

    //finds a patient in the array list so we can return them
    //given their id value entered as an arg
    public VaccinationCentre getCentreByEircode(String id){

        for(VaccinationCentre chad : this.getVacCentreArrayList()){
            if(id.equals(chad.getCentreEircode())){
                return chad;
            }
        }
        return null;
    }
    
    
    //finds all appointments in the array list with a users id
    //given their user id value entered as an arg
    //the list is filtered only to include appointments that have not been cancelled
    public ArrayList<Appointment> getAppsByUserIdActive(String id){
        ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();

        for(Appointment app : this.getAppointmentArrayList()){
            if(id.equals(app.getUser().getUserId()) && !(app.getAppointmentStatus().equals("Cancelled")) && !(app.getAppointmentStatus().equals("Absent"))){
                myAppointments.add(app);
            }
        }
        return myAppointments;
    }
    
    public ArrayList<Appointment> getAppsByUserIdAll(String id){
            ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();

            for(Appointment app : this.getAppointmentArrayList()){
                if(id.equals(app.getUser().getUserId())){
                    myAppointments.add(app);
                }
            }
            return myAppointments;
        }
    //returns all a clinicians appointments
    public ArrayList<Appointment> getAppsByClinicianIdAll(String id){
        ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();

        for(Appointment app : this.getAppointmentArrayList()){
           if(app.getClinician() != null ){     
           
           
                if(id.equals(app.getClinician().getUserId())){
                myAppointments.add(app);
                
                
                    
                }
           } 
            
        }
        return myAppointments;
    }
    public Appointment getAppByAppId(int id){

        for(Appointment app : this.getAppointmentArrayList()){
            if(id == app.getAppointmentId()){
                return app;
            }
        }
        return null;
    }

    public int nextAppId(){
        int lem = 0;
        for (Appointment appo: this.getAppointmentArrayList()) {
            if(appo.getAppointmentId()>lem){
                lem = appo.getAppointmentId();
            }
        }
        return lem+1;
    }
    
    public int nextBatchId(){
        int lem = 0;
        for (VaccineBatch btch: this.getVacBatchArrayList()) {
            if(btch.getBatchNo()>lem){
                lem = btch.getBatchNo();
            }
        }
        return lem+1;
    }

    //Returns list of appointments ongoing at vacc centre
    public ArrayList<Appointment> getAppsByCentreEircodeActiveOngoing(String id){
        ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();

        for(Appointment app : this.getAppointmentArrayList()){
            if(id.equals(app.getCentre().getCentreEircode()) && !(app.getAppointmentStatus().equals("Cancelled")) && !(app.getAppointmentStatus().equals("Successful")) && !(app.getAppointmentStatus().equals("Absent")) ){
                myAppointments.add(app);
            }
        }
        return myAppointments;
    }

    public boolean isUserAlreadyHere(User juan){
           for(User ben: this.getUserArrayList()) {
               if(ben.getUserId().equals(juan.getUserId())){
                   return true;
               }

           }
           return false;
    }
    
   
    //Convert to filename
    public static String getPathForName(String filename){
        filename+=".csv";
        filename = "src/db/"+filename;
        return filename;
    }

    //similar to the .toCsvString() method used on the different objects but this is instead a static version
    // that is called on normal string arrays rather than our objects
    public static String readArrayToString(String[] line){
        String delimitted = "";
        for (int i=0;i<line.length-1;i++){
            delimitted+=line[i]+",";
        }
        //separate from for loop as we don't want a comma at the end
        delimitted+= line[line.length-1];
        return delimitted;
    }


}
