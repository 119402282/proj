//Patient info controller (after questionnaire)
package gui;

import static bll.CsvInterface.formatEircode;
import bll.Patient;
import static gui.AlertBox.displayAlert;
import static gui.IS2209Project.currentUser;
import static gui.IS2209Project.dataPersistenceLayer;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * FXML Controller class
 *
 * @author eliot
 */

public class PRegistrationController implements Initializable {

    @FXML
    private TextField txtAddress;
    @FXML
    private DatePicker dtpDOB = new DatePicker();
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtEmail;

    /**
     * Method will change scene to pHome when called
     * @param event
     * @throws java.io.IOException
     */

    public void regSubmitButton(ActionEvent event) throws IOException {

        if (dtpDOB.getValue() == null) {
            displayAlert("Error","Please select a date:",ERROR);
        } else {
            //changes LocalDate to Date
            LocalDate localDOB = (dtpDOB.getValue());
            Instant instantDate = Instant.from(localDOB.atStartOfDay(ZoneId.systemDefault()));
            Date dateOfBirth = Date.from(instantDate);

            Patient theUser = new Patient();

            theUser.setUserId(currentUser.getUserId());
            theUser.setPassword(currentUser.getPassword());
            theUser.setFName(currentUser.getFName());
            theUser.setLName(currentUser.getLName());
            theUser.setAccountType(currentUser.getAccountType());

            theUser.setDob(dateOfBirth);
            theUser.setMobile(txtTel.getText());
            theUser.setEmail(txtEmail.getText().toLowerCase());
            theUser.setEircode(formatEircode(txtAddress.getText().toUpperCase()));

            Period diff=Period.between(localDOB, LocalDate.now());
            System.out.println(diff);


            System.out.println(theUser.getEircode().length() != 8 || theUser.getEircode().equals("false"));
            if(theUser.getEircode().equals("")) {
                displayAlert("Error","Please fill all areas of the form",ERROR);
            } else if (theUser.getEircode().length() != 8 || theUser.getEircode().equals("false")) {
                displayAlert("Eircode Error", "The Eircode entered doesn't match the length requirements. Enter the eircode in the following format: TXX XXXX.", ERROR);
            } else if(isNull(theUser.getDob())) {
                displayAlert("Error","Please fill all areas of the form",ERROR);
            } else if(diff.getYears() < 18) {
                displayAlert("Apologies","It would appear that you are too young to register",WARNING);
            } else if(theUser.getMobile().equals("")) {
                displayAlert("Error","Please fill all areas of the form",ERROR);
            } else if ((theUser.getMobile().length() > 10) || (theUser.getMobile().length() < 10) || (!theUser.getMobile().matches("[0-9]+")) ){
                displayAlert("Mobile Error", "The Phone Number entered is invalid.", ERROR);
            } else if (theUser.getEmail().equals("")) {
                displayAlert("Error","Please fill all areas of the form",ERROR);
            } else {

                dataPersistenceLayer.createPatient(theUser);
                Parent regParent = FXMLLoader.load(getClass().getResource("pHome.fxml"));
                Scene regSubmit = new Scene(regParent);

                //gets stage info
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(regSubmit);
                window.show();

            }


        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}