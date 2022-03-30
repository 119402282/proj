/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bll.Appointment;

import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static java.lang.Integer.parseInt;
import static javafx.scene.control.Alert.AlertType.WARNING;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.text.TabableView;

/**
 * FXML Controller class
 *
 * @author eliot
 */

public class AVaccineRequestsController implements Initializable {

    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, String> colPPS;
    @FXML
    private TableColumn<Appointment, String> colDate;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, String> colBatch;
    @FXML
    private TableColumn<Appointment, String> colEircode;
    @FXML
    private TableColumn<Appointment, Integer> colAppId;
    @FXML
    private TableColumn<Appointment, String> colClinician;

    @FXML
    private ComboBox  cboStatus;
    @FXML
    private ComboBox cboBatch;
    @FXML
    private ComboBox cboClinician;

    static Appointment myApp = new Appointment();



    /**
     * Method will change scene to aHome when called
     * @param event
     * @throws java.io.IOException
    */
    public void aHomeButton(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
        Scene home = new Scene(homeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(home);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccCentres when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccCentresButton(ActionEvent event) throws IOException {
        Parent vaccCentresParent = FXMLLoader.load(getClass().getResource("aVaccineCentres.fxml"));
        Scene vaccCentres = new Scene(vaccCentresParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(vaccCentres);
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

        
        window.setScene(logOut);
        window.show();
    }
    
    /**
     * Method will change scene to aPatients when called
     * @param event
     * @throws java.io.IOException
    */
    public void aPatientsButton(ActionEvent event) throws IOException {
        Parent patientParent = FXMLLoader.load(getClass().getResource("aPatients.fxml"));
        Scene patient = new Scene(patientParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(patient);
        window.show();
    }

    public ObservableList<Appointment> getApps(){
        return FXCollections.observableArrayList(dataPersistenceLayer.getAppointmentArrayList());
    }


    public void btnConfirmAction(ActionEvent event) throws IOException {

        if(myApp == null) {
            displayAlert("Not Selected", "Please select the appointment you wish to assign to clinician", WARNING);
        } else if(cboBatch.getSelectionModel().getSelectedItem().toString().equals("") ||cboStatus.getSelectionModel().getSelectedItem().toString().equals("") || cboClinician.getSelectionModel().getSelectedItem().toString().equals("")) {
            displayAlert("Empty Fields", "No field may be left empty when assigning an appointment", WARNING);
        } else {
            String valStatus = myApp.getAppointmentStatus();
            myApp.setAppointmentStatus(cboStatus.getSelectionModel().getSelectedItem().toString());
            if(cboClinician.getSelectionModel().getSelectedItem().toString()!="Unassigned"){
                myApp.setClinician(dataPersistenceLayer.getClinicianById(cboClinician.getSelectionModel().getSelectedItem().toString()));
            }
            if(cboBatch.getSelectionModel().getSelectedItem().toString()!="Unassigned"){
                myApp.setVaccineBatch(dataPersistenceLayer.getVaccBatchById(parseInt(cboBatch.getSelectionModel().getSelectedItem().toString())));
            }
            //the following logic will change stock values when a status is turned to successful from another value .. ie not successful
            boolean bon1 = !valStatus.equals(myApp.getAppointmentStatus());
            boolean bon2 = myApp.getAppointmentStatus().equals("Successful");
            if(bon1 && bon2 && cboBatch.getSelectionModel().getSelectedItem().toString()!="Unassigned"){
                dataPersistenceLayer.getVaccBatchById(myApp.getVaccineBatch().getBatchNo()).incrementUsed();
            }

            dataPersistenceLayer.updateAppointment(myApp);

        }

        tblAppointments.setItems(getApps());
        tblAppointments.refresh();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final String[] items = {"Requested",
                "Absent",
                "Authorised",
                "Cancelled",
                "Successful"};



        myApp=null;


        colPPS.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserId()));
        //colAppId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        colClinician.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClinicianToTable()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDateShort()));

        colStatus.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentStatus"));
        colBatch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBatchToTable()));
        colEircode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCentre().getCentreEircode()));
        tblAppointments.setItems(getApps());


        cboBatch.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getBatchAvailable(1)));
        cboClinician.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getClinicianArrayList()));
        cboStatus.setItems(FXCollections.observableArrayList(items));

        cboStatus.getSelectionModel().clearSelection();
        cboBatch.getSelectionModel().clearSelection();
        cboClinician.getSelectionModel().clearSelection();

        tblAppointments.setOnMouseClicked((
                MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                //Check whether item is selected and set value of selected item to Label
                if(tblAppointments.getSelectionModel().getSelectedItem() != null) {
                    myApp =tblAppointments.getSelectionModel().getSelectedItem();

                    cboBatch.getSelectionModel().select(myApp.getBatchToTable());
                    cboClinician.getSelectionModel().select(myApp.getClinicianToTable());
                    cboStatus.getSelectionModel().select(myApp.getAppointmentStatus());
                } else{
                    cboStatus.getSelectionModel().clearSelection();
                    cboBatch.getSelectionModel().clearSelection();
                    cboClinician.getSelectionModel().clearSelection();
                }
            }
        });
        

          
    }    
}
