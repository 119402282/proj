/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dal;

import bll.*;
import static gui.AlertBox.displayAlert;
import static java.lang.Integer.parseInt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 *
 * @author Henry Cullen <119402282%40umail.ucc.ie>
 */
public class DataAccess {

    //creating static instance so that we will only ever have one
    private static DataAccess DataAccessInstance;

    //all database properties
    private Connection conn;
    private static final String DB_URL = "jdbc:derby:IS2209CA2DB";
    //use the following if you created the Database yourself
    //private static final String DB_URL = "jdbc:derby://localhost:1527/IS2209CA2DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pass";
    //the statement for the sql
    private Statement stmt;

    //All arraylist for objects
    ArrayList<VaccinationCentre> vacCentreArrayList;
    ArrayList<User> userArrayList;
    ArrayList<Clinician> clinicianArrayList;
    ArrayList<Patient> patientArrayList;
    ArrayList<PHA> phaArrayList;
    ArrayList<InfectiousDiseases> diseasesArrayList;
    ArrayList<Vaccine> vaccineArrayList;
    ArrayList<VaccineBatch> vacBatchArrayList;
    ArrayList<Appointment> appointmentArrayList;
    COVIDInformation COVIDInfo;

    //makes initialization only possible within class
    private DataAccess() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            conn.setAutoCommit(true);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            loadAll();
        } catch(SQLException ex){
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Read Error");

        }
    }

    //Singleton
    public static DataAccess getInstance() {
        if (DataAccessInstance == null) {
            DataAccessInstance = new DataAccess();
        }
        return DataAccessInstance;
    }

    /*
         LOAD
         --------------------------------------------------

         The following section is for loading from the database

         --------------------------------------------------
    */

    //Loads all content from database to lists
    public void loadAll(){
        this.vacCentreArrayList = loadCentres();
        this.COVIDInfo = loadCovid();
        this.userArrayList = loadUsers();
        this.clinicianArrayList = loadClinician();
        this.patientArrayList = loadPatient();
        this.phaArrayList = loadPha();
        this.diseasesArrayList = loadDiseases();
        this.vaccineArrayList = loadVaccines();
        this.vacBatchArrayList = loadBatches();
        this.appointmentArrayList = loadAppointments();
    }

    //loads Users
    public ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();
        User temporaryUser = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBUSER");
            while(records.next()){
                temporaryUser = new User(records.getString("userid"), records.getString("fname"), records.getString("lname"), records.getString("pword"), records.getString("acctype"));
                users.add(temporaryUser);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return users;
    }
    //load patients
    public ArrayList<Patient> loadPatient(){
        ArrayList<Patient> patients = new ArrayList<>();
        Patient temporaryPatient = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT p.userid, u.pword, u.fname, u.lname, u.acctype, p.email, p.eircode, p.mobile, p.dob, p.elig, p.sched FROM ROOT.DBPATIENT p INNER JOIN ROOT.DBUSER u ON u.userid = p.userid");
            while(records.next()){
                temporaryPatient = new Patient(records.getString("userid"), records.getString("fname"), records.getString("lname"), records.getString("pword"), records.getString("email"), records.getString("eircode"), records.getString("mobile"), records.getTimestamp("dob"), records.getBoolean("elig"), records.getBoolean("sched"));
                patients.add(temporaryPatient);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return patients;
    }
    //load clinicians
    public ArrayList<Clinician> loadClinician(){
        ArrayList<Clinician> clinicians = new ArrayList<>();
        Clinician temporaryClinician = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT c.userid, u.pword, u.fname, u.lname, u.acctype, c.position FROM ROOT.DBCLINICIAN c INNER JOIN ROOT.DBUSER u ON u.userid = c.userid");
            while(records.next()){
                temporaryClinician = new Clinician(records.getString("userid"), records.getString("fname"), records.getString("lname"), records.getString("pword"), records.getString("position"));
                clinicians.add(temporaryClinician);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return clinicians;
    }
    //load phas
    public ArrayList<PHA> loadPha(){
        ArrayList<PHA> phas = new ArrayList<>();
        PHA tempPha = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT p.userid, u.pword, u.fname, u.lname, u.acctype FROM ROOT.DBPHA p INNER JOIN ROOT.DBUSER u ON u.userid = p.userid");
            while(records.next()){
                tempPha = new PHA(records.getString("userid"), records.getString("fname"), records.getString("lname"), records.getString("pword"));
                phas.add(tempPha);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return phas;
    }
    //load centres
    public ArrayList<VaccinationCentre> loadCentres(){
        ArrayList<VaccinationCentre> centres = new ArrayList<>();
        VaccinationCentre tempCentre = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBCENTRE");
            while(records.next()){
                tempCentre = new VaccinationCentre(records.getString("eircode"), records.getString("name"));
                centres.add(tempCentre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return centres;
    }
    //load diseases
    public ArrayList<InfectiousDiseases> loadDiseases(){
        ArrayList<InfectiousDiseases> diseases = new ArrayList<>();
        InfectiousDiseases tempDisease = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBDISEASE");
            while(records.next()){
                tempDisease = new InfectiousDiseases(records.getString("name"), records.getString("info"), records.getString("symptoms"), records.getString("remedies"), this.getPhaById(records.getString("phaauthor")), records.getTimestamp("lastedited"));
                diseases.add(tempDisease);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return diseases;
    }
    //load the covid info
    public COVIDInformation loadCovid(){
        COVIDInformation covid = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBCOVID");
            records.next();
            covid = new COVIDInformation(records.getString("info"), records.getString("symptoms"), records.getString("rollout"), records.getString("timeline"), records.getString("fig"));
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return covid;
    }
    //load Vaccines
    public ArrayList<Vaccine> loadVaccines(){
        ArrayList<Vaccine> vaccines = new ArrayList<>();
        Vaccine tempVacc = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBVACCINE");
            while(records.next()){
                tempVacc = new Vaccine(records.getInt("vaccid"), records.getString("name"), records.getBoolean("authorised"), records.getString("description"), records.getInt("doses"), records.getDouble("temp"), records.getDouble("efficacy"));
                vaccines.add(tempVacc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return vaccines;
    }
    //load batches
    public ArrayList<VaccineBatch> loadBatches(){
        ArrayList<VaccineBatch> batches = new ArrayList<>();
        VaccineBatch tempBatch = null;
        try {
            ResultSet records = stmt.executeQuery("SELECT b.vaccid, v.name, v.authorised, v.description, v.doses, v.temp, v.efficacy, b.batchno, b.orderedqty, b.qtyused, b.expiry FROM ROOT.DBBATCH b JOIN ROOT.DBVACCINE v ON v.vaccid = b.vaccid");
            while(records.next()){
                tempBatch = new VaccineBatch(records.getInt("vaccid"), records.getString("name"), records.getBoolean("authorised"), records.getString("description"), records.getInt("doses"), records.getDouble("temp"), records.getDouble("efficacy"), records.getInt("batchno"), records.getInt("orderedqty"), records.getInt("qtyUsed"), records.getTimestamp("expiry"));
                batches.add(tempBatch);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return batches;
    }
    //load appointments
    public ArrayList<Appointment> loadAppointments(){
        ArrayList<Appointment> apps = new ArrayList<>();
        Appointment tempApp = null;

        try {
            ResultSet records = stmt.executeQuery("SELECT * FROM ROOT.DBAPPOINTMENT");
            while(records.next()){
                //Solution to any null fields
                Patient pat;
                Clinician cat;
                VaccinationCentre centra;
                VaccineBatch anthony;

                records.getString("patient");
                if (records.wasNull()) {
                    pat = null;
                } else {
                    pat = this.getPatientById(records.getString("patient"));
                }

                records.getString("clinician");
                if (records.wasNull()) {
                    cat = null;
                } else {
                    cat = this.getClinicianById(records.getString("clinician"));
                }

                records.getString("centre");
                if (records.wasNull()) {
                    centra = null;
                } else {
                    centra = this.getCentreByEircode(records.getString("centre"));
                }

                records.getInt("batch");
                if (records.wasNull()) {
                    anthony = null;
                } else {
                    anthony = this.getVaccBatchById(records.getInt("batch"));
                }


                tempApp = new Appointment(records.getInt("appointmentid"), records.getTimestamp("appdate"), records.getString("appstatus"), pat, cat, anthony, centra);
                apps.add(tempApp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error executing query");
            displayAlert("An Error Has Occured","Failed to execute the query",ERROR);
        }
        return apps;
    }

    /*
         STORE
         --------------------------------------------------

         The following section is for storing to the database

         --------------------------------------------------
    */

    //stores all lists to the database
    public void storeAll(){
        storeUsers();
        storeCentres();
        storeCovid();
        storeVaccines();
        storeBatches();
        storeClinicians();
        storePatients();
        storePHAs();
        storeDiseases();
        storeAppointments();
    }

    //stores users
    public void storeUsers(){
        try {
            stmt.execute("DELETE FROM ROOT.DBUSER");
            for(User item : getUserArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBUSER (USERID, PWORD, FNAME, LNAME, ACCTYPE) VALUES ('"
                        + item.getUserId().replace("'","''")+"', '"
                        + item.getPassword().replace("'","''")+"', '"
                        + item.getFName().replace("'","''")+"', '"
                        + item.getLName().replace("'","''")+"', '"
                        + item.getAccountType().replace("'","''")+"')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            //attempt to load and then recover situation
            loadAll();
            storeAll();
        }
    }
    //stores patients
    public void storePatients(){
        try {
            stmt.execute("DELETE FROM ROOT.DBPATIENT");
            for(Patient item : getPatientArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBPATIENT (USERID, EMAIL, EIRCODE, MOBILE, DOB, ELIG, SCHED) VALUES ('"
                        + item.getUserId().replace("'","''")+"', '"
                        + item.getEmail().replace("'","''")+"', '"
                        + item.getEircode().replace("'","''")+"', '"
                        + item.getMobile().replace("'","''")+"', TIMESTAMP('"
                        + new Timestamp(item.getDob().getTime())+"'), "
                        + item.isEligibility()+", "
                        + item.isMaySchedule()+")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores clinicians
    public void storeClinicians() {
        try {
            stmt.execute("DELETE FROM ROOT.DBCLINICIAN");
            for (Clinician item : getClinicianArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBCLINICIAN (USERID, POSITION) VALUES ('"
                        + item.getUserId().replace("'", "''")+ "', '"
                        + item.getPosition().replace("'", "''")+ "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores phas
    public void storePHAs() {
        try {
            stmt.execute("DELETE FROM ROOT.DBPHA");
            for (PHA item : getPhaArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBPHA (USERID) VALUES ('"
                        + item.getUserId().replace("'", "''")+ "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores centres
    public void storeCentres() {
        try {
            stmt.execute("DELETE FROM ROOT.DBCENTRE");
            for (VaccinationCentre item : getVacCentreArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBCENTRE (EIRCODE, NAME) VALUES ('"
                        + item.getCentreEircode().replace("'", "''")+ "', '"
                        + item.getCentreName().replace("'", "''")+ "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores diseases
    public void storeDiseases() {
        try {
            stmt.execute("DELETE FROM ROOT.DBDISEASE");
            for (InfectiousDiseases item : getDiseasesArrayList()){

                stmt.executeUpdate("INSERT INTO ROOT.DBDISEASE (NAME, PHAAUTHOR, LASTEDITED, INFO, SYMPTOMS, REMEDIES) VALUES ('"
                        + item.getName().replace("'", "''")+ "', '"
                        + item.getAuthor().getUserId().replace("'", "''")+ "', TIMESTAMP('"
                        + new Timestamp(item.getEdited().getTime())+"'), '"
                        + item.getInformation().replace("'", "''")+ "', '"
                        + item.getSymptoms().replace("'", "''")+ "', '"
                        + item.getRemedies().replace("'", "''")+ "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores covid info
    public void storeCovid() {
        try {
            stmt.execute("DELETE FROM ROOT.DBCOVID");
            stmt.executeUpdate("INSERT INTO ROOT.DBCOVID(INFO, SYMPTOMS, ROLLOUT, TIMELINE, FIG) VALUES ('"
                    + getCOVIDInfo().getGeneralInfo().replace("'", "''")+ "', '"
                    + getCOVIDInfo().getSymptoms().replace("'", "''") + "', '"
                    + getCOVIDInfo().getRollout().replace("'", "''")+ "', '"
                    + getCOVIDInfo().getTimeline().replace("'", "''")+ "', '"
                    + getCOVIDInfo().getFig().replace("'", "''")+ "')");
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores vaccines
    public void storeVaccines() {
        try {
            stmt.execute("DELETE FROM ROOT.DBVACCINE");
            for (Vaccine item : getVaccineArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBVACCINE (VACCID, NAME, AUTHORISED, DESCRIPTION, DOSES, TEMP, EFFICACY) VALUES ("
                        + item.getVaccId() + ", '"
                        + item.getVaccName().replace("'", "''")+ "', "
                        + item.isVaccAuthorized()+ ", '"
                        + item.getVaccTypeDesc().replace("'", "''")+ "', "
                        + item.getReqDoses() + ", "
                        + item.getVaccineTemp() + ", "
                        + item.getEfficacy() + ")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores batches
    public void storeBatches() {
        try {
            stmt.execute("DELETE FROM ROOT.DBBATCH");
            for (VaccineBatch item : getVacBatchArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBBATCH (BATCHNO, VACCID, ORDEREDQTY, QTYUSED, EXPIRY) VALUES ("
                        + item.getBatchNo() + ", "
                        + item.getVaccId()+ ", "
                        + item.getOrderQty()+ ", "
                        + item.getQtyUsed()+ ", TIMESTAMP('"
                        + new Timestamp(item.getExpiryDate().getTime())+"'))");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }
    //stores appointments
    public void storeAppointments() {
        try {
            stmt.execute("DELETE FROM ROOT.DBAPPOINTMENT");
            for (Appointment item : getAppointmentArrayList()){
                stmt.executeUpdate("INSERT INTO ROOT.DBAPPOINTMENT (APPOINTMENTID, APPDATE, APPSTATUS) VALUES ("
                        + item.getAppointmentId() + ", "
                        +  " TIMESTAMP('"
                        + new Timestamp(item.getAppointmentDate().getTime())+"'), '"
                        + item.getAppointmentStatus().replace("'", "''") + "')");
                //logic for adding values when not null
                if (item.getUser() != null) {
                    stmt.executeUpdate("UPDATE DBAPPOINTMENT SET PATIENT = '"
                            + item.getUser().getUserId().replace("'", "''")
                            +"' WHERE APPOINTMENTID = "+item.getAppointmentId());
                }

                if (item.getClinician() != null) {
                    stmt.executeUpdate("UPDATE DBAPPOINTMENT SET CLINICIAN = '"
                            + item.getClinician().getUserId().replace("'", "''")
                            +"' WHERE APPOINTMENTID = "+item.getAppointmentId());
                }

                if (item.getCentre() != null) {
                    stmt.executeUpdate("UPDATE DBAPPOINTMENT SET CENTRE = '"
                            + item.getCentre().getCentreEircode().replace("'", "''")
                            +"' WHERE APPOINTMENTID = "+item.getAppointmentId());
                }

                if (item.getVaccineBatch() != null) {
                    stmt.executeUpdate("UPDATE DBAPPOINTMENT SET BATCH = "
                            + item.getVaccineBatch().getBatchNo()
                            +" WHERE APPOINTMENTID = "+item.getAppointmentId());
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database Write Error");
            displayAlert("An Error Has Occured","Unable to write to the file",ERROR);
            loadAll();
            storeAll();
        }
    }

    /*
         CREATE
         --------------------------------------------------

         The following section contains the create methods

         --------------------------------------------------
    */

    //creates clinician
    public void createClinician(Clinician newClinician){
        //creates a new User to pass the details to the user list
        User newUser = new User(newClinician);
        this.createUser(newUser);
        //adds to the arraylist
        this.clinicianArrayList.add(newClinician);
        this.storeClinicians();
    }
    //creates patient
    public void createPatient(Patient newPatient){
        //creates a new User to pass the details to the user list
        User newUser = new User(newPatient);
        this.createUser(newUser);
        //adds to the arraylist
        this.patientArrayList.add(newPatient);
        this.storePatients();
    }
    //creates pha
    public void createPHA(PHA newPHA){
        //creates a new User to pass the details to the user list
        User newUser = new User(newPHA);
        this.createUser(newUser);
        //adds to the arraylist
        this.phaArrayList.add(newPHA);
        this.storePHAs();
    }
    //creates user
    public void createUser(User newUser){
        //We don't want duplication
        if(!isUserAlreadyHere(newUser)){
            this.userArrayList.add(newUser);
        }
        this.storeUsers();
    }
    //creates centre
    public void createVacCentre(VaccinationCentre newVacCentre){
        //allows only the creation of new centres at unused eircodes
        if(getCentreByEircode(newVacCentre.getCentreEircode())==null){
            this.vacCentreArrayList.add(newVacCentre);
            this.storeCentres();
        }
    }
    //creates batch
    public void createVacBatch(VaccineBatch newVacBatch){
        this.vacBatchArrayList.add(newVacBatch);
        this.storeBatches();
    }
    //creates vaccine
    public void createVaccine(Vaccine newVaccine){
        this.vaccineArrayList.add(newVaccine);
        this.storeVaccines();
    }
    //creates appointment
    public void createAppointment(Appointment newAppointment){
        this.appointmentArrayList.add(newAppointment);
        this.storeAppointments();
    }
    //creates disease
    public void createDisease(InfectiousDiseases ill){
        this.diseasesArrayList.add(ill);
        this.storeDiseases();
    }
    /*
         RETRIEVE
         --------------------------------------------------

         The following section is for retrieving lists

         --------------------------------------------------
    */

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
    public COVIDInformation getCOVIDInfo() {
        return COVIDInfo;
    }

    /*
         UPDATE
         --------------------------------------------------

         The following section is updating list items

         --------------------------------------------------
    */

    //updates appointment
    public void updateAppointment(Appointment changedAppointment){
        for (int i=0; i < this.appointmentArrayList.size(); i++){
            if (this.appointmentArrayList.get(i).getAppointmentId() == changedAppointment.getAppointmentId()){
                this.appointmentArrayList.set(i, changedAppointment);
                break;
            }
        }
        this.storeAppointments();
    }
    //updates clinician
    public void updateClinician(Clinician changedClinician){
        for (int i=0; i < this.clinicianArrayList.size(); i++){
            if (this.clinicianArrayList.get(i).getUserId().equals(changedClinician.getUserId())){
                this.clinicianArrayList.set(i, changedClinician);
                break;
            }
        }
        //this will update their entry in the user arraylist as well
        updateUser(changedClinician);
        this.storeClinicians();
    }
    //updates pha
    public void updatePatient(Patient changedPatient){
        for (int i=0; i < this.patientArrayList.size(); i++){
            if (this.patientArrayList.get(i).getUserId().equals(changedPatient.getUserId())){
                this.patientArrayList.set(i, changedPatient);
                break;
            }
        }
        //this will update their entry in the user arraylist as well
        updateUser(changedPatient);
        this.storePatients();
    }
    //updates pha
    public void updatePHA(PHA changedPHA){
        for (int i=0; i < this.phaArrayList.size(); i++){
            if (this.phaArrayList.get(i).getUserId().equals(changedPHA.getUserId())){
                this.phaArrayList.set(i, changedPHA);
                break;
            }
        }
        updateUser(changedPHA);
        this.storePHAs();
    }
    //updates user
    public void updateUser(User changedUser){
        for (int i=0; i < this.userArrayList.size(); i++){
            if (this.userArrayList.get(i).getUserId().equals(changedUser.getUserId())){
                this.userArrayList.set(i, changedUser);
                break;
            }
        }
        this.storeUsers();
    }
    //updates centre
    public void updateCentre(VaccinationCentre changedVacCentre){
        for (int i=0; i < this.vacCentreArrayList.size(); i++){
            if (this.vacCentreArrayList.get(i).getCentreEircode().equals(changedVacCentre.getCentreEircode())){
                this.vacCentreArrayList.set(i, changedVacCentre);
                break;
            }
        }
        this.storeCentres();
    }
    //updates batch
    public void updateBatch(VaccineBatch changedVacBatch){
        for (int i=0; i < this.vacBatchArrayList.size(); i++){
            if (this.vacBatchArrayList.get(i).getBatchNo() == changedVacBatch.getBatchNo()){
                this.vacBatchArrayList.set(i, changedVacBatch);
                break;
            }
        }
        this.storeBatches();
    }
    //updates vaccine
    public void updateVaccine(Vaccine changedVaccine){
        for (int i=0; i < this.vaccineArrayList.size(); i++){
            if (this.vaccineArrayList.get(i).getVaccId() == changedVaccine.getVaccId()){
                this.vaccineArrayList.set(i, changedVaccine);
                //updates an batches also using this info
                for (VaccineBatch item : vacBatchArrayList) {
                    if(item.getVaccId() == changedVaccine.getVaccId()) {
                        updateBatch(new VaccineBatch(changedVaccine, item.getBatchNo(), item.getOrderQty(), item.getQtyUsed(), item.getExpiryDate()));
                    }
                }
                break;
            }
        }
        this.storeVaccines();
    }
    //updates a disease
    public void updateDisease(InfectiousDiseases ill){
        for (int i=0; i < this.diseasesArrayList.size(); i++){
            if (this.diseasesArrayList.get(i).getName().equals(ill.getName())){
                this.diseasesArrayList.set(i, ill);
                break;
            }
        }
        this.storeDiseases();
    }

    /*
         DELETE
         --------------------------------------------------

         The following section is for storing to the database

         --------------------------------------------------
    */

    //deletes an appointment by id
    public void delAppointment(int appointmentId){
        for (int i=0; i < this.appointmentArrayList.size(); i++){
            if (this.appointmentArrayList.get(i).getAppointmentId()== appointmentId){
                this.appointmentArrayList.remove(i);
            }
        }
        this.storeAppointments();
    }
    //deletes a clinician by id
    public void delClinician(String userId){
        for (int i=0; i < this.clinicianArrayList.size(); i++){
            if (this.clinicianArrayList.get(i).getUserId().equals(userId)){
                this.clinicianArrayList.remove(i);
            }
        }
        this.storeClinicians();
    }
    //deletes a patient by id
    public void delPatient(String userId){
        for (int i=0; i < this.patientArrayList.size(); i++){
            if (this.patientArrayList.get(i).getUserId().equals(userId)){
                this.patientArrayList.remove(i);
            }
        }
        this.storePatients();
    }
    //deletes a pha by id
    public void delPha(String phaID){
        for (int i=0; i < this.phaArrayList.size(); i++){
            if (this.phaArrayList.get(i).getUserId() == phaID){
                this.phaArrayList.remove(i);
            }
        }
        this.storePHAs();
    }
    //deletes a user by id
    public void delUser(String userId){
        for (int i=0; i < this.userArrayList.size(); i++){
            if (this.userArrayList.get(i).getUserId().equals(userId)){
                this.userArrayList.remove(i);
            }
        }
        //removes associated fields
        delPatient(userId);
        delPha(userId);
        delClinician(userId);
        this.storeUsers();
    }
    //deletes a centre by eircode
    public void delVaccinationCentre(String centreId){
        for (int i=0; i < this.vacCentreArrayList.size(); i++){
            if (this.vacCentreArrayList.get(i).getCentreEircode().equals(centreId)){
                this.vacCentreArrayList.remove(i);
            }
        }
        this.storeCentres();
    }
    //deletes a batch
    public void delVaccineBatch(int batchNo){
        for (int i=0; i < this.vacBatchArrayList.size(); i++){
            if (this.vacBatchArrayList.get(i).getBatchNo() == batchNo){
                this.vacBatchArrayList.remove(i);
            }
        }
        storeBatches();
    }
    //deletes vaccine
    public void delVaccine(int vaccineID){
        for (int i=0; i < this.vaccineArrayList.size(); i++){
            if (this.vaccineArrayList.get(i).getVaccId() == vaccineID){
                //removes any batches with this vaccine
                for (VaccineBatch item : vacBatchArrayList) {
                    if(item.getVaccId() == vaccineID) {
                        delVaccineBatch(item.getBatchNo());
                    }
                }
                this.vaccineArrayList.remove(i);
            }
        }
        storeVaccines();
    }

    /*
         QUERIES
         --------------------------------------------------

         The following section is for querying arraylists

         --------------------------------------------------
    */

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

    //finds a pha in the array list so we can return them
    //given their id value entered as an arg
    public PHA getPhaById(String id){
        for(PHA chad : this.getPhaArrayList()){
            if(id.equals(chad.getUserId())){
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

    //Returns list of patients with maySchedule status
    public ArrayList<Patient> getPatientsMaySchedule() {
        ArrayList<Patient> pat = new ArrayList<Patient>();
        for (Patient juan : this.getPatientArrayList() ) {
            if(juan.isEligibility() && this.getAppsByUserIdActive(juan.getUserId()).size() < 2) {
                pat.add(juan);
            }

        }
        return pat;
    }

    //finds a clinician in the array list so we can return them
    //given their id value entered as an arg
    public Clinician getClinicianById(String id){
        if (id.equals("")) {
            return null;
        }

        for(Clinician chad : this.getClinicianArrayList()){
            if(id.equals(chad.getUserId())){
                return chad;
            }
        }
        return null;
    }

    //returns batch with the batchno entered
    public VaccineBatch getVaccBatchById(int id){
        for(VaccineBatch chad : this.getVacBatchArrayList()){
            if(id == chad.getBatchNo()){
                return chad;
            }
        }
        return null;
    }

    //gets a list of all batches with greater than the amount of stock entered
    public ArrayList<VaccineBatch> getBatchAvailable(int minAmt){
        ArrayList<VaccineBatch> batches = new  ArrayList<VaccineBatch>();
        for(VaccineBatch vim : this.getVacBatchArrayList()){
            if(vim.getQtyRemaining()>=minAmt)
                batches.add(vim);
        }
        return batches;
    }

    //returns the ID of a vaccine based on its name
    public Vaccine getVaccineByName (String name){
        for(Vaccine chad : this.getVaccineArrayList()){
            if(name.equals(chad.getVaccName())){
                return chad;
            }
        }
        return null;
    }

    //returns the ID of a vaccine based on its name
    public Vaccine getVaccineById (int name){
        for(Vaccine chad : this.getVaccineArrayList()){
            if(name == chad.getVaccId()){
                return chad;
            }
        }
        return null;
    }

    //finds a centre in the array list so we can return them
    //given the eircode value entered as an arg
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

    //returns all of a users appointments
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
            if(app.getClinician() != null ) {
                if (id.equals(app.getClinician().getUserId())) {
                    myAppointments.add(app);
                }
            }
        }
        return myAppointments;
    }
    //returns appointment with the appId entered
    public Appointment getAppByAppId(int id){
        for(Appointment app : this.getAppointmentArrayList()){
            if(id == app.getAppointmentId()){
                return app;
            }
        }
        return null;
    }

    //calculates the next available appId
    public int nextAppId(){
        int lem = 0;
        for (Appointment appo: this.getAppointmentArrayList()) {
            if(appo.getAppointmentId()>lem){
                lem = appo.getAppointmentId();
            }
        }
        return lem+1;
    }

    //calculates the next available batchNo
    public int nextBatchId(){
        int lem = 0;
        for (VaccineBatch btch: this.getVacBatchArrayList()) {
            if(btch.getBatchNo()>lem){
                lem = btch.getBatchNo();
            }
        }
        return lem+1;
    }

    //Returns list of appointments ongoing at a vaccCentre
    public ArrayList<Appointment> getAppsByCentreEircodeActiveOngoing(String id){
        ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
        for(Appointment app : this.getAppointmentArrayList()){
            if(id.equals(app.getCentre().getCentreEircode()) && !(app.getAppointmentStatus().equals("Cancelled")) && !(app.getAppointmentStatus().equals("Successful")) && !(app.getAppointmentStatus().equals("Absent")) ){
                myAppointments.add(app);
            }
        }
        return myAppointments;
    }

    public boolean doesDiseaseExist(String name){
        for (InfectiousDiseases ill : getDiseasesArrayList()) {
            if(ill.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    //checks if a user already exists
    public boolean isUserAlreadyHere(User juan){
        for(User ben: this.getUserArrayList()) {
            if (ben.getUserId().equals(juan.getUserId())) {
                return true;
            }
        }
        return false;
    }

    /*
    LEGACY CODE BELOW
     */

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
