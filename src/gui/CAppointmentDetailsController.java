//appointment details scene
package gui;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
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
import static gui.IS2209Project.dataPersistenceLayer;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * FXML Controller class
 *
 * 
 */
public class CAppointmentDetailsController implements Initializable {
    /**
     * Method will change scene to cHome when called
     * @param event
     * @throws java.io.IOException
     * this button is temporary, ideally clicking on user in table will bring to app details
    */

    @FXML
    private TextField txtPPS;
    @FXML
    private DatePicker dtpDOB;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dtpAppDate;
    @FXML
    private TextField txtVaccinationCentre;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtEircode;
    @FXML
    private TextField txtAppTime;
    @FXML
    private TextField txtBatch;
    @FXML
    private Button btnHome;


    public void homeButton(ActionEvent event) throws IOException {
        Parent cHomeParent = FXMLLoader.load(getClass().getResource("cHome.fxml"));
        Scene cHomeScene = new Scene(cHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cHomeScene);
        window.show();
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
     * Method saves
     * @param event
     * @throws java.io.IOException
     */
    public void saveButton(ActionEvent event) throws IOException {

        if(!txtAppTime.getText().matches("(?:[01]\\d|2[0123]):(?:[012345]\\d)")) {
            displayAlert("Empty Fields", "Please enter in the time of the appointment. Time must be entered as HH:mm", WARNING);
        } else if (dtpAppDate.getValue().isBefore(LocalDate.now()) && dtpAppDate.getValue()!= dateToLocal(app.getAppointmentDate())){
            displayAlert("Invalid Date", "You cannot save/overwrite the date to a date in the past. Records of dates past will not change", WARNING);
        }else{
            LocalDate local = (dtpAppDate.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateApp = Date.from(instantDate);
            app.setAppointmentDate(dateApp);
            app.setAppointmentDate(app.getAppointmentDateShort() + " " + txtAppTime.getText());
            dataPersistenceLayer.updateAppointment(app);
            displayAlert("Updated Field", "The appointment details have been updated", INFORMATION);
            btnHome.fire();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtPPS.setText(app.getUser().getUserId());
        txtName.setText(app.getUser().getFName()+" "+app.getUser().getLName());
        txtEmail.setText(app.getUser().getEmail());
        txtEircode.setText(app.getUser().getEircode());
        txtBatch.setText(app.getVaccineBatch().getBatchNoAsString());
        txtMobile.setText(app.getUser().getMobile());
        txtVaccinationCentre.setText(app.getCentre().getCentreEircode());
        dtpDOB.setValue(dateToLocal(app.getUser().getDob()));

        txtAppTime.setText(app.getAppointmentDateTime());
        dtpAppDate.setValue(dateToLocal(app.getAppointmentDate()));
    }
    
}
