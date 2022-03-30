//Patient Home Controller
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class PHomeController implements Initializable {
    
    /**
     * Method will change scene to LogIn when called
     * @param event
     * @throws java.io.IOException
    */
    public void logOutButton(ActionEvent event) throws IOException {
        Parent pHomeParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene logIn = new Scene(pHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(logIn);
        window.show();
    }
    
    /**
     * Method will change scene to getVacc when called
     * @param event
     * @throws java.io.IOException
    */
    public void getVaccButton(ActionEvent event) throws IOException {
        
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
    
    /**
     * Method will change scene to learnMore when called
     * @param event
     * @throws java.io.IOException
    */
    public void learnMoreButton(ActionEvent event) throws IOException {
        Parent pHomeParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pLearnMore = new Scene(pHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pLearnMore);
        window.show();
    }
    
    /**
     * Method will change scene to pAppointments when called
     * @param event
     * @throws java.io.IOException
    */
    public void appointmentsButton(ActionEvent event) throws IOException {
        Parent pHomeParent = FXMLLoader.load(getClass().getResource("pAppointments.fxml"));
        Scene pAppointments = new Scene(pHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pAppointments);
        window.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    
    
}
