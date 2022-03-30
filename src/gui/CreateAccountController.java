/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bll.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import dal.DataAccess;
import static gui.AlertBox.displayAlert;

import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import static java.util.Objects.isNull;
import static javafx.scene.control.Alert.AlertType.*;

import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shane
 */
public class CreateAccountController implements Initializable {
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
    }    
    
    @FXML
    private TextField txtFname;
    @FXML
    private TextField txtLname;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtPPS;
    @FXML
    private Button btnCreateAccount;
    
    public void createAccountButton(ActionEvent event) throws IOException {
        
        //Binding the user to the controls
        User theUser = new User();
        theUser.setUserId(txtPPS.getText());
        theUser.setFName(txtFname.getText());
        theUser.setLName(txtLname.getText());
        theUser.setPassword(txtPassword.getText());
        theUser.setAccountType("Patient");
        
        //if statement to make sure text fields aren't empty
        if(theUser.getFName().equals(""))
        {
            displayAlert("ERROR!", "Please enter your first name.",ERROR);
        } 
        else if (theUser.getFName().length() < 2 || (theUser.getFName().length() > 50)) {
            displayAlert("First Name Error", "The First Name entered doesn't match the length requirements. \n A first name must be greater than 2 characters.", ERROR);
        }
        else if(theUser.getLName().equals(""))
        {
            displayAlert("ERROR!", "Please enter your last name.",ERROR);
        }
        else if (theUser.getLName().length() < 2 || (theUser.getLName().length() > 50)) {
            displayAlert("Last Name Error", "The Last Name entered doesn't match the length requirements. \n A last name must be greater than 2 characters.", ERROR);
        }
        else if(theUser.getPassword().equals(""))
        {
            displayAlert("ERROR!", "Please enter a password.",ERROR);
        }
        else if (theUser.getPassword().length() < 6 || (theUser.getPassword().length() > 20)) {
            displayAlert("Password Error", "The Password entered doesn't match the length requirements. \n A password must be between 6 and 20 characters.", ERROR);
        }
        else if(theUser.getUserId().equals(""))
        {
            displayAlert("ERROR!", "Please enter your PPS number.",ERROR);
        } else if (theUser.getUserId().length() < 8 || (theUser.getUserId().length() > 9)) {
            displayAlert("PPSN Error", "The PPS number entered doesn't match the required length for a PPS number. \n PPS numbers must be between 8 and 9 characters.", ERROR);
        }
        else
        {
            displayAlert("Information!", "You have successfully created an account! \n Your PPS number will be used as your username to log in!",INFORMATION);

            dataPersistenceLayer.createUser(theUser);
            currentUser = theUser;

            Parent createAccountParent = FXMLLoader.load(getClass().getResource("pRegistration.fxml"));
            Scene createAccount = new Scene(createAccountParent);

            //gets stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            System.out.println(currentUser);
            window.setScene(createAccount);
            window.show();
        }
         
         
        
    }
    public void backHomeButton(ActionEvent event) throws IOException {
        Parent cLogOutParent = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene cLogOutScene = new Scene(cLogOutParent);
        
        //gets stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(cLogOutScene);
        window.show();
    }
    
         
}
