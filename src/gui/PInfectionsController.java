//infection info controller
package gui;

import bll.*;

import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static javafx.scene.control.Alert.AlertType.ERROR;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

/**
 * FXML Controller class
 *
 * @author eliot
 */

public class PInfectionsController implements Initializable {
    /**
     * Method will change scene to pLearnMore when called
     * @param event
     * @throws java.io.IOException
     */
    @FXML private ComboBox cboDiseases = new ComboBox();
    @FXML private TextArea txtInfo = new TextArea();
    @FXML private TextArea txtSymptoms = new TextArea();
    @FXML private TextArea txtRemedies = new TextArea();
    @FXML private Button btnSave;
    @FXML private Button btnNew;
    @FXML private Button btnApps;
    @FXML private Button btnGetVacc;

    ObservableList<InfectiousDiseases> options;
    InfectiousDiseases diseasesObservable;

    public void addNew(ActionEvent event){
        TextInputDialog inputBox = new TextInputDialog();
        inputBox.setTitle("New Disease");
        inputBox.setHeaderText("Enter the name of the new disease");
        inputBox.setContentText("Disease name: ");
        Optional<String> name = inputBox.showAndWait();
        if (name.equals("")) {
            displayAlert("Empty Fields", "No disease was added as the field was left blank", ERROR);
        } else if (!name.isPresent()) {
            displayAlert("Empty Fields", "No disease was added as the field was left blank", ERROR);
        } else if (dataPersistenceLayer.doesDiseaseExist(name.get())) {
                displayAlert("Disease Exists", "No disease was added as the disease already exists", ERROR);
        } else if (!name.toString().isEmpty()) {

            diseasesObservable = new InfectiousDiseases(name.get(), "Enter Info here", "Enter symptoms here", "Enter remedies here", dataPersistenceLayer.getPhaById(currentUser.getUserId()), new Date());
            dataPersistenceLayer.createDisease(diseasesObservable);

            cboDiseases.setOnAction(e -> {
            });
            options = FXCollections.observableArrayList(dataPersistenceLayer.getDiseasesArrayList());
            cboDiseases.getSelectionModel().clearSelection();
            cboDiseases.getItems().removeAll();
            cboDiseases.setItems(options);
            cboDiseases.setOnAction(e -> {
                diseasesObservable = (InfectiousDiseases) cboDiseases.getSelectionModel().getSelectedItem();
                if (!diseasesObservable.getRemedies().equals("")) {
                    txtRemedies.setText(diseasesObservable.getRemedies());
                } else {
                    txtRemedies.setText("");
                }
                if (!diseasesObservable.getSymptoms().equals("")) {
                    txtSymptoms.setText(diseasesObservable.getSymptoms());
                } else {
                    txtSymptoms.setText("");
                }
                if (!diseasesObservable.getInformation().equals("")) {
                    txtInfo.setText(diseasesObservable.getInformation());
                } else {
                    txtInfo.setText("");
                }
            });
            cboDiseases.getSelectionModel().selectLast();
        }
    }

    /**
     * Initializes the controller class.
     * @param event
     */
    //the save button
    public void btnAction(ActionEvent event) {

        int index = cboDiseases.getSelectionModel().getSelectedIndex();
        diseasesObservable = (InfectiousDiseases) cboDiseases.getSelectionModel().getSelectedItem();
        diseasesObservable.setRemedies(txtRemedies.getText());
        diseasesObservable.setSymptoms(txtSymptoms.getText());
        diseasesObservable.setInformation(txtInfo.getText());
        diseasesObservable.setAuthor(dataPersistenceLayer.getPhaById(currentUser.getUserId()));
        diseasesObservable.setEdited(new Date());
        dataPersistenceLayer.updateDisease(diseasesObservable);

        cboDiseases.setOnAction(e -> {});
        options = FXCollections.observableArrayList(dataPersistenceLayer.getDiseasesArrayList());
        cboDiseases.getSelectionModel().clearSelection();
        cboDiseases.getItems().removeAll();
        cboDiseases.setItems(options);
        cboDiseases.setOnAction(e -> {
            diseasesObservable = (InfectiousDiseases) cboDiseases.getSelectionModel().getSelectedItem();
            if(!diseasesObservable.getRemedies().equals("")){
                txtRemedies.setText(diseasesObservable.getRemedies());
            } else {
                txtRemedies.setText("");
            }
            if(!diseasesObservable.getSymptoms().equals("")){
                txtSymptoms.setText(diseasesObservable.getSymptoms());
            } else {
                txtSymptoms.setText("");
            }
            if(!diseasesObservable.getInformation().equals("")){
                txtInfo.setText(diseasesObservable.getInformation());
            } else {
                txtInfo.setText("");
            }
        });
        cboDiseases.getSelectionModel().select(index);
    }

