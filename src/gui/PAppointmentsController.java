//Appointments controller
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bll.Appointment;
import bll.Patient;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
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
import javafx.stage.Stage;

import static gui.IS2209Project.dataPersistenceLayer;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class PAppointmentsController implements Initializable {

    @FXML
    private TableView<Appointment> tblAppointments;
    @FXML
    private TableColumn<Appointment, Integer> colAppId;
    @FXML
    private TableColumn<Appointment, String> colDate;
    @FXML
    private TableColumn<Appointment, String> colTime;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, String> colClinician;
    @FXML
    private TableColumn<Appointment, String> colBatch;
    @FXML
    private TableColumn<Appointment, String> colEircode;




    /**
     * Method will change scene to pVacLoc appoint when called
     * @param event
     * @throws java.io.IOException
    */
    public void scheduleAppointmentButton(ActionEvent event) throws IOException {
        tblAppointments.refresh();
        if(dataPersistenceLayer.getPatientById(currentUser.getUserId()).isEligibility() && dataPersistenceLayer.getAppsByUserIdActive(currentUser.getUserId()).size()<2){
            Parent pAppointmentParent = FXMLLoader.load(getClass().getResource("pVacLocation.fxml"));
            Scene pVacLoc = new Scene(pAppointmentParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(pVacLoc);
            window.show();
        }else if(!dataPersistenceLayer.getPatientById(currentUser.getUserId()).isEligibility()){
            displayAlert("Uneligible", "Please retake the eligility test. You are currently ineligible. If this persists contact your GP", INFORMATION);
        }else{
            displayAlert("Uneligible", "You have already recieved or have ongoing requests for two appointments", INFORMATION);

        }
    }
    public void cancelApp(ActionEvent event){
        tblAppointments.refresh();
        //code for getting selected appId
        //Check whether item is selected and set value of selected item to Label
        if(tblAppointments.getSelectionModel().getSelectedItem() != null && !tblAppointments.getSelectionModel().getSelectedItem().getAppointmentStatus().equals("Cancelled")) {
            //ask for confirmation
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Cancel this appointment?");
            alert.setHeaderText("Are you sure you want to cancel this appointment?\nCancellations cannot be undone without help from an account with higher privileges.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                tblAppointments.getSelectionModel().getSelectedItem().setAppointmentStatus("Cancelled");
                dataPersistenceLayer.updateAppointment(tblAppointments.getSelectionModel().getSelectedItem());
                tblAppointments.refresh();
            }
        }
    }
    public void pLearnMoreButton(ActionEvent event) throws IOException {
        Parent learnMoreParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene learnMore = new Scene(learnMoreParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(learnMore);
        window.show();
    } 
    public void pGetVaccButton(ActionEvent event) throws IOException {
        
        //If a patient is already eligible, they don't need to fill out the questionnaire again
        
        Patient patient = new Patient();
        patient = dataPersistenceLayer.getPatientById(currentUser.getUserId());
        
        if(patient.isEligibility() == true){
            displayAlert("Info","You are already eligible",INFORMATION);
        } else {
            Parent pHomeParent = FXMLLoader.load(getClass().getResource("pVaccRequest.fxml"));
            Scene pGetVacc = new Scene(pHomeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(pGetVacc);
            window.show();
        }
    }
    
    
     public void pLogOutButton(ActionEvent event) throws IOException {
        Parent logOutParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene logOut = new Scene(logOutParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(logOut);
        window.show();
    }
     
    /**
     * Method will change scene to pHome appoint when called
     * @param event
     * @throws java.io.IOException
    */
    public void pHome(ActionEvent event) throws IOException {
        Parent pAppHomeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
        Scene pHome = new Scene(pAppHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pHome);
        window.show();
    }

    public ObservableList<Appointment> getApps(){
        return FXCollections.observableArrayList(dataPersistenceLayer.getAppsByUserIdAll(currentUser.getUserId()));
    }



    //add confirm box
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        colAppId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        colClinician.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClinicianToTable()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDateShort()));
       // colTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDateTime()));
        colStatus.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentStatus"));
        colBatch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBatchToTable()));
        colEircode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCentre().getCentreEircode()));

        tblAppointments.setItems(getApps());
         tblAppointments.refresh();

    }    
    
}