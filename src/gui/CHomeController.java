//clinician home scene
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Appointment;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.lang.model.util.SimpleElementVisitor6;

import static bll.CsvInterface.dateToStringShort;
import static bll.CsvInterface.dateToStringTime;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * 
 */

public class CHomeController implements Initializable {
    /**
     * Method will change scene to Log In when called
     * @param event
     * @throws java.io.IOException
    */

    @FXML 
    private TableView<Appointment> tblAppointments;
    @FXML 
    private TableColumn<Appointment, String> colPPS;
    @FXML 
    private TableColumn<Appointment, String> colDate;
    @FXML 
    private TableColumn<Appointment, String> colTime;
    @FXML 
    private TableColumn<Appointment, String> colStatus;
    @FXML 
    private TableColumn<Appointment, String> colBatch;
    @FXML 
    private TableColumn<Appointment, String> colEircode;
    @FXML 
    private TableColumn<Appointment, Integer> colAppId;

    @FXML 
    private TextField txtTime;
    @FXML
    private ComboBox  cboStatus;

    static Appointment app = new Appointment();


    public void clinicianlogOutButton(ActionEvent event) throws IOException {
        Parent cLogOutParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene cLogOutScene = new Scene(cLogOutParent);
        currentUser = null;
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cLogOutScene);
        window.show();
    }

    /**
     * Method will change scene to cNewAppointment when called
     * @param event
     * @throws java.io.IOException
    */
    public void clinicianNewApButton(ActionEvent event) throws IOException {
        Parent cNewAppParent = FXMLLoader.load(getClass().getResource("cNewAppointment.fxml"));
        Scene cNewAppScene = new Scene(cNewAppParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cNewAppScene);
        window.show();
    }

    /**
     * Method will change scene to cAppDet when called
     * @param event
     * @throws java.io.IOException
     * this button is temporary, ideally clicking on user in table will bring to app details
    */
     public void clinicianAppDetButton(ActionEvent event) throws IOException {
        if(app == null){
            displayAlert("Item Missing", "No item has been selected for viewing", ERROR);
        } else{
            Parent cAppDetParent = FXMLLoader.load(getClass().getResource("cAppointmentDetails.fxml"));
            Scene cAppDetScene = new Scene(cAppDetParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(cAppDetScene);
            window.show();
        }

    }

    public void saveButton(ActionEvent event) throws IOException {

         if(app == null) {
            displayAlert("Not Selected", "Please select the appointment you wish to update", WARNING);
        } else if(!txtTime.getText().matches("(?:[01]\\d|2[0123]):(?:[012345]\\d)") || cboStatus.getSelectionModel().getSelectedItem().toString().equals("")) {
            displayAlert("Empty Fields", "Please enter in the time and status of the appointment. No field may be left empty. Time must be entered as HH:mm", WARNING);
        } else {
             String valStatus = app.getAppointmentStatus();
            app.setAppointmentStatus(cboStatus.getSelectionModel().getSelectedItem().toString());
            app.setAppointmentDate(app.getAppointmentDateShort() + " " + txtTime.getText());

            //the following logic will change stock values when a status is turned to successful from another value .. ie not successful
            boolean bon1 = !valStatus.equals(app.getAppointmentStatus());
            boolean bon2 = app.getAppointmentStatus().equals("Successful");
            if(bon1 && bon2 &&app.getVaccineBatch()!=null){
                dataPersistenceLayer.getVaccBatchById(app.getVaccineBatch().getBatchNo()).incrementUsed();
            }


            dataPersistenceLayer.updateAppointment(app);

        }

        tblAppointments.setItems(getApps());
        tblAppointments.refresh();
    }


    public ObservableList<Appointment> getApps(){
        return FXCollections.observableArrayList(dataPersistenceLayer.getAppsByClinicianIdAll(currentUser.getUserId()));
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        app=null;
        final String[] items = {"Requested",
                "Absent",
                "Authorised",
                "Cancelled",
                "Successful"};
        colAppId.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        colPPS.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUserId()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDateShort()));
        colTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDateTime()));
        colStatus.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentStatus"));
        colBatch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVaccineBatch().getBatchNoAsString()));
        colEircode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCentre().getCentreEircode()));

        tblAppointments.setItems(getApps());
        tblAppointments.refresh();

        cboStatus.setItems(FXCollections.observableArrayList(items));
        txtTime.setText(null);
        cboStatus.getSelectionModel().clearSelection();

        tblAppointments.setOnMouseClicked((
                MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                //Check whether item is selected and set value of selected item to Label
                if(tblAppointments.getSelectionModel().getSelectedItem() != null) {
                        app =tblAppointments.getSelectionModel().getSelectedItem();
                        txtTime.setText(app.getAppointmentDateTime());
                        cboStatus.getSelectionModel().select(app.getAppointmentStatus());
                } else{
                    cboStatus.getSelectionModel().clearSelection();
                    txtTime.setText(null);
                }
            }
        });

    }



}