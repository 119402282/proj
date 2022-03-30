
//vaccination location controller
package gui;

import bll.Appointment;
import bll.Patient;
import bll.VaccinationCentre;

import static bll.CsvInterface.dateToLocal;
import static gui.AlertBox.displayAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static java.time.temporal.ChronoUnit.DAYS;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;


/**
 * FXML Controller class
 *
 * @author eliot
 */

public class PVacLocationController implements Initializable {


/**
     * Method will change scene to pHome when called
    */

    @FXML
    DatePicker dtpAppDate;
    @FXML
    ListView<String> lstCentres;

    VaccinationCentre newLoc = new VaccinationCentre();

    public ObservableList<String> getLstCentres() {
        ArrayList<String> centres = new ArrayList<>();
        for (int i = 0; i < dataPersistenceLayer.getVacCentreArrayList().size(); i++) {
            centres.add(dataPersistenceLayer.getVacCentreArrayList().get(i).getListed());
        }
        return FXCollections.observableArrayList(centres);
    }
    
    public void logOutButton(ActionEvent event) throws IOException {
        Parent pHomeParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene logIn = new Scene(pHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(logIn);
        window.show();
    }
    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
    */
    public void pHomeButton(ActionEvent event) throws IOException {
        if(currentUser.getAccountType().equals("PHA")){
        
            Parent aHomeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
            Scene aHome = new Scene(aHomeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(aHome);
            window.show();

        } else {
                   
            Parent homeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
            Scene homeInfo = new Scene(homeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            //Add if statement with confirmbox

            window.setScene(homeInfo);
            window.show();
        }
    }
      public void pLearnMoreButton(ActionEvent event) throws IOException {
        Parent pLearnMoreParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pLearnMore = new Scene(pLearnMoreParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pLearnMore);
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
    
      public void pAppointmentsButton(ActionEvent event) throws IOException {
        Parent pAppointmentsParent = FXMLLoader.load(getClass().getResource("pAppointments.fxml"));
        Scene pAppointments = new Scene(pAppointmentsParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pAppointments);
        window.show();
    }

    public boolean suitableGap(LocalDate loc){
        ArrayList<Appointment> apps = dataPersistenceLayer.getAppsByUserIdActive(currentUser.getUserId());
        if(apps.size() == 0){
            return true;
        }
        LocalDate org = dateToLocal(apps.get(0).getAppointmentDate());
        long daysBetween = DAYS.between(org, loc);
        if(daysBetween<14){
            return false;
        } else{
            return true;
        }
    }

    public void scheduleAppointmentButton(ActionEvent event) throws IOException {

        if(newLoc == null) {
            displayAlert("Missing Selection", "Select the Vaccination Centre for your vaccination", WARNING);
        } else if( dtpAppDate.getValue()==null) {
            displayAlert("Invalid Date","Please select a date for your vaccine", Alert.AlertType.ERROR);
        } else if (dtpAppDate.getValue().isBefore(LocalDate.now())){
            displayAlert("Invalid Date","Please select a date in the future.", Alert.AlertType.ERROR);
        } else if (!suitableGap(dtpAppDate.getValue())){
            displayAlert("Invalid Date","Please select a date that is not within 14 days of another of your vaccinations.", Alert.AlertType.ERROR);
        } else {
            Appointment newApp = new Appointment();
            newApp.setAppointmentId(dataPersistenceLayer.nextAppId());
            LocalDate local = (dtpAppDate.getValue());
            Instant instDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            newApp.setAppointmentDate(Date.from(instDate));
            newApp.setClinician(null);
            newApp.setVaccineBatch(null);
            newApp.setAppointmentStatus("Requested");
            newApp.setUser(dataPersistenceLayer.getPatientById(currentUser.getUserId()));
            newApp.setCentre(newLoc);

            dataPersistenceLayer.createAppointment(newApp);
            displayAlert("Added","The appointment has been requested. The PHA will assign you a clinician and batch of the vaccine soon.",INFORMATION);
            Parent pTempParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
            Scene tempBtn = new Scene(pTempParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tempBtn);
            window.show();
        }
    }






    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstCentres.setItems(getLstCentres());
        newLoc=null;

        lstCentres.setOnMouseClicked((
                MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                //Check whether item is selected and set value of selected item to Label
                if(lstCentres.getSelectionModel().getSelectedItem() != null) {
                    newLoc =dataPersistenceLayer.getVacCentreArrayList().get(lstCentres.getSelectionModel().getSelectedIndex());

                } else{
                   newLoc = null;
                }
            }
        });

    }
}
