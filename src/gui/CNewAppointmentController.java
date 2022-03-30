//new appointment scene
package gui;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import bll.*;
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
import javafx.stage.Stage;

import static bll.CsvInterface.dateToLocal;
import static gui.AlertBox.displayAlert;
import static gui.CHomeController.app;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static java.lang.Integer.parseInt;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * 
 */
public class CNewAppointmentController implements Initializable {

    @FXML
    private ComboBox cboPPS;
    @FXML
    private DatePicker dtpDOB;
    @FXML
    private DatePicker dtpAppDate;

    @FXML
    private ComboBox cboCentre;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox cboBatch;
    @FXML
    private TextField txtAppTime;
    @FXML
    private TextField txtStatus;
    @FXML
    private Button btnHome;



    /**
     * Method will change scene to cHome when called
     * @param event
     * @throws java.io.IOException
     * this button is temporary, ideally clicking on user in table will bring to app details
    */
    public void homeButton(ActionEvent event) throws IOException {
        Parent cHomeParent = FXMLLoader.load(getClass().getResource("cHome.fxml"));
        Scene cHomeScene = new Scene(cHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cHomeScene);
        window.show();
    }
    
    /**
     * Method will change scene to cHome when called
     * @param event
     * @throws java.io.IOException
     * this button is temporary, ideally clicking on user in table will bring to app details
    */
    public void saveButton(ActionEvent event) throws IOException {
        if(txtName.getText().equals("")){
            displayAlert("Missing Field","Please select a patient from the combo box", Alert.AlertType.ERROR);
        }else if(cboCentre.getSelectionModel().getSelectedItem() == null) {
            displayAlert("Missing Field","Please select a vaccination centre from the combo box", Alert.AlertType.ERROR);
        } else if (cboBatch.getSelectionModel().getSelectedItem() == null){
            displayAlert("Missing Field","Please select a vaccine batch from the combo box", Alert.AlertType.ERROR);
        } else if( dtpAppDate.getValue()==null) {
            displayAlert("Invalid Date","Please select a date for the appointment", Alert.AlertType.ERROR);
        }  else if (dtpAppDate.getValue().isBefore(LocalDate.now())){
            displayAlert("Invalid Date","Please select a date in the future.", Alert.AlertType.ERROR);
        } else if (!txtAppTime.getText().matches("(?:[01]\\d|2[0123]):(?:[012345]\\d)")){
            displayAlert("Invalid Time","Please enter the time in the format HH:mm", Alert.AlertType.ERROR);
        } else {
            Clinician clin = dataPersistenceLayer.getClinicianById(currentUser.getUserId());
            Patient rufus = dataPersistenceLayer.getPatientById(cboPPS.getValue().toString());
            VaccinationCentre centre = dataPersistenceLayer.getCentreByEircode(cboCentre.getValue().toString());
            VaccineBatch bin = dataPersistenceLayer.getVaccBatchById(parseInt(cboBatch.getValue().toString()));
            Appointment appoint = new Appointment();
            LocalDate local = (dtpAppDate.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateApp = Date.from(instantDate);
            appoint.setAppointmentDate(dateApp);
            appoint.setAppointmentDate(appoint.getAppointmentDateShort() + " " + txtAppTime.getText());
            appoint.setAppointmentStatus("Authorised");
            appoint.setCentre(centre);
            appoint.setClinician(clin);
            appoint.setUser(rufus);
            appoint.setVaccineBatch(bin);
            appoint.setAppointmentId(dataPersistenceLayer.nextAppId());

            dataPersistenceLayer.createAppointment(appoint);
            cboPPS.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getPatientsMaySchedule()));
            cboBatch.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getBatchAvailable(1)));
            btnHome.fire();
        }

    }
    
    /**
     * Method will change scene to Log In when called
     * @param event
     * @throws java.io.IOException
    */
    public void logOutButton(ActionEvent event) throws IOException {
        Parent cLogOutParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene cLogOutScene = new Scene(cLogOutParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cLogOutScene);
        window.show();
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cboBatch.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getBatchAvailable(1)));
        cboCentre.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getVacCentreArrayList()));
        cboPPS.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getPatientsMaySchedule()));

        cboPPS.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            
            if(!cboPPS.getSelectionModel().isEmpty()){
            txtName.setText(dataPersistenceLayer.getPatientById(cboPPS.getValue().toString()).getFName()+" "+dataPersistenceLayer.getPatientById(cboPPS.getValue().toString()).getLName());
            dtpDOB.setValue(dateToLocal(dataPersistenceLayer.getPatientById(cboPPS.getValue().toString()).getDob()));
            } 
             
            }
        
        );


        txtStatus.setText("Authorised");

    }    
    
}
