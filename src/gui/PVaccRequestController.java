//Get Vaccinated Controller (questionnaire scene)
package gui;

import bll.Patient;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;



/**
 * FXML Controller class
 *
 * @author eliot
 */
public class PVaccRequestController implements Initializable {
    
    /**
     * Method will change scene to pHome when called
     */
    
    @FXML
    private RadioButton rdbSymptomsYes;
    //@FXML
    //private RadioButton rdbSymptomsNo;
    @FXML
    private RadioButton rdbAbroadYes;
    //@FXML
    // private RadioButton rdbAbroadNo;
    @FXML
    private RadioButton rdbContactYes;
    @FXML
    private RadioButton rdbBleedYes;
    @FXML
    private RadioButton rdbPregnantYes;
    @FXML
    private RadioButton rdbVaccineYes;
    @FXML
    private RadioButton rdbCovidYes;

       
    //@FXML
    //private RadioButton rdbContactNo; 
    @FXML
    private Button btnHome;
    @FXML
    private Button btnSubmit;
    //@FXML
    //private TextField txtCountry;

    
   
    public void gvHomeButton(ActionEvent event) throws IOException {
        if(currentUser.getAccountType().equals("PHA")){
        
            Parent aHomeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
            Scene aHome = new Scene(aHomeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(aHome);
            window.show();

        } else {
            Parent gvParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
            Scene gvHome = new Scene(gvParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(gvHome);
            window.show();
        }
    }
    
 
    
    /**
     * Method will change scene to pInfo when called
     */
    @FXML
    public void gvSubmitButton(ActionEvent event) throws IOException {

         
        if(rdbSymptomsYes.isSelected())
        { 
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
            //txtCountry.setDisable(false);
        } 
        else if(rdbAbroadYes.isSelected())  
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else if(rdbBleedYes.isSelected())  
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else if(rdbCovidYes.isSelected())  
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else if(rdbPregnantYes.isSelected())  
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else if(rdbVaccineYes.isSelected())  
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else if(rdbContactYes.isSelected())
        {
            displayAlert("STAY AT HOME","You are not eligible for a vaccine.\n Please wait until you have had zero symptoms and been within the country for 14 days before applying for your vaccination",WARNING);
        }
        else
        {
            displayAlert("STAY AT HOME","You are eligible for a vaccine.",CONFIRMATION);
        
            Patient patient = new Patient();
            patient = dataPersistenceLayer.getPatientById(currentUser.getUserId());
            patient.setEligibility(true);

            //count of current active (ie not cancelled) appointments for the user
            int count =dataPersistenceLayer.getAppsByUserIdActive(patient.getUserId()).size();
            
            if(count<2){
                patient.setMaySchedule(true);
            } else {
                patient.setMaySchedule(false);
             }

            dataPersistenceLayer.updatePatient(patient);

            System.out.println(patient);
            Parent gvParent = FXMLLoader.load(getClass().getResource("pVacLocation.fxml"));
            Scene gvSubmit = new Scene(gvParent);
        
        
            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
            //Add if statement with confirmbox      
             
            window.setScene(gvSubmit);
            window.show();
        }
       
        
       
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }
  
}  
