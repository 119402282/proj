/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import bll.VaccinationCentre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import bll.VaccinationCentre;
import static bll.CsvInterface.formatEircode;
import static gui.AlertBox.displayAlert;

import static gui.IS2209Project.dataPersistenceLayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class AVaccineCentresController implements Initializable {
    /**
     * Method will change scene to aHome when called
     *
     * @param event
     * @throws IOException
     */

    @FXML
    private TableView<VaccinationCentre> tblCentres;
    @FXML
    private TableColumn<VaccinationCentre, String> colEircode;
    @FXML
    private TableColumn<VaccinationCentre, String> colName;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEircode;
    
    
    
    public void aHomeButton(ActionEvent event) throws IOException {

        Parent homeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
        Scene home = new Scene(homeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(home);
        window.show();

        System.out.println(getCentres().toString());
    }

    public void addButton(ActionEvent event) throws IOException {
        
        VaccinationCentre centre = new VaccinationCentre(formatEircode(txtEircode.getText().toUpperCase()), txtName.getText() );
        
        if(centre.getCentreEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
            
        } else if (centre.getCentreName().length()<2 || centre.getCentreName().length()>50){
            displayAlert("Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (dataPersistenceLayer.getCentreByEircode(centre.getCentreEircode() )!= null ){
            displayAlert("Already Exists", "The centre entered already exists. Click update if you are trying to change an existing entry", WARNING);
        } else {
            dataPersistenceLayer.createVacCentre(centre);
            tblCentres.setItems(getCentres());
            txtEircode.setText("");
            txtName.setText("");
        }
        
    }
    
     public void updateButton(ActionEvent event) throws IOException {
        
        VaccinationCentre centre = new VaccinationCentre(formatEircode(txtEircode.getText().toUpperCase()), txtName.getText() );
        
        if(centre.getCentreEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
            
        } else if (centre.getCentreName().length()<2 || centre.getCentreName().length()>50){
            displayAlert("Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (dataPersistenceLayer.getCentreByEircode(centre.getCentreEircode() )== null ){
            displayAlert("Existing Centre Not Found", "The centre entered could not be located. /n This may be a result of attempting to change the unchangeable Eircode field", WARNING);
        }else {    
            dataPersistenceLayer.updateCentre(centre);
            tblCentres.setItems(getCentres());
            txtEircode.setText("");
            txtName.setText("");
        }
        
    }
     
      public void deleteButton(ActionEvent event) throws IOException {
        
        VaccinationCentre centre = new VaccinationCentre(formatEircode(txtEircode.getText().toUpperCase()), txtName.getText() );
        
        if(centre.getCentreEircode().length()!= 8){
             displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. \n Enter the eircode in the following format: TXX XXXX.", WARNING);
            
        } else if (centre.getCentreName().length()<2 || centre.getCentreName().length()>50){
            displayAlert("Name Error", "The name must be at least two letters with maximum of 50 letters in length.", WARNING);
        } else if (dataPersistenceLayer.getCentreByEircode(centre.getCentreEircode() )== null ){
            displayAlert("Existing Centre Not Found", "The centre entered could not be located. /n This may be a result of attempting to change the unchangeable Eircode field", WARNING);
        } else if (!dataPersistenceLayer.getAppsByCentreEircodeActiveOngoing(centre.getCentreEircode()).isEmpty() ){
            displayAlert("Existing Workflow", "This centre may not be deleted as it has ongoing vaccination requests", WARNING);
        } else {    
            dataPersistenceLayer.delVaccinationCentre(centre.getCentreEircode());
            tblCentres.setItems(getCentres());
            txtEircode.setText("");
            txtName.setText("");
        }
        
    }
     
       public void clearButton(ActionEvent event) throws IOException {
           txtEircode.setText("");
           txtName.setText("");
       
       }
           
    /**
     * Method will change scene to aVaccCentres when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccRequestsButton(ActionEvent event) throws IOException {
        Parent vaccRequestsParent = FXMLLoader.load(getClass().getResource("aVaccineRequests.fxml"));
        Scene vaccRequests = new Scene(vaccRequestsParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //Add if statement with confirmbox
        
        window.setScene(vaccRequests);
        window.show();
    }
    
    /**
     * Method will change scene to pArticles when called
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
    public void aVaccineInformationButton(ActionEvent event) throws IOException {
        Parent vaccInfoParent = FXMLLoader.load(getClass().getResource("aVaccineInfo.fxml"));
        Scene vaccInfo = new Scene(vaccInfoParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(vaccInfo);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccineInfo when called
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
    
    
    public ObservableList<VaccinationCentre> getCentres(){
        ObservableList<VaccinationCentre> centres = FXCollections.observableArrayList(dataPersistenceLayer.getVacCentreArrayList());
        return centres;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colEircode.setCellValueFactory(new PropertyValueFactory<VaccinationCentre, String>("centreEircode"));
        colName.setCellValueFactory(new PropertyValueFactory<VaccinationCentre, String>("centreName"));


        tblCentres.setItems(getCentres());
        
       
        
        tblCentres.setOnMouseClicked((MouseEvent event) -> {
        if(event.getButton().equals(MouseButton.PRIMARY)){
             //Check whether item is selected and set value of selected item to Label
            if(tblCentres.getSelectionModel().getSelectedItem() != null) {
                VaccinationCentre vacc =tblCentres.getSelectionModel().getSelectedItem();
                txtEircode.setText(vacc.getCentreEircode());
                txtName.setText(vacc.getCentreName());
            } else{
                txtEircode.setText("");
                txtName.setText("");
            }
        }
    });
       
    }   
    
}
