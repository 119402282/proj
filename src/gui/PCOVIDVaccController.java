// COVID vaccine info controller
package gui;

import bll.Patient;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Vaccine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import static gui.IS2209Project.dataPersistenceLayer;
import javafx.scene.control.Button;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;



/**
 * FXML Controller class
 *
 * @author eliot
 */

public class PCOVIDVaccController implements Initializable {

    
    //declaring labels
    
    @FXML private Label lblPfizer;
    @FXML private Label lblModerna;
    @FXML private Label lblAZ;
    ArrayList<Label> names = new ArrayList<>();

    //setting descriptions
    @FXML private TextArea txtPfDesc;
    @FXML private TextArea txtMdDesc;
    @FXML private TextArea txtAZDesc;
    ArrayList<TextArea> descriptions = new ArrayList<>();

    //setting doses
    @FXML private TextArea txtPfDose;
    @FXML private TextArea txtMdDose;
    @FXML private TextArea txtAZDose;
    ArrayList<TextArea> doses = new ArrayList<>();

    //setting efficacy
    @FXML private TextArea txtPfEff;
    @FXML private TextArea txtMdEff;
    @FXML private TextArea txtAZEff;
    ArrayList<TextArea> efficacy =  new ArrayList<>();

    ArrayList<Vaccine> vaccs = new ArrayList<>();

    //declaring button
    @FXML private Button btnSave;
    @FXML private Button btnLearnMore;
    @FXML private Button btnAppointments;
    @FXML private Button btnGetVaccinated;

    /**
    * Method will change scene to pRegistration when called
    * @param event
    * @throws java.io.IOException
    */
    public void moreInfoButton(ActionEvent event) throws IOException {
        Parent pCVaccMoreInfoParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pCVaccMoreInfo = new Scene(pCVaccMoreInfoParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCVaccMoreInfo);
        window.show();
    }
    
    /**
    * Method will change scene to pCOVID when called
    * @param event
    * @throws java.io.IOException
    */
    
    public void infReturnButton(ActionEvent event) throws IOException {
        Parent pInfReturnParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pReturn = new Scene(pInfReturnParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pReturn);
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
    
     public void infCovidButton(ActionEvent event) throws IOException {
        Parent pInfCovidParent = FXMLLoader.load(getClass().getResource("pCOVID.fxml"));
        Scene pCovid = new Scene(pInfCovidParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCovid);
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
    
    public boolean goodInputs(){
        for (TextArea item : efficacy) {
            if(!item.getText().matches("[0-9]+(\\.){0,1}[0-9]*")){
                return false;
            }
        }
        for (TextArea item : doses) {
            if(!item.getText().matches("\\d+")){
                return false;
            }
        }
        return true;
    }
      
      
    public void btnAction(ActionEvent event) {
       
        
        //validation for number only boxes
        if(goodInputs()){
            ArrayList<Double> eff = new ArrayList<>();
            ArrayList<Integer> reqD = new ArrayList<>();
            for (TextArea item : efficacy) {
                eff.add(Double.valueOf(item.getText()));
            }
            for (TextArea item : doses) {
                reqD.add(Integer.valueOf(item.getText()));
            }
            for (int i = 0; i < dataPersistenceLayer.getVaccineArrayList().size(); i++) {
                dataPersistenceLayer.updateVaccine(new Vaccine(vaccs.get(i).getVaccId(), names.get(i).getText(), vaccs.get(i).isVaccAuthorized(), descriptions.get(i).getText(), reqD.get(i), vaccs.get(i).getVaccineTemp(), eff.get(i)));
            }
            vaccs.set(0,dataPersistenceLayer.getVaccineByName("Pfizer/BioNTech"));
            vaccs.set(1,dataPersistenceLayer.getVaccineByName("Moderna"));
            vaccs.set(2,dataPersistenceLayer.getVaccineByName("AstraZeneca"));

        } else {
            displayAlert("Input Error", "The doses and the efficacy must be given as numbers", ERROR);
            for (int i = 0; i< 3; i++) {
            names.get(i).setText(vaccs.get(i).getVaccName());
            descriptions.get(i).setText(vaccs.get(i).getVaccTypeDesc());
            doses.get(i).setText(vaccs.get(i).getReqDosesAsString());
            efficacy.get(i).setText(vaccs.get(i).getEfficacyAsString());
            }
        }
    }
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        vaccs.add(dataPersistenceLayer.getVaccineByName("Pfizer/BioNTech"));
        vaccs.add(dataPersistenceLayer.getVaccineByName("Moderna"));
        vaccs.add(dataPersistenceLayer.getVaccineByName("AstraZeneca"));

        names.add(0,lblPfizer);
        names.add(1, lblModerna);
        names.add(2, lblAZ);

        descriptions.add(0, txtPfDesc);
        descriptions.add(1, txtMdDesc);
        descriptions.add(2, txtAZDesc);

        doses.add(0, txtPfDose);
        doses.add(1, txtMdDose);
        doses.add(2, txtAZDose);

        efficacy.add(0, txtPfEff);
        efficacy.add(1, txtMdEff);
        efficacy.add(2, txtAZEff);

         //logic to make sure only PHA can edit
        if(currentUser.getAccountType().equals("PHA")){

            for (TextArea box: efficacy) {
                box.setEditable(true);
            }
            for (TextArea box: descriptions) {
                box.setEditable(true);
            }
            for (TextArea box: doses) {
                box.setEditable(true);
            }

            btnLearnMore.setVisible(false);
            btnGetVaccinated.setVisible(false);
            btnAppointments.setVisible(false);

        } else {
                btnSave.setVisible(false);
        }
        for (int i = 0; i< 3; i++) {
            names.get(i).setText(vaccs.get(i).getVaccName());
            descriptions.get(i).setText(vaccs.get(i).getVaccTypeDesc());
            doses.get(i).setText(vaccs.get(i).getReqDosesAsString());
            efficacy.get(i).setText(vaccs.get(i).getEfficacyAsString());
        }

        // TODO
    }    
    
}
