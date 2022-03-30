/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Dylan
 */

public class AlertBox {
    


    public static void displayAlert(String title, String message, AlertType type) {
       
        Alert alert = new Alert(AlertType.NONE);
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
        
    }
}