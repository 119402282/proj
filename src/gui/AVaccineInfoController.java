/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static bll.CsvInterface.dateToLocal;
import static bll.CsvInterface.formatEircode;
import bll.Patient;
import bll.VaccinationCentre;
import bll.Vaccine;
import bll.VaccineBatch;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bll.*;
import static gui.AlertBox.displayAlert;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * FXML Controller class
 *
 * @author eliot
 */
public class AVaccineInfoController implements Initializable {
    
    /**
     * Method will change scene to aHome when called
     * @param event
     * @throws java.io.IOException
    */
    
    @FXML 
    private TableView<VaccineBatch> tblVaccineInfo;
    @FXML 
    private TableColumn<VaccineBatch, Integer> colBatchNo;
    @FXML 
    private TableColumn<VaccineBatch, String> colVaccName;
    @FXML 
    private TableColumn<VaccineBatch, Double> colStorage;
    @FXML  
    private TableColumn<VaccineBatch, Integer> colQuantityOrdered;
    @FXML
    private TableColumn<VaccineBatch, Integer> colQuantityUsed;
    @FXML 
    private TableColumn<VaccineBatch, String> colAvailability;
    @FXML
    private TableColumn<VaccineBatch, Date> colExpiryDate;
    @FXML
    private TableColumn<VaccineBatch, String> colDesc;
    @FXML
    private TableColumn<VaccineBatch, Integer> colDoses;
    @FXML
    private TableColumn<VaccineBatch, Double> colEfficacy;
    @FXML
    private ComboBox cmbVaccineName;
    @FXML
    private TextField txtVaccId;
    @FXML
    private TextField txtStorage;
    @FXML
    private TextField txtQuantityOrdered;
    @FXML
    private TextField txtQuantityUsed;
    @FXML
    private DatePicker dteExpiry; 
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDoses;
    @FXML
    private TextField txtEfficacy;
    
    
    public void aHomeButton(ActionEvent event) throws IOException {
        Parent homeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
        Scene home = new Scene(homeParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(home);
        window.show();
    }
    
     public void addButton(ActionEvent event) throws IOException {
          
        if(!txtQuantityOrdered.getText().matches("[0-9]+")) {
            displayAlert("Invalid Character","Please enter a numeric value.",ERROR);    

        
        } else if (!txtQuantityUsed.getText().matches("[0-9]+")){    
            displayAlert("Invalid Character","Please enter a numeric value.",ERROR);
            
        } else if(cmbVaccineName.getSelectionModel().getSelectedItem() == null){
            displayAlert("Missing Field","Please select a vaccine name from the combo box",ERROR);
            
        } else if (dteExpiry.getValue().isBefore(LocalDate.now())){
            displayAlert("Invalid Date","Please select a date in the future.",ERROR);  
        
        } else if(parseInt(txtQuantityOrdered.getText()) < parseInt(txtQuantityUsed.getText())){
            displayAlert("Invalid Order","Amount used cannot exceed amount ordered",ERROR);
        } else {    
            LocalDate local = (dteExpiry.getValue());
            Instant instantDate = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
            Date dateExpiry = Date.from(instantDate);
            VaccineBatch batch = new VaccineBatch(parseInt(txtVaccId.getText()),
                                              cmbVaccineName.getValue().toString(),
                                              true,
                                              txtDescription.getText(),
                                              parseInt(txtDoses.getText()),
                                              parseDouble(txtStorage.getText()),
                                              parseDouble(txtEfficacy.getText()),
                                              dataPersistenceLayer.nextBatchId(),
                                              parseInt(txtQuantityOrdered.getText()),
                                              parseInt(txtQuantityUsed.getText()),
                                              dateExpiry);
            if(dataPersistenceLayer.getVaccBatchById(batch.getBatchNo())==null){
                dataPersistenceLayer.createVacBatch(batch);
                tblVaccineInfo.setItems(getBatches());
            } else {
                dataPersistenceLayer.updateVaccine(batch);
                tblVaccineInfo.setItems(getBatches());
            }
            tblVaccineInfo.refresh();
        }
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
             
        window.setScene(vaccRequests);
        window.show();
    }
    
    /**
     * Method will change scene to pArticles when called
     * @param event
     * @throws java.io.IOException
    */
    public void aVaccCentresButton(ActionEvent event) throws IOException {
        Parent vaccCentresParent = FXMLLoader.load(getClass().getResource("aVaccineCentres.fxml"));
        Scene vaccCentres = new Scene(vaccCentresParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(vaccCentres);
        window.show();
    }
    
    /**
     * Method will change scene to aVaccineInfo when called
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
        
        
        colBatchNo.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Integer>("batchNo"));
        colVaccName.setCellValueFactory(new PropertyValueFactory<VaccineBatch, String>("vaccName"));
        colStorage.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Double>("vaccineTemp"));
        colQuantityOrdered.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Integer>("orderQty"));
        colQuantityUsed.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Integer>("qtyUsed"));
        colAvailability.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQtyRemainingAsString()));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Date>("expiryDate"));
        colDesc.setCellValueFactory(new PropertyValueFactory<VaccineBatch, String>("vaccTypeDesc"));
        colDoses.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Integer>("reqDoses"));
        colEfficacy.setCellValueFactory(new PropertyValueFactory<VaccineBatch, Double>("efficacy")); 
        
        
        tblVaccineInfo.setItems(getBatches());
        
        cmbVaccineName.setItems(FXCollections.observableArrayList(dataPersistenceLayer.getVaccineArrayList()));
        
        cmbVaccineName.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            
            if(!cmbVaccineName.getSelectionModel().isEmpty()){
            txtVaccId.setText(dataPersistenceLayer.getVaccineByName(cmbVaccineName.getValue().toString()).getVaccIdString());
            txtStorage.setText(dataPersistenceLayer.getVaccineByName(cmbVaccineName.getValue().toString()).getVaccineTempAsString());
            txtDescription.setText(dataPersistenceLayer.getVaccineByName(cmbVaccineName.getValue().toString()).getVaccTypeDesc());
            txtDoses.setText(dataPersistenceLayer.getVaccineByName(cmbVaccineName.getValue().toString()).getReqDosesAsString());
            txtEfficacy.setText(dataPersistenceLayer.getVaccineByName(cmbVaccineName.getValue().toString()).getEfficacyAsString());
            } 
             
            }
        
        );
    }    
    
    public ObservableList<VaccineBatch> getBatches() {
        
        return FXCollections.observableArrayList(dataPersistenceLayer.getVacBatchArrayList());
    }
}