    public void doNothing(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //logic to make sure only PHA can edit
        if(currentUser.getAccountType().equals("PHA")){
            //set items as editable
            txtInfo.setEditable(true);
            txtSymptoms.setEditable(true);
            txtRemedies.setEditable(true);
            btnSave.setVisible(true);
            btnNew.setVisible(true);
            btnApps.setVisible(false);
            btnGetVacc.setVisible(false);
        } else {
            btnApps.setVisible(true);
            btnGetVacc.setVisible(true);
            btnSave.setVisible(false);
            btnNew.setVisible(false);
        }

        options = FXCollections.observableArrayList(dataPersistenceLayer.getDiseasesArrayList());
        cboDiseases.getSelectionModel().clearSelection();
        cboDiseases.setItems(options);
        cboDiseases.getSelectionModel().selectFirst();

        //adds diseases array to the combobox
        diseasesObservable = (InfectiousDiseases) cboDiseases.getSelectionModel().getSelectedItem();
        txtRemedies.setText(diseasesObservable.getRemedies());
        txtSymptoms.setText(diseasesObservable.getSymptoms());
        txtInfo.setText(diseasesObservable.getInformation());

    }

    public void addListenerCombo(ActionEvent event){
        diseasesObservable = (InfectiousDiseases) cboDiseases.getSelectionModel().getSelectedItem();
        if(!diseasesObservable.getRemedies().equals("")){
            txtRemedies.setText(diseasesObservable.getRemedies());
        } else {
            txtRemedies.setText("");
        }
        if(!diseasesObservable.getSymptoms().equals("")){
            txtSymptoms.setText(diseasesObservable.getSymptoms());
        } else {
            txtSymptoms.setText("");
        }
        if(!diseasesObservable.getInformation().equals("")){
            txtInfo.setText(diseasesObservable.getInformation());
        } else {
            txtInfo.setText("");
        }
    }

    public void infReturnButton(ActionEvent event) throws IOException {
        Parent pInfReturnParent = FXMLLoader.load(getClass().getResource("pArticles.fxml"));
        Scene pReturn = new Scene(pInfReturnParent);

        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(pReturn);
        window.show();
    }
    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void pHomeButton(ActionEvent event) throws IOException {
        if(currentUser.getAccountType().equals("PHA")){
            Parent homeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
            Scene homeInfo = new Scene(homeParent);

            //gets stage info
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //Add if statement with confirmbox

            window.setScene(homeInfo);
            window.show();
        }else {
            Parent homeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
            Scene homeInfo = new Scene(homeParent);

            //gets stage info
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //Add if statement with confirmbox

            window.setScene(homeInfo);
            window.show();
        }

    }
    public void infCovidButton(ActionEvent event) throws IOException {
        Parent pInfCovidParent = FXMLLoader.load(getClass().getResource("pCOVID.fxml"));
        Scene pCovid = new Scene(pInfCovidParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(pCovid);
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
    public void logOutButton(ActionEvent event) throws IOException {
        Parent pHomeParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene logIn = new Scene(pHomeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(logIn);
        window.show();
    }
}