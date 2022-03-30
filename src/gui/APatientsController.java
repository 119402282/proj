/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bll.Appointment;
import static bll.CsvInterface.dateToLocal;
import static bll.CsvInterface.dateToStringShort;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bll.User;
import bll.Patient;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import bll.VaccinationCentre;
import static bll.CsvInterface.formatEircode;
import static gui.AlertBox.displayAlert;

import static gui.IS2209Project.dataPersistenceLayer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static java.util.Objects.isNull;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class APatientsController implements Initializable {
    /**
     * Method will change scene to aHome when called
     *
     * @param event
     * @throws IOException
     */

    @FXML
    private TableView<Patient> tblPatient;
    @FXML
    private TableColumn<Patient, String> colUserId;
    @FXML
    private TableColumn<Patient, String> colFName;
    @FXML
    private TableColumn<Patient, String> colLName;
    @FXML
    private TableColumn<Patient, String> colEmail;
    @FXML
    private TableColumn<Patient, String> colPhone;
    @FXML
    private TableColumn<Patient, String> colAccountType;
    @FXML
    private TableColumn<Patient, String> colPassword;
    @FXML
    private TableColumn<Patient, String> colEircode;
    @FXML
    private TableColumn<Patient, Date> colDob;
    @FXML
    private TableColumn<Patient, Boolean> colEligibility;
    @FXML
    private TableColumn<Patient, Boolean> colMaySchedule;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;   
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMobile; 
    @FXML
    private TextField txtEircode;
    @FXML
    private TextField txtPassword;
    @FXML
    private CheckBox chkEligibility;   
    @FXML
    private CheckBox chkMaySchedule;
    @FXML
    private DatePicker dtpDob;  
    @FXML
    private TextField txtAccountType;

    
    
    
    public void aHomeButton(ActionEvent event) throws IOException {

        Parent homeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
        Scene home = new Scene(homeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirm box
        
        window.setScene(home);
        window.show();

    }

    public void addButton(ActionEvent event) throws IOException {
        
          LocalDate local = (dtpDob.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateOfBirth = Date.from(instantDate);  

        Patient patient = new Patient(txtUserId.getText(), txtFName.getText(), txtLName.getText(), txtPassword.getText(),
                txtEmail.getText(), formatEircode(txtEircode.getText().toUpperCase()), txtMobile.getText(), 
                dateOfBirth, chkEligibility.isSelected(), chkMaySchedule.isSelected() );
        
        if(patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
            
        } else if (patient.getUserId().length() < 8 || (patient.getUserId().length() > 9)) {
            displayAlert("PPSN Error", "The PPS number entered doesn't match the required length for a PPS number. \n PPS numbers must be between 8 and 9 characters.", ERROR);
        } else if (patient.getFName().length()<2 || patient.getFName().length()>50){
            displayAlert("First Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (patient.getLName().length()<2 || patient.getLName().length()>50){
            displayAlert("Last Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (patient.getPassword().length() < 6 || (patient.getPassword().length() > 20)) {
            displayAlert("Password Error", "The password entered doesn't match the length requirements. \n A password must be between 6 and 20 characters.", ERROR);
        } else if (patient.getEmail().length() < 6 || (patient.getEmail().length() > 50)) {
                displayAlert("Email Error","The email entered doesn't match the length requirements. \n An email must be between 6 and 50 characters.",ERROR);
        } else if (patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
        } else if ((patient.getMobile().length() <10) || (patient.getMobile().length() > 10) || (!patient.getMobile().matches("[0-9]+")) ){
                displayAlert("Mobile Error", "The Phone Number entered is invalid.", ERROR);
        } else if(isNull(patient.getDob())) {
                displayAlert("Date Error","Please enter a date of birth.",ERROR);
        } else if (dataPersistenceLayer.getUserById(patient.getUserId() )!= null ){
            displayAlert("Patient Already Exists", "The UserID entered already exists. \n Click update if you are trying to change an existing entry", WARNING);
        }else {    
            dataPersistenceLayer.createPatient(patient);
            tblPatient.setItems(getPatients());
            txtUserId.setText("");
            txtFName.setText("");
            txtLName.setText("");
            txtPassword.setText("");
            txtEmail.setText("");
            txtEircode.setText("");
            txtMobile.setText("");        
            dtpDob.setValue(null);
            chkEligibility.setSelected(false);
            chkMaySchedule.setSelected(false);        }
    }
    
    
     public void updateButton(ActionEvent event) throws IOException {
         
            LocalDate local = (dtpDob.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateOfBirth = Date.from(instantDate);  

        Patient patient = new Patient(txtUserId.getText(), txtFName.getText(), txtLName.getText(), txtPassword.getText(),
                txtEmail.getText(), formatEircode(txtEircode.getText().toUpperCase()), txtMobile.getText(), 
                dateOfBirth, chkEligibility.isSelected(), chkMaySchedule.isSelected() );
        
        if(patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
        } else if (patient.getUserId().length() < 8 || (patient.getUserId().length() > 9)) {
            displayAlert("PPSN Error", "The PPS number entered doesn't match the required length for a PPS number. \n PPS numbers must be between 8 and 9 characters.", ERROR);
        } else if (patient.getFName().length()<2 || patient.getFName().length()>25){
            displayAlert("First Name Error", "The name must be at least two letters with maximum of 25 letters in length.", WARNING);
        } else if (patient.getLName().length()<2 || patient.getLName().length()>25){
            displayAlert("Last Name Error", "The name must be at least two letters with maximum of 25 letters in length.", WARNING);
        } else if (patient.getPassword().length() < 6 || (patient.getPassword().length() > 20)) {
            displayAlert("Password Error", "The password entered doesn't match the length requirements. \n A password must be between 6 and 20 characters.", ERROR);
        } else if (patient.getEmail().length() < 6 || (patient.getEmail().length() > 50)) {
                displayAlert("Email Error","The email entered doesn't match the length requirements. \n An email must be between 6 and 50 characters.",ERROR);
        } else if (patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
        } else if ((patient.getMobile().length() <10) || (patient.getMobile().length() > 10) || (!patient.getMobile().matches("[0-9]+")) ){
                displayAlert("Mobile Error", "The Phone Number entered is invalid.", ERROR);
        } else if(isNull(patient.getDob())) {
                displayAlert("Date Error","Please enter a date of birth.",ERROR);
        } else if (dataPersistenceLayer.getUserById(patient.getUserId() )== null ){
            displayAlert("Existing UserID Not Found", "The UserID can not be updated.", WARNING);
        }else {    
            dataPersistenceLayer.updatePatient(patient);
            tblPatient.setItems(getPatients());
            txtUserId.setText("");
            txtFName.setText("");
            txtLName.setText("");
            txtPassword.setText("");
            txtEmail.setText("");
            txtEircode.setText("");
            txtMobile.setText("");        
            dtpDob.setValue(null);
            chkEligibility.setSelected(false);
            chkMaySchedule.setSelected(false);
        }
    }
     
      public void deleteButton(ActionEvent event) throws IOException {
        
        LocalDate local = (dtpDob.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateOfBirth = Date.from(instantDate);  

        Patient patient = new Patient(txtUserId.getText(), txtFName.getText(), txtLName.getText(), txtPassword.getText(),
                txtEmail.getText(), formatEircode(txtEircode.getText().toUpperCase()), txtMobile.getText(), 
                dateOfBirth, chkEligibility.isSelected(), chkMaySchedule.isSelected() );
        
        
        Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete this user?");
        alert.setHeaderText("Are you sure you want to delete this user?\nDeleting this user will delete all of the users past, current, and future appointments?");
        alert.showAndWait();

       if (alert.getResult() == ButtonType.NO) {   
        } else if(patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);            
        } else if (patient.getUserId().length() < 8 || (patient.getUserId().length() > 9)) {
            displayAlert("PPSN Error", "The PPS number entered doesn't match the required length for a PPS number. \n PPS numbers must be between 8 and 9 characters.", ERROR);
        } else if (patient.getFName().length()<2 || patient.getFName().length()>50){
            displayAlert("First Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (patient.getLName().length()<2 || patient.getLName().length()>50){
            displayAlert("Last Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (patient.getPassword().length() < 6 || (patient.getPassword().length() > 20)) {
            displayAlert("Password Error", "The password entered doesn't match the length requirements. \n A password must be between 6 and 20 characters.", ERROR);
        } else if (patient.getEmail().length() < 6 || (patient.getEmail().length() > 50)) {
                displayAlert("Email Error","The email entered doesn't match the length requirements. \n An email must be between 6 and 50 characters.",ERROR);
        } else if (patient.getEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
        } else if ((patient.getMobile().length() <10) || (patient.getMobile().length() > 10) || (!patient.getMobile().matches("[0-9]+")) ){
                displayAlert("Mobile Error", "The Phone Number entered is invalid.", ERROR);
        } else if(isNull(patient.getDob())) {
                displayAlert("Date Error","Please enter a date of birth.",ERROR);
        } else if (dataPersistenceLayer.getUserById(patient.getUserId() )== null ){
            displayAlert("Existing UserID Not Found", "The UserID could not be located.", WARNING);           
        } else {    
            dataPersistenceLayer.delPatient(patient.getUserId());
            tblPatient.setItems(getPatients());
            txtUserId.setText("");
            txtFName.setText("");
            txtLName.setText("");
            txtPassword.setText("");
            txtEmail.setText("");
            txtEircode.setText("");
            txtMobile.setText("");        
            dtpDob.setValue(null);
            chkEligibility.setSelected(false);
            chkMaySchedule.setSelected(false);
            if (dataPersistenceLayer.getAppsByUserIdAll(patient.getUserId()).size() > 0) {
                for (Appointment app: dataPersistenceLayer.getAppsByUserIdAll(patient.getUserId())){
                    dataPersistenceLayer.delAppointment(app.getAppointmentId());
                } 
            } 
        }
    }
           
    /**
     * Method will change scene to aVaccCentres when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccRequestsButton(ActionEvent event) throws IOException {
        Parent vaccRequestsParent = FXMLLoader.load(getClass().getResource("aVaccineRequests.fxml"));
        Scene vaccRequests = new Scene(vaccRequestsParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(vaccRequests);
        window.show();
    }
    
    /**
     * Method will change scene to pArticles when called
     * @param event
     * @throws java.io.IOException
    */
    public void aArticlesButton(ActionEvent event) throws IOException {
        Parent articlesParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene articles = new Scene(articlesParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(articles);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccineInfo when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccineInformationButton(ActionEvent event) throws IOException {
        Parent vaccInfoParent = FXMLLoader.load(getClass().getResource("aVaccineInfo.fxml"));
        Scene vaccInfo = new Scene(vaccInfoParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(vaccInfo);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccineInfo when called
     * @param event
     * @throws java.io.IOException
    */
    public void aLogOutButton(ActionEvent event) throws IOException {
        Parent logOutParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene logOut = new Scene(logOutParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(logOut);
        window.show();
    }
    
    /**
     * Method will change scene to aPatients when called
     * @param event
     * @throws java.io.IOException
    */
     public void aVaccinationCentresButton(ActionEvent event) throws IOException {
        Parent vacCenParent = FXMLLoader.load(getClass().getResource("aVaccineCentres.fxml"));
        Scene vacCen = new Scene(vacCenParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(vacCen);
        window.show();
    }
    
    
    public ObservableList<Patient> getPatients(){
        ObservableList<Patient> patients = FXCollections.observableArrayList(dataPersistenceLayer.getPatientArrayList());
        return patients;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        


        colEmail.setCellValueFactory(new PropertyValueFactory<Patient, String>("email"));
        colUserId.setCellValueFactory(new PropertyValueFactory<Patient, String>("userId"));
        colFName.setCellValueFactory(new PropertyValueFactory<Patient, String>("fName")); 
        colLName.setCellValueFactory(new PropertyValueFactory<Patient, String>("lName"));
        colAccountType.setCellValueFactory(new PropertyValueFactory<Patient, String>("accountType"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Patient, String>("mobile"));
        colEligibility.setCellValueFactory(new PropertyValueFactory<Patient, Boolean>("eligibility"));
        colDob.setCellValueFactory(new PropertyValueFactory<Patient, Date>("dob"));
        colEircode.setCellValueFactory(new PropertyValueFactory<Patient, String>("eircode"));
        colMaySchedule.setCellValueFactory(new PropertyValueFactory<Patient, Boolean>("maySchedule"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Patient, String>("password"));


        
        tblPatient.setItems(getPatients());
        
       
        
        tblPatient.setOnMouseClicked((MouseEvent event) -> {
        if(event.getButton().equals(MouseButton.PRIMARY)){
             //Check whether item is selected and set value of selected item to Label
            if(tblPatient.getSelectionModel().getSelectedItem() != null) {
                Patient patient =tblPatient.getSelectionModel().getSelectedItem();
                txtUserId.setText(patient.getUserId());
                txtFName.setText(patient.getFName());
                txtLName.setText(patient.getLName());
                txtPassword.setText(patient.getPassword());
                txtEmail.setText(patient.getEmail());
                txtEircode.setText(patient.getEircode());
                txtMobile.setText(patient.getMobile());
                dtpDob.setValue(dateToLocal(patient.getDob()));
                chkEligibility.setSelected(patient.isEligibility());
                chkMaySchedule.setSelected(patient.isMaySchedule());
            } else{
                txtUserId.setText("");
                txtFName.setText("");
                txtLName.setText("");
                txtPassword.setText("");
                txtEmail.setText("");
                txtEircode.setText("");
                txtMobile.setText("");
                dtpDob.setValue(null);
                chkEligibility.setSelected(false);
                chkMaySchedule.setSelected(false);
            }
        }
    });
       
    }   
}
