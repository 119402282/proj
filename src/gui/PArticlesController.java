//Learn More Controller
package gui;

import static gui.IS2209Project.currentUser;
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
public class PArticlesController implements Initializable {

    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void homeButton(ActionEvent event) throws IOException {
         if(currentUser.getAccountType().equals("PHA")){
        
            Parent aHomeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
            Scene aHome = new Scene(aHomeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(aHome);
            window.show();

        } else {
        
            Parent pHomeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
            Scene pHome = new Scene(pHomeParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(pHome);
            window.show();
         }
    }
         
    
    /**
     * Method will change scene to pInfections when called
     * @param event
     * @throws java.io.IOException
     */
    public void InfDiseaseButton(ActionEvent event) throws IOException {
        Parent pLearnInfParent = FXMLLoader.load(getClass().getResource("pInfections.fxml"));
        Scene pInfDis = new Scene(pLearnInfParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pInfDis);
        window.show();
    }
    
    /**
     * Method will change scene to pCOVID when called
     * @param event
     * @throws java.io.IOException
     */
    public void covidButton(ActionEvent event) throws IOException {
        Parent pLearnCOVIDParent = FXMLLoader.load(getClass().getResource("pCOVID.fxml"));
        Scene pCOVID = new Scene(pLearnCOVIDParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCOVID);
        window.show();
    }
    
    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void covidVacButton(ActionEvent event) throws IOException {
        Parent pLearnCOVIDVacParent = FXMLLoader.load(getClass().getResource("pCOVIDVacc.fxml"));
        Scene pCOVIDVac = new Scene(pLearnCOVIDVacParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCOVIDVac);
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
