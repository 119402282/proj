// COVID info controller
package gui;

import bll.Patient;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class PCOVIDController implements Initializable {
    
    /**
    * Method will change scene to pLearnMore when called
    * @param event
    * @throws java.io.IOException
    */
   @FXML private TextArea txtGeneral;
   @FXML private TextArea txtSymptoms;
   @FXML private TextArea txtRollout;
   @FXML private TextArea txtTimeline;
   @FXML private TextArea txtFig;
   @FXML private Button btnSave;
   @FXML private Button btnLearnMore;
   @FXML private Button btnAppointments;
   @FXML private Button btnGetVaccinated;


    public void returnButton(ActionEvent event) throws IOException {
        Parent pCOVIDReturnParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pCOVIDReturn = new Scene(pCOVIDReturnParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCOVIDReturn);
        window.show();
    }
    
    /**
    * Method will change scene to pCOVIDVacc when called
    * @param event
    * @throws java.io.IOException
    */
    public void covidVacButton(ActionEvent event) throws IOException {
        Parent pCOVIDVacParent = FXMLLoader.load(getClass().getResource("pCOVIDVacc.fxml"));
        Scene pCOVIDVac = new Scene(pCOVIDVacParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCOVIDVac);
        window.show();
    }
    
     public void infVaccineButton(ActionEvent event) throws IOException {
        Parent pInfVaccineParent = FXMLLoader.load(getClass().getResource("pCOVIDVacc.fxml"));
        Scene pVaccine = new Scene(pInfVaccineParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pVaccine);
        window.show();
    }
     
     public void infDiseasesButton (ActionEvent event) throws IOException {
        Parent pLearnInfParent = FXMLLoader.load(getClass().getResource("pInfections.fxml"));
        Scene pInfDis = new Scene(pLearnInfParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pInfDis);
        window.show();
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
    
     public void btnAction(ActionEvent event) throws IOException {
        //     saving text to string
        //     doesn't actually retain since strings are hard-coded in
        dataPersistenceLayer.getCOVIDInfo().setGeneralInfo(txtGeneral.getText());
        dataPersistenceLayer.getCOVIDInfo().setSymptoms(txtSymptoms.getText());
        dataPersistenceLayer.getCOVIDInfo().setRollout(txtRollout.getText());
        dataPersistenceLayer.getCOVIDInfo().setTimeline(txtTimeline.getText());
        dataPersistenceLayer.getCOVIDInfo().setFig(txtFig.getText());
        dataPersistenceLayer.storeCovid();
     }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtGeneral.setText(dataPersistenceLayer.getCOVIDInfo().getGeneralInfo());
        txtSymptoms.setText(dataPersistenceLayer.getCOVIDInfo().getSymptoms());
        txtRollout.setText(dataPersistenceLayer.getCOVIDInfo().getRollout());
        txtTimeline.setText(dataPersistenceLayer.getCOVIDInfo().getTimeline());
        txtFig.setText(dataPersistenceLayer.getCOVIDInfo().getFig());
        
        //logic to make sure only pha can edit
        if(currentUser.getAccountType().equals("PHA")){
            txtGeneral.setEditable(true);
            txtSymptoms.setEditable(true);
            txtRollout.setEditable(true);
            txtTimeline.setEditable(true);
            txtFig.setEditable(true);
            btnLearnMore.setVisible(false);
            btnGetVaccinated.setVisible(false);
            btnAppointments.setVisible(false);
        
        } else {
                btnSave.setVisible(false);
        } 

        // TODO
    }    
    
}
