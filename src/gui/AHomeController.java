//Admin Home Controller
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class AHomeController implements Initializable {
    
    /**
     * Method will change scene to aUserBookApp when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccReqButton(ActionEvent event) throws IOException {
        Parent vacReqParent = FXMLLoader.load(getClass().getResource("aVaccineRequests.fxml"));
        Scene vacReqSubmit = new Scene(vacReqParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        
        window.setScene(vacReqSubmit);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccCentres when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccCentreButton(ActionEvent event) throws IOException {
        Parent vacCentreParent = FXMLLoader.load(getClass().getResource("aVaccineCentres.fxml"));
        Scene vacCentre = new Scene(vacCentreParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        
        window.setScene(vacCentre);
        window.show();
    }
    
    /**
     * Method will change scene to pLearnMore, but as an admin when called
     * @param event
     * @throws java.io.IOException
    */
    public void aArticlesButton(ActionEvent event) throws IOException {
        Parent articlesParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene articles = new Scene(articlesParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(articles);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccineInfo when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccInfoButton(ActionEvent event) throws IOException {
        Parent vInfoParent = FXMLLoader.load(getClass().getResource("aVaccineInfo.fxml"));
        Scene vInfo = new Scene(vInfoParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(vInfo);
        window.show();
    }
    
    /**
     * Method will change scene to LogIn when called
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
        
       
        window.setX(120); //changes stage x pos on start
        window.setY(250);//changes stage y pos on start
        window.setScene(patient);
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
