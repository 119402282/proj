//Log In LogInController
package gui;

import bll.User;
import dal.DataAccess;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {
    /**
     * Method will change scene to pHome when called
     *
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    User logUser = new User();


    public void logInButton(ActionEvent event) throws IOException {
        logUser = dataPersistenceLayer.getUserById(txtUser.getText());
        if (Objects.equals(logUser, null)){
            displayAlert("User Not Found", "No such user exists in our database",ERROR);
        } else if (txtUser.getText().equals(logUser.getUserId()) && txtPass.getText().equals(logUser.getPassword())) {


            currentUser = logUser;

            if (logUser.getAccountType().equals("Patient")) {
                if(dataPersistenceLayer.getPatientById(currentUser.getUserId()) == null) {
                    Parent createAccountParent = FXMLLoader.load(getClass().getResource("pRegistration.fxml"));
                    Scene createAccount = new Scene(createAccountParent);

                    //gets stage info
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(createAccount);
                    window.show();
                }else{
                    Parent pHomeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
                    Scene pHomeScene = new Scene(pHomeParent);

                    //gets stage info
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    window.setScene(pHomeScene);
                    window.show();
                }
            } else if (logUser.getAccountType().equals("Clinician")) {
                Parent cHomeParent = FXMLLoader.load(getClass().getResource("cHome.fxml"));
                Scene cHomeScene = new Scene(cHomeParent);

                //gets stage info
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(cHomeScene);
                window.show();
            } else if (logUser.getAccountType().equals("PHA")) {
                Parent aHomeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
                Scene aHomeScene = new Scene(aHomeParent);

                //gets stage info
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(aHomeScene);
                window.show();
            }
        } else {
            displayAlert("Sorry", "Incorrect password",ERROR);

        }
    }
        /**
         * Method will change scene to cHome when called
         * @param event
         * @throws java.io.IOException
         */
    public void ClinicianlogInButton(ActionEvent event) throws IOException {
        currentUser = dataPersistenceLayer.getUserById("lcotter546@hse.ie");
        Parent cHomeParent = FXMLLoader.load(getClass().getResource("cHome.fxml"));
        Scene cHomeScene = new Scene(cHomeParent);

        //gets stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(cHomeScene);
        window.show();
    }

    /**
     * Method will change scene to aHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void AdminlogInButton (ActionEvent event) throws IOException {
        currentUser = dataPersistenceLayer.getUserArrayList().get(1);
        Parent aHomeParent = FXMLLoader.load(getClass().getResource("aHome.fxml"));
        Scene aHomeScene = new Scene(aHomeParent);

        //gets stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(aHomeScene);
        window.show();
    }
    
    /**
     * Method will change scene to aHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void PatientlogInButton (ActionEvent event) throws IOException {
        currentUser = dataPersistenceLayer.getUserArrayList().get(0);
        Parent aHomeParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
        Scene aHomeScene = new Scene(aHomeParent);

        //gets stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(aHomeScene);
        window.show();
    }

    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
     */
    public void noAccButton (ActionEvent event) throws IOException {
        
        Parent noAccParent = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene noAccScene = new Scene(noAccParent);
        
        //gets stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(noAccScene);
        window.show();
    }


}



