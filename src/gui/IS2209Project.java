package gui;

import bll.User;
import dal.DataAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class IS2209Project extends Application {
    static DataAccess dataPersistenceLayer;
    static User currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {     
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));                   
        primaryStage.setTitle("Health-Help");
        primaryStage.setScene(new Scene(root, 831, 525));
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();

        
    }
    
    public IS2209Project() {
         dataPersistenceLayer = DataAccess.getInstance();
         currentUser = new User();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        

    }
    
}
